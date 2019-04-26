package com.hirain.qsy.shaft.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.hirain.qsy.shaft.common.annotation.Log;
import com.hirain.qsy.shaft.common.controller.BaseController;
import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.common.model.RespCodeEnum;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.common.model.UserRequest;
import com.hirain.qsy.shaft.model.User;
import com.hirain.qsy.shaft.service.UserService;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月8日 上午10:45:50
 * @Description
 *              <p>
 *              用户controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
public class UserController extends BaseController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@PostMapping("user/getUser")
	@RequiresAuthentication
	public ResponseBo getUser(Long userId) {

		try {
			return ResponseBo.ok(this.userService.findById(userId));
		} catch (Exception e) {
			log.error("获取用户失败", e);
			return ResponseBo.error("获取用户失败，请联系网站管理员！");
		}
	}

	@Log("获取用户信息")
	@PostMapping("user/list")
	@RequiresAuthentication
	public ResponseBo userList(QueryRequest request, UserRequest user) {

		Long currentUserId = getCurrentUser().getId();
		// Long currentUserId = 1L;
		// 超级管理员用户id为1
		if (currentUserId == 1) {
			return ResponseBo.ok(super.selectByPageNumSize(request, () -> this.userService.findUserWithRole(user, request)));
		} else {
			user.setId(currentUserId);
			user.setParentId(currentUserId);
			return ResponseBo.ok(super.selectByPageNumSize(request, () -> this.userService.findUserWithRoleByParentId(user)));

		}
	}

	@Log("新增用户")
	@PostMapping("user/addUser")
	@RequiresAuthentication
	public ResponseBo addUser(User user, Long[] roles) {

		try {
			if (userService.findByName(user.getUsername()) == null) {
				User currentUser = getCurrentUser();
				user.setParentId(currentUser.getId());
				user.setParentName(currentUser.getName());
				this.userService.addUser(user, roles);
				return ResponseBo.ok("新增用户成功！");
			} else {
				return ResponseBo.warn(RespCodeEnum.RepeatName);
			}
		} catch (Exception e) {
			log.error("新增用户失败", e);
			return ResponseBo.error("新增用户失败，请联系网站管理员！");
		}
	}

	@Log("修改用户")
	@PostMapping("user/updateUser")
	@RequiresAuthentication
	public ResponseBo updateUser(User user, Long[] roles) {

		try {
			User userOther = userService.findByName(user.getUsername());
			if (userOther == null || userOther.getId() == user.getId()) {
				this.userService.updateUser(user, roles);
				return ResponseBo.ok("修改用户成功！");
			} else {
				return ResponseBo.warn(RespCodeEnum.RepeatName);
			}
		} catch (Exception e) {
			log.error("修改用户失败", e);
			return ResponseBo.error("修改用户失败，请联系网站管理员！");
		}
	}

	@Log("删除用户")
	@PostMapping("user/deleteUser")
	@RequiresAuthentication
	public ResponseBo deleteUsers(String userIds) {

		try {
			List<String> userIdList = Arrays.asList(userIds.split(","));
			User currentUser = getCurrentUser();
			if (userIdList.contains(String.valueOf(currentUser.getId()))) {
				return ResponseBo.warn(RespCodeEnum.CanNotDeleteCurrentLoginUser);
			} else if (userIdList.contains("1")) {
				return ResponseBo.warn(RespCodeEnum.CanNotDeleteAdministrator);
			} else {
				this.userService.deleteUsers(userIds);
				return ResponseBo.ok("删除用户成功！");
			}
		} catch (Exception e) {
			log.error("删除用户失败", e);
			return ResponseBo.error("删除用户失败，请联系网站管理员！");
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {

		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}
}
