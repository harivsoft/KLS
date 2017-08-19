package com.vosftcorp.kls.report.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.report.service.data.LoanRegisterData;

public class LoanRegisterReportHelper {
	
	public static List<LoanRegisterData> getLoanRegisterDataList(List<Object[]> loanAccList, String businessDate,  String loanType) {
		
		List<LoanRegisterData> loanRegisterDataList = new ArrayList<LoanRegisterData>();
		
		LoanRegisterData data = null;
		
		for(Object[] object : loanAccList) {
			data = new LoanRegisterData();
			if("L".equals(loanType)) {
				data.setCustomerName((String) object[0]);
				data.setFatherName((String) object[1]);
				data.setMemberNumber((String) object[2]);
				data.setAccountNumber((String) object[3]);
				data.setLocNumber((BigInteger) object[4]);
				data.setCrop((String) object[6]);

				if(object[7] != null) {
					data.setActivity((String) object[7]);
				}

				data.setVillageName((String) object[8]);

				if(object[9] != null) {
					data.setPurpose((String)object[9]);
				}
				if(object[10] != null) {
					data.setSanctionDate((String)object[10].toString());
				}
				if(object[11] != null) {
					data.setSanctionAmount(((BigDecimal)object[11]).setScale(2).toString());
				}

				if(object[12] != null) {
					data.setLoanPeriod((Integer)object[12]);
				}
				if(object[13] != null) {
					data.setInstallmentAmount(((BigDecimal)object[13]).setScale(2).toString());
				}
				if(object[14] != null) {
					data.setDisbursementAmount(((BigDecimal)object[14]).setScale(2).toString());
				}
				if(object[15] != null) {
					data.setDateOfDisbursement((String)object[15].toString());
				}
			} else if("C".equals(loanType)) {
				
				data.setCustomerName((String) object[0]);
				data.setFatherName((String) object[1]);
				data.setMemberNumber((String) object[2]);
				data.setAccountNumber((String) object[3]);
				data.setLocNumber((BigInteger) object[4]);
				data.setCrop((String) object[6]);

				data.setVillageName((String) object[7]);
				if(object[8] != null) {
					data.setSanctionDate((String)object[8].toString());
				}
				if(object[9] != null) {
					data.setSanctionAmount(((BigDecimal)object[9]).setScale(2).toString());
				}
				if(object[10] != null) {
					data.setDisbursementAmount(((BigDecimal)object[10]).setScale(2).toString());
				}
				
				if(object[11] != null) {
					data.setSeasonId((BigInteger)object[11]);
				}
				if(object[12] != null) {
					data.setSeasonName((String) object[12]);
				}
			}
			loanRegisterDataList.add(data);
		}
		
		return loanRegisterDataList;
	}
	
	
public static List<LoanRegisterData> getLoanRegisterSummeryDataList(List<Object[]> loanAccList, String businessDate,  String loanType) {
		
		List<LoanRegisterData> loanRegisterSummeryDataList = new ArrayList<LoanRegisterData>();
		
		LoanRegisterData data = null;
		
		for(Object[] object : loanAccList) {
			data = new LoanRegisterData();
			if("L".equals(loanType)) {
				data.setCustomerName((String) object[0]);
				data.setFatherName((String) object[1]);
				data.setMemberNumber((String) object[2]);
				data.setAccountNumber((String) object[3]);
				//data.setLocNumber((BigInteger) object[4]);
				data.setCrop((String) object[4]);

				if(object[6] != null) {
					data.setActivity((String) object[6]);
				}

				data.setVillageName((String) object[7]);

				if(object[8] != null) {
					data.setPurpose((String)object[8]);
				}
				if(object[9] != null) {
					data.setSanctionDate((String)object[9].toString());
				}
				if(object[10] != null) {
					data.setSanctionAmount(((BigDecimal)object[10]).setScale(2).toString());
				}

				if(object[11] != null) {
					data.setLoanPeriod((Integer)object[11]);
				}
				if(object[12] != null) {
					data.setInstallmentAmount(((BigDecimal)object[12]).setScale(2).toString());
				}
				if(object[13] != null) {
					data.setDisbursementAmount(((BigDecimal)object[13]).setScale(2).toString());
				}
				if(object[14] != null) {
					data.setDateOfDisbursement((String)object[14].toString());
				}
			} else if("C".equals(loanType)) {
				
				data.setCustomerName((String) object[0]);
				data.setFatherName((String) object[1]);
				data.setMemberNumber((String) object[2]);
				data.setAccountNumber((String) object[3]);
				//data.setLocNumber((BigInteger) object[4]);
				//data.setCrop((String) object[4]);

				data.setVillageName((String) object[4]);
				if(object[5] != null) {
					data.setSanctionDate((String)object[5].toString());
				}
				if(object[6] != null) {
					data.setSanctionAmount(((BigDecimal)object[6]).setScale(2).toString());
				}
				if(object[7] != null) {
					data.setDisbursementAmount(((BigDecimal)object[7]).setScale(2).toString());
				}
				
				if(object[8] != null) {
					data.setSeasonId((BigInteger)object[8]);
				}
				if(object[9] != null) {
					data.setSeasonName((String) object[9]);
				}
			}
			loanRegisterSummeryDataList.add(data);
		}
		
		return loanRegisterSummeryDataList;
	}


}
