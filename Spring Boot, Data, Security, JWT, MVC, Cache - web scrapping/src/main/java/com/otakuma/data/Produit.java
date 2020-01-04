package com.otakuma.data;
import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


@Entity
@Table(name = "produit")
public class Produit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3527012924099773293L;

	@Id
	@JsonProperty(access = Access.WRITE_ONLY)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "produitid")
	protected long produitID;
/*
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "themeid")
	protected Long themeID;
*/
	@ManyToOne
	@JoinColumn(name="themeid", referencedColumnName = "themeid", insertable = false, updatable = false)    
    protected Theme theme;
	
	@ManyToOne
	@JoinColumn(name="categorieid", referencedColumnName = "categorieid", insertable = false, updatable = false)    
    protected Categorie categorie;

	protected String code;
	protected String nom;
	protected String keywords;
	
	@Column(name = "shortdescription")
	protected String shortDescription;
	
	protected String description;
	
	@Column(name = "isactive")
	protected Boolean isActive;
	
	/* images */
	protected String thumbnail;
	protected String image1;
	protected String image2;
	protected String image3;
	protected String extra1;
	protected String extra2;
	protected String extra3;
	
	/* prix */
	@Column(name = "prixunite")
	protected Double prixUnite;
	
	@Column(name = "prixpromo")
	protected Double prixPromo;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "datedebutpromo")
	protected Date dateDebutPromo;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "datefinpromo")
	protected Date dateFinPromo;
	@Transient
	protected String stringDateDebutPromo;
	@Transient
	protected String stringDateFinPromo;
	

	@Column(name = "qte", updatable = true, insertable = false)
	protected Long QTE;
	
	@Column(name = "pendingqte", insertable = false)
	protected Long pendingQte;
	
	@Column(name = "lockedqte", updatable = true, insertable = false)
	protected Long lockedQte;

	@Column(name = "hasvariations")
	protected Boolean hasVariations;
	


	public Long getProduitID() {
		return produitID;
	}
	
	public Long getQte() {
		return QTE;
	}
	
	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public void setQte(Long qTE	) {
		QTE = qTE;
	}
	public Long getLockedQte() {
		return lockedQte;
	}


	public void setLockedQte(Long lockedQte) {
		this.lockedQte = lockedQte;
	}

	public Long getPendingQte() {
		return pendingQte;
	}

	public void setPendingQte(Long pendingQte) {
		this.pendingQte = pendingQte;
	}
	@Transient
	public String getStringDateDebutPromo() {
		return stringDateDebutPromo;
	}

	public void setStringDateDebutPromo(String stringDateDebutPromo) 
	{
		this.stringDateDebutPromo = stringDateDebutPromo;
		
		//if(dateDebutPromo == null && stringDateDebutPromo != null)
		//	setDateDebutPromo(new Parse().ParseDate(stringDateDebutPromo) );
	}

	@Transient
	public String getStringDateFinPromo() {
		return stringDateFinPromo;
	}

	public void setStringDateFinPromo(String stringDateFinPromo) 
	{
		this.stringDateFinPromo = stringDateFinPromo;

		//if(dateFinPromo == null && stringDateFinPromo != null)
		//	setDateFinPromo(new Parse().ParseDate(stringDateFinPromo) );
	}
	public Date getDateDebutPromo() {
		return dateDebutPromo;
	}

	public void setDateDebutPromo(Date dateDebutPromo) {
		this.dateDebutPromo = dateDebutPromo;
		//if(dateDebutPromo != null)
		//	stringDateDebutPromo = new Parse().dateToString(dateDebutPromo);
	}
	
	public Date getDateFinPromo() {
		return dateFinPromo;
	}

	public void setDateFinPromo(Date dateFinPromo) {
		this.dateFinPromo = dateFinPromo;
		//if(dateFinPromo != null)
		//	stringDateFinPromo = new Parse().dateToString(dateFinPromo);
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getShortDescription() {
		if(shortDescription == null)
			return "";
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDescription() {
		if(description == null) return null;
		return description.replace("\r\n", "<br/>");
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public Double getPrixUnite() {
		return prixUnite;
	}

	public void setPrixUnite(Double prixUnite) {
		this.prixUnite = prixUnite;
	}

	public Double getPrixPromo() {
		return prixPromo;
	}

	public void setPrixPromo(Double prixPromo) {
		this.prixPromo = prixPromo;
	}

	public String getExtra1() {
		return extra1;
	}

	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}

	public String getExtra2() {
		return extra2;
	}

	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}

	public String getExtra3() {
		return extra3;
	}

	public void setExtra3(String extra3) {
		this.extra3 = extra3;
	}
	
	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	/*
	public Long getThemeID() {
		return themeID;
	}

	public void setThemeID(Long themeID) {
		this.themeID = themeID;
	}
*/
	public void setProduitID(long produitID) {
		this.produitID = produitID;
	}

	public Boolean getHasVariations() {
		return hasVariations;
	}

	public void setHasVariations(Boolean hasVariations) {
		this.hasVariations = hasVariations;
	}

}
