/**
 * 
 */
package com.vsoftcorp.kls.business.entity.account;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.kls.business.entity.loan.PacsLoanApplication;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
@TypeDefs({ @TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@DiscriminatorValue("L")

// L for MT/LT loans.
public class LoanLineOfCredit extends LineOfCredit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3175283650129788972L;

	@Basic
	@Column(name = "first_due_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date firstDueDate;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "pacs_loan_application_id", referencedColumnName = "id")
	private PacsLoanApplication pacsLoanApplication;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_account_property_id", referencedColumnName = "id")
	private LoanAccountProperty loanAccountProperty;

	@Basic
	@Column(name = "close_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date closeDate;

	@Override
	public String getLoanType() {
		return "L";
	}

	/**
	 * @return the firstDueDate
	 */
	public Date getFirstDueDate() {
		return firstDueDate;
	}

	/**
	 * @param firstDueDate
	 *            the firstDueDate to set
	 */
	public void setFirstDueDate(Date firstDueDate) {
		this.firstDueDate = firstDueDate;
	}

	/**
	 * @return the pacsLoanApplication
	 */
	public PacsLoanApplication getPacsLoanApplication() {
		return pacsLoanApplication;
	}

	/**
	 * @param pacsLoanApplication
	 *            the pacsLoanApplication to set
	 */
	public void setPacsLoanApplication(PacsLoanApplication pacsLoanApplication) {
		this.pacsLoanApplication = pacsLoanApplication;
	}

	/**
	 * @return the loanAccountProperty
	 */
	public LoanAccountProperty getLoanAccountProperty() {
		return loanAccountProperty;
	}

	/**
	 * @param loanAccountProperty
	 *            the loanAccountProperty to set
	 */
	public void setLoanAccountProperty(LoanAccountProperty loanAccountProperty) {
		this.loanAccountProperty = loanAccountProperty;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the closeDate
	 */
	public Date getCloseDate() {
		return closeDate;
	}

	/**
	 * @param closeDate
	 *            the closeDate to set
	 */
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

}
