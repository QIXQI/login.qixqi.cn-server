package cn.qixqi.login.dao.impl;

import java.util.Map;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.login.dao.BaseDao;
import cn.qixqi.login.dao.UserDao;
import cn.qixqi.login.entity.QixqiUser;

public class UserDaoImpl extends BaseDao implements UserDao {
	private Logger logger = LogManager.getLogger(UserDaoImpl.class.getName());

	@Override
	public int add(QixqiUser user) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "insert into user(username, email, phone, password) values (?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, user.getEmail());
			pst.setString(3, user.getPhone());
			pst.setString(4, user.getPassword());
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				this.logger.error("用户注册失败：username=" + user.getUsername());
				status = 200;
			}
		} catch(SQLException se) {
			this.logger.error("用户注册异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int delete(int uid) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "delete from user where uid = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				this.logger.error("用户注销账号失败：uid=" + uid);
				status = 200;
			}
		} catch(SQLException se) {
			this.logger.error("用户注销账号异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int update(int uid, Map<String, Object> map) {
		// TODO Auto-generated method stub
		String table = "user";
		String whereSql = " where uid = " + uid;
		return executeUpdate(table, whereSql, map);
	}

	@Override
	public int resetPass(int uid, String oldPass, String newPass) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "update user set password = ? where uid = ? and password = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, newPass);
			pst.setInt(2, uid);
			pst.setString(3, oldPass);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				status = 201;
				this.logger.error("用户" + uid + " 更新密码失败：原密码错误");
			}
		} catch(SQLException se) {
			this.logger.error("用户" + uid + " 更新密码失败：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public QixqiUser get(String key, String password) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		QixqiUser user = null;
		String sql = "select uid, username, email, phone, sex, birthday, register_time, status, avatar  " + 
				"from user, user_status " + 
				"where (username = ? or email = ? or phone = ?) and password = ? and user.status_id = user_status.status_id";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, key);
			pst.setString(2, key);
			pst.setString(3, key);
			pst.setString(4, password);
			rs = pst.executeQuery();
			if (rs.next()) {
				int uid = rs.getInt(1);
				String username = rs.getString(2);
				String email = rs.getString(3);
				String phone = rs.getString(4);
				char sex = rs.getString(5).charAt(0);
				Date birthday = rs.getDate(6);
				Date registerTime = rs.getTimestamp(7);
				String status = rs.getString(8);
				String avatar = rs.getString(9);
				user = new QixqiUser(uid, username, email, phone, password, sex, birthday, registerTime, status, avatar);
				this.logger.info("key:" + key + " 登录成功");
			} else {
				this.logger.info("key:" + key + " 登录失败");
			}
		} catch(SQLException se) {
			this.logger.error("key:" + key + " 登录失败：" + se.getMessage());
			user = null;
		}
		closeAll(conn, pst, rs);
		return user;
	}

	@Override
	public QixqiUser get(int uid) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		QixqiUser user = null;
		String sql = "select username, email, phone, password, sex, birthday, register_time, status, avatar " +
				"from user, user_status " +
				"where uid = ? and user.status_id = user_status.status_id";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			rs = pst.executeQuery();
			if (rs.next()) {
				String username = rs.getString(1);
				String email = rs.getString(2);
				String phone = rs.getString(3);
				String password = rs.getString(4);
				char sex = rs.getString(5).charAt(0);
				Date birthday = rs.getDate(6);
				Date registerTime = rs.getTimestamp(7);
				String status = rs.getString(8);
				String avatar = rs.getString(9);
				user = new QixqiUser(uid, username, email, phone, password, sex, birthday, registerTime, status, avatar);
				this.logger.info("uid:" + uid + " 查找成功");
			} else {
				this.logger.info("uid:" + uid + " 查找失败");
			}
		} catch(SQLException se) {
			user = null;
			this.logger.error("uid:" + uid + " 查找失败：" + se.getMessage());
		}
		closeAll(conn, pst, rs);
		return user;
	}

	@Override
	public String getAvatar(int uid) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String avatar = null;
		String sql = "select avatar from user where uid = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			rs = pst.executeQuery();
			if (rs.next()) {
				avatar = rs.getString(1);
				this.logger.info("用户" + uid + " 的头像链接获取成功");
			} else {
				this.logger.info("用户" + uid + " 的头像链接获取失败");
			}
		} catch(SQLException se) {
			this.logger.error("用户" + uid + " 的头像链接获取失败：" + se.getMessage());
			avatar = null;
		}
		closeAll(conn, pst, rs);
		return avatar;
	}

	@Override
	public QixqiUser getSimpleUser(int uid) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		QixqiUser user = null;
		String sql = "select username, sex, register_time, status, avatar " + 
				"from user, user_status " +
				"where uid = ? and user.status_id = user_status.status_id";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			rs = pst.executeQuery();
			if (rs.next()) {
				String username = rs.getString(1);
				char sex = rs.getString(2).charAt(0);
				Date registerTime = rs.getTimestamp(3);
				String status = rs.getString(4);
				String avatar = rs.getString(5);
				user = new QixqiUser(username, sex, registerTime, status, avatar);
				this.logger.info("获取用户uid=" + uid + " 简要信息成功");
			} else {
				this.logger.info("获取用户uid=" + uid + " 简要信息失败");
			}
		} catch(SQLException se) {
			user = null;
			this.logger.error("获取用户uid=" + uid + " 简要信息失败：" + se.getMessage());
		}
		closeAll(conn, pst, rs);
		return user;
	}

}
