/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.Entities;

import java.util.Date;

/**
 *
 * @author yasmine
 */
public class Recrutement {

    private Double reference;
    private Double salaire;
    private String description;
    private String nom;
    private Date date;
    private Double idclient;

    public Recrutement() {
    }

    public Recrutement(Double reference, Double salaire, String description, String nom, Date date, Double idclient) {
        this.reference = reference;
        this.salaire = salaire;
        this.description = description;
        this.nom = nom;
        this.date = date;
        this.idclient = idclient;
    }

    public Recrutement(Double salaire, String description, String nom, Date date, Double idclient) {
        this.salaire = salaire;
        this.description = description;
        this.nom = nom;
        this.date = date;
        this.idclient = idclient;
    }

    public Recrutement(String nom, String description, double salaire) {
        this.salaire = salaire;
        this.description = description;
        this.nom = nom;

    }
     public Recrutement(Double reference,String nom, String description, double salaire) {
        this.reference = reference;
        this.salaire = salaire;
        this.description = description;
        this.nom = nom;

    }

    public Double getReference() {
        return reference;
    }

    public void setReference(Double reference) {
        this.reference = reference;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getIdclient() {
        return idclient;
    }

    public void setIdclient(Double idclient) {
        this.idclient = idclient;
    }

    @Override
    public String toString() {
        return "Recrutement{" + "reference=" + reference + ", salaire=" + salaire + ", description=" + description + ", nom=" + nom + ", date=" + date + ", idclient=" + idclient + '}';
    }

}
