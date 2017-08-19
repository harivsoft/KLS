package com.vsoftcorp.kls.data;
import java.math.BigDecimal;

public class StLoanRecoveryData {

		private Long id;
		private Integer pacsId;
		private String customerNumber;
		private String customerName;
		private BigDecimal sanctionedAmount;
		private BigDecimal drawableAmount;
		private BigDecimal principleBalance;
		private BigDecimal interestReceivable;
		private BigDecimal totalPenalInterestReceivable;
		private BigDecimal subsidyInterestReceivable;
		private BigDecimal chargesReceivable;
		private BigDecimal totalReceivableAmount;
		private BigDecimal amountPaid;
		private BigDecimal principalPaid;
		private BigDecimal interestPaid;
		private BigDecimal penalInterestPaid;
		private BigDecimal subsidyPaid;
		private BigDecimal chargesPaid;
		private String remarks;
		private boolean standAloneStatus;
		private Integer status;
        private String passingDate;
        private String memberNumber;
        private String rejectionRemarks;

		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}

		/**
		 * @param id
		 *            the id to set
		 */
		public void setId(Long id) {
			this.id = id;
		}

		/**
		 * @return the customerName
		 */
		public String getCustomerName() {
			return customerName;
		}

		/**
		 * @param customerName the customerName to set
		 */
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		
		public BigDecimal getSanctionedAmount() {
			return sanctionedAmount;
		}

		public void setSanctionedAmount(BigDecimal sanctionedAmount) {
			this.sanctionedAmount = sanctionedAmount;
		}

		public BigDecimal getDrawableAmount() {
			return drawableAmount;
		}

		public void setDrawableAmount(BigDecimal drawableAmount) {
			this.drawableAmount = drawableAmount;
		}

		public BigDecimal getPrincipleBalance() {
			return principleBalance;
		}

		public void setPrincipleBalance(BigDecimal principleBalance) {
			this.principleBalance = principleBalance;
		}

		public BigDecimal getInterestReceivable() {
			return interestReceivable;
		}

		public void setInterestReceivable(BigDecimal interestReceivable) {
			this.interestReceivable = interestReceivable;
		}

		public BigDecimal getSubsidyInterestReceivable() {
			return subsidyInterestReceivable;
		}

		public void setSubsidyInterestReceivable(BigDecimal subsidyInterestReceivable) {
			this.subsidyInterestReceivable = subsidyInterestReceivable;
		}

		
		/**
		 * @return the totalPenalInterestReceivable
		 */
		public BigDecimal getTotalPenalInterestReceivable() {
			return totalPenalInterestReceivable;
		}

		/**
		 * @param totalPenalInterestReceivable
		 *            the totalPenalInterestReceivable to set
		 */
		public void setTotalPenalInterestReceivable(BigDecimal totalPenalInterestReceivable) {
			this.totalPenalInterestReceivable = totalPenalInterestReceivable;
		}

		
		/**
		 * @return the totalReceivableAmount
		 */
		public BigDecimal getTotalReceivableAmount() {
			return totalReceivableAmount;
		}

		/**
		 * @param totalReceivableAmount
		 *            the totalReceivableAmount to set
		 */
		public void setTotalReceivableAmount(BigDecimal totalReceivableAmount) {
			this.totalReceivableAmount = totalReceivableAmount;
		}

		/**
		 * @return the amountPaid
		 */
		public BigDecimal getAmountPaid() {
			return amountPaid;
		}

		/**
		 * @param amountPaid
		 *            the amountPaid to set
		 */
		public void setAmountPaid(BigDecimal amountPaid) {
			this.amountPaid = amountPaid;
		}

		/**
		 * @return the principalPaid
		 */
		public BigDecimal getPrincipalPaid() {
			return principalPaid;
		}

		/**
		 * @param principalPaid
		 *            the principalPaid to set
		 */
		public void setPrincipalPaid(BigDecimal principalPaid) {
			this.principalPaid = principalPaid;
		}

		/**
		 * @return the interestPaid
		 */
		public BigDecimal getInterestPaid() {
			return interestPaid;
		}

		/**
		 * @param interestPaid
		 *            the interestPaid to set
		 */
		public void setInterestPaid(BigDecimal interestPaid) {
			this.interestPaid = interestPaid;
		}

		/**
		 * @return the penalInterestPaid
		 */
		public BigDecimal getPenalInterestPaid() {
			return penalInterestPaid;
		}

		/**
		 * @param penalInterestPaid
		 *            the penalInterestPaid to set
		 */
		public void setPenalInterestPaid(BigDecimal penalInterestPaid) {
			this.penalInterestPaid = penalInterestPaid;
		}

		/**
		 * @return the chargesPaid
		 */
		public BigDecimal getSubsidyPaid() {
			return subsidyPaid;
		}

		/**
		 * @param chargesPaid
		 *            the chargesPaid to set
		 */
		public void setChargesPaid(BigDecimal subsidyPaid) {
			this.subsidyPaid = subsidyPaid;
		}

	
		
		/**
		 * @return the customerNumber
		 */
		public String getCustomerNumber() {
			return customerNumber;
		}

		/**
		 * @param customerNumber
		 *            the customerNumber to set
		 */
		public void setCustomerNumber(String customerNumber) {
			this.customerNumber = customerNumber;
		}

		/**
		 * @return the remarks
		 */
		public String getRemarks() {
			return remarks;
		}

		/**
		 * @param remarks
		 *            the remarks to set
		 */
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		/**
		 * @return the standAloneStatus
		 */
		public boolean getStandAloneStatus() {
			return standAloneStatus;
		}

		/**
		 * @param standAloneStatus the standAloneStatus to set
		 */
		public void setStandAloneStatus(boolean standAloneStatus) {
			this.standAloneStatus = standAloneStatus;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Integer getPacsId() {
			return pacsId;
		}

		public void setPacsId(Integer pacsId) {
			this.pacsId = pacsId;
		}

		public String getPassingDate() {
			return passingDate;
		}

		public void setPassingDate(String passingDate) {
			this.passingDate = passingDate;
		}

		public void setSubsidyPaid(BigDecimal subsidyPaid) {
			this.subsidyPaid = subsidyPaid;
		}

		public BigDecimal getChargesReceivable() {
			return chargesReceivable;
		}

		public void setChargesReceivable(BigDecimal chargesReceivable) {
			this.chargesReceivable = chargesReceivable;
		}

		public BigDecimal getChargesPaid() {
			return chargesPaid;
		}

		public String getMemberNumber() {
			return memberNumber;
		}

		public void setMemberNumber(String memberNumber) {
			this.memberNumber = memberNumber;
		}

		public String getRejectionRemarks() {
			return rejectionRemarks;
		}

		public void setRejectionRemarks(String rejectionRemarks) {
			this.rejectionRemarks = rejectionRemarks;
		}
		

	}




