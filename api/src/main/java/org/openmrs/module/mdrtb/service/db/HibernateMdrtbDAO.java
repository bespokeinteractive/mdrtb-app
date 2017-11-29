package org.openmrs.module.mdrtb.service.db;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.mdrtb.model.*;
import org.openmrs.module.mdrtb.service.MdrtbService;

public class HibernateMdrtbDAO implements MdrtbDAO {

    protected static final Log log = LogFactory.getLog(HibernateMdrtbDAO.class);
    
    /**
     * Hibernate session factory
     */
    private SessionFactory sessionFactory;
    
    
    public void setSessionFactory(SessionFactory sessionFactory) { 
        this.sessionFactory = sessionFactory;
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    /**
	 * @see MdrtbDAO#getLocationsWithAnyProgramEnrollments()
	 */
    @SuppressWarnings("unchecked")
	public List<Location> getLocationsWithAnyProgramEnrollments() throws DAOException {
		String query = "select distinct location from PatientProgram where voided = false";
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

    public List<UserLocation> getUserLocations(User user){
        Criteria criteria = getSession().createCriteria(UserLocation.class);
        criteria.add(Restrictions.eq("user", user));

        return criteria.list();
    }

    public UserLocation getUserLocations(User user, Location location){
        Criteria criteria = getSession().createCriteria(UserLocation.class);
        criteria.add(Restrictions.eq("user", user));
        criteria.add(Restrictions.eq("location", location));

        return (UserLocation) criteria.uniqueResult();
    }

    public List<PersonLocation> getPersonLocations(Person person){
        Criteria criteria = getSession().createCriteria(PersonLocation.class);
        criteria.add(Restrictions.eq("person", person));

        return criteria.list();
    }

    public PersonLocation getPersonLocation(Person person){
        List<PersonLocation> pLocations = getPersonLocations(person);
        Collections.reverse(pLocations);

        return pLocations.get(0);
    }

    public void setUserLocations(User user, List<Location> pLocations){
        List<Location> aLocations = Context.getLocationService().getAllLocations();
        List<Location> uLocations = Context.getService(MdrtbService.class).getLocationsByUser(user);

        for (Location location: aLocations){
            if (pLocations.contains(location) && !uLocations.contains(location)){
                //Not Existence before, Add
                UserLocation locale = new UserLocation();
                locale.setUser(user);
                locale.setLocation(location);
                locale = (UserLocation) getSession().merge(locale);
            }
            else if (uLocations.contains(location) && !pLocations.contains(location)){
                //Was there but not now, Remove
                UserLocation locale = getUserLocations(user, location);
                if (locale != null){
                    getSession().delete(locale);
                }
            }
        }
    }

    public PersonLocation savePersonLocation(PersonLocation pl){
        return (PersonLocation)getSession().merge(pl);
    }

    //Imports
    public LocationCentres getCentre(Integer id){
        Criteria criteria = getSession().createCriteria(LocationCentres.class);
        criteria.add(Restrictions.eq("id", id));

        return (LocationCentres) criteria.uniqueResult();
    }

    public List<LocationCentres> getCentres(){
        Criteria criteria = getSession().createCriteria(LocationCentres.class);
        List list = criteria.list();
        return list;
    }

    public List<LocationCentres> getCentres(List<Location> locations){
        Criteria criteria = getSession().createCriteria(LocationCentres.class);
        criteria.add(Restrictions.in("location", locations));

        return criteria.list();
    }

    public List<LocationCentres> getCentres(LocationCentresAgencies agency){
        Criteria criteria = getSession().createCriteria(LocationCentres.class);
        criteria.add(Restrictions.eq("agency", agency));

        return criteria.list();
    }

    public List<LocationCentresAgencies> getAgencies(){
        Criteria criteria = getSession().createCriteria(LocationCentresAgencies.class);
        List list = criteria.list();
        return list;
    }

    public List<LocationCentresRegions> getRegions(){
        Criteria criteria = getSession().createCriteria(LocationCentresRegions.class);
        List list = criteria.list();
        return list;
    }

    public List<LocationCentres> getCentresByRegion(String region){
        Criteria criteria = getSession().createCriteria(LocationCentres.class);
        criteria.createAlias("region", "rg");
        criteria.add(Restrictions.eq("rg.name", region));

        List list = criteria.list();
        return list;
    }

    public LocationCentres getCentresByLocation(Location location){
        Criteria criteria = getSession().createCriteria(LocationCentres.class);
        criteria.add(Restrictions.eq("location", location));
        return (LocationCentres) criteria.uniqueResult();
    }

    public LocationCentres saveLocationCentres(LocationCentres centre){
        return (LocationCentres)getSession().merge(centre);
    }

    public LocationCentresAgencies getAgency(Integer agentId){
        Criteria criteria = getSession().createCriteria(LocationCentresAgencies.class);
        criteria.add(Restrictions.eq("id", agentId));
        return (LocationCentresAgencies) criteria.uniqueResult();
    }

    public LocationCentresRegions getRegion(Integer regionId){
        Criteria criteria = getSession().createCriteria(LocationCentresRegions.class);
        criteria.add(Restrictions.eq("id", regionId));
        return (LocationCentresRegions) criteria.uniqueResult();
    }

    public LocationCentresRegions getRegionByName(String name){
        Criteria criteria = getSession().createCriteria(LocationCentresRegions.class);
        criteria.add(Restrictions.eq("name", name));
        return (LocationCentresRegions) criteria.uniqueResult();
    }

    public LocationFacilities getLocationFacility(Location location){
        Criteria criteria = getSession().createCriteria(LocationFacilities.class);
        criteria.add(Restrictions.eq("location", location));
        return (LocationFacilities) criteria.uniqueResult();
    }

    public LocationFacilities saveLocationFacilities(LocationFacilities facility){
        return (LocationFacilities)getSession().merge(facility);
    }

    public LocationFacilities getFacilityById(Integer facilityId){
        Criteria criteria = getSession().createCriteria(LocationFacilities.class);
        criteria.add(Restrictions.eq("id", facilityId));
        return (LocationFacilities)criteria.uniqueResult();
    }

    public List<LocationFacilities> getFacilities(Location location, String status){
        Criteria criteria = getSession().createCriteria(LocationFacilities.class);
        criteria.add(Restrictions.eq("location", location));
        if (StringUtils.isNotBlank(status)){
            criteria.add(Restrictions.eq("status", status));
        }
        return criteria.list();
    }

    public List<PatientProgramDetails> getActivePatients(Location location, Program program){
        Criteria criteria = getSession().createCriteria(PatientProgramDetails.class);
        criteria.createAlias("patientProgram", "pp");
        criteria.add(Restrictions.eq("pp.voided", false));
        criteria.add(Restrictions.eq("pp.program", program));
        criteria.add(Restrictions.eq("pp.location", location));
        criteria.add(Restrictions.isNull("pp.dateCompleted"));

        return criteria.list();
    }

    public PatientProgramDetails getPatientProgramDetails(Integer ppid){
        Criteria criteria = getSession().createCriteria(PatientProgramDetails.class);
        criteria.add(Restrictions.eq("id", ppid));
        return (PatientProgramDetails) criteria.uniqueResult();
    }

    public PatientProgramDetails getPatientProgramDetails(PatientProgram patientProgram){
        Criteria criteria = getSession().createCriteria(PatientProgramDetails.class);
        criteria.add(Restrictions.eq("patientProgram", patientProgram));
        return (PatientProgramDetails) criteria.uniqueResult();
    }

    public PatientProgramDetails savePatientProgramDetails(PatientProgramDetails patientProgramDetails) {
        return (PatientProgramDetails)getSession().merge(patientProgramDetails);
    }

    public RegimentType getRegimenType(Concept concept, Program program){
        Criteria criteria = getSession().createCriteria(RegimentType.class);
        criteria.add(Restrictions.eq("concept", concept));
        criteria.add(Restrictions.eq("program", program));
        criteria.add(Restrictions.eq("voided", 0));

        return (RegimentType)criteria.uniqueResult();
    }

    public List<RegimentType> getRegimenTypes(Concept concept, Program program){
        Criteria criteria = getSession().createCriteria(RegimentType.class);
        criteria.add(Restrictions.eq("concept", concept));
        criteria.add(Restrictions.eq("program", program));
        criteria.add(Restrictions.eq("voided", 0));

        return criteria.list();
    }

    public List<PatientProgramRegimen> getPatientProgramRegimens(PatientProgramDetails pd, Boolean active){
        Criteria criteria = getSession().createCriteria(PatientProgramRegimen.class);
        criteria.add(Restrictions.eq("programDetails", pd));
        if (active){
            criteria.add(Restrictions.isNull("finishedOn"));
        }

        return criteria.list();
    }

    public List<VisitTypes> getVisitTypes(Program program, Boolean initial, Boolean finals, Boolean voided){
        Criteria criteria = getSession().createCriteria(VisitTypes.class);
        criteria.add(Restrictions.eq("program", program));
        if (initial != null){
            criteria.add(Restrictions.eq("initialVisit", initial));
        }
        if (finals != null){
            criteria.add(Restrictions.eq("finalVisit", finals));
        }
        criteria.add(Restrictions.eq("voided", voided));

        return criteria.list();
    }

    public VisitTypes getVisitType(Program program, String name){
        Criteria criteria = getSession().createCriteria(VisitTypes.class);
        criteria.add(Restrictions.eq("program", program));
        criteria.add(Restrictions.eq("name", name));

        return (VisitTypes)criteria.uniqueResult();
    }

    public VisitTypes getVisitType(Integer id){
        Criteria criteria = getSession().createCriteria(VisitTypes.class);
        criteria.add(Restrictions.eq("id", id));

        return (VisitTypes)criteria.uniqueResult();
    }

    public PatientProgramVisits getPatientProgramVisit(PatientProgram patientProgram, VisitTypes visitType){
        Criteria criteria = getSession().createCriteria(PatientProgramVisits.class);
        criteria.add(Restrictions.eq("patientProgram", patientProgram));
        criteria.add(Restrictions.eq("visitType", visitType));

        return (PatientProgramVisits)criteria.uniqueResult();
    }

    public PatientProgramVisits getPatientProgramVisit(Encounter encounter){
        Criteria criteria = getSession().createCriteria(PatientProgramVisits.class);
        criteria.add(Restrictions.eq("encounter", encounter));

        return (PatientProgramVisits)criteria.uniqueResult();
    }

    public List<PatientProgramVisits> getPatientProgramVisits(PatientProgram patientProgram){
        Criteria criteria = getSession().createCriteria(PatientProgramVisits.class);
        criteria.add(Restrictions.eq("patientProgram", patientProgram));

        return criteria.list();
    }

    public PatientProgramRegimen savePatientProgramRegimen(PatientProgramRegimen patientProgramRegimen){
        return (PatientProgramRegimen)getSession().merge(patientProgramRegimen);
    }

    public PatientProgramDetails saveParentProgramOutcome(PatientProgramDetails ppd, Concept outcome, Date completedOn){
        while (ppd.getReferringProgram() != null){
            ppd = getPatientProgramDetails(ppd.getReferringProgram());
            ppd.setOutcome(outcome);

            PatientProgram pp = ppd.getPatientProgram();
            pp.setOutcome(outcome);
            pp.setDateCompleted(completedOn);

            Context.getProgramWorkflowService().savePatientProgram(pp);
            savePatientProgramDetails(ppd);
        }

        return ppd;
    }

    public PatientProgramVisits savePatientProgramVisits(PatientProgramVisits patientProgramVisit){
        return (PatientProgramVisits)getSession().merge(patientProgramVisit);
    }

    public  List<PatientProgramDetails> getPatientsFromDetails(Location location, Date startDate, Date endDate, LocationFacilities facility){
        Criteria criteria = getSession().createCriteria(PatientProgramDetails.class);
        criteria.createAlias("patientProgram", "pp");
        criteria.add(Restrictions.eq("pp.location", location));
        criteria.add(Restrictions.le("pp.voided", false));
        criteria.add(Restrictions.ge("pp.dateEnrolled", startDate));
        criteria.add(Restrictions.le("pp.dateEnrolled", endDate));
        if (facility != null){
            criteria.add(Restrictions.eq("facility", facility));
        }

        return criteria.list();
    }

    public PatientProgramTransfers savePatientProgramTransfers(PatientProgramTransfers patientProgramTransfers){
        return (PatientProgramTransfers)getSession().merge(patientProgramTransfers);
    }

    public PatientProgramTransfers getPatientProgramTransfers(Integer transferId){
        Criteria criteria = getSession().createCriteria(PatientProgramTransfers.class);
        criteria.add(Restrictions.eq("id", transferId));
        return (PatientProgramTransfers)criteria.uniqueResult();
    }

    public List<PatientProgramTransfers> getPatientProgramTransfers(Location location, Boolean status){
        Criteria criteria = getSession().createCriteria(PatientProgramTransfers.class);
        criteria.add(Restrictions.eq("location", location));
        criteria.add(Restrictions.eq("voided", false));
        if (status != null){
            criteria.add(Restrictions.eq("processed", status));
        }

        return criteria.list();
    }

    public List<PatientProgramTransfers> getActivePatientTransfers(PatientProgram patientProgram){
        Criteria criteria = getSession().createCriteria(PatientProgramTransfers.class);
        criteria.add(Restrictions.eq("patientProgram", patientProgram));
        criteria.add(Restrictions.eq("processed", false));
        criteria.add(Restrictions.eq("voided", false));

        return criteria.list();
    }
}
