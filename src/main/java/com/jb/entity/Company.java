package com.jb.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*Entity help to spring to know that is a Table already and make him in DB*/
@Entity
/*Name of Table*/
@Table(name = "company")
/*Extends Client*/
public class Company extends Client {
	//Fields
	
	//Default argument for filed 
/*Column with the name of field*/	
	@Column(name="name")
	private String name;
	
	/*Tell spring about the relation of Fields in Entity's
	 *  also tell spring thats is a Field
	 *  and make the connection more relative
	 *     and  Lazy loading  */
	@OneToMany(mappedBy = "company" , cascade = CascadeType.ALL)
	private List<Coupon> coupons;
	
	//Constructor initializing List
	public Company() {
		coupons = new ArrayList<>();
	}
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/* When we get a Company we don't want to her all coupons to */
	// help to read json and to save it in db if not the set will be in endless loop
	@JsonIgnore
	public List<Coupon> getCoupons() {
		return coupons;
	}
	
/*	Helps to add Coupon specific Company */
	public void add(Coupon coupon) {
		coupon.setCompany(this);
		coupons.add(coupon);
	}
	
	
	

}
