package cn.com.taiji.support;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import cn.com.taiji.service.PermissionService;

@Service
public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private PermissionService permissionService;

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

		Map<String, Collection<ConfigAttribute>> permissionMap = permissionService.getPermissionMap();
		for (String resUrl : permissionMap.keySet()) {
			System.out.println("----"+resUrl);
			AntPathRequestMatcher matcher = new AntPathRequestMatcher(resUrl);
			if (matcher.matches(request)) {
				return permissionService.getPermissionMap().get(resUrl);
			}
		}

		// String resUrl = request.getRequestURL();
		// return permissionService.getPermissionMap().get(resUrl);

		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}
