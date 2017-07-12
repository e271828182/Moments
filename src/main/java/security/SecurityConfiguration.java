package security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@ComponentScan
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
   
	@Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder
                .inMemoryAuthentication()
                        .withUser("好汉1")
                        .password("123")
                        .authorities("USER")
                    .and()
                        .withUser("好汉2")
                        .password("123")
                        .authorities("USER", "ADMIN");
        
    	
    }

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
