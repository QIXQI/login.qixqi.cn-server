package cn.qixqi.login.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;

import cn.qixqi.login.entity.QixqiUser;
import cn.qixqi.login.proxy.User;
import cn.qixqi.login.proxy.ProxyUser;
import cn.qixqi.login.proxy.Priorities;
import cn.qixqi.login.util.EmailUtil;

@Controller
public class Users {
	private Logger logger = LogManager.getLogger(Users.class.getName());
	
	@RequestMapping("register.do")
	public void register(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// 接收请求数据
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		QixqiUser user = new QixqiUser(username, email, phone, password);
		User proxy = new ProxyUser(Priorities.VISITOR);		// 游客获取第三方用户
		int status = proxy.userRegister(user);
		
		// 返回JSON数据
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("status", status);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("login.do")
	public void login(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException{
		if (session.getAttribute("user") != null) {
			session.removeAttribute("user");
			this.logger.info("切换账号，清空session");
		}
		// 接收数据
		String key = request.getParameter("loginField");
		String password = request.getParameter("password");
		User proxy = new ProxyUser(Priorities.VISITOR);
		QixqiUser user = proxy.userLogin(key, password);
		int status = 100;
		if (user == null) {
			status = 200;
		} else {
			session.setAttribute("user", user);
		}
		
		// 返回JSON数据
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("status", status);
		message.put("user", user);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("logout.do")
	public void logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException{
		int status = 100;
		if (session.getAttribute("user") != null) {
			QixqiUser user = (QixqiUser) session.getAttribute("user");
			session.removeAttribute("user");
			User proxy = new ProxyUser(Priorities.THIRD_PARTY_USER);
			status = proxy.userLogout(user.getUid());
		} else {
			status = 200;
		}
		
		// 返回JSON数据
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("status", status);
		out.println(message.toJSONString());
	}
	
	/**
	 * 根据邮箱发送邮件
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("validate.do")
	public void validate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// 接收请求数据
		String email = request.getParameter("email");
		// 发送邮件
		int status = EmailUtil.send(email);
		
		// 返回JSON数据
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("status", status);
		out.println(message.toJSONString());
	}
	
	/**
	 * 根据验证码重设密码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("reset.do")
	public void reset(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException{
		// 接收请求数据
		String code = request.getParameter("code");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		// 重设密码
		User proxy = new ProxyUser(Priorities.VISITOR);
		int status = proxy.resetPass(code, email, password);
		// 判断是否登录状态
		if (status == 100 && session.getAttribute("user") != null) {
			status = 101;	// 不需要跳转到登录页面
		}
		
		// 返回JSON数据
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("status", status);
		out.println(message.toJSONString());
	}
}
