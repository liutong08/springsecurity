package cn.com.taiji.dao;

import cn.com.taiji.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

/**
 * @author andyzhao
 */
@Mapper
public interface UserDao {
    User findByUserName(String user_name);

    //指定什么权限可以进行方法的使用
	// @Secured("ROLE_ADMIN")
    List<User> listAll();
}
