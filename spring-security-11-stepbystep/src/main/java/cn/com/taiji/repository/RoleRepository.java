package cn.com.taiji.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.com.taiji.domain.Role;


public interface RoleRepository extends JpaRepository<Role,Integer>{
	//通过关键字和属性拼接，实现数据库的查询操作
}
