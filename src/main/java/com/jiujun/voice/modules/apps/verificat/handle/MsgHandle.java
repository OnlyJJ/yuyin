package com.jiujun.voice.modules.apps.verificat.handle;

import java.net.URLEncoder;
import java.security.Security;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.stereotype.Component;

import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.http.HttpUtil;
import com.jiujun.voice.common.utils.http.entity.HttpEntity;

/**
 * 
 * @author Coody
 *
 */
@Component
public class MsgHandle {

	private static final String USERID = "5722";

	private static final String PWD = "75e6ee6eb84b1c167fe51915fc501fa0";

	public Boolean sendMobileCode(String mobile, String context) {
		Long time1 = System.currentTimeMillis();
		Integer msgId = new Long(System.currentTimeMillis()).intValue();
		String url = "http://kltx.sms10000.com.cn/sdk/SMS";
		try {
			// 构建请求报文
			String data = MessageFormat.format("cmd=send&uid={0}&psw={1}&mobiles={2}&msgid={3}&msg={4}", USERID, PWD,
					mobile, msgId.toString(), URLEncoder.encode(context, "GBK"));
			Map<String, String> headMap=new HashMap<String, String>();
			headMap.put("Content-Type", "application/x-www-form-urlencoded");
			// 发起请求
			HttpEntity entity = HttpUtil.post(url, data, "GBK",headMap);
			if (entity.getCode() != 200) {
				LogUtil.logger.error("发送给 " + mobile + " 发短信失败！httpCode=" + entity.getCode() + " "
						+ (System.currentTimeMillis() - time1));
				return false;
			}
			if ("100".equals(entity.getHtml())) {
				LogUtil.logger.info("发送给 " + mobile + " 短信成功！result=" + entity.getHtml() + " "
						+ (System.currentTimeMillis() - time1));
				return true;
			}
			LogUtil.logger.error(
					"发送给 " + mobile + " 发短信失败！result=" + entity.getHtml() + " " + (System.currentTimeMillis() - time1));
			return false;
		} catch (Exception e) {
			LogUtil.logger.error("发送给 " + mobile + " 发短信失败！result=" + null + " " + (System.currentTimeMillis() - time1)
					+ ">>" + PrintException.getErrorStack(e));
			return false;
		}
	}

	static String hostName = "smtp.exmail.qq.com";
	static String smtpPort = "25";
	static String smtpPort_ssl = "465";
	static String user = "noreply@9shows.com";
	static String password = "icbKpofxpMTkeDQu";

	/**
	 * 发送普通邮件
	 * 
	 * @param subject
	 * @param emailRecievers
	 * @param hideRecievers
	 * @param emailContent
	 * @param showName
	 * @return
	 */
	
	public boolean sendEmailCode(String to, String subject, String emailContent) {
		boolean send = false;
		try {
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			String sslFactory = "javax.net.ssl.SSLSocketFactory";
			Properties props = System.getProperties();
			props.setProperty("mail.smtp.host", hostName);
			props.setProperty("mail.smtp.socketFactory.class", sslFactory);
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.port", smtpPort_ssl);
			props.setProperty("mail.smtp.socketFactory.port", smtpPort_ssl);
			props.put("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.connectiontimeout", "60000");
			props.setProperty("mail.smtp.timeout", "60000");

			Session session = Session.getDefaultInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(user, "ET语音"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); 
			msg.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
			msg.setContent(emailContent, "text/html;charset=" + "UTF-8");
			msg.setSentDate(new Date());
			Transport.send(msg);
			LogUtil.logger.info("SEND EMAIL SUCCESS!" + " Send To:" + to + " Email Subject：" + subject
					+ " Email Content:" + emailContent);
			send = true;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.logger.info("SEND EMAIL Fail!" + " Send To:" + to + " Email Subject：" + subject + " Email Content:"
					+ emailContent);
		}
		return send;
	}

	public static void main(String[] args) {
		String context = "【美女直播】验证码：" + 5224 + "，感谢您注册帐号，请在30分钟内完成注册。工作人员不会向您索取验证码，请勿泄露。【美女直播】";
		new MsgHandle().sendMobileCode("13226635321", context);

	}
}
