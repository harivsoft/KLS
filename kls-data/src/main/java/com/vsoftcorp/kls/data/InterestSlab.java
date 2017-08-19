package com.vsoftcorp.kls.data;

import java.math.BigDecimal;

public class InterestSlab {

	private BigDecimal fromSlab;

	private BigDecimal toSlab;

	private Integer fromPeriod;

	private Integer toPeriod;

	private BigDecimal roi;

	private BigDecimal penalRoi;

	private Long id;

	/**
	 * @return the fromSlab
	 */
	public BigDecimal getFromSlab() {
		return fromSlab;
	}

	/**
	 * @param fromSlab
	 *            the fromSlab to set
	 */
	public void setFromSlab(BigDecimal fromSlab) {
		this.fromSlab = fromSlab;
	}

	/**
	 * @return the toSlab
	 */
	public BigDecimal getToSlab() {
		return toSlab;
	}

	/**
	 * @param toSlab
	 *            the toSlab to set
	 */
	public void setToSlab(BigDecimal toSlab) {
		this.toSlab = toSlab;
	}

	/**
	 * @return the fromPeriod
	 */
	public Integer getFromPeriod() {
		return fromPeriod;
	}

	/**
	 * @param fromPeriod
	 *            the fromPeriod to set
	 */
	public void setFromPeriod(Integer fromPeriod) {
		this.fromPeriod = fromPeriod;
	}

	/**
	 * @return the toPeriod
	 */
	public Integer getToPeriod() {
		return toPeriod;
	}

	/**
	 * @param toPeriod
	 *            the toPeriod to set
	 */
	public void setToPeriod(Integer toPeriod) {
		this.toPeriod = toPeriod;
	}

	/**
	 * @return the roi
	 */
	public BigDecimal getRoi() {
		return roi;
	}

	/**
	 * @param roi
	 *            the roi to set
	 */
	public void setRoi(BigDecimal roi) {
		this.roi = roi;
	}

	/**
	 * @return the penalRoi
	 */
	public BigDecimal getPenalRoi() {
		return penalRoi;
	}

	/**
	 * @param penalRoi
	 *            the penalRoi to set
	 */
	public void setPenalRoi(BigDecimal penalRoi) {
		this.penalRoi = penalRoi;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
