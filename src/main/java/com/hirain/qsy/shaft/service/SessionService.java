package com.hirain.qsy.shaft.service;

import java.util.List;

import com.hirain.qsy.shaft.model.UserOnline;

public interface SessionService {

	List<UserOnline> list();

	boolean forceLogout(String sessionId);
}
