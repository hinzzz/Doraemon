/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.hinz.commonapi.entities;


import cn.hutool.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据实体
 * @author quanhz
 * @date 2021-04-02
 */
public class CommonResult extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public CommonResult() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static CommonResult error() {
		return error(HttpStatus.HTTP_INTERNAL_ERROR, "未知异常，请联系管理员");
	}
	
	public static CommonResult error(String msg) {
		return error(HttpStatus.HTTP_INTERNAL_ERROR, msg);
	}
	
	public static CommonResult error(int code, String msg) {
		CommonResult r = new CommonResult();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static CommonResult ok(String msg) {
		CommonResult r = new CommonResult();
		r.put("msg", msg);
		return r;
	}
	
	public static CommonResult ok(Map<String, Object> map) {
		CommonResult r = new CommonResult();
		r.putAll(map);
		return r;
	}
	
	public static CommonResult ok() {
		return new CommonResult();
	}

	public CommonResult put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	public  Integer getCode() {

		return (Integer) this.get("code");
	}

}
