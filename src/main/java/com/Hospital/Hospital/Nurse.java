package com.Hospital.Hospital;
 
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.Nullable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity  
public class Nurse {
 
	@Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private @Nullable Integer id;
 
	@JsonProperty("full_name")
	private String fullName;
 
	@JsonProperty("user_name")
	private String userName;
 
	private String password;
 
	public Nurse() {
	}
 
	public int getId() {
		return id;
	}
 
	public void setId(int id) {
		this.id = id;
	}
 
	public String getFullName() {
		return fullName;
	}
 
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
 
	public String getUserName() {
		return userName;
	}
 
	public void setUserName(String userName) {
		this.userName = userName;
	}
 
	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
}
