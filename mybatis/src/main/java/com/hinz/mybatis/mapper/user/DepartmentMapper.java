package com.hinz.mybatis.mapper.user;


import com.hinz.mybatis.bean.user.Department;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper {
	
	public Department getDeptById(Integer id);
	
	public Department getDeptByIdPlus(Integer id);

	public Department getDeptByIdStep(Integer id);
}
