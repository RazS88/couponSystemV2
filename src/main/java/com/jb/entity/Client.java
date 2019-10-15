package com.jb.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*helps spring boot to know that Client is an abstract class not an Entity*/
@MappedSuperclass

@JsonIgnoreProperties("hibernateLazyIntializer")
public abstract class Client {
	
	//Fields
	
		//Default argument for filed 
	
	/*this annotated "ID" tells spring and  jpa repository in future that is unique field */
	@Id
	/*
	 * Helps spring to know what this may do and make him unique saved him as a key
	 * and value
	 */
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	public Client() {
	}
	
	public Client(long id) {
		this.id = id;
	}

	//Getters
	public long getId() {
		return id;
	}
	//Setters
	public void setId(long id) {
		this.id = id;
	}

}
