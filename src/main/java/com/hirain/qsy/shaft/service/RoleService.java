package com.hirain.qsy.shaft.service;

import java.util.List;

import com.hirain.qsy.shaft.model.Role;

public interface RoleService extends IService<Role> {

	List<Role> findRoleByUserId(Long userId);

	List<Role> findUserRole(String userName);

	/**
	 * @param userId
	 *            当前勾选的用户Id
	 * @return
	 */
	List<Role> findEditRole(Long userId);

	/**
	 * @param userId
	 *            当前登录用户id
	 * @return
	 */
	List<Role> findAddRole(Long userId);

	Role findByName(String roleName);

	void deleteRoles(String roleIds);

	void addRole(Role role);

	Role findRoleById(Long roleId);

	void updateRole(Role role);

	void bandRoleAndMenu(Long roleId, Long[] resIds);
}
