package pojo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import controller.validation.UserLoginValidGroup;
import controller.validation.UserRegisterValidGroup;

public class User {
	private String userId;

	@Size(min=2,max=30,message="{user.name.length.error}",groups=UserLoginValidGroup.class)
	@NotBlank(message="{user.name.isnull}",groups=UserLoginValidGroup.class)
    private String name;
	
	private String nickname;
	
	@NotBlank(message="{user.password.isnull}",groups=UserLoginValidGroup.class)
	private String password;

    private String sex;

    private Integer age;
    
    @NotNull(message="{user.birthday.isnull}",groups=UserRegisterValidGroup.class)
    private Date birthday;
    
    private String telephone;
    
    private String email;
    
    private String pic;
    
    private List<Discussion> discussions;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public List<Discussion> getDiscussions() {
		return discussions;
	}

	public void setDiscussions(List<Discussion> discussions) {
		this.discussions = discussions;
	}
    
    
    
}
