package com.otakuma.data.admin;

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
@Table(name ="admin_droit")
public class AdminDroit {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "admindroitid")
	private Long id;
	private String code;
	private String droit;
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	  name = "droitusedroit", 
	  joinColumns = @JoinColumn(name = "admindroitid"), 
	  inverseJoinColumns = @JoinColumn(name = "useddroitid"))
	private Set<AdminDroit> droitsfils;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long adminDroitID) {
		this.id = adminDroitID;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDroit() {
		return droit;
	}
	public void setDroit(String droit) {
		this.droit = droit;
	}
	public Set<AdminDroit> getDroitsfils() {
		return droitsfils;
	}
	public void setDroitsfils(Set<AdminDroit> droitsfils) {
		this.droitsfils = droitsfils;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "\n{\nadminDroitID=" + id + 
				"\ncode=" + code + 
				"\ndroit=" + droit + 
				"\ndescription=" + description + 
				"\ndroitsfils=" + droitsfils + "}";
	}
	

	
	
}
