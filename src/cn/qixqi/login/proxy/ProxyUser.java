package cn.qixqi.login.proxy;

import java.util.List;
import java.util.Map;

import cn.qixqi.login.entity.LoginLog;
import cn.qixqi.login.entity.QixqiUser;

public class ProxyUser implements User {
	private int priority;		// 权限
	private RealUser realUser = new RealUser();
	
	/**
	 * 构造函数
	 * @param priority
	 */
	public ProxyUser(int priority) {
		this.priority = priority;
	}

	@Override
	public int userRegister(QixqiUser user) {
		// TODO Auto-generated method stub
		if (priority <= Priorities.THIRD_PARTY_USER) {
			return realUser.userRegister(user);
		}
		return 300;
	}

	@Override
	public QixqiUser userLogin(String key, String password) {
		// TODO Auto-generated method stub
		if (priority <= Priorities.VISITOR) {
			return realUser.userLogin(key, password);
		}
		return null;
	}

	@Override
	public int userLogout(int uid) {
		// TODO Auto-generated method stub
		if (priority >= Priorities.THIRD_PARTY_USER) {
			return realUser.userLogout(uid);
		}
		return 300;
	}

	@Override
	public QixqiUser getUserInfo(int uid) {
		// TODO Auto-generated method stub
		if (priority >= Priorities.USER) {
			return realUser.getUserInfo(uid);
		}
		return null;
	}

	@Override
	public int updateUserInfo(int uid, Map<String, Object> map) {
		// TODO Auto-generated method stub
		if (priority >= Priorities.USER) {
			return realUser.updateUserInfo(uid, map);
		}
		return 300;
	}

	@Override
	public int updatePass(int uid, String oldPass, String newPass) {
		// TODO Auto-generated method stub
		if (priority >= Priorities.USER) {
			return realUser.updatePass(uid, oldPass, newPass);
		}
		return 300;
	}

	@Override
	public String getAvatar(int uid) {
		// TODO Auto-generated method stub
		if (priority >= Priorities.VISITOR) {
			return realUser.getAvatar(uid);
		}
		return null;
	}

	@Override
	public int updateAvatar(int uid, String avatar) {
		// TODO Auto-generated method stub
		if (priority >= Priorities.USER) {
			return realUser.updateAvatar(uid, avatar);
		}
		return 300;
	}

	@Override
	public QixqiUser getSimpleUser(int uid) {
		// TODO Auto-generated method stub
		if (priority >= Priorities.VISITOR) {
			return realUser.getSimpleUser(uid);
		}
		return null;
	}

	@Override
	public LoginLog getLastLogin(int uid) {
		// TODO Auto-generated method stub
		if (priority >= Priorities.VISITOR) {
			return realUser.getLastLogin(uid);
		}
		return null;
	}

	@Override
	public List<LoginLog> getLogins(int uid) {
		// TODO Auto-generated method stub
		if (priority >= Priorities.USER) {
			return realUser.getLogins(uid);
		}
		return null;
	}

}
