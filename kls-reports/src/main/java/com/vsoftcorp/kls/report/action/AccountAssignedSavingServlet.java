package com.vsoftcorp.kls.report.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountPropertyDAO;
import com.vsoftcorp.kls.report.factory.KLSReportSeriveFactory;
import com.vsoftcorp.kls.report.service.data.AccountInfoData;
import com.vsoftcorp.kls.report.service.data.SBAccountAssignedData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.util.Report;
import com.vsoftcorp.kls.report.util.ReportConfig;
import com.vsoftcorp.kls.report.util.ReportUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;

public class AccountAssignedSavingServlet extends HttpServlet 
{
	
	private static final long serialVersionUID = -2156179601256068005L;
	private static final Logger logger = Logger.getLogger(AccountAssignedSavingServlet.class);

	public AccountAssignedSavingServlet(){
		
	}
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	
	{
		logger.info("Start: calling getAccountInfoIreport() inside doPost() ");
	PrintWriter printWriter = null;
	String error = "Error Occured While Report Generation  ";
	try{
	Integer pacsId = Integer.parseInt(request.getParameter("txtPacsId").replaceAll("/", ""));
	logger.info("pacs id---" + pacsId);
	String Status = request.getParameter("txtStatus");
	logger.info("Status"+Status);
	String bankName = request.getParameter("bankName");
	logger.info("bankName---" + bankName);
	String userName = request.getParameter("userName");
	logger.info("userName : " + userName);
	JRHtmlExporter exporter = null;
	// Creating SBReport directory in usr folder
	ReportUtil.createSBReportDirectory();
	File fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
			+ ReportConfig.getProperty("SavingAccountStatus-Report-Txt-FileName"));

	fileName = ReportUtil.getFileName(fileName);
	HttpSession session = request.getSession();
	session.setAttribute("txtFileName", fileName.getPath());
	Map<String, Object> map = null;
	
	try{
		map = getAccountInfoIreport(pacsId,Status,bankName,userName,fileName);
	}
	catch (Exception e1) {
		e1.printStackTrace();
		printWriter.println(error);
	}
	if (map != null) {
		exporter = (JRHtmlExporter) map.get("exporter");

		if (map.containsKey(Report._ERROR)) {
			error = "<b>" + (String) map.get(Report._ERROR) + "</b>";
		}
		if (exporter == null) {
			printWriter.println(error);
		} else {
			response.setContentType("text/html");
			exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER, response.getWriter());
			try {
				exporter.exportReport();
			} catch (JRException e) {
				printWriter.println(error);
			}

		}
	} else {
		printWriter=response.getWriter();
		printWriter.println("Data not found for Report");
	}
	logger.info("End:  inside doPost() ");
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		printWriter.println(ex.getMessage());
		printWriter.println(error);
	}
}

public Map<String, Object> getAccountInfoIreport(Integer pacsId,String Status,String bankName,String userName, File filename) throws ParseException {
	logger.info("Start: Calling AccountInfoReportService in getAccountInfoIreport()");

	HashMap<String, Object> parameters = new HashMap<String, Object>();
	
	Pacs pac = null;

	if (pacsId != null) {

		IPacsDAO pDao = KLSDataAccessFactory.getPacsDAO();
		pac = pDao.getPacs(pacsId);
	}

	parameters.put("pacsName", pac.getName());
	parameters.put("bankName", bankName);
	parameters.put("userName", userName);
	parameters.put("Status",  Status);
	parameters.put("businessDate", ReportUtil.getUtilDateByString(null));
	try {
		 {
			List<SBAccountAssignedData> list = KLSReportSeriveFactory.getAccountAssignReportService().getAccountSavingStatus(pacsId,Status);
			if (list.isEmpty())
				return null;
			logger.info("AcclList:" + list);
			JasperPrint jasperPrint = ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
					.getProperty("SavingAccountStatus-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
			Map<String, List<String>> map = new HashMap<String, List<String>>();

			List<String> list2 = new ArrayList<String>();
		
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
			ReportUtil.exportText(160, 66, jasperPrint, filename.toString());
		
			String footerHtml = ReportUtil.getFastPrint(map, ReportConfig.getProperty("SavingAccountStatus-FPReport-JSP"), true, null);

			exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, footerHtml);

			HashMap<String, Object> myMap = new HashMap<String, Object>();
			myMap.put("exporter", exporter);
			return myMap;
		}
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("Error:Inside getAccountInfoIreport method");
		throw new KlsReportRuntimeException("Problem occured while generating report");
	}
	
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
	String txtName=ReportUtil.writeToFile(ReportConfig.getProperty("SavingAccountStatus-Report-Bat-FileName"), printerCommand,
	txtFileName);
	logger.info("End: inside callFastPrint() ");
	return txtName;
		
	}

}
