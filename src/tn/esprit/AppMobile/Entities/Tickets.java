/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.Entities;

import java.util.Date;

/**
 *
 * @author noure
 */
public class Tickets {
    
    private Double idticket;
    private Double idevent;
    
    private int idUser;
    private Double prixtotale;
    private int quantite;
    private String login;

    public Tickets(Double idevent, Double idticket, int idUser, Double prixtotale, int quantite, String login) {
        this.idevent = idevent;
        this.idticket = idticket;
        this.idUser = idUser;
        this.prixtotale = prixtotale;
        this.quantite = quantite;
        this.login = login;
    }

    public Tickets(Double idevent, int idUser, Double prixtotale, int quantite, String login) {
        this.idevent = idevent;
        this.idUser = idUser;
        this.prixtotale = prixtotale;
        this.quantite = quantite;
        this.login = login;
    }

    public Tickets() {
    }

    public void setIdevent(Double idevent) {
        this.idevent = idevent;
    }

    public void setIdticket(Double idticket) {
        this.idticket = idticket;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setPrixtotale(Double prixtotale) {
        this.prixtotale = prixtotale;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Double getIdevent() {
        return idevent;
    }

    public Double getIdticket() {
        return idticket;
    }

    public int getIdUser() {
        return idUser;
    }

    public Double getPrixtotale() {
        return prixtotale;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "Tickets{" + "idevent=" + idevent + ", idticket=" + idticket + ", idUser=" + idUser + ", prixtotale=" + prixtotale + ", quantite=" + quantite + ", login=" + login + '}';
    }

    
   
    
}