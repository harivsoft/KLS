package com.vsoftcorp.kls.data;

import java.math.BigDecimal;
import java.util.List;

public class LoanDisbursementEntryList 
{
	private String instrumentNumber;
	private String additionalInformation;
	private BigDecimal totDisburseAmt;
	private List<LoanDisbursementEntryData> listLoanDisbursementEntryData; 
	public String getInstrumentNumber() {
		return instrumentNumber;
	}
	public void setInstrumentNumber(String instrumentNumber) {
		this.instrumentNumber = instrumentNumber;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	public List<LoanDisbursementEntryData> getListLoanDisbursementEntryData() {
		return listLoanDisbursementEntryData;
	}
	public void setListLoanDisbursementEntryData(
			List<LoanDisbursementEntryData> listLoanDisbursementEntryData) {
		this.listLoanDisbursementEntryData = listLoanDisbursementEntryData;
	}
	public BigDecimal getTotDisburseAmt() {
		return totDisburseAmt;
	}
	public void setTotDisburseAmt(BigDecimal totDisburseAmt) {
		this.totDisburseAmt = totDisburseAmt;
	}
	
	
	
	

}


