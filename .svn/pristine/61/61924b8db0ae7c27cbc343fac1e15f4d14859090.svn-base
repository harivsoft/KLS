package com.vsoftcorp.kls.report.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.factory.KLSReportSeriveFactory;
import com.vsoftcorp.kls.report.service.data.MTLTLoanLedgerReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.util.Report;
import com.vsoftcorp.kls.report.util.ReportConfig;
import com.vsoftcorp.kls.report.util.ReportUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.time.Date;

public class MTLTLoanLedgerActionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MTLTLoanLedgerActionServlet.class);

	public MTLTLoanLedgerActionServlet() {
		// TODO Auto-generated constructor stub
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Start: calling getMTLTLoanLedgerIreport() inside doPost() ");
		PrintWriter printWriter = null;
		Long customerId = Long.parseLong(request.getParameter("txtMemberNo"));
		logger.info("memberNo--" + customerId);
		String fromDateString = request.getParameter("txtFromDate");
		logger.info("fromDateString---" + fromDateString);
		String toDateString = request.getParameter("txtToDate");
		logger.info("toDateString--" + toDateString);

		String isBankUser = request.getParameter("isBankUser");
		logger.info("isBankUser---" + isBankUser);
		Long locNumber = Long.parseLong(request.getParameter("txtLoanAccountNo"));
		System.out.println("locNumber : " + locNumber);
		String productType = request.getParameter("selectProductType");

		String userName = request.getParameter("userName");
		String businessDate = request.getParameter("businessDate");

		Integer pacsId = 0;
		if (isBankUser.equalsIgnoreCase("true"))
			pacsId = Integer.parseInt(request.getParameter("txtPacsId").replaceAll("/", ""));
		else
			pacsId = Integer.parseInt(request.getParameter("pacsId").replaceAll("/", ""));

		logger.info("pacsId---" + pacsId);

		JRHtmlExporter exporter = null;
		JRPdfExporter pdfExporter = null;
		// Creating SBReport directory in usr folder
		ReportUtil.createSBReportDirectory();
		String pdfRequired= ReportConfig.getProperty("pdfrequired");
		File fileName = null;
		if(pdfRequired.equalsIgnoreCase("Y")){
			fileName= new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
					+ ReportConfig.getProperty("MTLTLoanLedger-Report-PDF-FileName"));	
		} else  {
		
			fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
				+ ReportConfig.getProperty("MTLTLoanLedger-Report-Txt-FileName"));
		}
		logger.info("In Try after File Util ");

		fileName = ReportUtil.getFileName(fileName);
		
		String requiredFormat=null;
		
		if(pdfRequired.equalsIgnoreCase("Y")){
			requiredFormat="pdf";
		}else{
			requiredFormat="html";
			HttpSession session = request.getSession();
			session.setAttribute("txtFileName", fileName.getPath());
			session.setAttribute("outDatName", ReportConfig.getProperty("MTLTLoanLedger-Report-Bat-FileName"));
			session.setAttribute("reportName", ReportConfig.getProperty("MTLTLoanLedger-Report-Txt-FileName"));
		}
		
		fileName = ReportUtil.getFileName(fileName);
		HttpSession session = request.getSession();
		session.setAttribute("txtFileName", fileName.getPath());

		Map<String, Object> map = null;
		String error = "Error Occured While Report Generation  ";

		try {
			map = getMTLTLoanLedgerIreport(requiredFormat, fromDateString, toDateString, printWriter, customerId, fileName, pacsId, locNumber, productType,
					userName, businessDate);
		} catch (Exception e1) {
			e1.printStackTrace();
			printWriter.println(error);
		}
//		if (map != null) {
//			exporter = (JRHtmlExporter) map.get("exporter");
//
//			if (map.containsKey(Report._ERROR)) {
//				error = "<b>" + (String) map.get(Report._ERROR) + "</b>";
//			}
//			if (exporter == null) {
//				printWriter.println(error);
//			} else {
//				response.setContentType("text/html");
//				exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER, response.getWriter());
//				try {
//					exporter.exportReport();
//				} catch (JRException e) {
//					printWriter.println(error);
//				}
//
//			}
//		} 
		
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
					
					 String filename=PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport"+ ReportConfig.getProperty("MTLTLoanLedger-Report-PDF-FileName");
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
				
				PrintWriter writer = response.getWriter();

				response.setContentType("text/html");
				exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER,
						response.getWriter());
				try {
					exporter.exportReport();
				} catch (JRException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		}
		
		else {
			printWriter=response.getWriter();
			printWriter.println("Data not found for Report");
		}
		logger.info("End:  inside doPost() ");

	}

	public Map<String, Object> getMTLTLoanLedgerIreport(String reportType, String fromDateString, String toDateString, PrintWriter printWriter,
			Long customerId, File filename, Integer pacsId, Long locNumber, String productType, String userName, String businessDate)
			throws ParseException {
		logger.info("Start: Calling MTLTLoanLedgerReportService in getMTLTLoanLedgerIreport()");

		PersonData customer = null;
		String shareBalance = null;
		HashMap<String, Object> parameters = new HashMap<String, Object>();

		if (customerId != null && customerId != 0) {
			customer = RestClientUtil.getCustomerById(customerId.longValue());
			if (customer != null)
				shareBalance = RestClientUtil.getShareBalanceByCustomerId(customer.getCustomerId(), toDateString);

			parameters.put("customerId", customer.getMemberNumber());
			parameters.put("custName", customer.getName());
			parameters.put("pacsId", customer.getPacsId());
			pacsId = customer.getPacsId();
			parameters.put("shareBalance", shareBalance);
			Account account = KLSDataAccessFactory.getAccountDAO().getAccountByCustomer(customerId.toString());
			if (account != null)
				parameters.put("accountNumber", account.getAccountNumber());

		} else {
			shareBalance = "0.00";
			parameters.put("customerId", "All");
			parameters.put("custName", "All");
			parameters.put("accountNumber", "All");
			logger.info("Account Number :");

		}

		Pacs pacs = null;
		if (pacsId != null && pacsId != 0) {
			IPacsDAO pDao = KLSDataAccessFactory.getPacsDAO();
			pacs = pDao.getPacs(pacsId);
		}

		if (pacs != null) {
			parameters.put("pacName", pacs.getName());
			parameters.put("bankName", pacs.getBranch().getName());
		} else {
			parameters.put("pacName", "All");
			parameters.put("bankName", "All");
		}
		Date fromDate = Date.valueOf(DateUtil.getFormattedDate(fromDateString));
		Date toDate = Date.valueOf(DateUtil.getFormattedDate(toDateString));

		
		parameters.put("fromDate",DateUtil.getDateString(fromDate));
		parameters.put("toDate", DateUtil.getDateString(toDate));

		parameters.put("includeInterest", "Yes");
		parameters.put("includeCharges", "Yes");
		parameters.put("includePerticulars", "Yes");

		parameters.put("userName", userName);
		parameters.put("businessDate", ReportUtil.getUtilDateByString(null).toString());

	
		try {
			if (reportType.equals("pdf")) {
				
				List<MTLTLoanLedgerReportData> list = KLSReportSeriveFactory.getMTLTLoanLedgerReportService()
						.getMTLTLoanLedgerReport(locNumber, fromDate, toDate, customerId, pacsId);

				logger.info("StockRegister Item list Size::::" + list.size());
				if (list.isEmpty())
					return null;
				JasperPrint jasperPrint = ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
						.getProperty("MTLTLoanLedger-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
				
				
				JRPdfExporter pdfexporter = new JRPdfExporter();
				pdfexporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				
                JasperExportManager.exportReportToPdfFile(jasperPrint, PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
						+ ReportConfig.getProperty("MTLTLoanLedger-Report-PDF-FileName"));
                HashMap<String, Object> myMap = new HashMap<String, Object>();
				myMap.put("exporter", pdfexporter);

				return myMap;
			}
			
			
			if (reportType.equals("html")) {
				List<MTLTLoanLedgerReportData> mtltLoanLedgerReportDataList = KLSReportSeriveFactory.getMTLTLoanLedgerReportService()
						.getMTLTLoanLedgerReport(locNumber, fromDate, toDate, customerId, pacsId);
				logger.info("MTLT loan ledger list size::::" + mtltLoanLedgerReportDataList.size());
				if (mtltLoanLedgerReportDataList.isEmpty()){
					mtltLoanLedgerReportDataList.add(null);
					parameters.put("data", "No Record Found");
				}
				//				if (!mtltLoanLedgerReportDataList.isEmpty()){
				//					parameters.put("openingBalance", mtltLoanLedgerReportDataList.get(0).getPrincipalBalance());
				//				} else {
				//					return null;
				//				}
				//logger.info("mtltLoanLedgerReportDataList:" + mtltLoanLedgerReportDataList);

				// list.remove(0);

				/*			String includeInterest = "No";
							String includeCharges = "No";
							Iterator<MTLTLoanLedgerReportData> iterator = mtltLoanLedgerReportDataList.iterator();
							while (iterator.hasNext()) {
								MTLTLoanLedgerReportData data = (MTLTLoanLedgerReportData) iterator.next();

								if (includeInterest.equalsIgnoreCase("No") && (data.getInterestCredit() != null || data.getInterestDebit() != null))
									iterator.remove();

								if (includeCharges.equalsIgnoreCase("No") && (data.getChargesCredit() != null || data.getChargesDebit() != null))
									iterator.remove();

								if (data.getLocNo() == null)
									iterator.remove();
							}*/

				JasperPrint jasperPrint = ReportUtil.getJasperPrint(
						ReportUtil.class.getResourceAsStream(ReportConfig.getProperty("MTLTLoanLedger-Report-Jrxml")),
						new JRBeanCollectionDataSource(mtltLoanLedgerReportDataList), parameters);
				Map<String, List<String>> map = new HashMap<String, List<String>>();

				/*List<String> list3 = new ArrayList<String>();
				list3.add(fromDateString);
				map.put("fromDate", list3);

				List<String> list4 = new ArrayList<String>();
				list4.add(toDateString);
				map.put("toDate", list4);
				
				List<String> list7 = new ArrayList<String>();
				list7.add("Yes");
				map.put("includeInterest", list7);

				List<String> list8 = new ArrayList<String>();
				list8.add("Yes");
				map.put("includeCharges", list8);

				List<String> list9 = new ArrayList<String>();
				list9.add("Yes");
				map.put("includePerticulars", list9);
				*/

				JRHtmlExporter exporter = ReportUtil.getExportHtm(jasperPrint);

				exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, JRHtmlExporterParameter.SIZE_UNIT_PIXEL);

				exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);

				exporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);

				exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);

				exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

				exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.FALSE);

				exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(16));

				exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(16));

				exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);

				exporter.setParameter(JRHtmlExporterParameter.OFFSET_X, -300);

				exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 80);

				exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 160);

				// exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER,printWriter);
				exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
				ReportUtil.exportText(130, 80, jasperPrint, filename.toString());
				String footerHtml = ReportUtil.getFastPrint(map, ReportConfig.getProperty("MTLTLoanLedger-FPReport-JSP"), true, null);

				exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, footerHtml);

				HashMap<String, Object> myMap = new HashMap<String, Object>();
				myMap.put("exporter", exporter);
				return myMap;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error:Inside getMTLTLoanLedgerIreport method");
			throw new KlsReportRuntimeException("Problem occured while generating report");
		}
		logger.info("End: Calling MTLTLoanLedgerReportService in getMTLTLoanLedgerIreport()");

		return null;
	}

	public static String callFastPrint(String txtFileName) {
		logger.info("Start: inside callFastPrint() ");
		String printerName = PropertiesUtil.getProperty("IP_ADDRESS")+PropertiesUtil.getProperty("PRINTER_NAME");
		String printerCommand = ReportConfig.getProperty("printCommand");
		if (printerName != null) {
		String IPAddress = PropertiesUtil.getProperty("IP_ADDRESS");
		String printName = PropertiesUtil.getProperty("PRINTER_NAME");
		printerName = printName + " -h " + IPAddress + " -o raw ";
		printerCommand = ReportConfig.getProperty("printCommand-Form") + " " + printerName;
		}
		String txtName=ReportUtil.writeToFile(ReportConfig.getProperty("MTLTLoanLedger-Report-Bat-FileName"), printerCommand,
		txtFileName);
		logger.info("End: inside callFastPrint() ");
		return txtName;
	
	}

}
