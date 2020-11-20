package com.smartcontact.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	private boolean userStatus;
	private String userName;
	@Column(unique = true)
	private String userEmail;
	private String userDesc;
	private String userPassword;
	private String userProfile;
	private String userRole;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private List<Contact> contacts=new ArrayList<Contact>();
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return userId;
	}
	public void setId(int id) {
		this.userId = id;
	}
	public boolean isStatus() {
		return userStatus;
	}
	public void setStatus(boolean status) {
		this.userStatus = status;
	}
	public String getUserName() {
		return userName;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userStatus=" + userStatus + ", userName=" + userName + ", userEmail="
				+ userEmail + ", userDesc=" + userDesc + ", userPassword=" + userPassword + ", userProfile="
				+ userProfile + ", userRole=" + userRole + " ]";
	}

	public boolean isUserStatus() {
		return userStatus;
	}
	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	public User(int id, boolean status, String userName, String userEmail, String userDesc, String userPassword,
			String userProfile, String userRole) {
		super();
		this.userId = id;
		this.userStatus = status;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userDesc = userDesc;
		this.userPassword = userPassword;
		this.userProfile = userProfile;
		this.userRole = userRole;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserDesc() {
		return userDesc;
	}
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	

}
