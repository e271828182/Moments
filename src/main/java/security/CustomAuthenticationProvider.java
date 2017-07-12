package security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import mappers.UserMapper;
import pojo.User;
import service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationManager {

	static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
	
	static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
}
	
	@Resource
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		System.out.println(".........."+userMapper);
		System.out.println("++++++++"+userService);
		System.out.println("=========="+authentication);
		System.out.println("--------------"+authentication.getCredentials());
		
		User user = userService.findUserByNameAndPassword(authentication.getName(),authentication.getCredentials().toString());

		if (user!=null) {
			 return new UsernamePasswordAuthenticationToken(authentication.getName(),user, AUTHORITIES);
		 }
		 
		throw new BadCredentialsException("Bad Credentials");
	}

}
