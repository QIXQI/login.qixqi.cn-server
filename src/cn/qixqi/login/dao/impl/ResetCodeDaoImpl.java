package cn.qixqi.login.dao.impl;

import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import cn.qixqi.login.dao.BaseDao;
import cn.qixqi.login.dao.ResetCodeDao;
import cn.qixqi.login.entity.ResetCode;

public class ResetCodeDaoImpl extends BaseDao implements ResetCodeDao {
	private Logger logger = LogManager.getLogger(ResetCodeDaoImpl.class.getName());
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public int add(ResetCode code) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "insert into user_reset_code (email, code) values (?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, code.getEmail());
			pst.setString(2, code.getCode());
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				status = 200;
				this.logger.error("数据库插入验证码信息失败, email=" + code.getEmail());
			}
		} catch(SQLException se) {
			status = 200;
			this.logger.error("数据库插入验证码信息异常，email=" + code.getEmail() + "：" + se.getMessage());
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int clear() {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "delete from user_reset_code where send_time < ?";
		try {
			String thisDate = sdf.format(new Date());
			pst = conn.prepareStatement(sql);
			pst.setString(1, thisDate);
			int rowCount = pst.executeUpdate();
			this.logger.info(thisDate + " -- 清理重设密码验证码" + rowCount + "条");
		} catch(SQLException se) {
			status = 200;
			this.logger.error("清理重设密码验证码异常：" + se.getMessage());
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public ResetCode get(String email) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResetCode resetCode = null;
		String sql = "select uc1.code, uc1.send_time "
				+ "from user_reset_code uc1 "
				+ "where uc1.email = ? and uc1.send_time = (select max(uc2.send_time) from user_reset_code uc2 where uc2.email = ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, email);
			pst.setString(2, email);
			rs = pst.executeQuery();
			if (rs.next()) {
				String code = rs.getString(1);
				Date sendTime = rs.getTimestamp(2);
				resetCode = new ResetCode(email, code, sendTime);
			} else {
				this.logger.info("邮箱email=" + email + "，没有查取到最新验证码");
			}
		} catch(SQLException se) {
			resetCode = null;
			this.logger.error("邮箱email=" + email + "差取最新验证码异常：" + se.getMessage());
		}
		closeAll(conn, pst, rs);
		return resetCode;
	}

}
