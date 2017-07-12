package security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(AuthenticationManagerBuilder builder)
            throws Exception
    {
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
        security.ignoring().antMatchers("/resource/**");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security
                .authorizeRequests()
                	.antMatchers("/*").permitAll();
//                    .antMatchers("/captcha-image").permitAll()
//                    .antMatchers("/secure/**").hasAuthority("USER")
//                    .antMatchers("/admin/**").hasAuthority("ADMIN")
// //                   .anyRequest().authenticated()
//                .and().formLogin()
//                
//                    .loginPage("/tologin").failureUrl("/tologin")
//                    .defaultSuccessUrl("/thymeleaf/index")
//                    .usernameParameter("name")
//                    .passwordParameter("password")
//                    .permitAll();
////                .and().logout()
//                    .logoutUrl("/logout").logoutSuccessUrl("/login?loggedOut")
//                    .invalidateHttpSession(true).deleteCookies("JSESSIONID")
//                    .permitAll()
//                .and().sessionManagement()
//                    .sessionFixation().changeSessionId()
//                    .maximumSessions(1).maxSessionsPreventsLogin(true)
//                .and().and().csrf().disable();
    }
}
