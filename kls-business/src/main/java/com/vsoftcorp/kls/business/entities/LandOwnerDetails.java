package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="land_owner_details")
public class LandOwnerDetails implements Serializable {
	
	@Id
	@SequenceGenerator(name = "landOwnerDetailsIdSeq", sequenceName = "land_owner_details_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "landOwnerDetailsIdSeq")

	@Column(name="ID")
	private Long id;
	
	@Column(name="owner_name")
	private String ownerName;
	
	@Column(name="father_spouse")
	private String fatherOrSpouse;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="village_id",referencedColumnName="ID")
	private Village village;
	
	@Basic
	@Column(name="sub_village", length=45)
	private String subVillage;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="taluka_id",referencedColumnName="ID")
	private Taluka taluka;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="district_id",referencedColumnName="ID")
	private District district;
	
	@Column(name="contact_no")
	private String contactNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getFatherOrSpouse() {
		return fatherOrSpouse;
	}

	public void setFatherOrSpouse(String fatherOrSpouse) {
		this.fatherOrSpouse = fatherOrSpouse;
	}

	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public String getSubVillage() {
		return subVillage;
	}

	public void setSubVillage(String subVillage) {
		this.subVillage = subVillage;
	}

	public Taluka getTaluka() {
		return taluka;
	}

	public void setTaluka(Taluka taluka) {
		this.taluka = taluka;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

}
