package com.vsoftcorp.kls.report.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;

import com.vsoftcorp.kls.report.factory.KLSReportSeriveFactory;
import com.vsoftcorp.kls.report.service.data.InconsistencyConsistencyData;
import com.vsoftcorp.kls.report.util.Report;
import com.vsoftcorp.kls.report.util.ReportConfig;
import com.vsoftcorp.kls.report.util.ReportUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;

public class InconsistencyReportActionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(InconsistencyReportActionServlet.class);

	public InconsistencyReportActionServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

		List<InconsistencyConsistencyData> list = KLSReportSeriveFactory.getInconsistencyReportService().getInConsistencyReport();
		PrintWriter printWriter = response.getWriter();

		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("name", "");// no use

			JasperPrint jasperPrint = ReportUtil.getJasperPrint(
					ReportUtil.class.getResourceAsStream(ReportConfig.getProperty("Inconsistency-Report-Jrxml")),
					new JRBeanCollectionDataSource(list), parameters);
			// Creating SBReport directory in usr folder
			ReportUtil.createSBReportDirectory();
			File fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
					+ ReportConfig.getProperty("Inconsistency-Report-Txt-FileName"));
			fileName = ReportUtil.getFileName(fileName);
			HttpSession session = req.getSession();
			session.setAttribute("txtFileName", fileName.getPath());
			session.setAttribute("outDatName", ReportConfig.getProperty("Inconsistency-Report-Bat-FileName"));
			session.setAttribute("reportName", ReportConfig.getProperty("Inconsistency-Report-Txt-FileName"));
			JRHtmlExporter exporter = ReportUtil.getExportHtm(jasperPrint);
			exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, JRHtmlExporterParameter.SIZE_UNIT_PIXEL);
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			List<String> list1 = new ArrayList<String>();
			list1.add("");
			map.put("name", list1);
			exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);

			exporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);

			exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);

			exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

			exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.FALSE);

			exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(16));

			exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(16));

			exporter.setParameter(JRHtmlExporterParameter.OFFSET_X, -300);
			exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 50);
			exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 120);

			// exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER,printWriter);
			exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
			ReportUtil.exportText(130, 80, jasperPrint, fileName.toString());

			String footerHtml = ReportUtil.getFastPrint(map, ReportConfig.getProperty("Inconsistency-FPReport-JSP"), true, null);

			exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, footerHtml);
			String error = " Error Occured While Report Generation  ";
			HashMap<String, Object> myMap = new HashMap<String, Object>();
			myMap.put("exporter", exporter);
			JRHtmlExporter jrHtmlExporter = null;
			if (myMap != null && !list.isEmpty()) {
				jrHtmlExporter = (JRHtmlExporter) myMap.get("exporter");

				if (myMap.containsKey(Report._ERROR)) {
					error = "<b>" + (String) myMap.get(Report._ERROR) + "</b>";
				}
				if (jrHtmlExporter == null) {
					printWriter.println(error);
				} else {
					response.setContentType("text/html");
					jrHtmlExporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER, response.getWriter());
					try {
						jrHtmlExporter.exportReport();
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			} else {
				
				printWriter.println("Data not found for Report");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		String txtName=ReportUtil.writeToFile(ReportConfig.getProperty("Inconsistency-Report-Bat-FileName"), printerCommand,
		txtFileName);
		logger.info("End: inside callFastPrint() ");
		return txtName;
	
	}
}
