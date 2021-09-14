package com.liyan.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.liyan.entity.Project;
import com.liyan.service.ProjectService;

@Controller
public class ProjectController {
	

	@Autowired
	private ProjectService projectService;
	
	/**
	 * controller - service - mapper
	 * @param project
	 * @return
	 */
	@RequestMapping(value = "/insertProject", method = RequestMethod.POST)
	@ResponseBody
	public String insertProject(Project project) {
		project.setId(UUID.randomUUID().toString());
		//service
		int project1 = projectService.insertProject(project);
		if (project1 == 1) {
			return "success";
		} 
		return "error";
	}
	
	@RequestMapping(value = "/modifyProject", method = RequestMethod.GET)
	@ResponseBody
	public String modifyProject(String id,String projectName) {
		try {
			projectService.modifyProject(id,projectName);
		} catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	
	@RequestMapping(value = "/deleteProject", method = RequestMethod.GET)
	@ResponseBody
	public String deleteProject(String id) {
		try {
			projectService.deleteProject(id);
		} catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	@RequestMapping(value = "/getProject",method = RequestMethod.GET, produces = "application/json;chartset=utf-8")
	@ResponseBody
	public List<Project> getProject() {
		List<Project> result = new ArrayList<Project>();
		try {
			result = projectService.getProject();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value = "/getProjectById", method = RequestMethod.GET, produces = "application/json;chartset=utf-8")
	@ResponseBody
	public Project getProjectById(String id) {
		Project project = new Project();
		try {
			project = projectService.getProjectById(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return project;
	}
	
	@GetMapping(value = "/getProjectByPage", produces = "application/json;chartset=utf-8")
	@ResponseBody
	public List<Project> getProjectByPage(int currentPage,@RequestParam(defaultValue = "2") int pageSize){
		int startoff = currentPage*pageSize;
		List<Project> project = new ArrayList<Project>();
		RowBounds bounds = new RowBounds(startoff,pageSize);
		try {
			project = projectService.getProjectByPage(bounds);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return project;
	}
	
	//MultipartFile spring mvc提供的一个类
	@PostMapping("/uploadFile")
	@ResponseBody
	public Map<String,Object> uploadFile(MultipartFile file) {
		Map<String, Object> map = new HashMap<>();
		//获取原始文件名
		String fileName = file.getOriginalFilename();
		file.getContentType();
		//目标文件
		File dest = new File("D:/download/",fileName);
		System.out.println(dest.getAbsolutePath());
		try{
			//保存文件,需要传入绝对路径，否则不报错，但也没有上传成功
			file.transferTo(dest);
			map.put("result", "文件上传成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "文件上传失败");
		}
		return map;
	}
	
	
	
}
