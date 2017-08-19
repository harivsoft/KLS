/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.transaction.result.classes;

import com.vsoftcorp.accounting.types.AccountingMoney;

/**
 * @author a9152
 * 
 */
public class TransactionRecordByProductAndCrdr {

	private AccountingMoney transactionAmtSum;

	private Integer productId;

	private Integer crDr;

	private Long lineOfCreditId;

	private Long accountId;

	public TransactionRecordByProductAndCrdr() {
	}

	public TransactionRecordByProductAndCrdr(AccountingMoney transactionAmtSum, Integer productId, Integer crDr,
			Long lineOfCreditId, Long accountId) {
		this.transactionAmtSum = transactionAmtSum;
		this.productId = productId;
		this.crDr = crDr;
		this.lineOfCreditId = lineOfCreditId;
		this.accountId = accountId;
	}

	/**
	 * @return the transactionAmtSum
	 */
	public AccountingMoney getTransactionAmtSum() {
		return transactionAmtSum;
	}

	/**
	 * @param transactionAmtSum
	 *            the transactionAmtSum to set
	 */
	public void setTransactionAmtSum(AccountingMoney transactionAmtSum) {
		this.transactionAmtSum = transactionAmtSum;
	}

	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/**
	 * @return the crDr
	 */
	public Integer getCrDr() {
		return crDr;
	}

	/**
	 * @param crDr
	 *            the crDr to set
	 */
	public void setCrDr(Integer crDr) {
		this.crDr = crDr;
	}

	/**
	 * @return the lineOfCreditId
	 */
	public Long getLineOfCreditId() {
		return lineOfCreditId;
	}

	/**
	 * @param lineOfCreditId
	 *            the lineOfCreditId to set
	 */
	public void setLineOfCreditId(Long lineOfCreditId) {
		this.lineOfCreditId = lineOfCreditId;
	}

	/**
	 * @return the accountId
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
}