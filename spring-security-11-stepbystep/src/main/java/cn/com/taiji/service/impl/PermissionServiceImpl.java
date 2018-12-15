package cn.com.taiji.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import cn.com.taiji.domain.Permission;
import cn.com.taiji.domain.Role;
import cn.com.taiji.repository.PermissionRepository;
import cn.com.taiji.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
	private Map<String, Collection<ConfigAttribute>> permissionMap = null;

	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public Map<String, Collection<ConfigAttribute>> getPermissionMap() {
		// TODO Auto-generated method stub
		if (permissionMap == null && permissionMap.size() == 0) {
			initPermissions();
		}
		// key=url
		// value=List<Role>

		return permissionMap;
	}

	@PostConstruct
	public void initPermissions() {
		permissionMap = new HashMap<>();
		List<Permission> permissionList = permissionRepository.findAll();
		for (Permission permission : permissionList) {
			Collection<ConfigAttribute> collection = new ArrayList<ConfigAttribute>();
			for (Role r : permission.getRoles()) {
				ConfigAttribute cfg = new SecurityConfig(r.getName());
				collection.add(cfg);
			}
			permissionMap.put(permission.getUrl(), collection);
		}
	}
}
