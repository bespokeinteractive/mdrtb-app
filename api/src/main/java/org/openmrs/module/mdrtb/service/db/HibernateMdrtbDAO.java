package org.openmrs.module.mdrtb.service.db;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.mdrtb.model.UserLocation;

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
    
}
