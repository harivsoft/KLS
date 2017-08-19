package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.dao.IBorrowingAccountLedgerReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class BorrowingAccountLedgerReportDAO implements IBorrowingAccountLedgerReportDAO {
	
	private static final Logger logger = Logger
			.getLogger(BorrowingAccountLedgerReportDAO.class);
	
	public List<Object[]> getBorrowingAccountLedgerReportReport(Integer pacsId, Integer productId, Integer purposeId,  String borrowingType, 
			String fromDateString, String toDateString,Integer branchId) {
		
		List<Object[]> borrowingAccountLedgerReportList = new ArrayList<Object[]>();
		logger.info("Start:Inside getBatchWiseVoucherPrintReport method");
		
		logger.info("Start:Inside getMTLTLoanLedgerReport method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query qry=null;
			List<Integer> pacId=new ArrayList<Integer>();
			if(pacsId==0 && branchId!=0){
			qry=em.createQuery("select p.id from Pacs p where p.branch.id="+branchId);
			pacId=qry.getResultList();
			}
			if(pacsId!=0)
				pacId.add(pacsId);
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder
			.append("select distinct ct.business_date,ct.remarks,ct.opening_balance,"
					+ "(case when ct.crdr = -1 and ct.tran_code = 1 then ct.trans_amt else 0 end) as Debit,"
					+ "(case when ct.crdr = 1 and ct.tran_code = 1 then ct.trans_amt else 0 end) as Credit,"
					+ "(case when ct.crdr = 1 and ct.tran_code = 7 then ct.trans_amt else 0 end) as Due,"
					+ "(case when ct.tran_code=2 OR ct.tran_code = 8 then ct.trans_amt else 0 end) as Paid,"
					+ "prod.id as product,prod.name,pur.id as purpose_id,pur.purpose,a.account_number,ct.pacs_id,"
					+ "p.name as pacs_name,b.id as bid,b.name as bname,ct.tran_code as tranCode,ct.line_of_credit_id as locId,ct.opening_balance as opening_bal "
					+ "from (select * from kls_schema.current_transaction UNION select * from kls_schema.transaction_history) "
					+ "ct,"
					+ "kls_schema.line_of_credit loc,"
					+ "kls_schema.borrowing_product prod,"
					+ "kls_schema.loan_account_property lap,"
					+ "kls_schema.purpose pur,"
					+ "kls_schema.account a,"
					+ "kls_schema.product_type pt,"
					+ "kls_schema.pacs p, "
					+ "kls_schema.account_property ap, "
					+ "kls_schema.branch b "
					+ "where"
					+ " a.id=loc.account_id "
					+ " and ap.id = a.account_property_id "
					+ " and pt.id=prod.product_type_id"
					+ " and p.id=ct.pacs_id"
					+ " and ct.tran_code IN (1,2,7,8)"
					+ " and ct.trans_amt!=0"
					+ "	and pur.id=lap.purpose_id "
					+ " and ct.trans_type='B' and b.id = p.branch_id "
					+ " and pt.loan_type='"+borrowingType+"'"
					+ " and business_date >= '"+DateUtil.getVSoftDateByString(fromDateString)+"' and business_date <= '"+DateUtil.getVSoftDateByString(toDateString)+"'");
	
					if (productId != null && productId != 0) {
						stringBuilder.append(" and prod.id=" + productId);
					}
					if (pacId.size()>0) {
						stringBuilder.append(" and ct.pacs_id in(:pacId)");
					}
					if(purposeId != null && purposeId != 0) {
						stringBuilder.append(" and lap.purpose_id= " + purposeId);
					}
	
					stringBuilder.append(" and prod.id=lap.borrowing_product_id and loc.product_id=lap.product_id and loc.id=ct.line_of_credit_id  order by b.id,ct.pacs_id,prod.id,pur.purpose, a.account_number,ct.business_date,ct.id,ct.line_of_credit_id");
					Query query=em.createNativeQuery(stringBuilder.toString());
					if (pacId.size()>0)
						query.setParameter("pacId", pacId);
			borrowingAccountLedgerReportList = query.getResultList();
			logger.info("loanLedgerReportList---" + borrowingAccountLedgerReportList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getMTLTLoanLedgerReport method");
			throw new KlsReportRuntimeException(
					"Can not print MTLTLoanLedger Report:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		
		logger.info("End:Inside getMTLTLoanLedgerReport method");

		
		
		return borrowingAccountLedgerReportList;
	}
	
	
	public List<Object[]> getLendingAccountLedgerReportReport(Integer pacsId, Integer productId, Integer purposeId,  String borrowingType, 
			String fromDateString, String toDateString) {
		
		List<Object[]> borrowingAccountLedgerReportList = new ArrayList<Object[]>();
		logger.info("Start:Inside getBatchWiseVoucherPrintReport method");
		
		logger.info("Start:Inside getMTLTLoanLedgerReport method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder
					.append("select distinct ct.business_date,ct.remarks,ct.opening_balance,"
							+ "(case when ct.crdr = -1 and ct.tran_code = 1 then ct.trans_amt else 0 end) as Debit,"
							+ "(case when ct.crdr = 1 and ct.tran_code = 1 then ct.trans_amt else 0 end) as Credit,"
							+ "(case when ct.crdr = 1 and ct.tran_code = 7 then ct.trans_amt else 0 end) as Due,"
							+ "(case when ct.tran_code=2 OR ct.tran_code = 8 then ct.trans_amt else 0 end) as Paid,"
							+ "prod.id as product,prod.name,pur.id as purpose_id,pur.purpose,lap.ccb_account_number,ct.pacs_id,"
							+ "p.name as pacs_name,b.id as bid,b.name as bname,ct.tran_code as tranCode,ct.line_of_credit_id as locId,ct.opening_balance as opening_bal "
							+ "from (select * from kls_schema.current_transaction UNION select * from kls_schema.transaction_history) "
							+ "ct,"
							+ "kls_schema.line_of_credit loc,"
							+ "kls_schema.borrowing_product prod,"
							+ "kls_schema.loan_account_property lap,"
							+ "kls_schema.purpose pur,"
							+ "kls_schema.account a,"
							+ "kls_schema.product_type pt,"
							+ "kls_schema.pacs p, "
							+ "kls_schema.account_property ap, "
							+ "kls_schema.branch b "
							+ "where"
							+ " a.id=loc.account_id "
							+ " and ap.id = a.account_property_id "
							+ " and pt.id=prod.product_type_id"
							+ " and p.id=ct.pacs_id"
							+ " and ct.tran_code IN (1,2,7,8)"
							+ " and ct.trans_amt!=0"
							+ "	and pur.id=lap.purpose_id "
							+ " and ct.trans_type='B' and b.id = p.branch_id "
							+ " and pt.loan_type='"+borrowingType+"'"
							+ " and business_date >= '"+DateUtil.getVSoftDateByString(fromDateString)+"' and business_date <= '"+DateUtil.getVSoftDateByString(toDateString)+"'");
			
							if (productId != null && productId != 0) {
								stringBuilder.append(" and prod.id=" + productId);
							}
							if (pacsId != null && pacsId != 0) {
								stringBuilder.append(" and ct.pacs_id =" + pacsId);
							}
							if(purposeId != null && purposeId != 0) {
								stringBuilder.append(" and lap.purpose_id= " + purposeId);
							}
			
							stringBuilder.append(" and prod.id=lap.borrowing_product_id and loc.product_id=lap.product_id and loc.id=ct.line_of_credit_id  order by b.id,ct.pacs_id,prod.id,pur.purpose, a.account_number,ct.business_date,ct.id,ct.line_of_credit_id");
			borrowingAccountLedgerReportList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("loanLedgerReportList---" + borrowingAccountLedgerReportList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getMTLTLoanLedgerReport method");
			throw new KlsReportRuntimeException(
					"Can not print MTLTLoanLedger Report:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		
		logger.info("End:Inside getMTLTLoanLedgerReport method");

		
		
		return borrowingAccountLedgerReportList;
	}
	@Override	
	public  List<Object[]> getCustomerName(String locId){
		String memberName="";
		logger.info("Start:Inside getBatchWiseVoucherPrintReport method");
		
		logger.info("Start:Inside getMTLTLoanLedgerReport method");
		Integer locId1=Integer.parseInt(locId);
		List<Object[]> listOfObj = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder
					.append("select p.display_name, p.party_id from membership.party p where p.party_id in (select loc.customer_id from kls_schema.line_of_credit loc where loc.id in (select l.borrowings_loc_id from kls_schema.line_of_credit l where l.id="+locId1+"))");

			 listOfObj = em.createNativeQuery(stringBuilder.toString()).getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getMTLTLoanLedgerReport method");
			throw new KlsReportRuntimeException(
					"Can not print MTLTLoanLedger Report:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		
		logger.info("End:Inside getMTLTLoanLedgerReport method");

		 
		
		return listOfObj;
	}
	@Override	
	public  List<Object[]> getMemberName(String locId,String memberId){
		String memberName="";
		logger.info("Start:Inside getBatchWiseVoucherPrintReport method");
		
		logger.info("Start:Inside getMTLTLoanLedgerReport method");
		Integer locId1=Integer.parseInt(locId);
		Long partyId=Long.parseLong(memberId);
		List<Object[]> listOfObj = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder
					.append("select p.display_name, p.party_id from membership.party p where p.party_id in (select loc.customer_id from kls_schema.line_of_credit loc where loc.id in (select l.borrowings_loc_id from kls_schema.line_of_credit l where l.id="+locId1+"))");

			 listOfObj = em.createNativeQuery(stringBuilder.toString()).getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getMTLTLoanLedgerReport method");
			throw new KlsReportRuntimeException(
					"Can not print MTLTLoanLedger Report:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		
		logger.info("End:Inside getMTLTLoanLedgerReport method");

		 
		
		return listOfObj;
	}
	
	public List<Object[]> getLendingLedgerData(Integer pacsId, Integer productId, Integer purposeId,  String borrowingType, 
			String fromDateString, String toDateString,Integer noOfRecords) {
		
		List<Object[]> borrowingAccountLedgerReportList = new ArrayList<Object[]>();
		logger.info("Start:Inside getBatchWiseVoucherPrintReport method");
		
		logger.info("Start:Inside getMTLTLoanLedgerReport method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder
					.append("select distinct ct.business_date,ct.remarks,ct.opening_balance,"
							+ "(case when ct.crdr = -1 and ct.tran_code = 1 then ct.trans_amt else 0 end) as Debit,"
							+ "(case when ct.crdr = 1 and ct.tran_code = 1 then ct.trans_amt else 0 end) as Credit,"
							+ "(case when ct.crdr = 1 and ct.tran_code = 7 then ct.trans_amt else 0 end) as Due,"
							+ "(case when ct.tran_code=2 OR ct.tran_code = 8 then ct.trans_amt else 0 end) as Paid,"
							+ "prod.id as product,prod.name,pur.id as purpose_id,pur.purpose,lap.ccb_account_number,ct.pacs_id,"
							+ "p.name as pacs_name,b.id as bid,b.name as bname,ct.tran_code as tranCode,ct.line_of_credit_id as locId,ct.opening_balance as opening_bal "
							+ "from (select * from kls_schema.current_transaction UNION select * from kls_schema.transaction_history) "
							+ "ct,"
							+ "kls_schema.line_of_credit loc,"
							+ "kls_schema.borrowing_product prod,"
							+ "kls_schema.loan_account_property lap,"
							+ "kls_schema.purpose pur,"
							+ "kls_schema.account a,"
							+ "kls_schema.product_type pt,"
							+ "kls_schema.pacs p, "
							+ "kls_schema.account_property ap, "
							+ "kls_schema.branch b "
							+ "where"
							+ " a.id=loc.account_id "
							+ " and ap.id = a.account_property_id "
							+ " and pt.id=prod.product_type_id"
							+ " and p.id=ct.pacs_id"
							+ " and ct.tran_code IN (1,2,7,8)"
							+ " and ct.trans_amt!=0"
							+ "	and pur.id=lap.purpose_id "
							+ " and ct.trans_type='B' and b.id = p.branch_id "
							+ " and pt.loan_type='"+borrowingType+"'"
							+ " and business_date >= '"+DateUtil.getVSoftDateByString(fromDateString)+"' and business_date <= '"+DateUtil.getVSoftDateByString(toDateString)+"'");
														
							if (productId != null && productId != 0) {
								stringBuilder.append(" and prod.id=" + productId);
							}
							if (pacsId != null && pacsId != 0) {
								stringBuilder.append(" and ct.pacs_id =" + pacsId);
							}
							if(purposeId != null && purposeId != 0) {
								stringBuilder.append(" and lap.purpose_id= " + purposeId);
							}
			
							if(noOfRecords>0)
							stringBuilder.append(" and prod.id=lap.borrowing_product_id and loc.product_id=lap.product_id and loc.id=ct.line_of_credit_id order by b.id,ct.pacs_id,prod.id,pur.purpose, a.account_number,ct.business_date,ct.id,ct.line_of_credit_id ");
							else
								stringBuilder.append(" and prod.id=lap.borrowing_product_id and loc.product_id=lap.product_id and loc.id=ct.line_of_credit_id order by b.id,ct.pacs_id,prod.id,pur.purpose, a.account_number,ct.business_date,ct.id,ct.line_of_credit_id");
			borrowingAccountLedgerReportList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("loanLedgerReportList---" + borrowingAccountLedgerReportList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getMTLTLoanLedgerReport method");
			throw new KlsReportRuntimeException(
					"Can not print MTLTLoanLedger Report:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		
		logger.info("End:Inside getMTLTLoanLedgerReport method");

		
		
		return borrowingAccountLedgerReportList;
	}
	
	
	public List<Object[]> getLendingLedgerDatabyLocId(String borrowingType, 
			String fromDateString, String toDateString,Integer noOfRecords,String locId , String transType) {
		
		List<Object[]> borrowingAccountLedgerReportList = new ArrayList<Object[]>();
		logger.info("Start:Inside getBatchWiseVoucherPrintReport method");
		logger.info("Start:Inside getMTLTLoanLedgerReport method");
		Integer locId1=Integer.parseInt(locId);
logger.info("locId::::::::" +locId1);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();
						
			  stringBuilder.append(" select * from(select ct.business_date,ct.trans_amt  from kls_schema.current_transaction ct    ");
			  stringBuilder.append(" where ct.business_date >=   '"+DateUtil.getVSoftDateByString(fromDateString)+"' and ct.business_date <= '"+DateUtil.getVSoftDateByString(toDateString)+"'   ");
			  if("dr".equalsIgnoreCase(transType)){
			  stringBuilder.append("  and ct.crdr = -1 ");
			  }else{
			  stringBuilder.append("  and ct.crdr = 1  ");
			  }
			  stringBuilder.append("  and   ct.tran_code IN (1) and  ");
			  stringBuilder.append("  ct.trans_amt!=0 and ct.trans_type='B'  and    ct .line_of_credit_id = "+locId+"  ");
			  stringBuilder.append(" union ");  
			  stringBuilder.append(" select th.business_date,th.trans_amt  ");
			  stringBuilder.append(" from kls_schema.transaction_history th  ");
			  stringBuilder.append(" where th.business_date >=   '"+DateUtil.getVSoftDateByString(fromDateString)+"' and th.business_date <= '"+DateUtil.getVSoftDateByString(toDateString)+"' ");
			    if("dr".equalsIgnoreCase(transType)){
				  stringBuilder.append("  and th.crdr = -1 ");
				  }else{
				  stringBuilder.append("  and th.crdr = 1  ");
				  }
			  stringBuilder.append("  and   th.tran_code IN (1) and  ");
			  stringBuilder.append(" th.trans_amt!=0 and th.trans_type='B'  and  th .line_of_credit_id = "+locId+") order by  business_date desc ");
			  if(noOfRecords>0)
				  stringBuilder.append(" limit "+noOfRecords );  
				  
	
						
			borrowingAccountLedgerReportList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("loanLedgerReportList---" + borrowingAccountLedgerReportList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getMTLTLoanLedgerReport method");
			throw new KlsReportRuntimeException(
					"Can not print MTLTLoanLedger Report:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		
		logger.info("End:Inside getMTLTLoanLedgerReport method");

		
		
		return borrowingAccountLedgerReportList;
	}

}
