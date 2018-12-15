//package cn.com.taiji.config;//package cn.com.taiji.config;
//
//import cn.com.taiji.support.CustomAuthenticationProvider;
////import cn.com.taiji.support.CustomUserDetailsService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
////import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
////import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
///**
// * Spring Security 配置类.
// * 使用该配置类user 123456 可登录
// * admin 123456 不可登录
// */
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    /**
//     * 自定义配置
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//        		// 都可以访问
//                .antMatchers("/css/**", "/js/**", "/fonts/**", "/login").permitAll()
//                // 需要相应的角色才能访问
//                .antMatchers("/users/**").hasRole("USER")
//                // 需要相应的角色才能访问
//                .antMatchers("/admins/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                //基于 Form 表单登录验证
//                .formLogin()
//                .loginPage("/login").failureUrl("/login-error");
//    }
//
//    @Autowired
//    private CustomAuthenticationProvider customAuthenticationProvider;
////
////    @Autowired
////    private CustomUserDetailsService userDetailsService;
//
//    /**
//     * 认证信息管理
//     *
//     * @param builder
//     * @throws Exception
//     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
//        builder.authenticationProvider(customAuthenticationProvider);
////        builder.userDetailsService(userDetailsService);
//    }
//}
