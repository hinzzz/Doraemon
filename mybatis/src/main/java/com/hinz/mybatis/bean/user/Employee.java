package com.hinz.mybatis.bean.user;

import java.io.Serializable;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("emp")
public class Employee implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String lastName;
	private String email;
	private String gender;
	private Department dept;
}
