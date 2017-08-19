package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.vsoftcorp.kls.report.dao.IMemberProfileReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.MemberProfileData;

public class MemberProfileReportHelper {

	public static List<MemberProfileData> getMemberProfileListReport(List<Object[]> memberProfileList) {
		List<MemberProfileData> memProfileList = new ArrayList<MemberProfileData>();
		MemberProfileData data = null;
		try {
			for (Object[] object : memberProfileList) {
				data = new MemberProfileData();
				data.setVillageName((String) object[0]);
				data.setFarmerTypeName((String) object[1]);
				data.setCasteName((String) object[2]);
				data.setMemberNumber((String) object[3]);
				data.setMemberName((String) object[4]);
				data.setFatherOrSpouseName((String) object[5]);

				Date date = (Date) object[6];

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String text = df.format(date);

				data.setRegDate(text);
				data.setGender((String) object[7]);

				Date dob = (Date) object[8];

				Calendar currenttime = Calendar.getInstance();
				Date sqldate = new Date((currenttime.getTime()).getTime());

				IMemberProfileReportDAO mDao = KLSReportDataAccessFactory.getMemberProfileReportDAO();
				Integer age = mDao.getMemberAge(sqldate.toString(), dob.toString());

				data.setAge(age);

				data.setEducation((String) object[9]);
				data.setOccupation((String) object[10]);
				Double landArea = (Double) object[11];
				Double cultivatedArea = (Double) object[12];
				if (landArea != null)
					data.setLandArea(new BigDecimal(landArea).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
				else
					data.setLandArea(0.00d);
				if (cultivatedArea != null)
					data.setCultivatedArea(new BigDecimal(cultivatedArea).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
				else
					data.setCultivatedArea(0.00d);
				data.setDocNumber((String) object[13]);
				data.setTypeOfDoc((String) object[14]);
				data.setSbAccountNumber((String) object[15]);
				data.setMobileNumber((String) object[16]);
				BigDecimal casteId = (BigDecimal) object[17];
				data.setCasteId(casteId.intValue());
				data.setVillageId((Integer) object[18]);
				BigDecimal farmerTypeId = (BigDecimal) object[19];
				data.setFarmerTypeId(farmerTypeId.intValue());
				memProfileList.add(data);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return memProfileList;
	}
}
