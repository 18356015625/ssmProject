package com.liyan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.liyan.entity.Project;

@Mapper
public interface ProjectMapper {
	
	/**
	 * 新增课程
	 * @param project
	 * @return
	 */
	public int insertProject(Project project);
	
	/**
	 * 修改课程
	 * @param project
	 * @return
	 */
	public int modifyProject(@Param("id")String id, @Param("projectName")String projectName );
	
	/**
	 * 删除课程
	 * @param project
	 * @return
	 */
	public int deleteProject(@Param("id")String id);
	
	/**
	 * 查询所有课程
	 * @return
	 */
	public List<Project> getProject();
	
	/**
	 * 根据id查询课程
	 * @return
	 */
	public Project getProjectById(@Param("id")String id);
	
	
	/**
	 * 分页查询课程
	 * @param bounds
	 * @return
	 */
	public List<Project> getProjectByPage(RowBounds bounds);

}
