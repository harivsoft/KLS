package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.SBNextNumbers;
import com.vsoftcorp.kls.business.entities.SlabwiseInterestRate;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.subsidy.InterestSubsidy;
import com.vsoftcorp.kls.business.entity.subsidy.SlabwisesubsidyInterestRate;
import com.vsoftcorp.kls.business.entity.subsidy.SubsidyInterestAmounts;
import com.vsoftcorp.kls.business.entity.subsidy.SubsidyInterestAmountsId;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.entity.transaction.TransactionHistory;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.ILineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanInterestCalculationDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanLineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.pagination.ResultPagerBean;
import com.vsoftcorp.kls.dataaccess.subsidy.ISubsidyInterestAmountsDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.loan.util.InterestCalculationUtil;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.interest.InterestCalculationBasis;
import com.vsoftcorp.kls.valuetypes.loanproduct.PenalInterestApplicable;
import com.vsoftcorp.kls.valuetypes.subsidy.TypeOfScheme;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;
import com.vsoftcorp.time.Date;
import com.vsoftcorp.time.Second;

/**
 * @author sponnam
 * 
 */
public class LoanInterestCalculationDAO implements ILoanInterestCalculationDAO {

	private static final Logger logger = Logger.getLogger(LoanInterestCalculationDAO.class);

	@Override
	public void calculateInterest(Date theBusinessDate, InterestCalculationBasis theCalculationBasis,List<Integer> pacsList) {
		logger.info(" START TIME-----" + new java.util.Date());
		if (theCalculationBasis.getValue().equalsIgnoreCase(InterestCalculationBasis.Daily.getValue())) {
			currentDayInterestCalculation(theBusinessDate,pacsList);
		}
		logger.info(" END TIME-----" + new java.util.Date());
	}

	@Override
	public SlabwiseInterestRate getRateOfInterest(Integer interestCategoryId, Date theBusinessDate, Money theBalance) {

		logger.info("business date:" + theBusinessDate);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			SlabwiseInterestRate interestRate = null;

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<SlabwiseInterestRate> cq = cb.createQuery(SlabwiseInterestRate.class);
			Root<SlabwiseInterestRate> SlabwiseInterestRateRoot = cq.from(SlabwiseInterestRate.class);

			Predicate predicate1 = cb.equal(SlabwiseInterestRateRoot.get("interestCategory"), interestCategoryId);
			Predicate predicate2 = cb.lessThanOrEqualTo(SlabwiseInterestRateRoot.<Money> get("fromSlab"), Money.absolute(theBalance));
			Predicate predicate3 = cb.greaterThanOrEqualTo(SlabwiseInterestRateRoot.<Money> get("toSlab"), Money.absolute(theBalance));

			cq.where(cb.and(predicate1, predicate2, predicate3));

			TypedQuery<SlabwiseInterestRate> tq = em.createQuery(cq);
			if (tq.getResultList().size() > 0)
				interestRate = tq.getResultList().get(0);

			return interestRate;

		} catch (Exception excp) {
			logger.error(" exception while fetching ROI");
			throw new DataAccessException("Unable to fetch ROI from database", excp.getCause());
		}

	}
	
	@Override
	public SlabwisesubsidyInterestRate getSubsidyRateOfInterest(Long subsidyId,Money theBalance) {
		logger.info("START: Fetching the slab wise subsidy ROI data from the database in getSubsidyRateOfInterest() method.");
		SlabwisesubsidyInterestRate subsidyROI = null;
		List<SlabwisesubsidyInterestRate> slabwisesubsidyInterestRate=null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<SlabwisesubsidyInterestRate> cq = cb.createQuery(SlabwisesubsidyInterestRate.class);
			Root<SlabwisesubsidyInterestRate> SlabwiseInterestRateRoot = cq.from(SlabwisesubsidyInterestRate.class);

			Predicate predicate1 = cb.equal(SlabwiseInterestRateRoot.get("interestSubsidy"), subsidyId);
			Predicate predicate2 = cb.lessThanOrEqualTo(SlabwiseInterestRateRoot.<Money> get("fromAmount"), Money.absolute(theBalance));
			Predicate predicate3 = cb.greaterThan(SlabwiseInterestRateRoot.<Money> get("toAmount"), Money.absolute(theBalance));

			cq.where(cb.and(predicate1, predicate2, predicate3));

			TypedQuery<SlabwisesubsidyInterestRate> tq = em.createQuery(cq);
			
			/*Query query = em.createQuery("select s from SlabwisesubsidyInterestRate s where s.interestSubsidy.id="+subsidyId+""
					+ " and "+theBalance+" between fromAmount and toAmount");*/
			slabwisesubsidyInterestRate = tq.getResultList();
			if(slabwisesubsidyInterestRate != null && !slabwisesubsidyInterestRate.isEmpty()){
				subsidyROI=slabwisesubsidyInterestRate.get(0);
			}
		}catch(NoResultException e){
			logger.error("No records found with this interest subsidy id"+subsidyId);
			slabwisesubsidyInterestRate = null;
			
		}catch (Exception excp) {
			logger.error("Could not fetch the slab wise subsidy ROI data from the "
					+ "database using interestSubsidyId Exception thrown.");
			throw new DataAccessException("Could not fetch the slab wise subsidy ROI data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the slab wise subsidy ROI data from the database in getSubsidyRateOfInterest() method.");
		return subsidyROI;
	}
	
	@Override
	public List<InterestSubsidy> getSubsidyBySchemeIdDAO(Integer schemeId, Long seasonId) {
		logger.info("Start: Fetching the Interest Subsidy data from the database in getSubsidyBySchemeIdDAO() method.");
		List<InterestSubsidy> interestSubsidy = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("select s from InterestSubsidy s where s.scheme.id="+schemeId+" and seasonId="+seasonId);
			interestSubsidy = query.getResultList();
		}catch(NoResultException e){
			logger.error("No records found with this interest subsidy id"+schemeId);
			interestSubsidy = null;
			
		}catch (Exception excp) {
			logger.error("Could not fetch the InterestSubsidy data from the "
					+ "database using interestSubsidyId Exception thrown.");
			throw new DataAccessException("Could not fetch the InterestSubsidy data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the InterestSubsidy data from the database in getSubsidyBySchemeIdDAO() method.");
		return interestSubsidy;
	}
	

	@Override
	public void currentDayInterestCalculation(Date theBusinessDate,List<Integer> pacsList) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		// TODO: To take no of days as the difference between the business date
		// and lastintcal date
		ResultPagerBean bean = new ResultPagerBean();
		bean.init(10, "LineOfCredit.getTotalCountOfActiveLOCs", "LineOfCredit.findAllActiveLOCs", em,pacsList);
		Second s1 = new Second();
		boolean done = false;
		List locList = new ArrayList<>();
		logger.info("max results------------" + bean.getMaxResults());
		logger.info("page size" + bean.getPageSize());
		while (!done) {

			logger.info("max pages" + bean.getMaxPages());
			logger.info("current pages" + bean.getCurrentPage());

			if (!done) {
				locList = bean.getCurrentResults(em,pacsList);
			}
			logger.info("list Size" + locList.size());
			/**
			 * boolean flgTrans = false; if (!em.getTransaction().isActive()) {
			 * em.getTransaction().begin(); flgTrans = true; }
			 **/

			// em.getTransaction().begin();
			for (Iterator iterator = locList.iterator(); iterator.hasNext();) {
				LineOfCredit lineOfCredit = (LineOfCredit) iterator.next();
				logger.info("LOC ID ::--------: " + lineOfCredit.getId());
				logger.info("loan type ::---" + lineOfCredit.getLoanType());

				if(isLOCClear(lineOfCredit)) continue;
				
				BigDecimal theAmount = lineOfCredit.getCurrentBalance().getMoney().getAmount();

				// If balance is less than zero, calculate else set the
				// balance
				// as zero so that no calculation happens
				if (!lineOfCredit.getLoanType().equals("B")) {
					if (lineOfCredit.getCurrentBalance().getDebitOrCredit().getValue() == 0) // Debit
																								// balance
						theAmount = theAmount.negate();
					else
						theAmount = BigDecimal.ZERO;
				} else {
					if (!(lineOfCredit.getCurrentBalance().getDebitOrCredit().getValue() > 0)) // Not
																								// credit
																								// balance
						theAmount = BigDecimal.ZERO;
				}

				BigDecimal interestAccrued = BigDecimal.ZERO;
				BigDecimal overDueInterest = BigDecimal.ZERO;

				SlabwiseInterestRate interestRate = getRateOfInterest(lineOfCredit.getInterestCategory().getId(), theBusinessDate,
						lineOfCredit.getSanctionedAmount());
				interestAccrued = InterestCalculationUtil.perDaySimpleInterest(theAmount,
						interestRate == null ? BigDecimal.ZERO : interestRate.getRoi(), 1);
				/*
				 * calculating Subsidy based on schemeID only for ST. for MT/LT need to modify code
				 */
				
				if (lineOfCredit.getScheme() != null) {
					doSubsidyAndSubventionCalculation(lineOfCredit, theAmount, theBusinessDate);
				}
				if ((lineOfCredit.getLoanType().equals("L") || lineOfCredit.getLoanType().equals("B")) && lineOfCredit.getCurrentBalance().compareTo(AccountingMoney.ZERO) != 0) {
					overDueInterest = calculateMTLTPenalInterest(lineOfCredit, theBusinessDate, interestRate);
				} else {
					Date dueDate = null;
					if (lineOfCredit.getLoanType().equals("C")){
						/*dueDate = lineOfCredit.getSeason().getOverdueDate();*/
						dueDate = lineOfCredit.getLoanExpiryDate();
					}
					else if (lineOfCredit.getLoanType().equals("B"))
						dueDate = DateUtil.getNextDateByFreequncyPeroid(lineOfCredit.getSanctionedDate(), 12);
					if (dueDate != null && dueDate.compareTo(theBusinessDate) <= 0) {
						overDueInterest = InterestCalculationUtil.perDaySimpleInterest(theAmount, interestRate == null ? BigDecimal.ZERO
								: interestRate.getPenalRoi(), 1);
						logger.info("overDueInterest = "+overDueInterest);
					}
				}
				lineOfCredit.setInterestAccrued(lineOfCredit.getInterestAccrued().add(interestAccrued));
				lineOfCredit.setOverdueInterest(lineOfCredit.getOverdueInterest().add(overDueInterest));
				lineOfCredit.setLastInterestCalculatedDate(theBusinessDate);
				List<LineOfCredit> locs = new ArrayList<LineOfCredit>();
				if(lineOfCredit.getLoanType().equalsIgnoreCase("C") && lineOfCredit.getLoanExpiryDate().compareTo(theBusinessDate)<=0 ){
					logger.info("loc is added for overdue interest (penal Interest)");
						locs.add(lineOfCredit);
						try{
							List<CurrentTransaction> intPostTransactionList = getCurrentTransaction(locs, theBusinessDate);

							ICurrentTransactionDAO dao = KLSDataAccessFactory.getCurrentTransactionDAO();
							
							dao.saveCurrentTransaction(intPostTransactionList,
									Collections.<String, List<Object>> emptyMap());
							
						}catch(Exception exception){
							exception.printStackTrace();
							logger.error("Error in posting interest");
							throw new DataAccessException("Interest not Posted, some  problem ouccured.",exception.getCause());
						}
				}else if(lineOfCredit.getLoanType().equalsIgnoreCase("L") /*&& lineOfCredit.getLoanExpiryDate().compareTo(theBusinessDate)==0*/){
					ILineOfCreditDAO locDAO = KLSDataAccessFactory.getLineOfCreditDAO();
					//LoanLineOfCredit lloc = (LoanLineOfCredit) locDAO.getLocId(lineOfCredit.getId());
					Date expiryDate = DateUtil.getVSoftDateByString(locDAO.getLineOfExpiryDate(lineOfCredit.getId()));
					if(expiryDate != null){
						if(expiryDate.compareTo(theBusinessDate)==0){
							locs.add(lineOfCredit);
							try{
								List<CurrentTransaction> intPostTransactionList = getCurrentTransaction(locs, theBusinessDate);
		
								ICurrentTransactionDAO dao = KLSDataAccessFactory.getCurrentTransactionDAO();
								
								dao.saveCurrentTransaction(intPostTransactionList,
										Collections.<String, List<Object>> emptyMap());
								
							}catch(Exception exception){
								exception.printStackTrace();
								logger.error("Error in posting interest");
								throw new DataAccessException("Interest not Posted, some  problem ouccured.",
										exception.getCause());
							}
						}
					}
				}
				
				/*
				 * Query query = em .createQuery(
				 * "UPDATE LineOfCredit l SET interestAccrued = interestAccrued+:theInterestAccrued,overdueInterest=overdueInterest+:theOverDueInterest,lastInterestCalculatedDate=:theBusinessDate where l.id=:id"
				 * ); query.setParameter("theInterestAccrued", interestAccrued);
				 * query.setParameter("theOverDueInterest", overDueInterest);
				 * query.setParameter("theBusinessDate", theBusinessDate);
				 * query.setParameter("id", lineOfCredit.getId()); int count =
				 * query.executeUpdate(); logger.info("count is for update" +
				 * count);
				 */
			} // end for for loop
			/**
			 * if (flgTrans) em.getTransaction().commit();
			 **/

			/*
			 * if (bean.getCurrentPage() == 10) break;
			 */
			if (bean.getCurrentPage() > bean.getMaxPages() || bean.getMaxResults() < bean.getPageSize()) {
				done = true;
			}
			bean.next();

		} // end for while
			// EntityManagerUtil.closeSession();
		Second s2 = new Second();
		logger.info(" START TIME:" + s1 + "--END TIME:" + s2 + "-DIFFERENCE:" + s2.subtract(s1));
	}


	private boolean isLOCClear(LineOfCredit lineOfCredit) {
		boolean flag = false;
		int intAcc = lineOfCredit.getInterestAccrued().compareTo(BigDecimal.ZERO);
		int intRecivable = lineOfCredit.getInterestReceivable().compareTo(BigDecimal.ZERO);
		int penalInt = lineOfCredit.getPenalInterestReceivable().compareTo(BigDecimal.ZERO);
		int overdueInt = lineOfCredit.getOverdueInterest().compareTo(BigDecimal.ZERO);
		if(lineOfCredit.getLoanType().equals("C")){
			if((lineOfCredit.getCurrentBalance().isZero() || lineOfCredit.getCurrentBalance().isCredit()) 
	   			 && (intAcc == 0 || intAcc == 1) 
	   			 && (intRecivable == 0 || intRecivable == 1) 
	   			 && (penalInt == 0 || penalInt == 1)
	   			 && (overdueInt == 0 || overdueInt == 1)){	
					flag = true;
		        }	
		}else if(lineOfCredit.getLoanType().equals("B")){
			if((lineOfCredit.getCurrentBalance().isZero() || lineOfCredit.getCurrentBalance().isDebit()) 
		   			 && (intAcc == 0 || intAcc == -1) 
		   			 && (intRecivable == 0 || intRecivable == -1) 
		   			 && (penalInt == 0 || penalInt == -1)
		   			 && (overdueInt == 0 || overdueInt == -1)){	
					flag = true;
			    }	
		}
		
		return flag;
	}

	private void doSubsidyAndSubventionCalculation(LineOfCredit lineOfCredit, BigDecimal theAmount, Date theBusinessDate) {
	    List<InterestSubsidy> subsidyList = getSubsidyBySchemeIdDAO(lineOfCredit.getScheme().getId(), lineOfCredit.getSeason().getId());
	    
	    /*Date stdueDate = lineOfCredit.getSeason().getOverdueDate();*/
		Date stdueDate = lineOfCredit.getLoanExpiryDate();
		ICurrentTransactionDAO cDao = KLSDataAccessFactory.getCurrentTransactionDAO();
	   if (subsidyList != null && !subsidyList.isEmpty()) {
		  
		   for(InterestSubsidy subsidy : subsidyList){
			   List<SlabwisesubsidyInterestRate> slabList = getSlabwisesubsidyList(subsidy.getId().intValue());
			   	Date startDate = cDao.getFirstDrawalLocDates(lineOfCredit.getId());
				logger.info("startDate=========="+startDate);
			   	//Date startDate = lineOfCredit.getSeason().getDrawalStartDate();
				Integer diffMounths = getNoOfMonthsDiff(theBusinessDate, startDate);
				//theBusinessDate.
			  if(stdueDate != null){
				if(TypeOfScheme.SUBSIDY.compareTo(subsidy.getTypeOfScheme()) ==0){
						if (stdueDate.compareTo(theBusinessDate) > 0 && diffMounths<= subsidy.getMaxPeriodForSubsidy()){
							postSubsidy(subsidy, lineOfCredit, theAmount, false, slabList);
						}
				}else{
				 if(stdueDate.compareTo(theBusinessDate) > 0 && diffMounths<= subsidy.getMaxPeriodForSubsidy()){
							postSubsidy(subsidy, lineOfCredit, theAmount, false, slabList);
				 }else{
							postSubsidy(subsidy, lineOfCredit, theAmount, true, slabList);
				    }
				}
		      }
			}
		  // SubsidyOrSubvention on Overdue Date.
		  doSubsidyOrSubventionTransOnDueDate(stdueDate, theBusinessDate, lineOfCredit);
		
	}
}

	private void doSubsidyOrSubventionTransOnDueDate(Date stdueDate, Date theBusinessDate, LineOfCredit lineOfCredit) {
		if(stdueDate != null){
		 if(stdueDate.equals(theBusinessDate)){
		
		 //Updating Subvention amounts to line of credit on due date  
		 ISubsidyInterestAmountsDAO sDao = KLSDataAccessFactory.getSubsidyInterestAmountsDAO();
	   	 List<SubsidyInterestAmounts> list = sDao.getSubsidyListByLocId(lineOfCredit.getId());
	   	 if(list.size()>0){
	   		BigDecimal subsidyAcrud = BigDecimal.ZERO;
			BigDecimal subsidyReceivable = BigDecimal.ZERO;
			   for(SubsidyInterestAmounts subAmounts : list){
				   if(TypeOfScheme.SUBVENTION.compareTo(subAmounts.getId().getSubsidySchemeId().getTypeOfScheme()) == 0){
					   if(subAmounts.getSubsidyAccrued()!=null)
					   subsidyAcrud = subsidyAcrud.add(subAmounts.getSubsidyAccrued());
					   if(subAmounts.getSubsidyReceivable()!=null)
					   subsidyReceivable = subsidyReceivable.add(subAmounts.getSubsidyReceivable());
				   }
			   }
			     logger.info("subsidyAcrud : "+subsidyAcrud);
				 logger.info("subsidyReceivable : "+subsidyReceivable);
				   
				  //Updating line of credit amounts
				   
				  lineOfCredit.setInterestAccrued(lineOfCredit.getInterestAccrued().add(subsidyAcrud));
				  lineOfCredit.setInterestReceivable(lineOfCredit.getInterestReceivable().add(subsidyReceivable));
				  KLSDataAccessFactory.getLineOfCreditDAO().updateLineOfCredit(lineOfCredit);
				  //interest posting transactions for Subvention amount
				  doSubventionInterestPosting(lineOfCredit,theBusinessDate,subsidyReceivable);
	   	   }
			
	   	 	  // Reversal transactons for subsidy recovery on due date
			  doReversalTransactions(lineOfCredit);
			 
			  //Updating subsidy amounts as zero for over due locs
			  doSubsidyAmountsZero(lineOfCredit);
		}
	  }
  }

	private void doSubventionInterestPosting(LineOfCredit loc, Date theBusinessDate, BigDecimal subsidyReceivable) {
		
		Pacs pacs = null;
		Integer voucherNumber = null;
		String voucher = null;

		pacs = loc.getAccount().getAccountProperty().getPacs();
	
		String transType = TransactionType.Transfer.getValue();
		voucherNumber = getVoucherNumber(pacs.getId().toString(), transType);
		voucher = TransactionType.Transfer.getValue() + "-" + voucherNumber;
		List<CurrentTransaction> currentTrxnList = new ArrayList<CurrentTransaction>();
		if (subsidyReceivable.compareTo(BigDecimal.ZERO) != 0) {
					
			//loan interest posting
			CurrentTransaction intReceivableDrTrxn = new CurrentTransaction();
			intReceivableDrTrxn.setLineOfCredit(loc);
			intReceivableDrTrxn.setBusinessDate(theBusinessDate);
			intReceivableDrTrxn.setAccount(loc.getAccount());
			intReceivableDrTrxn.setAccountNumber(loc.getAccount().getAccountNumber());
			intReceivableDrTrxn.setChannelType(ChannelType.SYSTEM);
			AccountingMoney transAmt = MoneyUtil.getAccountingMoney(subsidyReceivable.abs().setScale(0, RoundingMode.HALF_UP));
			intReceivableDrTrxn.setOpeningBalance(loc.getCurrentBalance());
			intReceivableDrTrxn.setPostedStatus(1);
			intReceivableDrTrxn.setTransactionAmount(transAmt);
			
			intReceivableDrTrxn.setTransactionType(TransactionType.Transfer);
			intReceivableDrTrxn.setCrDr(-1);
			intReceivableDrTrxn.setRemarks("Interest Subvention posted Receivable");
			
			intReceivableDrTrxn.setPacs(pacs);

			intReceivableDrTrxn.setTransactionCode(TransactionCode.INTEREST_RECEIVABLE);

			intReceivableDrTrxn.setVoucherNumber(voucher);

			// For Credit Transaction
			CurrentTransaction intReceivableCrTrxn = new CurrentTransaction();
			intReceivableCrTrxn.setLineOfCredit(loc);
			intReceivableCrTrxn.setBusinessDate(theBusinessDate);
			intReceivableCrTrxn.setAccount(loc.getAccount());
			intReceivableCrTrxn.setAccountNumber(loc.getAccount().getAccountNumber());
			intReceivableCrTrxn.setChannelType(ChannelType.SYSTEM);

			intReceivableCrTrxn.setOpeningBalance(loc.getCurrentBalance());
			intReceivableCrTrxn.setPostedStatus(1);
			intReceivableCrTrxn.setTransactionAmount(transAmt);
			
			intReceivableCrTrxn.setTransactionType(TransactionType.Transfer);
			intReceivableCrTrxn.setCrDr(1);
			intReceivableCrTrxn.setRemarks("Interest Subvention Posted Received");
			
			intReceivableCrTrxn.setPacs(pacs);

			intReceivableCrTrxn.setTransactionCode(TransactionCode.INTEREST_RECEIVED);
			intReceivableCrTrxn.setVoucherNumber(voucher);
			currentTrxnList.add(intReceivableDrTrxn);
			currentTrxnList.add(intReceivableCrTrxn);
			KLSDataAccessFactory.getCurrentTransactionDAO().saveCurrentTransactionsList(currentTrxnList);
		} // end for interest		
	}

	private void doSubsidyAmountsZero(LineOfCredit lineOfCredit) {
			ISubsidyInterestAmountsDAO sDao = KLSDataAccessFactory.getSubsidyInterestAmountsDAO();
			 List<SubsidyInterestAmounts> list = sDao.getSubsidyListByLocId(lineOfCredit.getId());
			 for(SubsidyInterestAmounts subsidy : list){
				 subsidy.setSubsidyAccrued(BigDecimal.ZERO);
				 subsidy.setSubsidyPaid(BigDecimal.ZERO);
				 subsidy.setSubsidyReceivable(BigDecimal.ZERO);
				 sDao.updateSubsidyInterestAmounts(subsidy);
			 }
	}

	public Integer getNoOfMonthsDiff(Date businessDate,Date startDate){
			Integer diffYear=0;
			Integer diffMonth=0;
			Integer daydiff=0;
			Calendar startCal = new GregorianCalendar();
			startCal.setTime(startDate.getJavaDate());
			Calendar businessDateCal = new GregorianCalendar();
			businessDateCal.setTime(businessDate.getJavaDate());
			diffYear = businessDateCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
			logger.info("Year Diff="+diffYear);
			daydiff = businessDateCal.get(Calendar.DATE) - startCal.get(Calendar.DATE);
			logger.info("Days Diff="+daydiff);
			if(daydiff>=0){
				diffMonth=diffMonth+1;
			}
			diffMonth = diffMonth+ diffYear * 12 + businessDateCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH);
			logger.info("mounths Diff="+diffMonth);
			return diffMonth;
	}
	
	public void postSubsidy(InterestSubsidy subsidy, LineOfCredit lineOfCredit,BigDecimal theAmount, boolean isOverdue, List<SlabwisesubsidyInterestRate> slabList){
		BigDecimal subsidyAccrued = BigDecimal.ZERO;
		SlabwisesubsidyInterestRate subsidyROI = getSubsidyRateOfInterest(subsidy.getId(),lineOfCredit.getSanctionedAmount());
		
		subsidyAccrued = calculateSubsidyInterest(MoneyUtil.getAccountingMoney(theAmount), slabList);
		
//		subsidyAccrued = InterestCalculationUtil.perDaySimpleInterest(theAmount, subsidyROI == null ? BigDecimal.ZERO : subsidyROI.getSubsidyRoiPerAnnum(),1);
		if(isOverdue == false){
			SubsidyInterestAmounts subsidyInterestAmounts = new SubsidyInterestAmounts();
			SubsidyInterestAmountsId sid = new SubsidyInterestAmountsId();
			sid.setLocId(lineOfCredit);
			sid.setSubsidySchemeId(subsidy);
			subsidyInterestAmounts.setId(sid);
			subsidyInterestAmounts.setSubsidyReceivable(BigDecimal.ZERO);
			subsidyInterestAmounts.setSubsidyPaid(BigDecimal.ZERO);
			subsidyInterestAmounts.setSubsidyAccrued(subsidyAccrued);
			ISubsidyInterestAmountsDAO subsidyDAO = KLSDataAccessFactory.getSubsidyInterestAmountsDAO();
			subsidyDAO.saveSubsidyInterestAmounts(subsidyInterestAmounts);
		}else{
			lineOfCredit.setInterestAccrued(lineOfCredit.getInterestAccrued().add(subsidyAccrued));
			ILineOfCreditDAO lDao = KLSDataAccessFactory.getLineOfCreditDAO();
			lDao.updateLineOfCredit(lineOfCredit);
		}
	}

	@Override
	public void updateInterest(LineOfCredit theLoc, Money theInterestAccrued) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		theLoc.setInterestAccrued(theInterestAccrued.getAmount());
		em.merge(theLoc);
		em.getTransaction().commit();
		// EntityManagerUtil.closeSession();
	}

	@Override
	public List<LineOfCredit> getLineOfCreditList(List<Integer> pacsList) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<LineOfCredit> list = new ArrayList<LineOfCredit>();
		ResultPagerBean bean = new ResultPagerBean();
		bean.init(10, "LineOfCredit.getTotalCountOfActiveLOCs", "LineOfCredit.findAllActiveLOCs", em,pacsList);
		if (bean.getMaxPages() > bean.getCurrentPage()) {
			list = bean.getCurrentResults(em,pacsList);
		}
		logger.info("max pages" + bean.getMaxPages());
		logger.info("current pages" + bean.getPageSize());
		logger.info("max results------------" + bean.getMaxResults());
		logger.info("page size" + bean.getPageSize());
		// EntityManagerUtil.closeSession();
		return list;
	}

	/**
	 * Penal Interest calculation for MT/LT loans
	 * 
	 * @param loc
	 * @param businessDate
	 * @param interestRate
	 * @return
	 */
	private BigDecimal calculateMTLTPenalInterest(LineOfCredit loc, Date businessDate, SlabwiseInterestRate interestRate) {
		BigDecimal overDueAmount = BigDecimal.ZERO;
		BigDecimal penalInt = BigDecimal.ZERO;
		String penalInterestApplicable = loc.getProduct().getPenalInterestApplicable().getValue();
		String penal = PenalInterestApplicable.NOT_APPLICABLE.getValue();
		if (!penalInterestApplicable.equals(penal)) {
			Map map = getPrincipleAndInterestAmtByLocId(loc.getId(), businessDate);
			if (map != null) {
				Money totalPrincipleReceivableAmt = (Money) map.get("princlipleAmount");
				// BigDecimal totalInterestReceivableAmt = (BigDecimal)
				// map.get("interestAmt");
				if (totalPrincipleReceivableAmt != null) {

					BigDecimal totalInterestReceivableAmt = loc.getInterestReceivable().add(loc.getPenalInterestReceivable());
					int temp = totalInterestReceivableAmt.compareTo(BigDecimal.ZERO);
					if (temp == 1 || temp == 0)
						totalInterestReceivableAmt = BigDecimal.ZERO;
					else if (temp == -1)
						totalInterestReceivableAmt = totalInterestReceivableAmt.abs();

					overDueAmount = (totalPrincipleReceivableAmt.subtract(getTotalAmountPaidByLocId(loc.getId()))).getAmount().add(
							totalInterestReceivableAmt);

					if (overDueAmount.compareTo(BigDecimal.ZERO) == -1) {
						overDueAmount = BigDecimal.ZERO;
					}

					if (penalInterestApplicable.equals(PenalInterestApplicable.OUTSTANDING.getValue())) {
						if (overDueAmount.compareTo(BigDecimal.ZERO) != 0) // Defaulter
							overDueAmount = loc.getCurrentBalance().getMoney().getAmount(); // is
																							// +ve
					}
				}

				penalInt = InterestCalculationUtil.perDaySimpleInterest(overDueAmount,
						interestRate == null ? BigDecimal.ZERO : interestRate.getPenalRoi(), 1);
			}
		}

		return penalInt.negate();
	}

	@Override
	public Map getPrincipleAndInterestAmtByLocId(Long locId, Date businessDate) {
		Map map = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			map = (Map) em
					.createQuery(
							"select new Map(sum(lpc.principalAmount) as princlipleAmount,sum(lpc.interestAmount) as interestAmt) from LoanRepaymentSchedule lpc where lpc.loanRepaymentScheduleId.lineOfCreditId=:lineOfCreditId and lpc.installmentDate<=:businessDate")
					.setParameter("lineOfCreditId", locId).setParameter("businessDate", businessDate).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		} catch (Exception e) {
			logger.error("Error occured in getPrincipleAndInterestAmtByLocId(");
			throw new DataAccessException("Error occured in getPrincipleAndInterestAmtByLocId( )");
		}
		return map;
	}

	public Money getTotalAmountPaidByLocId(Long locId) {
		Money totalAmountPaid = Money.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			totalAmountPaid = (Money) em
					.createQuery("select sum(lc.principalPaid) from LoanRecovery lc where lc.loanLineOfCredit.id=:lineOfCreditId")
					.setParameter("lineOfCreditId", locId).getSingleResult();
		} catch (NoResultException noResultException) {
			return totalAmountPaid;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occured in getTotalAmountPaidByLocId(");
			throw new DataAccessException("Error occured in getTotalAmountPaidByLocId( )");
		}
		return totalAmountPaid != null ? totalAmountPaid : Money.ZERO;
	}
	
	private List<CurrentTransaction> doReversalTransactions(LineOfCredit loc) {
		// TODO Auto-generated method stub
		List<CurrentTransaction> reversalTrans = new ArrayList<>();
		List<CurrentTransaction> transList = KLSDataAccessFactory.getCurrentTransactionDAO().getSubsidyAllSubsidyTransactions(loc);
		List<TransactionHistory> hisTransList = KLSDataAccessFactory.getCurrentTransactionDAO().getSubsidyAllSubsidyHisTransactions(loc);
	
		for(TransactionHistory t : hisTransList){
			CurrentTransaction c = new CurrentTransaction();
			c.setId(t.getId());
			c.setAccount(t.getAccount());
			c.setAccountNumber(t.getAccountNumber());
			c.setBusinessDate(t.getBusinessDate());
			c.setChannelType(t.getChannelType());
			c.setCrDr(t.getCrDr());
			c.setLineOfCredit(t.getLineOfCredit());
			c.setOpeningBalance(t.getOpeningBalance());
			c.setPacs(t.getPacs());
			c.setPostedStatus(t.getPostedStatus());
			c.setRemarks(t.getRemarks());
			c.setTerminalId(t.getTerminalId());
			c.setTransactionAmount(t.getTransactionAmount());
			c.setTransactionCode(t.getTransactionCode());
			c.setTransactionType(t.getTransactionType());
			c.setVoucherNumber(t.getVoucherNumber());
			transList.add(c);
		}
		logger.info("transList after : "+transList.size());
		
		//AccountingMoney standaloneTransAmount = AccountingMoney.ZERO;
		//AccountingMoney onlineTransAmount = AccountingMoney.ZERO;
		Integer voucherNumber = getVoucherNumber(loc.getAccount().getAccountProperty().getPacs().getId().toString(), TransactionType.Transfer.getValue());
		String voucher = TransactionType.Transfer.getValue() + "-" + voucherNumber;
		ILineOfCreditDAO lDao = KLSDataAccessFactory.getLineOfCreditDAO();
		for(CurrentTransaction c : transList){
			CurrentTransaction currentTransaction = null;
			currentTransaction= doCopyOfObject(c);
			currentTransaction.setBusinessDate(getBusinessDate());
			currentTransaction.setCrDr(1);
			currentTransaction.setVoucherNumber(voucher);
			currentTransaction.setRemarks("reversal transaction for subsidy");
			reversalTrans.add(currentTransaction);
			
			CurrentTransaction currentTransaction1 = null;
			currentTransaction1 = doCopyOfObject(c);
			currentTransaction1.setBusinessDate(getBusinessDate());
			currentTransaction1.setVoucherNumber(voucher);
			currentTransaction1.setCrDr(-1);
			currentTransaction1.setAccount(loc.getAccount());
			currentTransaction1.setLineOfCredit(loc);
			currentTransaction1.setAccountNumber(loc.getAccount().getAccountNumber());
			currentTransaction1.setTransactionCode(TransactionCode.INTEREST_RECEIVABLE);
			currentTransaction1.setRemarks("Interest receivable reversal transaction for subsidy");
			reversalTrans.add(currentTransaction1);

			BigDecimal interestReceivable = loc.getInterestReceivable().add(c.getTransactionAmount().getDebitAmount().getAmount());
			loc.setInterestReceivable(interestReceivable);
			lDao.updateLineOfCredit(loc);
			
			/*if(c.getChannelType().equals(ChannelType.SYSTEM.getValue())){
				standaloneTransAmount = standaloneTransAmount.add(c.getTransactionAmount());
			}else{
				onlineTransAmount = onlineTransAmount.add(c.getTransactionAmount());
			}*/
		
		}
		/*logger.info("subsidy total transAmount for cash gl: "+onlineTransAmount);
		logger.info("standaloneTransAmount : "+standaloneTransAmount);*/
		
	
		/*if(!standaloneTransAmount.isZero()){
			CurrentTransaction currentTransaction = new CurrentTransaction();
			currentTransaction.setAccount(loc.getAccount());
			currentTransaction.setAccountNumber(loc.getAccount().getAccountProperty().getPacs().getCashInTransitGL());
			currentTransaction.setCrDr(-1);
			currentTransaction.setBusinessDate(getBusinessDate());
			currentTransaction.setTransactionAmount(standaloneTransAmount);
			currentTransaction.setVoucherNumber(voucher);
			currentTransaction.setLineOfCredit(loc);
			currentTransaction.setChannelType(ChannelType.SYSTEM);
			currentTransaction.setPostedStatus(1);
			currentTransaction.setTransactionType(TransactionType.Transfer);
			currentTransaction.setPacs(loc.getAccount().getAccountProperty().getPacs());
			currentTransaction.setRemarks("cash in transit reversal transaction for subsidy");
			currentTransaction.setTransactionCode(TransactionCode.CASH_IN_TRANSIT);
			reversalTrans.add(currentTransaction);
		}
		if(!onlineTransAmount.isZero()){
			CurrentTransaction currentTransaction = new CurrentTransaction();
			currentTransaction.setAccount(loc.getAccount());
			currentTransaction.setAccountNumber(loc.getAccount().getAccountProperty().getPacs().getPacsBankAccNumber());
			currentTransaction.setCrDr(-1);
			currentTransaction.setBusinessDate(getBusinessDate());
			currentTransaction.setVoucherNumber(voucher);
			currentTransaction.setTransactionAmount(onlineTransAmount);
			currentTransaction.setLineOfCredit(loc);
			currentTransaction.setChannelType(ChannelType.ATM);
			currentTransaction.setPostedStatus(1);
			currentTransaction.setTransactionType(TransactionType.Transfer);
			currentTransaction.setPacs(loc.getAccount().getAccountProperty().getPacs());
			currentTransaction.setRemarks("pacs bank account reversal transaction for subsidy");
			currentTransaction.setTransactionCode(TransactionCode.PACS_BANK_ACC);
			reversalTrans.add(currentTransaction);
		}*/
		
		logger.info("reversalTrans size : "+reversalTrans.size());
		KLSDataAccessFactory.getCurrentTransactionDAO().saveCurrentTransactionsList(reversalTrans);
		return reversalTrans;
	}
	private CurrentTransaction doCopyOfObject(CurrentTransaction c) {
		CurrentTransaction currentTransaction = new CurrentTransaction();
		//currentTransaction.setId(c.getId());
		currentTransaction.setAccount(c.getAccount());
		currentTransaction.setAccountNumber(c.getAccountNumber());
		currentTransaction.setBusinessDate(c.getBusinessDate());
		currentTransaction.setChannelType(c.getChannelType());
		currentTransaction.setLineOfCredit(c.getLineOfCredit());
		currentTransaction.setOpeningBalance(c.getOpeningBalance());
		currentTransaction.setPacs(c.getPacs());
		currentTransaction.setPostedStatus(c.getPostedStatus());
		currentTransaction.setTerminalId(c.getTerminalId());
		currentTransaction.setTransactionAmount(c.getTransactionAmount());
		currentTransaction.setTransactionCode(c.getTransactionCode());
		currentTransaction.setTransactionType(c.getTransactionType());
		currentTransaction.setVoucherNumber(c.getVoucherNumber());
		currentTransaction.setRemarks(c.getRemarks());
		return currentTransaction;
	}

	public Integer getVoucherNumber(String pacsId, String transType) {
		EntityManager em = EntityManagerUtil.getEntityManager();
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
		} catch (Exception e) {
			logger.error("Voucher Number Does not created.");
			e.printStackTrace();
			throw new DataAccessException("Voucher Number Does not created.");
		}
		return sbNextNumbers.getLastUsedNumber();
	}

	public static Date getBusinessDate() {
		List<BankParameter> bankParameterList = KLSDataAccessFactory.getBankParameterDAO().getAllBankParameters();
		BankParameter bankParameter = new BankParameter();
		if (bankParameterList != null && !bankParameterList.isEmpty()) {
			bankParameter = bankParameterList.get(0);
		}
		return bankParameter.getBusinessDate();
	}
	
	public List<SlabwisesubsidyInterestRate> getSlabwisesubsidyList(Integer interestSubsidyId) {
			
			logger.info("Start: Fetching the slab wise subsidy ROI data from the database in getSlabwisesubsidyList() method.");
			List<SlabwisesubsidyInterestRate> slabwisesubsidyInterestRate = null;
			try {
				EntityManager em = EntityManagerUtil.getEntityManager();
				Query query = em.createQuery("select s from SlabwisesubsidyInterestRate s  where s.interestSubsidy.id = "+interestSubsidyId+" order by s.fromAmount");
				slabwisesubsidyInterestRate = query.getResultList();
			}catch(NoResultException e){
				logger.error("No records found with this interest subsidy id"+interestSubsidyId);
				slabwisesubsidyInterestRate = null;
				
			}catch (Exception excp) {
				logger.error("Could not fetch the slab wise subsidy ROI data from the "
						+ "database using interestSubsidyId Exception thrown.");
				throw new DataAccessException("Could not fetch the slab wise subsidy ROI data from the database.",
						excp.getCause());
			}
			logger.info("End: Successfully fetched the slab wise subsidy ROI data from the database in getSlabwisesubsidyList() method.");
			return slabwisesubsidyInterestRate;
		}
	
	public BigDecimal calculateSubsidyInterest(AccountingMoney currentBalance, List<SlabwisesubsidyInterestRate> slabList) {
 		AccountingMoney interestAppliedAmount = AccountingMoney.ZERO;
		AccountingMoney totalInterest = AccountingMoney.ZERO;
		for(SlabwisesubsidyInterestRate slab : slabList) {
			AccountingMoney amount = AccountingMoney.ZERO;			
			if(currentBalance.getMoney().getAmount().compareTo(slab.getToAmount().getAmount()) >= 0) {
				amount = MoneyUtil.getAccountingMoney(slab.getToAmount()).subtract(interestAppliedAmount);
				totalInterest = totalInterest.add(MoneyUtil.getAccountingMoney(InterestCalculationUtil.perDaySimpleInterest(amount.getMoney().getAmount(),	slab == null ? BigDecimal.ZERO : slab.getSubsidyRoiPerAnnum(),1)));
				interestAppliedAmount = MoneyUtil.getAccountingMoney(slab.getToAmount());
			} else {
				if(currentBalance.getMoney().getAmount().compareTo(slab.getFromAmount().getAmount()) >= 0) {
					amount = MoneyUtil.getAccountingMoney(currentBalance.getMoney().getAmount().subtract(interestAppliedAmount.getMoney().getAmount()));
					totalInterest = totalInterest.add(MoneyUtil.getAccountingMoney(InterestCalculationUtil.perDaySimpleInterest(amount.getMoney().getAmount(),	slab == null ? BigDecimal.ZERO : slab.getSubsidyRoiPerAnnum(),1)));
					break;
				}
			}
		}
		return totalInterest.getMoney().getAmount();
	}
	
	private List<CurrentTransaction> getCurrentTransaction(List<LineOfCredit> locs, Date theBusinessDate) {
		// TODO Auto-generated method stub


		List<CurrentTransaction> currentTrxnList = new ArrayList<CurrentTransaction>();

		Integer voucherNumber = null;
		String voucher = null;

		// Loop for what you want and commit
		for (Iterator iterator = locs.iterator(); iterator.hasNext();) {
			LineOfCredit loc = (LineOfCredit) iterator.next();
			logger.info("LOC ID: ----------" + loc.getId());
			Pacs pacs = null;
			if (loc.getLoanType().equals("C") || loc.getLoanType().equals("L")
					|| loc.getLoanType().equals("B"))
				pacs = loc.getAccount().getAccountProperty().getPacs();
			if (pacs == null) {
				logger.info("Un Known loan type for LOC - " + loc.getId());
				continue;
			}
			String transType = null;
			if (!loc.getLoanType().equals("B"))
				transType = TransactionType.Transfer.getValue();
			else
				transType = TransactionType.Borrowings.getValue();

			voucherNumber = getVoucherNumber(pacs.getId().toString(), transType);
			voucher = TransactionType.Transfer.getValue() + "-" + voucherNumber;

			if (loc.getInterestAccrued().compareTo(BigDecimal.ZERO) != 0) {
				/*
				 * subsidy interest post
				 */
				postSubsidyInterest(loc);
				
				//loan interest posting
				BigDecimal interestAccrued = loc.getInterestAccrued();
				loc.setInterestReceivable((loc.getInterestReceivable().add(interestAccrued)).setScale(0, RoundingMode.HALF_UP));
				loc.setInterestAccrued(BigDecimal.ZERO);
				KLSDataAccessFactory.getLineOfCreditDAO().updateLineOfCredit(loc);
				CurrentTransaction intReceivableDrTrxn = new CurrentTransaction();
				intReceivableDrTrxn.setLineOfCredit(loc);
				intReceivableDrTrxn.setBusinessDate(theBusinessDate);
				intReceivableDrTrxn.setAccount(loc.getAccount());
				intReceivableDrTrxn.setAccountNumber(loc.getAccount().getAccountNumber());
				intReceivableDrTrxn.setChannelType(ChannelType.SYSTEM);
				AccountingMoney transAmt = MoneyUtil.getAccountingMoney(interestAccrued.abs().setScale(0, RoundingMode.HALF_UP));
				intReceivableDrTrxn.setOpeningBalance(loc.getCurrentBalance());
				intReceivableDrTrxn.setPostedStatus(1);
				intReceivableDrTrxn.setTransactionAmount(transAmt);
				if (!loc.getLoanType().equals("B")) {
					intReceivableDrTrxn.setTransactionType(TransactionType.Transfer);
					intReceivableDrTrxn.setCrDr(-1);
					intReceivableDrTrxn.setRemarks("Interest Accrued posted Receivable");
				} else {
					intReceivableDrTrxn.setTransactionType(TransactionType.Borrowings);
					intReceivableDrTrxn.setCrDr(1);
					intReceivableDrTrxn.setRemarks("Borrowings Interest Accrued posted Receivable");
				}
				intReceivableDrTrxn.setPacs(pacs);

				intReceivableDrTrxn.setTransactionCode(TransactionCode.INTEREST_RECEIVABLE);

				intReceivableDrTrxn.setVoucherNumber(voucher);

				// For Credit Transaction
				CurrentTransaction intReceivableCrTrxn = new CurrentTransaction();
				intReceivableCrTrxn.setLineOfCredit(loc);
				intReceivableCrTrxn.setBusinessDate(theBusinessDate);
				intReceivableCrTrxn.setAccount(loc.getAccount());
				intReceivableCrTrxn.setAccountNumber(loc.getAccount().getAccountNumber());
				intReceivableCrTrxn.setChannelType(ChannelType.SYSTEM);

				intReceivableCrTrxn.setOpeningBalance(loc.getCurrentBalance());
				intReceivableCrTrxn.setPostedStatus(1);
				intReceivableCrTrxn.setTransactionAmount(transAmt);
				if (!loc.getLoanType().equals("B")) {
					intReceivableCrTrxn.setTransactionType(TransactionType.Transfer);
					intReceivableCrTrxn.setCrDr(1);
					intReceivableCrTrxn.setRemarks("Interest Accrued Posted Received");
				} else {
					intReceivableCrTrxn.setTransactionType(TransactionType.Borrowings);
					intReceivableCrTrxn.setCrDr(-1);
					intReceivableCrTrxn.setRemarks("Borrowings Interest Accrued Posted Received");
				}
				intReceivableCrTrxn.setPacs(pacs);

				intReceivableCrTrxn.setTransactionCode(TransactionCode.INTEREST_RECEIVED);
				intReceivableCrTrxn.setVoucherNumber(voucher);
				currentTrxnList.add(intReceivableDrTrxn);
				currentTrxnList.add(intReceivableCrTrxn);
			} // end for interest

			if (loc.getOverdueInterest().compareTo(BigDecimal.ZERO) != 0) {
				BigDecimal overDueInterest = loc.getOverdueInterest();
				loc.setPenalInterestReceivable((loc.getPenalInterestReceivable().add(overDueInterest)).setScale(0, RoundingMode.HALF_UP));
				loc.setOverdueInterest(BigDecimal.ZERO);
				KLSDataAccessFactory.getLineOfCreditDAO().updateLineOfCredit(loc);
				CurrentTransaction penalIntDrTrxn = new CurrentTransaction();
				penalIntDrTrxn.setLineOfCredit(loc);
				penalIntDrTrxn.setBusinessDate(theBusinessDate);
				penalIntDrTrxn.setAccount(loc.getAccount());
				penalIntDrTrxn.setAccountNumber(loc.getAccount().getAccountNumber());
				penalIntDrTrxn.setChannelType(ChannelType.SYSTEM);

				penalIntDrTrxn.setOpeningBalance(loc.getCurrentBalance());
				penalIntDrTrxn.setPostedStatus(1);
				AccountingMoney transAmt = MoneyUtil.getAccountingMoney(overDueInterest.abs().setScale(0, RoundingMode.HALF_UP));
				penalIntDrTrxn.setTransactionAmount(transAmt);

				if (!loc.getLoanType().equals("B")) {
					penalIntDrTrxn.setTransactionType(TransactionType.Transfer);
					penalIntDrTrxn.setCrDr(-1);
					penalIntDrTrxn.setRemarks("Penal Interest Posting Receivable");
				} else {
					penalIntDrTrxn.setTransactionType(TransactionType.Borrowings);
					penalIntDrTrxn.setCrDr(1);
					penalIntDrTrxn.setRemarks("Borrowings Penal Interest Posting Receivable");
				}
				penalIntDrTrxn.setPacs(pacs);
				penalIntDrTrxn.setVoucherNumber(voucher);
				penalIntDrTrxn.setTransactionCode(TransactionCode.PENAL_INTEREST_RECEIVABLE);

				// For Penal Credit Transaction
				CurrentTransaction penalIntCrTrxn = new CurrentTransaction();
				penalIntCrTrxn.setLineOfCredit(loc);
				penalIntCrTrxn.setBusinessDate(theBusinessDate);
				penalIntCrTrxn.setAccount(loc.getAccount());
				penalIntCrTrxn.setAccountNumber(loc.getAccount().getAccountNumber());
				penalIntCrTrxn.setChannelType(ChannelType.SYSTEM);

				penalIntCrTrxn.setOpeningBalance(loc.getCurrentBalance());
				penalIntCrTrxn.setPostedStatus(1);
				penalIntCrTrxn.setTransactionAmount(transAmt);
				if (!loc.getLoanType().equals("B")) {
					penalIntCrTrxn.setTransactionType(TransactionType.Transfer);
					penalIntCrTrxn.setCrDr(1);
					penalIntCrTrxn.setRemarks("Penal Interest Posting Received");

				} else {
					penalIntCrTrxn.setTransactionType(TransactionType.Borrowings);
					penalIntCrTrxn.setCrDr(-1);
					penalIntCrTrxn.setRemarks("Borroings Penal Interest Posting Received");
				}
				penalIntCrTrxn.setPacs(pacs);

				penalIntCrTrxn.setTransactionCode(TransactionCode.PENAL_INTEREST_RECEIVED);
				penalIntCrTrxn.setVoucherNumber(voucher);

				currentTrxnList.add(penalIntDrTrxn);
				currentTrxnList.add(penalIntCrTrxn);
			} // end for penal

		}
		return currentTrxnList;
	
	}

	private void postSubsidyInterest(LineOfCredit loc) {
		// TODO Auto-generated method stub

		logger.info("START: posting the subsidy interest in postSubsidyInterest() mehtod");
		ISubsidyInterestAmountsDAO dao = KLSDataAccessFactory.getSubsidyInterestAmountsDAO();
		List<SubsidyInterestAmounts> samountsList = dao.getSubsidyListByLocId(loc.getId());
		for(SubsidyInterestAmounts subsidyAmount : samountsList){
			if(subsidyAmount.getSubsidyReceivable() == null){
				subsidyAmount.setSubsidyReceivable(subsidyAmount.getSubsidyAccrued().setScale(0, RoundingMode.HALF_UP));
			}else{
				subsidyAmount.setSubsidyReceivable(subsidyAmount.getSubsidyReceivable().add(subsidyAmount.getSubsidyAccrued()).setScale(0, RoundingMode.HALF_UP));
			}
			subsidyAmount.setSubsidyAccrued(BigDecimal.ZERO);
			dao.updateSubsidyInterestAmounts(subsidyAmount);
		}
		logger.info("END: posting the subsidy interest in postSubsidyInterest() mehtod");
	
		
	}
}
