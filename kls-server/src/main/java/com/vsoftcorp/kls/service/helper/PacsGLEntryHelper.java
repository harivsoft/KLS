package com.vsoftcorp.kls.service.helper;

import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.gl.GLEntrySummary;
import com.vsoftcorp.kls.gl.PacsGLEntry;

/**
 * 
 * @author a1605
 * 
 */
public class PacsGLEntryHelper {

	public static List<GLEntrySummary> getGlEntrySummarys(List<PacsGLEntry> pacsGLEntryList, String pacOrBank) {

		List<GLEntrySummary> glEntrySummarylist = new ArrayList<GLEntrySummary>();
		GLEntrySummary glEntrySummary = null;
		for (PacsGLEntry pacsGLEntry : pacsGLEntryList) {
			glEntrySummary = new GLEntrySummary();
			glEntrySummary.setAccountNumber(pacsGLEntry.getAccountNo());
			System.out.println("pacsGLEntry.getTransactionAmount()=="+pacsGLEntry.getTransactionAmount());
			if(pacsGLEntry.getTransactionAmount()!=null)
			glEntrySummary.setTransactionAmount(pacsGLEntry.getTransactionAmount().getMoney()
					.getAmount().toString());
			//glEntrySummary.setCrdr(pacsGLEntry.getCrDr().toString());
			if (pacsGLEntry.getCrDr() == -1 && pacOrBank.equalsIgnoreCase("bank")) {
				glEntrySummary.setCrdr("D");
			} else if (pacsGLEntry.getCrDr() == 1 && pacOrBank.equalsIgnoreCase("bank")) {
				glEntrySummary.setCrdr("C");
			}
			else if (pacsGLEntry.getCrDr() == -1 && pacOrBank.equalsIgnoreCase("pac")) {
				glEntrySummary.setCrdr(pacsGLEntry.getCrDr().toString());
			}
			else if (pacsGLEntry.getCrDr() == 1 && pacOrBank.equalsIgnoreCase("pac")) {
				glEntrySummary.setCrdr(pacsGLEntry.getCrDr().toString());
			}
			glEntrySummary.setTransType(pacsGLEntry.getTransType());
			glEntrySummary.setRemarks(pacsGLEntry.getRemarks());
			glEntrySummarylist.add(glEntrySummary);
		}
		return glEntrySummarylist;
	}

}
