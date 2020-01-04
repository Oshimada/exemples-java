package com.otakuma.data.admin;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "admin_role")
public class AdminRole  implements Serializable{

	private static final long serialVersionUID = 3081367540652108147L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "adminroleid")
	private Long adminRoleID;
	private String code;
	private String role;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	  name = "admin_roledroit", 
	  joinColumns = @JoinColumn(name = "adminroleid"), 
	  inverseJoinColumns = @JoinColumn(name = "admindroitid"))
	private Set<AdminDroit> droits;
	
	public Long getAdminRoleID() {
		return adminRoleID;
	}
	public void setAdminRoleID(Long adminRoleID) {
		this.adminRoleID = adminRoleID;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Set<AdminDroit> getDroits() {
		return droits;
	}
	public void setDroits(Set<AdminDroit> droits) {
		this.droits = droits;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "AdminRole [adminRoleID=" + adminRoleID + ", code=" + code + ", role=" + role + ", droits=" + droits
				+ "]";
	}

	

}
