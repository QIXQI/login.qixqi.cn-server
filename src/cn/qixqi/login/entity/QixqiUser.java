package cn.qixqi.login.entity;

import java.util.Date;

public class QixqiUser {
	private int uid;
	private String username;
	private String email;
	private String phone;
	private String password;
	private char sex;
	private Date birthday;
	private Date registerTime;
	private String status;
	private String avatar;
	
	/**
	 * 注册
	 * @param username
	 * @param email
	 * @param phone
	 * @param password
	 */
	public QixqiUser(String username, String email, String phone, String password) {
		super();
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.password = password;
	}
	
	/**
	 * 获取用户简要信息（默认人获取）
	 * @param username
	 * @param sex
	 * @param registerTime
	 * @param status
	 * @param avatar
	 */
	public QixqiUser(String username, char sex, Date registerTime, String status, String avatar) {
		super();
		this.username = username;
		this.sex = sex;
		this.registerTime = registerTime;
		this.status = status;
		this.avatar = avatar;
	}



	/**
	 * 用户完整信息
	 * @param uid
	 * @param username
	 * @param email
	 * @param phone
	 * @param password
	 * @param sex
	 * @param birthday
	 * @param registerTime
	 * @param statusId
	 * @param avatar
	 */
	public QixqiUser(int uid, String username, String email, String phone, String password, char sex, Date birthday,
			Date registerTime, String status, String avatar) {
		super();
		this.uid = uid;
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.sex = sex;
		this.birthday = birthday;
		this.registerTime = registerTime;
		this.status = status;
		this.avatar = avatar;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "QixqiUser [uid=" + uid + ", username=" + username + ", email=" + email + ", phone=" + phone
				+ ", password=" + password + ", sex=" + sex + ", birthday=" + birthday + ", registerTime="
				+ registerTime + ", status=" + status + ", avatar=" + avatar + "]";
	}
}
