package com.jb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	/*help to know which user us it company or customer*/
	@Any(metaColumn = @Column(name="role") )
	@AnyMetaDef(idType = "long",metaType = "int"
	,metaValues = {
			@MetaValue(value = "1",targetEntity = Customer.class),
			@MetaValue(value = "2",targetEntity = Company.class)
	})
	//not forging key
	@JoinColumn(name = "client_id")
	private Client client;
	
	public User(){
		
	}
	
	public User(@Qualifier Client client) {
		if (client instanceof Customer) {
			this.client = (Customer)client;
		}
		else {
		this.client = (Company)client;
		}
		/*Empty*/
	}
	@Autowired
	public User(Customer customer) {
		this.client = customer;
	}
	@Autowired
	public User(Company company) {
		this.client = company;
	}
//	public User(long id, String email, String password, Client client) {
//		this.id = id;
//		this.email = email;
//		this.client = client;
//	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@JsonIgnore
	public void setClient(Client client) {
		this.client = client;
	}
	@JsonIgnore
	public Client getClient() {
		return client;
	}

	
	
	
	
	
	
	
	
	
}
