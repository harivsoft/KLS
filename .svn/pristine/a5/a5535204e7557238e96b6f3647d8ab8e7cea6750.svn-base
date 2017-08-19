package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.BankPacsGl;
import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.data.BankPacsGlData;
import com.vsoftcorp.kls.dataaccess.IBankPacsGlDAO;
import com.vsoftcorp.kls.dataaccess.IBranchDAO;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IBankPacsGlService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.BankPacsGlHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

/**
 * 
 * @author a9152
 * 
 */
public class BankPacsGlService implements IBankPacsGlService {

	private static final Logger logger = Logger.getLogger(BankPacsGlService.class);

	/**
	 * This method checks for bankPacsGl id in the db. If exists, then
	 * throw an exception. Else save it to the database.
	 * 
	 * @param bankPacsGlData
	 */
	public void saveBankPacsGl(BankPacsGlData bankPacsGlData) {

		logger.info("Start : Calling bankPacsGl master dao in saveBankPacsGl() method.");
		// get the bankPacsGl dao.
		IBankPacsGlDAO dao = KLSDataAccessFactory.getBankPacsGlDAO();
		BankPacsGl master = BankPacsGlHelper.getBankPacsGl(bankPacsGlData);
		// get the taluka dao.
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		IBranchDAO branchDao = KLSDataAccessFactory.getBranchDAO();

		BankPacsGl bankPacsGl = null;

		Pacs pacs = null;
		Branch branch = null;

		try {
			/*pacs = pacsDao.getPacs(master.getPacs());
			branch = branchDao.getBranch(master.getBranch());

			if (pacs != null && branch != null) {
				master.setBranch(branch);
				master.setPacs(pacs);

				bankPacsGl = dao.getBankPacsGl(master, false);
				if (master.getId() == null) {
					dao.saveBankPacsGl(master);
				}
			}*/
			logger.info("deleted list size=============="+bankPacsGlData.getDeletedList());
			if(bankPacsGlData.getDeletedList()!=null){
			if(bankPacsGlData.getDeletedList().size()>0){
				List<BankPacsGlData> deleteList=bankPacsGlData.getDeletedList();
				
				
				for(BankPacsGlData bankGl:deleteList){
					logger.info(" deleteList " + deleteList.size() );
					dao.deleteBankPacsGl(Integer.parseInt(bankGl.getId()));
				}
			}
			}
			if (bankPacsGlData.getAccountNo()!=null && bankPacsGlData.getPosDeviceNo()!=null) {
				dao.saveBankPacsGl(master);
			}
			
		
		} catch (Exception excp) {
			logger.error("BankPacsGl id already exists");
			excp.printStackTrace();
			throw new KlsRuntimeException("BankPacsGl id already exists", excp.getCause());
			
		}
		/*if (pacs == null) {
			logger.error("BankPacsGl data cannot be saved to the db as pacs id does not exists");
			throw new KlsRuntimeException("BankPacsGl data cannot be saved to the db as pacs id does not exists");
		}
		if (branch == null) {
			logger.error("BankPacsGl data cannot be saved to the db as branch id does not exists");
			throw new KlsRuntimeException("BankPacsGl data cannot be saved to the db as branch id does not exists");
		}*/
		logger.info("End : Calling bankPacsGl dao in saveBankPacsGl() method.");
	}

	/**
	 * This method checks for bankPacsGl id in the db. If exists, then
	 * update the bankPacsGl data to the database. Else throw the
	 * exception.
	 * 
	 * @param bankPacsGlData
	 */
	public void updateBankPacsGl(BankPacsGlData bankPacsGlData) {

		logger.info("Start : Calling bankPacsGl dao in updateBankPacsGl() method.");
		// get the bankPacsGl dao.
		IBankPacsGlDAO dao = KLSDataAccessFactory.getBankPacsGlDAO();
		BankPacsGl master = BankPacsGlHelper.getBankPacsGl(bankPacsGlData);
		// update the bankPacsGl data to the db.
		try {
			dao.updateBankPacsGl(master);
		} catch (Exception excp) {
			logger.error("BankPacsGl data cannot be updated as bankPacsGl id does not exist");
			excp.printStackTrace();
			throw new KlsRuntimeException("BankPacsGl data cannot be updated as bankPacsGl id does not exist", excp.getCause());
		}
		logger.info("End : Calling bankPacsGl dao in updateBankPacsGl() method.");
	}

	@Override
	public List<BankPacsGlData> getAllBankPacsGls() {

		logger.info("Start : Calling bankPacsGl dao in getAllBankPacsGls() method.");
		// get the bankPacsGl dao.
		IBankPacsGlDAO dao = KLSDataAccessFactory.getBankPacsGlDAO();
		List<BankPacsGlData> bankPacsGlDataList = new ArrayList<BankPacsGlData>();
		try {
			List<BankPacsGl> bankPacsGlList = dao.getAllBankPacsGls(false);
			for (BankPacsGl masterData : bankPacsGlList) {
				bankPacsGlDataList.add(BankPacsGlHelper.getBankPacsGlMasterData(masterData));
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error in retrieving all the bankPacsGl records");
			
			throw new KlsRuntimeException("Error in retrieving all the bankPacsGl records", excp.getCause());
		}
		logger.info("End : Calling bankPacsGl dao in getAllBankPacsGls() method.");
		return bankPacsGlDataList;
	}
	
	public String getBankPacsGlAccNo(String bankCode, int branchId, int pacsId, String deviceNo){
		logger.info("Start : Calling bankPacsGl dao in getBankPacsGlAccNo() method.");
		// get the bankPacsGl dao.
		String accNo = "";
		IBankPacsGlDAO dao = KLSDataAccessFactory.getBankPacsGlDAO();
		try {
			accNo = dao.getBankPacsGlAccNo(bankCode, branchId, pacsId, deviceNo);
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error in retrieving bankPacsGl record. Inside getBankPacsGlAccNo()");
			throw new KlsRuntimeException("Error in retrieving the bankPacsGl record. Inside getBankPacsGlAccNo()", excp.getCause());
		}
		logger.info("End : Calling bankPacsGl dao in getBankPacsGlAccNo() method.");
		return accNo;		
	}

	@Override
	public void deleteBankPacsGl(Integer Id) {
		// TODO Auto-generated method stub
				
			logger.info("Start:Calling deleteBankPacsGl() method in BankPacsGl.. ");
			
			IBankPacsGlDAO dao=KLSDataAccessFactory.getBankPacsGlDAO();
			try{
				if(Id!=null)
					dao.deleteBankPacsGl(Id);
			}
			catch(Exception e)
			{
			e.printStackTrace();
			logger.error("Unable to delete BankPacsGl data ..in deleteBankPacsGl() method..");
			throw new DataAccessException("Unable to delete BankPacsGl data", e.getCause());
			}
		logger.info("End:Successfully  deleting BankPacsGl data in  deleteBankPacsGl data from db");
		}	
		
		
	
	
	
	
}
