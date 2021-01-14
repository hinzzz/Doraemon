package com.hinz.elasticsearch.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class User{
	private String name;
	private Integer age;
	private String address;
	private BigDecimal salary;
}
