package com.liyan.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.liyan.entity.Project;

public interface ProjectService {

	//新增课程
	int insertProject(Project project);
	
	//修改课程
	int modifyProject(String id,String projectName);
	
	//删除课程
	int deleteProject(String id);
	
	//查询所有课程
	List<Project> getProject();
	
	//根据id查询课程
	Project getProjectById(String id);
	
	//分页查询所有的课程
	List<Project> getProjectByPage(RowBounds bounds);
	
	
}
