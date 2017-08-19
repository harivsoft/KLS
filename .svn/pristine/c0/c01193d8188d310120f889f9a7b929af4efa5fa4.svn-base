package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.BorrowingProduct;
import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.business.entity.account.BorrowingsAccountProperty;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.BorrowingsAccountPropertyData;
import com.vsoftcorp.kls.valuetypes.AccountStatus;
import com.vsoftcorp.kls.valuetypes.account.AccountType;
import com.vsoftcorp.kls.valuetypes.account.OperatingInstructionsType;
import com.vsoftcorp.kls.valuetypes.account.OperatorType;
import com.vsoftcorp.kls.valuetypes.account.SanctionAuthority;
import com.vsoftcorp.kls.valuetypes.loan.LoanType;

public class BorrowingsAccountPropertyHelper {

	public static BorrowingsAccountProperty getBorrowingsAccountProperty(BorrowingsAccountPropertyData data) {
		BorrowingsAccountProperty master = new BorrowingsAccountProperty();
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
		
		AccountProperty acctProperty = new AccountProperty();
		Branch branch = new Branch();
		branch.setId(data.getBranchId());// data.getBranchId()
		acctProperty.setBranch(branch);
		if (data.getCustomerId() != null) {
			// Customer customer = new Customer();
			// customer.setId(data.getCustomerId());
			acctProperty.setCustomerId(data.getCustomerId());
		}
		Pacs pacs = new Pacs();
		pacs.setId(data.getPacsId());
		acctProperty.setPacs(pacs);
		master.getAccount().setAccountProperty(acctProperty);
		
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
		if (data.getResolutionNumber() != null)
			master.setResolutionNumber(data.getResolutionNumber());
		if (data.getRemarks() != null)
			master.setRemarks(data.getRemarks());
		if (data.getName() != null)
			master.setName(data.getName());
		if (data.getLenderName() != null)
			master.setLenderName(data.getLenderName());
		if (data.getCcbAccountNumber() != null)
			master.setCcbAccountNumber(data.getCcbAccountNumber());
		if (data.getBorrowingType() != null)
			master.setBorrowingType(LoanType.getType(data.getBorrowingType()));
		if (data.getPurposeId() != null) {
			Purpose purpose = new Purpose();
			purpose.setId(data.getPurposeId());
			master.setPurpose(purpose);
		}
		if (data.getBorrowingProductId() != null) {
			BorrowingProduct borrowingProduct = new BorrowingProduct();
			borrowingProduct.setId(data.getBorrowingProductId());
			master.setBorrowingProduct(borrowingProduct);
		}
		if (data.getInterestCategoryId() != null) {
			InterestCategory interestCategory = new InterestCategory();
			interestCategory.setId(data.getInterestCategoryId());
			master.setInterestCategory(interestCategory);
		}
		if (data.getSanctionAmount() != null)
			master.setSanctionedAmount(Money.valueOf(Currency.getInstance("INR"), data.getSanctionAmount()));
		if (data.getSanctionDate() != null)
			master.setSanctionedDate(DateUtil.getVSoftDateByString(data.getSanctionDate()));
		if (data.getSanctionAuthority() != null)
			master.setSanctionAuthority(SanctionAuthority.getType(data.getSanctionAuthority()));
		return master;
	}

	public static BorrowingsAccountPropertyData getBorrowingsAccountPropertyData(
			BorrowingsAccountProperty borrowingsAccountProperty) {
		BorrowingsAccountPropertyData acctPropertyData = new BorrowingsAccountPropertyData();
		if (borrowingsAccountProperty != null) {
			acctPropertyData.setId(borrowingsAccountProperty.getId());
			if (borrowingsAccountProperty.getAccountType() != null)
				acctPropertyData.setAccountType(borrowingsAccountProperty.getAccountType().getValue());
			Account account = borrowingsAccountProperty.getAccount();
			if (account != null) {
				acctPropertyData.setAccountId(account.getId());
				acctPropertyData.setOpenDate(DateUtil.getDateString(account.getOpenDate()));
				if (account.getCloseDate() != null) {
					acctPropertyData.setCloseDate(DateUtil.getDateString(account.getCloseDate()));
				}
				acctPropertyData.setStatus(account.getStatus().toString());
				acctPropertyData.setAccountNumber(account.getAccountNumber());
			}
			if (borrowingsAccountProperty.getAccount().getAccountProperty().getBranch() != null)
				acctPropertyData.setBranchId(borrowingsAccountProperty.getAccount().getAccountProperty().getBranch()
						.getId());
			if (borrowingsAccountProperty.getAccount().getAccountProperty().getCustomerId() != null)
				acctPropertyData.setCustomerId(borrowingsAccountProperty.getAccount().getAccountProperty()
						.getCustomerId());
			if (borrowingsAccountProperty.getOperatingInstructionsType() != null)
				acctPropertyData.setOperatingInstructions(borrowingsAccountProperty.getOperatingInstructionsType()
						.getValue());
			if (borrowingsAccountProperty.getOperatorType() != null)
				acctPropertyData.setOperator(borrowingsAccountProperty.getOperatorType().getValue());
			if (borrowingsAccountProperty.getAccount().getAccountProperty().getPacs() != null)
				acctPropertyData.setPacsId(borrowingsAccountProperty.getAccount().getAccountProperty().getPacs()
						.getId());
			if (borrowingsAccountProperty.getProduct() != null) {
				acctPropertyData.setProductId(borrowingsAccountProperty.getProduct().getId());
				acctPropertyData.setProductName(borrowingsAccountProperty.getProduct().getName());
			}
			if (borrowingsAccountProperty.getRemarks() != null)
				acctPropertyData.setRemarks(borrowingsAccountProperty.getRemarks());
			if (borrowingsAccountProperty.getResolutionNumber() != null)
				acctPropertyData.setResolutionNumber(borrowingsAccountProperty.getResolutionNumber());
			if (borrowingsAccountProperty.getResolutionDate() != null)
				acctPropertyData
						.setResolutionDate(DateUtil.getDateString(borrowingsAccountProperty.getResolutionDate()));
			if (borrowingsAccountProperty.getName() != null)
				acctPropertyData.setName(borrowingsAccountProperty.getName());
			if (borrowingsAccountProperty.getCcbAccountNumber() != null)
				acctPropertyData.setCcbAccountNumber(borrowingsAccountProperty.getCcbAccountNumber());
			if (borrowingsAccountProperty.getLenderName() != null)
				acctPropertyData.setLenderName(borrowingsAccountProperty.getLenderName());
			if (borrowingsAccountProperty.getBorrowingType() != null)
				acctPropertyData.setBorrowingType(borrowingsAccountProperty.getBorrowingType().getValue());
			if (borrowingsAccountProperty.getPurpose() != null) {
				acctPropertyData.setPurposeId(borrowingsAccountProperty.getPurpose().getId());
				acctPropertyData.setPurposeName(borrowingsAccountProperty.getPurpose().getName());
			}
			if (borrowingsAccountProperty.getBorrowingProduct() != null) {
				acctPropertyData.setBorrowingProductId(borrowingsAccountProperty.getBorrowingProduct().getId());
				acctPropertyData.setBorrowingProductName(borrowingsAccountProperty.getBorrowingProduct().getName());
			}
			if (borrowingsAccountProperty.getInterestCategory() != null) {
				acctPropertyData.setInterestCategoryId(borrowingsAccountProperty.getInterestCategory().getId());
				acctPropertyData.setInterestCategoryName(borrowingsAccountProperty.getInterestCategory()
						.getIntrCategoryDesc());
			}
			if (borrowingsAccountProperty.getSanctionedAmount() != null)
				acctPropertyData.setSanctionAmount(borrowingsAccountProperty.getSanctionedAmount().getAmount()
						.setScale(2).toString());
			if (borrowingsAccountProperty.getSanctionedDate() != null)
				acctPropertyData.setSanctionDate(DateUtil.getDateString(borrowingsAccountProperty.getSanctionedDate()));
			if (borrowingsAccountProperty.getSanctionAuthority() != null)
				acctPropertyData.setSanctionAuthority(borrowingsAccountProperty.getSanctionAuthority().getValue());
		}
		return acctPropertyData;
	}
}
