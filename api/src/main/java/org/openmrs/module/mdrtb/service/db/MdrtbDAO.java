package org.openmrs.module.mdrtb.service.db;

import java.util.List;

import org.openmrs.Location;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.mdrtb.model.PersonLocation;
import org.openmrs.module.mdrtb.model.UserLocation;

public interface MdrtbDAO {

    /**
     * @return all Locations which have non-voided Patient Programs associated with them
     */
    public List<Location> getLocationsWithAnyProgramEnrollments() throws DAOException;

    public List<UserLocation> getUserLocations(User user);

    public UserLocation getUserLocations(User user, Location location);

    public String getUserLocationsAsString(User user);

    public List<Location> getLocationsByUser(User user);

    public List<PersonLocation> getPersonLocations(Person person);

    public PersonLocation getPersonLocation(Person person);

    public void setUserLocations(User user, List<Location> locations);

    public PersonLocation savePersonLocation(PersonLocation pl);
}
