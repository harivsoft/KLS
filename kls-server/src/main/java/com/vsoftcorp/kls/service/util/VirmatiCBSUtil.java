package com.vsoftcorp.kls.service.util;

import java.math.BigInteger;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.vsoftcorp.kls.business.entities.CBSUID;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class VirmatiCBSUtil {
	public synchronized static  String getCBSUID(Integer pacsId) throws Exception {
		EntityManager em = EntityManagerUtil.getEntityManager();
		CBSUID cbsUID = null;
		String uID=null;
		try {
			String query = "Select v from CBSUID v where v.pacsId="+pacsId;
			cbsUID = em.createQuery(query, CBSUID.class).getSingleResult();
			if(cbsUID != null) {
				cbsUID.setLastUsedNumber(cbsUID.getLastUsedNumber().add(BigInteger.ONE));
				em.merge(cbsUID);
			} else {
				throw new Exception("UID  Does not created.");
			}
			uID=cbsUID.getLastUsedNumber().toString();
			String pID=pacsId.toString();
			StringUtils.leftPad(uID, 20-pID.length(),"0");
			uID=pID+uID;
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception("CBS UID Does not created.");
		}
		return uID;
	}
}
