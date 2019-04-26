package com.hirain.qsy.shaft.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.qsy.shaft.common.annotation.Log;
import com.hirain.qsy.shaft.common.controller.BaseController;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.model.User;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月8日 上午10:46:46
 * @Description
 *              <p>
 *              登录controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
public class LoginController extends BaseController {

	@Log("登录")
	@PostMapping(value = "/login")
	public ResponseBo login(String accountname, String password) {

		UsernamePasswordToken token = new UsernamePasswordToken(accountname, password);
		try {
			Subject subject = getSubject();
			if (subject != null)
				subject.logout();
			super.login(token);
			subject.getSession().setAttribute("user", subject.getPrincipal());
			return ResponseBo.ok();
		} catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
			e.printStackTrace();
			return ResponseBo.error(e.getMessage());
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return ResponseBo.error("认证失败！");
		} catch (Throwable e) {
			e.printStackTrace();
			return ResponseBo.error("认证失败！");
		}
	}

	@PostMapping("/logout")
	public String logOut(HttpSession session) {

		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		// session.removeAttribute("user");
		return "login";
	}

	@GetMapping(value = "/notlogin")
	public ResponseBo notlogin() {

		User user = (User) getCurrentUser();
		if (user == null) {
			return ResponseBo.error("NotLogin");
		} else {
			return ResponseBo.ok();
		}

	}
}
