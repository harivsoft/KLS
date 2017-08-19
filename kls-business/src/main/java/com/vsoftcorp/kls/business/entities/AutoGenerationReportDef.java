package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.time.Date;

@Entity
@Table(name = "autoreport_generate_def")
public class AutoGenerationReportDef implements Serializable{

		private static final long serialVersionUID = -7561752101720603519L;

		public AutoGenerationReportDef() {
		}

		@Id
		private Integer sno;

		@Basic
		@Column(name = "report_name")
		private String reportName;

		@Basic
		@Column(name = "default_parameters")
		private String defaultParameters;

		@Basic
		@Column(name = "freequence")
		private String frequecy;

		@Basic
		@Column(name = "event")
		private String event;

		@Basic
		@Column(name = "agriyesno")
		private String agriYesNo;

		@Basic
		@Column(name = "report_code")
		private String reportCode;


		@Basic
		@Column(name = "lastgenerated_dt")
		@Type(type = "com.vsoftcorp.time.Date")
		private Date lastgeneratedDate;

		



		public Integer getSno() {
			return sno;
		}



		public void setSno(Integer sno) {
			this.sno = sno;
		}



		public String getReportName() {
			return reportName;
		}



		public void setReportName(String reportName) {
			this.reportName = reportName;
		}



		public String getDefaultParameters() {
			return defaultParameters;
		}



		public void setDefaultParameters(String defaultParameters) {
			this.defaultParameters = defaultParameters;
		}



		public String getFrequecy() {
			return frequecy;
		}



		public void setFrequecy(String frequecy) {
			this.frequecy = frequecy;
		}



		public String getEvent() {
			return event;
		}



		public void setEvent(String event) {
			this.event = event;
		}



		public String getAgriYesNo() {
			return agriYesNo;
		}



		public void setAgriYesNo(String agriYesNo) {
			this.agriYesNo = agriYesNo;
		}



		public Date getLastgeneratedDate() {
			return lastgeneratedDate;
		}



		public void setLastgeneratedDate(Date lastgeneratedDate) {
			this.lastgeneratedDate = lastgeneratedDate;
		}



		public String getReportCode() {
			return reportCode;
		}



		public void setReportCode(String reportCode) {
			this.reportCode = reportCode;
		}

	
		
	}


