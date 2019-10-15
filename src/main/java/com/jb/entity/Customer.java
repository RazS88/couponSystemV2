package com.jb.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*Entity help to spring to know that is a Table already and make him in DB*/
@Entity
/* Name of Table */
@Table(name = "customer")
/* Extends Client */
public class Customer extends Client {
	// Fields

	// Default argument for filed
	/* Column with the name of field */
	@Column(name = "name")
	private String name;

	/*
	 * Tell spring about the relation of Fields in Entity's also tell spring thats
	 * is a List help control the changes in data and Lazy loading
	 */
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	/*
	 * Inner Join of Coupon and Customer with Fields of ID userID we got in
	 * inheritance from Client
	 */
	@JoinTable(name = "customer_coupon", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "coupon_id"))
	private List<Coupon> coupons;

//Constructor initializing List
	public Customer() {
		coupons = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* When we get a Company we don't want to her all coupons to */
//help to read json and to save it in db if not the set will be in endless loop
	@JsonIgnore
	public List<Coupon> getCoupons() {
		return coupons;
	}

	/* Helps to add Coupon for specific customer */
	public void addCoupon(Coupon coupon) {
		coupon.addCustomer(this);
		/* Use the Function from Coupon to set it After Purchase by Customer */
		coupon.deacreseAmount();
		this.coupons.add(coupon);
	}

}
