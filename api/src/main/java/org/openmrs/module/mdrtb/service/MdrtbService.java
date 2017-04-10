package org.openmrs.module.mdrtb.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openmrs.*;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.mdrtb.model.UserLocation;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.specimen.Culture;
import org.openmrs.module.mdrtb.specimen.Dst;
import org.openmrs.module.mdrtb.specimen.ScannedLabReport;
import org.openmrs.module.mdrtb.specimen.Smear;
import org.openmrs.module.mdrtb.specimen.Specimen;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.PanelUI;


public interface MdrtbService extends OpenmrsService {

    
    /**
     * @return all Locations which have non-voided Patient Programs associated with them
     */
    @Transactional(readOnly=true)
    public List<Location> getLocationsWithAnyProgramEnrollments();
    
    /**
     * Fetches a concept specified by a MdrtbConcepts mapping
     */
    public Concept getConcept(String... conceptMapping);
    
    /**
     * Fetches a concept specified by a MdrtbConcepts mapping
     */
    public Concept getConcept(String conceptMapping);
    
    /**
     * @return the Concept specified by the passed lookup string.  Checks MdrtbConcepts mapping, id, name, and uuid before returning null
     */
    public Concept findMatchingConcept(String lookup);
   
    /**
     * Resets the concept map cache
     */
    public void resetConceptMapCache();
    
    /**
     * Gets all MDR-TB specific encounters for the given patient
     */
    @Transactional(readOnly=true)
    public List<Encounter> getMdrtbEncounters(Patient patient);
    
    /**
     * Returns all the MDR-TB programs in the system
     */
    @Transactional(readOnly=true)
    public List<MdrtbPatientProgram> getAllMdrtbPatientPrograms();
    
    /**
     * Returns all the mdrtb programs in the system that were active during a specific date range
     */
    @Transactional(readOnly=true)
    public List<MdrtbPatientProgram> getAllMdrtbPatientProgramsInDateRange(Date startDate, Date endDate);
    
  	/**
  	 * Returns all the mdrtb programs for a given patient
  	 */
    @Transactional(readOnly=true)
	public List<MdrtbPatientProgram> getMdrtbPatientPrograms(Patient patient);
	
	/**
	 * Returns the most recent mdrtb program for a given patient
	 */
    @Transactional(readOnly=true)
	public MdrtbPatientProgram getMostRecentMdrtbPatientProgram(Patient patient);
	
    /**
     * Returns all the patient programs for a given patient that fall within a specific date range
     */
    @Transactional(readOnly=true)
    public List<MdrtbPatientProgram> getMdrtbPatientProgramsInDateRange(Patient patient, Date startDate, Date endDate);
    
    /**
     * Return the specific MdrtbPatientProgram the patient was enrolled in on the specified date 
     * (This assumes that a patient is only enrolled in one MDR-TB patient program at a time)
     * 
     * If the date is before any program enrollments, it returns the first program enrollment
     * If the date is after all program enrollments, it returns the most recent program enrollment
     * If the date is between two program enrollments, it returns the later of the two
     */
    @Transactional(readOnly=true)
    public MdrtbPatientProgram getMdrtbPatientProgramOnDate(Patient patient, Date date);
    
	/**
	 * Returns a specific MdrtbPatientProgram by id
	 */
    @Transactional(readOnly=true)
	public MdrtbPatientProgram getMdrtbPatientProgram(Integer patientProgramId);   
    
    /**
     * Creates a new specimen, associated with the given patient
     */
    public Specimen createSpecimen(Patient patient);
    
    /**
     * Fetches a specimen sample obj given a specimen id
     */
    public Specimen getSpecimen(Integer specimedId);
    
    /**
     * Fetches a specimen sample obj given an encounter of the Specimen Collection type
     */
    public Specimen getSpecimen(Encounter encounter);
    
    /**
     * Fetches all specimens for a patient (i.e., all Specimen Collection encounters)
     */
    public List<Specimen> getSpecimens(Patient patient);
    
    /**
     * Fetches all specimens within a certain data range
     * 
     * @param patient: only include specimens associated with this patient
     * @param startDate: only include specimens with a date collected after (or equal to) this start date
     * @param endDate: only include specimens with a date collected before (or equal to) this end date
     * 
     * All parameters can be set to null
     */
    public List<Specimen> getSpecimens(Patient patient, Date startDateCollected, Date endDateCollected);
    
    /**
     * Fetches all specimens within a certain data range and from a certain lab
     * 
     * @param patient: only include specimens associated with this patient
     * @param startDate: only include specimens with a date collected after (or equal to) this start date
     * @param endDate: only include specimens with a date collected before (or equal to) this end date
     * @param location: only include specimens collected from the specified location
     * 
     * All parameters can be set to null
     */
    public List<Specimen> getSpecimens(Patient patient, Date startDateCollected, Date endDateCollected, Location locationCollected);
    
    /**
     * Deletes a specimen, referenced by specimen Id
     */
    public void deleteSpecimen(Integer patientId);
    
    /**
     * Saves or updates a specimen object
     */
    @Transactional
	public void saveSpecimen(Specimen specimen);
    
    /**
     * Deletes a smear, culture, or dst test
     */
    @Transactional 
    public void deleteTest(Integer testId);
    
    /**
     * Creates a new Smear, associated with the given specimen
     */
    public Smear createSmear(Specimen specimen);

    /**
     * Creates a new Smear, associated with the given specimen, by copying the member properties of the given smear
     */
    public Smear createSmear(Specimen specimen, Smear smear);
    
    /**
     * Fetches a smear given the obs of a Tuberculosis Smear Test Construct
     * 
     * @param obs
     * @return
     */
    public Smear getSmear(Obs obs);
       
    /**
     * Fetches a smear given the obs_id of a Tuberculosis Smear Test Construct
     * 
     * @param obsId
     */
    public Smear getSmear(Integer obsId);
    
    /**
     * Saves a smear in the approriate obs construct
     */
    @Transactional
    public void saveSmear(Smear smear);
     
    /**
     * Creates a new culture, associated with the given specimen
     */
    public Culture createCulture(Specimen specimen);

    /**
     * Creates a new culture, associated with the given specimen, by copying the member properties of the given culture
     */
    public Culture createCulture(Specimen specimen, Culture culture);

    /**
     * Fetches a culture given the obs of a Tuberculosis Smear Test Construct
     * 
     * @param obs
     * @return
     */
    public Culture getCulture(Obs obs);
       
    /**
     * Fetches a culture given the obs_id of a Tuberculosis Smear Test Construct
     * 
     * @param obsId
     */
    public Culture getCulture(Integer obsId);
    
    /**
     * Saves a culture in the approriate obs construct
     */
    @Transactional
    public void saveCulture(Culture culture);
    
    
    /**
     * Creates a new dst, associated with the given specimen
     */
    public Dst createDst(Specimen specimen);

    /**
     * Creates a new dst, associated with the given specimen, by copying the member properties of the given dst
     */
    public Dst createDst(Specimen specimen, Dst dst);

    /**
     * Fetches a dst given the obs of a Tuberculosis Smear Test Construct
     * 
     * @param obs
     * @return
     */
    public Dst getDst(Obs obs);
       
    /**
     * Fetches a dst given the obs_id of a Tuberculosis Smear Test Construct
     * 
     * @param obsId
     */
    public Dst getDst(Integer obsId);
    
    /**
     * Saves a dst in the appropriate obs construct
     */
    @Transactional
    public void saveDst(Dst dst);
    
    
    /**
     * Deletes a dst result
     */
    @Transactional 
    public void deleteDstResult(Integer dstResultId);
    
    /**
     * Saves a scanned lab report in the appropriate obs constructs
     */
    @Transactional
    public void saveScannedLabReport(ScannedLabReport report);
    
    /**
     * Deletes a scanned lab report
     */
    @Transactional 
    public void deleteScannedLabReport(Integer reportId);
    
    /**
     * Handles exiting a patient from care
     */
    @Transactional
    public void processDeath(Patient patient, Date deathDate, Concept causeOfDeath);
    
    /**
     * Gets the MDR-TB program
     */
    @Transactional(readOnly=true)
    public Program getMdrtbProgram();
    
    /**
     * Gets the possible providers
     */
    @Transactional(readOnly=true)
    public Collection<Person> getProviders();
    
    /**
     * Returns all the concepts that are possible coded answers for the Tuberculosis Smear Test Result
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleSmearResults();
    
    /**
     * Returns all the concepts that are possible coded answers for the Tuberculosis Smear Method concept
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleSmearMethods();

    /**
     * Returns all the concepts that are possible coded answers for the GenXpert concept
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleGenXpertResults();

    /**
     * Returns all the concepts that are possible coded answers for the HIV Test concept
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleHivTestResults();

    /**
     * Returns all the concepts that are possible coded answers for the XRAY Test concept
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleXRayTestResults();
    
    /**
     * Returns all the concepts that are possible coded answers for the Tuberculosis Culture Test Result
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleCultureResults();
    
    /**
     * Returns all the concepts that are possible coded answers for the Tuberculosis Culture Method concept
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleCultureMethods();
    
    /**
     * Returns all the concepts that are possible coded answers for the Tuberculosis Drug Sensitivity Test Method concept
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleDstMethods();
    
    /**
     * Returns all the concepts that are possible Dst results
     */
    @Transactional(readOnly=true)
    public Collection<Concept> getPossibleDstResults();
    
    /**
     * Returns all the concepts that are possible coded answered for Type of Organism concept
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleOrganismTypes();
    
    /**
     * Returns all the concepts that are possible coded answer for the Tuberculosis Sample Source concept
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleSpecimenTypes();
    
    /**
     * Returns all the concepts that are possible coded answers for the appearance of a sputum specimen
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleSpecimenAppearances();
    
    /**
     * Returns all possible TB Anatomical sites
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleAnatomicalSites();

    /**
     * Returns all possible Referring Departments
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleReferringDepartments();

    /**
     * Returns all possible Directly Observer for DOTS
     */
    @Transactional(readOnly=true)
    public Collection<ConceptAnswer> getPossibleDirectObservers();
    
    /**
     * @return all of the Drugs within the ConceptSets which match the conceptMapKeys
     */
    public List<Concept> getDrugsInSet(String... conceptMapKey);
    
    /**
     * @return all of the Drugs within the ConceptSet which match the conceptMapKeys
     */
    public List<Concept> getDrugsInSet(Concept concept);
  
    /**
     * Returns all the possible drugs to display in a DST result, in the order we want to display them
     */
    public List<Concept> getMdrtbDrugs();
    public List<Concept> getDstDrugs();

    /**
     * Returns all the possible antiretrovirals
     */
    public List<Concept> getAntiretrovirals();

    /**
     * Returns all possible outcomes for the TB program & MDR-TB program
     */
    public Set<ProgramWorkflowState> getPossibleTbProgramOutcomes();

    public Set<ProgramWorkflowState> getPossibleMdrtbProgramOutcomes();

    /**
     * Returns all possible MDR-TB previous drug use classifications
     */
    public Set<ProgramWorkflowState> getPossibleClassificationsAccordingToPreviousDrugUse();

    /**
     * Returns all possible MDR-TB previous treatment classifications
     */
    public Set<ProgramWorkflowState> getPossibleClassificationsAccordingToPreviousTreatment();

    /**
     * Returns all possible TB patitient Type classifications
     */
    public Set<ProgramWorkflowState> getPossibleClassificationsAccordingToPatientType();

    /**
     * Returns all possible TB patitient Treatment Classifications
     */
    public Set<ProgramWorkflowState> getPossibleClassificationsAccordingToTreatmentCategory();

	/**
     * Check to see what color to associate with a given result concept
     */
    public String getColorForConcept(Concept concept);

    /**
     * Resets the color map cache to null to force cache reload
     */
    public void resetColorMapCache();

    public List<UserLocation> getUserLocations();

    public List<UserLocation> getUserLocations(User user);

    public String getUserLocationsAsString();

    public String getUserLocationsAsString(User user);

    public UserLocation getUserLocations(Location location);

    public UserLocation getUserLocations(User user, Location location);

    public List<Location> getLocationsByUser();

    public List<Location> getLocationsByUser(User user);

    @Transactional
    public void setUserLocations(User user, List<Location> locations);
}

