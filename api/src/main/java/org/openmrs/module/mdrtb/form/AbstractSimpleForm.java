package org.openmrs.module.mdrtb.form;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.service.MdrtbService;


public abstract class AbstractSimpleForm implements SimpleForm {
	protected Encounter encounter;
	
	public AbstractSimpleForm() {
		this.encounter = new Encounter();
	}
	
	public AbstractSimpleForm(Patient patient) {
		this.encounter = new Encounter();
		this.encounter.setPatient(patient);
	}
	
	public AbstractSimpleForm(Encounter encounter) {
		this.encounter = encounter;
	}
	
	
	public Integer getId() {
		return this.encounter.getId();
	}
	
	public void setEncounter(Encounter encounter) {
	    this.encounter = encounter;
    }

	public Encounter getEncounter() {
	    return encounter;
    }
	
	public Person getProvider() {
		return encounter.getProvider();
	}
	
	public void setProvider(Person provider) {
		encounter.setProvider(provider);
	}
	
	public Patient getPatient() {
		return encounter.getPatient();
	}
	
	public void setPatient(Patient patient) {
		encounter.setPatient(patient);
		
		// propogate to all the obs on the encounter
		for (Obs obs : encounter.getAllObs()) {
			obs.setPerson(patient);
		}
	}
	
	public Date getEncounterDatetime() {
		return encounter.getEncounterDatetime();
	}

	public void setEncounterDatetime(Date date) {
		encounter.setEncounterDatetime(date);
		
		// propogate to all the obs on the encounter
		for (Obs obs : encounter.getAllObs()) {
			obs.setObsDatetime(date);
		}
	}
	
	public Location getLocation() {
		return encounter.getLocation();
	}
	
	public void setLocation(Location location) {
		encounter.setLocation(location);
		
		// propogate to all the obs on the encounter
		for (Obs obs : encounter.getAllObs()) {
			obs.setLocation(location);
		}
	}
	
	public String getWeight() {
		return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.WEIGHT), this.encounter);
	}
	
	public void setWeight(String weight) {
		updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.WEIGHT), this.encounter, weight);
	}

    public String getHeight() {
        return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEIGHT), this.encounter);
    }

    public void setHeight(String height) {
        updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEIGHT), this.encounter, height);
    }

    public String getBMI() {
        return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.BMI), this.encounter);
    }

    public void setBMI(String bmi) {
        updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.BMI), this.encounter, bmi);
    }

    public String getMUAC() {
        return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MUAC), this.encounter);
    }

    public void setMUAC(String muac) {
        updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MUAC), this.encounter, muac);
    }
	
	public String getPulse() {
		return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PULSE), this.encounter);
	}
	
	public void setPulse(String pulse) {
		updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PULSE), this.encounter, pulse);
	}
	
	public String getTemperature() {
		return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TEMPERATURE), this.encounter);
	}
	
	public void setTemperature(String temperature) {
		updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TEMPERATURE), this.encounter, temperature);
	}
	
	public String getSystolicBloodPressure() {
		return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SYSTOLIC_BLOOD_PRESSURE), this.encounter);
	}
	
	public void setSystolicBloodPressure(String pressure) {
		updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SYSTOLIC_BLOOD_PRESSURE), this.encounter, pressure);
	}
	
	public String getRespiratoryRate() {
		return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RESPIRATORY_RATE), this.encounter);
	}
	
	public void setRespiratoryRate(String rate) {
		updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RESPIRATORY_RATE), this.encounter, rate);
	}
	
	public String getClinicianNotes() {
		return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CLINICIAN_NOTES), this.encounter);
	}
	
	public void setClinicianNotes(String comments) {
		updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CLINICIAN_NOTES), this.encounter, comments);
	}

	public String getLabNumber() {
		return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LAB_TEST_SERIAL_NUMBER), this.encounter);
	}

	public void getLabNumber(String number) {
		updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LAB_TEST_SERIAL_NUMBER), this.encounter, number);
	}

	public String getHealthFacility() {
        return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEALTH_FACILITY), this.encounter);
    }

    public void setHealthFacility(String facility) {
        updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEALTH_FACILITY), this.encounter, facility);
    }

    public String getTreatementSupporter() {
        return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TREATMENT_SUPPORTER), this.encounter);
    }

    public void setTreatementSupporter(String support) {
        updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TREATMENT_SUPPORTER), this.encounter, support);
    }

    public String getReferringDepartment() {
        return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REFERRED_BY), this.encounter);
    }

    public void setReferringDepartment(String referrer) {
        updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REFERRED_BY), this.encounter, referrer);
    }

    public String getDirectObserver() {
        return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DOTS_BY), this.encounter);
    }

    public void setDirectObserver(String observer) {
        updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DOTS_BY), this.encounter, observer);
    }

    public String getTreatmentStartDate() {
        return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TEST_START_DATE), this.encounter);
    }

    public void setTreatmentStartDate(String date) {
        updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TEST_START_DATE), this.encounter, date);
    }

	public String getSecondLineRegistrationDate() {
		return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDRTB_REGISTRATION_DATE), this.encounter);
	}

	public void setSecondLineRegistrationDate(String date) {
	    if (StringUtils.isNotEmpty(date)){
            updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDRTB_REGISTRATION_DATE), this.encounter, date);
        }
	}


	public String getSecondLineRegistrationNumber() {
		return fetchObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDRTB_REGISTRATION_NUMBER), this.encounter);
	}

	public void setSecondLineRegistrationNumber(String number) {
		updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDRTB_REGISTRATION_NUMBER), this.encounter, number);
	}

    public void setPatientStartedOnArt(String started, String startedOn) {
        updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ART_STARTED), this.encounter, started);
        updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ART_STARTED_ON), this.encounter, startedOn);
    }

	public void setPatientStartedOnCpt(String started, String startedOn) {
		updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CPT_STARTED), this.encounter, started);
		updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CPT_STARTED_ON), this.encounter, startedOn);
	}

    public void setSputumSmear(String date, String labNumber, String result){
        Obs obs =  new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SPUTUM_SMEAR_EXAM), encounter.getEncounterDatetime(), encounter.getLocation());
        encounter.addObs(obs);
        updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LAB_TEST_SERIAL_NUMBER), this.encounter, labNumber, obs);

        if (StringUtils.isNotEmpty(date) && StringUtils.isNotEmpty(result)){
            updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SPUTUM_COLLECTION_DATE), this.encounter, date, obs);
            updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SMEAR_RESULT), this.encounter, result, obs);
        }
	}

	public void setGenXpert(String date, String result){
	    if (StringUtils.isNotEmpty(date) && StringUtils.isNotEmpty(result)){
            Obs obs =  new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.GENXPERT_EXAM), encounter.getEncounterDatetime(), encounter.getLocation());
            encounter.addObs(obs);

            updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.GENXPERT_RESULTS), this.encounter, result, obs);
            updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.GENXPERT_DATE), this.encounter, date, obs);
        }
	}

	public void setHivResults(String date, String result){
	    if (StringUtils.isNotEmpty(date) && StringUtils.isNotEmpty(result)){
            Obs obs =  new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HIV_EXAM), encounter.getEncounterDatetime(), encounter.getLocation());
            encounter.addObs(obs);

            updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RESULT_OF_HIV_TEST), this.encounter, result, obs);
            updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HIV_EXAM_DATE), this.encounter, date, obs);
        }
	}

	public void setXrayResults(String date, String result){
        if (StringUtils.isNotEmpty(date) && StringUtils.isNotEmpty(result)){
            Obs obs =  new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.XRAY_EXAM), encounter.getEncounterDatetime(), encounter.getLocation());
            encounter.addObs(obs);

            updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.XRAY_RESULTS), this.encounter, result, obs);
            updateObs(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.XRAY_DATE), this.encounter, date, obs);
        }
	}
	
	/**
	 * Utility method that fetches the obs off the encounter that is associated with the specified concept
	 */
	private String fetchObs(Concept concept, Encounter encounter) {
		Obs obs = MdrtbUtil.getObsFromEncounter(concept, encounter);
		if (obs == null) {
			return null;
		}
		else {
			// get the value based on the datatype
			if (concept.getDatatype().equals(Context.getConceptService().getConceptDatatypeByName("NUMERIC"))) {
				return obs.getValueNumeric().toString();
			} 
			else {
			    return obs.getValueText();
			}
		}
	}
	
	
	/**
	 * Utility method that fetches the obs on the encounter that is associated with the specified concept
	 * and updates it if needed
	 */
	private void updateObs(Concept concept, Encounter encounter, String value) {
		Obs obs = MdrtbUtil.getObsFromEncounter(concept, encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && StringUtils.isBlank(value)) {
			return;
		}
		
		// we only have to update this if the value has changed or this is a new obs
		if (obs == null || !StringUtils.equals(obs.getValueText(), value)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module");
			}
				
			// now create the new Obs and add it to the encounter
			if(StringUtils.isNotBlank(value)) {
				obs = new Obs (encounter.getPatient(), concept, encounter.getEncounterDatetime(), encounter.getLocation());
                setResultDatatype(concept, value, obs);
                encounter.addObs(obs);
			}
		}
	}

    private void updateObs(Concept concept, Encounter encounter, String value, Obs obsgroup) {
        // if this obs have not been created, and there is no data to add, do nothing
        if (StringUtils.isBlank(value)) {
            return;
        }

        // now create the new Obs and add it to the encounter
        Obs obs = new Obs (encounter.getPatient(), concept, encounter.getEncounterDatetime(), encounter.getLocation());
        obs.setObsGroup(obsgroup);

        setResultDatatype(concept, value, obs);
        encounter.addObs(obs);

    }

    private void setResultDatatype(Concept concept, String value, Obs obs) {
        if (concept.getDatatype().equals(Context.getConceptService().getConceptDatatypeByName("NUMERIC"))) {
			obs.setValueNumeric(Double.valueOf(value));
        }
        else if (concept.getDatatype().getName().equals("Coded")) {
            obs.setValueCoded(Context.getConceptService().getConcept(Integer.parseInt(value)));
        }
        else {
            obs.setValueText(value);
        }
    }

}
