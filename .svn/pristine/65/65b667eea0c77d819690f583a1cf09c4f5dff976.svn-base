package com.vsoftcorp.kls.report.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.dao.IScheduleVsGLBalanceReportDAO;
import com.vsoftcorp.kls.report.service.data.GLHistoryData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

public class ScheduleVsGLBalanceReportDAO  implements IScheduleVsGLBalanceReportDAO{
	
	private static final Logger logger = Logger
			.getLogger(ScheduleVsGLBalanceReportDAO.class);
	@Override
	
	public List<Object[]> getScheduleVsGLBalance(String pacsId, Date toDate,String status){
		
		logger.info("Start: Indise of the method getScheduleVsGLBalance()");
		List<Object[]> scheduleBalList = new ArrayList<Object[]>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String query="";
			if(status.equals("B"))
			//query="select pt.loan_type,sum(h.trans_amt),p.gl_code,p.product_id,pr.name,count(l.id) from kls_schema.line_of_credit l Left Join kls_schema.product pr on pr.id=l.product_id Left Join kls_schema.pacs_gl_mapping p on pr.id=p.product_id Left Join kls_schema.transaction_history h on l.id=h.line_of_credit_id  left join kls_schema.product_type pt on pt.id=pr.product_type_id where p.pacs_id="+pacsId+" and h.tran_code in(1,5,6,15,19) and l.status=1 and h.trans_type!='B' and h.business_date<='"+toDate+"'  group by p.gl_code,p.product_id,pr.id,pr.name,pt.loan_type,h.crdr order by pt.loan_type desc";
			query="select pt.loan_type,sum(case when h.crdr = -1 then (h.trans_amt) else -1 * h.trans_amt end),p.gl_code,p.product_id,pr.name,count(distinct l.id) from kls_schema.line_of_credit l, kls_schema.product pr, kls_schema.pacs_gl_mapping p,  kls_schema.transaction_history h,kls_schema.product_type pt where pr.id=l.product_id and pr.id=p.product_id and l.id=h.line_of_credit_id and pt.id=pr.product_type_id and p.pacs_id="+pacsId+" and h.tran_code in(1,5,6,15,19)  and h.trans_type!='B' and h.business_date<='"+toDate+"' and h.pacs_id="+pacsId+"  group by pt.loan_type, p.gl_code,p.product_id,pr.name order by pt.loan_type desc,p.product_id";
			else
				//query="select pt.loan_type,sum(h.trans_amt),p.int_receivable_gl,p.product_id,pr.name,count(l.id) from kls_schema.line_of_credit l Left Join kls_schema.product pr on pr.id=l.product_id Left Join kls_schema.pacs_gl_mapping p on pr.id=p.product_id Left Join kls_schema.transaction_history h on l.id=h.line_of_credit_id  left join kls_schema.product_type pt on pt.id=pr.product_type_id where p.pacs_id="+pacsId+" and h.tran_code in(2,7) and l.status=1 and h.trans_type!='B' and h.business_date<='"+toDate+"' group by p.int_receivable_gl,p.product_id,pr.id,pr.name,pt.loan_type,h.crdr order by pr.id";
			query="select pt.loan_type,sum(case when h.crdr = -1 then (h.trans_amt) else -1 * h.trans_amt end),p.gl_code,p.product_id,pr.name,count(distinct l.id) from kls_schema.line_of_credit l, kls_schema.product pr, kls_schema.pacs_gl_mapping p,  kls_schema.transaction_history h,kls_schema.product_type pt where pr.id=l.product_id and pr.id=p.product_id and l.id=h.line_of_credit_id and pt.id=pr.product_type_id and p.pacs_id="+pacsId+" and h.tran_code in(2,7)  and h.trans_type!='B' and h.business_date<='"+toDate+"' and h.pacs_id="+pacsId+"  group by pt.loan_type, p.gl_code,p.product_id,pr.name order by pt.loan_type desc,p.product_id";
			Query qry = em.createNativeQuery(query.toString());
			scheduleBalList = qry.getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error while retriving records for Schedule Vs GL Balance");
			throw new KlsReportRuntimeException(
					"Error while retriving records for Schedule Vs GL Balance:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Indise of the method getScheduleVsGLBalance()  scheduleBalList::"
				+ scheduleBalList);
		
		return scheduleBalList;
		
	}

	
@Override
	
	public String getGLBalance(String glcode, Date toDate,String branchId,String pacsId){
		
		logger.info("Start: Indise of the method getGLBalance()");
		Map<String,String> glData=new HashMap<String,String>();
		BigDecimal bal=BigDecimal.ZERO;
		try {
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("PACSPersistenceUnit");
			EntityManager em = emf.createEntityManager();
			logger.info("Got connection for schedule balance" + em);
			em.getEntityManagerFactory().getPersistenceUnitUtil();
			em.getTransaction().begin();
			String glacccode = null;
			String query="select sum(g.dr_balance)-sum(g.cr_balance) from sbbranchglbalance g where g.glacc_no='"+glcode+"' and gldate='"+toDate+"' and branch_id=lpad('"+branchId+"',4,'0')||lpad('"+pacsId+"',5,'0')";
			logger.info("query glacccode" + query);
			bal=(BigDecimal)em.createNativeQuery(query).getSingleResult();
			
			//logger.info("glacccode" + glacccode);
			em.getTransaction().commit();
			//glData = (GLHistoryData)qry.getSingleResult();
			
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error while retriving records for Schedule Vs GL Balance");
			throw new KlsReportRuntimeException(
					"Error while retriving records for Schedule Vs GL Balance:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Indise of the method getScheduleVsGLBalance()  scheduleBalList::"
				+ glData);
		if(bal != null)
			return bal.toString();
		else
			return "0";
		
	}

@Override

public List<Object[]> getScheduleBalance(String pacsId, Date toDate,String productId){
	
	logger.info("Start: Indise of the method getScheduleBalance()");
	List<Object[]> scheduleBalList = new ArrayList<Object[]>();
	try {
		EntityManager em = EntityManagerUtil.getEntityManager();
		String query="";
		/*if(balancetype.equalsIgnoreCase("Debit"))
			query="select  mp.first_name,mp.member_number,ka.account_number||'-'||loc.id accountNumber,sum(h.trans_amt)  from kls_schema.line_of_credit loc, kls_schema.account ka,membership.person mp ,(select * from kls_schema.transaction_history union select * from kls_schema.current_transaction)  h  where ka.id=loc.account_id and mp.party_id = loc.customer_id and mp.pacs_id="+pacsId+" and loc.product_id = "+productId+" and loc.id = h.line_of_credit_id and h.tran_code in(1,5,6,15,19)  and h.trans_type!='B' and h.business_date<='"+toDate+"' and h.crdr=-1group by loc.id, mp.first_name,mp.member_number,accountNumber order by accountNumber";
			//query="select  mp.first_name,mp.member_number,ka.account_number||'-'||loc.id accountNumber,(select sum(h.trans_amt) from (select * from kls_schema.transaction_history union select * from kls_schema.current_transaction)  h where loc.id = h.line_of_credit_id and h.tran_code in(1,5,6,15,19)  and h.trans_type!='B' and h.business_date<='"+toDate+"' and h.crdr=-1) as amount from kls_schema.line_of_credit loc, kls_schema.account ka,membership.person mp where ka.id=loc.account_id and mp.party_id = loc.customer_id and mp.pacs_id="+pacsId+" and loc.product_id = "+productId+" group by loc.id, mp.first_name,mp.member_number,accountNumber order by accountNumber";
		else if(balancetype.equalsIgnoreCase("Credit"))
			query="select  mp.first_name,mp.member_number,ka.account_number||'-'||loc.id accountNumber,sum(h.trans_amt)  from kls_schema.line_of_credit loc, kls_schema.account ka,membership.person mp ,(select * from kls_schema.transaction_history union select * from kls_schema.current_transaction)  h  where ka.id=loc.account_id and mp.party_id = loc.customer_id and mp.pacs_id="+pacsId+" and loc.product_id = "+productId+" and loc.id = h.line_of_credit_id and h.tran_code in(1,5,6,15,19)  and h.trans_type!='B' and h.business_date<='"+toDate+"' and h.crdr=1 group by loc.id, mp.first_name,mp.member_number,accountNumber order by accountNumber";
			//query="select  mp.first_name,mp.member_number,ka.account_number||'-'||loc.id accountNumber,(select sum(h.trans_amt) from (select * from kls_schema.transaction_history union select * from kls_schema.current_transaction)  h where loc.id = h.line_of_credit_id and h.tran_code in(1,5,6,15,19)  and h.trans_type!='B' and h.business_date<='"+toDate+"' and h.crdr=1) as amount from kls_schema.line_of_credit loc, kls_schema.account ka,membership.person mp where ka.id=loc.account_id and mp.party_id = loc.customer_id and mp.pacs_id="+pacsId+" and loc.product_id = "+productId+" group by loc.id, mp.first_name,mp.member_number,accountNumber order by accountNumber";
		else*/
		  query="select  mp.first_name,mp.member_number,ka.account_number||'-'||loc.id accountNumber,sum(case when h.crdr = -1 then (h.trans_amt) else -1 * h.trans_amt end)  from kls_schema.line_of_credit loc, kls_schema.account ka,membership.person mp ,(select * from kls_schema.transaction_history union select * from kls_schema.current_transaction)  h  where ka.id=loc.account_id and mp.party_id = loc.customer_id and mp.pacs_id="+pacsId+" and loc.product_id = "+productId+" and loc.id = h.line_of_credit_id and h.tran_code in(1,5,6,15,19)  and h.trans_type!='B' and h.business_date<='"+toDate+"' group by loc.id, mp.first_name,mp.member_number,accountNumber order by accountNumber";
			//query="select  mp.first_name,mp.member_number,ka.account_number||'-'||loc.id accountNumber,(select sum(case when h.crdr = -1 then (h.trans_amt) else -1 * h.trans_amt end) from (select * from kls_schema.transaction_history union select * from kls_schema.current_transaction)  h where loc.id = h.line_of_credit_id and h.tran_code in(1,5,6,15,19)  and h.trans_type!='B' and h.business_date<='"+toDate+"') as amount from kls_schema.line_of_credit loc, kls_schema.account ka,membership.person mp where ka.id=loc.account_id and mp.party_id = loc.customer_id and mp.pacs_id="+pacsId+" and loc.product_id = "+productId+" group by loc.id, mp.first_name,mp.member_number,accountNumber order by accountNumber";
		Query qry = em.createNativeQuery(query.toString());
		scheduleBalList = qry.getResultList();
	} catch (Exception exception) {
		exception.printStackTrace();
		logger.error("Error while retriving records for Schedule Vs GL Balance");
		throw new KlsReportRuntimeException(
				"Error while retriving records for Schedule Vs GL Balance:",
				exception.getCause());
	}
	
	finally
	{
		EntityManagerUtil.closeSession();
	}
	logger.info("End: Indise of the method getScheduleVsGLBalance()  scheduleBalList::"
			+ scheduleBalList);
	
	return scheduleBalList;
	
}

public String getBranchId(String pacsId)
{

	logger.info("Start: Indise of the method getBranchId()");
	Map<String,String> glData=new HashMap<String,String>();
	BigDecimal bal=BigDecimal.ZERO;
	String branchId="";
	try {
		
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("PACSPersistenceUnit");
		//EntityManager em = emf.createEntityManager();
		//logger.info("Got connection for schedule balance" + em);
		//em.getEntityManagerFactory().getPersistenceUnitUtil();
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		String glacccode = null;
		String query="select branch_id from kls_schema.pacs where id="+pacsId;
		logger.info("query glacccode" + query);
		Integer bId=(Integer)em.createNativeQuery(query).getSingleResult();
		branchId= bId.toString();
		
		//logger.info("glacccode" + glacccode);
		em.getTransaction().commit();
		
		
	} catch (Exception exception) {
		exception.printStackTrace();
		logger.error("Error while retriving records for Schedule Vs GL Balance");
		throw new KlsReportRuntimeException(
				"Error while retriving records for Schedule Vs GL Balance:",
				exception.getCause());
	}
	
	finally
	{
		EntityManagerUtil.closeSession();
	}
	logger.info("End: Indise of the method getBranchId()  scheduleBalList::"
			+ glData);
	
		return branchId;	
}
















}
