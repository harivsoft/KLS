package com.vsoftcorp.kls.loans.gl.service.impl;

public class TTUMField {
	
	private String name;
	private Integer size;
	private Integer startPosition;
	private Integer endPosition;
	private char justification; // Posible values 'L' Left, 'R' Right, 'N' Not applicable
	private boolean isMandatory;
	private String value;
	
	public TTUMField(String name, Integer size, Integer startPosition, Integer endPosition, char justification, boolean isMandatory, String value){
		this.name = name;
		this.size = size;
		this.startPosition = startPosition; 
		this.endPosition = endPosition;
		this.justification = justification; // Posible values 'L' Left, 'R' Right, 'N' Not applicable
		this.isMandatory = isMandatory;
		this.value = value;
	}
	
	public TTUMField(String name, Integer size,char justification, String value){
		this.name = name;
		this.size = size;
		this.justification = justification; // Posible values 'L' Left, 'R' Right, 'N' Not applicable
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getStartPosition() {
		return startPosition;
	}
	public void setStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
	}
	public char getJustification() {
		return justification;
	}
	public void setJustification(char justification) {
		this.justification = justification;
	}
	public boolean isMandatory() {
		return isMandatory;
	}
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Integer getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(Integer endPosition) {
		this.endPosition = endPosition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getFormattedValue() throws TTUMException{
		StringBuilder adjustedValue = new StringBuilder("");
		if (value.length() > size){
			throw new TTUMException("Value of the TTUM Field "+ name + " is larger than expected size.");
		}else if (value.length() == size){
			return value;
		} else {

			if (justification == 'R'){
				for (int i=0; i < (size - value.length()) ; i++){
					adjustedValue.append(' ');
				}
				adjustedValue.append(value);
			}else  {
				adjustedValue.append(value);
				for (int i=0; i < (size - value.length()) ; i++){
					adjustedValue.append(' ');
				}
			}
		}
		return adjustedValue.toString();
	}
}
