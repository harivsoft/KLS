package com.vsoftcorp.kls.loans.gl.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.gl.GLEntrySummary;
import com.vsoftcorp.kls.gl_interface.common.CoreTransactionFileRecord;
import com.vsoftcorp.time.Date;

public class GLExtractRecordBuilder {

	private String LOANS_ACCOUNT_CATEGORY_FOR_GL_INTERFACE = "2";

	private GLEntrySummary glEntrySummary;

	public GLExtractRecordBuilder(GLEntrySummary theSummary) {

		glEntrySummary = theSummary;
	}

	public String constructGLRecord() {

		StringBuilder builder = new StringBuilder();
		
		/*builder.append(String.format("%0"
				+ CoreTransactionFileRecord.CORE_PRODUCT_LENGTH + "d",
				01));

		builder.append(String.format("%0"
				+ CoreTransactionFileRecord.INSTITUTION_LENGTH + "d", Long
				.valueOf(glEntrySummary.getInstitutionId())));

		builder.append(String.format("%0"
				+ CoreTransactionFileRecord.BRANCH_LENGTH + "d", glEntrySummary.getBranchId() == null ? Long.valueOf(90) 
						: Long.valueOf(glEntrySummary.getBranchId())));
		
		builder.append(String.format("%0"
				+ CoreTransactionFileRecord.ACCOUNT_TYPE_LENGTH + "d",2
				));

		builder.append(String.format("%0"
				+ CoreTransactionFileRecord.PRODUCT_CODE_LENGTH + "d",glEntrySummary.getProductTypeId()==null?0:
				Long.valueOf(glEntrySummary.getProductTypeId())));

		builder.append(String.format("%0"
				+ CoreTransactionFileRecord.MONITARY_COMPONENT_CODE_LENGTH
				+ "d", 0));

		 
		String accountNumber=glEntrySummary.getAccountNumber()==null ? "0": glEntrySummary.getAccountNumber();
		if(accountNumber.length()>12)
			accountNumber=accountNumber.substring(0,12);
		
		builder.append(getWithSuffixedZeros(accountNumber,
				CoreTransactionFileRecord.ACCOUNT_NUMBER_LENGTH));

		
		builder.append(String.format("%"
				+ CoreTransactionFileRecord.TRAN_CODE_LENGTH + "s",glEntrySummary.getTrancode()));
		
		builder.append(String.format("%"
				+ CoreTransactionFileRecord.DEBITCREDIT_LENGTH + "s",
				glEntrySummary.getTransactionAmount().getDebitOrCredit().getValue()
						.toString()));

		builder.append(String.format("%"
				+ CoreTransactionFileRecord.CURRENCY_LENGTH + "s",
				glEntrySummary.getTransactionAmount().getMoney().getCurrency()
						.getAlphabeticCode()));
		
		builder.append(String.format("%0"
				+ (CoreTransactionFileRecord.AMOUNT_DOLLAR_LENGTH+CoreTransactionFileRecord.AMOUNT_CENTS_LENGTH)+ "d",
				glEntrySummary.getTransactionAmount().getMoney().getAmount()
				.multiply(BigDecimal.valueOf(100)).longValue()));
		
		builder.append(String.format("%"
				+ CoreTransactionFileRecord.AMOUNT_SIGN_LENGTH + "s","+"));

		builder.append(String.format("%"
				+ CoreTransactionFileRecord.EFFECTIVE_DATE_LENGTH + "s",
				getFormattedDate(glEntrySummary.getEffectivateDate())));
		
		builder.append(String.format("%"
				+ CoreTransactionFileRecord.TRANSACTION_DATE_LENGTH + "s",
				getFormattedDate(glEntrySummary.getPostingDate())));
		
		builder.append(String.format("%"+CoreTransactionFileRecord.POST_STATUS_LENGTH
				+"s",glEntrySummary.getPostStatus()));
		
		builder.append(String.format("%"+CoreTransactionFileRecord.ENTRY_TYPE_LENGTH
				+"s",glEntrySummary.getSourceType()==null?"":glEntrySummary.getSourceType()));
		
		builder.append(String.format("%"+ CoreTransactionFileRecord.STATUS_LENGTH+"s",glEntrySummary.getLoanStatus()==null?"": 
				glEntrySummary.getLoanStatus()));

		builder.append(String.format("%" + CoreTransactionFileRecord.DESCRIPTION_LENGTH + "s",""));
		
		builder.append(String.format("%0"+ CoreTransactionFileRecord.XREF_TRX_ID_LENGTH+"d",glEntrySummary.getTransactionId()== null?0:glEntrySummary.getTransactionId()));
		String fileName = glEntrySummary.getFileName();
		if ( fileName != null && fileName.length()>= CoreTransactionFileRecord.FILE_NAME_LENGTH){
			fileName = fileName.substring(0, CoreTransactionFileRecord.FILE_NAME_LENGTH - 2 );
		} else if (fileName == null) {
			fileName = "";
		}
		builder.append(String.format("%"+ CoreTransactionFileRecord.FILE_NAME_LENGTH+"s",fileName));
		builder.append(LOANS_ACCOUNT_CATEGORY_FOR_GL_INTERFACE);*/
		
		return builder.toString();	
		

	}

	private String getWithSuffixedZeros(String theValue, int length) {
		StringBuffer buffer;

		if (theValue == null)
			buffer = new StringBuffer("0");

		buffer = new StringBuffer(theValue);
		while (length > buffer.length()) {
			buffer.append('0');
		}

		return buffer.toString();
	}

	private String getFormattedDate(Date theDate) {
		if (theDate == null)
			return String.format("%08d", 0);
		return String.format("%8s",
				format.format(new java.util.Date(theDate.getTimeInMillis())));
	}

	// private String addLeadingZeros(String str, int length) {
	//
	// String allZeros = String.format("%0" + length + "d", 0);
	//
	// if (str == null || str.length() == 0) {
	// return allZeros;
	// } else if (str.length() > length) {
	// return str;
	// }
	//
	// String result = allZeros.concat(str);
	// result = result.substring(str.length(), result.length());
	// return result;
	// }

	private SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");

	public static void main(String[] args) {

	}

	private static final transient Logger logger = Logger
			.getLogger(GLExtractRecordBuilder.class);
}
