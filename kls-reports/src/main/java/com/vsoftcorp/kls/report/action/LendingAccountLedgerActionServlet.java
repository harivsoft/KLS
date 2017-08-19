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

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.BorrowingProduct;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.dataaccess.IBorrowingProductDAO;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IPurposeDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.factory.KLSReportSeriveFactory;
import com.vsoftcorp.kls.report.service.data.BorrowingAccountLedgerReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.util.Report;
import com.vsoftcorp.kls.report.util.ReportConfig;
import com.vsoftcorp.kls.report.util.ReportUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;

public class LendingAccountLedgerActionServlet  extends HttpServlet{
	
	    private static final long serialVersionUID = 1L;
		private static final Logger logger = Logger.getLogger(BorrowingAccountLedgerActionServlet.class);

		public LendingAccountLedgerActionServlet() {
			// TODO Auto-generated constructor stub
		}
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doPost(request,response);
		}
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			logger.info("Start: calling getBorrowingAccountLedgerIreport() inside doPost() ");

			PrintWriter printWriter = null;
			String bankName = request.getParameter("bankName");
			logger.info("bankName : " + bankName);
			Integer productId = Integer.parseInt(request.getParameter("txtProductId"));
			Integer purposeId = Integer.parseInt(request.getParameter("txtPurposeId"));
			String borrowingType = request.getParameter("typeOfBorrowing");
			String fromDateString = request.getParameter("txtFromDate");
			logger.info("fromDateString---" + fromDateString);
			String toDateString = request.getParameter("txtToDate");
			logger.info("toDateString--" + toDateString);

			String userName = request.getParameter("userName");
			String businessDate = request.getParameter("businessDate");
			String memberId = request.getParameter("customerId");
			logger.info("memberNo--" + memberId);
			String isBankUser = request.getParameter("isBankUser");
			logger.info("isBankUser---" + isBankUser);
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
						+ ReportConfig.getProperty("LendingAccountLedger-Report-PDF-FileName"));	
			} else  {
				
				fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
					+ ReportConfig.getProperty("LendingAccountLedger-Report-Txt-FileName"));
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
				session.setAttribute("outDatName", ReportConfig.getProperty("LendingAccountLedger-Report-Bat-FileName"));
				session.setAttribute("reportName", ReportConfig.getProperty("LendingAccountLedger-Report-Txt-FileName"));
			}
			
			fileName = ReportUtil.getFileName(fileName);
			HttpSession session = request.getSession();
			session.setAttribute("txtFileName", fileName.getPath());

			Map<String, Object> map = null;
			String error = "Error Occured While Report Generation  ";
			try {
				map = getBorrowingAccountLedgerDataList(requiredFormat, pacsId, productId, purposeId, borrowingType, fromDateString, toDateString, printWriter,
						fileName, userName, businessDate, bankName,memberId);
			} catch (Exception e1) {
				e1.printStackTrace();
				printWriter.println(error);
			}

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
//						printWriter.println(error);
//					}
	//
//				}
		
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
					
					 String filename=PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport"+ ReportConfig.getProperty("BorrowingAccountLedger-Report-PDF-FileName");
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

		public Map<String, Object> getBorrowingAccountLedgerDataList(String reportType, Integer pacsId, Integer productId, Integer purposeId,
				String borrowingType, String fromDateString, String toDateString, PrintWriter printWriter, File filename, String userName,
				String businessDate, String bankName,String memberId) throws ParseException {
			logger.info("Start: Calling getBorrowingAccountLedgerDataList in getgetBorrowingAccountLedgerIreport()");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("bankName", bankName);
			Pacs pacs = null;
			if (pacsId != null && pacsId != 0) {
				IPacsDAO pDao = KLSDataAccessFactory.getPacsDAO();
				pacs = pDao.getPacs(pacsId);
			}

			if (pacs != null) {
				parameters.put("pacsName", pacs.getName());
				parameters.put("branchName", pacs.getBranch().getName());
			} else {
				parameters.put("pacsName", "All");
				parameters.put("branchName", "All");
			}

			if (purposeId != null && purposeId != 0) {
				IPurposeDAO purposeDAO = KLSDataAccessFactory.getPurposeDAO();
				Purpose purpose = purposeDAO.getPurposeById(purposeId);
				parameters.put("purposeName", purpose.getName());
			} else {
				parameters.put("purposeName", "All");
			}

			if (productId != null && productId != 0) {
				IBorrowingProductDAO borrowingProductDAO = KLSDataAccessFactory.getBorrowingProductDAO();
				BorrowingProduct borrowingProduct = borrowingProductDAO.getBorrowingProduct(productId, false);
				parameters.put("productName", borrowingProduct.getName());
			} else {
				parameters.put("productName", "All");
			}

			//DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			//java.util.Date fDate = df.parse(fromDateString);
			parameters.put("fDate", fromDateString);
			//DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
			//java.util.Date tDate = df.parse(toDateString);
			parameters.put("tDate", toDateString);

			parameters.put("userName", userName);
			parameters.put("businessDate", ReportUtil.getUtilDateByString(businessDate).toString());
			parameters.put("loanType", borrowingType);

			try {
				
				if (reportType.equals("pdf")) {
					
					
					List<BorrowingAccountLedgerReportData> list= KLSReportSeriveFactory
							.getBorrowingAccountLedgerReportService().getLendingAccountLedgerReportReport(pacsId, productId, purposeId, borrowingType,
									fromDateString, toDateString,memberId);

					logger.info("StockRegister Item list Size::::" + list.size());
					if (list.isEmpty())
						return null;
					JasperPrint jasperPrint = ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
							.getProperty("BorrowingAccountLedger-Report-Jrxm")), new JRBeanCollectionDataSource(list), parameters);
					
					
					JRPdfExporter pdfexporter = new JRPdfExporter();
					pdfexporter.setParameter(JRExporterParameter.JASPER_PRINT,
							jasperPrint);
					
	                JasperExportManager.exportReportToPdfFile(jasperPrint, PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
							+ ReportConfig.getProperty("LendingAccountLedger-Report-PDF-FileName"));
	                HashMap<String, Object> myMap = new HashMap<String, Object>();
					myMap.put("exporter", pdfexporter);

					return myMap;
				}
				
				if (reportType.equals("html")) {
					List<BorrowingAccountLedgerReportData> getBorrowingAccountLedgerReportDataList = KLSReportSeriveFactory
							.getBorrowingAccountLedgerReportService().getLendingAccountLedgerReportReport(pacsId, productId, purposeId, borrowingType,
									fromDateString, toDateString,memberId);
					if (getBorrowingAccountLedgerReportDataList.isEmpty())
						return null;
					logger.info("getBatchWiseVoucherPrintDataList:" + getBorrowingAccountLedgerReportDataList.size());

					JasperPrint jasperPrint = ReportUtil.getJasperPrint(
							ReportUtil.class.getResourceAsStream(ReportConfig.getProperty("LendingAccountLedger-Report-Jrxm")),
							new JRBeanCollectionDataSource(getBorrowingAccountLedgerReportDataList), parameters);
					Map<String, List<String>> map = new HashMap<String, List<String>>();

					JRHtmlExporter exporter = ReportUtil.getExportHtm(jasperPrint);

					exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, JRHtmlExporterParameter.SIZE_UNIT_PIXEL);

					exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);

					exporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);

					exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);

					exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

					exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.FALSE);


					/*exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(12));

					exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(6));*/

					exporter.setParameter(JRHtmlExporterParameter.OFFSET_X, -300);
					/*exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 80);
					exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 120);*/

					// exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER,printWriter);
					exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
					ReportUtil.exportText(120, 80, jasperPrint, filename.toString());
					String footerHtml = ReportUtil.getFastPrint(map, ReportConfig.getProperty("lendingAccountLedger-FPReport-JSP"), true, null);

					exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, footerHtml);

					HashMap<String, Object> myMap = new HashMap<String, Object>();
					myMap.put("exporter", exporter);

					return myMap;
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error:Inside getBatchWiseVoucherPrintDataList method");
				throw new KlsReportRuntimeException("Problem occured while generating report");
			}
			logger.info("End: Calling BatchWiseVoucherPrintDataList in getMTLTLoanLedgerIreport()");

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
			String txtName=ReportUtil.writeToFile(ReportConfig.getProperty("LendingAccountLedger-Report-Bat-FileName"), printerCommand,
			txtFileName);
			logger.info("End: inside callFastPrint() ");
			return txtName;
		
		}

	}

	
	


