package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementSchedule;
import com.vsoftcorp.kls.dataaccess.loan.ILoanDisbursementScheduleDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
/**
 * 
 * @author a1565
 *
 */
public class LoanDisbursementScheduleDAO implements ILoanDisbursementScheduleDAO{

	private static final Logger logger = Logger.getLogger(LoanDisbursementScheduleDAO.class);
	
	@Override
	public void saveLoanDisbursementSchedule(LoanDisbursementSchedule master) {
		// TODO Auto-generated method stub
		logger.info("Start : Calling LoanDisbursementScheduleDAO in saveLoanDisbursementSchedule() method.");
		EntityManager em = EntityManagerUtil.getEntityManager();

		try{
			em.merge(master);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Unable to commit the transaction of LoanDisbursementSchedule data in database Exception thrown.");
			throw new DataAccessException("Unable to commit the transaction of saving the LoanDisbursementSchedule data.",
					e.getCause());
		}
		logger.info("End : Successfully Completed Calling LoanDisbursementScheduleDAO in saveLoanDisbursementSchedule() method.");

	}

	//DAO To get DisbursementSchedule data
	@Override
	public List<LoanDisbursementSchedule> getDisbursementSchedule(Long lineOfCreditId) {
		// TODO Auto-generated method stub
		logger.info("Start : Calling LoanDisbursementScheduleDAO in getDisbursementSchedule() method."+lineOfCreditId);
		List<LoanDisbursementSchedule> list=new ArrayList<LoanDisbursementSchedule>();
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			Query query = em.createQuery("SELECT l FROM LoanDisbursementSchedule l where l.loanDisbursmentCompositeId.lineOfCreditId ="+lineOfCreditId+" Order By l.loanDisbursmentCompositeId.noOfDisbursement ");
//			query.setParameter("lineOfCreditId", lineOfCreditId);
			list=query.getResultList();
		}
		catch(Exception e){
			e.getMessage();
			logger.info("Error While Getting Disbursement List");
			throw new DataAccessException("Error While Getting Disbursement List"+e.getCause());			
		}
		
		logger.info("End : Successfully Completed Calling LoanDisbursementScheduleDAO in getDisbursementSchedule() method.");
		
		return list;
	}
	
//DAO for  deleting  NonDisbursedSchedule Data 
	@Override
	public void deleteNonDisbursedSchedule(Long lineOfCreditId) {

		logger.info("Start: Checking the LoanDisbursementSchedule  data based on Disbussed Amount from the database in deleteLoanDisbursementSchedule() method.");
		
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info("In deleteLoanDisbursementSchedule()" + lineOfCreditId );
			
			if (lineOfCreditId != null) {
				Query query = em
						.createQuery("delete from LoanDisbursementSchedule l where l.loanDisbursmentCompositeId.lineOfCreditId=:lineOfCreditId and disbursedAmount=:isZero");
				query.setParameter("lineOfCreditId", lineOfCreditId);
				query.setParameter("isZero", Money.ZERO);

//				em.getTransaction().begin();
				query.executeUpdate();
//				em.getTransaction().commit();

				logger.info("LineOfCredit  Records are deleted Succesfully By comparing LineOfCreditId and DisbursedAmount");
			}
			else
				logger.info("Line OF Credit Id is null");

		} catch (Exception excp) {
			logger.error("Unable to delete  LoanDisbursementSchedule records data from the "
					+ "database using LoanDisbursementSchedule locid and disbussedAmount .Exception thrown.");
			throw new DataAccessException(
					"Unable to delete  LoanDisbursementSchedule data from the database.",
					excp.getCause());
		}
	}


	

	
}
