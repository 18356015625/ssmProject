package com.liyan.entity;

import java.util.Date;

/**
 * 修改用户信息传输实体类
 * @author dell
 *
 */
public class UserForm {
	
	private String guid;
	
	private String username;
	
	private String password;
	
	private String name;
	
	private Date createTime;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
