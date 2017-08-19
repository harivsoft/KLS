package com.vsoftcorp.kls.business.entity.loan;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.business.entities.SubPurpose;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;
import com.vsoftcorp.kls.valuetypes.loanapplication.InstallmentFrequency;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1565
 * 
 */

@TypeDefs({
		@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
				@Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }),
		@TypeDef(name = "loanApplicationState", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.LoanApplicationState"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "installmentFrequency", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.loanapplication.InstallmentFrequency"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }) })
@Entity
@Table(name = "pacs_loan_application")
public class PacsLoanApplication implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8474800431463890897L;

	public PacsLoanApplication() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(generator = "pacsloanapplicationIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "pacsloanapplicationIdSequence", sequenceName = "pacs_loan_application_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Basic
	@Column(name = "app_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date applicationDate;

	@Basic
	@Column(name = "master_app_id")
	private Long masterApplicationId;

	@Basic
	@Column(name = "customer_id")
	private Long customerId;

	@OneToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	@OneToOne
	@JoinColumn(name = "purpose_id", referencedColumnName = "id")
	private Purpose purpose;

	@OneToOne
	@JoinColumn(name = "sub_purpose_id", referencedColumnName = "id")
	private SubPurpose subPurpose;

	@Basic
	@Column(name = "total_number_of_units")
	private Integer totalNumberOfUnits;

	@Basic
	@Type(type = "money")
	@Column(name = "total_requested_amount", precision = 22, scale = 6)
	private Money totalRequestedAmount;

	@Basic
	@Type(type = "money")
	@Column(name = "total_amount_as_per_unit_cost", precision = 22, scale = 6)
	private Money totalAmountAsPerUnitCost;

	@Basic
	@Type(type = "money")
	@Column(name = "recommended_amount", precision = 22, scale = 6)
	private Money recommendedAmount;

	@Basic
	@Column(name = "application_status")
	@Enumerated(EnumType.ORDINAL)
	@Type(type = "loanApplicationState")
	private LoanApplicationState applicationStatus;

	@Basic
	@Type(type = "money")
	@Column(name = "scrutiny_amount", precision = 22, scale = 6)
	private Money scrutinyAmount;

	@Basic
	@Column(name = "scrutiny_remarks", length = 60)
	private String scrutinyRemarks;

	@Basic
	@Column(name = "sanction_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date sanctionDate;

	@Basic
	@Type(type = "money")
	@Column(name = "inspection_amount", precision = 22, scale = 6)
	private Money inspectionAmount;

	@Basic
	@Column(name = "inspection_remarks", length = 60)
	private String inspectionRemarks;

	@Basic
	@Column(name = "inspection_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date inspectionDate;

	@Basic
	@Column(name = "marginal_percentage", precision = 5, scale = 2)
	private BigDecimal marginalPercentage;

	@Basic
	@Type(type = "money")
	@Column(name = "marginal_amount", precision = 22, scale = 6)
	private Money marginalAmount;

	@Basic
	@Type(type = "money")
	@Column(name = "sanction_amount", precision = 22, scale = 6)
	private Money sanctionAmount;

	@Basic
	@Column(name = "sanction_remarks", length = 60)
	private String sanctionRemarks;

	@Basic
	@Column(name = "loan_period")
	private Integer loanPeriod;

	@Basic
	@Column(name = "no_of_installments")
	private Integer noOfInstallments;

	@ManyToOne(optional = true)
	@JoinColumn(name = "interest_category_id", referencedColumnName = "id")
	private InterestCategory interestCategory;

	@OneToOne(optional = true)
	@JoinColumn(name = "scheme_id", referencedColumnName = "id")
	private Scheme scheme;

	@Basic
	@Column(name = "installment_frequency")
	@Enumerated(EnumType.ORDINAL)
	@Type(type = "installmentFrequency")
	private InstallmentFrequency installmentFrequency;

	@Basic
	@Column(name = "moratorium_principle_period")
	private Integer moratoriumPrinciplePeriod;

	@Basic
	@Column(name = "moratorium_interest_period")
	private Integer moratoriumInterestPeriod;
	
	@Basic
	@Column(name = "processing_fee", precision = 22, scale = 6)
	private BigDecimal processingFee;
	
	@Basic
	@Column(name = "inspection_auth_remarks", length = 60)
	private String inspectionAuthRemarks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public Long getMasterApplicationId() {
		return masterApplicationId;
	}

	public void setMasterApplicationId(Long masterApplicationId) {
		this.masterApplicationId = masterApplicationId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Purpose getPurpose() {
		return purpose;
	}

	public void setPurpose(Purpose purpose) {
		this.purpose = purpose;
	}

	public SubPurpose getSubPurpose() {
		return subPurpose;
	}

	public void setSubPurpose(SubPurpose subPurpose) {
		this.subPurpose = subPurpose;
	}

	public Integer getTotalNumberOfUnits() {
		return totalNumberOfUnits;
	}

	public void setTotalNumberOfUnits(Integer totalNumberOfUnits) {
		this.totalNumberOfUnits = totalNumberOfUnits;
	}

	public Money getTotalRequestedAmount() {
		return totalRequestedAmount;
	}

	public Money getRecommendedAmount() {
		return recommendedAmount;
	}

	public void setRecommendedAmount(Money recommendedAmount) {
		this.recommendedAmount = recommendedAmount;
	}

	public void setTotalRequestedAmount(Money totalRequestedAmount) {
		this.totalRequestedAmount = totalRequestedAmount;
	}

	public Money getTotalAmountAsPerUnitCost() {
		return totalAmountAsPerUnitCost;
	}

	public void setTotalAmountAsPerUnitCost(Money totalAmountAsPerUnitCost) {
		this.totalAmountAsPerUnitCost = totalAmountAsPerUnitCost;
	}

	/**
	 * @return the applicationStatus
	 */
	public LoanApplicationState getApplicationStatus() {
		return applicationStatus;
	}

	/**
	 * @param applicationStatus
	 *            the applicationStatus to set
	 */
	public void setApplicationStatus(LoanApplicationState applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	/**
	 * @return the scrutinyAmount
	 */
	public Money getScrutinyAmount() {
		return scrutinyAmount;
	}

	/**
	 * @param scrutinyAmount
	 *            the scrutinyAmount to set
	 */
	public void setScrutinyAmount(Money scrutinyAmount) {
		this.scrutinyAmount = scrutinyAmount;
	}

	/**
	 * @return the scrutinyRemarks
	 */
	public String getScrutinyRemarks() {
		return scrutinyRemarks;
	}

	/**
	 * @param scrutinyRemarks
	 *            the scrutinyRemarks to set
	 */
	public void setScrutinyRemarks(String scrutinyRemarks) {
		this.scrutinyRemarks = scrutinyRemarks;
	}

	/**
	 * @return the inspectionAmount
	 */
	public Money getInspectionAmount() {
		return inspectionAmount;
	}

	/**
	 * @param inspectionAmount
	 *            the inspectionAmount to set
	 */
	public void setInspectionAmount(Money inspectionAmount) {
		this.inspectionAmount = inspectionAmount;
	}

	/**
	 * @return the inspectionRemarks
	 */
	public String getInspectionRemarks() {
		return inspectionRemarks;
	}

	/**
	 * @param inspectionRemarks
	 *            the inspectionRemarks to set
	 */
	public void setInspectionRemarks(String inspectionRemarks) {
		this.inspectionRemarks = inspectionRemarks;
	}

	/**
	 * @return the sanctionDate
	 */
	public Date getSanctionDate() {
		return sanctionDate;
	}

	/**
	 * @param sanctionDate
	 *            the sanctionDate to set
	 */
	public void setSanctionDate(Date sanctionDate) {
		this.sanctionDate = sanctionDate;
	}

	/**
	 * @return the inspectionDate
	 */
	public Date getInspectionDate() {
		return inspectionDate;
	}

	/**
	 * @param inspectionDate
	 *            the inspectionDate to set
	 */
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	/**
	 * @return the marginalPercentage
	 */
	public BigDecimal getMarginalPercentage() {
		return marginalPercentage;
	}

	/**
	 * @param marginalPercentage
	 *            the marginalPercentage to set
	 */
	public void setMarginalPercentage(BigDecimal marginalPercentage) {
		this.marginalPercentage = marginalPercentage;
	}

	/**
	 * @return the marginalAmount
	 */
	public Money getMarginalAmount() {
		return marginalAmount;
	}

	/**
	 * @param marginalAmount
	 *            the marginalAmount to set
	 */
	public void setMarginalAmount(Money marginalAmount) {
		this.marginalAmount = marginalAmount;
	}

	/**
	 * @return the sanctionAmount
	 */
	public Money getSanctionAmount() {
		return sanctionAmount;
	}

	/**
	 * @param sanctionAmount
	 *            the sanctionAmount to set
	 */
	public void setSanctionAmount(Money sanctionAmount) {
		this.sanctionAmount = sanctionAmount;
	}

	/**
	 * @return the sanctionRemarks
	 */
	public String getSanctionRemarks() {
		return sanctionRemarks;
	}

	/**
	 * @param sanctionRemarks
	 *            the sanctionRemarks to set
	 */
	public void setSanctionRemarks(String sanctionRemarks) {
		this.sanctionRemarks = sanctionRemarks;
	}

	/**
	 * @return the loanPeriod
	 */
	public Integer getLoanPeriod() {
		return loanPeriod;
	}

	/**
	 * @param loanPeriod
	 *            the loanPeriod to set
	 */
	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	/**
	 * @return the noOfInstallments
	 */
	public Integer getNoOfInstallments() {
		return noOfInstallments;
	}

	/**
	 * @param noOfInstallments
	 *            the noOfInstallments to set
	 */
	public void setNoOfInstallments(Integer noOfInstallments) {
		this.noOfInstallments = noOfInstallments;
	}

	/**
	 * @return the interestCategory
	 */
	public InterestCategory getInterestCategory() {
		return interestCategory;
	}

	/**
	 * @param interestCategory
	 *            the interestCategory to set
	 */
	public void setInterestCategory(InterestCategory interestCategory) {
		this.interestCategory = interestCategory;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	/**
	 * @return the installmentFrequency
	 */
	public InstallmentFrequency getInstallmentFrequency() {
		return installmentFrequency;
	}

	/**
	 * @param installmentFrequency
	 *            the installmentFrequency to set
	 */
	public void setInstallmentFrequency(InstallmentFrequency installmentFrequency) {
		this.installmentFrequency = installmentFrequency;
	}

	/**
	 * @return the moratoriumPrinciplePeriod
	 */
	public Integer getMoratoriumPrinciplePeriod() {
		return moratoriumPrinciplePeriod;
	}

	/**
	 * @param moratoriumPrinciplePeriod
	 *            the moratoriumPrinciplePeriod to set
	 */
	public void setMoratoriumPrinciplePeriod(Integer moratoriumPrinciplePeriod) {
		this.moratoriumPrinciplePeriod = moratoriumPrinciplePeriod;
	}

	/**
	 * @return the moratoriumInterestPeriod
	 */
	public Integer getMoratoriumInterestPeriod() {
		return moratoriumInterestPeriod;
	}

	/**
	 * @param moratoriumInterestPeriod
	 *            the moratoriumInterestPeriod to set
	 */
	public void setMoratoriumInterestPeriod(Integer moratoriumInterestPeriod) {
		this.moratoriumInterestPeriod = moratoriumInterestPeriod;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	/**
	 * @return the processingFee
	 */
	public BigDecimal getProcessingFee() {
		return processingFee;
	}

	/**
	 * @param processingFee
	 *            the processingFee to set
	 */
	public void setProcessingFee(BigDecimal processingFee) {
		this.processingFee = processingFee;
	}
	
	public String getInspectionAuthRemarks() {
		return inspectionAuthRemarks;
	}

	public void setInspectionAuthRemarks(String inspectionAuthRemarks) {
		this.inspectionAuthRemarks = inspectionAuthRemarks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicationDate == null) ? 0 : applicationDate.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((masterApplicationId == null) ? 0 : masterApplicationId.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((purpose == null) ? 0 : purpose.hashCode());
		// result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((subPurpose == null) ? 0 : subPurpose.hashCode());
		result = prime * result + ((totalAmountAsPerUnitCost == null) ? 0 : totalAmountAsPerUnitCost.hashCode());
		result = prime * result + ((totalNumberOfUnits == null) ? 0 : totalNumberOfUnits.hashCode());
		result = prime * result + ((totalRequestedAmount == null) ? 0 : totalRequestedAmount.hashCode());
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
		PacsLoanApplication other = (PacsLoanApplication) obj;
		if (applicationDate == null) {
			if (other.applicationDate != null)
				return false;
		} else if (!applicationDate.equals(other.applicationDate))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (masterApplicationId == null) {
			if (other.masterApplicationId != null)
				return false;
		} else if (!masterApplicationId.equals(other.masterApplicationId))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (purpose == null) {
			if (other.purpose != null)
				return false;
		} else if (!purpose.equals(other.purpose))
			return false;

		if (subPurpose == null) {
			if (other.subPurpose != null)
				return false;
		} else if (!subPurpose.equals(other.subPurpose))
			return false;
		if (totalAmountAsPerUnitCost == null) {
			if (other.totalAmountAsPerUnitCost != null)
				return false;
		} else if (!totalAmountAsPerUnitCost.equals(other.totalAmountAsPerUnitCost))
			return false;
		if (totalNumberOfUnits == null) {
			if (other.totalNumberOfUnits != null)
				return false;
		} else if (!totalNumberOfUnits.equals(other.totalNumberOfUnits))
			return false;
		if (totalRequestedAmount == null) {
			if (other.totalRequestedAmount != null)
				return false;
		} else if (!totalRequestedAmount.equals(other.totalRequestedAmount))
			return false;

		return true;
	}

}
