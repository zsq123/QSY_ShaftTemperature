package com.hirain.qsy.shaft.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.qsy.shaft.common.annotation.Log;
import com.hirain.qsy.shaft.common.controller.BaseController;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.model.Menu;
import com.hirain.qsy.shaft.model.User;
import com.hirain.qsy.shaft.service.MenuService;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月8日 上午10:46:29
 * @Description
 *              <p>
 *              菜单controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MenuService menuService;

	@GetMapping("/tree")
	@RequiresAuthentication
	public ResponseBo getMenuTree() {

		try {
			User user = (User) getCurrentUser();
			List<Menu> tree = this.menuService.getMenuTree(user);
			return ResponseBo.ok(tree);

		} catch (Exception e) {
			logger.error("获取菜单树失败", e);
			return ResponseBo.error("获取菜单树失败！");
		}
	}

	@GetMapping("/getAllMenu")
	@RequiresAuthentication
	public ResponseBo getAllMenu() {

		try {
			User user = (User) getCurrentUser();
			List<Menu> tree = this.menuService.getAllMenu(user);
			ResponseBo response = ResponseBo.ok(tree);
			response.put("perms", "111");
			return response;

		} catch (Exception e) {
			logger.error("获取菜单失败", e);
			return ResponseBo.error("获取菜单失败！");
		}
	}

	@PostMapping("/toGrantTSysRole")
	@RequiresAuthentication
	public ResponseBo findMenuListByRoleId(Long id) {

		try {
			List<Menu> menus = this.menuService.findMenuListByRoleId(id);
			return ResponseBo.ok(menus);

		} catch (Exception e) {
			logger.error("获取菜单失败", e);
			return ResponseBo.error("获取菜单失败！");
		}
	}

	// @Log("新增菜单/按钮")
	// @PostMapping("/add")
	// @RequiresAuthentication
	// public ResponseBo addMenu(Menu menu) {
	//
	// String name;
	// if (Menu.TYPE_MENU.equals(menu.getType())) {
	// name = "菜单";
	// } else {
	// name = "按钮";
	// }
	// try {
	// this.menuService.addMenu(menu);
	// return ResponseBo.ok("新增" + name + "成功！");
	// } catch (Exception e) {
	// logger.error("新增{}失败", name, e);
	// return ResponseBo.error("新增" + name + "失败，请联系网站管理员！");
	// }
	// }

	@Log("删除菜单")
	@PostMapping("/delete")
	@RequiresAuthentication
	public ResponseBo deleteMenus(String ids) {

		try {
			this.menuService.deleteMeuns(ids);
			return ResponseBo.ok("删除成功！");
		} catch (Exception e) {
			logger.error("获取菜单失败", e);
			return ResponseBo.error("删除失败，请联系网站管理员！");
		}
	}

	@Log("修改菜单/按钮")
	@PostMapping("/update")
	@RequiresAuthentication
	public ResponseBo updateMenu(Menu menu) {

		String name;
		if (Menu.TYPE_MENU.equals(menu.getType()))
			name = "菜单";
		else
			name = "按钮";
		try {
			this.menuService.updateMenu(menu);
			return ResponseBo.ok("修改" + name + "成功！");
		} catch (Exception e) {
			logger.error("修改{}失败", name, e);
			return ResponseBo.error("修改" + name + "失败，请联系网站管理员！");
		}
	}

}
