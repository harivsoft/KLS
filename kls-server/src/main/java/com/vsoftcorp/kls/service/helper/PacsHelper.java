package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Village;
import com.vsoftcorp.kls.data.PacsData;
import com.vsoftcorp.kls.valuetypes.DayStatus;
import com.vsoftcorp.kls.valuetypes.PacsStatus;
import com.vsoftcorp.kls.valuetypes.VoucherResetFrequency;
import com.vsoftcorp.kls.valuetypes.transaction.PacsGLExtractUpload;

/**
 * @author a9153
 * 
 *         Helper Class for conversion from pojo to entity and vice versa.
 */

public class PacsHelper {

	/**
	 * The method converts pacs etity to pacs pojo.
	 * 
	 * @param theMaster
	 * @return
	 */
	public static PacsData getPacsData(Pacs pacs) {
		PacsData data = new PacsData();
		if (pacs.getId() != null) {
			data.setId(pacs.getId().toString());
		}
		data.setName(pacs.getName());
		if (pacs.getVillage() != null) {
			data.setVillageId(pacs.getVillage().getId());
			data.setVillageName(pacs.getVillage().getName());
		}
		data.setLocation(pacs.getLocation());
		data.setSecretaryName(pacs.getSecretaryName());
		data.setBranchId(pacs.getBranch().getId().toString());
		data.setBranchName(pacs.getBranch().getName());
		data.setPacsBankAccNumber(pacs.getPacsBankAccNumber());
		data.setBankPacsAccNumber(pacs.getBankPacsAccNumber());
		data.setCashGl(pacs.getCashGl());
		data.setMarginGl(pacs.getMarginGl());
		data.setCashInTransitGL(pacs.getCashInTransitGL());

		VoucherResetFrequency voucherResetFrequency = pacs.getVoucherResetFrequency();
		if (voucherResetFrequency != null)
			data.setVoucherResetFrequency(voucherResetFrequency.getValue());
		if (pacs.getInventoryImplemented() != null)
			data.setInventoryImplemented(pacs.getInventoryImplemented());

		data.setIsBorrwingTransRequiredInGLExtract(pacs.getIsBorrwingTransRequiredInGLExtract());
		PacsGLExtractUpload pacsGLExtractUpload = pacs.getPacsGlExtarctUpload();
		if (pacsGLExtractUpload != null)
			data.setPacsGLExtactUpload(pacsGLExtractUpload.getValue());

		data.setInterestPaidEditable(pacs.getIsInterestPaidEditable());
		
		if(pacs.getCashInTransitAccNo() != null)
		data.setCashInTransitAccNo(pacs.getCashInTransitAccNo());
		if(pacs.getLandValidation() != null){
			data.setLandValidation(pacs.getLandValidation());
		}
		data.setPacsStatus(pacs.getPacsStatus().getValue());
		data.setDayStatus(pacs.getDayStatus().getValue());
		
		
		return data;
	}

	/**
	 * This method converts pacs pojo and pacs entity.
	 * 
	 * @param data
	 * @return
	 */
	public static Pacs getPacs(PacsData data) {
		Pacs pacs = new Pacs();
		if (data.getId() != null)
			pacs.setId(Integer.parseInt(data.getId()));
		pacs.setName(data.getName());
		pacs.setLocation(data.getLocation());
		pacs.setSecretaryName(data.getSecretaryName());

		Village village = new Village();
		village.setId(data.getVillageId());
		village.setName(data.getVillageName());
		pacs.setVillage(village);

		Branch branch = new Branch();
		branch.setId(Integer.parseInt(data.getBranchId()));
		branch.setName(data.getBranchName());
		pacs.setBranch(branch);
		pacs.setPacsBankAccNumber(data.getPacsBankAccNumber());
		pacs.setBankPacsAccNumber(data.getBankPacsAccNumber());
		pacs.setCashGl(data.getCashGl());
		pacs.setMarginGl(data.getMarginGl());
		pacs.setCashInTransitGL(data.getCashInTransitGL());

		if (data.getVoucherResetFrequency() != null) {
			String voucherResetFrequency = data.getVoucherResetFrequency();
			pacs.setVoucherResetFrequency(VoucherResetFrequency.getType(voucherResetFrequency));
		}

		if (data.getInventoryImplemented() != null)
			pacs.setInventoryImplemented(data.getInventoryImplemented());

		pacs.setIsBorrwingTransRequiredInGLExtract(data.getIsBorrwingTransRequiredInGLExtract());

		String pacsGlExtarctUploadString = data.getPacsGLExtactUpload();
		if (pacsGlExtarctUploadString != null)
			pacs.setPacsGlExtarctUpload(PacsGLExtractUpload.getType(pacsGlExtarctUploadString));

		pacs.setIsInterestPaidEditable(data.isInterestPaidEditable());
		
		if(data.getCashInTransitAccNo()!=null)
			pacs.setCashInTransitAccNo(data.getCashInTransitAccNo());
		if(data.getLandValidation() != null){
			pacs.setLandValidation(data.getLandValidation());
		}
		pacs.setPacsStatus(PacsStatus.getType(data.getPacsStatus()));
		pacs.setDayStatus(DayStatus.getType(data.getDayStatus()));
		return pacs;
	}
}
