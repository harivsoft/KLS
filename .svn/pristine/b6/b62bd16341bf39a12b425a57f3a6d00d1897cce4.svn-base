package com.vsoftcorp.kls.service.util;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.SBNextNumbers;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class VoucherNumberUtil {

	private static final Logger logger = Logger.getLogger(VoucherNumberUtil.class);

	public synchronized static Integer getVoucherNumber(String pacsId, String transType) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		boolean flgTrans = false;
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
			flgTrans = true;
		}
		SBNextNumbers sbNextNumbers = null;
		try {
			String query = "Select v from SBNextNumbers v where v.pacsId= '" + Integer.parseInt(pacsId) + "' and v.transType= '" + transType + "'";
			sbNextNumbers = em.createQuery(query, SBNextNumbers.class).getSingleResult();
			if (sbNextNumbers != null) {
				sbNextNumbers.setLastUsedNumber(sbNextNumbers.getLastUsedNumber() + 1);
				em.merge(sbNextNumbers);
			} else {
				throw new DataAccessException("Voucher Number Does not created.");
			}
			if(flgTrans)
				em.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Voucher Number Does not created.");
			e.printStackTrace();
			throw new DataAccessException("Voucher Number Does not created.");
		}
		return sbNextNumbers.getLastUsedNumber();
	}

}
