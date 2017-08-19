package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;
import java.math.BigDecimal;

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

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */

@TypeDefs({
		@TypeDef(name = "loanApplicationState", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.LoanApplicationState"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
				@Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@Table(name = "PACS_LOAN_APPLICATION_DTL")
public class PacsLoanApplicationDetail implements Serializable {

	private static final long serialVersionUID = -684500325025395560L;

	public PacsLoanApplicationDetail() {
		super();
	}

	@Id
	@GeneratedValue(generator = "loanApplDtlIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "loanApplDtlIdSequence", sequenceName = "loan_appl_dtl_id_seq", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "header_id", referencedColumnName = "id")
	private PacsLoanApplicationHeader headerId;

	@Basic
	@Column(name = "customer_id")
	private Long customerId;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "crop_id", referencedColumnName = "id")
	private Crop cropId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "season_id", referencedColumnName = "id")
	private Season seasonId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "land_type_id", referencedColumnName = "id")
	private LandType landTypeId;

	@Basic
	@Column(name = "land_area", precision = 9, scale = 2)
	private BigDecimal landArea;

	@Basic
	@Column(name = "priority")
	private Integer priority;

	@Basic
	@Type(type = "money")
	@Column(name = "required_amount", precision = 22, scale = 6)
	private Money requiredAmount;

	@Basic
	@Column(name = "loan_application_no", length = 15)
	private String loanApplicationNo;

	@Basic
	@Type(type = "money")
	@Column(name = "calculated_amount", precision = 22, scale = 6)
	private Money calculatedAmount;

	@Basic
	@Type(type = "money")
	@Column(name = "recommended_amount", precision = 22, scale = 6)
	private Money recommendedAmount;

	@Basic
	@Column(name = "entered_remarks", length = 60)
	private String enteredRemarks;

	@Basic
	@Column(name = "inspection_remarks", length = 60)
	private String inspectionRemarks;

	@Basic
	@Type(type = "money")
	@Column(name = "inspection_amount", precision = 22, scale = 6)
	private Money inspectionAmount;

	@Basic
	@Column(name = "inspected_date", nullable = true)
	@Type(type = "com.vsoftcorp.time.Date")
	private Date inspectedDate;

	@Basic
	@Column(name = "inspected_by", length = 45)
	private String inspectedBy;

	@Basic
	@Type(type = "money")
	@Column(name = "sanctioned_amount", precision = 22, scale = 6)
	private Money sanctionedAmount;

	@Basic
	@Column(name = "application_status")
	@Enumerated(EnumType.ORDINAL)
	@Type(type = "loanApplicationState")
	private LoanApplicationState applicationStatus;

	@Basic
	@Column(name = "sanctioned_date", nullable = true)
	@Type(type = "com.vsoftcorp.time.Date")
	private Date sanctionedDate;

	@Basic
	@Column(name = "sanctioned_by", length = 45)
	private String sanctionedBy;
	
	@Basic
	@Column(name = "sanctioned_remarks", length = 60)
	private String sanctionedRemarks;
	
	@Basic
	@Type(type = "money")
	@Column(name="share_amount", precision = 22, scale = 6)
	private Money shareAmount;
	
	@Basic
	@Type(type = "money")
	@Column(name="insurance_amount", precision = 22, scale = 6)
	private Money insuranceAmount;
	
	@Basic
	@Type(type = "money")
	@Column(name="eligible_sanction_amount", precision = 22, scale = 6)
	private Money eligibleSanctionAmount;
	
	@Basic
	@Column(name = "inspection_auth_remarks", length = 60)
	private String inspectionAuthRemarks;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PacsLoanApplicationHeader getHeaderId() {
		return headerId;
	}

	public void setHeaderId(PacsLoanApplicationHeader headerId) {
		this.headerId = headerId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Crop getCropId() {
		return cropId;
	}

	public void setCropId(Crop cropId) {
		this.cropId = cropId;
	}

	public Season getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(Season seasonId) {
		this.seasonId = seasonId;
	}

	public LandType getLandTypeId() {
		return landTypeId;
	}

	public void setLandTypeId(LandType landTypeId) {
		this.landTypeId = landTypeId;
	}

	public BigDecimal getLandArea() {
		return landArea;
	}

	public void setLandArea(BigDecimal landArea) {
		this.landArea = landArea;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Money getRequiredAmount() {
		return requiredAmount;
	}

	public void setRequiredAmount(Money requiredAmount) {
		this.requiredAmount = requiredAmount;
	}

	public String getLoanApplicationNo() {
		return loanApplicationNo;
	}

	public void setLoanApplicationNo(String loanApplicationNo) {
		this.loanApplicationNo = loanApplicationNo;
	}

	public Money getCalculatedAmount() {
		return calculatedAmount;
	}

	public void setCalculatedAmount(Money calculatedAmount) {
		this.calculatedAmount = calculatedAmount;
	}

	public Money getRecommendedAmount() {
		return recommendedAmount;
	}

	public void setRecommendedAmount(Money recommendedAmount) {
		this.recommendedAmount = recommendedAmount;
	}

	public String getEnteredRemarks() {
		return enteredRemarks;
	}

	public void setEnteredRemarks(String enteredRemarks) {
		this.enteredRemarks = enteredRemarks;
	}

	public String getInspectionRemarks() {
		return inspectionRemarks;
	}

	public void setInspectionRemarks(String inspectionRemarks) {
		this.inspectionRemarks = inspectionRemarks;
	}

	public Money getInspectionAmount() {
		return inspectionAmount;
	}

	public void setInspectionAmount(Money inspectionAmount) {
		this.inspectionAmount = inspectionAmount;
	}

	public Date getInspectedDate() {
		return inspectedDate;
	}

	public void setInspectedDate(Date inspectedDate) {
		this.inspectedDate = inspectedDate;
	}

	public String getInspectedBy() {
		return inspectedBy;
	}

	public void setInspectedBy(String inspectedBy) {
		this.inspectedBy = inspectedBy;
	}

	public Money getSanctionedAmount() {
		return sanctionedAmount;
	}

	public void setSanctionedAmount(Money sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}

	public LoanApplicationState getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(LoanApplicationState applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public Date getSanctionedDate() {
		return sanctionedDate;
	}

	public void setSanctionedDate(Date sanctionedDate) {
		this.sanctionedDate = sanctionedDate;
	}

	public String getSanctionedBy() {
		return sanctionedBy;
	}

	public void setSanctionedBy(String sanctionedBy) {
		this.sanctionedBy = sanctionedBy;
	}

	public String getSanctionedRemarks() {
		return sanctionedRemarks;
	}

	public void setSanctionedRemarks(String sanctionedRemarks) {
		this.sanctionedRemarks = sanctionedRemarks;
	}

	public Money getShareAmount() {
		return shareAmount;
	}

	public Money getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setShareAmount(Money shareAmount) {
		this.shareAmount = shareAmount;
	}

	public void setInsuranceAmount(Money insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public Money getEligibleSanctionAmount() {
		return eligibleSanctionAmount;
	}

	public void setEligibleSanctionAmount(Money eligibleSanctionAmount) {
		this.eligibleSanctionAmount = eligibleSanctionAmount;
	}

	public String getInspectionAuthRemarks() {
		return inspectionAuthRemarks;
	}

	public void setInspectionAuthRemarks(String inspectionAuthRemarks) {
		this.inspectionAuthRemarks = inspectionAuthRemarks;
	}

}
