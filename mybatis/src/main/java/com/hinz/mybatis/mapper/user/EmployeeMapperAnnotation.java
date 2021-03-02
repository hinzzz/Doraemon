package com.hinz.mybatis.mapper.user;

import com.hinz.mybatis.bean.user.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapperAnnotation {
	
	@Select("select * from tbl_employee where id=#{id}")
	public Employee getEmpById(Integer id);
}
