package cn.com.taiji.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.repository.UserInfoRepository;
import cn.com.taiji.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	public UserInfo findByUsername(String username) {
		// TODO Auto-generated method stub
		return userInfoRepository.findByUsername(username);
	}
	
}
