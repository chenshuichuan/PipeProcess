package llcweb.config;

import llcweb.service.impl.LoginServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：ricardo
 * Date：2018/8/10
 * Time：22:55
 * ========================
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //完成自定义认证实体注入
    @Bean
    UserDetailsService userService() {
        return new LoginServiceImpl();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService());//添加自定义的userDetailsService认证
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring()
                .antMatchers("/assets/**")
                .antMatchers("/custom/**")
                .antMatchers("/iframe/**")
                .antMatchers("/fortable/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable();

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/index", "/message/").permitAll()   //定义不需要认证就可以访问的URL
                .anyRequest().authenticated()//所有请求必须登陆后访问
                .and()
                .formLogin()
                .loginPage("/login")
                //设置默认登录成功跳转页面
                .defaultSuccessUrl("/index").failureUrl("/login?error").permitAll()
                //开启cookie保存用户数据
                .and()
                .rememberMe()
                //设置cookie有效期
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .and()
                .logout()
                .logoutUrl("/logout")
                //设置注销成功后跳转页面，默认是跳转到登录页面
                .logoutSuccessUrl("/login?logout")
                .permitAll();//注销请求可直接访问

    }
}
