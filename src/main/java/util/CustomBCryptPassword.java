package util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomBCryptPassword {
	
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	private String username;
	
	private CustomBCryptPassword(){
		
	}
	
	private CustomBCryptPassword(String username){
		this.bcryptPasswordEncoder = new BCryptPasswordEncoder(5);
		this.username = username;
	}
	
	public static CustomBCryptPassword getInstance(String username){
		return new CustomBCryptPassword(username);
	}

	public String encode(CharSequence rawPassword) {
		return this.bcryptPasswordEncoder.encode(rawPassword+this.username);
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return this.bcryptPasswordEncoder.matches(rawPassword+this.username, encodedPassword);
	}

}
