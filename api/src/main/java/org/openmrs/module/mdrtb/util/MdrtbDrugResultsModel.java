package org.openmrs.module.mdrtb.util;

import org.openmrs.Concept;

/**
 * Created by Dennis Henry
 * Created on 3/22/2017.
 */
public class MdrtbDrugResultsModel {
    private Concept drug;
    private Concept result;

    public Concept getDrug() {
        return drug;
    }

    public void setDrug(Concept drug) {
        this.drug = drug;
    }

    public Concept getResult() {
        return result;
    }

    public void setResult(Concept result) {
        this.result = result;
    }
}
