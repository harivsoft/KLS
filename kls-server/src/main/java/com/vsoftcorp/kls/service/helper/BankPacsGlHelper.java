package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.BankPacsGl;
import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.data.BankPacsGlData;

/**
 * @author a9153
 * 
 *         Helper Class for conversion from pojo to entity and vice versa.
 */

public class BankPacsGlHelper {

	/**
	 * The method converts bankPacsGl entity to bankPacsGl pojo.
	 * 
	 * @param theMaster
	 * @return
	 */
	public static BankPacsGlData getBankPacsGlMasterData(BankPacsGl theMaster) {
		BankPacsGlData data = new BankPacsGlData();
		if (theMaster.getId() != null) {
			data.setId(theMaster.getId().toString());
		}
		if (theMaster.getAccountNo() != null) {
			data.setAccountNo(theMaster.getAccountNo());
		}
		if (theMaster.getBankCode() != null) {
			data.setBankCode(theMaster.getBankCode());
		}
		if (theMaster.getBranch() != null) {
			data.setBranchId(theMaster.getBranch().getId().toString());
		}
		if (theMaster.getPacs() != null) {
			data.setPacsId(theMaster.getPacs().getId().toString());
		}

		if (theMaster.getPosDeviceNo() != null) {
			data.setPosDeviceNo(theMaster.getPosDeviceNo());
		}

		return data;
	}

	/**
	 * This method converts bankPacsGl pojo and bankPacsGl entity.
	 * 
	 * @param data
	 * @return
	 */
	public static BankPacsGl getBankPacsGl(BankPacsGlData data) {
		BankPacsGl master = new BankPacsGl();

		if (data.getId() != null) {
			master.setId(Integer.parseInt(data.getId()));
		}

		if (data.getAccountNo() != null) {

			master.setAccountNo(data.getAccountNo());
		}
		if (data.getPosDeviceNo() != null) {
			master.setPosDeviceNo(data.getPosDeviceNo());
		}
		if (data.getUserDetails().getBankCode() != null) {
			master.setBankCode(data.getUserDetails().getBankCode());
		}
		if (data.getBranchId() != null) {
			Branch branch = new Branch();
			branch.setId(Integer.parseInt(data.getBranchId()));
			master.setBranch(branch);
		}
		if (data.getUserDetails().getPacsId() != null) {
			Pacs pacs = new Pacs();
			pacs.setId(Integer.parseInt(data.getUserDetails().getPacsId()));
			master.setPacs(pacs);
		}
		if (data.getUserDetails().getBranchId() != null) {
			Branch branch=new Branch();
			branch.setId(Integer.parseInt(data.getUserDetails().getBranchId()));
			master.setBranch(branch);
		}
		return master;
	}
}
