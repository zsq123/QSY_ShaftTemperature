package com.hirain.qsy.shaft.service;

import com.hirain.qsy.shaft.model.UserRole;

public interface UserRoleService extends IService<UserRole> {

	void deleteUserRolesByRoleId(String roleIds);

	void deleteUserRolesByUserId(String userIds);
}
