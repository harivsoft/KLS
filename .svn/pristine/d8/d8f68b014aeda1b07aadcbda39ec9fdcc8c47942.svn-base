package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.dataaccess.loan.IKLSCustomerDAO;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class KLSCustomerDAO implements IKLSCustomerDAO {
	
	private static final Logger logger = Logger.getLogger(KLSCustomerDAO.class);

	@Override
	public Long getCustomerDetails(String customerNumber,Integer pacsId) {
		
		logger.info("Start: Fetching customer details by mumber number in getCustomerDetails()");
		Long partyId=null;
		try{
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createNativeQuery("select c.party_id from membership.person c where c.member_number='"+customerNumber+"' and c.pacs_id="+pacsId);
			Object temp = query.getSingleResult();
			if(temp != null){
				partyId = ((BigDecimal)temp).longValue();
			}
			
		}catch(NoResultException nre){
			logger.error("ERROR: No customer was found with given member number: "+customerNumber);
			partyId=null;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR: Error while fetching customer details with given member number: "+customerNumber);
		}
		
		
		logger.info("End: Fetching customer details by mumber number in getCustomerDetails()");
		return partyId;
	}

}
