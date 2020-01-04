package com.otakuma.data.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "admin")
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "adminid")
	private Long adminID;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "adminroleid")
	private Long adminRoleID;
	
	@ManyToOne
	@JoinColumn(name="adminroleid", referencedColumnName = "adminroleid", insertable = false, updatable = false)    
    private AdminRole adminrole;
	
	private String nom;
	private String prenom;
	private String email;
	private String CIN;
	
	public Long getAdminID() {
		return adminID;
	}
	public void setAdminID(Long adminID) {
		this.adminID = adminID;
	}
	public Long getAdminRoleID() {
		return adminRoleID;
	}
	public void setAdminRoleID(Long adminRoleID) {
		this.adminRoleID = adminRoleID;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCIN() {
		return CIN;
	}
	public void setCIN(String cIN) {
		CIN = cIN;
	}

	
}
