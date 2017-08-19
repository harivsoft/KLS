package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.vsoftcorp.kls.business.entity.transaction.AccountWiseConsistency;
import com.vsoftcorp.kls.report.dao.IInconsistencyReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class InconsistencyReportDAO implements IInconsistencyReportDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountWiseConsistency> getInconsistencyReport() {
		List<AccountWiseConsistency> inconsistentList = new ArrayList<AccountWiseConsistency>();
		try {
						
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select i  from AccountWiseConsistency i where i.consistencyStatus='N'");
			
			Query query =  em.createQuery(stringBuilder.toString());
			inconsistentList = query.getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new KlsReportRuntimeException("Can not print inconsistent report:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}

		return inconsistentList;
	}

	@Override
	public String getCustomerName(Long customerId) {
		String customerName =null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try
		{
		customerName=em.createNativeQuery("select display_name from membership.party   where party_id="+customerId).getSingleResult().toString();
		}
		catch(Exception e){
			System.out.println(" Error While Getting Customer Name "+customerId );
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		
		return customerName;
	}
	
	
	
	
}
