package com.prodyna.pac.ars.backend.reservation.service;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AircraftReservationVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String aircraftType;

	private String registrationCode;

	private Date beginDate;

	private Date endDate;

	private String userName;

	private String status;

	private boolean pickUpTimeAchieved;

	public String getAircraftType() {
		return aircraftType;
	}

	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isPickUpTimeAchieved() {
		return pickUpTimeAchieved;
	}

	public void setPickUpTimeAchieved(boolean pickUpTimeAchieved) {
		this.pickUpTimeAchieved = pickUpTimeAchieved;
	}

}
