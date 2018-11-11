package com.xuyu.message;


/**
 * 老师类，存取老师的信息
 * @author zhagyinghao
 */
public class Teacher {
	private String userId;
	private String name;
	private String mobile;
	private String itemNo;

	public Teacher(String userId, String name, String mobile) {
		this.userId = userId;
		this.name = name;
		this.mobile = mobile;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public String getUserId() {
		return userId;
	}

	public String getMobile() {
		return mobile;
	}

	public String getItemNo() {
		return itemNo;
	}
}

