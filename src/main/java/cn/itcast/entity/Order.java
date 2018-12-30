package cn.itcast.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "t_orders", catalog = "jpa_springdata10000")
@Entity
public class Order implements Serializable {
	private static final long serialVersionUID = 2493396067792329482L;
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private Integer oid;
	private String name;
	@JoinColumn(name="customer_id")
	@ManyToOne(targetEntity=Customer.class)
	private Customer customer;

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Order [oid=" + oid + ", name=" + name + ", customer=" + customer + "]";
	}

}
