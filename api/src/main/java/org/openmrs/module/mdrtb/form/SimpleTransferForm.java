package org.openmrs.module.mdrtb.form;

import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;

/**
 * Created by Dennis Henry
 * Created on 4/18/2017.
 */
public class SimpleTransferForm extends AbstractSimpleForm {
    public SimpleTransferForm(){
        super();
        this.encounter.setEncounterType(Context.getEncounterService().getEncounterType(Context.getAdministrationService().getGlobalProperty("mdrtb.transfer_encounter_type")));
    }

    public SimpleTransferForm(Patient patient){
        super(patient);
        this.encounter.setEncounterType(Context.getEncounterService().getEncounterType(Context.getAdministrationService().getGlobalProperty("mdrtb.transfer_encounter_type")));
    }

    public SimpleTransferForm(Encounter encounter) {
        super(encounter);
    }
}
