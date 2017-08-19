package com.vsoftcorp.kls.report.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.GepRep.business.NPAParameters;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.dao.INPAParameterDAO;
import com.vsoftcorp.kls.report.dao.INPAReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LineOfCreditStatus;
import com.vsoftcorp.time.Date;

public class NPAReportDAO implements INPAReportDAO {

	private static final Logger logger = Logger.getLogger(NPAReportDAO.class);

	@Override
	public List<LineOfCredit> getSTOverDueList(Date asOnDate) {

		List<LineOfCredit> overDue = new ArrayList<LineOfCredit>();
		logger.info("Start:Inside getSTOverDueList method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			Query query = em.createQuery("select loc from LineOfCredit loc,Season s where loc.season.id=s.id and "
					+ "s.overdueDate < '" + asOnDate + "' and loc.loanType='C' and loc.currentBalance < 0 and loc.lineOfCreditStatus = "+LineOfCreditStatus.Active.getValue());

			overDue = query.getResultList();
			logger.info("Overdue List in NPA---" + overDue.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getSTOverDueList method");
			throw new KlsReportRuntimeException("Can not print NPA report:", exception.getCause());
		}
		logger.info("End:Inside getSTOverDueList method");

		return overDue;
	}

	@Override
	public List<LineOfCredit> getMTOverDueList(Date asOnDate) {
		
		List<LineOfCredit> overDue = new ArrayList<LineOfCredit>();
		logger.info("Start:Inside getMTOverDueList method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			Query query = em.createQuery("select loc from LineOfCredit loc where loc.loanType='L' and loc.currentBalance < 0 "
					+ " and loc.sanctionedDate <= '"+asOnDate+"' and loc.lineOfCreditStatus = "+LineOfCreditStatus.Active.getValue());

			overDue = query.getResultList();
			logger.info("Overdue List in NPA---" + overDue.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getSTOverDueList method");
			throw new KlsReportRuntimeException("Can not print NPA report:", exception.getCause());
		}
		logger.info("End:Inside getMTOverDueList method");

		return overDue;
	}

	public Date getNpaDate(Season season, String asOnDate) {

		List<Season> seasonList = new ArrayList<Season>();
		Date npaDate =null;
		Date onDate = DateUtil.getVSoftDateByString(asOnDate);
		logger.info("Start:Inside getNpaDate method");
		try {
			INPAParameterDAO parameterDAO = KLSReportDataAccessFactory.getNPAParameterDAO();
			List<NPAParameters> npaParameters = parameterDAO.getNPAParameters();
			Integer noOfMonths=0;
			EntityManager em = EntityManagerUtil.getEntityManager();

			Query query = em.createQuery("select s from Season s where s.overdueDate < '" + onDate + 
					"' and s.drawalStartDate > (select overdueDate from Season s1 where s1.id="+season.getId()+") order by s.id");

			seasonList = query.getResultList();
			logger.info("seasonList---" + seasonList.size());
			
			logger.info("queryqueryqueryqueryquery---" + query);
			
			Integer k=seasonList.size();
			if(k>npaParameters.get(0).getNoOfSeasons()){
				k=npaParameters.get(0).getNoOfSeasons();
			}
			for(int i=0;i<k;i++){
				Season s =seasonList.get(i);
				
				noOfMonths = noOfMonths+getNoOfMonths(s.getDrawalStartDate(), s.getOverdueDate());
			}
			if(noOfMonths > npaParameters.get(0).getPeriod()){
				noOfMonths=npaParameters.get(0).getPeriod();
			}
			System.out.println("due date="+season.getOverdueDate()+"==="+noOfMonths);
			String temDate = DateUtil.getDateByAddingNoOfMonths(DateUtil.getDateString(season.getOverdueDate()), noOfMonths);
			npaDate = DateUtil.getVSoftDateByString(temDate);
			System.out.println("NPA DATE======"+npaDate);
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getNpaDate method");
			throw new KlsReportRuntimeException("Can not print NPA report:", exception.getCause());
		}
		logger.info("End:Inside getNpaDate method");

		
		return npaDate;
	}
	
	public Integer getNoOfMonths(Date dueDate,Date npaDate){
		logger.info("in getMonths");
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(dueDate.getJavaDate());
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(npaDate.getJavaDate());

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		logger.info("end getMonths");
		return diffMonth;
	}
	
	public Object[] getMemberData(Long customerId) {
		logger.info("Start: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");
		List<Object[]> ids = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" customerId : " + customerId);
				Query query = em
						.createNativeQuery("select first_name,member_number from membership.person  where party_id="+customerId);
				ids = query.getResultList();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the loan repayment schedule list from the "
					+ "database using line of credit id.Exception thrown.");

			throw new DataAccessException("Could not fetch the loan repayment schedule list from the database.",
					excp.getCause());
		}
		logger.info("End: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");
		if (ids.size() > 0)
			return ids.get(0);
		else
			return null;
	}

	public Integer getSeasonMonths(Season season, Integer noOfSeasons) {

		List<Season> seasonList = new ArrayList<Season>();
		Date npaDate =null;
		Integer noOfMonths=0;
		logger.info("Start:Inside getNpaDate method");
		try {
			
			EntityManager em = EntityManagerUtil.getEntityManager();

			Query query = em.createQuery("select s from Season s where s.drawalStartDate > '"+season.getOverdueDate()+"' order by s.id");

			seasonList = query.getResultList();
			
			logger.info("seasonList for substance---" + seasonList.size());
			Integer k=seasonList.size();
			if(k>noOfSeasons){
				k=noOfSeasons;
			}
			
			for(int i=0;i<k;i++){
				Season s =seasonList.get(i);
				noOfMonths = noOfMonths+getNoOfMonths(s.getDrawalStartDate(), s.getOverdueDate());
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getNpaDate method");
			throw new KlsReportRuntimeException("Can not print NPA report:", exception.getCause());
		}
		logger.info("End:Inside getNpaDate method");

		
		return noOfMonths;
	}
	
	public List<LoanRepaymentSchedule> getLoanRepaymentScheduleByLocIdAndBusinessDate(Long locId, Date businessDate) {
		logger.info("locId : " + locId);
		logger.info("businessDate : " + businessDate);
		logger.info("Start: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");
		List<LoanRepaymentSchedule> repaymentScheduleList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" loanLocId : " + locId);
			if (locId != null) {
				Query query = em
						.createQuery("SELECT l FROM LoanRepaymentSchedule l where "
								+ "l.loanRepaymentScheduleId.lineOfCreditId = :loanLocId and l.installmentDate <= :businessDate order by l.loanRepaymentScheduleId.installmentNumber");
				query.setParameter("loanLocId", locId);
				query.setParameter("businessDate", businessDate);
				repaymentScheduleList = query.getResultList();
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loan repayment schedule list from the "
					+ "database using line of credit id.Exception thrown.");

			throw new DataAccessException("Could not fetch the loan repayment schedule list from the database.",
					excp.getCause());
		}
		logger.info("End: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");
		return repaymentScheduleList;
	}
	
	public BigDecimal getCurrentBalanceAsOnDate(Long locId,Date asOnDate) {
		List<Object[]> transList = new ArrayList<Object[]>();
		logger.info("Start:Inside getCurrentBalanceAsOnDate method");
		BigDecimal currentBal= BigDecimal.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder
					.append("select * from (select * from kls_schema.current_transaction "
							+ "union select * from kls_schema.transaction_history ) t "
							+ "where business_date <= '"+asOnDate+"' and line_of_credit_id="+locId+" order by id desc");

			transList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("transList---" + transList.size());
			Object[] obj =null;
			if(transList.size()>0){
			obj = transList.get(0);
			BigDecimal crDr = new BigDecimal((Integer) obj[3]);
			BigDecimal openingBal = (BigDecimal) obj[4];
			BigDecimal trans_amt = (BigDecimal) obj[6];
			currentBal = openingBal.multiply(crDr).add(trans_amt);
			}
			logger.info("current bal---" + currentBal);
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getCurrentBalanceAsOnDate method");
			throw new KlsReportRuntimeException("Can not print NPA report:", exception.getCause());
		}
		logger.info("End:Inside getCurrentBalanceAsOnDate method");

		return currentBal.abs();
	}
	
	public Season getCurrentSeason(String businessDate){
		logger.info("Start: Fetching all the season master data from the database in getCurrentSeason() method.");
		List<Season> seasonMasterList = new ArrayList<Season>();
		try {
			System.out.println("ons==="+businessDate);
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query= em.createQuery("SELECT v FROM Season v where '"+businessDate+"' between v.drawalStartDate and v.drawalEndDate");
			seasonMasterList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all seasons from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all seasons from the database",
					e.getCause());
		}
		logger.info("End: Fetching all the season master data from the database in getCurrentSeason() method.");
		return seasonMasterList.get(0);
	}
	
	/* Borrowings NPA */
	
	@Override
	public List<LineOfCredit> getBorrowingSTOverDueList(Date asOnDate) {

		List<LineOfCredit> overDue = new ArrayList<LineOfCredit>();
		logger.info("Start:Inside getBorrowingsSTOverDueList method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			
			/*Query query = em.createNativeQuery("select * from kls_schema.line_of_credit where loan_type='B' and loan_account_property_id in " +
					"(select id from kls_schema.loan_account_property where account_property_type='B' and borrowing_type='ST')" ) ;
			//overDue =  query.getResultList(); 
			loanAccountProperty*/
			String hqlQuery = " select lloc from LoanLineOfCredit lloc where lloc.loanType = 'B' and lloc.loanAccountProperty in " +
					" (select bap.id from  BorrowingsAccountProperty bap where bap.accountPropertyType ='B' and bap.borrowingType='ST') ";
			logger.info("Query : "+hqlQuery);
			Query query = em.createQuery(hqlQuery);
			
			overDue =  query.getResultList();
			
			logger.info("Overdue List in BorrowingsNPA---" + overDue.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getBorrowingsSTOverDueList method");
			throw new KlsReportRuntimeException("Can not print Borrowing NPA report:", exception.getCause());
		}
		logger.info("End:Inside getBorrowingsSTOverDueList method");

		return overDue;
	}

	@Override
	public List<LineOfCredit> getBorrowingMTOverDueList(Date asOnDate) {
		logger.info("Start:Inside getBorrowingMTOverDueList method");

		List<LineOfCredit> overDue = new ArrayList<LineOfCredit>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String hqlQuery = " select lloc from LoanLineOfCredit lloc where lloc.loanType = 'B' and lloc.loanAccountProperty in " +
					" (select bap.id from  BorrowingsAccountProperty bap where bap.accountPropertyType ='B' and bap.borrowingType='MT') ";
			logger.info("Query : "+hqlQuery);
			Query query = em.createQuery(hqlQuery);
				
			overDue = query.getResultList();
			
			logger.info(" Borro LT Overdue List in BorrowingsNPA---" + overDue.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getBorrowingLTOverDueList method");
			throw new KlsReportRuntimeException("Can not print Borrowing NPA report:", exception.getCause());
		}
		logger.info("End:Inside getBorrowingMTOverDueList method");

		return overDue;
	}
	@Override
	public String getLoanLineOfCreditById(Long loanLocId,Date asOnDate) {

		logger.info("Start: Fetching the Loan line of credit id from the database in getLoanLineOfCreditById() method-DAO---."
				+ loanLocId);
		LoanLineOfCredit master = null;
		logger.info(" loanLocId : " + loanLocId);
		String dueDate= "";
		List <Object> dueDtList=new ArrayList<Object>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (loanLocId != null) {
				String str="select l.business_date from kls_schema.transaction_history l where l.line_of_credit_id="+loanLocId+" and business_date<='"+asOnDate+"' order by l.business_date";
				Query qry = em.createNativeQuery(str);
				dueDtList=qry.getResultList();
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
				dueDate=dueDtList.get(0).toString();
				dueDate = sdf2.format(sdf1.parse(dueDate.toString()));
				logger.info("dueDate :::" +dueDate);
				//logger.info("value==="+dueDate);
				//logger.info("PAC ID: " + master.getAccount().getAccountProperty().getPacs());
				//logger.info("Barnch ID: " + master.getAccount().getAccountProperty().getBranch().getId());
				//logger.info("account number : " + master.getAccount().getAccountNumber());

			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the loan loc data from the " + "database using loc id  Exception thrown.");
			throw new DataAccessException("Could not fetch the loan loc data from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Loan line of credit data from the database in getLoanLineOfCreditById() method.");
		return dueDate;
	}
	@Override
	public BigDecimal geMtLtPrincipleOverDue(Long loanLocId,Date asOnDate) {
		logger.info("Start: Fetching the Loan line of credit id from the database in geMtLttLoanList() method-DAO---."
				+ loanLocId);
		LoanLineOfCredit master = null;
		logger.info(" loanLocId : " + loanLocId);
		BigDecimal dueDate=BigDecimal.ZERO;
		//List <Object> dueDtList=new ArrayList<Object>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (loanLocId != null) {
				String str="select sum(l.total_pricipal_receivable)-sum(l.principal_paid) as prinoverdue from kls_schema.loan_recovery l where l.line_of_credit_id = "+loanLocId+" and recovered_date '"+asOnDate+"'";
				Query qry = em.createNativeQuery(str);
				dueDate = (BigDecimal)qry.getSingleResult();
				//dueDate=dueDtList.get(0).toString();
				logger.info("value==="+dueDate);
				//logger.info("PAC ID: " + master.getAccount().getAccountProperty().getPacs());
				//logger.info("Barnch ID: " + master.getAccount().getAccountProperty().getBranch().getId());
				//logger.info("account number : " + master.getAccount().getAccountNumber());

			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the loan loc data from the " + "database using loc id  Exception thrown.");
			throw new DataAccessException("Could not fetch the loan loc data from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Loan line of credit data from the database in geMtLttLoanList() method.");
		return dueDate;
	}
	@Override
	public BigDecimal geMtLtIntOverDue(Long loanLocId,Date asOnDate) {
		logger.info("Start: Fetching the Loan line of credit id from the database in geMtLttLoanList() method-DAO---."
				+ loanLocId);
		LoanLineOfCredit master = null;
		logger.info(" loanLocId : " + loanLocId);
		BigDecimal dueDate=BigDecimal.ZERO;
		//List <Object> dueDtList=new ArrayList<Object>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (loanLocId != null) {
				String str="select sum(l.total_interest_receivable)-sum(l.interest_paid) as intoverdue from kls_schema.loan_recovery l where l.line_of_credit_id = "+loanLocId+" and recovered_date '"+asOnDate+"'";
				Query qry = em.createNativeQuery(str);
				dueDate = (BigDecimal)qry.getSingleResult();
				//dueDate=dueDtList.get(0).toString();
				logger.info("value==="+dueDate);
				//logger.info("PAC ID: " + master.getAccount().getAccountProperty().getPacs());
				//logger.info("Barnch ID: " + master.getAccount().getAccountProperty().getBranch().getId());
				//logger.info("account number : " + master.getAccount().getAccountNumber());

			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the loan loc data from the " + "database using loc id  Exception thrown.");
			throw new DataAccessException("Could not fetch the loan loc data from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Loan line of credit data from the database in geMtLttLoanList() method.");
		return dueDate;
	}
	@Override
	public List<LineOfCredit> getSTOverDueListBasedOnproductId(Date asOnDate,Integer productId,Integer pacsId) {

		List<LineOfCredit> overDue = new ArrayList<LineOfCredit>();
		logger.info("Start:Inside getSTOverDueList method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			StringBuilder queryString = new StringBuilder("");
			queryString.append("select loc from LineOfCredit loc,Season s where loc.season.id=s.id and "
					+ "s.overdueDate < '" + asOnDate +"'"
					+ " and loc.loanType='C'"
					+ " and loc.currentBalance < 0 " 
					+ " and loc.lineOfCreditStatus = '"+LineOfCreditStatus.Active.getValue()+"'");
					if(pacsId != 0){
						queryString.append(" and loc.account.accountProperty.pacs = "+ pacsId +" ");
					}
					if(productId != 0){
						queryString.append(" and loc.product.id="+productId);
					}
					queryString.append(" order by loc.product.id");
					Query query = em.createQuery(queryString.toString());
			overDue = query.getResultList();
			logger.info("Overdue List in NPA---" + overDue.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getSTOverDueList method");
			throw new KlsReportRuntimeException("Can not print NPA report:", exception.getCause());
		}
		logger.info("End:Inside getSTOverDueList method");

		return overDue;
	}

	@Override
	public List<LineOfCredit> getMTOverDueListBasedOnproductId(Date asOnDate,Integer productId,Integer pacsId) {
		
		List<LineOfCredit> overDue = new ArrayList<LineOfCredit>();
		logger.info("Start:Inside getMTOverDueList method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder queryString = new StringBuilder("");
			queryString.append("select loc from LineOfCredit loc where"
					+ " loc.loanType='L' and loc.currentBalance < 0 "
					+ " and loc.sanctionedDate <= '"+asOnDate+"'"
					+ " and loc.lineOfCreditStatus = "+LineOfCreditStatus.Active.getValue()+"");
					if(pacsId!=0){
						queryString.append(" and loc.account.accountProperty.pacs = "+ pacsId +" ");
					}
			if(productId!=0)
			{
				queryString.append(" and loc.product.id="+productId);
			}
			queryString.append(" order by loc.product.id");
			Query query = em.createQuery(queryString.toString());
			overDue = query.getResultList();
			logger.info("Overdue List in NPAReport 222222222222222---" + overDue.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getSTOverDueList method");
			throw new KlsReportRuntimeException("Can not print NPA report:", exception.getCause());
		}
		logger.info("End:Inside getMTOverDueList method");

		return overDue;
	}
	
	@Override
	public String getProductName(Integer id) {
		
		String prdName="";
		logger.info("Start:Inside getMTOverDueList method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder queryString = new StringBuilder("");
			queryString.append("select name from Product p where"
					+ " p.id="+id );
				
			Query query = em.createQuery(queryString.toString());
			prdName = query.getSingleResult().toString();
			logger.info("Overdue List in NPA---" + prdName);
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getSTOverDueList method");
			throw new KlsReportRuntimeException("Can not print NPA report:", exception.getCause());
		}
		logger.info("End:Inside getMTOverDueList method");

		return prdName;
	}
	
}
