package com.vsoftcorp.kls.report.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vsoftcorp.kls.service.util.PropertiesUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ReportUtil {
	public static String filesLocation = PropertiesUtil.getDocumentProperty("filesLocation") + "SBReport";
	public static String appletPrintLocation = PropertiesUtil.getDocumentProperty("APPLET_PRINT_LOCATION");

	private static File outputDir;

	public static JRHtmlExporter getExportHtm(JasperPrint jasperPrint) throws Exception {
		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, "images");
		exporter.setParameter(JRHtmlExporterParameter.FRAMES_AS_NESTED_TABLES, false);
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
		return exporter;
	}

	public static String writeToFile(String outDatName, String printCmd, String outTxtName) {
		String tempFileName = null;
		try {
			Process proc;
			Runtime rt = Runtime.getRuntime();
			File f = null;
			FileOutputStream fot = null;
			/*******************/
			String[] strArr = outDatName.split("\\.", 2);
			tempFileName = strArr[0];
			tempFileName = tempFileName + ".txt";
			//String command = "awk 'NF' " + outTxtName +" > " + tempFileName ;

			String[] command = { "/bin/sh", "-c", "awk NF " + outTxtName + " > " + appletPrintLocation + tempFileName };

			proc = rt.exec(command);
			proc.waitFor();
			InputStream in = proc.getInputStream();

			String filePath = appletPrintLocation + tempFileName;
			StringBuilder header = new StringBuilder("\\n\\n\\n\\n\\n");
			String line = "";
			int headerLineCount = 5;

			BufferedReader br = new BufferedReader(new FileReader(filePath));

			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				header.append(line + "\\n");
				headerLineCount++;
				if (line.contains("S.NO")) {
					if(br.readLine().contains("------")){
						header.append("    ----------------------------------------------------------------------------------------------------------------------------\\n");
					   // headerLineCount++;
					}
					break;
				}

			}

			proc = rt.exec("sync");
			proc.waitFor();
			in = proc.getInputStream();

			String[] command1 = {
					"/bin/sh",
					"-c",
					"awk " + "'" + "{ if ((NR % " + (74 - headerLineCount) + ") == 0) printf(\"" + header.toString() + "\"); print; } " + "' "
							+ filePath + " > " + filePath + "1" };

			proc = rt.exec(command1);
			proc.waitFor();
			in = proc.getErrorStream();
			String s = "";

			//InputStream input = proc.getErrorStream();
			BufferedInputStream buffer = new BufferedInputStream(in);
			BufferedReader commandResult = new BufferedReader(new InputStreamReader(buffer));
			String line1 = "";
			try {
				while ((line1 = commandResult.readLine()) != null) {
					s += line1 + "\n";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(s);

			StringBuilder error = new StringBuilder("");
			for (int i = 0; i < headerLineCount; i++) {
				error.append("\\n");
			}
			String[] command3 = {
					"/bin/sh",
					"-c",
					"awk " + "'" + "{ if (NR == " + (74 - headerLineCount) + " ) printf(\"" + error.toString() + "\"); print; } " + "' " + filePath
							+ "1" + " > " + filePath + "2" };

			proc = rt.exec(command3);
			proc.waitFor();
			in = proc.getInputStream();

			/* for Batch file generation
			 f = new File(filesLocation+outDatName);
			 f.createNewFile();            
			 fot = new FileOutputStream(f);
			 StringBuffer s = new StringBuffer(printCmd + " " + tempFileName);
			 String ss = s.toString();
			 byte[] b = ss.getBytes();
			 fot.write(b);
			 fot.close();
			 outDatName=filesLocation+outDatName;
			 proc = rt.exec("sh " + outDatName);
			 in = proc.getInputStream();*/
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tempFileName + "2";
	}
	
	
	public static String writeToFile(String outDatName, String printCmd, String outTxtName,int headerLineCount) {
		String tempFileName = null;
		try {
			Process proc;
			Runtime rt = Runtime.getRuntime();
			File f = null;
			FileOutputStream fot = null;
			/*******************/
			String[] strArr = outDatName.split("\\.", 2);
			tempFileName = strArr[0];
			tempFileName = tempFileName + ".txt";
			//String command = "awk 'NF' " + outTxtName +" > " + tempFileName ;sed '1,13d'

			String[] command = { "/bin/sh", "-c", "awk NF " + outTxtName + " > " + appletPrintLocation + tempFileName };

			proc = rt.exec(command);
			proc.waitFor();
			
			String filePath = appletPrintLocation + tempFileName;
			StringBuilder header = new StringBuilder("\\n\\n\\n");
			//StringBuilder header = new StringBuilder("\\n\\n\\n\\n\\n");
			StringBuilder firstHeader=new StringBuilder("\\t");
			String line = "";
			//int headerLineCount = 5;
			int headingCount=headerLineCount;
			//headerLineCount=0;
			BufferedReader br = new BufferedReader(new FileReader(filePath));
            int headerDisplay=1;
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				header.append(line + "\\n");
				firstHeader.append(line + "\\n");
				headerLineCount++;
				headerDisplay++;
				if (headerDisplay>headingCount) {
					if(br.readLine().contains("------")){
						header.append("    ----------------------------------------------------------------------------------------------------------------------------\\n");
						firstHeader.append("    ----------------------------------------------------------------------------------------------------------------------------\\n");
					   // headerLineCount++;
					}
					break;
				}
			}
			System.out.println("the header line count is"+headerLineCount);
			System.out.println("the header is ..."+header);
			proc = rt.exec("sync");
			proc.waitFor();
			
			String[] command2 = { "/bin/sh", "-c", "sed '1,12d' " + filePath + " > " + filePath + "1" };

			proc = rt.exec(command2);
			proc.waitFor();
			
			filePath="";
			filePath=appletPrintLocation + tempFileName +"1";
			
			System.out.println("the file path is... "+filePath);
			
			InputStream in = proc.getInputStream();
			
			in = proc.getInputStream();
			
			//int count=0;
			
			
			//String[] command2={"/bin/sh" "-c" "awk" "'" printf(\"" + header.toString() + "\"); print;" 
			
			//System.out.println("the counter value is..."+count);
		
			String appletHeaderPath="";
			appletHeaderPath=appletPrintLocation + tempFileName +"3";
			
			
			String[] command3 = {
					"/bin/sh",
					"-c",
					"awk " + "'" + "{ if ((NR % "+(53)+") == 0) printf(\"" + header.toString() + "\"); print; } " + "' "
							+ filePath + " > " + appletHeaderPath  };

			proc = rt.exec(command3);
			proc.waitFor();
			in = proc.getErrorStream();

			String finalpath="";
			finalpath=appletPrintLocation + tempFileName +"2";

			String[] command1 = {
					"/bin/sh",
					"-c",
					"sed '1 i " + firstHeader.toString() + " ' "
							+ appletHeaderPath + " > " + finalpath  };
			
			//sed '1 i \\n\n\n\n\n\n\n TETSTSTS' memberProfileReport_0.txt>memberProfileReport_12.txt
			proc = rt.exec(command1);
			proc.waitFor();
			in = proc.getErrorStream();
						/*
						 
						 String s = "";

			//InputStream input = proc.getErrorStream();
			BufferedInputStream buffer = new BufferedInputStream(in);
			BufferedReader commandResult = new BufferedReader(new InputStreamReader(buffer));
			String line1 = "";
			try {
				while ((line1 = commandResult.readLine()) != null) {
					s += line1 + "\n";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(s);


						  
						  StringBuilder error = new StringBuilder("");
			for (int i = 0; i < headerLineCount; i++) {
				error.append("\\n");
			}
			String[] command3 = {
					"/bin/sh",
					"-c",
					"awk " + "'" + "{ if (NR == " + (74 - headerLineCount) + " ) printf(\"" + error.toString() + "\"); print; } " + "' " + filePath
							+ "1" + " > " + filePath + "2" };

			proc = rt.exec(command3);
			proc.waitFor();
			in = proc.getInputStream();*/

			/* for Batch file generation
			 f = new File(filesLocation+outDatName);
			 f.createNewFile();            
			 fot = new FileOutputStream(f);
			 StringBuffer s = new StringBuffer(printCmd + " " + tempFileName);
			 String ss = s.toString();
			 byte[] b = ss.getBytes();
			 fot.write(b);
			 fot.close();
			 outDatName=filesLocation+outDatName;
			 proc = rt.exec("sh " + outDatName);
			 in = proc.getInputStream();*/
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tempFileName + "2";
	}

	public static String saveToFile(String outDatName, String printCmd, String outTxtName) {
		
		String filePath="";
		try {
			filePath = writeToFile(outDatName, printCmd, outTxtName);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return appletPrintLocation+filePath;
	}
	
	
	
	public static JasperPrint getJasperPrint(InputStream inputStreamForReport, JRBeanCollectionDataSource ds, Map parameters) {
		try {
			//InputStream inputStreamForReport = null;
			JasperDesign jasperDesign = JRXmlLoader.load(inputStreamForReport);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

			// THIS HELPS IN CASE YOU WANT TO COMPILE A REPORT DYNAMICALLY !!
			// JasperCompileManager.compileReportToFile("sample.jrxml",
			// "sample.jasper");

			return jasperPrint;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void exportText(int width, int height, JasperPrint jasperPrint, String outputFileName) {
		try {

			JRTextExporter exporter = new JRTextExporter();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			//exporter.setParameter(JRTextExporterParameter.BETWEEN_PAGES_TEXT,"\f");
			exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, Float.valueOf(6));
			exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, Float.valueOf(12));
			exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, new Integer(120));
			exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, new Integer(80));

			exporter.setParameter(JRTextExporterParameter.BETWEEN_PAGES_TEXT, String.valueOf('\f'));

			// exporter.setParameter(JRTextExporterParameter.LINE_SEPARATOR, "\n");

			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);

			exporter.exportReport();

			//			exporter.setParameter(JRTextExporterParameter.BETWEEN_PAGES_TEXT,"\f");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void exportTextPageBreak(int width, int height, JasperPrint jasperPrint, String outputFileName) {
		try {
			JRTextExporter exporter = new JRTextExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);
			exporter.setParameter(JRTextExporterParameter.BETWEEN_PAGES_TEXT, "\f");

			exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, width);
			exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, height);

			exporter.exportReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getTextExporter(JasperPrint jasperPrint, String outputFileName) {

		try {

			JRExporter exporter = new JRTextExporter();
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);

			exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, Integer.valueOf(7));
			exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, Integer.valueOf(12));

			exporter.setParameter(JRTextExporterParameter.BETWEEN_PAGES_TEXT, String.valueOf('\f'));

			exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, jasperPrint.getPageWidth());

			exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, jasperPrint.getPageHeight());

			exporter.exportReport();

		} catch (JRException jre) {
			jre.printStackTrace();
		}
	}

	public static JasperPrint getJasperPrint(JasperReport jasperReport, JRBeanCollectionDataSource ds, Map parameters) {
		try {

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
			return jasperPrint;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getFastPrint(Map<String, List<String>> map, String fastPrintUrl, boolean reqMultiplePrinterSupport, String strBranchid) {

		String params = "";
		if (map != null && map.isEmpty() == false) {
			Set<String> set = map.keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String string = (String) iterator.next();
				List<String> list = map.get(string);
				Iterator<String> iterator2 = list.iterator();
				while (iterator2.hasNext()) {
					String string2 = (String) iterator2.next();
					if (params.contains("?")) {
						params += "&" + string + "=" + string2;
					} else {
						params += "?" + string + "=" + string2;
					}

				}
			}
		}

		String html = "";

		html += "<center><div id=\"bottomButtonsDiv\">";

		//html += "<span><a href=\"javascript:void(0)\" onclick=\"hideBottomDiv();window.print();dispalyBottomDiv();\" ><img border=0 src=\"../img/print.gif\" title=\"Print\" ></a></span>";
		html += "<br/><span><a href=\"javascript:void(0);\" onclick=\"fnSave();\" ><img border=0 src=\"images/Save.gif\" title=\"Save\" ></a></span>&nbsp;&nbsp;&nbsp;";
		html += "<span><a href=\"javascript:void(0);\" target=\"_blank\" onclick=\"callFastPrint();\" ><img border=0 src=\"images/Fastprint.gif\" title=\"Fastprint\" ></a></span>&nbsp;&nbsp;&nbsp;";
		// html+="<span><a href=\"javascript:void(0);\" onclick=\"fnSave();\" ><img border=0 src=\"../images/saveas.gif\" title=\"Save As\" ></a></span>";
		html += "<span><a href=\"javascript:void(0);\" onclick=\"fnClose();\" ><img border=0 src=\"images/close.gif\" title=\"Close\" ></a></span>";
		

		/*// Adding Multiple Printers..
		if (reqMultiplePrinterSupport) {
			try {
				CConPool db =new CConPool();
				ResultSet rs1=null;
				String sqlPrint ="Select printtype, printername, ipaddress, rpad(ipaddress,16,' ')||'/'||printtype from sbprintinfo where branchid= '"+strBranchid+"' and printnature in ('N','F')";
				 rs1 = db.executeSelectQuery(sqlPrint);
				html += "<span><select  name =\"SltPrinter\" id=\"SltPrinter\" ><option value=\"select\" >--Select--</option>";
				while (rs1.next()) {
					String printerType = rs1.getString("printtype");
					String printerName = rs1.getString(4);
					html += "<option value=\"" + printerName + "\">" + printerType
							+ "</option>";

				}
				rs1.close();
				html += "</select></span>";

			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		html += "<div></center>";

		html += "<iframe name=\"myframe\" height=\"0\" width=\"0\"></iframe>";
		if (reqMultiplePrinterSupport) {
			
			html += "<script type=\"text/javascript\"> function callFastPrint(){ myframe.location.href=\"view/reports/"+fastPrintUrl+params+"\";}";
			html += "function fnSave(){ myframe.location.href=\"view/reports/save.jsp"+params+"\";}"+"</script>";
//					+ "alert('fast print');var printerName = document.getElementById(\"SltPrinter\").value; if(printerName == 'select'){ alert('Select the Printer');return;}"
//					+ "var url=\"" + fastPrintUrl + params;
//			if (params.contains("?"))
//				html += "&printerName=\"+document.getElementById(\"SltPrinter\").value";
//			else
//				html += "?printerName=\"+document.getElementById(\"SltPrinter\").value";
			
		/*	html += " function fnClose() { 	window.close(); } function fnSave() { document.execCommand('SaveAs',true,file.html) ; }"
					+ "function hideBottomDiv(){document.getElementById('bottomButtonsDiv').style.display=\"none\"; }"
					+ "function dispalyBottomDiv(){document.getElementById('bottomButtonsDiv').style.display=\"block\" ; } " + "</script>";*/
		} else {
			html += "<script type=\"text/javascript\"> function callFastPrint(){ myframe.location.href=\"" + fastPrintUrl + params
					+ "\";alert('Creating File For Printing , Keep Prnter On');	}"
					+ " function fnClose() { 	window.close(); } function fnSave() { document.execCommand('SaveAs',true,file.html) ; }"
					+ "function hideBottomDiv(){document.getElementById('bottomButtonsDiv').style.display=\"none\"; }"
					+ "function dispalyBottomDiv(){document.getElementById('bottomButtonsDiv').style.display=\"block\" ; } " + "</script>";
		}

		return html;

	}

	public static String getFastPrint(Map<String, List<String>> map, String fastPrintUrl, String strBranchid) {
		return getFastPrint(map, fastPrintUrl, false, strBranchid);
	}

	public static void createSBReportDirectory() {
		File theDir = new File(filesLocation);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			try {
				theDir.mkdir();
			} catch (SecurityException se) {
				se.printStackTrace();
			}
		}
	}

	public static File getFileName(File fileName) {
		while (fileName.exists()) {
			outputDir = new File(filesLocation);
			if (!outputDir.exists()) {
				outputDir.mkdir();
			}
			String filNam[] = fileName.getName().split("_");
			String filName = filNam[1].replace(".", "_");
			String num[] = filName.split("_");
			int j = Integer.parseInt(num[0]);
			++j;
			fileName = new File(filesLocation + "/" + filNam[0] + "_" + j + "." + num[1]);
		}
		return fileName;
	}

	public static Date getUtilDateByString(String dateString) throws ParseException {
		//System date is showing in reports summery
		//DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return new Date(System.currentTimeMillis());
		//return df.parse(dateString);
	}
}
