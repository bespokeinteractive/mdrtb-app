package org.openmrs.module.mdrtb.model;

import org.openmrs.Location;
import org.openmrs.User;

import java.io.Serializable;

/**
 * Created by Dennis Henry on 3/30/2017.
 */
public class UserLocation implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private User user;
    private Location location;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
