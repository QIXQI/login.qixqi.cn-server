package cn.qixqi.login.dao;

import java.util.Map;
import cn.qixqi.login.entity.QixqiUser;

public interface UserDao {
	public int add(QixqiUser user);
	public int delete(int uid);
	public int update(int uid, Map<String, Object> map);
	public int resetPass(int uid, String oldPass, String newPass);
	public QixqiUser get(String key, String password);
	public QixqiUser get(int uid);
	public String getAvatar(int uid);
	public QixqiUser getSimpleUser(int uid);
}
