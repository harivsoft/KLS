package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.valuetypes.BankProcessStatus;
import com.vsoftcorp.kls.valuetypes.CBSMethodEnum;
import com.vsoftcorp.kls.valuetypes.transaction.BorrowingTransactionMethod;
import com.vsoftcorp.time.Date;

/**
 * @author sponnam
 * 
 */
@TypeDefs({
		@TypeDef(name = "bankProcessStatus", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.BankProcessStatus"), @Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"), @Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "bTransactionethod", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
					@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.transaction.BorrowingTransactionMethod"), @Parameter(name = "identifierMethod", value = "getValue"),
					@Parameter(name = "convertIdentifierMethod", value = "getType"), @Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "cbsMethodEnum", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
			@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.CBSMethodEnum"), @Parameter(name = "identifierMethod", value = "getValue"),
			@Parameter(name = "convertIdentifierMethod", value = "getType"), @Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = { @Parameter(name = "defaultISOCurrencyCode", value = "INR"),
				@Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@Table(name = "bank_parameter")
public class BankParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	public BankParameter() {
		super();
	}

	@Id
	@GeneratedValue(generator = "bankIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "bankIdSequence", sequenceName = "bank_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "bank_code", length = 5)
	private String bankCode;

	@Column(name = "bank_name", length = 45)
	private String bankName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "district_id")
	private District district;

	@Basic
	@Column(name = "business_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date businessDate;

	@Basic
	@Column(name = "process_status", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "bankProcessStatus")
	private BankProcessStatus bankProcessStatus;
	
	@Basic
	@Column(name = "borrowing_transaction_method", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "bTransactionethod")
	private BorrowingTransactionMethod borrowingTransactionMethod ;

	@Column(name = "priority_order", length = 3)
	private String priorityOrder;

	@Basic
	@Column(name = "max_share_amount", precision = 22, scale = 6)
	@Type(type = "money")
	private Money maxShareAmount;

	// This is used when calculating Sanctioned amount - KCC Loan Limit Per
	// Farmer at District Level
	@Basic
	@Column(name = "loan_limit_per_farmer", precision = 22, scale = 6)
	@Type(type = "money")
	private Money loanLimitPerFarmer;

	// This is for maximum days allowed for account wise inconsistency
	@Basic
	@Column(name = "max_days_for_inconsistency", length = 2)
	private Integer maxDaysForInconsistency;
	
	@Basic
	@Column(name = "cbs_integration_method", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "cbsMethodEnum")
	private CBSMethodEnum cbsIntegrationMethod;
	
	@Basic
	@Column(name = "other_cbs")
	private String other_cbs;
	
	@Basic
	@Column(name="cash_gl", length=20)
	private String cashGl;
	
	@Column(name = "show_receivable_at_branch", length = 1)
	private String showReceivableAtBranch;
	
	
	@Column(name = "image_Upload_Size")
	private Integer imageUploadSize;

	@Basic
	@Column(name="suspense_account", length=20)
	private String suspenseAccount;

		
	@Basic
	@Column(name="authorization_required")
    private String authorizationRequired;
	
	@Basic
	@Column(name="pending_return_status")
    private String pendingReturnStatus;

	@Basic
	@Column(name="multiple_login_allow")
    private String multipleLoginAllow;


	public String getMultipleLoginAllow() {
		return multipleLoginAllow;
	}

	public void setMultipleLoginAllow(String multipleLoginAllow) {
		this.multipleLoginAllow = multipleLoginAllow;
	}

	public String getOther_cbs() {
		return other_cbs;
	}

	public void setOther_cbs(String other_cbs) {
		this.other_cbs = other_cbs;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public BankProcessStatus getBankProcessStatus() {
		return bankProcessStatus;
	}

	public void setBankProcessStatus(BankProcessStatus bankProcessStatus) {
		this.bankProcessStatus = bankProcessStatus;
	}

	public Money getLoanLimitPerFarmer() {
		return loanLimitPerFarmer;
	}

	public void setLoanLimitPerFarmer(Money loanLimitPerFarmer) {
		this.loanLimitPerFarmer = loanLimitPerFarmer;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPriorityOrder() {
		return priorityOrder;
	}

	public void setPriorityOrder(String priorityOrder) {
		this.priorityOrder = priorityOrder;
	}

	public Money getMaxShareAmount() {
		return maxShareAmount;
	}

	public void setMaxShareAmount(Money maxShareAmount) {
		this.maxShareAmount = maxShareAmount;
	}

	public Integer getMaxDaysForInconsistency() {
		return maxDaysForInconsistency;
	}

	public void setMaxDaysForInconsistency(Integer maxDaysForInconsistency) {
		this.maxDaysForInconsistency = maxDaysForInconsistency;
	}

	public BorrowingTransactionMethod getBorrowingTransactionMethod() {
		return borrowingTransactionMethod;
	}

	public void setBorrowingTransactionMethod(BorrowingTransactionMethod borrowingTransactionMethod) {
		this.borrowingTransactionMethod = borrowingTransactionMethod;
	}
	

	public String getCashGl() {
		return cashGl;
	}

	public void setCashGl(String cashGl) {
		this.cashGl = cashGl;
	}

	public CBSMethodEnum getCbsIntegrationMethod() {
		return cbsIntegrationMethod;
	}

	public void setCbsIntegrationMethod(CBSMethodEnum cbsIntegrationMethod) {
		this.cbsIntegrationMethod = cbsIntegrationMethod;
	}
	
	public String getShowReceivableAtBranch() {
		return showReceivableAtBranch;
	}

	public void setShowReceivableAtBranch(String showReceivableAtBranch) {
		this.showReceivableAtBranch = showReceivableAtBranch;
	}
	
	public Integer getImageUploadSize() {
		return imageUploadSize;
	}

	public void setImageUploadSize(Integer imageUploadSize) {
		this.imageUploadSize = imageUploadSize;
	}

	public String getSuspenseAccount() {
		return suspenseAccount;
	}

	public void setSuspenseAccount(String suspenseAccount) {
		this.suspenseAccount = suspenseAccount;
	}


	public String getAuthorizationRequired() {
		return authorizationRequired;
	}


	public void setAuthorizationRequired(String authorizationRequired) {
		this.authorizationRequired = authorizationRequired;
	}
	public String getPendingReturnStatus() {
		return pendingReturnStatus;
	}

	public void setPendingReturnStatus(String pendingReturnStatus) {
		this.pendingReturnStatus = pendingReturnStatus;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bankCode == null) ? 0 : bankCode.hashCode());
		result = prime * result + ((bankProcessStatus == null) ? 0 : bankProcessStatus.hashCode());
		result = prime * result + ((businessDate == null) ? 0 : businessDate.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((loanLimitPerFarmer == null) ? 0 : loanLimitPerFarmer.hashCode());
		result = prime * result + ((maxShareAmount == null) ? 0 : maxShareAmount.hashCode());
		result = prime * result + ((priorityOrder == null) ? 0 : priorityOrder.hashCode());
		//result = prime * result + ((pendingReturnStatus == null) ? 0 : pendingReturnStatus.hashCode());
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
		BankParameter other = (BankParameter) obj;
		if (bankCode == null) {
			if (other.bankCode != null)
				return false;
		} else if (!bankCode.equals(other.bankCode))
			return false;
		if (bankProcessStatus != other.bankProcessStatus)
			return false;
		if (businessDate == null) {
			if (other.businessDate != null)
				return false;
		} else if (!businessDate.equals(other.businessDate))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (loanLimitPerFarmer == null) {
			if (other.loanLimitPerFarmer != null)
				return false;
		} else if (!loanLimitPerFarmer.equals(other.loanLimitPerFarmer))
			return false;
		if (maxShareAmount == null) {
			if (other.maxShareAmount != null)
				return false;
		} else if (!maxShareAmount.equals(other.maxShareAmount))
			return false;
		if (priorityOrder == null) {
			if (other.priorityOrder != null)
				return false;
		} else if (!priorityOrder.equals(other.priorityOrder))
			return false;
		return true;
	}

}
