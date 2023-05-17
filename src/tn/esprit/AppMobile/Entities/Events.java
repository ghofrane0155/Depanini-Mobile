/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.Entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author noure
 */
public class Events {
    
     private Double idevent;
    private String nomevent;
    private Double nombrelimevent;
    private String lieuevent;
    private Date datedebutevent;
    private Date datefinevent;
    private String organisateurevent;
    private String descriptionevent;
    private String imageevent;
    private Double prixevent;

    public Events() {
    }

    public void setPrixevent(Double prixevent) {
        this.prixevent = prixevent;
    }

    public Double getPrixevent() {
        return prixevent;
    }

    public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", idevent);
    map.put("name", nomevent);
    map.put("description", descriptionevent);
    map.put("location", lieuevent);
    map.put("organizer", organisateurevent);
    map.put("date", datedebutevent);
    return map;
}

    public Events(Double idevent, String nomevent, Double nombrelimevent, String lieuevent, Date datedebutevent, Date datefinevent, String organisateurevent, String descriptionevent, String imageevent, Double prixevent) {
        this.idevent = idevent;
        this.nomevent = nomevent;
        this.nombrelimevent = nombrelimevent;
        this.lieuevent = lieuevent;
        this.datedebutevent = datedebutevent;
        this.datefinevent = datefinevent;
        this.organisateurevent = organisateurevent;
        this.descriptionevent = descriptionevent;
        this.imageevent = imageevent;
        this.prixevent = prixevent;
    }

    public Events(String nomevent, Double nombrelimevent, String lieuevent, Date datedebutevent, Date datefinevent, String organisateurevent, String descriptionevent, String imageevent, Double prixevent) {
        this.nomevent = nomevent;
        this.nombrelimevent = nombrelimevent;
        this.lieuevent = lieuevent;
        this.datedebutevent = datedebutevent;
        this.datefinevent = datefinevent;
        this.organisateurevent = organisateurevent;
        this.descriptionevent = descriptionevent;
        this.imageevent = imageevent;
        this.prixevent = prixevent;
    }

    
    public Double getIdevent() {
        return idevent;
    }

    public String getNomevent() {
        return nomevent;
    }

    public Double getNombrelimevent() {
        return nombrelimevent;
    }

    public String getLieuevent() {
        return lieuevent;
    }

    public Date getDatedebutevent() {
        return datedebutevent;
    }

    public Date getDatefinevent() {
        return datefinevent;
    }

    @Override
    public String toString() {
        return "Events{" + "idevent=" + idevent + ", nomevent=" + nomevent + ", nombrelimevent=" + nombrelimevent + ", lieuevent=" + lieuevent + ", datedebutevent=" + datedebutevent + ", datefinevent=" + datefinevent + ", organisateurevent=" + organisateurevent + ", descriptionevent=" + descriptionevent + ", imageevent=" + imageevent + '}';
    }

    public String getOrganisateurevent() {
        return organisateurevent;
    }

    public String getDescriptionevent() {
        return descriptionevent;
    }

    public String getImageevent() {
        return imageevent;
    }

    public void setIdevent(Double idevent) {
        this.idevent = idevent;
    }

    public void setNomevent(String nomevent) {
        this.nomevent = nomevent;
    }

    public void setNombrelimevent(Double nombrelimevent) {
        this.nombrelimevent = nombrelimevent;
    }

    public void setLieuevent(String lieuevent) {
        this.lieuevent = lieuevent;
    }

    public void setDatedebutevent(Date datedebutevent) {
        this.datedebutevent = datedebutevent;
    }

    public void setDatefinevent(Date datefinevent) {
        this.datefinevent = datefinevent;
    }

    public void setOrganisateurevent(String organisateurevent) {
        this.organisateurevent = organisateurevent;
    }

    public void setDescriptionevent(String descriptionevent) {
        this.descriptionevent = descriptionevent;
    }

    public void setImageevent(String imageevent) {
        this.imageevent = imageevent;
    }
    
    
    
}