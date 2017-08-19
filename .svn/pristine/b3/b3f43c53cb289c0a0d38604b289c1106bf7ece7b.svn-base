package com.vsoftcorp.kls.report.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.factory.KLSReportSeriveFactory;
import com.vsoftcorp.kls.report.service.data.DCBReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.util.Report;
import com.vsoftcorp.kls.report.util.ReportConfig;
import com.vsoftcorp.kls.report.util.ReportUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.time.Date;

public class DCBReportActionServlet extends HttpServlet {

	private static final long serialVersionUID = -2156179601256068005L;
	private static final Logger logger = Logger.getLogger(DCBReportActionServlet.class);

	public DCBReportActionServlet() {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("Start: calling getDCBIreport() inside doPost() ");
		PrintWriter printWriter = null;

		try {
			Integer branchId =0;
			if(request.getParameter("txtBranchId")!=null)
				branchId=Integer.parseInt(request.getParameter("txtBranchId").replaceAll("/", ""));

			Integer pacId =0;
			if(request.getParameter("txtPacsId")!=null)
				pacId=Integer.parseInt(request.getParameter("txtPacsId").replaceAll("/", ""));

			Integer schemeId = Integer.parseInt(request.getParameter("txtSchemeId").replaceAll("/", ""));

			Long custId = Long.parseLong(request.getParameter("txtcustId").replaceAll("/", ""));

			Integer demandFrequency = Integer.parseInt(request.getParameter("demandFrequency").replaceAll("/", ""));

			Integer txtDCBFor = Integer.parseInt(request.getParameter("DCBFor").replaceAll("/", ""));

			Integer reportType = Integer.parseInt(request.getParameter("reportType").replaceAll("/", ""));

			String fromDateString = request.getParameter("txtFromDate");
			String toDateString = request.getParameter("txtToDate");
			String userName = request.getParameter("userName");
			String businessDate = request.getParameter("businessDate");
			String bankName = request.getParameter("bankName");

			// Creating SBReport directory in usr folder
			ReportUtil.createSBReportDirectory();
			
			String pdfRequired= ReportConfig.getProperty("pdfrequired");
			File fileName = null;
			logger.info("checking dcbe report:::"+txtDCBFor+pdfRequired);
			if(pdfRequired.equalsIgnoreCase("Y")){
				if(0==txtDCBFor)
				{
				fileName= new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
						+ ReportConfig.getProperty("DCB-Report-Princile-Jrxml"));	
				
				}else if(1==txtDCBFor)
				{
					fileName= new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
							+ ReportConfig.getProperty("DCB-Report-Interest-Jrxml"));
				}
				else
				{
					fileName= new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
							+ ReportConfig.getProperty("DCB-Report-PDF-FileName"));	
				}
			} else  {
				
				if(0==txtDCBFor)
				{
				fileName= new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
						+ ReportConfig.getProperty("DCB-Report-Princile-html-Jrxml"));	
				
				}else if(1==txtDCBFor)
				{
					fileName= new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
							+ ReportConfig.getProperty("DCB-Report-Interest-Jrxml"));
				}
				
				logger.info("file name========="+fileName);
				fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
					+ ReportConfig.getProperty("DCB-Report-Txt-FileName"));
			}
			logger.info("In Try after File Util "+fileName);

			fileName = ReportUtil.getFileName(fileName);
			logger.info("In Try after File Util 212222");
			String requiredFormat=null;
			
			if(pdfRequired.equalsIgnoreCase("Y")){
				requiredFormat="pdf";
			}else{
				requiredFormat="html";
				HttpSession session = request.getSession();
				session.setAttribute("txtFileName", fileName.getPath());
				 session.setAttribute("outDatName", ReportConfig.getProperty("STMTLTDCB-Report-Bat-FileName"));
				 session.setAttribute("reportName", ReportConfig.getProperty("DCB-Report-Txt-FileName"));
				
			}
			logger.info("In Try after File Util "+requiredFormat);
			fileName = ReportUtil.getFileName(fileName);
			HttpSession session = request.getSession();
			session.setAttribute("txtFileName", fileName.getPath());
			logger.info("In Try after File Util333333 "+requiredFormat);
			JRHtmlExporter exporter = null;
			JRPdfExporter pdfExporter = null;
			Map<String, Object> map = getDCBIreport(requiredFormat, fromDateString, toDateString, printWriter, branchId, pacId, schemeId, custId,
					demandFrequency, txtDCBFor, reportType, fileName, userName, businessDate, bankName);
			String error = "Error Occured While Report Generation  ";
			logger.info("In Try after File Util 444444"+requiredFormat);
//			if (map != null) {
//				exporter = (JRHtmlExporter) map.get("exporter");
//
//				if (map.containsKey(Report._ERROR)) {
//					error = "<b>" + (String) map.get(Report._ERROR) + "</b>";
//				}
//				if (exporter == null) {
//					printWriter.println(error);
//				} else {
//					response.setContentType("text/html");
//					exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER, response.getWriter());
//					try {
//						exporter.exportReport();
//					} catch (JRException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//				}
//			} 
			if (map != null) 
			{
				 System.out.println("======pdfinside map"+pdfRequired);
				if(pdfRequired.equalsIgnoreCase("Y")){
					pdfExporter = (JRPdfExporter) map.get("exporter");
				}else{
				exporter = (JRHtmlExporter) map.get("exporter");
				}
				if (map.containsKey(Report._ERROR)) 
				{
					error = "<h3>" + (String) map.get(Report._ERROR) + "</h3>";
				}
				
				if(pdfRequired.equalsIgnoreCase("Y")){
					if (pdfExporter == null) 
					{		
						printWriter.println(error);
					}else{
						response.setContentType("application/pdf");
						
						

						String filename = null;
						if(0==txtDCBFor) {
							filename =  PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport"+ ReportConfig.getProperty("DCB-Report-PDF-FileName");
						} else {
							filename = PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport"+ ReportConfig.getProperty("DCB-Report-PDF-FileName");
						}
							File file = new File(filename);
							FileInputStream in = new FileInputStream(file);
							ServletOutputStream out1=response.getOutputStream();
							byte[] buf = new byte[4096];
							int count = 0;
							while ((count = in.read(buf)) >= 0)
							{
							out1.write(buf, 0, count);
							}

							in.close();
							out1.close();
					}
					
				}else{
				if (exporter == null) 
				{		
					printWriter.println(error);
				}
				else 
				{
					
					//PrintWriter writer = response.getWriter();

					response.setContentType("text/html");
					exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER,
							response.getWriter());
					exporter.exportReport();
					
				}
			}
			}
			
			else {
				
				printWriter=response.getWriter();
				printWriter.println("Data not found for Report");
			}
		} catch (Exception exception) {
			
			exception.getStackTrace();
			
		}
		logger.info("End:  inside doPost() ");

	}

	public static Map<String, Object> getDCBIreport(String reportTyp, String fromDateString, String toDateString, PrintWriter printWriter,
			Integer branchId, Integer pacId, Integer schemeId, Long customerId, Integer demandFrequency, Integer dcbFor, Integer reportType,
			File fileName, String userName, String businessDate, String bankName) throws ParseException {
		logger.info("Start: Calling DCBReportService in getDCBIreport()" + demandFrequency + reportType + dcbFor);

		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("bankName", bankName);
		parameters.put("dcbFor", dcbFor);
		parameters.put("fromDate", fromDateString);
		parameters.put("toDate", toDateString);
		parameters.put("demandFrequency", demandFrequency);
		parameters.put("reportType", reportType);
		parameters.put("userName", userName);
		parameters.put("businessDate", ReportUtil.getUtilDateByString(businessDate));

		Date fromDate = Date.valueOf(DateUtil.getFormattedDate(fromDateString));
		Date toDate = Date.valueOf(DateUtil.getFormattedDate(toDateString));
		try {
			if (reportTyp.equals("pdf")) {
				int pacs;
				
				List<DCBReportData> list = KLSReportSeriveFactory.getDCBReportService().getDCBReport(branchId, pacId, schemeId, customerId, fromDate,
						toDate, demandFrequency);
				logger.info("StockRegister Item list Size::::" + list.size());
				List<DCBReportData> newList = null;
				if(reportType == 1) {
					newList = new ArrayList<>();
					if(list.size() > 0) {
						
						BigDecimal previousPrincipalOverdueDemand = BigDecimal.ZERO;
						BigDecimal previousInterestOverdueDemand = BigDecimal.ZERO;
						
						BigDecimal currentPrincipalDemand = BigDecimal.ZERO;
						BigDecimal currentInterestDemand = BigDecimal.ZERO;
						
						BigDecimal overduePrincipalCollection = BigDecimal.ZERO;
						BigDecimal overdueInterestCollection = BigDecimal.ZERO;
						
						BigDecimal totalPrincipalCollection = BigDecimal.ZERO;
						BigDecimal totalInterestCollection = BigDecimal.ZERO;
						
						BigDecimal advancePrincipalCollection = BigDecimal.ZERO;
						BigDecimal advanceInterestCollection = BigDecimal.ZERO;
						
						BigDecimal principalBalanceOutstanding = BigDecimal.ZERO;
						BigDecimal interestBalanceOutstanding = BigDecimal.ZERO;
						
						BigDecimal overduePrincipleAsOnFromDate = BigDecimal.ZERO;
						BigDecimal overdueInterestAsOnFromDate = BigDecimal.ZERO;
						
						BigDecimal principalOverdueFromCurrentDemand = BigDecimal.ZERO;
						BigDecimal interestOverdueFromCurrentDemand = BigDecimal.ZERO;
						
						BigDecimal totalPrincipalOverdue = BigDecimal.ZERO;
						BigDecimal totalInterestOverdue = BigDecimal.ZERO;
						
						DCBReportData dbcData = null;
						for(DCBReportData data : list) {
							
							dbcData = data;
							previousPrincipalOverdueDemand = previousPrincipalOverdueDemand.add(data.getPreviousPrincipalOverdueDemand());
							previousInterestOverdueDemand = previousInterestOverdueDemand.add(data.getPreviousInterestOverdueDemand());
							dbcData.setPreviousPrincipalOverdueDemand(previousPrincipalOverdueDemand);
							dbcData.setPreviousInterestOverdueDemand(previousInterestOverdueDemand);
							
							currentPrincipalDemand = currentPrincipalDemand.add(data.getCurrentPrincipalDemand());
							currentInterestDemand = currentInterestDemand.add(data.getCurrentInterestDemand());
							dbcData.setCurrentPrincipalDemand(currentPrincipalDemand);
							dbcData.setCurrentInterestDemand(currentInterestDemand);
							
							overduePrincipalCollection = overduePrincipalCollection.add(data.getOverduePrincipalCollection());
							overdueInterestCollection = overdueInterestCollection.add(data.getOverdueInterestCollection());
							dbcData.setOverduePrincipalCollection(overduePrincipalCollection);
							dbcData.setOverdueInterestCollection(overdueInterestCollection);
							
							totalPrincipalCollection = totalPrincipalCollection.add(data.getTotalPrincipalCollection());
							totalInterestCollection = totalInterestCollection.add(data.getTotalInterestCollection());
							dbcData.setTotalPrincipalCollection(totalPrincipalCollection);
							dbcData.setTotalInterestCollection(totalInterestCollection);
							
							advancePrincipalCollection = advancePrincipalCollection.add(data.getAdvancePrincipalCollection());
							advanceInterestCollection = advanceInterestCollection.add(data.getAdvanceInterestCollection());
							dbcData.setAdvancePrincipalCollection(advancePrincipalCollection);
							dbcData.setAdvanceInterestCollection(advanceInterestCollection);
							
							principalBalanceOutstanding = principalBalanceOutstanding.add(data.getPrincipalBalanceOutstanding());
							interestBalanceOutstanding = interestBalanceOutstanding.add(data.getInterestBalanceOutstanding());
							dbcData.setPrincipalBalanceOutstanding(principalBalanceOutstanding);
							dbcData.setInterestBalanceOutstanding(interestBalanceOutstanding);
							
							overduePrincipleAsOnFromDate = overduePrincipleAsOnFromDate.add(data.getOverduePrincipleAsOnFromDate());
							overdueInterestAsOnFromDate = overdueInterestAsOnFromDate.add(data.getOverdueInterestAsOnFromDate());
							dbcData.setOverduePrincipleAsOnFromDate(overduePrincipleAsOnFromDate);
							dbcData.setOverdueInterestAsOnFromDate(overdueInterestAsOnFromDate);
							
							principalOverdueFromCurrentDemand = principalOverdueFromCurrentDemand.add(data.getPrincipalOverdueFromCurrentDemand());
							interestOverdueFromCurrentDemand = interestOverdueFromCurrentDemand.add(data.getInterestOverdueFromCurrentDemand());
							dbcData.setPrincipalOverdueFromCurrentDemand(principalOverdueFromCurrentDemand);
							dbcData.setInterestOverdueFromCurrentDemand(interestOverdueFromCurrentDemand);
							
							totalPrincipalOverdue = totalPrincipalOverdue.add(data.getTotalPrincipalOverdue());
							totalInterestOverdue = totalInterestOverdue.add(data.getTotalInterestOverdue());
							dbcData.setTotalPrincipalOverdue(totalPrincipalOverdue);
							dbcData.setTotalInterestOverdue(totalInterestOverdue);
						}
						newList.add(dbcData);
					}
					logger.info("New newList: "+newList.size());
					list = newList;
				}

				
				if (list.isEmpty())
					return null;
				JasperPrint jasperPrint=null;
				
				if(0==dcbFor)
				{
				
				
					jasperPrint	= ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
						.getProperty("DCB-Report-Princile-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
				}
				
				else if(1==dcbFor)
				{
					jasperPrint	= ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
							.getProperty("DCB-Report-Interest-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
					
				}
				
				else
				{
					jasperPrint	= ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
							.getProperty("DCB-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
				}
				
				JRPdfExporter pdfexporter = new JRPdfExporter();
				pdfexporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				
                JasperExportManager.exportReportToPdfFile(jasperPrint, PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
						+ ReportConfig.getProperty("DCB-Report-PDF-FileName"));
                
				
				
                HashMap<String, Object> myMap = new HashMap<String, Object>();
				myMap.put("exporter", pdfexporter);

				return myMap;
			}
			if (reportTyp.equals("html")) {
				List<DCBReportData> list = KLSReportSeriveFactory.getDCBReportService().getDCBReport(branchId, pacId, schemeId, customerId, fromDate,
						toDate, demandFrequency);
				List<DCBReportData> newList = null;
				if(reportType==1){
				newList = new ArrayList<>();
				if(list.size() > 0) {
				BigDecimal previousPrincipalOverdueDemand = BigDecimal.ZERO;
				BigDecimal previousInterestOverdueDemand = BigDecimal.ZERO;
				
				BigDecimal currentPrincipalDemand = BigDecimal.ZERO;
				BigDecimal currentInterestDemand = BigDecimal.ZERO;
				
				BigDecimal overduePrincipalCollection = BigDecimal.ZERO;
				BigDecimal overdueInterestCollection = BigDecimal.ZERO;
				
				BigDecimal totalPrincipalCollection = BigDecimal.ZERO;
				BigDecimal totalInterestCollection = BigDecimal.ZERO;
				
				BigDecimal advancePrincipalCollection = BigDecimal.ZERO;
				BigDecimal advanceInterestCollection = BigDecimal.ZERO;
				
				BigDecimal principalBalanceOutstanding = BigDecimal.ZERO;
				BigDecimal interestBalanceOutstanding = BigDecimal.ZERO;
				
				BigDecimal overduePrincipleAsOnFromDate = BigDecimal.ZERO;
				BigDecimal overdueInterestAsOnFromDate = BigDecimal.ZERO;
				
				BigDecimal principalOverdueFromCurrentDemand = BigDecimal.ZERO;
				BigDecimal interestOverdueFromCurrentDemand = BigDecimal.ZERO;
				
				BigDecimal totalPrincipalOverdue = BigDecimal.ZERO;
				BigDecimal totalInterestOverdue = BigDecimal.ZERO;
				
				DCBReportData dbcData = null;
				for(DCBReportData data : list) {
					
					dbcData = data;
					previousPrincipalOverdueDemand = previousPrincipalOverdueDemand.add(data.getPreviousPrincipalOverdueDemand());
					previousInterestOverdueDemand = previousInterestOverdueDemand.add(data.getPreviousInterestOverdueDemand());
					dbcData.setPreviousPrincipalOverdueDemand(previousPrincipalOverdueDemand);
					dbcData.setPreviousInterestOverdueDemand(previousInterestOverdueDemand);
					
					currentPrincipalDemand = currentPrincipalDemand.add(data.getCurrentPrincipalDemand());
					currentInterestDemand = currentInterestDemand.add(data.getCurrentInterestDemand());
					dbcData.setCurrentPrincipalDemand(currentPrincipalDemand);
					dbcData.setCurrentInterestDemand(currentInterestDemand);
					
					overduePrincipalCollection = overduePrincipalCollection.add(data.getOverduePrincipalCollection());
					overdueInterestCollection = overdueInterestCollection.add(data.getOverdueInterestCollection());
					dbcData.setOverduePrincipalCollection(overduePrincipalCollection);
					dbcData.setOverdueInterestCollection(overdueInterestCollection);
					
					totalPrincipalCollection = totalPrincipalCollection.add(data.getTotalPrincipalCollection());
					totalInterestCollection = totalInterestCollection.add(data.getTotalInterestCollection());
					dbcData.setTotalPrincipalCollection(totalPrincipalCollection);
					dbcData.setTotalInterestCollection(totalInterestCollection);
					
					advancePrincipalCollection = advancePrincipalCollection.add(data.getAdvancePrincipalCollection());
					advanceInterestCollection = advanceInterestCollection.add(data.getAdvanceInterestCollection());
					dbcData.setAdvancePrincipalCollection(advancePrincipalCollection);
					dbcData.setAdvanceInterestCollection(advanceInterestCollection);
					
					principalBalanceOutstanding = principalBalanceOutstanding.add(data.getPrincipalBalanceOutstanding());
					interestBalanceOutstanding = interestBalanceOutstanding.add(data.getInterestBalanceOutstanding());
					dbcData.setPrincipalBalanceOutstanding(principalBalanceOutstanding);
					dbcData.setInterestBalanceOutstanding(interestBalanceOutstanding);
					
					overduePrincipleAsOnFromDate = overduePrincipleAsOnFromDate.add(data.getOverduePrincipleAsOnFromDate());
					overdueInterestAsOnFromDate = overdueInterestAsOnFromDate.add(data.getOverdueInterestAsOnFromDate());
					dbcData.setOverduePrincipleAsOnFromDate(overduePrincipleAsOnFromDate);
					dbcData.setOverdueInterestAsOnFromDate(overdueInterestAsOnFromDate);
					
					principalOverdueFromCurrentDemand = principalOverdueFromCurrentDemand.add(data.getPrincipalOverdueFromCurrentDemand());
					interestOverdueFromCurrentDemand = interestOverdueFromCurrentDemand.add(data.getInterestOverdueFromCurrentDemand());
					dbcData.setPrincipalOverdueFromCurrentDemand(principalOverdueFromCurrentDemand);
					dbcData.setInterestOverdueFromCurrentDemand(interestOverdueFromCurrentDemand);
					
					totalPrincipalOverdue = totalPrincipalOverdue.add(data.getTotalPrincipalOverdue());
					totalInterestOverdue = totalInterestOverdue.add(data.getTotalInterestOverdue());
					dbcData.setTotalPrincipalOverdue(totalPrincipalOverdue);
					dbcData.setTotalInterestOverdue(totalInterestOverdue);
				}
				newList.add(dbcData);
			}
			logger.info("New newList: "+newList.size());
			list = newList;
			}
				if (list.isEmpty())
					return null;
				logger.info("list:" + list.size());
				JasperPrint jasperPrint=null;
				/*if(dcbFor==0){
				jasperPrint = ReportUtil.getJasperPrint(
							ReportUtil.class.getResourceAsStream(ReportConfig.getProperty("DCB-Report-interest-Jrxml")), new JRBeanCollectionDataSource(list),
							parameters);
				}
				else if(dcbFor==1){
				jasperPrint = ReportUtil.getJasperPrint(
							ReportUtil.class.getResourceAsStream(ReportConfig.getProperty("DCB-Report-principle-Jrxml")), new JRBeanCollectionDataSource(list),
							parameters);
				}
				else{*/
				jasperPrint = ReportUtil.getJasperPrint(
						ReportUtil.class.getResourceAsStream(ReportConfig.getProperty("DCB-Report-Jrxml")), new JRBeanCollectionDataSource(list),
						parameters);
				//}
				Map<String, List<String>> map = new HashMap<String, List<String>>();

				List<String> list1 = new ArrayList<String>();
				list1.add(bankName);
				map.put("bankName", list1);

				List<String> list2 = new ArrayList<String>();
				list2.add(demandFrequency.toString());
				map.put("demandFrequency", list2);

				List<String> list3 = new ArrayList<String>();
				list3.add(fromDateString);
				map.put("fromDate", list3);

				List<String> list4 = new ArrayList<String>();
				list4.add(toDateString);
				map.put("toDate", list4);

				List<String> list5 = new ArrayList<String>();
				list5.add(dcbFor.toString());
				map.put("dcbFor", list5);

				List<String> list6 = new ArrayList<String>();
				list6.add(reportType.toString());
				map.put("reportType", list6);

				JRHtmlExporter exporter = ReportUtil.getExportHtm(jasperPrint);

				exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, JRHtmlExporterParameter.SIZE_UNIT_PIXEL);

				exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);

				exporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);

				exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);

				exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

				exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.FALSE);

				exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(16));

				exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(16));

				exporter.setParameter(JRHtmlExporterParameter.OFFSET_X, -300);
				exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 80);
				exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 160);

				// exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER,printWriter);
				exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
				ReportUtil.exportText(130, 80, jasperPrint, fileName.toString());

				String footerHtml = ReportUtil.getFastPrint(map, ReportConfig.getProperty("DCB-FPReport-JSP"), true, null);

				exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, footerHtml);

				HashMap<String, Object> myMap = new HashMap<String, Object>();
				myMap.put("exporter", exporter);
				return myMap;
			}
		} catch (Exception e) {
			logger.error("Error:Inside getDCBIreport method");
			throw new KlsReportRuntimeException("Problem occured while generating report");
		}
		logger.info("End: Calling DCBReportService in getDCBIreport()");

		return null;
	}

	public static String callFastPrint(String txtFileName) {
		logger.info("Start: inside callFastPrint() ");
		String printerName = PropertiesUtil.getProperty("IP_ADDRESS") + PropertiesUtil.getProperty("PRINTER_NAME");
		String printerCommand = ReportConfig.getProperty("printCommand");
		if (printerName != null) {
			String IPAddress = PropertiesUtil.getProperty("IP_ADDRESS");
			String printName = PropertiesUtil.getProperty("PRINTER_NAME");
			printerName = printName + " -h " + IPAddress + " -o raw ";
			printerCommand = ReportConfig.getProperty("printCommand-Form") + " " + printerName;
		}
		String txtName = ReportUtil.writeToFile(ReportConfig.getProperty("STMTLTDCB-Report-Bat-FileName"), printerCommand, txtFileName);
		logger.info("End: inside callFastPrint() ");
		return txtName;

	}
}
