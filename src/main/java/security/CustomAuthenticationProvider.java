package security;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import pojo.User;
import service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationManager {

	
	@Autowired
	private UserService userService;
		
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		User user = userService.findUserByNameAndPassword(authentication.getName(),authentication.getCredentials().toString());

		if (user!=null) {
			 return new UsernamePasswordAuthenticationToken(authentication.getName(),user, authorities);
		 }
		 
		throw new BadCredentialsException("您输入的用户名或密码不正确");
	}

}
