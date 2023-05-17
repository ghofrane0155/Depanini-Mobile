/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.Entities;

import java.util.Date;

/**
 *
 * @author MSI
 */
public class Produit {
    private double idproduit,
                iduser,
                prixproduit,
                barecode;
    private String nomproduit,
                   categorieproduit,
                   description,
                   imageproduit;
    private Date datecreation;
    
    public Produit(){
    
    }

    public Produit(double prixproduit, double barecode, String nomproduit, String categorieproduit, String description, String imageproduit, Date datecreation) {
        this.prixproduit = prixproduit;
        this.barecode = barecode;
        this.nomproduit = nomproduit;
        this.categorieproduit = categorieproduit;
        this.description = description;
        this.imageproduit = imageproduit;
        this.datecreation = datecreation;
    }
    

    public Produit(double iduser, double prixproduit, double barecode, String nomproduit, String categorieproduit, String description, String imageproduit, Date datecreation) {
        this.iduser = iduser;
        this.prixproduit = prixproduit;
        this.barecode = barecode;
        this.nomproduit = nomproduit;
        this.categorieproduit = categorieproduit;
        this.description = description;
        this.imageproduit = imageproduit;
        this.datecreation = datecreation;
    }
    

    public Produit(double idproduit, double iduser, double prixproduit, double barecode, String nomproduit, String categorieproduit, String description, String imageproduit, Date datecreation) {
        this.idproduit = idproduit;
        this.iduser = iduser;
        this.prixproduit = prixproduit;
        this.barecode = barecode;
        this.nomproduit = nomproduit;
        this.categorieproduit = categorieproduit;
        this.description = description;
        this.imageproduit = imageproduit;
        this.datecreation = datecreation;
    }

    public Produit(double idproduit, double prixproduit, String nomproduit, String description) {
        this.idproduit = idproduit;
        this.prixproduit = prixproduit;
        this.nomproduit = nomproduit;
        this.description = description;
    }

   
    public Produit(double idproduit, double prixproduit, String nomproduit, String categorieproduit, String description) {
        this.idproduit = idproduit;
        this.prixproduit = prixproduit;
        this.nomproduit = nomproduit;
        this.categorieproduit = categorieproduit;
        this.description = description;
    }

    
    public double getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(double idproduit) {
        this.idproduit = idproduit;
    }

    public double getIduser() {
        return iduser;
    }

    public void setIduser(double iduser) {
        this.iduser = iduser;
    }

    public double getPrixproduit() {
        return prixproduit;
    }

    public void setPrixproduit(double prixproduit) {
        this.prixproduit = prixproduit;
    }

    public double getBarecode() {
        return barecode;
    }

    public void setBarecode(double barecode) {
        this.barecode = barecode;
    }

    public String getNomproduit() {
        return nomproduit;
    }

    public void setNomproduit(String nomproduit) {
        this.nomproduit = nomproduit;
    }

    public String getCategorieproduit() {
        return categorieproduit;
    }

    public void setCategorieproduit(String categorieproduit) {
        this.categorieproduit = categorieproduit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageproduit() {
        return imageproduit;
    }

    public void setImageproduit(String imageproduit) {
        this.imageproduit = imageproduit;
    }

    public Date getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
    }

    @Override
    public String toString() {
        return "Produit{idProduit="+idproduit + "idUser=" + iduser + ", nomProduit=" + nomproduit + ", categorieProduit=" + categorieproduit + ", description=" + description + ", prixProduit=" +prixproduit+ ", datecreation=" + datecreation +", barecode=" + barecode +  ", imageproduit=" + imageproduit +  '}';
    }

    
    
    
    
    
}