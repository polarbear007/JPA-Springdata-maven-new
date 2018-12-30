package cn.itcast.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name="t_customers", catalog="jpa_springdata10000")
@Entity
public class Customer implements Serializable {
	private static final long serialVersionUID = -1765874101816408934L;
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private Integer cid;
	private String cname;
	private Date birth;
	
	
	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Temporal(TemporalType.DATE)
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		return "Customer [cid=" + cid + ", cname=" + cname + ", birth=" + birth + "]";
	}
	
	
}
