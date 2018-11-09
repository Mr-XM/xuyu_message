package com.xuyu.message;


/**
 * 老师类，存取老师的信息
 * @author zhagyinghao
 */
public class Teacher {
	private String UserId;
	private String name;
	private String mobile;

	public Teacher(String U_id, String n, String m) {
		UserId = U_id;
		name = n;
		mobile = m;
	}

	public void setUserId(String U_id) {
		UserId = U_id;
	}

	public void setName(String n) {
		name = n;
	}

	public void setMobile(String m) {
		mobile = m;
	}

	public String getName() {
		return name;
	}

	public String getUserId() {
		return UserId;
	}

	public String getMobile() {
		return mobile;
	}
}

