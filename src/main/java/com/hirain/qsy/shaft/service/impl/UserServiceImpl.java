package com.hirain.qsy.shaft.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.common.model.UserRequest;
import com.hirain.qsy.shaft.dao.UserMapper;
import com.hirain.qsy.shaft.dao.UserRoleMapper;
import com.hirain.qsy.shaft.model.Role;
import com.hirain.qsy.shaft.model.User;
import com.hirain.qsy.shaft.model.UserRole;
import com.hirain.qsy.shaft.service.RoleService;
import com.hirain.qsy.shaft.service.UserRoleService;
import com.hirain.qsy.shaft.service.UserService;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends BaseService<User> implements UserService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private RoleService roleService;

	@Override
	@Transactional
	public void addUser(User user, Long[] roles) {

		user.setCreateTime(new Date());
		this.save(user);
		setUserRoles(user, roles);
	}

	private void setUserRoles(User user, Long[] roles) {

		Arrays.stream(roles).forEach(roleId -> {
			UserRole ur = new UserRole();
			ur.setUserId(user.getId());
			ur.setRoleId(roleId);
			this.userRoleMapper.insert(ur);
		});
	}

	@Override
	@Transactional
	public void updateUser(User user, Long[] roles) {

		this.updateNotNull(user);
		Example example = new Example(UserRole.class);
		example.createCriteria().andCondition("user_id=", user.getId());
		this.userRoleMapper.deleteByExample(example);
		setUserRoles(user, roles);
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	@Transactional
	public void deleteUsers(String userIds) {

		List<String> list = Arrays.asList(userIds.split(","));
		this.batchDelete(list, "id", User.class);

		this.userRoleService.deleteUserRolesByUserId(userIds);
	}

	@Override
	public Map<String, Object> findById(Long userId) {

		Map<String, Object> map = new HashMap<>();
		List<Role> roles = roleService.findRoleByUserId(userId);
		User user = userMapper.selectByPrimaryKey(userId);
		map.put("roles", roles);
		map.put("tSysUser", user);
		return map;
	}

	@Override
	public User findByName(String userName) {

		Example example = new Example(User.class);
		example.createCriteria().andCondition("lower(username)=", userName.toLowerCase());
		List<User> list = this.selectByExample(example);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public List<User> findUserWithRole(UserRequest user, QueryRequest request) {

		try {
			return this.userMapper.findUserWithRole(user);
		} catch (Exception e) {
			log.error("error", e);
			return new ArrayList<>();
		}
	}

	@Override
	public List<User> findUserWithRoleByParentId(UserRequest user) {
		try {
			List<User> users = userMapper.findUserWithRoleByParentId(user);
			// 通过用户id和条件查出来的用户，size<=0
			List<User> findUserWithRole = userMapper.findUserWithRole(user);
			if (findUserWithRole.size() != 0) {
				Long userId = findUserWithRole.get(0).getId();
				if (!isContains(users, userId)) {
					users.addAll(findUserWithRole);
				}
			}
			return users;
		} catch (Exception e) {
			log.error("error", e);
			return new ArrayList<>();
		}
	}

	private boolean isContains(List<User> users, Long userId) {
		for (User user : users) {
			if (user.getId() == userId) {
				return true;
			}
		}
		return false;
	}
}
