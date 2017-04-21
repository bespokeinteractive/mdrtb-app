package org.openmrs.module.mdrtb.service.db;

import java.util.ArrayList;
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
import org.openmrs.module.mdrtb.model.PersonLocation;
import org.openmrs.module.mdrtb.model.UserLocation;
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

    public String getUserLocationsAsString(User user){
        String UserLocations = "N/A";
        List<Location> locations = getLocationsByUser(user);
        for (int i=0; i<locations.size(); i++){
            if (i == 0){
                UserLocations = locations.get(i).getName();
            }
            else if (i == (locations.size()-1)){
                UserLocations += " & " + locations.get(i).getName();
            }
            else {
                UserLocations += ", " + locations.get(i).getName();
            }
        }

        return UserLocations;
    }

    public List<Location> getLocationsByUser(User user){
        List<UserLocation> locales = getUserLocations(user);
        List<Location> locations = new ArrayList<Location>();

        for (UserLocation locale: locales){
            locations.add(locale.getLocation());
        }

        return locations;
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
        List<Location> uLocations = getLocationsByUser(user);

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
                getSession().delete(locale);
            }
        }
    }

    public PersonLocation savePersonLocation(PersonLocation pl){
        return (PersonLocation)getSession().merge(pl);
    }
}
