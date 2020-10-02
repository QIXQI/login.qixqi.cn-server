package cn.qixqi.login.dao;

import cn.qixqi.login.entity.ResetCode;

public interface ResetCodeDao {
	public int add(ResetCode code);
	public int clear();
	public ResetCode get(String email);	// 最新的验证码
}
