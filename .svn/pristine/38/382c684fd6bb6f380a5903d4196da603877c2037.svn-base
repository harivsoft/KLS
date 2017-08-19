package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.PacsGlMapping;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.data.PacsGlMappingData;

public class PacsGlMappingHelper {

	// Get PacsGlMapping Data

	public static PacsGlMappingData getPacsGlMappingData(PacsGlMapping entity) {

		PacsGlMappingData data = new PacsGlMappingData();

		data.setId(entity.getId());
		if (entity.getProduct() != null) {
			data.setProductId(entity.getProduct().getId());
			data.setProductName(entity.getProduct().getName());
		}
		if (entity.getPacs() != null) {
			data.setPacsId(entity.getPacs().getId());
			data.setPacsName(entity.getPacs().getName());
		}
		if (entity.getGlCode() != null)
			data.setGlCode(entity.getGlCode());
		if (entity.getIntReceivableGL() != null)
			data.setIntReceivableGL(entity.getIntReceivableGL());
		if (entity.getIntReceivedGL() != null)
			data.setIntReceivedGl(entity.getIntReceivedGL());
		if (entity.getPenalintReceivableGL() != null)
			data.setPenalIntReceivableGL(entity.getPenalintReceivableGL());
		if (entity.getPenalIntReceivedGL() != null)
			data.setPenalIntReceivedGL(entity.getPenalIntReceivedGL());
		data.setProcessingFeeGl(entity.getProcessingFeeGl());
		data.setInsuranceGl(entity.getInsuranceGl());

		if (entity.getClosingChargesGl() != null)
			data.setClosingChargesGl(entity.getClosingChargesGl());
		return data;

	}

	// Get Entity
	public static PacsGlMapping getPacsGlMapping(PacsGlMappingData data) {

		PacsGlMapping entity = new PacsGlMapping();

		if (data.getId() != null)
			entity.setId(data.getId());

		if (data.getProductId() != null) {
			Product product = new Product();
			product.setId(data.getProductId());
			entity.setProduct(product);
		}

		if (data.getPacsId() != null) {
			Pacs pacs = new Pacs();
			pacs.setId(data.getPacsId());
			entity.setPacs(pacs);
		}
		entity.setGlCode(data.getGlCode());
		if (data.getIntReceivableGL() != null)
			entity.setIntReceivableGL(data.getIntReceivableGL());
		if (data.getIntReceivedGl() != null)
			entity.setIntReceivedGL(data.getIntReceivedGl());
		if (data.getPenalIntReceivableGL() != null)
			entity.setPenalintReceivableGL(data.getPenalIntReceivableGL());
		if (data.getPenalIntReceivedGL() != null)
			entity.setPenalIntReceivedGL(data.getPenalIntReceivedGL());
		entity.setProcessingFeeGl(data.getProcessingFeeGl());
		entity.setInsuranceGl(data.getInsuranceGl());
		if (data.getClosingChargesGl() != null)
			entity.setClosingChargesGl(data.getClosingChargesGl());

		return entity;
	}

}
