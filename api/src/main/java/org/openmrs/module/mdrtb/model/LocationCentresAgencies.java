package org.openmrs.module.mdrtb.model;

import org.openmrs.Person;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dennis Henry on 12/24/2016.
 */
public class LocationCentresAgencies implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Person creator;
    private Date createdOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }
}
