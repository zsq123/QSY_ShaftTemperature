package com.hirain.qsy.shaft.dao;

import java.util.List;

import com.hirain.qsy.shaft.common.config.MyMapper;
import com.hirain.qsy.shaft.model.Menu;

public interface MenuMapper extends MyMapper<Menu> {

	List<Menu> findUserPermissions(String userName);

	List<Menu> findUserMenus(String userName);

	List<Menu> findAllMenus();

	// 删除父节点，子节点变成顶级节点（根据实际业务调整）
	void changeToTop(List<String> menuIds);

	List<Menu> findMenuListByRoleId(Long id);
}