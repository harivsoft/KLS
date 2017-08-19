package com.vsoftcorp.kls.gl_interface.common;


import java.math.BigDecimal;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.accounting.types.DebitOrCredit;
import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.time.Date;
import com.vsoftcorp.time.DayOfMonth;
import com.vsoftcorp.time.MonthOfYear;
import com.vsoftcorp.time.YearOfEra;

public class CoreTransactionFileRecord {
	
	public static final int CORE_PRODUCT_COLUMN = 0;
	public static final int CORE_PRODUCT_LENGTH = 2;
	public static final int INSTITUTION_COLUMN = 2;
	public static final int INSTITUTION_LENGTH = 4;
	public static final int BRANCH_COLUMN = 6;
	public static final int BRANCH_LENGTH = 4;
	public static final int ACCOUNT_TYPE_COLUMN = 10;
	public static final int ACCOUNT_TYPE_LENGTH = 4;
	public static final int PRODUCT_CODE_COLUMN = 14;
	public static final int PRODUCT_CODE_LENGTH = 3;
	public static final int MONITARY_COMPONENT_CODE_COLUMN = 17;
	public static final int MONITARY_COMPONENT_CODE_LENGTH = 3;
	public static final int ACCOUNT_NUMBER_COLUMN = 20;
	public static final int ACCOUNT_NUMBER_LENGTH = 12;
	public static final int TRAN_CODE_COLUMN = 32;
	public static final int TRAN_CODE_LENGTH = 11;
	public static final int DEBITCREDIT_COLUMN = 43;
	public static final int DEBITCREDIT_LENGTH = 1;
	public static final int CURRENCY_COLUMN = 44;
	public static final int CURRENCY_LENGTH = 3;
	public static final int AMOUNT_DOLLAR_COLUMN = 47;
	public static final int AMOUNT_DOLLAR_LENGTH = 11;
	public static final int AMOUNT_CENTS_COLUMN = 58;
	public static final int AMOUNT_CENTS_LENGTH = 2;
	public static final int AMOUNT_SIGN_COLUMN = 60;
	public static final int AMOUNT_SIGN_LENGTH = 1;
	public static final int EFFECTIVE_DATE_COLUMN = 61;
	public static final int EFFECTIVE_DATE_LENGTH = 8;
	public static final int TRANSACTION_DATE_COLUMN = 69;
	public static final int TRANSACTION_DATE_LENGTH = 8;
	public static final int POST_STATUS_COLUMN=77;
	public static final int POST_STATUS_LENGTH=1;
	public static final int ENTRY_TYPE_COLUMN=78;
	public static final int ENTRY_TYPE_LENGTH=1;
	public static final int STATUS_COLUMN=79;
	public static final int STATUS_LENGTH = 3;
	public static final int DESCRIPTION_COLUMN = 82;
	public static final int DESCRIPTION_LENGTH = 30;
	public static final int XREF_TRX_ID_COLUMN = 112;
	public static final int XREF_TRX_ID_LENGTH = 15;
	public static final int FILE_NAME_COLUMN = 127;
	public static final int FILE_NAME_LENGTH = 30;
	public static final int ACCOUNT_CATEGORY_COLUMN = 157;
	public static final int ACCOUNT_CATEGORY_LENTH = 1;
	public static final int IS_SOLDLOAN_COLUMN=158;
	public static final int IS_SOLDLOAN_LENGTH=1;
	public static final int INVESTOR_CODE_COLUMN=159;
    public static final int INVESTOR_CODE_LENGTH=10;
	
	private String record;
	private Date transactionDate;
	
	public CoreTransactionFileRecord(String record, Date transactionDate) {
		this.record = record;
		this.transactionDate = transactionDate;
	}	

	/**
	 * @return the record
	 */
	public String getRecord() {
		return record;
	}
	
	/**
	 * Get institution.
	 * 
	 * @return
	 */
	public Integer getInstitution() {
		String value = getColumnValueAsString(INSTITUTION_COLUMN, INSTITUTION_LENGTH);
		
		if(value.trim().length() ==  0) {
			throw new RuntimeException("Institution not found...");
		}
		
		return Integer.valueOf(value);
	}
	
	/**
	 * Get branch.
	 * 
	 * @return
	 */
	public Integer getBranch() {
		String value = getColumnValueAsString(BRANCH_COLUMN, BRANCH_LENGTH);
		
		if(value.trim().length() ==  0) {
			throw new RuntimeException("Branch not found...");
		}
		
		return Integer.valueOf(value);
	}
	
	/**
	 * Get Account Type.
	 * 
	 * @return
	 */
	public Integer getAccountType() {
		String value = getColumnValueAsString(ACCOUNT_TYPE_COLUMN, ACCOUNT_TYPE_LENGTH);
		
		if(value==null || value.trim().length()==0) {
			throw new RuntimeException("Account Type not found.");
		}
			
		return Integer.valueOf(value);
	}
	

	/**
	 * Get product code.
	 * 
	 * @return
	 */
	public Integer getProductCode() {
		String value = getColumnValueAsString(PRODUCT_CODE_COLUMN, PRODUCT_CODE_LENGTH);
		
		if(value.trim().length() ==  0) {
			throw new RuntimeException("Product code not found...");
		}
		
		return Integer.valueOf(value);
	}

	/**
	 * Get monitary component code.
	 * 
	 * @return
	 */
	public Integer getMonitaryComponentCode() {
		String value = getColumnValueAsString(MONITARY_COMPONENT_CODE_COLUMN, MONITARY_COMPONENT_CODE_LENGTH);
		
		if(value.trim().length() ==  0) {
			throw new RuntimeException("Product code not found...");
		}
		
		return Integer.valueOf(value);
	}
	
	/**
	 * Get account number.
	 * 
	 * @return
	 */
	public String getAccountNumber() {
		String value = getColumnValueAsString(ACCOUNT_NUMBER_COLUMN, ACCOUNT_NUMBER_LENGTH);
		
		if(value.trim().length() ==  0) {
			throw new RuntimeException("Account number not found...");
		}
		
		return value;
	}
	
	/**
	 * Get transaction code.
	 * 
	 * @return
	 */
	public String getTranCode() {
		String value = getColumnValueAsString(TRAN_CODE_COLUMN, TRAN_CODE_LENGTH);
		
		if(value.trim().length() ==  0) {
			throw new RuntimeException("Transaction code not found...");
		}
		
		return value;
	}
	
	/**
	 * Get debit/credit.
	 * 
	 * @return
	 */
	public DebitOrCredit getDebitCredit() {
		String value = getColumnValueAsString(DEBITCREDIT_COLUMN, DEBITCREDIT_LENGTH);
		
		if(value.trim().length() ==  0) {
			throw new RuntimeException("Core product not found...");
		}
		
		return DebitOrCredit.valueOf(Integer.valueOf(value));
	}

	/**
	 * Get currency.
	 * 
	 * @return
	 */
	public String getCurrency() {
		String value = getColumnValueAsString(CURRENCY_COLUMN, CURRENCY_LENGTH);
		
		if(value.trim().length() ==  0) {
			throw new RuntimeException("Currency not found...");
		}
		
		return value;
	}
	
	/**
	 * Get amount.
	 * 
	 * @return
	 */
	public AccountingMoney getAmount() {
		String currency = getCurrency();
		
		String amountDollar = getColumnValueAsString(AMOUNT_DOLLAR_COLUMN, AMOUNT_DOLLAR_LENGTH);
		
		if(amountDollar.trim().length() ==  0) {
			throw new RuntimeException("Amount not found...");
		}
		
		String amountCent = getColumnValueAsString(AMOUNT_CENTS_COLUMN, AMOUNT_CENTS_LENGTH);
		
		if(amountDollar.trim().length() ==  0) {
			throw new RuntimeException("Amount not found...");
		}
		
			
		String amountValue =  amountDollar + "." + amountCent;
		
		String amountSign = getColumnValueAsString(AMOUNT_SIGN_COLUMN, AMOUNT_SIGN_LENGTH);
		DebitOrCredit debitCredit = getDebitCredit();
		
		BigDecimal amount = null;
	
		if(amountSign.equals("+")) {
			amount = BigDecimal.valueOf(Double.parseDouble(amountValue));
		} else {
			amount = BigDecimal.valueOf(-1 * Double.parseDouble(amountValue));
		}
		
		AccountingMoney accountingMoney = AccountingMoney.valueOf(Currency.getInstance(currency), amount, debitCredit);
		
		return accountingMoney;
	}
	
	/**
	 * Get effective date.
	 * 
	 * @return
	 */
	public Date getEffectiveDate() {
		String date = getColumnValueAsString(EFFECTIVE_DATE_COLUMN, EFFECTIVE_DATE_LENGTH).trim();
		
		if (date.trim().length() == 0 || Integer.valueOf(date) == 0)
			throw new RuntimeException("Effective date not found...");
		
		MonthOfYear month = MonthOfYear.valueOf(Integer.valueOf(date.substring(
				0, 2)));
		DayOfMonth day = DayOfMonth.valueOf(Integer.valueOf(date
				.substring(2, 4)));
		YearOfEra year = YearOfEra.valueOf(Integer
				.valueOf(date.substring(4, 8)));
		return new Date(year, month, day);
	}
	
	/**
	 * Get transaction date.
	 * 
	 * @return
	 */
	public Date getTransactionDate() {
		String date = getColumnValueAsString(TRANSACTION_DATE_COLUMN, TRANSACTION_DATE_LENGTH).trim();
		
		if (date.trim().length() == 0 || Integer.valueOf(date) == 0)
			throw new RuntimeException("Transaction date not found...");
		
		MonthOfYear month = MonthOfYear.valueOf(Integer.valueOf(date.substring(
				0, 2)));
		DayOfMonth day = DayOfMonth.valueOf(Integer.valueOf(date
				.substring(2, 4)));
		YearOfEra year = YearOfEra.valueOf(Integer
				.valueOf(date.substring(4, 8)));
		return new Date(year, month, day);
	}

	
	public String getStatus() {
		String status = getColumnValueAsString(STATUS_COLUMN, STATUS_LENGTH).trim();
		
		if (status.trim().length() == 0 )
			throw new RuntimeException("Account Status not found...");
		
		return status.trim();
		
	}
	/**
	 * Get description.
	 * 
	 * @return
	 */
	public String getDescription() {
		return getColumnValueAsString(DESCRIPTION_COLUMN, DESCRIPTION_LENGTH);
	}
	
	public Long getXrefTrxID() {
		String value = getColumnValueAsString(XREF_TRX_ID_COLUMN, XREF_TRX_ID_LENGTH);
		
		if(value == null || value.trim().length()==0) {
			throw new RuntimeException("XREF. TRX. ID not found...");
		}
		
		return Long.valueOf(value.trim());
	}
	
	public String getFileName() {
		String value = getColumnValueAsString(FILE_NAME_COLUMN, FILE_NAME_LENGTH);
		
		return value;
	}
	
	public String getAccountCategory() {
		String value = getColumnValueAsString(ACCOUNT_CATEGORY_COLUMN, ACCOUNT_CATEGORY_LENTH);
		return value;
	}
	
	public String getInvestorCode() {
		String value = getColumnValueAsString(INVESTOR_CODE_COLUMN,
				INVESTOR_CODE_LENGTH);
		return value;
	}
	
	public String getIsSoldLoan() {
		String value = getColumnValueAsString(IS_SOLDLOAN_COLUMN,
				IS_SOLDLOAN_LENGTH);
		return value;
	}
	/**
	 * Returns the value of the specified column as String.
	 * 
	 * @param columnIndex	The starting position of the column in the .dat file.
	 * @param columnLength	The length of the column in the .dat file.
	 * @return	Value of the column.
	 */
	private String getColumnValueAsString(int columnIndex, int columnLength) {
		String value = null;
		
		if(record.length() <= columnIndex) 
			value = "";
		else 
			value = (record.length() < (columnIndex + columnLength)) ? record.substring(columnIndex)
						: record.substring(columnIndex, columnIndex	+ columnLength);

		return value;
	}
}
