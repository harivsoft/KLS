package com.vsoftcorp.kls.gl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vsoftcorp.kls.business.util.PropertiesUtil;

public class GLExtractFile {

	private static Logger logger = Logger.getLogger(GLExtractFile.class);

	private OutputStream fileOutPutStream;

	private File outputFile;
	
	private File outputDir;
	
	private char NEW_LINE = '\n';

	private char CARRIAGE_RETURN = '\r';

	/**
	 * constructor opens a outputstream.The file name is in the format
	 * "yyyyMMddhhmmss" of date.(start date time_end date time.)
	 * 
	 * @param theFileName
	 *            the output file name.
	 */
	public GLExtractFile(final String fileName) {

		logger.info("Start: creating the file in the directory given in properties file.");
		try {
			final String outputFileName = fileName.trim();
			String fileDirectory = PropertiesUtil.getProperty("filesLocation")+"GLExtract/";
			
			logger.info(" fileDirectory : " + fileDirectory);
			outputDir = new File(fileDirectory);
			if(!outputDir.exists()){
				outputDir.mkdir();
			}
			outputFile = new File(fileDirectory + outputFileName);
			
			fileOutPutStream = new FileOutputStream(outputFile);
			if (!outputFile.exists()) {
				outputFile.createNewFile();
			}
			logger.info(" file absolute path : " + outputFile.getAbsolutePath());
		} catch (Exception excp) {
			logger.error(" file cannot be created.");
			throw new RuntimeException("file cannot be created.");
		}
		logger.info("End: creating the file in the directory given in properties file.");
	}

	/**
	 * constructor opens a outputstream.The file name is in the format
	 * "yyyyMMddhhmmss" of date.(start date time_end date time.)
	 * 
	 * @param theFileName
	 *            the output file name.
	 */
	public void GLExtractFileRDY(final String theFileName) {

		logger.info("Start: creating the file in the directory given in properties file in GLExtractFileRDY() method.");
		try {
			final String outputFileName = theFileName.trim();
			String fileDirectory = PropertiesUtil.getProperty("FILE_DIRECTORY");
			logger.info(" fileDirectory : " + fileDirectory);
			outputFile = new File(fileDirectory + outputFileName);
			fileOutPutStream = new FileOutputStream(outputFile);
		} catch (final FileNotFoundException fileExcp) {
			logger.error("File Not Found :: " + theFileName);
			throw new RuntimeException("File Not Found", fileExcp.getCause());
		}
		logger.info("End: creating the file in the directory given in properties file in GLExtractFileRDY() method.");
	}

	/**
	 * 
	 * @param workbook
	 */
	public void write(final XSSFWorkbook workbook) {

		logger.info("Start: writing to the file in write() method.");
		try {
			workbook.write(fileOutPutStream);
			fileOutPutStream.close();
			fileOutPutStream.flush();
		} catch (final IOException ioExcp) {
			logger.error("Unable to write in to the file Stream :: " + fileOutPutStream);
			throw new RuntimeException("Unable to write in to the file Stream", ioExcp.getCause());
		}
		logger.info("End: writing to the file in write() method.");
	}

	public void write(final String theOutputRecord) {

		logger.info("Start: writing to the file in write() method.");
		try {
			byte[] b = (theOutputRecord + CARRIAGE_RETURN + NEW_LINE).getBytes();
			fileOutPutStream.write(b);
			fileOutPutStream.flush();
		} catch (final IOException ioExcp) {
			logger.error("Unable to write in to the file Stream :: " + fileOutPutStream);
			throw new RuntimeException("Unable to write in to the file Stream", ioExcp.getCause());
		}
		logger.info("End: writing to the file in write() method.");
	}

	public void renameTo(String theNewFileName) {

		logger.info("Start: renaming to the new file name in renameTo() method.");
		try {
			File newOutputFile = new File(theNewFileName);
			fileOutPutStream.close();
			boolean success = outputFile.renameTo(newOutputFile);
			if (!success)
				throw new RuntimeException("Unable to rename  the file  :: " + getName());
		} catch (IOException ioExcp) {
			logger.error("Unable to rename  the file  :: " + getName());
			throw new RuntimeException("Unable to rename  the file", ioExcp.getCause());
		}
		logger.info("End: renaming to the new file name in renameTo() method.");
	}

	public String getName() {
		return outputFile.getName();
	}

}
