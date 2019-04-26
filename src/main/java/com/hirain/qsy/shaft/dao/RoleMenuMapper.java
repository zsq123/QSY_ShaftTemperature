package com.hirain.qsy.shaft.dao;

import java.util.Map;

import com.hirain.qsy.shaft.common.config.MyMapper;
import com.hirain.qsy.shaft.model.RoleMenu;

public interface RoleMenuMapper extends MyMapper<RoleMenu> {

	void bandRoleAndMenu(Map<String, Object> params);
}