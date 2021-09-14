package com.liyan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.liyan.entity.Project;
import com.liyan.entity.ProjectLink;
import com.liyan.entity.User;

@Mapper
public interface TestMapper {

	@Insert({
			"insert into user(guid,name,username,password,create_time,photo) values (#{guid},#{name},#{username},#{password},#{createTime},#{photo,jdbcType=CLOB})" })
	// 新增用户
	public int addUser(User user);

	// 修改用户
	@Update({ "update user set username = #{username},name = #{name} where guid = #{guid} " })
	public int modifyUser(User user);

	@Delete({ "delete from user where guid = #{username}" })
	// 删除用户
	public int deleteUser(@Param("username") String username);

	// 查询用户
	public List<User> getUser(@Param("username") String username, @Param("password") String password);

	// 批量插入课程
	public int insertProjects(List<Project> projects);

	// 批量上传文件
	public int uploadFile(List<ProjectLink> links);

	//查询用户下面包含的课程
	public List<Project> getUserContainsProject(@Param("guid") String guid);
	
	//分页查询
	public List<User> getUserByPage(@Param("username") String username,RowBounds bounds);
	
	@Select("select * from user where guid = #{guid}")
	public User getUserByGuid(String guid);
}
