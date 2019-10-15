package com.jb.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*Entity help to spring to know that is a Table already and make him in DB*/
@Entity
/* Name of Table */
@Table(name = "coupon")
public class Coupon {

	@Id
	/*
	 * Helps spring to know what this may do and make him unique saved him as a key
	 * and value
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/* Column with the name of field */
	@Column(name = "id")
	private long id;

	// Fields

	// Default argument for filed

	// make connection with company with names company_id and coupon id (in company)
	// want only DETACH and REFRESH because i don't want to influe on company
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	/* The join column make the connection */
	@JoinColumn(name = "user_id")
	private Company company;

	/*
	 * Tell spring about the relation of Fields in Entity's also tell spring thats
	 * is a List help control the changes in data and Lazy loading
	 */
	@ManyToMany(mappedBy = "coupons", cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	private List<Customer> customers;

	@Column(name = "title")
	private String title;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "endDate")
	private Date endDate;

	@Column(name = "category")
	private int category;

	@Column(name = "amount")
	private int amount;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private double price;

	@Column(name = "image")
	private String image;

	// Constructor initializing List
	public Coupon() {
		customers = new ArrayList<>();
	}

	// Constructor initializing Fields
	public Coupon(String title, Date startDate, Date endDate, int category, int amount, String description,
			double price, String image, Company company) {
		this();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.category = category;
		this.amount = amount;
		this.description = description;
		this.price = price;
		this.image = image;
		this.company = company;

	}

	// Getters & Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Company getCompany() {
		return company;
	}

	/* When we get a Company we don't want to her all coupons to */
	// help to read json and to save it in db if not the set will be in endless loop
	@JsonIgnore
	public void setCompany(Company company) {
		this.company = company;
	}

	@JsonIgnore
	public List<Customer> getCustomers() {
		return customers;
	}

	/* Function to decrees the amount of coupon */
	public void deacreseAmount() {
		--this.amount;
	}

	/* Function for adding Coupon to Customer */
	public void addCustomer(Customer customer) {
		customers.add(customer);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", category=" + category + ", amount=" + amount + ", description=" + description + ", price=" + price
				+ ", image=" + image + "]";
	}

}
