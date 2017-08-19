package com.vsoftcorp.kls.service.helper;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationHeader;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.data.PacsLoanApplicationHeaderData;
import com.vsoftcorp.kls.dataaccess.IProductDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.impl.PacsLoanApplicationHeaderService;

/**
 * @author a9153
 * 
 *         Helper Class for conversion from pojo to entity and vice versa.
 */

public class PacsLoanApplicationHeaderHelper {
	private static final Logger logger = Logger
			.getLogger(PacsLoanApplicationHeaderHelper.class);
	public static PacsLoanApplicationHeaderData getPacsLoanApplicationHeaderData(
			PacsLoanApplicationHeader theMaster) {
		PacsLoanApplicationHeaderData data = new PacsLoanApplicationHeaderData();
		logger.info("inside helper::");
		data.setId(theMaster.getId().toString());
		data.setApplicationType(theMaster.getApplicationType());
		data.setProcessStatus(theMaster.getProcessStatus());
		data.setPacsId(theMaster.getPacs().getId().toString());
		data.setPacsName(theMaster.getPacs().getName());
		data.setSchemeId(theMaster.getScheme().getId().toString());
		data.setSchemeName(theMaster.getScheme().getName());
		data.setFinancialYear(theMaster.getFinancialYear());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String applicationDate = formatter.format(theMaster
				.getApplicationDate());
		data.setApplicationDate(applicationDate);
	//	data.setCropId(theMaster.getCrop().getId().toString());
	//data.setCropName(theMaster.getCrop().getName());
		if(theMaster.getProduct()!=null){
			data.setProductId(theMaster.getProduct().getId());
			data.setProductName(theMaster.getProduct().getName());
		}
		return data;
	}

	public static PacsLoanApplicationHeader getPacsLoanApplicationHeader(
			PacsLoanApplicationHeaderData data) {
		PacsLoanApplicationHeader master = new PacsLoanApplicationHeader();

		if (data.getId() != null)
			master.setId(Long.parseLong(data.getId()));
		master.setApplicationType(data.getApplicationType());
		master.setProcessStatus(data.getProcessStatus());
		master.setFinancialYear(data.getFinancialYear());
		master.setApplicationDate(MasterHelper.getDateFromString(
				data.getApplicationDate(), "dd/MM/yyyy"));

		Pacs pacs = new Pacs();
		Crop crop = new Crop();
		Product product = new Product();
		// Season season = new Season();
		Scheme scheme = new Scheme();

		pacs.setId(Integer.parseInt(data.getPacsId()));
		// season.setId(Long.parseLong(data.getSeasonId()));
		//crop.setId(Integer.parseInt(data.getCropId()));
		scheme.setId(Integer.parseInt(data.getSchemeId()));
		product.setId(data.getProductId());
		
		master.setPacs(pacs);
		//master.setCrop(crop);
		master.setScheme(scheme);
		//IProductDAO pDao = KLSDataAccessFactory.getProductMasterDAO();
		//List<Product> list = pDao.getProductsBySchemeId(Integer.parseInt(data.getSchemeId()), false);
		master.setProduct(product);
		return master;
	}
}
