package com.hirain.qsy.shaft.service;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.common.model.UserRequest;
import com.hirain.qsy.shaft.model.User;

@CacheConfig(cacheNames = "UserService")
public interface UserService extends IService<User> {

	Map<String, Object> findById(Long userId);

	User findByName(String userName);

	@Cacheable(key = "#p0.toString() + (#p1 != null ? #p1.toString() : '')")
	List<User> findUserWithRole(UserRequest user, QueryRequest request);

	@CacheEvict(allEntries = true)
	void addUser(User user, Long[] roles);

	@CacheEvict(allEntries = true)
	void updateUser(User user, Long[] roles);

	@CacheEvict(allEntries = true)
	void deleteUsers(String userIds);

	List<User> findUserWithRoleByParentId(UserRequest user);

}
