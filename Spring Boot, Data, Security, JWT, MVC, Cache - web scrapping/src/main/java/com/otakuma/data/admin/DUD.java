package com.otakuma.data.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name ="droitusedroit")
@Deprecated
/** utilisée uniquement dans les jointures des requetes natives */
public class DUD  implements Serializable{

	private static final long serialVersionUID = 112L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "dudid")
	private Long dudID;
	@Column(name = "admindroitid")
	private Long adminDroitID;
	@Column(name = "useddroitid")
	private Long usedDroitID;
	
	
	
	public Long getDudID() {
		return dudID;
	}
	public void setDudID(Long dudID) {
		this.dudID = dudID;
	}
	public Long getAdminDroitID() {
		return adminDroitID;
	}
	public void setAdminDroitID(Long adminDroitID) {
		this.adminDroitID = adminDroitID;
	}
	public Long getUsedDroitID() {
		return usedDroitID;
	}
	public void setUsedDroitID(Long usedDroitID) {
		this.usedDroitID = usedDroitID;
	}
	

}