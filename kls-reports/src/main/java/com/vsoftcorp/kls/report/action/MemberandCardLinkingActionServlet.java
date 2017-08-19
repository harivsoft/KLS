package com.vsoftcorp.kls.report.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.vosftcorp.kls.report.service.IMemberProfileReportService;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.factory.KLSReportSeriveFactory;
import com.vsoftcorp.kls.report.service.data.MemberandCardLinkingData;
import com.vsoftcorp.kls.report.service.data.ScheduleReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.util.Report;
import com.vsoftcorp.kls.report.util.ReportConfig;
import com.vsoftcorp.kls.report.util.ReportUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.kls.userexceptions.AccountDoesntExistsException;
import com.vsoftcorp.time.Date;

public class MemberandCardLinkingActionServlet extends HttpServlet{
	private static final long serialVersionUID = 8249361955406450693L;
	
	private static final Logger logger = Logger.getLogger(MemberandCardLinkingActionServlet.class);
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("start: calling getMemberandCardLinkingReport() in doPost()");
		PrintWriter printWriter = null;
		String error = "Error Occured While Report Generation  ";
		try{
			Integer pacsId = 0;

			pacsId = Integer.parseInt(request.getParameter("pacsId").replaceAll("/", ""));
			logger.info("pacsId---" + pacsId);
			String branchName = request.getParameter("branchName");
			logger.info("branchName---" + branchName);
			String userName = request.getParameter("userName");
			logger.info("userName : " + userName);
			String businessDate = request.getParameter("businessDate");
			logger.info("businessDate:" +businessDate);
			String rType = request.getParameter("rType");
			logger.info("rType:" +rType);
			String customerId = request.getParameter("customerId");
			logger.info("customerId:" +customerId);
			Integer memberId=Integer.parseInt(customerId);
			
			
			JRHtmlExporter exporter = null;
			JRPdfExporter pdfExporter = null;
			ReportUtil.createSBReportDirectory();
			String pdfRequired= ReportConfig.getProperty("pdfrequired");
			File fileName = null;
			if(pdfRequired.equalsIgnoreCase("Y")){
				fileName= new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
						+ ReportConfig.getProperty("MemberandCardLinking-Report-PDF-FileName"));	
		}
			else  {
				
				fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
					+ ReportConfig.getProperty("MemberandCardLinking-Report-Txt-FileName"));
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
			}
			
			
			//fileName = ReportUtil.getFileName(fileName);
			HttpSession session = request.getSession();
			session.setAttribute("txtFileName", fileName.getPath());
			Map<String, Object> map = getMemberandCardLinkingReport(requiredFormat, printWriter, pacsId,fileName,branchName,userName,businessDate,rType ,memberId);
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
						
						 String filename=PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport"+ ReportConfig.getProperty("MemberandCardLinking-Report-PDF-FileName");
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
					exporter.exportReport();
					
				}
			}
			}
			else {
				printWriter=response.getWriter();
				printWriter.println("Data not found for Report");
			}
		} catch (Exception e) {
			logger.error("Error occured while generating report");
			e.printStackTrace();
			if (e instanceof AccountDoesntExistsException)
				printWriter.println(e.getMessage());
			else
				printWriter.println(error);
		}
		logger.info("End:  inside doPost() ");

	}
	public Map<String, Object> getMemberandCardLinkingReport(String reportType,PrintWriter printWriter,Integer pacsId,File fileName,String branchName,String userName,String businessDate , String rType , Integer customerId) throws ParseException {
		logger.info("Start: Calling MemberandCardLinkingReportService in getMemberandCardLinkingReport()");
		Pacs pac = null;
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		if (pacsId != null) {

			IPacsDAO pDao = KLSDataAccessFactory.getPacsDAO();
			pac = pDao.getPacs(pacsId);
			parameters.put("pacsName", pac.getName());
			parameters.put("branchName", pac.getBranch().getName());

		}
		
		parameters.put("pacName", pac != null ? pac.getName() : "");
		parameters.put("userName", userName);
		parameters.put("branchName", branchName);
		parameters.put("businessDate", ReportUtil.getUtilDateByString(null));
		parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
				try{					
					if (reportType.equals("pdf")) {
						List<MemberandCardLinkingData> list = KLSReportSeriveFactory.getMemberandCardLinkingService().getMemberandCardLinkingReport(pacsId ,rType ,customerId);
						if (list.isEmpty())
							return null;
						JasperPrint jasperPrint = ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
								.getProperty("MemberandCardLinking-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
						
						
						JRPdfExporter pdfexporter = new JRPdfExporter();
						pdfexporter.setParameter(JRExporterParameter.JASPER_PRINT,
								jasperPrint);
						
		                JasperExportManager.exportReportToPdfFile(jasperPrint, PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
								+ ReportConfig.getProperty("MemberandCardLinking-Report-PDF-FileName"));
		                HashMap<String, Object> myMap = new HashMap<String, Object>();
						myMap.put("exporter", pdfexporter);

						return myMap;
					}
					if (reportType.equals("html")) {
						List<MemberandCardLinkingData> list = KLSReportSeriveFactory.getMemberandCardLinkingService().getMemberandCardLinkingReport(pacsId,rType ,customerId);
		logger.info("Account statement list::::" + list.size());
						if (list.isEmpty())
							return null;

						JasperPrint jasperPrint = ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
								.getProperty("MemberandCardLinking-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
						
						logger.info(ReportConfig.getProperty("MemberandCardLinking-Report-Jrxml"));
						logger.info("jasperPrint" +jasperPrint);
						Map<String, List<String>> map = new HashMap<String, List<String>>();
						List<String> list2 = new ArrayList<String>();
						list2.add(pac.getName());
						map.put("pacName", list2);
						
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
						
						String footerHtml = ReportUtil.getFastPrint(map, ReportConfig.getProperty("MemberandCardLinking-FPReport-JSP"), true, null);

						exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, footerHtml);

						HashMap<String, Object> myMap = new HashMap<String, Object>();
						myMap.put("exporter", exporter);
						return myMap;
					}
				}catch (Exception e) {
					e.printStackTrace();
					logger.error("Error:Inside getMemberandCardLinkingreport method");
					throw new KlsReportRuntimeException("Problem occured while generating report");
				}
				logger.info("End: Calling MemberandCardLinkingService in getMemberandCardLinkingeport()");

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
	String txtName = ReportUtil.writeToFile(ReportConfig.getProperty("MemberandCardLinking-Report-Bat-FileName"), printerCommand, txtFileName);
	logger.info("End: inside callFastPrint() ");
	return txtName;

}

}