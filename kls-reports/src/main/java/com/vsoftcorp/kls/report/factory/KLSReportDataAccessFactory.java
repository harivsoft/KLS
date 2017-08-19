package com.vsoftcorp.kls.report.factory;

import com.vosftcorp.kls.report.dao.dcb.IDCBReportDAO;
import com.vosftcorp.kls.report.dao.dcb.ISTMTLTDcbReportDAO;
import com.vosftcorp.kls.report.dao.dcb.impl.DCBReportDAO;
import com.vosftcorp.kls.report.dao.dcb.impl.STMTLTDcbReportDAO;
import com.vsoftcorp.kls.GepRep.dao.IGenerateReportDAO;
import com.vsoftcorp.kls.GepRep.dao.impl.GenerateReportDAO;
import com.vsoftcorp.kls.report.dao.IAccountInfoReportDAO;
import com.vsoftcorp.kls.report.dao.IAccountSavingReportDAO;
import com.vsoftcorp.kls.report.dao.IAccountStatementReportDAO;
import com.vsoftcorp.kls.report.dao.IBalanceListReportDAO;
import com.vsoftcorp.kls.report.dao.IBatchWiseVoucherPrintReportDAO;
import com.vsoftcorp.kls.report.dao.IBorrowingAccountLedgerReportDAO;
import com.vsoftcorp.kls.report.dao.ICollectionReportDAO;
import com.vsoftcorp.kls.report.dao.ICropWiseDrawlReportDAO;
import com.vsoftcorp.kls.report.dao.IDayBookReportDAO;
import com.vsoftcorp.kls.report.dao.IDeceasedReportDAO;
import com.vsoftcorp.kls.report.dao.IDrawalReportDAO;
import com.vsoftcorp.kls.report.dao.IInconsistencyReportDAO;
import com.vsoftcorp.kls.report.dao.IInterestChargedReportDAO;
import com.vsoftcorp.kls.report.dao.IInterestSubsidyReportDAO;
import com.vsoftcorp.kls.report.dao.IJindagiReportDAO;
import com.vsoftcorp.kls.report.dao.ILandRegisterReportDAO;
import com.vsoftcorp.kls.report.dao.ILoanApplicationStatusReportDAO;
import com.vsoftcorp.kls.report.dao.ILoanRegisterReportDAO;
import com.vsoftcorp.kls.report.dao.IMTLTLoanLedgerReportDAO;
import com.vsoftcorp.kls.report.dao.IMasterReportDAO;
import com.vsoftcorp.kls.report.dao.IMemberProfileReportDAO;
import com.vsoftcorp.kls.report.dao.IMemberandCardLinkingReportDAO;
import com.vsoftcorp.kls.report.dao.INPAParameterDAO;
import com.vsoftcorp.kls.report.dao.INPAReportDAO;
import com.vsoftcorp.kls.report.dao.IOverdueReportDAO;
import com.vsoftcorp.kls.report.dao.IProductWiseCollectionReportDAO;
import com.vsoftcorp.kls.report.dao.IPurposeDisbursmentReportDAO;
import com.vsoftcorp.kls.report.dao.ISanctionReportDAO;
import com.vsoftcorp.kls.report.dao.IScheduleVsGLBalanceReportDAO;
import com.vsoftcorp.kls.report.dao.IShareAccountBalancingReportDAO;
import com.vsoftcorp.kls.report.dao.IShareAccountLedgerReportDAO;
import com.vsoftcorp.kls.report.dao.impl.AccountInfoReportDAO;
import com.vsoftcorp.kls.report.dao.impl.AccountSavingReportDAO;
import com.vsoftcorp.kls.report.dao.impl.AccountStatementReportDAO;
import com.vsoftcorp.kls.report.dao.impl.BalanceListReportDAO;
import com.vsoftcorp.kls.report.dao.impl.BatchWiseVoucherPrintReportDAO;
import com.vsoftcorp.kls.report.dao.impl.BorrowingAccountLedgerReportDAO;
import com.vsoftcorp.kls.report.dao.impl.CollectionReportDAO;
import com.vsoftcorp.kls.report.dao.impl.CropWiseDrawlReportDAO;
import com.vsoftcorp.kls.report.dao.impl.DayBookReportDAO;
import com.vsoftcorp.kls.report.dao.impl.DeceasedReportDAO;
import com.vsoftcorp.kls.report.dao.impl.DrawalReportDAO;
import com.vsoftcorp.kls.report.dao.impl.InconsistencyReportDAO;
import com.vsoftcorp.kls.report.dao.impl.InterestChargedReportDAO;
import com.vsoftcorp.kls.report.dao.impl.InterestSubsidyReportDAO;
import com.vsoftcorp.kls.report.dao.impl.JindagiReportDAO;
import com.vsoftcorp.kls.report.dao.impl.LandRegisterReportDAO;
import com.vsoftcorp.kls.report.dao.impl.LoanApplicationStatusReportDAO;
import com.vsoftcorp.kls.report.dao.impl.LoanRegisterReportDAO;
import com.vsoftcorp.kls.report.dao.impl.MTLTLoanLedgerReportDAO;
import com.vsoftcorp.kls.report.dao.impl.MasterReportDAO;
import com.vsoftcorp.kls.report.dao.impl.MemberProfileReportDAO;
import com.vsoftcorp.kls.report.dao.impl.MemberandCardLinkingReportDAO;
import com.vsoftcorp.kls.report.dao.impl.NPAParameterDAO;
import com.vsoftcorp.kls.report.dao.impl.NPAReportDAO;
import com.vsoftcorp.kls.report.dao.impl.OverdueReportDAO;
import com.vsoftcorp.kls.report.dao.impl.ProductWiseCollectionReportDAO;
import com.vsoftcorp.kls.report.dao.impl.PurposeWiseDisbursmentReportDAO;
import com.vsoftcorp.kls.report.dao.impl.SanctionReportDAO;
import com.vsoftcorp.kls.report.dao.impl.ScheduleVsGLBalanceReportDAO;
import com.vsoftcorp.kls.report.dao.impl.ShareAccountBalancingReportDAO;
import com.vsoftcorp.kls.report.dao.impl.ShareAccountLedgerReportDAO;

/**
 * This is the factory class for all Report DAO's
 * 
 * @author a1605
 * 
 */
public class KLSReportDataAccessFactory {

	private static IDrawalReportDAO drawalReportDAO;

	private static ICollectionReportDAO collectionReportDAO;

	private static IInconsistencyReportDAO inconsistencyReportDAO;

	private static ISanctionReportDAO sanctionReportDAO;

	private static IAccountStatementReportDAO accountStatementReportDAO;

	private static IDCBReportDAO dcbReportDAO;

	private static IShareAccountLedgerReportDAO shareAccountLedgerReportDAO;

	private static ICropWiseDrawlReportDAO cropWiseDrawlReportDAO;

	private static IMTLTLoanLedgerReportDAO mtltLoanLedgerReportDAO;

	private static IOverdueReportDAO overdueReportDAO;

	private static IPurposeDisbursmentReportDAO purposeDisbursmentReportDAO;

	private static ILandRegisterReportDAO landRegisterReportDAO;

	private static IDeceasedReportDAO deceasedReportDAO;

	private static ILoanApplicationStatusReportDAO loanApplicationStatusReportDAO;

	private static IBatchWiseVoucherPrintReportDAO batchWiseVoucherPrintReportDAO;

	private static IShareAccountBalancingReportDAO shareAccountBalancingReportDAO;

	private static IBorrowingAccountLedgerReportDAO borrowingAccountLedgerReportDAO;

	private static ISTMTLTDcbReportDAO stmtltDcbReportDAO;

	private static IInterestChargedReportDAO interestChargedReportDAO;

	private static IProductWiseCollectionReportDAO productWiseCollectionReportDAO;

	private static IMasterReportDAO masterReportDAO;

	private static IMemberProfileReportDAO memberProfileReportDAO;

	private static IAccountInfoReportDAO accountInfoReportDAO;

	private static IBalanceListReportDAO balanceListReportDAO;
	
	private static IInterestSubsidyReportDAO interestSubsidyReportDAO;
	
	private static IAccountSavingReportDAO  iAccountSavingReportDAO;
	
	private static IScheduleVsGLBalanceReportDAO iScheduleVsGLBalanceDAO;
	
	private static IDayBookReportDAO dayBookReportDAO;
	
	private static ILoanRegisterReportDAO loanRegisterReportDAO;
	
	private static IGenerateReportDAO iGenerateReportDAO;
	
	private static IJindagiReportDAO jindagiReportDAO;
	
	private static IMemberandCardLinkingReportDAO memberandCardLinkingReportDAO;
	
	private static INPAReportDAO npaReportDAO;
	
	private static INPAParameterDAO npaParameterDAO;
	
	public static INPAParameterDAO getNPAParameterDAO() {
		return npaParameterDAO == null ? npaParameterDAO = new NPAParameterDAO(): npaParameterDAO;
	}
	
	public static INPAReportDAO getNPAReportDAO() {
		return npaReportDAO == null ? npaReportDAO = new NPAReportDAO(): npaReportDAO;
	}
	
	public static IGenerateReportDAO getGenerateReportDAO() {
		return iGenerateReportDAO ==null ? iGenerateReportDAO = new GenerateReportDAO():iGenerateReportDAO;
	}
	
	public static IDayBookReportDAO getDayBookReportDAO()
	{
		return dayBookReportDAO ==null ? dayBookReportDAO = new DayBookReportDAO():dayBookReportDAO;
	}
	
	public static IAccountSavingReportDAO getAccountSavingReportDAO()
	{
		return iAccountSavingReportDAO ==null ? iAccountSavingReportDAO = new AccountSavingReportDAO():iAccountSavingReportDAO;
	}

	public static ILoanApplicationStatusReportDAO getLoanAccountStatusReportDAO() {
		return loanApplicationStatusReportDAO == null ? loanApplicationStatusReportDAO = new LoanApplicationStatusReportDAO()
				: loanApplicationStatusReportDAO;
	}

	public static IDeceasedReportDAO getDeceasedReportDAO() {
		return deceasedReportDAO == null ? deceasedReportDAO = new DeceasedReportDAO() : deceasedReportDAO;
	}

	public static ILandRegisterReportDAO getLandRegisterReportDAO() {
		return landRegisterReportDAO == null ? landRegisterReportDAO = new LandRegisterReportDAO() : landRegisterReportDAO;
	}

	public static IDrawalReportDAO getDrawalReportDAO() {
		return drawalReportDAO == null ? drawalReportDAO = new DrawalReportDAO() : drawalReportDAO;
	}

	public static ICollectionReportDAO getCollectionReportDAO() {
		return collectionReportDAO == null ? collectionReportDAO = new CollectionReportDAO() : collectionReportDAO;
	}

	public static IInconsistencyReportDAO getInconsistencyReportDAO() {
		return inconsistencyReportDAO == null ? inconsistencyReportDAO = new InconsistencyReportDAO() : inconsistencyReportDAO;
	}

	public static ISanctionReportDAO getSanctionReportDAO() {
		return sanctionReportDAO == null ? sanctionReportDAO = new SanctionReportDAO() : sanctionReportDAO;
	}

	public static IAccountStatementReportDAO getAccountStatementReportDAO() {
		return accountStatementReportDAO == null ? accountStatementReportDAO = new AccountStatementReportDAO() : accountStatementReportDAO;
	}

	public static IDCBReportDAO getDCBReportDAO() {
		return dcbReportDAO == null ? dcbReportDAO = new DCBReportDAO() : dcbReportDAO;
	}

	public static IShareAccountLedgerReportDAO getShareAccountLedgerReportDAO() {
		return shareAccountLedgerReportDAO == null ? shareAccountLedgerReportDAO = new ShareAccountLedgerReportDAO() : shareAccountLedgerReportDAO;
	}

	public static IMTLTLoanLedgerReportDAO getMTLTLoanLedgerReportDAO() {
		return mtltLoanLedgerReportDAO == null ? mtltLoanLedgerReportDAO = new MTLTLoanLedgerReportDAO() : mtltLoanLedgerReportDAO;
	}

	public static IOverdueReportDAO getOverdueReportDAO() {
		return overdueReportDAO == null ? overdueReportDAO = new OverdueReportDAO() : overdueReportDAO;
	}

	public static ICropWiseDrawlReportDAO getCropWiseDrawlReportDAO() {
		return cropWiseDrawlReportDAO == null ? cropWiseDrawlReportDAO = new CropWiseDrawlReportDAO() : cropWiseDrawlReportDAO;
	}

	public static IBatchWiseVoucherPrintReportDAO getBatchWiseVoucherPrintReportDAO() {
		return batchWiseVoucherPrintReportDAO == null ? batchWiseVoucherPrintReportDAO = new BatchWiseVoucherPrintReportDAO()
				: batchWiseVoucherPrintReportDAO;
	}

	public static IShareAccountBalancingReportDAO getShareAccountBalancingReportDAO() {
		return shareAccountBalancingReportDAO == null ? shareAccountBalancingReportDAO = new ShareAccountBalancingReportDAO()
				: shareAccountBalancingReportDAO;
	}

	public static IAccountInfoReportDAO getAccountInfoReportDAO() {
		return accountInfoReportDAO == null ? accountInfoReportDAO = new AccountInfoReportDAO() : accountInfoReportDAO;
	}

	public static IBorrowingAccountLedgerReportDAO getBorrowingAccountLedgerReportDAO() {
		return borrowingAccountLedgerReportDAO == null ? borrowingAccountLedgerReportDAO = new BorrowingAccountLedgerReportDAO()
				: borrowingAccountLedgerReportDAO;
	}

	public static IPurposeDisbursmentReportDAO getPurposeDisbursmentReportDAO() {
		return purposeDisbursmentReportDAO == null ? purposeDisbursmentReportDAO = new PurposeWiseDisbursmentReportDAO()
				: purposeDisbursmentReportDAO;
	}

	public static IMemberProfileReportDAO getMemberProfileReportDAO() {
		return memberProfileReportDAO == null ? memberProfileReportDAO = new MemberProfileReportDAO() : memberProfileReportDAO;
	}

	public static ISTMTLTDcbReportDAO getstmtltDcbReportDAO() {
		return stmtltDcbReportDAO == null ? stmtltDcbReportDAO = new STMTLTDcbReportDAO() : stmtltDcbReportDAO;
	}

	public static IInterestChargedReportDAO getInterestChargedReportDAO() {
		return interestChargedReportDAO == null ? interestChargedReportDAO = new InterestChargedReportDAO() : interestChargedReportDAO;
	}

	public static IMasterReportDAO getMasterReportDAO() {
		return masterReportDAO == null ? masterReportDAO = new MasterReportDAO() : masterReportDAO;
	}

	public static IProductWiseCollectionReportDAO getProductWiseCollectionReportDAO() {
		return productWiseCollectionReportDAO == null ? productWiseCollectionReportDAO = new ProductWiseCollectionReportDAO()
				: productWiseCollectionReportDAO;
	}

	public static IBalanceListReportDAO getBalanceListReportDAO() {
		return balanceListReportDAO == null ? balanceListReportDAO = new BalanceListReportDAO() : balanceListReportDAO;
	}
	
	public static IInterestSubsidyReportDAO getInterestSubsidyReportDAO() {
		return interestSubsidyReportDAO == null ? interestSubsidyReportDAO = new InterestSubsidyReportDAO() : interestSubsidyReportDAO;
	}
	public static IScheduleVsGLBalanceReportDAO getScheduleVsGLBalanceReportDAO(){
		return iScheduleVsGLBalanceDAO == null? iScheduleVsGLBalanceDAO = new ScheduleVsGLBalanceReportDAO() : iScheduleVsGLBalanceDAO;
	}
	
	public static ILoanRegisterReportDAO getLoanRegisterReportDAO() {
		return loanRegisterReportDAO == null ? loanRegisterReportDAO = new LoanRegisterReportDAO() : loanRegisterReportDAO; 
	}
	
	public static IJindagiReportDAO getJindagiReportDAO() {
		return jindagiReportDAO == null ? jindagiReportDAO = new JindagiReportDAO() : jindagiReportDAO;
	}

	public static IMemberandCardLinkingReportDAO getMemberandCardLinkingReportDAO() {
		// TODO Auto-generated method stub
		return memberandCardLinkingReportDAO == null ? memberandCardLinkingReportDAO = new MemberandCardLinkingReportDAO() : memberandCardLinkingReportDAO;
	}

	
}
