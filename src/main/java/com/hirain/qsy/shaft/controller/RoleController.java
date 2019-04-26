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

import com.hirain.qsy.shaft.common.controller.BaseController;
import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.model.Role;
import com.hirain.qsy.shaft.model.User;
import com.hirain.qsy.shaft.service.RoleService;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月8日 上午10:46:05
 * @Description
 *              <p>
 *              角色controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleService roleService;

	@PostMapping("/list")
	@RequiresAuthentication
	public ResponseBo roleList(QueryRequest request, Long userId, boolean isAdd) {
		if (isAdd) {
			return ResponseBo.ok(super.selectByPageNumSize(request, () -> this.roleService.findAddRole(getCurrentUser().getId())));
		}
		return ResponseBo.ok(super.selectByPageNumSize(request, () -> this.roleService.findEditRole(userId)));
	}

	@PostMapping("/getAllRoles")
	@RequiresAuthentication
	public ResponseBo getAllRoles(QueryRequest request) {
		return ResponseBo.ok(super.selectByPageNumSize(request, () -> this.roleService.selectAll()));
	}

	@PostMapping("/findRoleByRoleId")
	@RequiresAuthentication
	public ResponseBo findRoleByRoleId(Long id) {

		try {
			Role role = this.roleService.findRoleById(id);
			return ResponseBo.ok(role);
		} catch (Exception e) {
			log.error("获取角色信息失败", e);
			return ResponseBo.error("获取角色信息失败，请联系网站管理员！");
		}
	}

	@GetMapping("/findRoleByUserId")
	@RequiresAuthentication
	public ResponseBo findRoleByUserId() {

		try {
			User user = (User) getCurrentUser();
			List<Role> roles = this.roleService.findRoleByUserId(user.getId());
			if (roles == null || roles.size() == 0) {
				return ResponseBo.error("该用户未绑定角色");
			} else {
				return ResponseBo.ok(roles.get(0).getRoleName());

			}
		} catch (Exception e) {
			log.error("获取角色信息失败", e);
			return ResponseBo.error("获取角色信息失败，请联系网站管理员！");
		}
	}

	// @Log("新增角色")
	@PostMapping("/addRole")
	@RequiresAuthentication
	public ResponseBo addRole(Role role) {

		try {
			Role result = this.roleService.findByName(role.getRoleName());
			if (result != null) {
				return ResponseBo.warn("该角色名已存在,请重新命名！");
			} else {
				this.roleService.addRole(role);
				return ResponseBo.ok("新增角色成功！");
			}
		} catch (Exception e) {
			log.error("新增角色失败", e);
			return ResponseBo.error("新增角色失败，请联系网站管理员！");
		}
	}

	// @Log("删除角色")
	@PostMapping("/delete")
	@RequiresAuthentication
	public ResponseBo deleteRoles(String ids) {

		try {
			this.roleService.deleteRoles(ids);
			return ResponseBo.ok("删除角色成功！");
		} catch (Exception e) {
			log.error("删除角色失败", e);
			return ResponseBo.error("删除角色失败，请联系网站管理员！");
		}
	}

	// @Log("修改角色")
	@PostMapping("/updateRole")
	@RequiresAuthentication
	public ResponseBo updateRole(Role role) {

		try {
			this.roleService.updateRole(role);
			return ResponseBo.ok("修改角色成功！");
		} catch (Exception e) {
			log.error("修改角色失败", e);
			return ResponseBo.error("修改角色失败，请联系网站管理员！");
		}
	}

	// @Log("修改角色")
	@PostMapping("/bandRoleAndMenu")
	@RequiresAuthentication
	public ResponseBo bandRoleAndMenu(Long roleId, Long[] resIds) {

		try {
			this.roleService.bandRoleAndMenu(roleId, resIds);
			return ResponseBo.ok("修改角色成功！");
		} catch (Exception e) {
			log.error("修改角色失败", e);
			return ResponseBo.error("修改角色失败，请联系网站管理员！");
		}
	}
}
