package org.openmrs.module.mdrtb.model;

import org.openmrs.PatientProgram;
import org.openmrs.Program;

/**
 * Created by Dennis Henry
 * Created on 8/3/2017.
 */
public class VisitTypes {
    private Integer id;
    private String name;
    private Program program;
    private Boolean initialVisit;
    private Boolean finalVisit;
    private Boolean voided;

    public final static String TB_VISIT_00 = "Before Treatment";
    public final static String TB_VISIT_02 = "2-3 Months";
    public final static String TB_VISIT_05 = "5 Months";
    public final static String TB_VISIT_08 = "End of Treatment";


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Boolean getInitialVisit() {
        return initialVisit;
    }

    public void setInitialVisit(Boolean initialVisit) {
        this.initialVisit = initialVisit;
    }

    public Boolean getFinalVisit() {
        return finalVisit;
    }

    public void setFinalVisit(Boolean finalVisit) {
        this.finalVisit = finalVisit;
    }

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

}
