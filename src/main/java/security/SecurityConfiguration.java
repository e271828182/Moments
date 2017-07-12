package security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@ComponentScan
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Override
    public void configure(WebSecurity security)
    {
        security.ignoring().antMatchers("/css/**","/js/**,/img/**");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security
                .authorizeRequests()
                	.antMatchers("/","/login").permitAll()
                	.antMatchers("/captcha-image").permitAll()
                	.anyRequest().authenticated()
                .and().formLogin()
                	.loginPage("/toLogin").defaultSuccessUrl("/").failureUrl("/login").permitAll().and().httpBasic()
                .and().logout()
                	.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll()
                .and().sessionManagement()
                	.sessionFixation().changeSessionId().maximumSessions(1).expiredUrl("/")
                .and()
                	.and().csrf().disable();
                	
   
    }
}
