package com.vsoftcorp.kls.data;

import java.io.Serializable;

public class UserLoginDetailsData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName;
	private String userId;
	private String bankName;
	private String businessDate;
	private Integer branchid;
	private Integer pacsId;
	private boolean isLoggedin;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public Integer getBranchId() {
		return branchid;
	}

	public void setBranchId(Integer branchid) {
		this.branchid = branchid;
	}

	public Integer getPacsId() {
		return pacsId;
	}

	public void setPacsId(Integer pacsId) {
		this.pacsId = pacsId;
	}

	public boolean isLoggedin() {
		return isLoggedin;
	}

	public void setLoggedin(boolean isLoggedin) {
		this.isLoggedin = isLoggedin;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
