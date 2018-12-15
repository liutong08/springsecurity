package cn.com.taiji.init;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cn.com.taiji.domain.Permission;
import cn.com.taiji.domain.Role;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.repository.PermissionRepository;
import cn.com.taiji.repository.RoleRepository;
import cn.com.taiji.repository.UserInfoRepository;

@Service
public class DataInit {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	
	//被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。
	@PostConstruct
	public void dataInit() {
		
		Role adminRole=new Role();
		adminRole.setName("ROLE_ADMIN");
		adminRole.setDescription("管理员");
		roleRepository.save(adminRole);
		

		Role userRole=new Role();
		userRole.setName("ROLE_USER");
		userRole.setDescription("用户");
		roleRepository.save(userRole);
		
		List<Role> adminList=new ArrayList<>();
		adminList.add(adminRole);
		adminList.add(userRole);
		
		UserInfo admin=new UserInfo();
		admin.setUsername("admin");
		//对写入数据表中的密码进行加密
		admin.setPassword(bCryptPasswordEncoder.encode("admin"));
		//获取枚举类中的其中一个角色
		admin.setRoles(adminList);
		//执行新增操作
		userInfoRepository.save(admin);
		
		List<Role> userList=new ArrayList<>();
		userList.add(userRole);
		
		UserInfo user=new UserInfo();
		user.setUsername("user");
		user.setPassword(bCryptPasswordEncoder.encode("user"));
		user.setRoles(userList);
		userInfoRepository.save(user);
		
		Permission permission1=new Permission();
		permission1.setUrl("/helloAdmin");
		permission1.setName("adminUrl");
		permission1.setDescription("管理员");
		List<Role> role1=new ArrayList<>();
		role1.add(adminRole);
		permission1.setRoles(role1);
		permissionRepository.save(permission1);
		
		Permission permission2=new Permission();
		permission2.setUrl("/helloUser");
		permission2.setName("userUrl");
		permission2.setDescription("用户");

		List<Role> role2=new ArrayList<>();
		role2.add(userRole);
		permission2.setRoles(role2);
		permissionRepository.save(permission2);
	}
}
