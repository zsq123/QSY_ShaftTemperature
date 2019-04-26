package com.hirain.qsy.shaft.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.qsy.shaft.common.util.TreeUtil;
import com.hirain.qsy.shaft.dao.MenuMapper;
import com.hirain.qsy.shaft.model.Menu;
import com.hirain.qsy.shaft.model.User;
import com.hirain.qsy.shaft.service.MenuService;
import com.hirain.qsy.shaft.service.RoleMenuServie;

@Service("menuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends BaseService<Menu> implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private RoleMenuServie roleMenuService;

	@Override
	public List<Menu> findUserPermissions(String userName) {

		return this.menuMapper.findUserPermissions(userName);
	}

	@Override
	public List<Menu> getMenuTree(User user) {

		List<Menu> menus = this.menuMapper.findUserMenus(user.getUsername());
		TreeUtil treeUtil = new TreeUtil();
		List<Menu> menusOrder = treeUtil.getChildTreeObjects(menus, 0);
		return menusOrder;
	}

	@Override
	public List<Menu> getAllMenu(User user) {

		List<Menu> menus = this.menuMapper.findAllMenus();
		TreeUtil treeUtil = new TreeUtil();
		List<Menu> menusOrder = treeUtil.getChildTreeObjects(menus, 0);

		return menusOrder;
	}

	@Override
	@Transactional
	public void addMenu(Menu menu) {

		menu.setCreateTime(new Date());
		if (menu.getParentId() == null)
			menu.setParentId(0L);
		if (Menu.TYPE_BUTTON.equals(menu.getType())) {
			menu.setUrl(null);
			menu.setIcon(null);
		}
		this.save(menu);
	}

	@Override
	@Transactional
	public void deleteMeuns(String menuIds) {

		List<String> list = Arrays.asList(menuIds.split(","));
		this.batchDelete(list, "id", Menu.class);
		this.roleMenuService.deleteRoleMenusByMenuId(menuIds);
		this.menuMapper.changeToTop(list);
	}

	@Override
	@Transactional
	public void updateMenu(Menu menu) {

		menu.setModifyTime(new Date());
		if (menu.getParentId() == null)
			menu.setParentId(0L);
		if (Menu.TYPE_BUTTON.equals(menu.getType())) {
			menu.setUrl(null);
			menu.setIcon(null);
		}
		this.updateNotNull(menu);
	}

	@Override
	public List<Menu> findMenuListByRoleId(Long id) {

		return menuMapper.findMenuListByRoleId(id);
	}

}
