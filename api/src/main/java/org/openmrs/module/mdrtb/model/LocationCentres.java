package org.openmrs.module.mdrtb.model;

import org.openmrs.Location;
import org.openmrs.Person;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dennis Henry on 12/24/2016.
 */
public class LocationCentres implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String serialNumber;
    private Location location;
    private LocationCentresAgencies agency;
    private LocationCentresRegions region;
    private Person creator;
    private Date createdOn;

    //Non-Persistent Properties
    private Double amount;
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocationCentresAgencies getAgency() {
        return agency;
    }

    public void setAgency(LocationCentresAgencies agency) {
        this.agency = agency;
    }

    public LocationCentresRegions getRegion() {
        return region;
    }

    public void setRegion(LocationCentresRegions region) {
        this.region = region;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
