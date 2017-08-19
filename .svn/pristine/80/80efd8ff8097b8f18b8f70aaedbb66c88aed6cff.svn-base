package com.vsoftcorp.kls.service.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementSchedule;
import com.vsoftcorp.kls.data.DisbursementData;
import com.vsoftcorp.kls.data.LoanDisbursementScheduleData;
import com.vsoftcorp.kls.data.LoanLineOfCreditData;
import com.vsoftcorp.kls.data.PacsLoanApplicationData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.ILoanDisbursementScheduleDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanLineOfCreditDAO;
import com.vsoftcorp.kls.service.account.ILoanLineOfCreditService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.LoanDisbursementScheduleHelper;
import com.vsoftcorp.kls.service.loan.ILoanDisbursementScheduleService;
import com.vsoftcorp.kls.service.loan.IPacsLoanApplicationService;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
/**
 * 
 * @author a1565
 *
 */
public class LoanDisbursementScheduleService implements ILoanDisbursementScheduleService{

	private static final Logger logger = Logger.getLogger(LoanDisbursementScheduleService.class);

	
	@Override
	public void saveLoanDisbursementSchedule(LoanDisbursementScheduleData data) {
		// TODO Auto-generated method stub
				logger.info("Start:Calling saveLoanDisbursementSchedule() method in LoanDisbursementScheduleService.. ");
				ILoanDisbursementScheduleDAO dao=KLSDataAccessFactory.getLoanDisbursementScheduleDAO();
				List<LoanDisbursementSchedule> disbursementScheduleList=new ArrayList<LoanDisbursementSchedule>();				
				Boolean flag=false;

				try
					{
						logger.info("Disbursement Schedule list Size::::"+data.getDisbursementList().size());
						disbursementScheduleList=LoanDisbursementScheduleHelper.getLoanDisbursementScheduleList(data.getDisbursementList());
						
						// Check whether total amount is matching sanction
						
						// Deleting existing schedule, which is not performed
						EntityManagerUtil.beginTransaction();

						dao.deleteNonDisbursedSchedule(data.getLocId());

						if(disbursementScheduleList!=null)
						{
							flag=true;
							for (LoanDisbursementSchedule loanDisbursementSchedule : disbursementScheduleList) {
								if(loanDisbursementSchedule.getDisbursedAmount().isZero()) {
									dao.saveLoanDisbursementSchedule(loanDisbursementSchedule);
								}
							}
						}
						
						EntityManagerUtil.CommitOrRollBackTransaction(true);
						
				}
				catch(RollbackException re)
				{
					re.printStackTrace();
					logger.error(" RollBack Exception "+re.getMessage());
					throw new DataAccessException("Unable to save LoanDisbursementSchedule data", re.getCause());
				}
				catch(Exception e)
				{
					e.printStackTrace();
					
					if(flag)
						EntityManagerUtil.CommitOrRollBackTransaction(false);

					logger.error("Unable to save LoanDisbursementSchedule data  in saveLoanDisbursementSchedule() method..");
					throw new DataAccessException("Unable to save LoanDisbursementSchedule data", e.getCause());
				}
				
		logger.info("End :LoanDisbursementSchedule data saved Successfully");
	}

	

	@Override
	public LoanDisbursementScheduleData getLOCWithDisbursementSchedule(Long lineOfCreditId) {

		LoanDisbursementScheduleData result = new LoanDisbursementScheduleData();
		
		LoanLineOfCredit loc=null;
		
		//PacsLoanApplicationData
		PacsLoanApplicationData applicationData=new PacsLoanApplicationData();
		
		/*//Getting Line Of Credit Data By LineOfCreditId
		LoanLineOfCreditData locData=new LoanLineOfCreditData();
		ILoanLineOfCreditService loanLocService = KLSServiceFactory.getLoanLineOfCreditService();
		locData = loanLocService.getLineOfCreditDataById(lineOfCreditId);*/
		
		
		ILoanLineOfCreditDAO loanLocDAO = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		loc=loanLocDAO.getLoanLineOfCreditById(lineOfCreditId);

		//Getting Application Data By LineOfCreditId
		IPacsLoanApplicationService applicationService=KLSServiceFactory.getPacsLoanApplicationService();
		applicationData=applicationService.getLoanApplicationById(loc.getPacsLoanApplication().getId());
		
		//Getting DisbursementData List  By LineOfCreditId
		List<DisbursementData> disbursementDataList=getDisbursementScheduleData(lineOfCreditId);
		logger.info("Account");
		result =LoanDisbursementScheduleHelper.getDisbursementScheduleData(loc,applicationData,disbursementDataList);
		
		return result;
	}
	
	//Service to get DisbursementScheduleData
	@Override
	public List<DisbursementData> getDisbursementScheduleData(Long lineOfCreditId) {
		// TODO Auto-generated method stub
		logger.info("Start: Getting DisbursementScheduleData using lineOfCreditid in getDisbursementScheduleData() method.");
		ILoanDisbursementScheduleDAO dao=KLSDataAccessFactory.getLoanDisbursementScheduleDAO();
		List<DisbursementData> list= new ArrayList<DisbursementData>();
		List<LoanDisbursementSchedule> disbursementList =new ArrayList<LoanDisbursementSchedule>();

		logger.info(" loanLocId : " + lineOfCreditId);
		try {
			disbursementList = dao.getDisbursementSchedule(lineOfCreditId);
			if(disbursementList !=null)
			{
				list=LoanDisbursementScheduleHelper.getLoanDisbursementScheduleListData(disbursementList);
			}
			else
				logger.info("DisbursementList is Empty");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Unable to get LoanDisbursementSchedule data in getDisbursementScheduleData() method..");
			throw new DataAccessException("Unable to get DisbursementSchedule data", e.getCause());
		}
		logger.info("End:Successfully completed getting DisbursementSchedule data from db"+list.get(0).getDisbursementDate());

		return list;
	}

		
}
