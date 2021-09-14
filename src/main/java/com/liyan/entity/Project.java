package com.liyan.entity;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3997945887472520716L;
	private String id;
	private String projectName;
	private String teacher;
	private String userId;
	
	//保存课程下面的附件
	private List<ProjectLink> link;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<ProjectLink> getLink() {
		return link;
	}
	public void setLink(List<ProjectLink> link) {
		this.link = link;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	@Override
	public String toString() {
		return "Project [id=" + id + ", projectName=" + projectName + ", userId=" + userId + "]";
	}
	
}
