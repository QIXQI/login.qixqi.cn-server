package cn.qixqi.login.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import cn.qixqi.login.dao.BaseDao;

@Controller
public class Init {
	private Logger logger = LogManager.getLogger(Init.class.getName());
	private BaseDao bd = new BaseDao();
	
	@RequestMapping("init.do")
	public void init(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Connection conn = bd.getConnection();
		PreparedStatement pst = null;
		int status = 100;
		
		try {
			// user_status
			String sql = "create table if not exists `user_status`( "
					+ "	`status_id` int(11) primary key, "
					+ "	`status` varchar(255) not null unique "
					+ ")ENGINE=InnoDB default charset=utf8;";
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			
			// 插入数据
			sql = "insert into user_status (status_id, status) values (?, ?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, 0);
			pst.setString(2, "离线");
			pst.addBatch();
			pst.setInt(1, 1);
			pst.setString(2, "在线");
			pst.addBatch();
			pst.executeBatch();
			
			
			// user
			sql = "create table if not exists `user`( "
					+ "	`uid` int(11) auto_increment primary key, "
					+ "	`username` varchar(255) not null unique, "
					+ "	`email` varchar(255) not null unique, "
					+ "	`phone` varchar(255) not null unique, "
					+ "	`password` varchar(255) not null, "
					+ "	`sex` char(1) default 'u', "
					+ "	`birthday` date, "
					+ "	`register_time` timestamp default CURRENT_TIMESTAMP, "
					+ "	`status_id` int(11) default 0, "
					+ "	`avatar` varchar(255) default 'default.png', "
					+ "	foreign key(`status_id`) references user_status(`status_id`) "
					+ "	on delete set null on update cascade "
					+ ")ENGINE=InnoDB AUTO_INCREMENT=1 default charset=utf8;";
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			
			// user_login_log
			sql = "create table if not exists `user_login_log`( "
					+ "	`uid` int(11) not null, "
					+ "	`login_time` timestamp default CURRENT_TIMESTAMP, "
					+ "	`site` varchar(255), "
					+ "	`login_ip`	varchar(255), "
					+ "	foreign key(`uid`) references user(`uid`) "
					+ "	on delete cascade on update cascade "
					+ ")ENGINE=InnoDB default charset=utf8;";
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			
			// user_reset_code
			sql = "create table if not exists `user_reset_code`( "
					+ "	`email` varchar(255) not null, "
					+ "	`code` char(6) not null, "
					+ "	`send_time` timestamp default CURRENT_TIMESTAMP "
					+ ")ENGINE=InnoDB default charset=utf8;";
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
		
			this.logger.info("数据库初始化成功");
		} catch(SQLException se) {
			status = 200;
			this.logger.info("数据库初始化失败：" + se.getMessage());
		}
		bd.closeAll(conn, pst, null);
		
		// 返回JSON数据
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("status", status);
		out.println(message.toJSONString());
	}

}
