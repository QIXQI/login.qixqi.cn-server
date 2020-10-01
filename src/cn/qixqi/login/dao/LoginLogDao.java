package cn.qixqi.login.dao;

import java.util.List;
import cn.qixqi.login.entity.LoginLog;

public interface LoginLogDao {
	public int add(LoginLog loginLog);
	public LoginLog get(int uid);		// 用户最近一次登录日志
	public List<LoginLog> gets(int uid);	// 用户登录日志列表
}
