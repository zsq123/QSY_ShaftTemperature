package com.hirain.qsy.shaft.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;

import com.hirain.qsy.shaft.model.Menu;
import com.hirain.qsy.shaft.model.User;

@CacheConfig(cacheNames = "MenuService")
public interface MenuService extends IService<Menu> {

	List<Menu> findUserPermissions(String userName);

	List<Menu> getMenuTree(User user);

	void addMenu(Menu menu);

	void updateMenu(Menu menu);

	void deleteMeuns(String menuIds);

	List<Menu> getAllMenu(User user);

	List<Menu> findMenuListByRoleId(Long id);
}
