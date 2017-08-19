package com.vsoftcorp.kls.report.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;



public class Report {

	public static final String _PAGE_NO ="pageNo";
	public static final String _PAGES_TOT ="totpages";
	public static final String _ERROR ="errorTxt";

	private static final Logger logger = Logger.getLogger(Report.class);

   /**
	 * 
	 * The Below Service Methods is For Production...
	 * above all are related to testing Purpose............
	 * */

	/*
	 * This is the Report Generated based on the status of the Member Loan Application....
	 * Application Register
	 * Delete Register
	 * System Sanction Register etc..
	 * 
	 * 
	 * populates the Data as a Text Report...It is Used in Fast Print....
	 * 
	 * if Necessary please Undo the  comment on TransactionUtill
	 * 
	 * */


	



	/*
	 * This is the Report Generated based on the status of the Member Loan Application....
	 * Application Register
	 * Delete Register
	 * System Sanction Register etc..
	 * 
	 * 
	 * populates the Data On a Jsp
	 * 
	 * if Necessary please Undo the  comment on TransactionUtill
	 * 
	 * */

	



	
	static protected String amountInWords;  
	static protected String words = "";  
	final static String units[] = {"",   
		"One ",  
		"Two ",  
		"Three ",  
		"Four ",  
		"Five ",  
		"Six ",  
		"Seven ",  
		"Eight ",  
		"Nine ",  
		"Ten ",  
		"Eleven ",  
		"Twelve ",  
		"Thirteen ",  
		"Fourteen ",  
		"Fifteen ",  
		"Sixteen ",  
		"Seventeen ",  
		"Eighteen ",  
	"Nineteen "};  
	
	static String tens[] = {"",  
			"Ten ",  
			"Twenty ",  
			"Thirty ",  
			"Forty ",  
			"Fifty ",  
			"Sixty ",  
			"Seventy ",  
			"Eighty ",  
	"Ninety "};  

	public  static String  strAmountInWords(int amount)  
	{  
		amountInWords="";
		words="";
		if(amount!=0)
		{
			int crore = amount / 10000000;   
			String tword1 =threeDigits(crore);
			if(tword1.length()>1)  
				words+=tword1 + "Crore ";
			amount-= crore*10000000;  	

			int millions = amount / 100000;   
			String tword =threeDigits(millions);  
			if(tword.length()>1)  
				words+=tword + "Lakh ";  
			amount-= millions*100000;    


			int thousands = amount / 1000;  
			tword = threeDigits(thousands);  
			if(tword.length()>1)  
				words+= tword + "Thousand ";  
			amount-=thousands*1000;  

			words+=threeDigits(amount) ;  
			amountInWords = words; 
		}
		else
		{
			words="Zero";
		}

		return words;


	}  
	public static String threeDigits(int digits)  
	{  
		String digWord = "";  
		int hnd = digits / 100;  
		if(hnd>0)  
			digWord+=units[hnd] + "Hundred ";  
		int ten = digits - hnd *100;  
		if(ten<20)  
			digWord+= units[ten];  
		else  
		{  
			int tenth = ten / 10;  
			digWord+=tens[tenth];  
			int last = ten - tenth*10;  
			digWord+= units[last];  

		}  
		return digWord;  
	} 
	
	
	public static void main(String[] args) throws Exception
	{
		Report p = new Report();
		List<String> applNo = new ArrayList<String>();
		 
		 applNo.add("1294150779555");
		 applNo.add("1294150746303");
		//p.printForwardingMemo(applNo);
	
/*	
		MembershipClosureData membershipClosureData=new MembershipClosureData();
		membershipClosureData.setCustomerName("Mohan Seshu");
		membershipClosureData.setFatherName("Rao");
		membershipClosureData.setAddress("Plot no.37, Nanakramguda, hyderabad");
		membershipClosureData.setDesignation("GM");
		membershipClosureData.setDepartment("01002");
		membershipClosureData.setConstituency("01001");
		SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy");
		membershipClosureData.setDateOfBirth(sf.parse("03-10-1975"));
		membershipClosureData.setScrJoinDate(sf.parse("01-01-2000"));
		membershipClosureData.setSocietyJoinDate(sf.parse("01-01-2001"));
		membershipClosureData.setRetirementDate(sf.parse("31-10-2035"));
		membershipClosureData.setAmount(BigDecimal.valueOf(100));
		membershipClosureData.setRemarks("Being Closed");
		
*/		
		/*MemberNomineeData data = new MemberNomineeData();
		data.setNomineeName("Mohan");
		data.setNomineeRelationShip("Nephew");
		data.setNomineeAge(13);
		data.setGender(Gender.MALE);
		data.setGaurdianName("Sri");
		data.setPercentage(BigDecimal.valueOf(43));
		data.setBankAccountNo("000011112222");
		data.setRemarks("ok");
		membershipClosureData.addMemberNominee(data);
		
		MemberNomineeData data2 = new MemberNomineeData();
		data2.setNomineeName("Nagarjuna");
		data2.setNomineeRelationShip("Cousin");
		data2.setNomineeAge(23);
		data2.setGender(Gender.FEMALE);
		data2.setGaurdianName("Raja");
		data2.setPercentage(BigDecimal.valueOf(34));
		data2.setBankAccountNo("000011112223");
		data2.setRemarks("not ok");
		
		membershipClosureData.addMemberNominee(data2);
		
		membershipClosureData.addBusinessMessages("Member has not conmpleted 3 years as a member in the society");
		membershipClosureData.addBusinessMessages("Member has more liabilities than assets");
		DocumentData documentData=new DocumentData();
		documentData.setDocumentName("Book bank");
		membershipClosureData.addSelectedDocument(documentData);
		DocumentData documentData1=new DocumentData();
		documentData1.setDocumentName("XYZ");
		membershipClosureData.addSelectedDocument(documentData1);
		*/
/*		AssetsLiabilitesData assetsLiabilitesData=new AssetsLiabilitesData();
		AssetData assetData=new AssetData();
		assetData.setAssetAmount(BigDecimal.valueOf(12));
		assetData.setAssetType("RBF");
		assetsLiabilitesData.addAsset(assetData);
		AssetData assetData1=new AssetData();
		assetData1.setAssetAmount(BigDecimal.valueOf(13));
		assetData1.setAssetType("Funeral Amount");
		assetsLiabilitesData.addAsset(assetData1);
		
		AssetData assetData2=new AssetData();
		assetData2.setAssetAmount(BigDecimal.valueOf(23));
		assetData2.setAssetInterest(BigDecimal.valueOf(2));
		assetData2.setAssetType("Share");
		assetsLiabilitesData.addAsset(assetData2);

		AssetData assetData3=new AssetData();
		assetData3.setAssetAmount(BigDecimal.valueOf(231));
		assetData3.setAssetInterest(BigDecimal.valueOf(22));
		assetData3.setAssetType("MBF");
		assetsLiabilitesData.addAsset(assetData3);


		AssetData assetData4=new AssetData();
		assetData4.setAssetAmount(BigDecimal.valueOf(31));
		assetData4.setAssetInterest(BigDecimal.valueOf(2));
		assetData4.setAssetType("CMTD");
		assetsLiabilitesData.addAsset(assetData4);

		
		LiabilityData liabilityData=new LiabilityData();
		liabilityData.setLiabilityAmount(BigDecimal.valueOf(6));
		liabilityData.setLiabilityType("Advance");
		assetsLiabilitesData.addLiability(liabilityData);
		
		LiabilityData liabilityData1=new LiabilityData();
		liabilityData1.setLiabilityAmount(BigDecimal.valueOf(17));
		liabilityData1.setLiabilityType("Postal charges");
		assetsLiabilitesData.addLiability(liabilityData1);
		
		LiabilityData liabilityData2=new LiabilityData();
		liabilityData2.setLiabilityAmount(BigDecimal.valueOf(7));
		liabilityData2.setLiabilityType("GL");
		liabilityData2.setLiabilityInterest(BigDecimal.valueOf(2));
		assetsLiabilitesData.addLiability(liabilityData2);

		LiabilityData liabilityData3=new LiabilityData();
		liabilityData3.setLiabilityAmount(BigDecimal.valueOf(17));
		liabilityData3.setLiabilityInterest(BigDecimal.valueOf(2.5));
		liabilityData3.setLiabilityType("CMTD Loan");
		assetsLiabilitesData.addLiability(liabilityData3);

		LiabilityData liabilityData4=new LiabilityData();
		liabilityData4.setLiabilityAmount(BigDecimal.valueOf(4));
		liabilityData4.setLiabilityInterest(BigDecimal.valueOf(3));
		liabilityData4.setLiabilityType("CDL");
		assetsLiabilitesData.addLiability(liabilityData4);

		BigDecimal totalAssetsAmount=BigDecimal.ZERO;
		for(AssetData assetData0:assetsLiabilitesData.getAssets())
		{
			if (assetData0.getAssetAmount()!=null)
				totalAssetsAmount=totalAssetsAmount.add(assetData0.getAssetAmount());
			if (assetData0.getAssetInterest()!=null)
				totalAssetsAmount=totalAssetsAmount.add(assetData0.getAssetInterest());
		}
		assetsLiabilitesData.setTotalAsset(totalAssetsAmount);

		BigDecimal totalLiabilitiesAmount=BigDecimal.ZERO;
		for(LiabilityData liabilityData0:assetsLiabilitesData.getLiabilites())
		{
			if (liabilityData0.getLiabilityAmount()!=null)
				totalLiabilitiesAmount=totalLiabilitiesAmount.add(liabilityData0.getLiabilityAmount());
			if (liabilityData0.getLiabilityInterest()!=null)
				totalLiabilitiesAmount=totalLiabilitiesAmount.add(liabilityData0.getLiabilityInterest());
		}
		assetsLiabilitesData.setTotalLiability(totalLiabilitiesAmount);
		
		assetsLiabilitesData.setNetPayableAmount(totalAssetsAmount.subtract(totalLiabilitiesAmount));

		membershipClosureData.setAssetsLiabilitesData(assetsLiabilitesData);
		//boolean isReund=true;
		MembershipClosureDataSCRJR membershipClosureDataSCRJR=new MembershipClosureDataSCRJR(membershipClosureData);
		
		
		logger.info("printMemberClosureApplicationAcknowledgement ==========applicant name==========>"+membershipClosureData.getCustomerName());
		String jrxmlFilePath = "/reports/refundMainReport.jrxml"; // path
		//HashMap<String,Object> parameters = new HashMap<String, Object>();
		//String title;
		
		
		//	title = " MEMBER APPLICATION ACKNOWLEDGEMENT";
		
		//parameters.put("username", membershipClosureData.getLoggedUser());
		//parameters.put("title", title);
		
		List<MembershipClosureDataSCRJR>  datas = new ArrayList<MembershipClosureDataSCRJR>();
		
		datas.add(membershipClosureDataSCRJR);
		
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
				datas);
		JasperPrint jasperPrint = ReportUtil.getJasperPrint(jrxmlFilePath,
				ds, null);

		ReportUtil.exportText(120,70, jasperPrint, "/usr/SBReport/MemberRefund.txt");

		ReportUtil.writeToFile("/usr/SBReport/MemberRefund.bat", null, "/usr/SBReport/MemberRefund.txt");
	
		
*/		
	}
	
	
	
	/*
	 * This Below Method is Used in Printing Share Certificate as a Single Instrument..
	 * 
	 * if Necessary please Undo the  comment on TransactionUtill
	 * */


}
