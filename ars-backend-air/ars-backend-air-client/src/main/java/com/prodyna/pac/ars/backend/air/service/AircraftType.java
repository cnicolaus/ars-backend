package com.prodyna.pac.ars.backend.air.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.ars.backend.common.client.AbstractEntity;

@Entity
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "aircraftType.findAll", query = "FROM AircraftType order by name"),
		@NamedQuery(name = "aircraftType.findByName", query = "FROM AircraftType where name = ?") })
public class AircraftType extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, columnDefinition = "VARCHAR(64)", unique = true)
	@NotNull
	@Size(min = 1, max = 64)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
