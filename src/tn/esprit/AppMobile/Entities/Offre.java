/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.Entities;

import java.util.Date;

/**
 *
 * @author leila
 */
public class Offre {
    private double idOffre;
    private Double prixOffre;
    private String descriptionOffre;
    private String localisationOffre;
    private String nomOffre;
    private String imageOffre;  
    private String typeOffre; 
    private int idUser;
    private int idCategorie;

    @Override
    public String toString() {
        return "Offre{" + "idOffre=" + idOffre + ", prixOffre=" + prixOffre + ", descriptionOffre=" + descriptionOffre + ", localisationOffre=" + localisationOffre + ", nomOffre=" + nomOffre + ", imageOffre=" + imageOffre + ", typeOffre=" + typeOffre + ", idUser=" + idUser + ", idCategorie=" + idCategorie + '}';
    }

    
    public Offre(Double prixOffre, String descriptionOffre, String localisationOffre, String nomOffre, String typeOffre, int idUser, int idCategorie) {
        this.prixOffre = prixOffre;
        this.descriptionOffre = descriptionOffre;
        this.localisationOffre = localisationOffre;
        this.nomOffre = nomOffre;
        this.imageOffre = imageOffre;
        this.typeOffre = typeOffre;
        this.idUser = idUser;
        this.idCategorie = idCategorie;
    }

    
    
    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }


    public Offre() {
    }

    public Offre(double idOffre, Double prixOffre, String descriptionOffre, String localisationOffre, String nomOffre, String imageOffre, String typeOffre, int idUser) {
        this.idOffre = idOffre;
        this.prixOffre = prixOffre;
        this.descriptionOffre = descriptionOffre;
        this.localisationOffre = localisationOffre;
        this.nomOffre = nomOffre;
        this.imageOffre = imageOffre;
        this.typeOffre = typeOffre;
        this.idUser = idUser;
    }

    public Offre(Double prixOffre, String descriptionOffre, String localisationOffre, String nomOffre, String imageOffre, String typeOffre, int idUser) {
        this.prixOffre = prixOffre;
        this.descriptionOffre = descriptionOffre;
        this.localisationOffre = localisationOffre;
        this.nomOffre = nomOffre;
        this.imageOffre = imageOffre;
        this.typeOffre = typeOffre;
        this.idUser = idUser;
    }

    public double getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(double idOffre) {
        this.idOffre = idOffre;
    }

    public Double getPrixOffre() {
        return prixOffre;
    }

    public void setPrixOffre(Double prixOffre) {
        this.prixOffre = prixOffre;
    }

    public String getDescriptionOffre() {
        return descriptionOffre;
    }

    public void setDescriptionOffre(String descriptionOffre) {
        this.descriptionOffre = descriptionOffre;
    }

    public String getLocalisationOffre() {
        return localisationOffre;
    }

    public void setLocalisationOffre(String localisationOffre) {
        this.localisationOffre = localisationOffre;
    }

    public String getNomOffre() {
        return nomOffre;
    }

    public void setNomOffre(String nomOffre) {
        this.nomOffre = nomOffre;
    }

    public String getImageOffre() {
        return imageOffre;
    }

    public void setImageOffre(String imageOffre) {
        this.imageOffre = imageOffre;
    }

    public String getTypeOffre() {
        return typeOffre;
    }

    public void setTypeOffre(String typeOffre) {
        this.typeOffre = typeOffre;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    


}