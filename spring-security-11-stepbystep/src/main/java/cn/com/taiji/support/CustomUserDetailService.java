package cn.com.taiji.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import cn.com.taiji.domain.Role;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.service.UserInfoService;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserInfoService userInfoService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// username->UserDetails
		// 1.根据username从数据库查出UserInfo
		// 2.获取UserInfo的密码
		// 3.使用GrantedAuthority接收用户角色，使用List<GrantedAuthority>保存多个角色
		// 4.将用户名，密码和角色封装到UserDetails中，并返回到WebSecurityConfig中
		UserInfo userInfo = userInfoService.findByUsername(username);
		if (userInfo == null) {
			throw new UsernameNotFoundException("not found UserInfo By Username" + username);
		}
		String password = userInfo.getPassword();
		List<GrantedAuthority> authorities = new ArrayList<>();
		// GrantedAuthority authority=new
		// SimpleGrantedAuthority(userInfo.getRole().name());
		for (Role role : userInfo.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		UserDetails user = new User(username, password, authorities);
		return user;
	}

}
