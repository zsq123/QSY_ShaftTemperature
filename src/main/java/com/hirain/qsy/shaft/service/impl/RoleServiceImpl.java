package com.hirain.qsy.shaft.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.qsy.shaft.dao.RoleMapper;
import com.hirain.qsy.shaft.dao.RoleMenuMapper;
import com.hirain.qsy.shaft.model.Role;
import com.hirain.qsy.shaft.model.User;
import com.hirain.qsy.shaft.service.RoleMenuServie;
import com.hirain.qsy.shaft.service.RoleService;
import com.hirain.qsy.shaft.service.UserRoleService;

import tk.mybatis.mapper.entity.Example;

@Service("roleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RoleMenuMapper roleMenuMapper;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private RoleMenuServie roleMenuService;

	@Override
	public List<Role> findUserRole(String userName) {

		return this.roleMapper.findUserRole(userName);
	}

	@Override
	public List<Role> findEditRole(Long userId) {

		try {
			Long checkedRoleId = null;
			Long currentLoginUserRoleId = null;
			// 当前登录用户
			User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
			// 当前勾选的用户拥有的角色
			List<Role> checkedRoleList = roleMapper.findRoleByUserId(userId);
			if (checkedRoleList != null && checkedRoleList.size() > 0) {
				checkedRoleId = checkedRoleList.get(0).getRoleId();
			}
			// 当前登录用户拥有的角色
			List<Role> currentLoginUserRoleList = roleMapper.findRoleByUserId(currentUser.getId());
			if (currentLoginUserRoleList != null && currentLoginUserRoleList.size() > 0) {
				currentLoginUserRoleId = currentLoginUserRoleList.get(0).getRoleId();
			}
			Example example = new Example(Role.class);
			if (checkedRoleId != null && currentLoginUserRoleId != null) {
				switch (String.valueOf(currentLoginUserRoleId)) {
				case "1":
					if (checkedRoleId != currentLoginUserRoleId) {
						example.createCriteria().andCondition("role_id!=", 1);
					}
					break;
				case "2":
					if (checkedRoleId != currentLoginUserRoleId) {
						example.createCriteria().andNotIn("roleId", Arrays.asList(new Long[] { 1L, 2L }));
					} else {
						example.createCriteria().andCondition("role_id!=", 1);
					}
					break;
				default:
					break;
				}
				example.setOrderByClause("create_time");
				return this.selectByExample(example);
			} else {
				throw new Exception("当前用户或勾选的用户未绑定角色");
			}

		} catch (Exception e) {
			log.error("获取角色信息失败", e);
			return new ArrayList<>();
		}
	}

	@Override
	public List<Role> findAddRole(Long userId) {

		try {
			Long currentLoginUserRoleId = null;
			// 当前登录用户拥有的角色
			List<Role> currentLoginUserRoleList = roleMapper.findRoleByUserId(userId);
			if (currentLoginUserRoleList != null && currentLoginUserRoleList.size() > 0) {
				currentLoginUserRoleId = currentLoginUserRoleList.get(0).getRoleId();
			}
			Example example = new Example(Role.class);
			switch (String.valueOf(currentLoginUserRoleId)) {
			case "1":
				example.createCriteria().andCondition("role_id!=", 1);
				break;
			case "2":
				example.createCriteria().andNotIn("roleId", Arrays.asList(new Long[] { 1L, 2L }));
				break;
			default:
				break;
			}
			example.setOrderByClause("create_time");
			return this.selectByExample(example);

		} catch (Exception e) {
			log.error("获取角色信息失败", e);
			return new ArrayList<>();
		}
	}

	@Override
	public Role findByName(String roleName) {

		Example example = new Example(Role.class);
		example.createCriteria().andCondition("lower(role_name)=", roleName.toLowerCase());
		List<Role> list = this.selectByExample(example);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	@Transactional
	public void addRole(Role role) {

		role.setCreateTime(new Date());
		this.save(role);
	}

	@Override
	@Transactional
	public void deleteRoles(String roleIds) {

		List<String> list = Arrays.asList(roleIds.split(","));
		this.batchDelete(list, "roleId", Role.class);

		this.roleMenuService.deleteRoleMenusByRoleId(roleIds);
		this.userRoleService.deleteUserRolesByRoleId(roleIds);

	}

	@Override
	public List<Role> findRoleByUserId(Long userId) {

		return this.roleMapper.findRoleByUserId(userId);
	}

	@Override
	public Role findRoleById(Long roleId) {

		return roleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	@Transactional
	public void updateRole(Role role) {

		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	@Transactional
	public void bandRoleAndMenu(Long roleId, Long[] menuIds) {

		Example example = new Example(Role.class);
		example.createCriteria().andCondition("role_id=", roleId);
		roleMenuMapper.deleteByExample(example);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("menuIds", menuIds);
		roleMenuMapper.bandRoleAndMenu(params);
	}

}
