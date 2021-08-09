package com.hinz.elasticsearch.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{
	private String name;
	private Integer age;
	private String address;
	private BigDecimal salary;
}
