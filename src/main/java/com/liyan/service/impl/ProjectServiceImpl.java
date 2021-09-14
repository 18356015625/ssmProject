package com.liyan.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyan.entity.Project;
import com.liyan.mapper.ProjectMapper;
import com.liyan.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectMapper projectMapper;
	
	@Override
	public int insertProject(Project project) {
		return projectMapper.insertProject(project);
		
	}

	@Override
	public int modifyProject(String id, String projectName) {
		return projectMapper.modifyProject(id, projectName);
	}

	@Override
	public int deleteProject(String id) {
		return projectMapper.deleteProject(id);
	}

	@Override
	public List<Project> getProject() {
		return projectMapper.getProject();
	}

	@Override
	public Project getProjectById(String id) {
		return projectMapper.getProjectById(id);
	}

	@Override
	public List<Project> getProjectByPage(RowBounds bounds) {
		
		return projectMapper.getProjectByPage(bounds);
	}


}
