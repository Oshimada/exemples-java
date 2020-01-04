package ma.otaku.data.stock;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ma.otaku.data.TableWithCode;
import ma.otaku.data.TableWithQte;
import ma.otaku.utils.Parse;

@Entity
@Table(name="sku")
public class StockDB implements TableWithCode, TableWithQte{

	@JsonProperty(access = Access.WRITE_ONLY)
	private long skuID;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long achatStockItemID;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long variationID;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Date dateInsertion;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long authAdminID;
	
	private String achatStockItemCode;
	private String variationCode;
	private String authAdminNom;
	
	private String description;
	private Long QTE;
	private Boolean isActive;
	private String code;
	private String motif;
	private String dateInsertionText;
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getSkuID() {
		return skuID;
	}
	public void setSkuID(long skuID) {
		this.skuID = skuID;
	}
	public Long getVariationID() {
		return variationID;
	}
	public void setVariationID(Long variationID) {
		this.variationID = variationID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	@Column(insertable = true)
	public Long getQte() {
		return QTE;
	}
	@Override
	public void setQte(Long qTE) {
		QTE = qTE;
	}

	public String getMotif() {
		return motif;
	}
	public void setMotif(String motif) {
		this.motif = motif;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Column(insertable = false, updatable = false)
	public Date getDateInsertion() {
		return dateInsertion;
	}
	public void setDateInsertion(Date dateInsertion) {
		this.dateInsertion = dateInsertion;
	}
	public Long getAchatStockItemID() {
		return achatStockItemID;
	}
	public void setAchatStockItemID(Long achatStockItemID) {
		this.achatStockItemID = achatStockItemID;
	}

	@Transient
	public String getAchatStockItemCode() {
		return achatStockItemCode;
	}
	public void setAchatStockItemCode(String achatStockItemCode) {
		this.achatStockItemCode = achatStockItemCode;
	}
	@Transient
	public String getVariationCode() {
		return variationCode;
	}
	public void setVariationCode(String variationCode) {
		this.variationCode = variationCode;
	}
	@Transient
	public String getDateInsertionText() {
		if(dateInsertionText == null) dateInsertionText = new Parse().dateToString(dateInsertion);
		return dateInsertionText;
	}
	public void setDateInsertionText(String dateInsertionText) {
		this.dateInsertionText = dateInsertionText;
	}
	@Transient
	public String getAuthAdminNom() {
		return authAdminNom;
	}
	public void setAuthAdminNom(String authAdminNom) {
		this.authAdminNom = authAdminNom;
	}
	
	public Long getAuthAdminID() {
		return authAdminID;
	}
	public void setAuthAdminID(Long authAdminID) {
		this.authAdminID = authAdminID;
	}
	
}
