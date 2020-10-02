package cn.qixqi.login.dao;

import java.util.Map;
import cn.qixqi.login.entity.QixqiUser;

public interface UserDao {
	public int add(QixqiUser user);
	public int delete(int uid);
	public int update(int uid, Map<String, Object> map);				
	public int updatePass(int uid, String oldPass, String newPass);		// 更新密码（需要原密码）
	public int resetPass(String email, String password);				// 重设密码（需要验证码）
	public QixqiUser get(String key, String password);
	public QixqiUser get(int uid);
	public String getAvatar(int uid);
	public QixqiUser getSimpleUser(int uid);
	public boolean isExist(String email);
}
