package com.liyan.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.liyan.entity.Project;
import com.liyan.entity.ProjectLink;
import com.liyan.entity.User;
import com.liyan.entity.UserForm;
import com.liyan.mapper.TestMapper;
import com.liyan.service.TestService;


@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestMapper testMapper;

	@Cacheable(value = "redisCacheManager",key="#user.guid")
	@Override
	public User addUser(User user) {
		//TODO 业务逻辑处理
		
		//操作数据库
		int result = testMapper.addUser(user);
		if(result > 0) {
			List<Project> projects = user.getProjects();
			if (projects != null && !projects.isEmpty()) {
				for (Project p: projects) {
					p.setId(UUID.randomUUID().toString());
					p.setUserId(user.getGuid());
				}
				testMapper.insertProjects(projects);
			}
			return user;
		}
		return null;
	}
	
	@CachePut(value = "redisCacheManager",key="#userForm.guid")
	@Override
	public User modefyUser(UserForm userForm) {
		int result = 0;
		User user = new User();
		BeanUtils.copyProperties(userForm, user);
		user.setUpdateTime(new Date());
		result = testMapper.modifyUser(user);
		if(result > 0)
		{
			//按照id查询数据
			user = testMapper.getUserByGuid(user.getGuid());
		}
		return user;
	}

	@CacheEvict(value = "redisCacheManager",key="#guid")
	@Override
	public int deleteUser(String guid) {
		int result = 0;
		result = testMapper.deleteUser(guid);
		return result;
	}

	@Override
	public List<User> getUser(String username, String password) {
		return testMapper.getUser(username,password);
	}

	@Override
	public String insertProjects(List<Project> users) {
		int result = testMapper.insertProjects(users);
		if (result > 0) {
			return "success";
		}
		return "error";
	}
	
	@Override
	public String uploadFile(List<ProjectLink> links) {
		int result = testMapper.uploadFile(links);
		if (result > 0) {
			return "上传文件成功";
		}
		
		return "上传文件失败";
	}
	
	//@Cacheable(key = "'redis_user_'+ #bounds.offset",value = "redisCacheManager")
	@Override
	public List<User> getUserByPage(String username, RowBounds bounds) {
		List<User> users = testMapper.getUserByPage(username,bounds);
		return users;
	}

	@Override
	public List<Project> getUserContainsProject(String guid) {
		return testMapper.getUserContainsProject(guid);
	}


}
