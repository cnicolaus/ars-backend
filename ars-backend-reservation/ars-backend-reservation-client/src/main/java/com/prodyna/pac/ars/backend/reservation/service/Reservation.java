package com.prodyna.pac.ars.backend.reservation.service;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.ars.backend.common.client.AbstractEntity;

@Entity
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "reservation.findAll", query = "FROM Reservation"),
		@NamedQuery(name = "reservation.findByState", query = "FROM Reservation where state in (?,?) order by beginDate"),
		@NamedQuery(name = "reservation.findByUserIdAndState", query = "FROM Reservation where userId = ? and state in (?,?) order by beginDate"),
		@NamedQuery(name = "reservation.updateState", query = "update Reservation set state = ? where state in (?, ?) and endDate <= ?"),
		@NamedQuery(name = "reservation.findByAircraftIdAndInterval", query = "FROM Reservation where aircraftId = ? and state in (?, ?) and ((? >= beginDate and ? <= endDate) or (? >= beginDate and ? <= endDate) or (? < beginDate and ? > endDate))") })
public class Reservation extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, columnDefinition = "CHAR(36)")
	@NotNull
	@Size(min = 1, max = 36)
	private String userId;

	@Column(nullable = false, columnDefinition = "CHAR(36)")
	@Size(min = 1, max = 36)
	private String aircraftId;

	@Column(nullable = false, columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date beginDate;

	@Column(nullable = false, columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date endDate;

	@Column(nullable = false, columnDefinition = "VARCHAR(16)")
	@Enumerated(EnumType.STRING)
	private ReservationState state;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAircraftId() {
		return aircraftId;
	}

	public void setAircraftId(String aircraftId) {
		this.aircraftId = aircraftId;
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

	public ReservationState getState() {
		return state;
	}

	public void setState(ReservationState state) {
		this.state = state;
	}

}
