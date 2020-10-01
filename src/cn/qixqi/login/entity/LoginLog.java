package cn.qixqi.login.entity;

import java.util.Date;

public class LoginLog {
	private int uid;
	private Date loginTime;
	private String site;
	private String loginIp;
	
	/**
	 * 添加日志
	 * @param uid
	 * @param site
	 * @param loginIp
	 */
	public LoginLog(int uid, String site, String loginIp) {
		super();
		this.uid = uid;
		this.site = site;
		this.loginIp = loginIp;
	}

	/**
	 * 获取日志完整信息
	 * @param uid
	 * @param loginTime
	 * @param site
	 * @param loginIp
	 */
	public LoginLog(int uid, Date loginTime, String site, String loginIp) {
		super();
		this.uid = uid;
		this.loginTime = loginTime;
		this.site = site;
		this.loginIp = loginIp;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Override
	public String toString() {
		return "LoginLog [uid=" + uid + ", loginTime=" + loginTime + ", site=" + site + ", loginIp=" + loginIp + "]";
	}
}
