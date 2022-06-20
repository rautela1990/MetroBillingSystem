package com.metro.dto;

import java.sql.Timestamp;

public class TapRequest {
	private Integer id;
	private Timestamp dateTimeUTC;
	private String tapType;
	private String stopId;
	private String companyId;
	private String busId;
	private String pan;

	@Override
	public String toString() {
		return "TapRequest [id=" + id + ", dateTimeUTC=" + dateTimeUTC + ", tapType=" + tapType + ", stopId=" + stopId
				+ ", companyId=" + companyId + ", busId=" + busId + ", pan=" + pan + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getDateTimeUTC() {
		return dateTimeUTC;
	}

	public void setDateTimeUTC(Timestamp dateTimeUTC) {
		this.dateTimeUTC = dateTimeUTC;
	}

	public String getTapType() {
		return tapType;
	}

	public void setTapType(String tapType) {
		this.tapType = tapType;
	}

	public String getStopId() {
		return stopId;
	}

	public void setStopId(String stopId) {
		this.stopId = stopId;
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

	public TapRequest(Integer id, Timestamp dateTimeUTC, String tapType, String stopId, String companyId, String busId,
			String pan) {
		super();
		this.id = id;
		this.dateTimeUTC = dateTimeUTC;
		this.tapType = tapType;
		this.stopId = stopId;
		this.companyId = companyId;
		this.busId = busId;
		this.pan = pan;
	}
}
