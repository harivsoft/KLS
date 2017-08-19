package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.dataaccess.loan.IPacsGLEntryDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;

/**
 * 
 * @author a1605
 * 
 */
public class PacsGLEntryDAO implements IPacsGLEntryDAO {
	private static final Logger logger = Logger.getLogger(PacsGLEntryDAO.class);

	@Override
	public AccountingMoney getAmountForPacsBankAccount(Integer pacsId) {
		logger.info("Start: in getAmountForPacsBankAccount");

		AccountingMoney resultList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select sum(ct.transactionAmount) from CurrentTransaction ct where ct.pacs.id=:pacsId  and ct.transactionType!=:transactionType and ((ct.transactionCode =1 and ct.crDr=-1 and ct.channelType = 'S') or (ct.transactionCode=5 or ct.transactionCode=6 or ct.transactionCode=19))");
			query.setParameter("pacsId", pacsId);
			query.setParameter("transactionType", TransactionType.Borrowings);
			resultList = (AccountingMoney) query.getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: while getting transaction amount for pacsbankaccount ");
			throw new DataAccessException("could not get amount for bank pacs account number");
		}
		logger.info("End: in getAmountForPacsBankAccount");

		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getBankGLTransactions() {
		List<Map> result = new ArrayList<Map>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder queryString = new StringBuilder("");
			queryString
					.append("select new Map(ct.lineOfCredit.product.id as productId,ct.pacs.id as pacsId, "
							+ "ct.transactionCode as transactionCode,(ct.crDr)*(-1) as crDr,sum(ct.transactionAmount) "
							+ "as transactionAmount) from CurrentTransaction ct where ct.transactionType=:transactionType "
							+ "group by ct.pacs.id,ct.lineOfCredit.product.id,ct.transactionCode,ct.crDr");
			Query query = em.createQuery(queryString.toString());
			query.setParameter("transactionType", TransactionType.Borrowings);
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("Error while getting bank gl extract data");
		}
		return result;
	}

}
