## qixqi.cn 门户登录
### 状态码
1. 成功：100

	* 101 登录用户重设密码成功
2. 失败：200

	* 201 更新密码时，原密码错误
	* 202 重设密码发送邮箱时，邮箱还未注册
	* 203 重设密码时，验证码还未发送
	* 204 重设密码时，验证码不匹配
	* 205 重设密码时，验证码过期
3. 权限不匹配：300

####
1. 100 注销成功
2. 200 还未登录


### Tips
1. 代理层：
	* 注册、登录、注销代理（本站用户与第三方用户共有）
	* 其他代理（本站用户与第三方用户分离）
2. 发送邮件需要jar包：
	mail.jar, sactivation.jar
	没有activation.jar包，编译正常，运行报错：java.lang.ClassNotFoundException
3. 清理重设密码验证码每天 00:10 清除
4. IDE 中注意java的编译版本，比如这次本地编写阶段 javase14，服务器java1.8，编译结果就不能在服务器运行
5. smtp.163.com 在本地25端口可用，但在腾讯云、阿里云服务器25端口关闭，并且也不安全，使用465端口SSL安全连接

	```java
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
	```
	

### Problems
1. 登录日志表中：获取用户最新登录的记录的查询语句效率问题
2. springframework 只接受get或post请求实现
3. 浏览器输入网址：localhost:8080/login/validate.do 过程，浏览器预处理，会使得访问validate.do两次，发送两封邮件
	* 影响不大，因为用户不会输入网址，靠链接跳转与后台交互
4. 获取登录用户的地址和ip