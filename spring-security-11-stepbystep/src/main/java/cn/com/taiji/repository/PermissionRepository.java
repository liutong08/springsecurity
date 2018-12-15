package cn.com.taiji.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.com.taiji.domain.Permission;


public interface PermissionRepository extends JpaRepository<Permission,Integer>{
	//通过关键字和属性拼接，实现数据库的查询操作
}
