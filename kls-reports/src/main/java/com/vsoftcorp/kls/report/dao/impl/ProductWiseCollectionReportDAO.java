package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.report.dao.IProductWiseCollectionReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public class ProductWiseCollectionReportDAO implements IProductWiseCollectionReportDAO {

	private static final Logger logger = Logger.getLogger(ProductWiseCollectionReportDAO.class);

	@Override
	public List<LoanLineOfCredit> getProductWiseCollectionReportData(Integer pacsId, Integer productId, Integer purposeId, String loanType) {
		List<LoanLineOfCredit> locList = new ArrayList<LoanLineOfCredit>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder("");
			query.append("select lc from LoanLineOfCredit lc where lc.loanType='L' and ");
			if (pacsId != 0)
				query.append("lc.account.accountProperty.pacs.id=" + pacsId + " and ");
			if (productId != 0)
				query.append("lc.product.id=" + productId + " and ");
			query.append("lc.product.productType.loanType='" + loanType + "'");
			if (purposeId != 0)
				query.append(" and lc.pacsLoanApplication.purpose.id=" + purposeId);
			query.append(" order by lc.account.accountProperty.pacs.branch.id,lc.account.accountProperty.pacs.id,lc.product.id,lc.pacsLoanApplication.purpose.id");
			locList = em.createQuery(query.toString()).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			throw new KlsReportRuntimeException("Error while getting Product wise collection report data");
		}
		
		/*finally
		{
			EntityManagerUtil.closeSession();
		}*/
		return locList;
	}

	@Override
	public Object[] getProductWiseCollectionReportDataByLocId(Long locId, Date fromDate, Date toDate) {
		Object[] result = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select sum(lr.principalPaid) as principal,sum(lr.interestPaid) as interest,sum(lr.penalInterestPaid) as penalInterest,(select sum(pld.disbursedAmount) from "
							+ "PacsLoanDisbursement pld where pld.lineOfCredit.id=:locId and pld.disbursementDate between :fromDate and :toDate) "
							+ "from LoanRecovery lr where lr.loanLineOfCredit.id=:locId and lr.recoveredDate between :fromDate and :toDate");
			query.setParameter("locId", locId).setParameter("fromDate", fromDate).setParameter("toDate", toDate);
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new KlsReportRuntimeException("Error while getting Product wise collection report data");
		}
		
		/*finally
		{
			EntityManagerUtil.closeSession();
		}*/
		return result;
	}
}
