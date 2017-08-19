package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.dao.IMasterReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class MasterReportDAO implements IMasterReportDAO {
	private static final Logger logger = Logger.getLogger(MasterReportDAO.class);

	@Override
	public List<Map> getMasterReport(String master) {

		List<Map> masterdataList = new ArrayList<Map>();
		logger.info("Start:Inside getMasterReport method--" + master);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			if (master.equalsIgnoreCase("LoanProductPurposeMapping")) {
				stringBuilder
						.append("select new Map(l.product.name as productName,l.purpose.name as purposeName,l.subPurpose.name as subPurposeName,l.activity.name as activityName) from LoanProductPurposeMapping l,Product p,Purpose pur,SubPurpose sp,Activity a where l.product.id = p.id and l.purpose.id = pur.id and l.subPurpose.id = sp.id and l.activity.id = a.id");
			}else if(master.equalsIgnoreCase("ProductChargesMapping")){
				stringBuilder.append("select new Map(p.product.name as name,p.chargesMaster.chargesDescription as chargesDescription) from ProductChargesMapping p,Product pr,ChargesMaster c where p.product.id=pr.id and p.chargesMaster.id=c.id");
			}else if(master.equalsIgnoreCase("ChargesMaster")){
				stringBuilder.append("select new Map(c.chargesDescription as chargesDescription,c.chargesCode as chargesCode,c.minAmount as minAmount,c.maxAmount as maxAmount,c.chargesType as chargesType,c.chargesReceivedGL as chargesReceivedGL,c.chargesReceivableGL as chargesReceivableGL) from ChargesMaster c");
			}
			else{
			stringBuilder.append("select distinct new Map(name as name) from " + master + "");
			}
			Query query = em.createQuery(stringBuilder.toString());
			masterdataList = query.getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getMasterReport method");
			throw new KlsReportRuntimeException("Can not print master report:", exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Inside getMasterReport method");

		return masterdataList;
	}

}
