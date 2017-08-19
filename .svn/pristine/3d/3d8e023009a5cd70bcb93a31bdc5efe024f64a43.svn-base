package com.vsoftcorp.kls.report.factory;

import com.vosftcorp.kls.report.service.IAccountAssignReportService;
import com.vosftcorp.kls.report.service.IAccountInfoReportService;
import com.vosftcorp.kls.report.service.IAccountStatementReportService;
import com.vosftcorp.kls.report.service.IBalanceListReportService;
import com.vosftcorp.kls.report.service.IBatchWiseVoucherPrintReportService;
import com.vosftcorp.kls.report.service.IBorrowingAccountLedgerReportService;
import com.vosftcorp.kls.report.service.ICollectionReportService;
import com.vosftcorp.kls.report.service.ICropWiseDrawlReportService;
import com.vosftcorp.kls.report.service.IDayBookReportService;
import com.vosftcorp.kls.report.service.IDeceasedReportService;
import com.vosftcorp.kls.report.service.IDrawalReportService;
import com.vosftcorp.kls.report.service.IInconsistencyReportService;
import com.vosftcorp.kls.report.service.IInterestChargedReportService;
import com.vosftcorp.kls.report.service.IInterestSubsidyReportService;
import com.vosftcorp.kls.report.service.IJindagiReportService;
import com.vosftcorp.kls.report.service.ILandRegisterReportService;
import com.vosftcorp.kls.report.service.ILoanApplicationStatusReportService;
import com.vosftcorp.kls.report.service.ILoanRegisterReportService;
import com.vosftcorp.kls.report.service.IMTLTLoanLedgerReportService;
import com.vosftcorp.kls.report.service.IMasterReportService;
import com.vosftcorp.kls.report.service.IMemberProfileReportService;
import com.vosftcorp.kls.report.service.IMemberandCardLinkingService;
import com.vosftcorp.kls.report.service.INPAReportService;
import com.vosftcorp.kls.report.service.INPAReportService2;
import com.vosftcorp.kls.report.service.IOverdueReportService;
import com.vosftcorp.kls.report.service.IProductWiseCollectionReportService;
import com.vosftcorp.kls.report.service.IPurposeWiseDisbursmentService;
import com.vosftcorp.kls.report.service.ISanctionReportService;
import com.vosftcorp.kls.report.service.IScheduleVsGLBalanceReportService;
import com.vosftcorp.kls.report.service.IShareAccountBalancingReportService;
import com.vosftcorp.kls.report.service.IShareAccountLedgerReportService;
import com.vosftcorp.kls.report.service.dcb.IDCBReportService;
import com.vosftcorp.kls.report.service.dcb.ISTMTLTDcbReportService;
import com.vosftcorp.kls.report.service.dcb.impl.DCBReportService;
import com.vosftcorp.kls.report.service.dcb.impl.STMTLTDcbReportService;
import com.vosftcorp.kls.report.service.impl.AccountInfoReportService;
import com.vosftcorp.kls.report.service.impl.AccountSavingReportService;
import com.vosftcorp.kls.report.service.impl.AccountStatementReportSerive;
import com.vosftcorp.kls.report.service.impl.BalanceListReportService;
import com.vosftcorp.kls.report.service.impl.BatchWiseVoucherPrintReportService;
import com.vosftcorp.kls.report.service.impl.BorrowingAccountLedgerReportService;
import com.vosftcorp.kls.report.service.impl.CollectionReportService;
import com.vosftcorp.kls.report.service.impl.CropWiseDrawlReportService;
import com.vosftcorp.kls.report.service.impl.DayBookReportService;
import com.vosftcorp.kls.report.service.impl.DeceasedReportService;
import com.vosftcorp.kls.report.service.impl.DrawalReportSerive;
import com.vosftcorp.kls.report.service.impl.InconsistencyReportService;
import com.vosftcorp.kls.report.service.impl.InterestChargedReportService;
import com.vosftcorp.kls.report.service.impl.InterestSubsidyReportService;
import com.vosftcorp.kls.report.service.impl.JindagiReportService;
import com.vosftcorp.kls.report.service.impl.LandRegisterService;
import com.vosftcorp.kls.report.service.impl.LoanApplicationStatusReportService;
import com.vosftcorp.kls.report.service.impl.LoanRegisterReportService;
import com.vosftcorp.kls.report.service.impl.MTLTLoanLedgerReportService;
import com.vosftcorp.kls.report.service.impl.MasterReportService;
import com.vosftcorp.kls.report.service.impl.MemberProfileReportService;
import com.vosftcorp.kls.report.service.impl.MemberandCardLinkingService;
import com.vosftcorp.kls.report.service.impl.NPAReportService;
import com.vosftcorp.kls.report.service.impl.NPAReportService2;
import com.vosftcorp.kls.report.service.impl.OverdueReportService;
import com.vosftcorp.kls.report.service.impl.ProductWiseCollectionReportService;
import com.vosftcorp.kls.report.service.impl.PurposeWiseDisbursmentService;
import com.vosftcorp.kls.report.service.impl.SanctionReportSerive;
import com.vosftcorp.kls.report.service.impl.ScheduleVsGLBalanceReportService;
import com.vosftcorp.kls.report.service.impl.ShareAccountBalancingReportService;
import com.vosftcorp.kls.report.service.impl.ShareAccountLedgerReportService;
import com.vsoftcorp.kls.GepRep.service.IGenerateReportService;
import com.vsoftcorp.kls.GepRep.service.impl.GenerateReportService;

/**
 * This is the factory class for all Report Service's
 * 
 * @author a1605
 * 
 */
public class KLSReportSeriveFactory {

	private static IDrawalReportService drawalReportService;

	private static ICollectionReportService collectionReportService;

	private static IInconsistencyReportService inconsistencyReportService;

	private static ISanctionReportService sanctionReportService;

	private static IAccountStatementReportService accountStatementReportService;

	private static IDCBReportService dcbReportService;

	private static IShareAccountLedgerReportService shareAccountLedgerReportService;

	private static ICropWiseDrawlReportService cropWiseDrawlReportService;

	private static IMTLTLoanLedgerReportService mtltLoanLedgerReportService;

	private static IOverdueReportService overdueReportService;

	private static IShareAccountBalancingReportService shareAccountBalancingReportService;

	private static IPurposeWiseDisbursmentService purposeWiseDisbursmentService;

	private static IBatchWiseVoucherPrintReportService batchWiseVoucherPrintReportService;

	private static ILandRegisterReportService landRegisterReportService;

	private static IBorrowingAccountLedgerReportService borrowingAccountLedgerReportService;

	private static IDeceasedReportService deceasedReportService;

	private static IMemberProfileReportService memberProfileReportService;

	private static ISTMTLTDcbReportService stmtltdcbReportService;

	private static IInterestChargedReportService interestChargedReportService;

	private static ILoanApplicationStatusReportService loanApplicationStatusReportService;

	private static IMasterReportService masterReportService;

	private static IAccountInfoReportService accountInfoReportService;

	private static IProductWiseCollectionReportService productWiseCollectionReportService;

	private static IBalanceListReportService balanceListReportService;
	
	private static IInterestSubsidyReportService interestSubsidyReportService;
	
	private static IAccountAssignReportService iAccountAssignReportService;
	
	private static IScheduleVsGLBalanceReportService scheduleVsGLBalanceReportService;
	
	private static IDayBookReportService dayBookReportService;
	
	private static ILoanRegisterReportService loanRegisterReportService;
	
	private static IGenerateReportService iGenerateReportService ;
	
	private static IJindagiReportService jindagiReportService;
	
	private static IMemberandCardLinkingService memberandcardlinkingService;
	
	private static INPAReportService npaReportService;
	
	private static INPAReportService2 npaReportService2;

	
	public static INPAReportService getNPAReportService() {
		return npaReportService == null ? npaReportService = new NPAReportService() : npaReportService;
	}
	
	
	public static IGenerateReportService getGenerateReportService() {
		return iGenerateReportService == null ? iGenerateReportService = new GenerateReportService() : iGenerateReportService;
	}
	
	
	public static IDayBookReportService getDayBookReportService()
	{
		return dayBookReportService == null ?  dayBookReportService = new DayBookReportService()
		: dayBookReportService;
	}
	
	public static IAccountAssignReportService getAccountAssignReportService()
	{
		return iAccountAssignReportService == null ?  iAccountAssignReportService = new AccountSavingReportService()
		: iAccountAssignReportService;
	}
	
	
public static ILoanApplicationStatusReportService getLoanApplicationStatusReportService() {
		return loanApplicationStatusReportService == null ? loanApplicationStatusReportService = new LoanApplicationStatusReportService()
				: loanApplicationStatusReportService;
	}

	public static IDeceasedReportService getDeceasedReportService() {
		return deceasedReportService == null ? deceasedReportService = new DeceasedReportService() : deceasedReportService;
	}

	public static ILandRegisterReportService getLandRegisterReportService() {
		return landRegisterReportService == null ? landRegisterReportService = new LandRegisterService() : landRegisterReportService;
	}

	public static IDrawalReportService getDrawalReportService() {
		return drawalReportService == null ? drawalReportService = new DrawalReportSerive() : drawalReportService;
	}

	public static ICollectionReportService getCollectionReportService() {
		return collectionReportService == null ? collectionReportService = new CollectionReportService() : collectionReportService;
	}

	public static IInconsistencyReportService getInconsistencyReportService() {
		return inconsistencyReportService == null ? inconsistencyReportService = new InconsistencyReportService() : inconsistencyReportService;
	}

	public static ISanctionReportService getsanctionReportService() {
		return sanctionReportService == null ? sanctionReportService = new SanctionReportSerive() : sanctionReportService;
	}

	public static IAccountStatementReportService getAccountStatementReportService() {
		return accountStatementReportService == null ? accountStatementReportService = new AccountStatementReportSerive()
				: accountStatementReportService;
	}

	public static IDCBReportService getDCBReportService() {
		return dcbReportService == null ? dcbReportService = new DCBReportService() : dcbReportService;
	}

	public static IShareAccountLedgerReportService getShareAccountLedgerReportService() {
		return shareAccountLedgerReportService == null ? shareAccountLedgerReportService = new ShareAccountLedgerReportService()
				: shareAccountLedgerReportService;
	}

	public static ICropWiseDrawlReportService getCropWiseDrawlReportService() {
		return cropWiseDrawlReportService == null ? cropWiseDrawlReportService = new CropWiseDrawlReportService() : cropWiseDrawlReportService;
	}

	public static IMTLTLoanLedgerReportService getMTLTLoanLedgerReportService() {
		return mtltLoanLedgerReportService == null ? mtltLoanLedgerReportService = new MTLTLoanLedgerReportService() : mtltLoanLedgerReportService;
	}

	public static IOverdueReportService getOverdueReportService() {
		return overdueReportService == null ? overdueReportService = new OverdueReportService() : overdueReportService;

	}

	public static IBatchWiseVoucherPrintReportService getBatchWiseVoucherPrintReportService() {
		return batchWiseVoucherPrintReportService == null ? batchWiseVoucherPrintReportService = new BatchWiseVoucherPrintReportService()
				: batchWiseVoucherPrintReportService;
	}

	public static IShareAccountBalancingReportService getShareAccountBalancingReportService() {
		return shareAccountBalancingReportService == null ? shareAccountBalancingReportService = new ShareAccountBalancingReportService()
				: shareAccountBalancingReportService;

	}

	public static IPurposeWiseDisbursmentService getPurposeWiseDisbursmentService() {
		return purposeWiseDisbursmentService == null ? purposeWiseDisbursmentService = new PurposeWiseDisbursmentService()
				: purposeWiseDisbursmentService;

	}

	public static IAccountInfoReportService getAccountInfoReportService() {
		return accountInfoReportService == null ? accountInfoReportService = new AccountInfoReportService() : accountInfoReportService;

	}

	public static IBorrowingAccountLedgerReportService getBorrowingAccountLedgerReportService() {
		return borrowingAccountLedgerReportService == null ? borrowingAccountLedgerReportService = new BorrowingAccountLedgerReportService()
				: borrowingAccountLedgerReportService;
	}

	public static IMemberProfileReportService getMemberProfileReportService() {
		return memberProfileReportService == null ? memberProfileReportService = new MemberProfileReportService() : memberProfileReportService;
	}

	public static ISTMTLTDcbReportService getStmtltDcbReportService() {
		return stmtltdcbReportService == null ? stmtltdcbReportService = new STMTLTDcbReportService() : stmtltdcbReportService;
	}

	public static IInterestChargedReportService getInterestChargedReportService() {
		return interestChargedReportService == null ? interestChargedReportService = new InterestChargedReportService()
				: interestChargedReportService;
	}

	public static IMasterReportService getMasterReportService() {
		return masterReportService == null ? masterReportService = new MasterReportService() : masterReportService;
	}

	public static IProductWiseCollectionReportService getProductWiseCollectionReportService() {
		return productWiseCollectionReportService == null ? productWiseCollectionReportService = new ProductWiseCollectionReportService()
				: productWiseCollectionReportService;
	}

	public static IBalanceListReportService getBalanceListReportService() {
		return balanceListReportService == null ? balanceListReportService = new BalanceListReportService() : balanceListReportService;
	}
	
	public static IInterestSubsidyReportService getInterestSubsidyReportService() {
		return interestSubsidyReportService == null ? interestSubsidyReportService = new InterestSubsidyReportService() : interestSubsidyReportService;
	}
	
	public static IScheduleVsGLBalanceReportService getScheduleVsGLBalanceReportService(){
		return scheduleVsGLBalanceReportService == null ? scheduleVsGLBalanceReportService = new ScheduleVsGLBalanceReportService() : scheduleVsGLBalanceReportService;
	}
	
	public static ILoanRegisterReportService getLoanRegisterReportService() {
		return loanRegisterReportService == null ? loanRegisterReportService = new LoanRegisterReportService() : loanRegisterReportService;
	}
	
	public static IJindagiReportService getJindagiReportService() {
		return jindagiReportService == null ? jindagiReportService = new JindagiReportService() : jindagiReportService;
	}
	public static IMemberandCardLinkingService getMemberandCardLinkingService() {
				return memberandcardlinkingService == null ? memberandcardlinkingService = new MemberandCardLinkingService() :memberandcardlinkingService;
	}

	public static INPAReportService2 getNPAReportService2() {
		return npaReportService2 == null ? npaReportService2 = new NPAReportService2() : npaReportService2;
	}


	
	
}
