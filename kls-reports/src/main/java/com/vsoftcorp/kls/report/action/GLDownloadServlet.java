package com.vsoftcorp.kls.report.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.service.util.PropertiesUtil;

public class GLDownloadServlet extends HttpServlet {

	/**
	 * @skeleti
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(GLDownloadServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("Start: Inside GLDownload class ");
		Integer pacs_id = Integer.parseInt(request.getParameter("pacs_id"));
		String isBankUser = request.getParameter("isBankUser");
		System.out.println("isBankUser :" + isBankUser);
		logger.info("pacs_id : " + pacs_id);
		String date = request.getParameter("GLDate");
		date = date.replaceAll("/", "-");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			// bankgl_03-06-2016.xls
			String filename = null;

			if (isBankUser.equalsIgnoreCase("true"))
				filename = "/bankgl_" + date + ".xls";
			else
				filename = "/pacsgl_" + pacs_id + "_" + date + ".xls";
			String filepath = PropertiesUtil.getDocumentProperty("glDownload");
			logger.info("GL Path: " + filepath + filename);
			java.io.FileInputStream fileInputStream = new java.io.FileInputStream(filepath + filename);
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			int i;
			while ((i = fileInputStream.read()) != -1) {
				out.write(i);
			}
			fileInputStream.close();
			out.close();
			logger.info("END: End of GLDownload class) ");
		} catch (FileNotFoundException e) {
			logger.info("GL Extact Not Generated to this Pac On the Selected Date");
			e.printStackTrace();
			response.setContentType("text/html");
			out.println("GL Extact Not Generated to this Pac On the Selected Date");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
