package com.liyan.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.liyan.entity.Project;
import com.liyan.entity.ProjectLink;
import com.liyan.entity.User;
import com.liyan.entity.UserForm;

public interface TestService {
	
	//新增user信息
	User addUser(User user);
		
	//修改用户信息
	User modefyUser(UserForm userForm);
	
	//删除用户信息
	int deleteUser(String username);
	
	//查询用户信息
	List<User> getUser(String username,String password);
	
	//加载用户下面的课程
	List<Project> getUserContainsProject (String guid);
	
	//批量插入数据
	String insertProjects(List<Project> users);
	
	//批量上传文件
	String uploadFile(List<ProjectLink> links);
	
	//分页查询
	List<User> getUserByPage(String username, RowBounds bounds);
	
}

