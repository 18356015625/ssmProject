package com.liyan.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User implements Serializable{
	
	private static final long serialVersionUID = -474115354737001414L;
	
	private String guid;
	private String username;
	private String name;
	private String password;
	private String delFlag;
	private Date createTime;
	private Date updateTime;
	private byte[] photo;
	private String photostr;
	
	private List<Project> projects;
	
	private List<ProjectLink> link;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public List<ProjectLink> getLink() {
		return link;
	}
	public void setLink(List<ProjectLink> link) {
		this.link = link;
	}
	public String getPhotostr() {
		return photostr;
	}
	public void setPhotostr(String photostr) {
		this.photostr = photostr;
	}
	
	
	
}
