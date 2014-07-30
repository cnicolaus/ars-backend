package com.prodyna.pac.ars.backend.air.service;

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
@NamedQueries({ @NamedQuery(name = "aircraft.findAll", query = "FROM Aircraft order by registrationCode"),
		@NamedQuery(name = "aircraft.findByRegistrationCode", query = "FROM Aircraft where registrationCode = ?") })
public class Aircraft extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, columnDefinition = "VARCHAR(8)", unique = true)
	@NotNull
	@Size(min = 3, max = 8)
	private String registrationCode;

	@NotNull
	@Valid
	@ManyToOne
	private AircraftType aircraftType;

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public AircraftType getAircraftType() {
		return aircraftType;
	}

	public void setAircraftType(AircraftType aircraftType) {
		this.aircraftType = aircraftType;
	}

}
