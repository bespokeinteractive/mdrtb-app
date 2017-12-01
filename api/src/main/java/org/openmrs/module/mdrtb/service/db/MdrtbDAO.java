package org.openmrs.module.mdrtb.service.db;

import java.util.Date;
import java.util.List;

import org.openmrs.*;
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

    //New Imports
    LocationFacilities getLocationFacility(Location location);
    LocationFacilities saveLocationFacilities(LocationFacilities facility);
    LocationFacilities getFacilityById(Integer facilityId);
    List<LocationFacilities> getFacilities(Location location, String status);

    //Final Imports
    PatientProgramDetails getPatientProgramDetails(Integer ppid);
    PatientProgramDetails savePatientProgramDetails(PatientProgramDetails patientProgramDetails);
    PatientProgramDetails saveParentProgramOutcome(PatientProgramDetails ppd, Concept outcome, Date completedOn);
    List<PatientProgramDetails> getActivePatients(Location location, Program program);

    PatientProgramTransfers savePatientProgramTransfers(PatientProgramTransfers patientProgramTransfers);
    PatientProgramTransfers getPatientProgramTransfers(Integer transferId);
    List<PatientProgramTransfers> getPatientProgramTransfers(Location location, Boolean status);
    List<PatientProgramTransfers> getActivePatientTransfers(PatientProgram patientProgram);
    RegimentType getRegimenType(Concept concept, Program program);
    List<RegimentType> getRegimenTypes(Concept concept, Program program);

    PatientProgramRegimen savePatientProgramRegimen(PatientProgramRegimen patientProgramRegimen);
    List<PatientProgramRegimen> getPatientProgramRegimens(PatientProgramDetails pd, Boolean active);

    PatientProgramVisits savePatientProgramVisits(PatientProgramVisits patientProgramVisit);
    PatientProgramVisits getPatientProgramVisit(PatientProgram patientProgram, VisitTypes visitType);
    PatientProgramVisits getPatientProgramVisit(Encounter encounter);
    List<PatientProgramVisits> getPatientProgramVisits(PatientProgram patientProgram);

    List<VisitTypes> getVisitTypes(Program program, Boolean initial, Boolean finals, Boolean voided);
    VisitTypes getVisitType(Program program, String name);
    VisitTypes getVisitType(Integer id);

    PatientProgramDetails getPatientProgramDetails(PatientProgram patientProgram);
    List<PatientProgramDetails> getPatientsFromDetails(Location location, Program program, Date date1, Date date2);
    List<PatientProgramDetails> getPatientsFromDetails(Location location, Date startDate, Date endDate, LocationFacilities facility);
}
