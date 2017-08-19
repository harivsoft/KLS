package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.StLoanRecovery;
import com.vsoftcorp.kls.business.entity.loan.LoanRecovery;
import com.vsoftcorp.kls.dataaccess.loan.ILoanRecoveryDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;
import com.vsoftcorp.time.Date;

public class LoanRecoveryDAO implements ILoanRecoveryDAO {
	private static final Logger logger = Logger.getLogger(LoanRecoveryDAO.class);

	@Override
	public List<LoanRecovery> getLoanRecoveryByLocId(Long locId) {

		logger.info("Start: Fetching the Loan loc list from the database in getLoanRecoveryByLocId() method.");
		List<LoanRecovery> loanRecoveryList = new ArrayList<>();
		logger.info(" locId : " + locId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (locId != null) {
				Query query = em.createQuery("SELECT l FROM LoanRecovery l where l.loanLineOfCredit.id = :locId");
				query.setParameter("locId", locId);
				loanRecoveryList = query.getResultList();
			}
		}
		catch(NoResultException excp){
			logger.error("No entity found for query");
			throw new DataAccessException("No entity found for query");
		}
		catch (Exception excp) {
			logger.error("Could not fetch the loan recovery list from the "
					+ "database using customerId  Exception thrown.");
			throw new DataAccessException("Could not fetch the loan recovery list from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Loan recovery id list from the database in getLoanRecoveryByLocId() method.");
		return loanRecoveryList;
	}

	@Override
	public LoanRecovery getLoanRecovery(LoanRecovery master, boolean isCloseSession) {

		logger.info("Start: Fetching the loan recovery from the database in getLoanRecovery() method.");
		LoanRecovery loanRecovery = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long loanRecoveryId = master.getId();
			if (loanRecoveryId != null) {
				loanRecovery = em.find(LoanRecovery.class, loanRecoveryId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loanRecovery from the "
					+ "database using loanRecovery id.Exception thrown.");
			throw new DataAccessException("Could not fetch the loanRecovery from the database.", excp.getCause());
		} /*finally {
			if (isCloseSession) {
				EntityManagerUtil.closeSession();
			}
		}*/
		logger.info("End: Successfully fetched the loanRecovery from the database in getLoanRecovery() method.");
		return loanRecovery;
	}

	@Override
	public void saveLoanRecovery(LoanRecovery master) {

		logger.info("Start: Saving the loan recovery data to the database in saveLoanRecovery() method.");
		boolean flgTrans = false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if(!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			em.merge(master);
			if(flgTrans)
				em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the loan recovery"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the loan recovery data to the database.",
					excp.getCause());
		} /*finally {
			EntityManagerUtil.closeSession();
		}*/
		logger.info("End: Successfully saved the loan recovery to the database in saveLoanRecovery() method.");
	}
	@Override
	public Money getLoanRecoveryByLocIdAndDate(Long locId, Date asOnDate) {

		logger.info("Start: Fetching the Loan loc list from the database in getLoanRecoveryByLocId() method.");
		Money principalAmount = Money.ZERO;
		logger.info(" locId : " + locId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (locId != null) {
				Query query = em.createQuery("SELECT sum(l.principalPaid) FROM LoanRecovery l where l.loanLineOfCredit.id = :locId and l.recoveredDate <= :asOnDate");
				query.setParameter("locId", locId);
				query.setParameter("asOnDate", asOnDate);
				principalAmount = (Money)query.getSingleResult();
			}
		}
		catch(NoResultException excp){
			logger.error("No entity found for query");
			throw new DataAccessException("No entity found for query");
		}
		catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the loan recovery list from the "
					+ "database using customerId  Exception thrown.");
			throw new DataAccessException("Could not fetch the loan recovery list from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Loan recovery id list from the database in getLoanRecoveryByLocId() method.");
		return principalAmount;
	}
	@Override
	public String saveSTRecovery(StLoanRecovery master){
		String msg="Recovery Entry Data Saved Successfully";
		boolean flag=false;
		StLoanRecovery recoveryData=new StLoanRecovery();
		try{
			EntityManager em=EntityManagerUtil.getEntityManager();
			if(!em.getTransaction().isActive()){
				em.getTransaction().begin();
				flag=true;
			}
			if(master.getId()==null)
			em.persist(master);
			else{
				recoveryData=em.find(StLoanRecovery.class, master.getId());
				recoveryData.setStatus(master.getStatus());
				recoveryData.setRejectionRemarks(master.getRejectionRemarks());
				em.merge(recoveryData);
				if(master.getStatus().equals(LoanApplicationState.REJECTED.getValue()))
					msg="Recovery Entry Rejected";
			}
				
			if(flag)
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		return msg;
	}

	@Override
	public List<StLoanRecovery> getStLoanRecoveryData(Integer pacsId){
		List<StLoanRecovery> recoveryList=new ArrayList<StLoanRecovery>();
		try{
			EntityManager em=EntityManagerUtil.getEntityManager();
			Query query=em.createQuery("select s from StLoanRecovery s where s.pacsId=:pacsId and s.status=1");
			query.setParameter("pacsId", pacsId);
			recoveryList=(List<StLoanRecovery>)query.getResultList();
			
		}catch(NoResultException excp){
			logger.error("No entity found for query");
			throw new DataAccessException("No entity found for query");
		}
		catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the loan recovery list from the "
					+ "database using customerId  Exception thrown.");
			throw new DataAccessException("Could not fetch the loan recovery list from the database.", excp.getCause());
		}
		return recoveryList;
	}
@Override
public String saveMtLoanRecovery(LoanRecovery master){
	logger.info("Start : saveMtLoanRecovery method");
	String returnMsg="Loan Recovery Entry Data Saved Successfully";
	LoanRecovery masterData=new LoanRecovery();
	boolean flag=false;
	try{
		EntityManager em=EntityManagerUtil.getEntityManager();
		if(!em.getTransaction().isActive())
		{
			em.getTransaction().begin();
			flag=true;
		}
		if(master.getId()==null)
		em.persist(master);
		else{
			/*masterData=em.find(LoanRecovery.class, master.getId());
			master.setStatus(master.getStatus());
			em.merge(masterData);*/
			String qryStr="update LoanRecovery m set m.status=:status,m.rejectionRemarks=:rejectionRemarks where id=:id";
			Query qry=em.createQuery(qryStr);
			qry.setParameter("status", master.getStatus());
			qry.setParameter("rejectionRemarks", master.getRejectionRemarks());
			qry.setParameter("id", master.getId());
			int updateCount=qry.executeUpdate();
			if(master.getStatus().equals(LoanApplicationState.REJECTED.getValue()))
				returnMsg="Recovery Entry Rejected";
		}
		if(flag)
			em.getTransaction().commit();
				
	}catch(Exception e){
		e.printStackTrace();
	}
	logger.info("End : saveMtLoanRecovery method");
	return returnMsg;
}

@Override
public List<LoanRecovery> getMtLoanRecovery(Integer pacsId){
	logger.info("Start : getMtLoanRecovery method");
	List<LoanRecovery> recoveryList=new ArrayList<LoanRecovery>();
	try{
		EntityManager em=EntityManagerUtil.getEntityManager();
		String qryStr="select m from LoanRecovery m where m.loanLineOfCredit.account.accountProperty.pacs.id=:pacsId and m.status=1";
		Query qry=em.createQuery(qryStr);
		qry.setParameter("pacsId", pacsId);
		recoveryList=(List<LoanRecovery>)qry.getResultList();
	}catch(Exception e){
		e.printStackTrace();
	}
	logger.info("End : getMtLoanRecovery method");
	return recoveryList;
}
}
