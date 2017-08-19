/**
 * 
 */
package com.vsoftcorp.kls.service.account.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.bcel.Repository;
import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.data.AccountData;
import com.vsoftcorp.kls.data.SBAccountMappingData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;
import com.vsoftcorp.kls.dataaccess.loan.IAccountPropertyDAO;
import com.vsoftcorp.kls.service.account.IAccountPropertyService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.SBAccountMappingHelper;
import com.vsoftcorp.kls.service.loan.IPacsLoanSanctionProcessService;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.SBAccountStatus;

/**
 * @author a9152
 * 
 */
public class AccountPropertyService implements IAccountPropertyService {

	private static final Logger logger = Logger.getLogger(AccountPropertyService.class);

	@Override
	public boolean checkIfAccountExists(String custId) {

		logger.info("Start: checking the account using cust id in checkIfAccountExists() method.");
		IAccountPropertyDAO dao = KLSDataAccessFactory.getAccountPropertyDAO();
		boolean flag = false;
		logger.info(" custId : " + custId);
		try {
			flag = dao.checkIfAccountExists(custId, false);
		} catch (Exception e) {
			logger.error("Error while retriving the account based on cust id from the database");
			throw new KlsRuntimeException("Error while retriving the account based on cust id from the database",
					e.getCause());
		}
		logger.info("End: checking the account using cust id in checkIfAccountExists() method.");
		return flag;
	}

	@Override
	public List<String> getAccountNumberListByPacsId(Integer pacsId) {
		// TODO Auto-generated method stub
		
		logger.info("Start: Calling getAccountNumberListByPacsId  method to get AccountNumberList Based on PacsId.");
		logger.info("PacsId : " + pacsId);
		IAccountDAO dao = KLSDataAccessFactory.getAccountDAO();
		
		List<String> accountNumberList=new ArrayList<String>();
		try {
			
			accountNumberList = dao.getAccountNumbersByPacsId(pacsId);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving the accountNumberList based on PacsId from the database");
			throw new KlsRuntimeException("Error while retriving the accountNumberList based on PacsId from the database"+e.getCause());
		}
		logger.info("End: Successfully Completed Calling getAccountNumberListByPacsId  method to get AccountNumberList Based on PacsId.");
		return accountNumberList;
	}
	
	@Override
	public AccountData getAccountInfoByCustId(Long custId) {
		logger.info("Start: Fetching the AccountProperty from the database in getAccountBycustId() method.");

		AccountProperty accProperty = null;
		Account acc = null;
		AccountData accData = new AccountData();
		try {
			IAccountPropertyDAO dao = KLSDataAccessFactory.getAccountPropertyDAO();
			IAccountDAO accDao = KLSDataAccessFactory.getAccountDAO();

			accProperty = dao.getAccountByCustId(custId);
			acc = accDao.getAccountByCustomer(custId.toString());
			
			if (accProperty == null || acc == null ){
				//accData.setErrorMssg("Account does not exist for give customer");
				return null;
			}else{
				accData.setSavingBankAccNo(accProperty.getSavingsAccountNumber());
				accData.setAccountNo(acc.getAccountNumber());
				accData.setAccountId(acc.getId());
			}
			
		} catch (Exception e) {
			logger.error("Could not fetch the AccountProperty from the database using customerId  Exception thrown.");
			//throw new DataAccessException("Could not fetch the AccountProperty from the database.", e.getCause());
			throw e;
		}
		logger.info("End: Fetching the AccountProperty from the database in getAccountBycustId() method.");

		return accData;
	}

	@Override
	public String mapSavingsAccountWithCust(SBAccountMappingData data) {
		logger.info("Start: Mapping the savings account wiht AccountProperty from the database in mapSavingsAccountWithCust() method.");
		String responce="SB Account not assigned";
		boolean isAccountExist; 
		boolean flag = true;
		try {
			IAccountPropertyDAO dao = KLSDataAccessFactory.getAccountPropertyDAO();
			IPacsLoanSanctionProcessService loanService = KLSServiceFactory.getPacsLoanSanctionProcessService();
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			dao.saveSBAccountMappingInfo(SBAccountMappingHelper.getSbAccountMapping(data));
			if(SBAccountStatus.Not_Assigned_Not_Found_In_CBS.getValue() != data.getStatus() && SBAccountStatus.Not_Assigned_No_Acccount_Exist_In_CBS.getValue() != data.getStatus()){
				//checking whether SB account already assigned to any customer or not
				AccountProperty accountProperty=dao.getAccountProperty(data.getSavingsAccountNo(), false);
				if(accountProperty == null){//SB account not assigned
					//checking Account property exist or not
					isAccountExist=dao.checkIfAccountPropertyExists(data.getPartyId(), true);
					if(!isAccountExist){
						//creating new account property if not exists
						loanService.createAccount(data.getPartyId().toString(), data.getPacsId().toString());
					}
					responce=dao.mapSavingsAccountWithCust(data.getPartyId(), data.getSavingsAccountNo());
					//updating SB account number in PartyBankInfo if user selects override or not matched
					if(SBAccountStatus.Assign_Matched.getValue() != data.getStatus()){
						responce=RestClientUtil.updatePartyBankInfo(data.getPartyId(), data.getSavingsAccountNo());
						if("YES".equalsIgnoreCase(responce)){
							responce="SB Account is assigned successfully";
						}else{
							responce="Error while assigning SB Account to customer";
							flag=false;
						}
					}
				}else{//SB account already assigned
					PersonData person = RestClientUtil.getAllCustomerDataByCusomterId(data.getPartyId());
					responce="SB Account: "+data.getSavingsAccountNo()+" is already assigned to customer : "+person.getDisplayName()+" with member number: "+person.getMemberNumber();
				}
			}
			if(SBAccountStatus.Unassign.getValue() == data.getStatus()){
				responce="SB Account is unassigned successfully";
			}
			if(flag)
				em.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Could not assign the savings account wiht AccountProperty from the database in from the database using customerId  Exception thrown.");
			responce="Error while assigning savings account to customer";
			throw e;
		}
		logger.info("End: Mapping the savings account wiht AccountProperty from the database in mapSavingsAccountWithCust() method.");
		return responce;
	}
	
	@Override
	public String getSbByAtmLoanAccount(String atmLoanAccount) {

		logger.info("Start:fetching SB account by ATMLoanAccount in getSbByAtmLoanAccount() method.");
		String sbAccount =null;
		
		try {
			IAccountPropertyDAO adao = KLSDataAccessFactory.getAccountPropertyDAO();
			AccountProperty accountProperty = adao.getAccountPropertyByAtmLoanAccount(atmLoanAccount, false);
			if(accountProperty != null){
				sbAccount = accountProperty.getSavingsAccountNumber();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("End:fetching SB account by ATMLoanAccount in getSbByAtmLoanAccount() method.");
		return sbAccount;
	}
}
