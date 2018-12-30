package cn.itcast.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name = "t_student", catalog = "jpa_springdata10000")
@Entity
public class Student implements Serializable {
	private static final long serialVersionUID = -5304386891883937131L;

	// 声明一个枚举类型
	public static enum GenderType {
		M, F;
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer sid;
	private String name;
	@Temporal(TemporalType.DATE)
	private Date birthday;
	@Enumerated(EnumType.STRING)
	private GenderType gender;

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Student [sid=" + sid + ", name=" + name + ", birthday=" + birthday + ", gender=" + gender + "]";
	}

}
