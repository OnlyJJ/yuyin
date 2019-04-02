package com.jiujun.voice.modules.apps.user.useraccount.handle;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jiujun.voice.common.cmd.vo.Header;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.http.HttpUtil;
import com.jiujun.voice.common.utils.http.entity.HttpEntity;
import com.jiujun.voice.modules.apps.file.handle.FileWriteHandle;
import com.jiujun.voice.modules.apps.user.useraccount.domain.ThirdConfig;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
import com.jiujun.voice.modules.apps.user.useraccount.schema.UserAccountSchema;
import com.jiujun.voice.modules.apps.user.useraccount.service.ThirdConfigService;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;

/**
 * 
 * @author Coody
 *
 */
@Component
public class ThirdLoginHandle {

	@Resource
	private UserInfoService userInfoService;
	@Resource
	private UserAccountService userAccountService;

	@Resource
	ThirdConfigService thirdConfigService;

	@Resource
	FileWriteHandle fileWriteHandle;

	private static final String QQ_AUTH_URL = "https://graph.qq.com/user/get_user_info";

	private static final String WX_AUTH_URL = "https://api.weixin.qq.com/sns/userinfo";

	public UserAccountSchema getUserGeneralForQQByCode(String code, Header header) {
		ThirdConfig config = thirdConfigService.getConfig(header.getPackager(), 2);
		if (config == null) {
			LogUtil.logger.error("包名未配置>>packager:" + header.getPackager());
			throw new CmdException(ErrorCode.ERROR_1034);
		}
		String accessToken = getAccessTokenForQQ(code, header);
		if (StringUtil.isNullOrEmpty(accessToken)) {
			return null;
		}
		
		return getUserGeneralForQQByToken(accessToken, header);
	}

	public UserAccountSchema getUserGeneralForQQByToken(String accessToken, Header header) {
		try {
			ThirdConfig config = thirdConfigService.getConfig(header.getPackager(), 2);
			if (config == null) {
				LogUtil.logger.error("包名未配置>>packager:" + header.getPackager());
				throw new CmdException(ErrorCode.ERROR_1034);
			}
			// 获取unionId
			/*
			 * HttpEntity httpEntity = HttpUtil
			 * .get("https://graph.qq.com/oauth2.0/me?access_token=" +
			 * accessToken + "&unionid=1"); String html=httpEntity.getHtml();
			 * html=StringUtil.textCutCenter(html, "(", ")"); Map<String,
			 * String> map = JSON.parseObject(html, new
			 * TypeReference<Map<String, String>>() { }); String unionid =
			 * map.get("unionid"); if (StringUtil.isNullOrEmpty(unionid)) {
			 * logger.error("获取unionid失败>>openId:" + openId + ";accessToken:" +
			 * accessToken); throw new CmdException(ErrorCode.ERROR_1036); }
			 */
			Map<String, String> openMap = getOpenIdForQQ(accessToken);
			if (StringUtil.isNullOrEmpty(openMap)) {
				return null;
			}
			String openId = openMap.get("openid");
			HttpEntity httpEntity = HttpUtil.get(new StringBuffer(QQ_AUTH_URL).append("?").append("oauth_consumer_key=")
					.append(config.getAppId()).append("&access_token=").append(accessToken).append("&openid=")
					.append(openId).append("&unionid=1").toString(), "utf-8");
			String res = httpEntity.getHtml();
			res = res.replace("<", "＜");
			res = res.replace(">", "＞");
			res = res.replace("\r\n", "\n");
			res = res.replace("\n", " ");
			LogUtil.logger.info("QQ登录>>openId:" + openId + ";accessToken:" + accessToken + ">>" + res);
			Map<String, String> map = JSON.parseObject(res, new TypeReference<Map<String, String>>() {
			});
			if (StringUtil.isNullOrEmpty(map)) {
				LogUtil.logger.error("获取用户信息失败>>openId:" + openId + ";accessToken:" + accessToken);
				throw new CmdException(ErrorCode.ERROR_1033);
			}
			String unionId = openMap.get("unionid");
			UserAccount account = userAccountService.getUserAccount(unionId, 2);
			if (StringUtil.isNullOrEmpty(account)) {
				account = userAccountService.getUserAccount(openId, 2);
				if(!StringUtil.isNullOrEmpty(account)){
					account.setUnionId(unionId);
					userAccountService.updateUserAccount(account, "unionId");
				}
			}
			if (!StringUtil.isNullOrEmpty(account)) {
				account.setAccessToken(accessToken);
				userAccountService.changeAuthToken(account);
				UserAccountSchema schema = new UserAccountSchema();
				BeanUtils.copyProperties(account, schema);
				schema.setIsFirstLogin(0);
				return schema;
			}
			account = userAccountService.initUserAccount(map.get("nickname"), null, null, unionId, header.getClientId(),
					null, 2);
			account.setAccessToken(accessToken);
			userAccountService.changeAuthToken(account);
			// 更新用户资料
			UserInfo userInfo = userInfoService.getUserInfo(account.getUserId());

			userInfo.setSex(map.get("gender").equals("男") ? 1 : 0);
			String head = headDown(map.get("figureurl_qq_2"));
			userInfo.setHead(head);
			if (!StringUtil.isNullOrEmpty(map.get("year")) && StringUtil.toInteger(map.get("year")) != 0) {

				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, StringUtil.toInteger(map.get("year")));
				calendar.set(Calendar.MONTH, 0);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				Date birth = calendar.getTime();
				userInfo.setBirth(DateUtils.toString(birth, DateUtils.DATA_PATTERN));
				userInfo.setAge(Calendar.getInstance().get(Calendar.YEAR) - StringUtil.toInteger(map.get("year")) + 1);
				if (userInfo.getAge() > 200) {
					userInfo.setAge(18);
				}
			}
			userInfo.setCity(map.get("city"));
			userInfo.setProvince(map.get("province"));

			userInfoService.saveUserInfo(userInfo);
			UserAccountSchema schema = new UserAccountSchema();
			BeanUtils.copyProperties(account, schema);
			schema.setIsFirstLogin(1);
			return schema;
		} catch (CmdException e) {
			LogUtil.logger.error("获取用户信息失败>accessToken:" + accessToken);
			throw e;
		} catch (Exception e) {
			LogUtil.logger.error("获取用户信息失败>>accessToken:" + accessToken + "\r\n"
					+ PrintException.getErrorStack(e));
			throw new CmdException(ErrorCode.ERROR_1033);
		}
	}

	public UserAccountSchema getUserGeneralForWX(String code, Header header) {
		try {
			ThirdConfig config = thirdConfigService.getConfig(header.getPackager(), 3);
			if (config == null) {
				LogUtil.logger.error("包名未配置>>packager:" + header.getPackager());
				throw new CmdException(ErrorCode.ERROR_1034);
			}
			String accessToken = getAccessTokenForWX(config.getAppId(), config.getSecret(), code);
			if (StringUtil.isNullOrEmpty(accessToken)) {
				LogUtil.logger.error("获取AccessToken失败>>authId:" + code + ";accessToken:" + accessToken);
				throw new CmdException(ErrorCode.ERROR_1033);
			}
			HttpEntity httpEntity = HttpUtil.get(
					new StringBuffer(WX_AUTH_URL).append("?").append("oauth_consumer_key=").append(config.getAppId())
							.append("&access_token=").append(accessToken).append("&openid=").append(code).toString());
			String res = httpEntity.getHtml();
			res = res.replace("<", "＜");
			res = res.replace(">", "＞");
			res = res.replace("\r\n", "\n");
			res = res.replace("\n", " ");
			Map<String, String> map = JSON.parseObject(res, new TypeReference<Map<String, String>>() {
			});
			if (map == null || StringUtil.isNullOrEmpty(map.get("unionid"))) {
				LogUtil.logger.error("获取用户信息失败>>authId:" + code + ";accessToken:" + accessToken);
				throw new CmdException(ErrorCode.ERROR_1033);
			}
			String unionid = map.get("unionid");
			UserAccount account = userAccountService.getUserAccount(unionid, 3);
			if (!StringUtil.isNullOrEmpty(account)) {
				account.setAccessToken(accessToken);
				userAccountService.changeAuthToken(account);
				UserAccountSchema schema = new UserAccountSchema();
				BeanUtils.copyProperties(account, schema);
				schema.setIsFirstLogin(0);
				return schema;
			}
			account = userAccountService.initUserAccount(map.get("nickname"), null, null, unionid, header.getClientId(),
					null, 3);
			account.setAccessToken(accessToken);
			userAccountService.changeAuthToken(account);
			// 更新用户资料
			UserInfo userInfo = userInfoService.getUserInfo(account.getUserId());
			userInfo.setSex(map.get("sex").equals("1") ? 1 : 0);
			if (!StringUtil.isNullOrEmpty(map.get("year")) && StringUtil.toInteger(map.get("year")) != 0) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, StringUtil.toInteger(map.get("year")));
				calendar.set(Calendar.MONTH, 0);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				Date birth = calendar.getTime();
				userInfo.setBirth(DateUtils.toString(birth, DateUtils.DATA_PATTERN));
				userInfo.setAge(Calendar.getInstance().get(Calendar.YEAR) - StringUtil.toInteger(map.get("year")) + 1);
				if (userInfo.getAge() > 200) {
					userInfo.setAge(18);
				}
			}
			userInfo.setCity(map.get("city"));
			userInfo.setProvince(map.get("province"));
			String head = headDown(map.get("headimgurl"));
			userInfo.setHead(head);
			userInfoService.saveUserInfo(userInfo);
			UserAccountSchema schema = new UserAccountSchema();
			BeanUtils.copyProperties(account, schema);
			schema.setIsFirstLogin(1);
			return schema;
		} catch (CmdException e) {
			LogUtil.logger.error("获取用户信息失败>>authId:" + code);
			throw e;
		} catch (Exception e) {
			LogUtil.logger.error("获取用户信息失败>>authId:" + code);
			throw new CmdException(ErrorCode.ERROR_1033);
		}
	}

	private String getAccessTokenForWX(String appid, String secret, String code) {
		try {
			StringBuffer sbfUrl = new StringBuffer();
			sbfUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token").append("?").append("appid=")
					.append(appid).append("&secret=").append(secret).append("&code=").append(code)
					.append("&grant_type=authorization_code");
			// 请求微信服务器，获取到json字符串
			HttpEntity entity = HttpUtil.get(sbfUrl.toString());
			String res = entity.getHtml();
			Map<String, String> map = JSON.parseObject(res, new TypeReference<Map<String, String>>() {
			});
			LogUtil.logger.info("微信登录获取AccessToken>>" + JSON.toJSONString(map));
			return map.get("access_token");
		} catch (Exception e) {
			PrintException.printException(e);
		}
		return null;
	}

	private String getAccessTokenForQQ(String code, Header header) {
		ThirdConfig config = thirdConfigService.getConfig(header.getPackager(), 2);
		String data = "grant_type=authorization_code&client_id={0}&client_secret={1}&code={2}&redirect_uri=http://www.wowolive99.com/login/callbackLogin&scope=get_user_info";
		data = MessageFormat.format(data, config.getAppId(), config.getSecret(), code);
		try {
			HttpEntity result = HttpUtil.post("https://graph.qq.com/oauth2.0/token", data);
			String accessToken = StringUtil.textCutCenter(result.getHtml(), "access_token=", "&");
			return accessToken;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, String> getOpenIdForQQ(String accessToken) {
		String data = "access_token={0}&unionid=1";
		data = MessageFormat.format(data, accessToken);
		try {
			HttpEntity result = HttpUtil.post("https://graph.qq.com/oauth2.0/me", data);
			String json = StringUtil.textCutCenter(result.getHtml(), "callback(", ");");
			Map<String, String> map = (Map<String, String>) JSON.parseObject(json,
					new TypeReference<Map<String, String>>() {
					});
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String headDown(String url) {
		if (StringUtil.isNullOrEmpty(url)) {
			return null;
		}
		HttpEntity entity = HttpUtil.get(url);
		return fileWriteHandle.writeFile("head", "jpg", entity.getBye());
	}

}
