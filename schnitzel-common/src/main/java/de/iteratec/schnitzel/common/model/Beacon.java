package de.iteratec.schnitzel.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "BEACON")
public class Beacon extends BaseEntity {
	
	private String beaconUuid;
	private String name;

	@Column(unique=true, nullable=false)
	public String getBeaconUuid() {
		return beaconUuid;
	}

	public void setBeaconUuid(String beaconUuid) {
		this.beaconUuid = beaconUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
