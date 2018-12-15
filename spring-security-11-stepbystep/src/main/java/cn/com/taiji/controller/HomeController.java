package cn.com.taiji.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	// 默认路径
	@GetMapping({ "", "/", "/index" })
	public String index(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal.equals("anonymousUser")) {
			model.addAttribute("name", "anonymousUser");
		} else {
			User user = (User) principal;
			model.addAttribute("name", user.getUsername());
		}

		return "/index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	// security有默认的退出登录
	// @PostMapping("/logout")
	// public String logout() {
	// return "login";
	// }
	// security有默认的403,目录必须在error下
	// @GetMapping("/403")
	// public String to403() {
	// return "error/403";
	// }
}
