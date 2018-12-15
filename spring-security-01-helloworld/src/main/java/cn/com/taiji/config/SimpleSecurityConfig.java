package cn.com.taiji.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security 配置类.
 */
// SimpleSecurityConfig
@EnableWebSecurity//自动加载web安全
public class SimpleSecurityConfig extends WebSecurityConfigurerAdapter {
	/**
	 * // * 自定义配置 //
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//SimpleExceptionSecurityConfig
		// 都可以访问
		http.authorizeRequests()
		//设定所有静态资源可以访问
		      .antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()
		      //设定登录页面和登录失败页面可以访问
		      .antMatchers("/login","/login-error").permitAll()
		      // 需要相应的角色才能访问 只有角色为USER时，才可以访问/users下的信息
		      .antMatchers("/users/**").hasRole("USER")//hasRole中只能写一个角色
		      // 需要相应的角色才能访问 
		      .antMatchers("/admins/**").hasAnyRole("ADMIN")//hasAnyRole可以写多个角色，只要满足一个就可以
		      .anyRequest().authenticated()//对于每一个请求都进行权限鉴定
		      .and()
		      // 表单登录验证，自定义登录界面;指定表单登录的登录页面和登录失败页面
		      .formLogin().loginPage("/login").failureUrl("/login-error")
		      .and()
		      // 处理异常，拒绝访问就重定向到 403 页面
		      .exceptionHandling().accessDeniedPage("/403");

		// SimpleAuthorizationSecurityConfig配置类中的
//		http.authorizeRequests()
//				// 都可以访问
//				.antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()
//				.antMatchers("/login").permitAll()
//				// 需要相应的角色才能访问
//				.antMatchers("/users/**").hasRole("USER")
//				// 需要相应的角色才能访问
//				.antMatchers("/admins/**").hasAnyRole("ADMIN")
		//访问需要角色ADMIN或者拥有权限
//				.antMatchers("/admins/**").access("hasRole('ADMIN') or hasAuthority('fooAuthority')")
//				.antMatchers("/admins/**").hasIpAddress("127.0.0.1")//指定访问的ip地址为127.0.0.1
//				.anyRequest().authenticated().and().formLogin().loginPage("/login");

		// SimpleLogoutSecurityConfig配置类中的
//		http.authorizeRequests()
//				// 都可以访问
//				.antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()
//				.antMatchers("/login").permitAll()
//				.anyRequest().authenticated()
//				.and().formLogin().loginPage("/login");

		// 禁用csrf后，logout支持get，post，delete，put
		// 默认csrf是开启的，logout只能是post
		// http.csrf().disable();

		// SimpleCustomLoginPageSecurityConfig配置类中的
//		 http.authorizeRequests()
//		 // 都可以访问
//			 .antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()
//			 .antMatchers("/login").permitAll()
//			 .anyRequest().authenticated()
//			 .and().formLogin().loginPage("/login");

		// SimpleAddUserSecurityConfig配置类中的
		// super.configure(http);

	}

	// SimpleAddUserSecurityConfig配置类中的
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	// SimpleAddUserSecurityConfig配置类中的
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// super.configure(auth);
		//设置可登录的用户以及其密码和角色
		auth.inMemoryAuthentication().withUser("user").password("123456").roles("USER")//角色
		.and().withUser("admin").password("123456").roles("USER", "ADMIN")//角色
		.and().withUser("foo").password("123456").authorities("fooAuthority");//拥有权限
	}
}
