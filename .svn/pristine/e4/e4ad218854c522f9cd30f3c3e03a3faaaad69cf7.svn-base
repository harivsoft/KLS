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
@Table(name = "pacsrepotslog")
public class PacsRepotsLog implements Serializable{
	
	private static final long serialVersionUID = -7561752101720603519L;

			public PacsRepotsLog() {
			}

			@Id
			@GeneratedValue(generator = "pacsrepotslog_sequence_no_seq", strategy = GenerationType.SEQUENCE)
			@SequenceGenerator(name = "pacsrepotslog_sequence_no_seq", sequenceName = "pacsrepotslog_sequence_no_seq", allocationSize = 1)
			@Column(name="sequence_no")
			private Integer sno;

			@Basic
			@Column(name="pacid")
			private Integer id;
			
			@Basic
			@Column(name = "gen_reportname")
			private String reportName;

			@Basic
			@Column(name = "filename")
			private String fileName;

			@Basic
			@Column(name = "freequencyid")
			private Integer frequecyId;

			@Basic
			@Column(name = "reportid")
			private Integer reportId;


			@Basic
			@Column(name = "gen_dt")
			@Type(type = "com.vsoftcorp.time.Date")
			private Date generatedDate;

			public Integer getSno() {
				return sno;
			}


			public void setSno(Integer sno) {
				this.sno = sno;
			}


			public Integer getId() {
				return id;
			}


			public void setId(Integer id) {
				this.id = id;
			}


			public String getReportName() {
				return reportName;
			}


			public void setReportName(String reportName) {
				this.reportName = reportName;
			}


			public String getFileName() {
				return fileName;
			}


			public void setFileName(String fileName) {
				this.fileName = fileName;
			}


			public Integer getFrequecyId() {
				return frequecyId;
			}


			public void setFrequecyId(Integer frequecyId) {
				this.frequecyId = frequecyId;
			}


			public Integer getReportId() {
				return reportId;
			}


			public void setReportId(Integer reportId) {
				this.reportId = reportId;
			}


			public Date getGeneratedDate() {
				return generatedDate;
			}


			public void setGeneratedDate(Date generatedDate) {
				this.generatedDate = generatedDate;
			}

			
			

		}




