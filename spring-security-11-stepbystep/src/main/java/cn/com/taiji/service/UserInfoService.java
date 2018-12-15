package cn.com.taiji.service;

import cn.com.taiji.domain.UserInfo;

public interface UserInfoService {
	UserInfo findByUsername(String username);
}
