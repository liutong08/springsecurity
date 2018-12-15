package cn.com.taiji.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import cn.com.taiji.filter.AfterLoginFilter;
//import cn.com.taiji.filter.AtLoginFilter;
//import cn.com.taiji.filter.BeforeLoginFilter;
import cn.com.taiji.support.CustomFilterSecurityInterceptor;
import cn.com.taiji.support.CustomUserDetailService;

@Configuration
// 通过 @EnableWebSecurity注解开启Spring Security的功能
@EnableWebSecurity
// 可以开启security的注解，我们可以在需要控制权限的方法上面使用@PreAuthorize，@PreFilter这些注解
// 开启security方法级别的功能，prePostEnabled=true使得Controller中的@PreAuthorize生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
// 继承 WebSecurityConfigurerAdapter 类，并重写它的方法来设置一些web安全的细节
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomFilterSecurityInterceptor customFilterSecurityInterceptor;
	// 注入自定义的用户
	@Autowired
	private CustomUserDetailService customUserDetailService;

	// 注入BCryptPasswordEncoder进行密码加密
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
//		http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and()
//				.logout().permitAll();
//		http.addFilterBefore(new BeforeLoginFilter(), UsernamePasswordAuthenticationFilter.class);
//		http.addFilterAfter(new AfterLoginFilter(), UsernamePasswordAuthenticationFilter.class);
//		http.addFilterAt(new AtLoginFilter(), UsernamePasswordAuthenticationFilter.class);
		// 有默认的403，在error目录下
		// .and().exceptionHandling().accessDeniedPage("/403");
		http.authorizeRequests()
        .antMatchers("/", "index", "/login").permitAll()
        .anyRequest().authenticated()
        .and().formLogin()
        .loginPage("/login")
        //注销行为任意访问
        .and().logout().permitAll()
        .and().addFilterBefore(customFilterSecurityInterceptor, FilterSecurityInterceptor.class);
	}

	// 重写有关登录用户的相关配置
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 每次通过新创建BCryptPasswordEncoder，调用.encode()方法，对密码进行加密
		// auth.inMemoryAuthentication().passwordEncoder(new
		// BCryptPasswordEncoder()).withUser("u").password(new
		// BCryptPasswordEncoder().encode("u")).roles();
		// auth.inMemoryAuthentication().passwordEncoder(new
		// BCryptPasswordEncoder()).withUser("a").password(new
		// BCryptPasswordEncoder().encode("a")).roles();
		// 通过注入BCryptPasswordEncoder依赖，对密码进行加密
		// auth.inMemoryAuthentication().withUser("u").password(bCryptPasswordEncoder().encode("u")).roles("USER");
		// //.roles("ADMIN","USER"),为用户指定角色
		// auth.inMemoryAuthentication().withUser("a").password(bCryptPasswordEncoder().encode("a")).roles("ADMIN","USER");
		// 获取CustomUserDetailService返回的UserDetails，使用密码加密对输入的密码进行加密
		auth.userDetailsService(customUserDetailService).passwordEncoder(bCryptPasswordEncoder());
	}
}
