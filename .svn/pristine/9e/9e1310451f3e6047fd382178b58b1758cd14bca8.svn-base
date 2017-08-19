package com.vsoftcorp.kls.report.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
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

import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.factory.KLSReportSeriveFactory;
import com.vsoftcorp.kls.report.service.data.ScheduleVsGLBalanceReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.util.Report;
import com.vsoftcorp.kls.report.util.ReportConfig;
import com.vsoftcorp.kls.report.util.ReportUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.kls.userexceptions.AccountDoesntExistsException;
import com.vsoftcorp.time.Date;


public class ScheduleVsGLBalanceActionServlet extends HttpServlet{
	
		private static final long serialVersionUID = 8249361955406450693L;

		private static final Logger logger = Logger.getLogger(ScheduleVsGLBalanceActionServlet.class);

		
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doPost(request, response);
		}
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			logger.info("start: calling getAccountStatementReport() in doPost()");
			PrintWriter printWriter =null;
			
			String error = "Error Occured While Report Generation  ";
			try {
				String pacsId = request.getParameter("txtPacsId");
				logger.info("pacsId:::" +pacsId);
				String toDateString =request.getParameter("txtAsOnDate");
				String status =request.getParameter("txtStatus");
				logger.info("status:::::" +status);
				String bankName = request.getParameter("bankName");
				String pacName = request.getParameter("pacName");
				logger.info("pacName:::::" +pacName);
				logger.info("bankName---" + bankName);
				String userName = request.getParameter("userName");
				String businessDate = request.getParameter("businessDate");
				logger.info("userName : " + userName);
				logger.info("parameter values:pacsId::"+pacsId+"toDate::"+toDateString);
				JRHtmlExporter exporter = null;
				JRPdfExporter pdfExporter = null;
				// Creating SBReport directory in usr folder
				ReportUtil.createSBReportDirectory();
				
				String pdfRequired= ReportConfig.getProperty("pdfrequired");
				File fileName = null;
				if(pdfRequired.equalsIgnoreCase("Y")){
					fileName= new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
							+ ReportConfig.getProperty("ScheduleBal-Report-PDF-FileName"));	
				} else  {
					
					fileName = new File(PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
						+ ReportConfig.getProperty("ScheduleBal-Report-Txt-FileName"));
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
					session.setAttribute("outDatName", ReportConfig.getProperty("ScheduleBal-Report-Bat-FileName"));
					session.setAttribute("reportName", ReportConfig.getProperty("ScheduleBal-Report-Txt-FileName"));
				}
				
				
				fileName = ReportUtil.getFileName(fileName);
				HttpSession session = request.getSession();
				session.setAttribute("txtFileName", fileName.getPath());
				Map<String, Object> map = getScheduleVsBalanceReport(requiredFormat,  toDateString, printWriter, pacsId,fileName,bankName,userName,businessDate,status);
//				if (map != null) {
//					exporter = (JRHtmlExporter) map.get("exporter");
//
//					if (map.containsKey(Report._ERROR)) {
//						error = "<b>" + (String) map.get(Report._ERROR) + "</b>";
//					}
//					if (exporter == null) {
//						printWriter.println(error);
//					} else {
//						response.setContentType("text/html");
//						exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER, response.getWriter());
//						try {
//							exporter.exportReport();
//						} catch (JRException e) {
//							printWriter.println(error);
//						}
//
//					}
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
							
							 String filename=PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport"+ ReportConfig.getProperty("ScheduleBal-Report-PDF-FileName");
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
				}else {
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

		public Map<String, Object> getScheduleVsBalanceReport(String reportType,  String toDateString, PrintWriter printWriter,String pacsId,File fileName,String bankName,String userName,String businessDate,String status) {
			logger.info("Start: Calling AccountStatementReportService in getAccountStatementReport()");
			try {
				Pacs pac = null;
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				//parameters.put("bankName", bankName);
				if (pacsId != null) {
					Integer pacId = Integer.parseInt(pacsId);
					IPacsDAO pDao = KLSDataAccessFactory.getPacsDAO();
					pac = pDao.getPacs(pacId);
				}
				
				parameters.put("pacName", pac != null ? pac.getName() : "");
				parameters.put("userName", userName);
				parameters.put("bankName", bankName);
				parameters.put("businessDate", ReportUtil.getUtilDateByString(businessDate));
				parameters.put("asOnDate", toDateString);
				
				parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);

				//Date fromDate = Date.valueOf(DateUtil.getFormattedDate(fromDateString));
				Date toDate = Date.valueOf(DateUtil.getFormattedDate(toDateString));
				
				
				if (reportType.equals("pdf")) {
					int pacs;
					if (pacsId != null)
						pacs = Integer.parseInt(pacsId);
					else {
						System.out.println("Pacs Id is null " + pacsId);
						return null;
					}
					
					List<ScheduleVsGLBalanceReportData> list = KLSReportSeriveFactory.getScheduleVsGLBalanceReportService().getScheduleVsGLBalance(pacsId,toDate,status);

					logger.info("StockRegister Item list Size::::" + list.size());
					if (list.isEmpty())
						return null;
					JasperPrint jasperPrint = ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
							.getProperty("ScheduleBal-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
					
					
					JRPdfExporter pdfexporter = new JRPdfExporter();
					pdfexporter.setParameter(JRExporterParameter.JASPER_PRINT,
							jasperPrint);
					
	                JasperExportManager.exportReportToPdfFile(jasperPrint, PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport/"
							+ ReportConfig.getProperty("ScheduleBal-Report-PDF-FileName"));
	                HashMap<String, Object> myMap = new HashMap<String, Object>();
					myMap.put("exporter", pdfexporter);

					return myMap;
				}

				if (reportType.equals("html")) {
					List<ScheduleVsGLBalanceReportData> list = KLSReportSeriveFactory.getScheduleVsGLBalanceReportService().getScheduleVsGLBalance(pacsId,toDate,status);
					logger.info("Account statement list::::" + list);
					if (list.isEmpty())
						return null;
					/*	{
						list.add(null);
						parameters.put("data", "No Record Found");
						}*/
						

					ScheduleVsGLBalanceReportData accountStatementReportData = list.get(0);

					parameters.put("Loan Type", accountStatementReportData.getLoanType());
					// list.remove(0);
					Iterator<ScheduleVsGLBalanceReportData> iterator = list.iterator();

					/*while (iterator.hasNext()) {
						ScheduleVsGLBalanceReportData data = (ScheduleVsGLBalanceReportData) iterator.next();

						/*if (data.getLocNo() == null)
							iterator.remove();
					}*/
					JasperPrint jasperPrint = ReportUtil.getJasperPrint(ReportUtil.class.getResourceAsStream(ReportConfig
							.getProperty("ScheduleBal-Report-Jrxml")), new JRBeanCollectionDataSource(list), parameters);
					Map<String, List<String>> map = new HashMap<String, List<String>>();

					/*List<String> list1 = new ArrayList<String>();
					list1.add(bankName);
					map.put("bankName", list1);*/

					List<String> list2 = new ArrayList<String>();
					list2.add(pac.getName());
					map.put("pacName", list2);

					/*List<String> list3 = new ArrayList<String>();
					list3.add(fromDateString);
					map.put("fromDate", list3);*/

					List<String> list4 = new ArrayList<String>();
					list4.add(toDateString);
					map.put("toDate", list4);

					/*
					 * List<String> list5 = new ArrayList<String>();
					 * list5.add(addressData.getLine1()); map.put("location",
					 * list5);
					 */

					/*List<String> list6 = new ArrayList<String>();
					list6.add(village.getName());
					map.put("villageName", list6);

					List<String> list7 = new ArrayList<String>();
					list7.add(includeInterest);
					map.put("includeInterest", list7);

					List<String> list8 = new ArrayList<String>();
					list8.add(includeCharges);
					map.put("includeCharges", list8);

					List<String> list9 = new ArrayList<String>();
					list9.add(includePerticulars);
					map.put("includePerticulars", list9);

					List<String> list10 = new ArrayList<String>();
					list10.add(accNo);
					map.put("accNo", list10);*/

				/*	List<String> list11 = new ArrayList<String>();
					list11.add(customer.getCustomerId().toString());
					map.put("customerId", list11);*/

					List<String> list12 = new ArrayList<String>();
					list12.add(accountStatementReportData.getSumOfOpeningBal());
					map.put("openingBalance", list12);

					/*List<String> list13 = new ArrayList<String>();
					list13.add(customer.getName());
					map.put("custName", list13);

					List<String> list14 = new ArrayList<String>();
					list14.add(addressData.getMobileNo());
					map.put("contactNo", list14);

					List<String> list15 = new ArrayList<String>();
					list15.add(addressData.getLine1());
					map.put("address", list15);

					List<String> list16 = new ArrayList<String>();
					list16.add(shareBalance);
					map.put("shareBalance", list16);*/

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

					String footerHtml = ReportUtil.getFastPrint(map, ReportConfig.getProperty("ScheduleBal-FPReport-JSP"), true, null);

					exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, footerHtml);

					HashMap<String, Object> myMap = new HashMap<String, Object>();
					myMap.put("exporter", exporter);
					return myMap;
				}
			} catch (AccountDoesntExistsException e) {
				e.printStackTrace();
				logger.error("Error:Account number does not exist");
				throw new AccountDoesntExistsException("Account number does not exist:");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error:Inside getAccountStatementReport method");
				throw new KlsReportRuntimeException("Error while showing Account statement report:", e.getCause());
			}
			logger.info("End: Calling AccountStatementReportService in getAccountStatementReport()");

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
			String txtName = ReportUtil.writeToFile(ReportConfig.getProperty("ScheduleBal-Report-Bat-FileName"), printerCommand, txtFileName);
			logger.info("End: inside callFastPrint() ");
			return txtName;

		}
	}



