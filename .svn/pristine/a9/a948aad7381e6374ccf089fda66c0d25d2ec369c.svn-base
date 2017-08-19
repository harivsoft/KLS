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
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.factory.KLSReportSeriveFactory;
import com.vsoftcorp.kls.report.service.data.NPAReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.util.Report;
import com.vsoftcorp.kls.report.util.ReportConfig;
import com.vsoftcorp.kls.report.util.ReportUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;

public class BorrowingsNPAReportActionServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(BorrowingsNPAReportActionServlet.class);
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("Start: calling BorrowingsNPAReportActionServlet() inside doPost() ");
		PrintWriter printWriter = null;
		String bankName = request.getParameter("bankName");
		Integer pacsId = Integer.parseInt(request.getParameter("pacsId").replaceAll("/", ""));
		String businessDate = request.getParameter("businessDate");
		String loanType = request.getParameter("loanType");
		String reportType = request.getParameter("reportType");
		String asOnDate = request.getParameter("asOnDate");
		String userName = request.getParameter("userName");
		String excludeStndLoan = request.getParameter("excludeStndLoan");
		/*Integer productId = Integer.parseInt(request.getParameter("productId").replaceAll("/", ""));
		String productName=request.getParameter("productName");
		String loginUser=request.getParameter("userName");*/
		/*String seasonId = Integer.parseInt(request.getParameter("productId").replaceAll("/", ""));
		String seasonName = request.getParameter("productName");*/
		
		JRHtmlExporter exporter = null;
		JRPdfExporter pdfExporter = null;
		// Creating SBReport directory in usr folder
		ReportUtil.createSBReportDirectory();



		String pdfRequired= ReportConfig.getProperty("pdfrequired");	

		File fileName = null;

		if(pdfRequired.equalsIgnoreCase("Y")){
			if("BL".equals(loanType)) {
				fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
						+ ReportConfig.getProperty("Borrowing-NPA-Report-PDF-FileName"));
			} else {
				fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
						+ ReportConfig.getProperty("Borrowing-NPA-Report-PDF-FileName"));
			}
		} else {
			if("BL".equals(loanType)) {
				fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
						+ ReportConfig.getProperty("Borrowing-NPA-Report-Txt-FileName"));
			} else {
				fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
						+ ReportConfig.getProperty("Borrowing-NPA-Report-Txt-FileName"));
			}
		}

		String requiredFormat=null;

		if(pdfRequired.equalsIgnoreCase("Y")){
			requiredFormat="pdf";
		}else{
			requiredFormat="html";
			HttpSession session = request.getSession();
			session.setAttribute("txtFileName", fileName.getPath());
			session.setAttribute("outDatName", ReportConfig.getProperty("Borrowing-NPA-Report-Bat-FileName"));
			session.setAttribute("reportName", ReportConfig.getProperty("Borrowing-NPA-Report-Txt-FileName"));
		}



		//fileName = ReportUtil.getFileName(fileName);
		HttpSession session = request.getSession();
		session.setAttribute("txtFileName", fileName.getPath());
		Map<String, Object> map = null;
		String error = "Error Occured While Report Generation  ";
		try {
			map = getBorrowingsNpaReport(requiredFormat, loanType, pacsId, asOnDate, reportType, fileName,businessDate,userName,bankName,excludeStndLoan);
		} catch (Exception e1) {
			e1.printStackTrace();
			printWriter.println(error);
		}
		
		if (map != null)  {
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
					if("BL".equals(loanType)) {
						filename =  PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport"+ ReportConfig.getProperty("Borrowing-NPA-Report-PDF-FileName");
					} else {
						filename = PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport"+ ReportConfig.getProperty("Borrowing-NPA-Report-PDF-FileName");
					}

					File file = new File(filename);
					FileInputStream in = new FileInputStream(file);
					ServletOutputStream out1=response.getOutputStream();
					byte[] buf = new byte[4096];
					int count = 0;
					while ((count = in.read(buf)) >= 0) {
						out1.write(buf, 0, count);
					}

					in.close();
					out1.close();
				}

			}else{
				if (exporter == null) {		
					printWriter.println(error);
				}
				else {
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

	public Map<String, Object> getBorrowingsNpaReport(String requiredFormat, String loanType, Integer pacsId, String asOnDate, String reportType, File filename, String businessDate,String userName,String bankName,String excludeStndLoan) throws ParseException {
		logger.info("Start: Calling getBorrowingsNpaReport()");

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("bankName", bankName);
		Pacs pac = null;


		if (pacsId != null) {
			IPacsDAO pDao = KLSDataAccessFactory.getPacsDAO();
			pac = pDao.getPacs(pacsId);
			parameters.put("pacsName", pac.getName());
			parameters.put("address",pac.getLocation());
		}
		if (pac != null) {
		parameters.put("pacsName", pac.getName());
		parameters.put("branchName", pac.getBranch().getName());
		}
		else {
			parameters.put("pacsName", "All");
			parameters.put("branchName", "All");
		}
		parameters.put("businessDate", businessDate);
		parameters.put("asOnDate",asOnDate);
		parameters.put("userName", userName);
		if("BC".equalsIgnoreCase(loanType.trim())){
			parameters.put("loanType", "ST Loans");
		}else{
			parameters.put("loanType", "MT/LT Loans");
		}
		
		try {
			
			List<NPAReportData> list = KLSReportSeriveFactory.getNPAReportService().getBorrowingsNPAReportData( asOnDate, loanType, reportType,excludeStndLoan);
			if (list.isEmpty())
				return null;
			logger.info("npa data :" + list.size());
			parameters.put("totalNumOfLoans", list.size());

			if (requiredFormat.equals("pdf")) {

				JasperPrint jasperPrint = null;
				if("BL".equals(loanType)) {
					jasperPrint =	ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
							.getProperty("Borrowing-NPA-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
				} else {
					jasperPrint =	ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
							.getProperty("Borrowing-NPA-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
				}

				JRPdfExporter pdfexporter = new JRPdfExporter();
				pdfexporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);

				if("BL".equals(loanType)) {
					JasperExportManager.exportReportToPdfFile(jasperPrint, PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
							+ ReportConfig.getProperty("Borrowing-NPA-Report-PDF-FileName"));
				} else {
					JasperExportManager.exportReportToPdfFile(jasperPrint, PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
							+ ReportConfig.getProperty("Borrowing-NPA-Report-PDF-FileName"));
				}
				HashMap<String, Object> myMap = new HashMap<String, Object>();
				myMap.put("exporter", pdfexporter);

				return myMap;
			}

			if (requiredFormat.equals("html")) {
				JasperPrint jasperPrint = null;
				if("BL".equals(loanType)) {
					jasperPrint =	ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
							.getProperty("Borrowing-NPA-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
				} else {
					jasperPrint =	ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
							.getProperty("Borrowing-NPA-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
				}

				Map<String, List<String>> map = new HashMap<String, List<String>>();

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
				ReportUtil.exportText(160, 66, jasperPrint, filename.toString());

				String footerHtml = null;
				if("BL".equals(loanType)) {
					footerHtml = ReportUtil.getFastPrint(map, ReportConfig.getProperty("Borrowing-npa-FPReport-JSP"), true, null);
				} else {
					footerHtml = ReportUtil.getFastPrint(map, ReportConfig.getProperty("Borrowing-npa-FPReport-JSP"), true, null);
				}

				exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, footerHtml);

				HashMap<String, Object> myMap = new HashMap<String, Object>();
				myMap.put("exporter", exporter);
				return myMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error:Inside getBorrowingsNpaReport");
			throw new KlsReportRuntimeException("Problem occured while generating report");
		}
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
		String txtName=ReportUtil.writeToFile(ReportConfig.getProperty("Borrowing-NPA-Report-Bat-FileName"), printerCommand,
				txtFileName);
		logger.info("End: inside callFastPrint() ");
		return txtName;

	}
	
	public static String saveToFile(String txtFileName) {
		logger.info("Start: inside saveToFile() ");
		String txtName=ReportUtil.saveToFile(ReportConfig.getProperty("Borrowing-NPA-Report-Bat-FileName"), "",
				txtFileName);
		logger.info("End: inside saveToFile() ");
		return txtName;

	}


}
