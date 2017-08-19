package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.kls.valuetypes.DayStatus;
import com.vsoftcorp.kls.valuetypes.PacsStatus;
import com.vsoftcorp.kls.valuetypes.VoucherResetFrequency;
import com.vsoftcorp.kls.valuetypes.transaction.PacsGLExtractUpload;

@TypeDefs({
		@TypeDef(name = "voucherResetFrequency", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.VoucherResetFrequency"),
				@Parameter(name = "identifierMethod", value = "getValue"), @Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "gLExtractUpload", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.transaction.PacsGLExtractUpload"),
				@Parameter(name = "identifierMethod", value = "getValue"), @Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "pacsStatus", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
					@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.PacsStatus"),
					@Parameter(name = "identifierMethod", value = "getValue"), @Parameter(name = "convertIdentifierMethod", value = "getType"),
					@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "dayStatus", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.DayStatus"),
				@Parameter(name = "identifierMethod", value = "getValue"), @Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") })})
@Entity
@Table(name = "pacs")
public class Pacs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6878247169387951952L;

	public Pacs() {
	}

	@Id
	@GeneratedValue(generator = "pacsIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "pacsIdSequence", sequenceName = "pacs_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	@Basic
	@Column(name = "name", length = 45)
	private String name;

	@ManyToOne(optional = false)
	@JoinColumn(name = "village_id", referencedColumnName = "id")
	private Village village;

	@Basic
	@Column(name = "address1", length = 60)
	private String location;

	@Basic
	@Column(name = "secretary_name", length = 45)
	private String secretaryName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "branch_id", referencedColumnName = "id")
	private Branch branch;

	@Basic
	@Column(name = "pacs_bank_acc_number", length = 20)
	private String pacsBankAccNumber;

	@Basic
	@Column(name = "cash_gl", length = 18)
	private String cashGl;

	@Basic
	@Column(name = "margin_gl", length = 18)
	private String marginGl;

	@Basic
	@Column(name = "cash_in_transit_gl", length = 18)
	private String cashInTransitGL;
	
	@Basic
	@Column(name = "land_validation", length = 1)
	private String landValidation;

	@Basic
	@Column(name = "gl_extract_upload", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "gLExtractUpload")
	private PacsGLExtractUpload pacsGlExtarctUpload;

	@Basic
	@Column(name = "is_borrowing_trans_required_in_gl_extract", length = 1)
	private String isBorrwingTransRequiredInGLExtract;

	@Basic
	@Column(name = "is_interest_paid_editable")
	private boolean isInterestPaidEditable;
	
	@Basic
	@Column(name="cash_in_transit_accno")
	private String cashInTransitAccNo;
	
	@Basic
	@Column(name="pacs_status")
	@Enumerated(EnumType.STRING)
	@Type(type="pacsStatus")
	private PacsStatus pacsStatus;
	
	
	@Basic
	@Column(name="day_status")
	@Enumerated(EnumType.STRING)
	@Type(type="dayStatus")
	private DayStatus dayStatus;
	
	/**
	 * @return the marginGl
	 */
	public String getMarginGl() {
		return marginGl;
	}

	/**
	 * @param marginGl
	 *            the marginGl to set
	 */
	public void setMarginGl(String marginGl) {
		this.marginGl = marginGl;
	}

	@Basic
	@Column(name = "voucher_reset_frequency", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "voucherResetFrequency")
	private VoucherResetFrequency voucherResetFrequency;

	/**
	 * @return the voucherResetFrequency
	 */
	public VoucherResetFrequency getVoucherResetFrequency() {
		return voucherResetFrequency;
	}

	/**
	 * @param voucherResetFrequency
	 *            the voucherResetFrequency to set
	 */
	public void setVoucherResetFrequency(VoucherResetFrequency voucherResetFrequency) {
		this.voucherResetFrequency = voucherResetFrequency;
	}

	@Basic
	@Column(name = "inventory_implemented", length = 3)
	private String inventoryImplemented;

	/**
	 * @return the inventoryImplemented
	 */
	public String getInventoryImplemented() {
		return inventoryImplemented;
	}

	/**
	 * @param inventoryImplemented
	 *            the inventoryImplemented to set
	 */
	public void setInventoryImplemented(String inventoryImplemented) {
		this.inventoryImplemented = inventoryImplemented;
	}

	@Basic
	@Column(name = "bank_pacs_acc_number", length = 20)
	private String bankPacsAccNumber;

	/**
	 * @return the bankPacsAccNumber
	 */
	public String getBankPacsAccNumber() {
		return bankPacsAccNumber;
	}

	/**
	 * @param bankPacsAccNumber
	 *            the bankPacsAccNumber to set
	 */
	public void setBankPacsAccNumber(String bankPacsAccNumber) {
		this.bankPacsAccNumber = bankPacsAccNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pacs other = (Pacs) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public String getLandValidation() {
		return landValidation;
	}

	public void setLandValidation(String landValidation) {
		this.landValidation = landValidation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCashInTransitGL() {
		return cashInTransitGL;
	}

	public void setCashInTransitGL(String cashInTransitGL) {
		this.cashInTransitGL = cashInTransitGL;
	}

	public String getSecretaryName() {
		return secretaryName;
	}

	public void setSecretaryName(String secretaryName) {
		this.secretaryName = secretaryName;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getPacsBankAccNumber() {
		return pacsBankAccNumber;
	}

	public void setPacsBankAccNumber(String pacsBankAccNumber) {
		this.pacsBankAccNumber = pacsBankAccNumber;
	}

	public String getCashGl() {
		return cashGl;
	}

	public void setCashGl(String cashGl) {
		this.cashGl = cashGl;
	}

	/**
	 * @return the isBorrwingTransRequiredInGLExtract
	 */
	public String getIsBorrwingTransRequiredInGLExtract() {
		return isBorrwingTransRequiredInGLExtract;
	}

	/**
	 * @param isBorrwingTransRequiredInGLExtract
	 *            the isBorrwingTransRequiredInGLExtract to set
	 */
	public void setIsBorrwingTransRequiredInGLExtract(String isBorrwingTransRequiredInGLExtract) {
		this.isBorrwingTransRequiredInGLExtract = isBorrwingTransRequiredInGLExtract;
	}

	/**
	 * @return the pacsGlExtarctUpload
	 */
	public PacsGLExtractUpload getPacsGlExtarctUpload() {
		return pacsGlExtarctUpload;
	}

	/**
	 * @param pacsGlExtarctUpload
	 *            the pacsGlExtarctUpload to set
	 */
	public void setPacsGlExtarctUpload(PacsGLExtractUpload pacsGlExtarctUpload) {
		this.pacsGlExtarctUpload = pacsGlExtarctUpload;
	}

	/**
	 * @return the isInterestPaidEditable
	 */
	public Boolean getIsInterestPaidEditable() {
		return isInterestPaidEditable;
	}

	/**
	 * @param isInterestPaidEditable the isInterestPaidEditable to set
	 */
	public void setIsInterestPaidEditable(Boolean isInterestPaidEditable) {
		this.isInterestPaidEditable = isInterestPaidEditable;
	}

	/**
	 * @return the cashInTransitAccNo
	 */
	public String getCashInTransitAccNo() {
		return cashInTransitAccNo;
	}

	/**
	 * @param cashInTransitAccNo the cashInTransitAccNo to set
	 */
	public void setCashInTransitAccNo(String cashInTransitAccNo) {
		this.cashInTransitAccNo = cashInTransitAccNo;
	}

	/**
	 * @param isInterestPaidEditable the isInterestPaidEditable to set
	 */
	public void setInterestPaidEditable(boolean isInterestPaidEditable) {
		this.isInterestPaidEditable = isInterestPaidEditable;
	}

	public PacsStatus getPacsStatus() {
		return pacsStatus;
	}

	public void setPacsStatus(PacsStatus pacsStatus) {
		this.pacsStatus = pacsStatus;
	}

	public DayStatus getDayStatus() {
		return dayStatus;
	}

	public void setDayStatus(DayStatus dayStatus) {
		this.dayStatus = dayStatus;
	}
	
	


}