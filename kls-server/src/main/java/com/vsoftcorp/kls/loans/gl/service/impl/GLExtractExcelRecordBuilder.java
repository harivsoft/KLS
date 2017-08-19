/**
 * 
 */
package com.vsoftcorp.kls.loans.gl.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vsoftcorp.kls.gl.GLEntrySummary;

/**
 * @author a9152
 * 
 */
public class GLExtractExcelRecordBuilder {

	private List<GLEntrySummary> glEntrySummaryList;

	private static Logger logger = Logger.getLogger(GLExtractExcelRecordBuilder.class);

	public GLExtractExcelRecordBuilder(List<GLEntrySummary> glEntrySummaryList) {

		this.glEntrySummaryList = glEntrySummaryList;
	}

	/**
	 * 
	 * @return
	 */
	public XSSFWorkbook constructExcelGLRecord() {

		logger.info("Start: writing gl entries into the excel file  from the current transaction/transaction history tables in constructExcelGLRecord() method.");
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("GL Data");
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		for (int i = 0; i < glEntrySummaryList.size(); i++) {
			// This data needs to be written (Object[])
			GLEntrySummary glEntrySummary = glEntrySummaryList.get(i);
			data.put(new Integer(i + 1).toString(),
					new Object[] { glEntrySummary.getAccountNumber(), glEntrySummary.getTransactionAmount(),
							glEntrySummary.getCrdr().toString(),glEntrySummary.getTransType() ,glEntrySummary.getRemarks() });

		}

		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		logger.info("End: writing gl entries into the excel file  from the current transaction/transaction history tables in constructExcelGLRecord() method.");
		return workbook;
	}
}
