package cn.com.taiji.controller;

//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//返回为JSON
@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		return "hello world";
	}

	@GetMapping("/helloUser")
	// 设定当前路径只有指定角色允许访问
	// @PreAuthorize("hasAnyRole('USER')")
	public String getHelloUser() {
		return "hello User";
	}

	@GetMapping("/helloAdmin")
	// 设定当前路径只有指定角色允许访问
	// @PreAuthorize("hasAnyRole('ADMIN')")
	public String getHelloAdmin() {
		return "hello Admin";
	}
}
