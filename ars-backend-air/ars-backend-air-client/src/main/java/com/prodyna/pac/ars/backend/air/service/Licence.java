package com.prodyna.pac.ars.backend.air.service;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.ars.backend.common.client.AbstractEntity;

@Entity
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "licence.findByUserId", query = "FROM Licence where userId = ?"),
		@NamedQuery(name = "licence.findByUserIdAndAircraftTypeId", query = "FROM Licence where userId = ? and aircraftType.id = ?") })
public class Licence extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, columnDefinition = "VARCHAR(64)")
	@NotNull
	@Size(min = 1, max = 64)
	private String userId;

	@NotNull
	@Valid
	@ManyToOne
	private AircraftType aircraftType;

	@Column(nullable = false)
	private Date expiryDate;

	public AircraftType getAircraftType() {
		return aircraftType;
	}

	public void setAircraftType(AircraftType aircraftType) {
		this.aircraftType = aircraftType;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
