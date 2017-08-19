package com.vsoftcorp.kls.data;

public class EventTypeData {

	private Integer id;
	private String sequenceName;
	private String recoveryOrder;

	// Map<Integer, String> recoveryOrder = new HashMap<Integer, String>();
	private String checkValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public String getRecoveryOrder() {
		return recoveryOrder;
	}

	public void setRecoveryOrder(String recoveryOrder) {
		this.recoveryOrder = recoveryOrder;
	}

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}

}
