package cn.com.taiji.config;//package cn.com.taiji.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

/**
 * Spring Security 配置类.
 */
@EnableWebSecurity(debug=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        		// 都可以访问
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/login").permitAll()
                // 需要相应的角色才能访问
                .antMatchers("/users/**").hasRole("USER")
                // 需要相应的角色才能访问
                .antMatchers("/admins/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                //基于 Form 表单登录验证
                .formLogin()
                .loginPage("/login").failureUrl("/login-error");
    }

    @Autowired
    private DataSource dataSource;

    /**
     * 认证信息管理
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //使用mysql数据库中的数据进行登录
    	auth.jdbcAuthentication().dataSource(dataSource);
    }
}
