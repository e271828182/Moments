package pojo;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import controller.validation.ValidGroupOne;

public class User {
	private Integer id;

	@Size(min=2,max=30,message="{user.name.length.error}",groups=ValidGroupOne.class)
    private String name;

    private String sex;

    private Integer age;
    
    @NotNull(message="{user.birthday.isnull}",groups=ValidGroupOne.class)
    private Date birthday;
    
    private String pic;
    
    

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
    
    
}
