package com.vsoftcorp.kls.report.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.dao.IDayBookReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

public class DayBookReportDAO implements IDayBookReportDAO {
	
	private static final Logger logger = Logger
			.getLogger(DayBookReportDAO.class);


	@Override
	public List<Object[]> getDayBookReportDAO(Date asOnDate, Integer pacsId) {
		List<Object[]> dayBookList = new ArrayList<Object[]>();
		logger.info("Start:Inside LandRegisterReportDAO method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder
					.append("select account_number,tran_code,crdr,voucher_number,trans_type,AccountName,"
							+ "productId,productName,cashdebits,cashcredits,transferdebits,transfercredits "
							+ "from((select account_number,tran_code,crdr,voucher_number,trans_type,mp.first_name as AccountName,"
							+ "product_id as productId,p.name  as productName,"
							+ "(case when h.crdr = -1 and h.trans_type in('D','W') then (h.trans_amt)  else  0  end) as cashdebits,"
							+ "(case when h.crdr = 1 and h.trans_type in('D','W') then (h.trans_amt)  else  0  end) as cashcredits,"
							+ "(case when h.crdr = -1 and h.trans_type ='T' then (h.trans_amt)  else  0  end) as transferdebits,"
							+ "(case when h.crdr = 1 and h.trans_type ='T' then (h.trans_amt)  else  0  end) as transfercredits "
							+ "from  kls_schema.line_of_credit l,"
							+ "(select * from kls_schema.transaction_history union select * from kls_schema.current_transaction)  h, "
							+ "membership.person mp,kls_schema.product p where  tran_code in (1,11,5,6,15,19,17,18)  "
							+ "and h.line_of_credit_id=l.id  and h.account_id=l.account_id and l.customer_id=mp.party_id "
							+ "and l.product_id=p.id and  trans_type <> 'B' and h.pacs_id="+pacsId+"and h.business_date='"+asOnDate+"'  "
							+ "group by p.name,account_number,crdr,voucher_number,trans_type,mp.first_name,product_id,tran_code,h.trans_amt "
							+ " order by p.name)"
							+ " union"
							+ " ( select account_number,tran_code,crdr,voucher_number,trans_type,bp.name as AccountName,"
							+ "borrowing_product_id as productId,p.name as productName,"
							+ "(case when h.crdr = -1 and h.trans_type in('D','W') then (h.trans_amt)  else  0  end) as cashdebits,"
							+ "(case when h.crdr = 1 and h.trans_type in('D','W') then (h.trans_amt)  else  0  end) as cashcredits,"
							+ "(case when h.crdr = -1 and h.trans_type in('B','T') then (h.trans_amt)  else  0  end) as transferdebits,"
							+ "(case when h.crdr = 1 and h.trans_type in ('B','T') then (h.trans_amt)  else  0  end) as transfercredits  "
							+ "from  kls_schema.line_of_credit l,(select * from kls_schema.transaction_history "
							+ "union "
							+ "select * from kls_schema.current_transaction)  h, "
							+ "kls_schema.loan_account_property bp,kls_schema.borrowing_product p where  h.line_of_credit_id=l.id  "
							+ "and h.account_id=bp.account_id  and bp.borrowing_product_id=p.id and  trans_type = 'B' and h.pacs_id="+pacsId+"and h.business_date='"+asOnDate+"' "
							+ "group by p.name,account_number,crdr,voucher_number,trans_type,bp.name,borrowing_product_id,tran_code,h.trans_amt"
							+ " order by p.name) "
							+ "union"
							+ " ( select account_number,tran_code,crdr,voucher_number,trans_type,mp.first_name as AccountName,"
							+ "share_product_id as productId,p.product_name as productName,"
							+ "(case when h.crdr = -1 and h.trans_type in('D','W') then (h.trans_amt)  else  0  end) as cashdebits,"
							+ "(case when h.crdr = 1 and h.trans_type in('D','W') then (h.trans_amt)  else  0  end) as cashcredits,"
							+ "(case when h.crdr = -1 and h.trans_type in('B','T') then (h.trans_amt)  else  0  end) as transferdebits,"
							+ "(case when h.crdr = 1 and h.trans_type in ('B','T') then (h.trans_amt)  else  0  end) as transfercredits  "
							+ "from  kls_schema.line_of_credit l,(select * from kls_schema.transaction_history union select * from kls_schema.current_transaction)  h, "
							+ "membership.account a,membership.share_product p,membership.person mp where tran_code = 12  and h.pacs_id="+pacsId+" and h.business_date='"+asOnDate+"' "
							+ "and account_number=a.account_no and  a.share_product_id=p.id and  mp.party_id=a.party_id  and h.pacs_id="+pacsId+"and h.business_date='"+asOnDate+"' "
							+ "group by p.product_name,account_number,crdr,voucher_number,trans_type,p.product_name,share_product_id,h.trans_amt,tran_code,"
							+ "mp.first_name order by p.product_name) "
							+ "union "
							+ "  ( select a.account_number,tran_code,crdr,voucher_number,trans_type,p.first_name as AccountName,0 as productId,h.account_number as productName,"
							+ "case when h.crdr = -1 and h.trans_type in('D','W') then (h.trans_amt)  else  0  end as cashdebits,"
							+ "case when h.crdr = 1 and h.trans_type in('D','W') then (h.trans_amt)  else  0  end as cashcredits,"
							+ "case when h.crdr = -1 and h.trans_type in('B','T') then (h.trans_amt)  else  0  end as transferdebits,"
							+ "case when h.crdr = 1 and h.trans_type in ('B','T') then (h.trans_amt)  else  0  end as transfercredits  "
							+ "from (select * from kls_schema.transaction_history union select * from kls_schema.current_transaction)  h ,kls_schema.account a, kls_schema.account_property ap, membership.person p "
							+ " where tran_code in(22,27,28,29) and h.pacs_id="+pacsId+" and h.account_id = a.id and a.account_property_id = ap.id and ap.customer_id = p.party_id and h.business_date='"+asOnDate+"' "
							+ " group by h.account_number,a.account_number,p.first_name,crdr,voucher_number,trans_type,tran_code,h.trans_amt order by productName)"
							+ "union "
							+ "  ( select h.account_number,tran_code,crdr,voucher_number,trans_type,p2.first_name as AccountName,0 as productId," 
						    + "( case when tran_code in (2,7, 30) then m.int_receivable_gl when tran_code = 8 then m.int_received_gl when tran_code = 3 or tran_code = 9 then m.penal_int_receivable_gl when tran_code = 10 then m.penal_int_received_gl when tran_code = 19 then m.processing_fee_gl when tran_code = 15 then p.margin_gl when tran_code = 6 and l.loan_type = 'C' then s.insurance_gl when tran_code = 6 and l.loan_type <> 'C' then m.insurance_gl else h.account_number end) as productName ,"
							+ "(case when h.crdr = -1 and h.trans_type in('D','W') then (h.trans_amt)  else  0  end) as cashdebits,"
							+ "(case when h.crdr = 1 and h.trans_type in('D','W') then (h.trans_amt)  else  0  end) as cashcredits,"
							+ "(case when tran_code in (15) or tran_code not in (19, 6) and h.crdr = -1 and h.trans_type in('T') then (h.trans_amt)  else  0  end) as transferdebits,"
							+ "(case when tran_code in (19, 6) or tran_code not in (15) and h.crdr = 1 and h.trans_type in ('T') then (h.trans_amt)  else  0  end) as transfercredits  "
							+ "from (select * from kls_schema.transaction_history union select * from kls_schema.current_transaction)  h ,membership.person p2,kls_schema.account a,kls_schema.account_property ap," 
                            + "  kls_schema.line_of_credit l, kls_schema.pacs_gl_mapping m, kls_schema.pacs p,"
                            + " (select l2.loan_type, locId, pacsId,insurance_gl  from (select loan_type, l1.id locId, season_id, crop_id, p1.pacs_id pacsId from kls_schema.line_of_credit l1, membership.person p1 where l1.customer_id = p1.party_id) l2, kls_schema.season_parameter s1 where s1.season_id (+)= l2.season_id and s1.crop_id (+)= l2.crop_id and s1.pacs_id (+)= l2.pacsId) s " 
                            + "where tran_code in(2,3,6,7,8,9,10,15,19,30) and h.pacs_id="+pacsId+" and trans_type <> 'B' and h.line_of_credit_id = l.id and l.product_id = m.product_id and h.pacs_id = p.id and s.locId = l.id and s.pacsId = h.pacs_id and h.account_id = a.id and a.account_property_id = ap.id and ap.customer_id = p2.party_id and h.business_date='"+asOnDate+"' "
							+ " group by h.account_number,crdr,voucher_number,trans_type,tran_code, productId, trans_amt,p2.first_name,tran_code,productName,h.trans_amt order by productName) order by productName)");
							
			
			
			dayBookList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("LandRegister List---" + dayBookList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside LandRegisterReportDAO method");
			throw new KlsReportRuntimeException(
					"Can not print LandRegister report:", exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Inside LandRegisterReportDAO method");
		return dayBookList;
	}
	
	public String getGLNameByCode(String glcode){
	    
	    logger.info("Start: Indise of the method getGLNameByCode()");
	    
	    String glname="";
	    try {
	        
	        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PACSPersistenceUnit");
	        EntityManager em = emf.createEntityManager();
	        logger.info("Got connection for gl name" + em);
	        em.getEntityManagerFactory().getPersistenceUnitUtil();
	        em.getTransaction().begin();
	        String glacccode = null;
	        String query="select glname from glaccount  where glacc_no='"+glcode+"'";
	        logger.info("query glacccode" + query);
	        glname=(String) em.createNativeQuery(query).getSingleResult();
	 
	        em.getTransaction().commit();

	        
	    } catch (Exception exception) {
	        exception.printStackTrace();
	        logger.error("Error while retriving records for daybook");
	        throw new KlsReportRuntimeException(
	                "Error while retriving records for daybook:",
	                exception.getCause());
	    }
	    
	    finally
		{
			EntityManagerUtil.closeSession();
		}
	    logger.info("End: Indise of the method daybook  glname::"
	            + glname);
	    
	    return glname;
	    
	}
	
	public BigDecimal getGLBalance(String glcode, Date toDate){
		
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
			String query="select sum(g.dr_balance)-sum(g.cr_balance) from glhistory g where g.glacc_no='"+glcode+"' and gldate='"+toDate+"'";
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
			return bal;
		else
			
			
			return BigDecimal.ZERO;
		
	}

}
