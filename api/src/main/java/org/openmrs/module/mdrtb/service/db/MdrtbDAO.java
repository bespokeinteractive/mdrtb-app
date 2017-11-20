package org.openmrs.module.mdrtb.service.db;

import java.util.List;

import org.openmrs.Location;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.mdrtb.model.*;

public interface MdrtbDAO {

    /**
     * @return all Locations which have non-voided Patient Programs associated with them
     */
    public List<Location> getLocationsWithAnyProgramEnrollments() throws DAOException;

    public List<UserLocation> getUserLocations(User user);

    public UserLocation getUserLocations(User user, Location location);

    //public List<Location> getLocationsByUser(User user);

    public List<PersonLocation> getPersonLocations(Person person);

    public PersonLocation getPersonLocation(Person person);

    public void setUserLocations(User user, List<Location> locations);

    public PersonLocation savePersonLocation(PersonLocation pl);

    //Imports
    public LocationCentres getCentre(Integer id);
    public List<LocationCentres> getCentres();
    public List<LocationCentres> getCentres(List<Location> locations);
    public List<LocationCentres> getCentres(LocationCentresAgencies agency);
    public List<LocationCentres> getCentresByRegion(String region);
    public LocationCentres getCentresByLocation(Location location);
    public LocationCentres saveLocationCentres(LocationCentres centre);

    public List<LocationCentresAgencies> getAgencies();
    public List<LocationCentresRegions> getRegions();
    public LocationCentresAgencies getAgency(Integer agentId);
    public LocationCentresRegions getRegion(Integer regionId);
    public LocationCentresRegions getRegionByName(String name);
}
