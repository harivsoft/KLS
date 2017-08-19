package com.vsoftcorp.kls.report.dao.impl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.dao.IBatchWiseVoucherPrintReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class BatchWiseVoucherPrintReportDAO implements IBatchWiseVoucherPrintReportDAO {
	
	private static final Logger logger = Logger
			.getLogger(BatchWiseVoucherPrintReportDAO.class);
	
	public List<Object[]> getBatchWiseVoucherPrintReport(String voucherType, String transactionType, 
			String fromDateString, String toDateString, PrintWriter printWriter, Integer pacsId) {
		
		List<Object[]> batchWiseVoucherReportList = new ArrayList<Object[]>();
		logger.info("Start:Inside getBatchWiseVoucherPrintReport method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();
			
			stringBuilder.append("select id, business_date, trans_type, remarks, debit_amt, credit_amt, "
					+ "opening_balance +credit_amt -debit_amt as balance, opening_balance, voucher_number,tran_code,tmode from "
					+ "(select kth.id,kth.business_date, trans_type, "
					+ "case when crdr = 1 and tran_code in(1,2,3,17,27) then 'R' else (case when tran_code in (13) then 'R' else 'D' end) end tmode, "
					+ "case when crdr = 1 then trans_amt else 0 end credit_amt, "
					+ "case when crdr = -1 then trans_amt else 0 end debit_amt, voucher_number, kp.name, "
					+ "case when opening_balance IS NOT NULL then opening_balance else 0 end opening_balance,  "
					+ "remarks,tran_code from "
					+ "(select * from kls_schema.current_transaction UNION select * from kls_schema.transaction_history) kth, "
					+ "kls_schema.pacs kp where  kth.pacs_id = kp.id and "
					+ "business_date  between '"+DateUtil.getVSoftDateByString(fromDateString)+"'  and '"+DateUtil.getVSoftDateByString(toDateString)+"' and kth.pacs_id =  "+pacsId);
					if(voucherType.equals("D") || voucherType.equals("T")) {
						stringBuilder.append(" and voucher_number like '"+voucherType+"%')");
					}else{
						stringBuilder.append(")");
					}
					if( transactionType.equals("R") || transactionType.equals("D") || transactionType.equals("C") ){
						stringBuilder.append(" where tmode='"+transactionType+"'");
					}
					stringBuilder.append(" order by business_date,tmode,id");
					
					
			batchWiseVoucherReportList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			
			logger.info("loanLedgerReportList---" + batchWiseVoucherReportList.size());
			
		}catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getBatchWiseVoucherPrintReport method");
			throw new KlsReportRuntimeException(
					"Can not print Batch Wise Voucher Print Report:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Inside getBatchWiseVoucherPrintReport method");
		return batchWiseVoucherReportList;
	}

}
