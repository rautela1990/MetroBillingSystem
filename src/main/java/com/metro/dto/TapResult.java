package com.metro.dto;

import java.sql.Timestamp;

public class TapResult {
	private Integer id;
	private Timestamp journeyStarted;
	private Timestamp journeyFinished;
	private Long DurationInSeconds;
	private String fromStopId;
	private String toStopId;
	private String chargeAmount;
	private String companyId;
	private String busId;
	private String pan;
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getJourneyStarted() {
		return journeyStarted;
	}

	public void setJourneyStarted(Timestamp journeyStarted) {
		this.journeyStarted = journeyStarted;
	}

	public Timestamp getJourneyFinished() {
		return journeyFinished;
	}

	public void setJourneyFinished(Timestamp journeyFinished) {
		this.journeyFinished = journeyFinished;
	}

	public Long getDurationInSeconds() {
		return DurationInSeconds;
	}

	public void setDurationInSeconds(Long durationInSeconds) {
		DurationInSeconds = durationInSeconds;
	}

	public String getFromStopId() {
		return fromStopId;
	}

	public void setFromStopId(String fromStopId) {
		this.fromStopId = fromStopId;
	}

	public String getToStopId() {
		return toStopId;
	}

	public void setToStopId(String toStopId) {
		this.toStopId = toStopId;
	}

	public String getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TapResult [id=" + id + ", journeyStarted=" + journeyStarted + ", journeyFinished=" + journeyFinished
				+ ", DurationInSeconds=" + DurationInSeconds + ", fromStopId=" + fromStopId + ", toStopId=" + toStopId
				+ ", chargeAmount=" + chargeAmount + ", companyId=" + companyId + ", busId=" + busId + ", pan=" + pan
				+ ", status=" + status + "]";
	}

	public TapResult(Integer id, Timestamp journeyStarted, Timestamp journeyFinished, Long durationInSeconds,
			String fromStopId, String toStopId, String chargeAmount, String companyId, String busId, String pan,
			String status) {
		super();
		this.id = id;
		this.journeyStarted = journeyStarted;
		this.journeyFinished = journeyFinished;
		DurationInSeconds = durationInSeconds;
		this.fromStopId = fromStopId;
		this.toStopId = toStopId;
		this.chargeAmount = chargeAmount;
		this.companyId = companyId;
		this.busId = busId;
		this.pan = pan;
		this.status = status;
	}

}
