package com.vsoftcorp.kls.service.account.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.business.entity.account.BorrowingsAccountProperty;
import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.data.BorrowingsLineOfCreditData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IBorrowingsAccountPropertyDAO;
import com.vsoftcorp.kls.dataaccess.loan.IBorrowingsLineOfCreditDAO;
import com.vsoftcorp.kls.service.account.IBorrowingsLineOfCreditService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.BorrowingsLineOfCreditHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

/**
 * 
 * @author a1605
 * 
 */
public class BorrowingsLineOfCreditService implements IBorrowingsLineOfCreditService {

	private static final Logger logger = Logger.getLogger(BorrowingsLineOfCreditService.class);

	IBorrowingsLineOfCreditDAO borrwoingsLOCDAO = KLSDataAccessFactory.getBorrowingsLineOfCreditDAO();

	@Override
	public void saveBorrowingLineOfCredit(BorrowingsLineOfCredit borrowingsLineOfCredit) {
		logger.info("Start:Saving borrowing line of credit");

		try {
			borrwoingsLOCDAO.saveBorrowingsLineOfCredit(borrowingsLineOfCredit);
		} catch (Exception e) {
			logger.error("Eror: while Saving borrowing line of credit");
			e.printStackTrace();
		}
		logger.info("End:Saving borrowing line of credit");

	}

	@Override
	public BorrowingsLineOfCredit createBorrowingLineOfCredit(LineOfCredit lineOfCredit) {
		logger.info("Start:creating borrowing line of credit from line of credit, inside createBorrowingLineOfCredit() ");

		if (lineOfCredit == null) {
			throw new KlsRuntimeException("Unable to create borrowings loc as line of credit is null");
		}

		BorrowingsAccountProperty borrowingsAccountProperty = null;
		BorrowingsLineOfCredit borrowingsLineOfCredit = new BorrowingsLineOfCredit();

		try {
			if (lineOfCredit instanceof LoanLineOfCredit) {

				return createBorrowingLineOfCredit((LoanLineOfCredit) lineOfCredit);
			}

			IBorrowingsAccountPropertyDAO bAccountPropertyDAO = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO();

			borrowingsAccountProperty = bAccountPropertyDAO.getAccountPropertyByProductId(lineOfCredit
					.getProduct().getId());

			borrowingsLineOfCredit.setCurrentBalance(AccountingMoney.ZERO);
			borrowingsLineOfCredit.setDrawalPriority(lineOfCredit.getDrawalPriority());
			borrowingsLineOfCredit.setSanctionedAmount(lineOfCredit.getSanctionedAmount());
			borrowingsLineOfCredit.setSanctionedDate(lineOfCredit.getSanctionedDate());

			/*** join columns **/
			borrowingsLineOfCredit.setAccount(borrowingsAccountProperty.getAccount());
			borrowingsLineOfCredit.setCrop(lineOfCredit.getCrop());
			borrowingsLineOfCredit.setSeason(lineOfCredit.getSeason());
			borrowingsLineOfCredit.setInterestCategory(borrowingsAccountProperty.getInterestCategory());
			borrowingsLineOfCredit.setScheme(lineOfCredit.getScheme());

			/*** join columns **/

			borrowingsLineOfCredit.setLastInterestCalculatedDate(lineOfCredit.getLastInterestCalculatedDate());
			borrowingsLineOfCredit.setOverdueInterest(lineOfCredit.getOverdueInterest());
			borrowingsLineOfCredit.setLineOfCreditStatus(lineOfCredit.getLineOfCreditStatus());
			borrowingsLineOfCredit.setIsFirstDrawal(lineOfCredit.getIsFirstDrawal());
			borrowingsLineOfCredit.setInterestAccrued(lineOfCredit.getInterestAccrued());
			borrowingsLineOfCredit.setInterestReceivable(lineOfCredit.getInterestReceivable());
			borrowingsLineOfCredit.setPenalInterestReceivable(lineOfCredit.getPenalInterestReceivable());
			borrowingsLineOfCredit.setKindLimit(lineOfCredit.getKindLimit());
			borrowingsLineOfCredit.setKindBalance(lineOfCredit.getKindBalance());
			borrowingsLineOfCredit.setOperatingCashLimit(lineOfCredit.getOperatingCashLimit());

			/*** Additional fields ***/

			// borrowingsLineOfCredit.setLoanExpiryDate();
			// borrowingsLineOfCredit.setFirstDueDate();
			/*** join columns **/
			borrowingsLineOfCredit.setCustomerId(borrowingsAccountProperty.getAccount().getAccountProperty().getCustomerId());
			// borrowingsLineOfCredit.setPacsLoanApplication();
			borrowingsLineOfCredit.setLoanAccountProperty(borrowingsAccountProperty);
			borrowingsLineOfCredit.setProduct(borrowingsAccountProperty.getProduct());

		} catch (Exception e) {
			logger.error("Error while creating borrowing loc from line of credit. " + e.getMessage());
			if (e instanceof KlsRuntimeException)
				throw e;
			throw new KlsRuntimeException("Error while creating borrowing loc from line of credit", e.getCause());
		}
		logger.info("End:creating borrowing line of credit from line of credit, inside createBorrowingLineOfCredit() ");
		return borrowingsLineOfCredit;
	}

	@Override
	public BorrowingsLineOfCredit createBorrowingLineOfCredit(LoanLineOfCredit loanLineOfCredit) {
		logger.info("Start:creating borrowing loan line of credit from line of credit, inside createBorrowingLineOfCredit() ");

		if (loanLineOfCredit == null) {
			throw new KlsRuntimeException("Unable to create borrowings loc as loan line of credit is null");
		}
		BorrowingsAccountProperty borrowingsAccountProperty = null;
		BorrowingsLineOfCredit borrowingsLineOfCredit = new BorrowingsLineOfCredit();

		try {
			IBorrowingsAccountPropertyDAO bAccountPropertyDAO = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO();
			borrowingsAccountProperty = bAccountPropertyDAO.getAccountPropertyByProductId(loanLineOfCredit.getProduct()
					.getId());

			borrowingsLineOfCredit.setCurrentBalance(AccountingMoney.ZERO);
			borrowingsLineOfCredit.setDrawalPriority(loanLineOfCredit.getDrawalPriority());
			borrowingsLineOfCredit.setSanctionedAmount(loanLineOfCredit.getSanctionedAmount());
			borrowingsLineOfCredit.setSanctionedDate(loanLineOfCredit.getSanctionedDate());
			borrowingsLineOfCredit.setLastInterestCalculatedDate(loanLineOfCredit.getLastInterestCalculatedDate());
			borrowingsLineOfCredit.setOverdueInterest(loanLineOfCredit.getOverdueInterest());

			/*** join columns **/
			borrowingsLineOfCredit.setAccount(borrowingsAccountProperty.getAccount());
			borrowingsLineOfCredit.setCrop(loanLineOfCredit.getCrop());
			borrowingsLineOfCredit.setSeason(loanLineOfCredit.getSeason());
			borrowingsLineOfCredit.setInterestCategory(borrowingsAccountProperty.getInterestCategory());
			borrowingsLineOfCredit.setScheme(loanLineOfCredit.getScheme());
			/*** join columns **/

			borrowingsLineOfCredit.setLineOfCreditStatus(loanLineOfCredit.getLineOfCreditStatus());
			borrowingsLineOfCredit.setIsFirstDrawal(loanLineOfCredit.getIsFirstDrawal());
			borrowingsLineOfCredit.setInterestAccrued(loanLineOfCredit.getInterestAccrued());
			borrowingsLineOfCredit.setInterestReceivable(loanLineOfCredit.getInterestReceivable());
			borrowingsLineOfCredit.setPenalInterestReceivable(loanLineOfCredit.getPenalInterestReceivable());
			borrowingsLineOfCredit.setKindLimit(loanLineOfCredit.getKindLimit());
			borrowingsLineOfCredit.setKindBalance(loanLineOfCredit.getKindBalance());
			borrowingsLineOfCredit.setOperatingCashLimit(loanLineOfCredit.getOperatingCashLimit());
			borrowingsLineOfCredit.setLoanExpiryDate(loanLineOfCredit.getLoanExpiryDate());
			borrowingsLineOfCredit.setFirstDueDate(loanLineOfCredit.getFirstDueDate());

			/*** join columns **/

			borrowingsLineOfCredit.setCustomerId(loanLineOfCredit.getCustomerId());
			borrowingsLineOfCredit.setPacsLoanApplication(loanLineOfCredit.getPacsLoanApplication());
			borrowingsLineOfCredit.setLoanAccountProperty(borrowingsAccountProperty);
			borrowingsLineOfCredit.setProduct(borrowingsAccountProperty.getProduct());
			/*** join columns **/
		} catch (Exception e) {
			logger.error("Error while creating borrowing loc from loan line of credit. " + e.getMessage());
			if (e instanceof DataAccessException)
				throw e;
			throw new KlsRuntimeException("Error while creating borrowing loc loan from line of credit", e.getCause());
		}

		logger.info("End:creating borrowing loan line of credit from line of credit, inside createBorrowingLineOfCredit() ");
		return borrowingsLineOfCredit;
	}
	
	@Override
    public BorrowingsLineOfCreditData getBorrowingsLineOfCreditById(Long loanLocId){
        return  BorrowingsLineOfCreditHelper.getBorrowingsLineOfCredit(borrwoingsLOCDAO.getBorrowingsLineOfCreditById(loanLocId));
    }
    
    @Override
    public List<BorrowingsLineOfCreditData> getOrderedBorrowingLocListByAccountId(Long accountId){
        return BorrowingsLineOfCreditHelper.getBorrowingsLineOfCreditList(borrwoingsLOCDAO.getOrderedBorrowingLocListByAccountId(accountId));
    }



}
