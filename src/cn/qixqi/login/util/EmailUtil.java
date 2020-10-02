package cn.qixqi.login.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;		// 千万不要删除，否则邮件模块 ClassNotFoundException

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import cn.qixqi.login.config.EmailConfig;
import cn.qixqi.login.entity.ResetCode;
import cn.qixqi.login.dao.ResetCodeDao;
import cn.qixqi.login.dao.impl.ResetCodeDaoImpl;
import cn.qixqi.login.dao.UserDao;
import cn.qixqi.login.dao.impl.UserDaoImpl;

public class EmailUtil {
	private static Logger logger = LogManager.getLogger(EmailUtil.class.getName());
	
	public static int send(String email) {
		// 判断邮箱是否为空
		if (email == null) {
			logger.error("邮箱为空，发送重设密码邮件失败");
			return 200;
		}
		
		// 判断邮箱是否已注册
		UserDao ud = new UserDaoImpl();
		if (!ud.isExist(email)) {
			// 邮箱尚未注册
			logger.error("邮箱email=" + email + "尚未注册");
			return 202;
		}
		
		// 创建参数配置
		Properties props = new Properties();
		// 使用协议
		props.setProperty("mail.transport.protocol", EmailConfig.PROTOCOL);
		// SMTP 服务器地址
		props.setProperty("mail.smtp.host", EmailConfig.HOST);
		// 服务器端口
		props.setProperty("mail.smtp.port", EmailConfig.PORT);
		// SSL
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		// 请求认证
		props.setProperty("mail.smtp.auth", EmailConfig.AUTHENTICATION);
		
		// 定义邮件程序所需的环境信息
		Session session = Session.getInstance(props);
		// debug 模式，可以发送详细的日志
		// session.setDebug(true);
		
		// 生成验证码
		String code = generateCode();
		
		// 邮件传输对象
		Transport transport = null;
		int status = 100;
		try {
			// 创建一封邮件
			MimeMessage message = new MimeMessage(session);
			// 设置发件人
			message.setFrom(new InternetAddress(EmailConfig.ACCOUNT, EmailConfig.SEND_NICKNAME, EmailConfig.ENCODING));
			// 设置收件人
			message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email, EmailConfig.RECEIVE_NICKNAME, 
					EmailConfig.ENCODING));
			// 设置主题
			message.setSubject(EmailConfig.SUBJECT, EmailConfig.ENCODING);
			// 设置内容
			message.setContent("<!DOCTYPE html>"
					+ "<html lang='zh'>"
					+ "<head>"
					+ "<title>密码重设 - qixqi.cn</title>"
					+ "<style>"
					+ "		.words{"
					+ "			letter-spacing: 1px;"
					+ "		}"
					+ "</style>"
					+ "</head>"
					+ "<body>"
					+ "		<h3 align='center'>密码重设 - qixqi.cn</h3>"
					+ "		<div style='line-height: 30px; letter-spacing: 2px;'>"
					+ "			<p>"
					+ "				<label class='words'>Hey, Dear User!</label><br />"
					+ " 			忘记<label class='words'>QixQi</label>的密码了吗？别着急，以下的验证码可以帮助您找回密码：<br />"
					+ "  			验证码：<label class='words'>" + code + "</label> （<label class='words'>10</label>分钟内有效）<br /><br />"
					+ "				如果这不是您的邮件请忽略，很抱歉打扰您，请原谅～ <br /><br /><br />"
					+ "				<a href='https://qixqi.cn'><label class='words'>QixQi</label></a><br />"
					+ "				首席攻城狮[<label class='words'>doge</label>]<br />"
					+ "			</p>"	
					+ "		</div>"
					+ "</body>"
					+ "</html>", 
					EmailConfig.CONTENT_TYPE);
			// 设置发送时间
			message.setSentDate(new Date());
			// 保存更改
			message.saveChanges();
			
			// 根据 Session 获取邮件传输对象
			transport = session.getTransport();
			
			// 连接邮件服务器
			transport.connect(EmailConfig.ACCOUNT, EmailConfig.PASSWORD);
			// 发送邮件
			transport.sendMessage(message, message.getAllRecipients());
			
			// 将验证码信息插入数据库
			ResetCodeDao rd = new ResetCodeDaoImpl();
			status = rd.add(new ResetCode(email, code));
			
		} catch(UnsupportedEncodingException ue) {
			status = 200;
			logger.error("发件人、收件人邮箱地址编码异常：" + ue.getMessage());
		} catch(MessagingException mex) {
			status = 200;
			logger.error("向邮箱:" + email + "发送邮件失败：" + mex.getMessage());
		} finally {
			// 关闭连接
			try {
				if (transport != null) {
					transport.close();
				}
			} catch(MessagingException mex2) {
				status = 200;
				logger.error("邮件传输对象关闭连接异常：" + mex2.getMessage());
			}
		}
		return status;
	}
	
	/**
	 * 生成6位随机数充当验证码
	 * @return
	 */
	private static String generateCode() {
		StringBuilder codeBuilder = new StringBuilder();
		for (int i=0; i<6; i++) {
			int temp = (int)(Math.random() * 10);		// [0, 10)
			codeBuilder.append(temp);
		}
		return codeBuilder.toString();
	}
}
