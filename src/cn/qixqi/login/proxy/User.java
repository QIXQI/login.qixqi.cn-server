package cn.qixqi.login.proxy;

import java.util.List;
import java.util.Map;
import cn.qixqi.login.entity.QixqiUser;
import cn.qixqi.login.entity.LoginLog;

public interface User {
	// ******************************
	// 用户
	// 用户注册
	public int userRegister(QixqiUser user);
	
	// 用户登录
	public QixqiUser userLogin(String key, String password);
	
	// 用户注销
	public int userLogout(int uid);
	
	// 获取用户信息
	public QixqiUser getUserInfo(int uid);
	
	// 更新用户信息
	public int updateUserInfo(int uid, Map<String, Object> map);
	
	// 更新密码
	public int updatePass(int uid, String oldPass, String newPass);
	
	// 重设密码
	public int resetPass(String code, String email, String password);
	
	// 用户用户头像
	public String getAvatar(int uid);
	
	// 更新用户头像
	public int updateAvatar(int uid, String avatar);
	
	// 获取用户简要信息
	public QixqiUser getSimpleUser(int uid);
	
	// *********************************
	// 登录日志
	// 获取用户最新登录日志
	public LoginLog getLastLogin(int uid);
	
	// 获取用户登录日志列表
	public List<LoginLog> getLogins(int uid);

}
