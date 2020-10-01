package cn.qixqi.login.dao.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.login.dao.BaseDao;
import cn.qixqi.login.dao.LoginLogDao;
import cn.qixqi.login.entity.LoginLog;

public class LoginLogDaoImpl extends BaseDao implements LoginLogDao {
	private Logger logger = LogManager.getLogger(LoginLogDaoImpl.class.getName());

	@Override
	public int add(LoginLog loginLog) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "insert into user_login_log (uid, site, login_ip) values (?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, loginLog.getUid());
			pst.setString(2, loginLog.getSite());
			pst.setString(3, loginLog.getLoginIp());
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				status = 200;
				this.logger.error("用户登录日志添加失败：uid=" + loginLog.getUid());
			}
		} catch(SQLException se) {
			status = 200;
			this.logger.error("用户uid=" + loginLog.getUid() + "登录日志添加异常：" + se.getMessage());
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public LoginLog get(int uid) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		LoginLog log = null;
		String sql = "select log1.login_time, log1.site, log1.login_ip "
				+ "from user_login_log as log1 "
				+ "where log1.uid = ? and log1.login_time = (select max(log2.login_time) from user_login_log as log2 where log2.uid = ?) ";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			pst.setInt(2, uid);
			rs = pst.executeQuery();
			if (rs.next()) {
				Date loginTime = rs.getTimestamp(1);
				String site = rs.getString(2);
				String loginIp = rs.getString(3);
				log = new LoginLog(uid, loginTime, site, loginIp);
				this.logger.info("获取用户uid=" + uid + " 最新登录日志成功");
			} else {
				this.logger.info("获取用户uid=" + uid + " 最新登录日志失败");
			}
		} catch(SQLException se) {
			log = null;
			this.logger.error("获取用户uid=" + uid + " 最新登录日志异常：" + se.getMessage());
		}
		closeAll(conn, pst, rs);
		return log;
	}

	@Override
	public List<LoginLog> gets(int uid) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<LoginLog> logList = new ArrayList<LoginLog>();
		String sql = "select login_time, site, login_ip from user_login_log where uid = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			rs = pst.executeQuery();
			while (rs.next()) {
				Date loginTime = rs.getTimestamp(1);
				String site = rs.getString(2);
				String loginIp = rs.getString(3);
				logList.add(new LoginLog(uid, loginTime, site, loginIp));
			}
		} catch (SQLException se) {
			logList = null;
			this.logger.error("获取用户uid=" + uid + "登录日志异常：" + se.getMessage());
		}
		closeAll(conn, pst, rs);
		return logList;
	}

}
