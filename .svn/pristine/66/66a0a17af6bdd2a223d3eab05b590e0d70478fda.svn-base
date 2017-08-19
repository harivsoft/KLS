package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.kls.valuetypes.loanproduct.InterestFrequency;
import com.vsoftcorp.time.Date;

@TypeDefs({
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }),
		@TypeDef(name = "interestFreq", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.loanproduct.InterestFrequency"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }) })
@Entity
@Table(name = "borrowing_product")
public class BorrowingProduct implements Serializable {

	private static final long serialVersionUID = 6067921645190702550L;

	public BorrowingProduct() {
	}

	@Id
	@GeneratedValue(generator = "borrowingProductIdSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "borrowingProductIdSeq", sequenceName = "borrowing_product_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "name", length = 20)
	private String name;

	@Basic
	@Column(name = "code", length = 20)
	private String code;

	@Basic
	@Column(name = "short_name", length = 20)
	private String shortName;

	@Basic
	@Column(name = "gl_name", length = 20)
	private String glName;

	@Column(name = "interest_frequency", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "interestFreq")
	private InterestFrequency interestFrequency;

	@Basic
	@Column(name = "release_date", length = 20)
	@Type(type = "com.vsoftcorp.time.Date")
	private Date releaseDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "interest_category_id", referencedColumnName = "ID")
	private InterestCategory interestCategory;

	@ManyToOne(optional = false)
	@JoinColumn(name = "product_type_id", referencedColumnName = "ID")
	private ProductType productType;

//	@ManyToOne(optional = false)
//	@JoinColumn(name = "gl_code", referencedColumnName = "gl_code")
	
	@Basic
	@Column(name = "gl_code", length = 12)
	private String glCode;

	@Basic
	@Column(name = "interest_payable_gl_code" ,length=45)
	private String interestpayableGl;

	@ManyToOne(optional = false)
	@JoinColumn(name = "scheme_id", referencedColumnName = "ID")
	private Scheme scheme;

	@Basic
	@Column(name = "min_period")
	private Integer minPeriod;

	@Basic
	@Column(name = "max_period")
	private Integer maxPeriod;

	@Basic
	@Column(name = "interest_paid_gl_code", length=45)
	private String interestpaidGl;

	@Basic
	@Column(name ="interest_subsidy", length=45)
	private String interestSubsidyGl;

	@Basic
	@Column(name = "principle_subsidy",length=45)
	private String principleSubsidyGl;
	
	@Basic
	@Column(name = "bank_interest_receivable_gl", length = 18)
	private  String bankInterestReceivableGL;
	
	@Basic
	@Column(name = "bank_interest_received_gl", length = 18)
	private String  bankInterestReceivedGL;
	
	@Basic
	@Column(name = "bank_penal_interest_receivable_gl", length = 18)
	private String bankPenalInterestReceivableGL;
	
	@Basic
	@Column(name = "bank_penal_interest_received_gl", length = 18)
	private String bankPenalInterestReceivedGL;

	public String getBankInterestReceivableGL() {
		return bankInterestReceivableGL;
	}

	public void setBankInterestReceivableGL(String bankInterestReceivableGL) {
		this.bankInterestReceivableGL = bankInterestReceivableGL;
	}

	public String getBankInterestReceivedGL() {
		return bankInterestReceivedGL;
	}

	public void setBankInterestReceivedGL(String bankInterestReceivedGL) {
		this.bankInterestReceivedGL = bankInterestReceivedGL;
	}

	public String getBankPenalInterestReceivableGL() {
		return bankPenalInterestReceivableGL;
	}

	public void setBankPenalInterestReceivableGL(
			String bankPenalInterestReceivableGL) {
		this.bankPenalInterestReceivableGL = bankPenalInterestReceivableGL;
	}

	public String getBankPenalInterestReceivedGL() {
		return bankPenalInterestReceivedGL;
	}

	public void setBankPenalInterestReceivedGL(String bankPenalInterestReceivedGL) {
		this.bankPenalInterestReceivedGL = bankPenalInterestReceivedGL;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getGlName() {
		return glName;
	}

	public void setGlName(String glName) {
		this.glName = glName;
	}

	public InterestFrequency getInterestFrequency() {
		return interestFrequency;
	}

	public void setInterestFrequency(InterestFrequency interestFrequency) {
		this.interestFrequency = interestFrequency;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public InterestCategory getInterestCategory() {
		return interestCategory;
	}

	public void setInterestCategory(InterestCategory interestCategory) {
		this.interestCategory = interestCategory;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public String getGlCode() {
		return glCode;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	public String getInterestpayableGl() {
		return interestpayableGl;
	}

	public void setInterestpayableGl(String interestpayableGl) {
		this.interestpayableGl = interestpayableGl;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public Integer getMinPeriod() {
		return minPeriod;
	}

	public void setMinPeriod(Integer minPeriod) {
		this.minPeriod = minPeriod;
	}

	public Integer getMaxPeriod() {
		return maxPeriod;
	}

	public void setMaxPeriod(Integer maxPeriod) {
		this.maxPeriod = maxPeriod;
	}

	public String getInterestpaidGl() {
		return interestpaidGl;
	}

	public void setInterestpaidGl(String interestpaidGl) {
		this.interestpaidGl = interestpaidGl;
	}

	public String getInterestSubsidyGl() {
		return interestSubsidyGl;
	}

	public void setInterestSubsidyGl(String interestSubsidyGl) {
		this.interestSubsidyGl = interestSubsidyGl;
	}

	public String getPrincipleSubsidyGl() {
		return principleSubsidyGl;
	}

	public void setPrincipleSubsidyGl(String principleSubsidyGl) {
		this.principleSubsidyGl = principleSubsidyGl;
	}

	
	

}