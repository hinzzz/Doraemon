package com.hinz.mybatis.mapper.user;

import com.hinz.mybatis.bean.user.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapperPlus {
	
	public Employee getEmpById(Integer id);
	
	public Employee getEmpAndDept(Integer id);
	
	public Employee getEmpByIdStep(Integer id);
	
	public List<Employee> getEmpsByDeptId(Integer deptId);

}
