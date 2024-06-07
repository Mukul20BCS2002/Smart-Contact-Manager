package com.smart.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	
	@NotBlank(message = "First Name can not be empty!!")
	@Size(min=2,max = 20,message = "min 2 and max 20 characters are allowed!!")
	private String fname;
	
	@NotBlank(message = "First Name can not be empty!!")
	@Size(min=2,max = 20,message = "min 2 and max 20 characters are allowed!!")
	private String lname;
	
	private String work;
	
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	
	@Pattern(regexp="(^$|[0-9]{10})")
	private String phone;
	
	@Column(length = 500)
	@Size(min=10,max = 500,message = "min 10 and max 500 characters are allowed!!")
	private String description;
	
	@ManyToOne
	@JsonIgnore
	private User user;

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contact(int cid, String fname, String lname, String work, String email, String phone, String description,
			User user) {
		super();
		this.cid = cid;
		this.fname = fname;
		this.lname = lname;
		this.work = work;
		this.email = email;
		this.phone = phone;
		this.description = description;
		this.user = user;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Contact [cid=" + cid + ", fname=" + fname + ", lname=" + lname + ", work=" + work + ", email=" + email
				+ ", phone=" + phone + ", description=" + description + ", user=" + user + "]";
	}
	
	
	
	
	
}
