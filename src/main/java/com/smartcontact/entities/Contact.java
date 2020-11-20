package com.smartcontact.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONTACT")
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int contactId;
	
	public int getContactId() {
		return contactId;
	}
	

	@Override
	public String toString() {
		return "Contact [contactId=" + contactId + ", contactName=" + contactName + ", contactNumber=" + contactNumber
				+ ", contactEmail=" + contactEmail + ", contactNickName=" + contactNickName + ", contactProfile="
				+ contactProfile + ", contactWork=" + contactWork + ", contactDesc=" + contactDesc + ", user=" + user
				+ "]";
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public Contact(int contactId, String contactName, String contactNumber, String contactEmail, String contactNickName,
			String contactProfile, String contactWork, String contactDesc) {
		super();
		this.contactId = contactId;
		this.contactName = contactName;
		this.contactNumber = contactNumber;
		this.contactEmail = contactEmail;
		this.contactNickName = contactNickName;
		this.contactProfile = contactProfile;
		this.contactWork = contactWork;
		this.contactDesc = contactDesc;
	}
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactNickName() {
		return contactNickName;
	}
	public void setContactNickName(String contactNickName) {
		this.contactNickName = contactNickName;
	}
	public String getContactProfile() {
		return contactProfile;
	}
	public void setContactProfile(String contactProfile) {
		this.contactProfile = contactProfile;
	}
	public String getContactWork() {
		return contactWork;
	}
	public void setContactWork(String contactWork) {
		this.contactWork = contactWork;
	}
	public String getContactDesc() {
		return contactDesc;
	}
	public void setContactDesc(String contactDesc) {
		this.contactDesc = contactDesc;
	}
	private String contactName;
	@Column(length = 10)
	private String contactNumber;
	private String contactEmail;
	private String contactNickName;
	private String contactProfile;
	private String contactWork;
	@Column(length = 5000)
	private String contactDesc;
	@ManyToOne
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
