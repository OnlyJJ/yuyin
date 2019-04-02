package com.jiujun.voice.modules.apps.file.cmd;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.login.annotation.LoginIgnore;
import com.jiujun.voice.common.utils.FileUtils;
import com.jiujun.voice.common.utils.JUUIDUtil;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.file.cmd.vo.UploadByWCReqVO;
import com.jiujun.voice.modules.apps.file.cmd.vo.UploadByWCRespVO;
import com.jiujun.voice.modules.apps.file.cmd.vo.UploadReqVO;
import com.jiujun.voice.modules.apps.file.cmd.vo.UploadRespVO;
import com.jiujun.voice.modules.apps.file.config.OssConfig;
import com.jiujun.voice.modules.apps.file.handle.FileWriteHandle;
import com.jiujun.voice.modules.apps.file.util.HMacSHAUtil;

/**
 * 
 * @author Coody
 *
 */
@CmdOpen("file")
@DocFlag("文件上传")
public class FileCmd extends RootCmd {

	@Value("${file.savePath}")
	private String savePath;
	@Value("${file.domain}")
	private String domain;
	@Value("${wscdn.domain}")
	private String wsCdnDomain;

	@Resource
	FileWriteHandle fileWriteHandle;

	/**
	 * 图片类型 如：头像、相册
	 */
	private static final List<String> BUS_TYPE = Arrays
			.asList(new String[] { "head", "album", "report", "general", "audio" });
	/**
	 * 图片格式
	 */
	private static final List<String> ALLOW_SUFFIX = Arrays
			.asList(new String[] { "jpg", "png", "jpeg", "bmp", "gif", "mp3" });

	@CmdAction
	@DocFlag("单文件接口")
	public UploadRespVO upload(UploadReqVO req) {
		if (!ALLOW_SUFFIX.contains(req.getFileType().toLowerCase())) {
			return new UploadRespVO().pushErrorCode(ErrorCode.ERROR_1023);
		}
		if (!BUS_TYPE.contains(req.getBusType())) {
			return new UploadRespVO().pushErrorCode(ErrorCode.ERROR_1032);
		}
		String uri = fileWriteHandle.writeFile(req.getBusType(), req.getFileType(), req.getImg());
		UploadRespVO resp = new UploadRespVO();
		resp.setUri(uri);
		resp.setUrl(domain + uri);
		return resp;
	}

	@DocFlag("网宿云获取token接口")
	@CmdAction
	@LoginIgnore
	public UploadByWCRespVO uploadByWC(UploadByWCReqVO req) throws UnsupportedEncodingException {

		if (!ALLOW_SUFFIX.contains(req.getFileType().toLowerCase())) {
			return new UploadByWCRespVO().pushErrorCode(ErrorCode.ERROR_1023);
		}
		if (!BUS_TYPE.contains(req.getBusType())) {
			return new UploadByWCRespVO().pushErrorCode(ErrorCode.ERROR_1032);
		}

		String fileName = fileWriteHandle.getFileName(req.getBusType(), req.getFileType());
		Map<String, String> map = new HashMap<String, String>();
		map.put("scope", "music-wowo");
		map.put("deadline", String.valueOf(System.currentTimeMillis() + 1000 * 60 * 30));
		map.put("returnBody", "fname=$(fname)&url=$(url)");
		map.put("saveKey", fileName);
		String putPolicy = Base64.getEncoder().encodeToString(JSON.toJSONString(map).getBytes("UTF-8"));
		String sign = Base64.getEncoder()
				.encodeToString(HMacSHAUtil.HMACSHA1(putPolicy, OssConfig.WC_SECRETKEY).getBytes("UTF-8"));
		String uploadToken = MessageFormat.format("{0}:{1}:{2}", OssConfig.WC_ACCESSKEY, sign, putPolicy);
		UploadByWCRespVO resp = new UploadByWCRespVO();
		resp.setToken(uploadToken);
		resp.setUrl(wsCdnDomain + File.separator + fileName);
		return resp;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		/**
		 * 构建策略数据
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("scope", "music-wowo");
		map.put("deadline", String.valueOf(System.currentTimeMillis() + 1000 * 60 * 30));
		map.put("returnBody", "fname=$(fname)&url=$(url)");
		map.put("saveKey", "test/image/" + JUUIDUtil.createUuid() + ".jpg");

		String putPolicy = JSON.toJSONString(map);

		putPolicy = Base64.getEncoder().encodeToString(putPolicy.getBytes("UTF-8"));

		String sign = HMacSHAUtil.HMACSHA1(putPolicy, OssConfig.WC_SECRETKEY);

		sign = Base64.getEncoder().encodeToString(sign.getBytes("UTF-8"));

		String uploadToken = MessageFormat.format("{0}:{1}:{2}", OssConfig.WC_ACCESSKEY, sign, putPolicy);

		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("token", uploadToken);
		params.put("file", FileUtils.readFileByte("d://apppayqrcode.png"));
		String result = uploadFile("http://jiujunxinxi.up20.v1.wcsapi.com/file/upload", params);

		System.out.println(result);
		System.out.println(new String(Base64.getDecoder().decode(result.getBytes("iso-8859-1")), "iso-8859-1"));
	}

	/**
	 * 多文件上传的方法
	 * 
	 * @param actionUrl：上传的路径
	 * @param uploadFilePaths：需要上传的文件路径，数组
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String uploadFile(String actionUrl, Map<String, Object> params) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = JUUIDUtil.createUuid();

		DataOutputStream ds = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {
			// 统一资源
			URL url = new URL(actionUrl);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

			// 设置是否从httpUrlConnection读入，默认情况下是true;
			httpURLConnection.setDoInput(true);
			// 设置是否向httpUrlConnection输出
			httpURLConnection.setDoOutput(true);
			// Post 请求不能使用缓存
			httpURLConnection.setUseCaches(false);
			// 设定请求的方法，默认是GET
			httpURLConnection.setRequestMethod("POST");
			// 设置字符编码连接参数
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			// 设置字符编码
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// 设置请求内容类型
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			StringBuilder context = new StringBuilder();
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (StringUtil.isNullOrEmpty(value)) {
					continue;
				}
				if (value instanceof String) {
					context.append(twoHyphens).append(boundary).append(end);
					context.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(end)
							.append(end);
					context.append(value).append(end);
				}

				if (value instanceof byte[]) {
					context.append(twoHyphens).append(boundary).append(end);
					context.append("Content-Disposition: form-data; ").append("name=\"").append(key)
							.append("\";filename=\"").append("temp.jpg").append("\"").append(end);
					context.append("Content-Type: application/octet-stream").append(end).append(end);
					context.append(new String(((byte[]) value), "ISO-8859-1")).append(end);
				}
			}
			context.append(twoHyphens).append(boundary).append(twoHyphens).append(end);
			System.out.println(context.toString());

			byte[] data = context.toString().getBytes("ISO-8859-1");
			httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data));
			ds = new DataOutputStream(httpURLConnection.getOutputStream());
			ds.write(data, 0, data.length);
			ds.flush();
			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception(
						"HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode() + "\r\n"
								+ FileUtils.inputStream2String(httpURLConnection.getErrorStream(), "utf-8"));
			}
			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = httpURLConnection.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);
				tempLine = null;
				resultBuffer = new StringBuffer();
				while ((tempLine = reader.readLine()) != null) {
					resultBuffer.append(tempLine);
					resultBuffer.append("\n");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ds != null) {
				try {
					ds.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return resultBuffer.toString();
		}
	}

}
