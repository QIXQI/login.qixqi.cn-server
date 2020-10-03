package cn.qixqi.login.config;

// zhengxiang4056@163.com
public class EmailConfigBackup {
	// 发件人邮箱
	public static final String ACCOUNT = "***";	// 邮箱账号
	// 发件人邮箱密码
	public static final String PASSWORD = "";	// 授权码（一般与密码不同）
	// 发件人昵称
	public static final String SEND_NICKNAME = "qixqi";
	// 收件人昵称
	public static final String RECEIVE_NICKNAME = "Dear User";
	// 邮件编码
	public static final String ENCODING = "UTF-8";
	// 邮件类型
	public static final String CONTENT_TYPE = "text/html; charset=utf-8";
	// 邮件主题
	public static final String SUBJECT = "密码重置 - qixqi.cn";
	
	
	// 发件人SMTP服务器地址
	public static final String HOST = "***";		// 比如 smtp.163.com, smtp.exmail.qq.com 
	
	// 发件人端口
	public static final String PORT = "***";		// 端口 25/ 465(SSL)
	
	// SSL
	public static final String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
	
	// 使用协议
	public static final String PROTOCOL = "smtp";
	
	// 请求认证
	public static final String AUTHENTICATION = "true";
}
