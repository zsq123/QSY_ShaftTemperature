package com.hirain.qsy.shaft.common.model;

/**
 * 返回代码 常量
 * 
 * @author
 *
 */
public enum RespCodeEnum {

	// 该用户名已存在！
	RepeatName("0001", "该用户名已存在！"),
	// 不能删除超级管理员
	CanNotDeleteAdministrator("0002", "不能删除超级管理员"),
	// 删除用户失败，不能删除当前登录用户！
	CanNotDeleteCurrentLoginUser("0003", "删除用户失败，不能删除当前登录用户！");

	private RespCodeEnum(String code, String name) {

		this.code = code;
		this.name = name;
	}

	public String code;

	public String name;

	public String getCode() {

		return this.code;
	}

	public String getName() {

		return this.name;
	}

}
