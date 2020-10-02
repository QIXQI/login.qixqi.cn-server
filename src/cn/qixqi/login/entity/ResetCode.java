package cn.qixqi.login.entity;

import java.util.Date;

public class ResetCode {
	private String email;
	private String code;
	private Date sendTime;
	
	/**
	 * 添加
	 * @param email
	 * @param code
	 */
	public ResetCode(String email, String code) {
		super();
		this.email = email;
		this.code = code;
	}

	/**
	 * 查询
	 * @param email
	 * @param code
	 * @param sendTime
	 */
	public ResetCode(String email, String code, Date sendTime) {
		super();
		this.email = email;
		this.code = code;
		this.sendTime = sendTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "ResetCode [email=" + email + ", code=" + code + ", sendTime=" + sendTime + "]";
	}
}
