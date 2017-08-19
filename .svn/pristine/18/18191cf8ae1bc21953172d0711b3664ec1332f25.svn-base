package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.business.entity.account.LoanAccountProperty;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.LoanAccountPropertyData;
import com.vsoftcorp.kls.data.LoggedInUserDetailsData;
import com.vsoftcorp.kls.valuetypes.AccountStatus;
import com.vsoftcorp.kls.valuetypes.account.AccountType;
import com.vsoftcorp.kls.valuetypes.account.OperatingInstructionsType;
import com.vsoftcorp.kls.valuetypes.account.OperatorType;

public class LoanAccountPropertyHelper {

	public static LoanAccountProperty getLoanAccountProperty(LoanAccountPropertyData data) {

		LoanAccountProperty master = new LoanAccountProperty();
		if (data.getId() != null)
			master.setId(data.getId());
		Account account = new Account();
		account.setId(data.getAccountId());
		String openDate = data.getOpenDate();
		if (openDate != null) {
			account.setOpenDate(DateUtil.getVSoftDateByString(openDate));
		}
		String closeDate = data.getCloseDate();
		if (closeDate != null) {
			account.setCloseDate(DateUtil.getVSoftDateByString(closeDate));
		}
		String status = data.getStatus();
		if (status != null) {
			account.setStatus(AccountStatus.getType(status));
		} else {
			account.setStatus(AccountStatus.Active);
		}

		master.setAccount(account);
		AccountProperty accountProperty = new AccountProperty();
		LoggedInUserDetailsData loggedInUserData = data.getLoggedInUserDetailsData();
		if (loggedInUserData != null) {
			Branch branch = new Branch();
			String branchId = loggedInUserData.getBranchId();
			if (branchId != null) {
				branch.setId(new Integer(branchId));
			}
			accountProperty.setBranch(branch);
			Pacs pacs = new Pacs();
			String pacsId = loggedInUserData.getPacsId();
			if (pacsId != null) {
				pacs.setId(new Integer(pacsId));
			}
			accountProperty.setPacs(pacs);
		}

		if (data.getCustomerId() != null) {
			// Customer customer = new Customer();
			// customer.setId(data.getCustomerId());
			accountProperty.setCustomerId(data.getCustomerId());
		}
		master.getAccount().setAccountProperty(accountProperty);
		
		if (data.getProductId() != null) {
			Product product = new Product();
			product.setId(data.getProductId());
			master.setProduct(product);
		}
		String operatingInstructions = data.getOperatingInstructions();
		if (operatingInstructions != null) {
			master.setOperatingInstructionsType(OperatingInstructionsType.getType(operatingInstructions));
		}
		String accountType = data.getAccountType();
		if (accountType != null) {
			master.setAccountType(AccountType.getType(accountType));
		}
		String operatorType = data.getOperator();
		if (operatorType != null) {
			master.setOperatorType(OperatorType.getType(operatorType));
		}
		String resolutionDate = data.getResolutionDate();
		if (resolutionDate != null) {
			master.setResolutionDate(DateUtil.getVSoftDateByString(resolutionDate));
		}

		master.setResolutionNumber(data.getResolutionNumber());
		master.setRemarks(data.getRemarks());
		return master;
	}

	public static LoanAccountPropertyData getLoanAccountPropertyData(LoanAccountProperty loanAccountProperty) {

		LoanAccountPropertyData pacsAcctPropertyData = new LoanAccountPropertyData();
		try{
		if (loanAccountProperty != null) {
			pacsAcctPropertyData.setId(loanAccountProperty.getId());
			if (loanAccountProperty.getAccountType() != null)
				pacsAcctPropertyData.setAccountType(loanAccountProperty.getAccountType().getValue());
			Account account = loanAccountProperty.getAccount();
			if (account != null) {
				pacsAcctPropertyData.setAccountId(account.getId());
				pacsAcctPropertyData.setOpenDate(DateUtil.getDateString(account.getOpenDate()));
				if (account.getCloseDate() != null) {
					pacsAcctPropertyData.setCloseDate(DateUtil.getDateString(account.getCloseDate()));
				}
				pacsAcctPropertyData.setStatus(account.getStatus().toString());
			}
			pacsAcctPropertyData.setBranchId(loanAccountProperty.getAccount().getAccountProperty().getBranch().getId());
			pacsAcctPropertyData.setCustomerId(loanAccountProperty.getAccount().getAccountProperty().getCustomerId());
			if (loanAccountProperty.getOperatingInstructionsType() != null)
				pacsAcctPropertyData.setOperatingInstructions(loanAccountProperty.getOperatingInstructionsType()
						.getValue());
			if (loanAccountProperty.getOperatorType() != null)
				pacsAcctPropertyData.setOperator(loanAccountProperty.getOperatorType().getValue());
			if (loanAccountProperty.getAccount().getAccountProperty().getPacs() != null)
				pacsAcctPropertyData.setPacsId(loanAccountProperty.getAccount().getAccountProperty().getPacs().getId());
			if (loanAccountProperty.getProduct() != null)
				pacsAcctPropertyData.setProductId(loanAccountProperty.getProduct().getId());
			if (loanAccountProperty.getRemarks() != null)
				pacsAcctPropertyData.setRemarks(loanAccountProperty.getRemarks());
			if (loanAccountProperty.getResolutionNumber() != null)
				pacsAcctPropertyData.setResolutionNumber(loanAccountProperty.getResolutionNumber());
			if (loanAccountProperty.getResolutionDate() != null)
				pacsAcctPropertyData.setResolutionDate(DateUtil.getDateString(loanAccountProperty.getResolutionDate()));
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return pacsAcctPropertyData;
	}
}
