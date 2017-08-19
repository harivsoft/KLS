package com.vsoftcorp.kls.service.transaction;

import com.vsoftcorp.kls.data.LoanRecoveryData;
import com.vsoftcorp.kls.data.PacsLoanDisbursementData;
import com.vsoftcorp.kls.data.gateway.datahelpers.KLSResponse;

public interface IPostVirmatiCBSTransactions {
	
	public boolean postVirmatiCBSDebitTransactions(KLSResponse klsResponse,String accountNumber,boolean isStandalone,String modeOfDisbursement);
	
	public boolean postVirmatiCBSCebitTransactions(KLSResponse klsResponse,String accountNumber,boolean isStandalone,String modeOfDisbursement);
	
	public boolean postVirmatiCBSDebitTransactionsForMT(PacsLoanDisbursementData pacsLoanDisbursementData);
	
	public boolean postVirmatiCBSCebitTransactionsForMT(LoanRecoveryData loanRecoveryData,boolean isStandalone);

}
