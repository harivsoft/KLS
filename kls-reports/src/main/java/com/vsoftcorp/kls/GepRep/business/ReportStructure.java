package com.vsoftcorp.kls.GepRep.business;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sbgenreport_sql")
public class ReportStructure implements Serializable{
	

	@Id
	@Column(name = "report_id")
	private Integer id;
	
	@Basic
	@Column(name = "report_query")
	private String reportQuery;
	
	@Basic
	@Column(name = "report_column")
	private String reportColumn;
	
	@Basic
	@Column(name = "report_column_size")
	private String reportColumnSize;
	
	@Basic
	@Column(name = "report_title")
	private String reportTitle;
	
	@Basic
	@Column(name = "report_orderby")
	private String reportOrderby;
	
	@Basic
	@Column(name = "report_col_alignment")
	private String reportColAlignment;
	
	@Basic
	@Column(name = "report_col_total")
	private String reportColTotal;
	
	@Basic
	@Column(name = "report_groupby")
	private String reportGroupby;
	
	@Basic
	@Column(name = "report_branchid_filter")
	private String reportBranchidFilter;
	
	@Basic
	@Column(name = "report_print_group")
	private String reportPrintGroup;
	
	@Basic
	@Column(name = "report_call_function")
	private String reportCallFunction;
	
	@Basic
	@Column(name = "report_function_para")
	private Integer reportFunctionPara;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReportQuery() {
		return reportQuery;
	}

	public void setReportQuery(String reportQuery) {
		this.reportQuery = reportQuery;
	}

	public String getReportColumn() {
		return reportColumn;
	}

	public void setReportColumn(String reportColumn) {
		this.reportColumn = reportColumn;
	}

	public String getReportColumnSize() {
		return reportColumnSize;
	}

	public void setReportColumnSize(String reportColumnSize) {
		this.reportColumnSize = reportColumnSize;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public String getReportOrderby() {
		return reportOrderby;
	}

	public void setReportOrderby(String reportOrderby) {
		this.reportOrderby = reportOrderby;
	}

	public String getReportColAlignment() {
		return reportColAlignment;
	}

	public void setReportColAlignment(String reportColAlignment) {
		this.reportColAlignment = reportColAlignment;
	}

	public String getReportColTotal() {
		return reportColTotal;
	}

	public void setReportColTotal(String reportColTotal) {
		this.reportColTotal = reportColTotal;
	}

	public String getReportGroupby() {
		return reportGroupby;
	}

	public void setReportGroupby(String reportGroupby) {
		this.reportGroupby = reportGroupby;
	}

	public String getReportBranchidFilter() {
		return reportBranchidFilter;
	}

	public void setReportBranchidFilter(String reportBranchidFilter) {
		this.reportBranchidFilter = reportBranchidFilter;
	}

	public String getReportPrintGroup() {
		return reportPrintGroup;
	}

	public void setReportPrintGroup(String reportPrintGroup) {
		this.reportPrintGroup = reportPrintGroup;
	}

	public String getReportCallFunction() {
		return reportCallFunction;
	}

	public void setReportCallFunction(String reportCallFunction) {
		this.reportCallFunction = reportCallFunction;
	}

	public Integer getReportFunctionPara() {
		return reportFunctionPara;
	}

	public void setReportFunctionPara(Integer reportFunctionPara) {
		this.reportFunctionPara = reportFunctionPara;
	}
	
		
}
