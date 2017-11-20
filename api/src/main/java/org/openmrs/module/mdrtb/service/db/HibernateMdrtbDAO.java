package org.openmrs.module.mdrtb.service.db;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Location;
import org.openmrs.Person;
import org.openmrs.User;
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
}
