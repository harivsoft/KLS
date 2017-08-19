package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.dao.IInterestSubsidyReportDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class InterestSubsidyReportDAO implements IInterestSubsidyReportDAO{
	private static final Logger logger = Logger.getLogger(InterestSubsidyReportDAO.class);

	
	@Override
	public List<Map> getTransHistoryRecoveredLoansBySeasonDate(Integer pacId, Long subSchemeId, String typeOfScheme) {

		logger.info("Start: Fetching the Loan loc list from the database in getTransHistoryRecoveredLoansBySeasonDate() method.=="+typeOfScheme);
		List<Map> transHistoryList = new ArrayList<Map>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder();
			if(typeOfScheme.equalsIgnoreCase("SB")){
				query.append("select new Map(l.id as locId,");
				query.append("sum(t.transactionAmount) as paidAmount,");
				query.append("l.sanctionedAmount as sanctionedAmount,(amt.subsidyReceivable+amt.subsidyAccrued) as subsidyReceivable)");
				query.append(" from ");
				query.append("TransactionHistory t,LineOfCredit l,");
				query.append("InterestSubsidy i,SubsidyInterestAmounts amt");
				query.append(" where i.id = "+subSchemeId+" and t.transactionCode in (1,5,6) and t.crDr = -1");
				query.append(" and l.season.id = i.seasonId and l.scheme.id = i.scheme.id and l.id = t.lineOfCredit.id");
				query.append(" and t.pacs.id = "+pacId);
				query.append(" and l.loanType = 'C'");
				query.append(" and amt.id.locId.id = l.id and amt.id.subsidySchemeId.id = i.id");
				query.append(" group by l.id,amt.subsidyReceivable,amt.subsidyPaid,amt.subsidyAccrued");
			}else{
				query.append("select new Map(l.id as locId,");
				query.append("sum(t.transactionAmount) as paidAmount,");
				query.append("l.sanctionedAmount as sanctionedAmount, case when p.asAndWhenImplemented = true then (amt.subsidyReceivable+amt.subsidyAccrued+amt.subsidyPaid) else (amt.subsidyReceivable+amt.subsidyPaid) end as subsidyReceivable)");
				query.append(" from ");
				query.append("TransactionHistory t,LineOfCredit l,");
				query.append("InterestSubsidy i,SubsidyInterestAmounts amt,Product p");
				query.append(" where i.id = "+subSchemeId+" and t.transactionCode in (1,5,6) and t.crDr = -1");
				query.append(" and l.season.id = i.seasonId and l.scheme.id = i.scheme.id and l.id = t.lineOfCredit.id");
				query.append(" and t.pacs.id = "+pacId);
				query.append(" and l.loanType = 'C'");
				query.append(" and amt.id.locId.id = l.id and amt.id.subsidySchemeId.id = i.id and p.id = l.product.id");
				query.append(" group by l.id,amt.subsidyReceivable,amt.subsidyAccrued,p.asAndWhenImplemented,amt.subsidyPaid order by locId");
			}
			Query qry = em.createQuery(query.toString());
	
			transHistoryList = qry.getResultList();
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
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the Loan recovery id list from the database in getTransHistoryRecoveredLoansBySeasonDate() method.");
		return transHistoryList;
	}
	
	

}
