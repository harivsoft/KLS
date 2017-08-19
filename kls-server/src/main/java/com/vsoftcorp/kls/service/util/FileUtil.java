package com.vsoftcorp.kls.service.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.jboss.resteasy.util.Base64;

public class FileUtil {

	private static Logger logger = Logger.getLogger(FileUtil.class);

	public static String filesLocation = PropertiesUtil
			.getDocumentProperty("filesLocation");
	
	private static File outputDir;
	
	public static String writeFile(String completeImageData) throws Exception {
		outputDir = new File(filesLocation);
		if(!outputDir.exists()){
			outputDir.mkdir();
		}
		logger.info("FileUtil write file");
		String fileName = null;
		FileOutputStream out = null;
		try {
			if (completeImageData != null
					&& !completeImageData.trim().isEmpty()) {
				String fileType = completeImageData.substring(
						completeImageData.indexOf(":") + 1,
						completeImageData.indexOf(";"));
				String imageDataBytes = completeImageData
						.substring(completeImageData.indexOf(",") + 1);
				InputStream stream = new ByteArrayInputStream(
						Base64.decode(imageDataBytes.getBytes()));
				fileName = BusinessKeyGenerator.generateProductCode() + "."
						+ fileType.split("/")[1];
				File fileDir = new File(filesLocation + fileName);
				out = new FileOutputStream(fileDir);
				byte[] buffer = new byte[2000000];
				for (int n; (n = stream.read(buffer)) != -1;)
					out.write(buffer, 0, n);
				out.flush();
				logger.info("completeImageData.contains ::"
						+ completeImageData.contains(":"));
			}
		} catch (Exception ex) {
			logger.error("Error While Saving File");
			ex.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return fileName;
	}

	public static String readFile(String fileName) throws Exception {
		String imageDataString = null;
		FileInputStream fis = null;
		String fileType = null;
		try {
			File file = new File(filesLocation + fileName);

			fis = new FileInputStream(file);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[2000000];

			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
			}

			byte[] bytes = bos.toByteArray();
			imageDataString = Base64.encodeBytes(bytes);
			fileType = URLConnection.guessContentTypeFromName(file.getName());
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return "data:" + fileType + ";base64," + imageDataString;
	}

	public static String deleteFile(String fileName) throws Exception {
		FileInputStream fis = null;
		try {
			File file = new File(filesLocation + fileName);
			logger.info("filesLocation = " + filesLocation + fileName);
			logger.info("" + file.getPath());
			file.delete();

		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error While File Deleting : " + ex.getCause();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return "File Deleted Successfully";
	}
	public static File getEncodedFile(String decodedString) throws Exception {
		outputDir = new File(filesLocation);
		if(!outputDir.exists()){
			outputDir.mkdir();
		}
		logger.info("FileUtil write file");
		String fileName = null;
		FileOutputStream out = null;
		File fileDir =null;
		try{
			if(decodedString != null && !decodedString.trim().isEmpty()){			
				String fileType = decodedString.substring(decodedString.indexOf(":")+1, decodedString.indexOf(";"));		
				String imageDataBytes = decodedString.substring(decodedString.indexOf(",")+1);
				InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes()));
				fileName = generateProductCode()+"."+fileType.split("/")[1];
				fileDir = new File(filesLocation+fileName);			
				out = new FileOutputStream(fileDir);
				byte[] buffer = new byte[2000000];  	
		        for (int n; (n = stream.read(buffer)) != -1; )   
		        	out.write(buffer, 0, n);  
				out.flush();
				logger.info("Exccel.contains ::"+decodedString.contains(":"));
			}
		} catch (Exception ex){
			logger.error("Error While Saving File");
			ex.printStackTrace();
		}
		finally{
			if(out != null){
				out.close();				
			}
		}
		return fileDir;
	}
	public static String generateProductCode() {
		long currentDateTime = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(currentDateTime);
		return String.valueOf(currentDateTime);
	}
}
