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
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.factory.KLSReportSeriveFactory;
import com.vsoftcorp.kls.report.service.data.OverdueListReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.util.Report;
import com.vsoftcorp.kls.report.util.ReportConfig;
import com.vsoftcorp.kls.report.util.ReportUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.time.Date;

public class OverdueReportActionServlet extends HttpServlet {

	private static final long serialVersionUID = -2156179601256068005L;
	private static final Logger logger = Logger.getLogger(OverdueReportActionServlet.class);

	public OverdueReportActionServlet() {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("Start: calling getOverDueIreport() inside doPost() ");
		PrintWriter printWriter = null;
		Integer productId = Integer.parseInt(request.getParameter("txtProductId").replaceAll("/", ""));
		logger.info("productId---" + productId);
		String userName = request.getParameter("userName");
		String bankName = request.getParameter("bankName");

		String businessDate = request.getParameter("businessDate");

		/*
		 * Integer branchId =
		 * Integer.parseInt(request.getParameter("branchId").replaceAll("/",
		 * "")); logger.info("branchId---" + branchId);
		 */

		String memberNo = request.getParameter("txtMemberNo");
		logger.info("memberNo : " + memberNo);
		String installmentDate = request.getParameter("txtAsOnDate");
		logger.info("installmentDate : " + installmentDate);

		String loanType = request.getParameter("selectProductType");
		logger.info("selectProductType : " + loanType);

		String isBankUser = request.getParameter("isBankUser");
		logger.info("isBankUser : " + isBankUser);

		String reportMode = request.getParameter("selectReportMode");
		logger.info("reportMode : " + reportMode);

		Integer pacsId = 0;
		if (isBankUser.equalsIgnoreCase("true"))
			pacsId = Integer.parseInt(request.getParameter("txtPacsId").replaceAll("/", ""));
		else
			pacsId = Integer.parseInt(request.getParameter("pacsId").replaceAll("/", ""));

		logger.info("pacsId : " + pacsId);

		BigDecimal villageId = BigDecimal.valueOf(Long.valueOf(request.getParameter("txtVillagesId").replaceAll("/", "").toString()));
		logger.info("villageId : " + villageId);

		// String accountNumber = request.getParameter("accountNumber");

		JRHtmlExporter exporter = null;
		JRPdfExporter pdfExporter = null;
		// Creating SBReport directory in usr folder
		ReportUtil.createSBReportDirectory();
		String pdfRequired= ReportConfig.getProperty("pdfrequired");
		File fileName = null;
		if(pdfRequired.equalsIgnoreCase("Y")){
			fileName= new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
					+ ReportConfig.getProperty("Overdue-Report-PDF-FileName"));	
		} else  {
			
			fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
				+ ReportConfig.getProperty("Overdue-Report-Txt-FileName"));
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
			session.setAttribute("outDatName", ReportConfig.getProperty("Overdue-Report-Bat-FileName"));
			session.setAttribute("reportName", ReportConfig.getProperty("Overdue-Report-Txt-FileName"));
		}
	
		System.out.println("fileName---" + fileName);
		fileName = ReportUtil.getFileName(fileName);
		System.out.println("fileName11---" + fileName);
		HttpSession session = request.getSession();
		session.setAttribute("txtFileName", fileName.getPath());
		Map<String, Object> map = null;
		String error = "Error Occured While Report Generation  ";
		try {
			map = getOverdueIreport(requiredFormat, printWriter, pacsId, productId, memberNo, installmentDate, villageId, loanType, fileName, userName,
					businessDate, reportMode, bankName);
		} catch (Exception e1) {
			e1.printStackTrace();
			printWriter.println(error);
		}
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
					
					 String filename=PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport"+ ReportConfig.getProperty("Overdue-Report-PDF-FileName");
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
		} else {
			printWriter=response.getWriter();
			printWriter.println("Data not found for Report");
		}
		logger.info("End:  inside doPost() ");

	}

	public Map<String, Object> getOverdueIreport(String reportType, PrintWriter printWriter, Integer pacsId, Integer productId, String memberNo,
			String installmentDate, BigDecimal villageId, String loanType, File filename, String userName, String businessDate, String reportMode,
			String bankName) throws ParseException {
		logger.info("Start: Calling OverdueReportService in getOverdueIreport()");

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		Pacs pac = null;

		if (pacsId != null) {

			IPacsDAO pDao = KLSDataAccessFactory.getPacsDAO();
			pac = pDao.getPacs(pacsId);
		}
		if (pac != null) {
			// parameters.put("pacName", pac.getName());
		}
		parameters.put("asOnDate", installmentDate);
		parameters.put("userName", userName);
		parameters.put("businessDate", ReportUtil.getUtilDateByString(null));
		parameters.put("bankName", bankName);

		// parameters.put("productId", productId);

		// parameters.put("productName", product.getName());

		Date instalmentDate = Date.valueOf(DateUtil.getFormattedDate(installmentDate));

		try {
			
			if (reportType.equals("pdf")) {
				
				List<OverdueListReportData> list = KLSReportSeriveFactory.getOverdueReportService().getOverdueReport(pacsId, productId, memberNo,
						instalmentDate, villageId, loanType, reportMode);

				logger.info("StockRegister Item list Size::::" + list.size());
				if (list.isEmpty())
					return null;
				JasperPrint jasperPrint = ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
						.getProperty("Overdue-Summary-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
				
				
				JRPdfExporter pdfexporter = new JRPdfExporter();
				pdfexporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				
                JasperExportManager.exportReportToPdfFile(jasperPrint, PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
						+ ReportConfig.getProperty("Overdue-Report-PDF-FileName"));
                HashMap<String, Object> myMap = new HashMap<String, Object>();
				myMap.put("exporter", pdfexporter);

				return myMap;
			}
			
			if (reportType.equals("html")) {
				List<OverdueListReportData> list = KLSReportSeriveFactory.getOverdueReportService().getOverdueReport(pacsId, productId, memberNo,
						instalmentDate, villageId, loanType, reportMode);
				if (list.isEmpty())
					return null;
				logger.info("shareAcclList:" + list);
				logger.info("reportMode : " + reportMode);
				JasperPrint jasperPrint = new JasperPrint();
				if (reportMode.equalsIgnoreCase("D")) {
					jasperPrint = ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig.getProperty("Overdue-Report-Jrxml")),
							new JRBeanCollectionDataSource(list), parameters);
				}
				if (reportMode.equalsIgnoreCase("S")) {
					jasperPrint = ReportUtil.getJasperPrint(
							ReportUtil.class.getResourceAsStream(ReportConfig.getProperty("Overdue-Summary-Report-Jrxml")),
							new JRBeanCollectionDataSource(list), parameters);
				}
				Map<String, List<String>> map = new HashMap<String, List<String>>();

				/*
				 * List<String> list1 = new ArrayList<String>();
				 * list1.add(product.getName()); map.put("productName", list1);
				 */
				/*
								List<String> list2 = new ArrayList<String>();
								list2.add(pac.getName());
								map.put("pacName", list2);*/

				List<String> list3 = new ArrayList<String>();
				list3.add(installmentDate);
				map.put("installmentDate", list3);

				List<String> list5 = new ArrayList<String>();
				list5.add(memberNo);
				map.put("memberNo", list5);

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
				ReportUtil.exportText(130, 80, jasperPrint, filename.toString());
				String footerHtml = ReportUtil.getFastPrint(map, ReportConfig.getProperty("Overdue-FPReport-JSP"), true, null);

				exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, footerHtml);

				HashMap<String, Object> myMap = new HashMap<String, Object>();
				myMap.put("exporter", exporter);
				return myMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error:Inside getOverdueIreport method");
			throw new KlsReportRuntimeException("Problem occured while generating report");
		}
		logger.info("End: Calling OverDueReportService in getOverdueIreport()");

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
		String txtName=ReportUtil.writeToFile(ReportConfig.getProperty("Overdue-Report-Bat-FileName"), printerCommand,
		txtFileName);
		logger.info("End: inside callFastPrint() ");
		return txtName;
	
	}
}
