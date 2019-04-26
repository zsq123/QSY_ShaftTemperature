package com.hirain.qsy.shaft.dao;

import java.util.List;

import com.hirain.qsy.shaft.common.config.MyMapper;
import com.hirain.qsy.shaft.model.User;

public interface UserMapper extends MyMapper<User> {

	List<User> findUserWithRole(User user);

	List<User> findUserWithRoleByParentId(User user);

}