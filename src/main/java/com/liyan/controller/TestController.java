package com.liyan.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.liyan.entity.Project;
import com.liyan.entity.ProjectLink;
import com.liyan.entity.User;
import com.liyan.entity.UserForm;
import com.liyan.service.TestService;

@RestController
public class TestController {

	@Autowired
	private TestService testService;

	/**
	 * 新增实体
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addUser(@RequestParam(value = "photo", required = false) MultipartFile photo, User user) {
		user.setGuid(UUID.randomUUID().toString());
		user.setCreateTime(new Date());
		try {
			if(photo != null){
				InputStream inputStream = photo.getInputStream();
				byte[] b = new byte[inputStream.available()];
				inputStream.read(b);
				byte[] base64 = Base64.encodeBase64(b);
				user.setPhoto(base64);
			}
			testService.addUser(user);
		} catch (IOException e1) {
			e1.printStackTrace();
			return "error";
		}
		return "success";
	}

	/**
	 * 修改实体
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateUser(@RequestBody UserForm userForm) {
		try {
			testService.modefyUser(userForm);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	/**
	 * 删除实体
	 */
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String deleteUser(String guid, @RequestParam(value = "password",required = false)String password) {
		try {
			// 为了安全性，验证是否存在该用户
			/*List<User> user = testService.getUser(username, password);
			if (user != null) {
				// 进行删除
				
			} else {
				return "error";
			}*/
			testService.deleteUser(guid);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	/**
	 * 查询实体信息
	 */
	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public List<User> getUser(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password) {
		List<User> user = new ArrayList<>();
		try {
			user = testService.getUser(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	@RequestMapping(value="getUserByPage",method=RequestMethod.GET, produces="application/json;charset=utf-8")
	public List<User> getUserByPage(@RequestParam(value = "username",required = false) String username,
			@RequestParam(value="currentPage",defaultValue= "0")int currentPage) {
		int pageSize = 6;
		int startpage = currentPage*pageSize;
		RowBounds bounds = new RowBounds(startpage, pageSize);
		List<User> users = new ArrayList<>();
		try{
			users = testService.getUserByPage(username,bounds);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	

	/**
	 * 查询用户信息包括课程信息
	 * 
	 */
	@RequestMapping(value = "getUserProject", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public Map<String, Object> getUserProject(String guid) {
		//Project project = new Project();
		List<Project> projects = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		try {
			projects = testService.getUserContainsProject(guid);
			map.put("success", true);
			map.put("result", projects);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("errMsg", e.toString());
		}
		return map;
	}

	/**
	 * 查询用户信息包括课程信息
	 * 
	 */
	/*@RequestMapping(value = "getUserProject1", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public String getUserProject1(String username, String password) {
		User user = new User();
		JSONObject map = new JSONObject();
		try {
			user = testService.getUserContainsProject(username);
			map.put("success", true);
			map.put("result", user);
		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.toString());
		}
		return map.toString();
	}
*/	
	/**
	 * 根据用户添加课程
	 */
	@RequestMapping(value = "insertProjects", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String insertProjects(@RequestBody User user) {
		List<Project> projects = user.getProjects();
		String id = user.getGuid();
		for (Project project : projects) {
			project.setUserId(id);
		}
		try {
			testService.insertProjects(projects);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	/**
	 * 根据用户添加多个附件
	 * 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@PostMapping(value = "/uploadFiles", produces = "application/json;charset=utf-8")
	public String uploadFiles(User user, MultipartFile[] files, HttpServletRequest request)
			throws IllegalStateException, IOException {
		List<ProjectLink> links = new ArrayList<>();
		String id = user.getGuid();
		String path = request.getSession().getServletContext().getRealPath(File.separator);
		path.replace(File.separator, "/");
		File targetFiles = new File(path, "upload/" + id);
		if (!targetFiles.exists()) {
			targetFiles.mkdirs();
		}
		for (MultipartFile file : files) {
			String name = file.getOriginalFilename();
			File f = new File(targetFiles, name);
			ProjectLink link = new ProjectLink();
			file.transferTo(f);
			link.setGlid(id);
			link.setId(UUID.randomUUID().toString());
			link.setFileName(name);
			link.setDelFlag("0");
			link.setInsertTime(new Date());
			links.add(link);
		}
		try {
			testService.uploadFile(links);
		} catch (Exception e) {
			e.printStackTrace();
			return "文件上传失败！";
		}
		return "文件上传成功！";

	}

	/**
	 * 获取路径下面的文件
	 * 
	 * @throws IOException
	 */
	@PostMapping(value = "/downLoadFile", produces = "application/json;charset=utf-8")
	public String downLoadFile(String id, String file, HttpServletRequest request) throws IOException {
		String path = request.getSession().getServletContext().getRealPath(File.separator);
		File targetFiles = new File(path, "upload/" + id + "/" + file);
		if (targetFiles.exists()) {
			File dest = new File("D:/download/", file);
			FileCopyUtils.copy(targetFiles, dest);
			return "下载成功！";
		}
		return "下载失败！";

	}

	/**
	 * 通过浏览器下载文件 1,先通request对象获取到要下载的文件，通过文件流的方式将文件写入到浏览器的输出流中，同过浏览器进行路径下载；
	 * 
	 * @throws IOException
	 */

	@GetMapping("/downLoad")
	public void downLoad(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = request.getParameter("fileName");
		String id = request.getParameter("id");
		String path = request.getSession().getServletContext().getRealPath(File.separator);
		File targetFiles = new File(path, "upload/" + id + "/" + fileName);
		OutputStream out = response.getOutputStream();
		FileInputStream in = null;
		// 判断文件是否存在
		if (targetFiles.exists()) {
			// 设置响应头，浏览器文件下载
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			in = new FileInputStream(targetFiles);
			// 定义一个文件流缓冲区 ，大小为1024
			byte[] b = new byte[1024];
			int length = 0;
			while ((length = in.read(b)) > 0) {
				// 写出文件流
				out.write(b, 0, length);
			}
		} else {
			out.write("该文件不存在".getBytes());
		}
		in.close();// 关闭输入流
		out.close();// 关闭输出流
	}
}
