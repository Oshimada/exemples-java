package com.otakuma.data.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "admin_login")
public class AdminLogin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "adminloginid")
	private Long id;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long adminID;
	
	private String pseudo;
	private String password;
	private String question;
	private String reponse;

	public Long getId() {
		return id;
	}

	public void setId(Long adminLoginID) {
		this.id = adminLoginID;
	}

	public Long getAdminID() {
		return adminID;
	}

	public void setAdminID(Long adminID) {
		this.adminID = adminID;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
}
