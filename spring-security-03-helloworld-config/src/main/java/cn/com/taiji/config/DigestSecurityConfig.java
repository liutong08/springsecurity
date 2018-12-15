package cn.com.taiji.config;//package cn.com.taiji.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@EnableWebSecurity
public class DigestSecurityConfig extends WebSecurityConfigurerAdapter {

    public final static String REALM = "MY_REALM";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//禁用csrf
        http.csrf().disable()
                .authorizeRequests()
                 //对所有请求认证
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint(getDigestEntryPoint())
                .and().addFilter(getDigestAuthenticationFilter());
    }

    /**
     * 认证信息管理
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("123456").roles("USER")
                .and()
                .withUser("admin").password("123456").roles("USER", "ADMIN");
    }

    //digest认证
    //AuthenticationEntryPoint将用户定向到认证的入口，收集认证信息。
    //DigestProcessingFilterEntryPoint
    //和Basic认证类似，会向浏览器发送一个RFC2616标准的digest认证头信息。浏览器在收到认证头后，会弹出对话框，收集用户名和密码。
    //浏览器会将收集到的用户名和密码，打包成RFC标准的认证响应，发送到服务器。在下一次请求到达DigestProcessingFilter时，过滤器会提取认证信息，并由AuthenticationManager处理认证。
    @Bean
    public DigestAuthenticationEntryPoint getDigestEntryPoint() {
        DigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
        digestAuthenticationEntryPoint.setKey("mykey");
        digestAuthenticationEntryPoint.setNonceValiditySeconds(120);
        digestAuthenticationEntryPoint.setRealmName(REALM);
        return digestAuthenticationEntryPoint;
    }

    @Bean
    public DigestAuthenticationFilter getDigestAuthenticationFilter() throws Exception {
        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
        digestAuthenticationFilter.setAuthenticationEntryPoint(getDigestEntryPoint());
        digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
        return digestAuthenticationFilter;
    }
}
