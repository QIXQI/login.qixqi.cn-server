package cn.qixqi.login.proxy;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.login.entity.LoginLog;
import cn.qixqi.login.entity.QixqiUser;
import cn.qixqi.login.dao.UserDao;
import cn.qixqi.login.dao.LoginLogDao;
import cn.qixqi.login.dao.impl.UserDaoImpl;
import cn.qixqi.login.dao.impl.LoginLogDaoImpl;

public class RealUser implements User {
	private Logger logger = LogManager.getLogger(RealUser.class.getName());
	private UserDao ud = new UserDaoImpl();
	private LoginLogDao ld = new LoginLogDaoImpl();

	@Override
	public int userRegister(QixqiUser user) {
		// TODO Auto-generated method stub
		return ud.add(user);
	}

	@Override
	public QixqiUser userLogin(String key, String password) {
		// TODO Auto-generated method stub
		// 获取用户
		QixqiUser user = ud.get(key, password);
		if (user != null) {
			// 更新用户为在线状态
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status_id", Status.ONLINE);
			int status = ud.update(user.getUid(), map);
			if (status == 100) {
				this.logger.info("用户uid=" + user.getUid() + " 更新为在线状态成功");
				
				// 重新获取用户
				user = ud.get(key, password);
				
				// 添加登录日志
				LoginLog log = new LoginLog(user.getUid(), "地点", "ip");
				ld.add(log);
				
				return user;
			} else {
				this.logger.error("用户uid=" + user.getUid() + " 更新为在线状态失败");
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public int userLogout(int uid) {
		// TODO Auto-generated method stub
		// 更新用户为离线状态
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status_id", Status.OFFLINE);
		int status = ud.update(uid, map);
		if (status == 100) {
			this.logger.info("用户uid=" + uid + " 更新为离线状态成功");
		} else {
			this.logger.error("用户uid=" + uid + " 更新为离线状态失败");
		}
		return status;
	}

	@Override
	public QixqiUser getUserInfo(int uid) {
		// TODO Auto-generated method stub
		return ud.get(uid);
	}

	@Override
	public int updateUserInfo(int uid, Map<String, Object> map) {
		// TODO Auto-generated method stub
		int status = ud.update(uid, map);
		if (status == 100) {
			this.logger.info("用户uid=" + uid + " 更新个人信息成功");
		} else {
			this.logger.error("用户uid=" + uid + " 更新个人信息失败");
		}
		return status;
	}

	@Override
	public int updatePass(int uid, String oldPass, String newPass) {
		// TODO Auto-generated method stub
		return ud.resetPass(uid, oldPass, newPass);
	}

	@Override
	public String getAvatar(int uid) {
		// TODO Auto-generated method stub
		return ud.getAvatar(uid);
	}

	@Override
	public int updateAvatar(int uid, String avatar) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("avatar", avatar);
		int status = ud.update(uid, map);
		if (status == 100) {
			this.logger.info("用户uid=" + uid + " 更新头像成功");
		} else {
			this.logger.error("用户uid=" + uid + " 更新头像失败");
		}
		return status;
	}

	@Override
	public QixqiUser getSimpleUser(int uid) {
		// TODO Auto-generated method stub
		return ud.getSimpleUser(uid);
	}

	@Override
	public LoginLog getLastLogin(int uid) {
		// TODO Auto-generated method stub
		return ld.get(uid);
	}

	@Override
	public List<LoginLog> getLogins(int uid) {
		// TODO Auto-generated method stub
		return ld.gets(uid);
	}

}
