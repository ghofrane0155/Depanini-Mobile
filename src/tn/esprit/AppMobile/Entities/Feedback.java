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
public class Feedback {

    private double idFeedback;
    private String commentaire;
    private Date date;
    private double stars;
    private double idFreelancer;
    private double idClient;
    
    private String nomUser,prenomUser,photoUser;

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getPrenomUser() {
        return prenomUser;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public String getPhotoUser() {
        return photoUser;
    }

    public void setPhotoUser(String photoUser) {
        this.photoUser = photoUser;
    }

    
    
    public Feedback() {
    }

    public Feedback(double idFeedback, String commentaire, Date date, double stars, double idFreelancer, double idClient) {
        this.idFeedback = idFeedback;
        this.commentaire = commentaire;
        this.date = date;
        this.stars = stars;
        this.idFreelancer = idFreelancer;
        this.idClient = idClient;
    }

    public Feedback(String commentaire, Date date, double stars, double idFreelancer, double idClient) {
        this.commentaire = commentaire;
        this.date = date;
        this.stars = stars;
        this.idFreelancer = idFreelancer;
        this.idClient = idClient;
    }

    public double getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(double idFeedback) {
        this.idFeedback = idFeedback;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public double getIdFreelancer() {
        return idFreelancer;
    }

    public void setIdFreelancer(double idFreelancer) {
        this.idFreelancer = idFreelancer;
    }

    public double getIdClient() {
        return idClient;
    }

    public void setIdClient(double idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return "Feedback{" + "idFeedback=" + idFeedback + ", commentaire=" + commentaire + ", date=" + date + ", stars=" + stars + ", idFreelancer=" + idFreelancer + ", idClient=" + idClient + ", nomUser=" + nomUser + ", prenomUser=" + prenomUser + ", photoUser=" + photoUser + '}';
    }

   
    
    
    
    
}
