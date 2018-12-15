package cn.com.taiji.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;

public interface PermissionService {
	Map<String,Collection<ConfigAttribute>> getPermissionMap();
}
