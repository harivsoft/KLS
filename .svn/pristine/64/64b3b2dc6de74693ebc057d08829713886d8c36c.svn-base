package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.vsoftcorp.kls.GepRep.business.NPAParameters;
import com.vsoftcorp.kls.report.dao.INPAParameterDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import org.apache.log4j.Logger;

public class NPAParameterDAO  implements INPAParameterDAO{
	private static final Logger logger = Logger.getLogger(NPAParameterDAO.class);
	
	public List<NPAParameters> getNPAParameters() {
		List<NPAParameters> npaParametersList = new ArrayList<NPAParameters>();
		logger.info("Start:Inside getnpaparameter method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			npaParametersList = em.createQuery("SELECT n FROM NPAParameters n order by id").getResultList(); 
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getnpaparameter method");
			throw new KlsReportRuntimeException(" Inside getnpaparameter method", exception.getCause());
		}
		logger.info("End:Inside getnpaparameter method");
		return npaParametersList;
	}

}
