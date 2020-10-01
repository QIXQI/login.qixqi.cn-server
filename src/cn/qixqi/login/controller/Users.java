package cn.qixqi.login.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
public class Users {
	private Logger logger = LogManager.getLogger(Users.class.getName());
	
	@RequestMapping("register.do")
	public void register(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		out.println("hello");
	}
	
	@RequestMapping("login.do")
	public void login(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
	}
	
	@RequestMapping("logout.do")
	public void logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
	}
	
	@RequestMapping("validate.do")
	public void validate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
	}
	
	@RequestMapping("reset.do")
	public void reset(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
	}
}
