package org.openmrs.module.mdrtb.model;

import org.openmrs.Location;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.context.Context;

import java.util.Date;

/**
 * Created by Dennis Henry
 * Created on 4/12/2017.
 */
public class PersonLocation {
    private Integer id;
    private Person person;
    private Location location;
    private Date createdOn;
    private User createdBy;
    private String description;

    public PersonLocation(){
        this.createdOn = new Date();
        this.createdBy = Context.getAuthenticatedUser();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
