package com.vsoftcorp.kls.report.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.factory.KLSReportSeriveFactory;
import com.vsoftcorp.kls.report.service.data.JindagiData;
import com.vsoftcorp.kls.report.service.data.JindagiReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.util.Report;
import com.vsoftcorp.kls.report.util.ReportConfig;
import com.vsoftcorp.kls.report.util.ReportUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;

public class JindagiReportActionServlet  extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private static final Logger logger = Logger.getLogger(JindagiReportActionServlet.class);

	public JindagiReportActionServlet() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter printWriter = null;
		logger.info("customerId--" + request.getParameter("customerId"));
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));

		logger.info("customerId--" + customerId);

		Integer pacsId = Integer.parseInt(request.getParameter("pacsId").replaceAll("/", ""));
		logger.info("pacsId---" + pacsId);

		String userName = request.getParameter("userName");
		String businessDate = request.getParameter("businessDate");

		JRHtmlExporter exporter = null;
		JRRtfExporter exporter2= new JRRtfExporter();
		JRPdfExporter pdfExporter = null;
		// Creating SBReport directory in usr folder
		ReportUtil.createSBReportDirectory();
		String pdfRequired= ReportConfig.getProperty("pdfrequired");
		File fileName = null;
		if(pdfRequired.equalsIgnoreCase("Y")){
			System.out.println("the file pathe is"+PropertiesUtil.getDocumentProperty("filesLocation"));
			fileName= new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
					+ ReportConfig.getProperty("Jindagi-Report-PDF-FileName"));	
		} else  {

			fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
					+ ReportConfig.getProperty("Jindagi-Report-Txt-FileName"));
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
			session.setAttribute("outDatName", ReportConfig.getProperty("Jindagi-Report-Bat-FileName"));
			session.setAttribute("reportName", ReportConfig.getProperty("Jindagi-Report-Txt-FileName"));
		}
		fileName = ReportUtil.getFileName(fileName);
		HttpSession session = request.getSession();
		session.setAttribute("txtFileName", fileName.getPath());
		Map<String, Object> map = null;
		String error = "Error Occured While Report Generation  ";
		try {
			map = getJindagiIreport(requiredFormat, printWriter, customerId, fileName, pacsId, userName, businessDate);
			
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
						
						 String filename=PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport"+ ReportConfig.getProperty("Jindagi-Report-PDF-FileName");
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
			else{
				printWriter=response.getWriter();
				printWriter.println("Data not found for Report");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			printWriter.println(error);
		}
	}

	public Map<String, Object> getJindagiIreport(String reportType, PrintWriter printWriter, Integer customerId, File filename, Integer pacsId,
			String userName, String businessDate) throws ParseException {

		logger.info("Start: Calling JindagiIreportService in getJindagiIreport()");

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		Pacs pac = null;

		if (pacsId != null && pacsId > 0) {

			IPacsDAO pDao = KLSDataAccessFactory.getPacsDAO();
			pac = pDao.getPacs(pacsId);
			parameters.put("pacsName", pac.getName());
		}else{
			parameters.put("pacsName", "All Pacs");
		}
		if (pac != null) {
			parameters.put("branchName", pac.getBranch().getName());
			parameters.put("user", userName);
		}
		parameters.put("businessDate", ReportUtil.getUtilDateByString(businessDate));
		parameters.put("memberNumber", customerId);
		
		try {
		
			if (reportType.equals("pdf")) {
				JindagiReportData jindagiReportData = KLSReportSeriveFactory.getJindagiReportService().getLandDetailsByCustomerId(customerId.longValue());
				List<JindagiData> jindagiDataList = new ArrayList<JindagiData>();
				JindagiData jindagiData = new JindagiData();
				jindagiData.setCustomerLandDetailsData(new JRBeanCollectionDataSource(jindagiReportData.getCustomerLandDetailsDataList()));
				jindagiData.setLandChargesData(new JRBeanCollectionDataSource(jindagiReportData.getLandChargesDataList()));
				jindagiData.setTenantLandData(new JRBeanCollectionDataSource(jindagiReportData.getTenantLandDataList()));
				jindagiData.setRentLandData(new JRBeanCollectionDataSource(jindagiReportData.getRentLandDataList()));
				
				jindagiDataList.add(jindagiData);
				
				parameters.put("memberNumber", jindagiReportData.getMemberNumber());
				parameters.put("memberName", jindagiReportData.getMemberName());
				logger.info("jindagiReportData Item list Size::::" + jindagiDataList.size());
				if (jindagiReportData == null)
					return null;
				JasperPrint jasperPrint = ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
						.getProperty("Jindagi-Report-Jrxml")), new JRBeanCollectionDataSource(jindagiDataList), parameters);
				
				
				JRPdfExporter pdfexporter = new JRPdfExporter();
				pdfexporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				
				System.out.println("jasper Print"+jasperPrint);
				
				System.out.println("file Loacation"+PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport"
						+ ReportConfig.getProperty("Jindagi-Report-PDF-FileName"));
				
                JasperExportManager.exportReportToPdfFile(jasperPrint, PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport"
						+ ReportConfig.getProperty("Jindagi-Report-PDF-FileName"));
                HashMap<String, Object> myMap = new HashMap<String, Object>();
				myMap.put("exporter", pdfexporter);

				return myMap;
			} else {
				if (reportType.equals("html")) {
					
					JindagiReportData jindagiReportData = KLSReportSeriveFactory.getJindagiReportService().getLandDetailsByCustomerId(customerId.longValue());
					List<JindagiData> jindagiDataList = new ArrayList<JindagiData>();
					JindagiData jindagiData = new JindagiData();
					jindagiData.setCustomerLandDetailsData(new JRBeanCollectionDataSource(jindagiReportData.getCustomerLandDetailsDataList()));
					jindagiData.setLandChargesData(new JRBeanCollectionDataSource(jindagiReportData.getLandChargesDataList()));
					jindagiData.setTenantLandData(new JRBeanCollectionDataSource(jindagiReportData.getTenantLandDataList()));
					jindagiData.setRentLandData(new JRBeanCollectionDataSource(jindagiReportData.getRentLandDataList()));
					
					jindagiDataList.add(jindagiData);
					
					parameters.put("memberNumber", jindagiReportData.getMemberNumber());
					parameters.put("memberName", jindagiReportData.getMemberName());
					logger.info("jindagiReportData Item list Size::::" + jindagiDataList.size());	
					
					if (jindagiDataList.isEmpty())
						return null;
					logger.info("LandRegisterList:" + jindagiDataList.size());
					JasperPrint jasperPrint = ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
							.getProperty("Jindagi-Report-Jrxml")), new JRBeanCollectionDataSource(jindagiDataList), parameters);
					Map<String, List<String>> map = new HashMap<String, List<String>>();

					List<String> list1 = new ArrayList<String>();
					list1.add(pac.getBranch().getName());
					map.put("bankName", list1);

					List<String> list2 = new ArrayList<String>();
					list2.add(pac.getName());
					map.put("pacsName", list2);

					List<String> list3 = new ArrayList<String>();
					list3.add(pac.getLocation());
					map.put("address", list3);

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
					String footerHtml = ReportUtil.getFastPrint(map, ReportConfig.getProperty("Jindagi-Report-JSP"), true, null);

					exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, footerHtml);

					HashMap<String, Object> myMap = new HashMap<String, Object>();
					myMap.put("exporter", exporter);
					return myMap;
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Error:Inside getJindagiIreport method");
			throw new KlsReportRuntimeException("Problem occured while generating report");
		}
		logger.info("End: Calling JindagiIreportService in getJindagiIreport()");

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
		String txtName=ReportUtil.writeToFile(ReportConfig.getProperty("Jindagi-Report-Bat-FileName"), printerCommand,
		txtFileName);
		logger.info("End: inside callFastPrint() ");
		return txtName;
	
	}

}
