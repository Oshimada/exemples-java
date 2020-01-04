package com.otakuma.data.admin;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "token")
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "tokenid")
	private Long tokenID;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "adminid")
	private Long adminID;
	
	private String pseudo;
	private String token;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Timestamp debut;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Timestamp fin;
	
	
	


	public Token(Long adminID, String pseudo, String token, Timestamp debut, Timestamp fin) {
		this.adminID = adminID;
		this.pseudo = pseudo;
		this.token = token;
		this.debut = debut;
		this.fin = fin;
	}



	public Token() {
	}
	


	public Long getTokenID() {
		return tokenID;
	}
	public void setTokenID(Long tokenID) {
		this.tokenID = tokenID;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Timestamp getDebut() {
		return debut;
	}
	public void setDebut(Timestamp debut) {
		this.debut = debut;
	}
	public Timestamp getFin() {
		return fin;
	}
	public void setFin(Timestamp fin) {
		this.fin = fin;
	}

	
	
	
}