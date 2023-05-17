/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.Entities;

import java.util.Date;

/**
 *
 * @author Mega-pc
 */
public class Reclamation {

    private Double idReclamation;
    private String type;
    private String description;
    private Date dateReclamation;
    private String pieceJointe;
    private String statut;
    private Date dateResolution;
    private Double idAdmin;
    private Double idUser;

    public Reclamation() {
    }

    public Reclamation(Double idReclamation, String type, String description, Date dateReclamation, String pieceJointe, String statut, Date dateResolution, Double idAdmin, Double idUser) {
        this.idReclamation = idReclamation;
        this.type = type;
        this.description = description;
        this.dateReclamation = dateReclamation;
        this.pieceJointe = pieceJointe;
        this.statut = statut;
        this.dateResolution = dateResolution;
        this.idAdmin = idAdmin;
        this.idUser = idUser;
    }

    public Reclamation(String type, String description, Date dateReclamation, String pieceJointe, String statut, Date dateResolution, Double idAdmin, Double idUser) {
        this.type = type;
        this.description = description;
        this.dateReclamation = dateReclamation;
        this.pieceJointe = pieceJointe;
        this.statut = statut;
        this.dateResolution = dateResolution;
        this.idAdmin = idAdmin;
        this.idUser = idUser;
    }

    public Reclamation(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public Double getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(Double idReclamation) {
        this.idReclamation = idReclamation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateReclamation() {
        return dateReclamation;
    }

    public void setDateReclamation(Date dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    public String getPieceJointe() {
        return pieceJointe;
    }

    public void setPieceJointe(String pieceJointe) {
        this.pieceJointe = pieceJointe;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Date getDateResolution() {
        return dateResolution;
    }

    public void setDateResolution(Date dateResolution) {
        this.dateResolution = dateResolution;
    }

    public Double getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Double idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Double getIdUser() {
        return idUser;
    }

    public void setIdUser(Double idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "idReclamation=" + idReclamation + ", type=" + type + ", description=" + description + ", dateReclamation=" + dateReclamation + ", pieceJointe=" + pieceJointe + ", statut=" + statut + ", dateResolution=" + dateResolution + ", idAdmin=" + idAdmin + ", idUser=" + idUser + '}';
    }

}
