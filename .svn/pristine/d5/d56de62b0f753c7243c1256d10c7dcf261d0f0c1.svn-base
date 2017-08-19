package com.vsoftcorp.kls.report.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.factory.KLSReportSeriveFactory;
import com.vsoftcorp.kls.report.service.data.BalanceListReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.util.Report;
import com.vsoftcorp.kls.report.util.ReportConfig;
import com.vsoftcorp.kls.report.util.ReportUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public class BalanceListReportActionServlet extends HttpServlet {

	private static final long serialVersionUID = -4632229005426047061L;

	private static final Logger logger = Logger.getLogger(BalanceListReportActionServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("start: BalanceListReportActionServlet in doPost()");
		PrintWriter printWriter = null;
		String error = "Error Occured While Report Generation  ";
		try {
			String bankName = request.getParameter("bankName");
			String pacsIdString = request.getParameter("pacsId");
			String userName = request.getParameter("userName");
			String businessDate = request.getParameter("businessDate");
			String pacsName = request.getParameter("pacsName");
			String producIdString = request.getParameter("txtProductId");
			String memberIdString = request.getParameter("txtMemberNo");
			String asOnDateString = request.getParameter("txtBalanceDate");
			Integer pacsId = null;
			Integer producId = null;
			Long customerId = null;
			if (pacsIdString != null)
				pacsId = Integer.parseInt(pacsIdString);
			if (producIdString != null)
				producId = Integer.parseInt(producIdString);
			if (memberIdString != null)
				customerId = Long.parseLong(memberIdString);

			JRHtmlExporter exporter = null;
			JRPdfExporter pdfExporter = null;
			// Creating SBReport directory in usr folder
			ReportUtil.createSBReportDirectory();
			
			String pdfRequired= ReportConfig.getProperty("pdfrequired");
			File fileName = null;
			if(pdfRequired.equalsIgnoreCase("Y")){
				fileName= new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
						+ ReportConfig.getProperty("BalanceList-Report-PDF-FileName"));	
			} else  {
				fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
					+ ReportConfig.getProperty("BalanceList-Report-Txt-FileName"));
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
				session.setAttribute("outDatName", ReportConfig.getProperty("BalanceList-Report-Bat-FileName"));
				session.setAttribute("reportName", ReportConfig.getProperty("BalanceList-Report-Txt-FileName"));
			}
			fileName = ReportUtil.getFileName(fileName);
			HttpSession session = request.getSession();
			session.setAttribute("txtFileName", fileName.getPath());
			Map<String, Object> map = getBalanceListReportData(requiredFormat, pacsId, producId, customerId, asOnDateString, bankName, userName, pacsName,
					fileName,businessDate);
			error = "Error Occured While Report Generation  ";
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
						
						 String filename=PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport"+ ReportConfig.getProperty("BalanceList-Report-PDF-FileName");
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
		} catch (Exception e) {
			e.printStackTrace();
			printWriter.println(error);
		}
		logger.info("End:  inside doPost() ");

	}

	public Map<String, Object> getBalanceListReportData(String reportType, Integer pacsId, Integer productId, Long customerId, String asOnDataString,
			String bankName, String userName, String pacsName, File fileName,String businessDate) {
		logger.info("Start: Calling BalanceListReportService in getBalanceListReportData()");
		try {
			Date asOnDate = DateUtil.getVSoftDateByString(asOnDataString);
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("bankName", bankName);
			parameters.put("asOnDate", asOnDataString);
			parameters.put("pacsName", pacsName);
			parameters.put("userName", userName);
			parameters.put("businessDate", ReportUtil.getUtilDateByString(businessDate));
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
			
			if (reportType.equals("pdf")) {
			
				
				List<BalanceListReportData> list = KLSReportSeriveFactory.getBalanceListReportService().getBalanceListReportData(pacsId, productId,
						customerId, asOnDate);
				

				logger.info("StockRegister Item list Size::::" + list.size());
				if (list.isEmpty())
					return null;
				JasperPrint jasperPrint = ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
						.getProperty("BalanceList-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
				
				
				JRPdfExporter pdfexporter = new JRPdfExporter();
				pdfexporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				
                JasperExportManager.exportReportToPdfFile(jasperPrint, PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
						+ ReportConfig.getProperty("BalanceList-Report-PDF-FileName"));
                HashMap<String, Object> myMap = new HashMap<String, Object>();
				myMap.put("exporter", pdfexporter);

				return myMap;
			}
			if (reportType.equals("html")) {
				List<BalanceListReportData> list = KLSReportSeriveFactory.getBalanceListReportService().getBalanceListReportData(pacsId, productId,
						customerId, asOnDate);
				logger.info("Balance List Report list size::::" + list.size());
				if (list.isEmpty())
					return null;

				JasperPrint jasperPrint = ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
						.getProperty("BalanceList-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
				Map<String, List<String>> map = new HashMap<String, List<String>>();

				List<String> list1 = new ArrayList<String>();
				list1.add(bankName);
				map.put("bankName", list1);

				List<String> list2 = new ArrayList<String>();
				list2.add(asOnDataString);
				map.put("asOnDate", list2);

				List<String> list3 = new ArrayList<String>();
				list3.add(pacsName);
				map.put("pacsName", list3);

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

				String footerHtml = ReportUtil.getFastPrint(map, ReportConfig.getProperty("BalanceList-FPReport-JSP"), true, null);

				exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, footerHtml);

				HashMap<String, Object> myMap = new HashMap<String, Object>();
				myMap.put("exporter", exporter);
				return myMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error:Inside Balance list report method");
			throw new KlsReportRuntimeException("Error while showing Balance list report:", e.getCause());
		}
		logger.info("End: Calling BalanceListReportService in getBalanceListReportData()");

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
		String txtName=ReportUtil.writeToFile(ReportConfig.getProperty("BalanceList-Report-Bat-FileName"), printerCommand,
		txtFileName);
		logger.info("End: inside callFastPrint() ");
		return txtName;
	
	}
}
