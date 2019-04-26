package com.hirain.qsy.shaft.common.shiro;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.hirain.qsy.shaft.model.Menu;
import com.hirain.qsy.shaft.model.Role;
import com.hirain.qsy.shaft.model.User;
import com.hirain.qsy.shaft.service.MenuService;
import com.hirain.qsy.shaft.service.RoleService;
import com.hirain.qsy.shaft.service.UserService;

public class AuthRealm extends AuthorizingRealm {

	@Autowired
	@Lazy
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	/**
	 * 授权模块，获取用户角色和权限
	 *
	 * @param principal
	 *            principal
	 * @return AuthorizationInfo 权限信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

		User user = (User) SecurityUtils.getSubject().getPrincipal();
		String userName = user.getUsername();

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		// 获取用户角色集
		List<Role> roleList = this.roleService.findUserRole(userName);
		Set<String> roleSet = roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
		simpleAuthorizationInfo.setRoles(roleSet);
		// 获取用户权限集
		List<Menu> permissionList = this.menuService.findUserPermissions(userName);
		Set<String> permissionSet = permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
		simpleAuthorizationInfo.setStringPermissions(permissionSet);
		return simpleAuthorizationInfo;
	}

	/**
	 * 用户认证
	 *
	 * @param token
	 *            AuthenticationToken 身份认证 token
	 * @return AuthenticationInfo 身份认证信息
	 * @throws AuthenticationException
	 *             认证相关异常
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		// 获取用户输入的用户名和密码
		String userName = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());

		// 通过用户名到数据库查询用户信息
		User user = this.userService.findByName(userName);

		if (user == null)
			throw new UnknownAccountException("用户名或密码错误！");
		if (!password.equals(user.getPassword()))
			throw new IncorrectCredentialsException("用户名或密码错误！");
		return new SimpleAuthenticationInfo(user, password, getName());
	}

	/**
	 * 清除权限缓存
	 * 使用方法：在需要清除用户权限的地方注入 ShiroRealm,
	 * 然后调用其clearCache方法。
	 */
	public void clearCache() {

		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}

}
