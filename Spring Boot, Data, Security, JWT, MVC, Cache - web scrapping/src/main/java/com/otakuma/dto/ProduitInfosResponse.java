package com.otakuma.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProduitInfosResponse {

	private String nom;
	private String code;
	private String categorieNom;
	private String themeNom;
	
	
	public ProduitInfosResponse(String nom, String code, String categorieNom, String themeNom) {

		this.nom = nom;
		this.code = code;
		this.categorieNom = categorieNom;
		this.themeNom = themeNom;
	}
	
	public ProduitInfosResponse() {
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getCategorieNom() {
		return categorieNom;
	}

	public void setCategorieNom(String categorieNom) {
		this.categorieNom = categorieNom;
	}

	public String getThemeNom() {
		return themeNom;
	}

	public void setThemeNom(String themeNom) {
		this.themeNom = themeNom;
	}
	
}
