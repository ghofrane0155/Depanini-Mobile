/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.Entities;

import java.util.Date;

/**
 *
 * @author Ghof
 */
public class User {
     private double idUser;
    private String nomUser;
    private String prenomUser;
    private String login;
    private String password;
    private Date dateNaisUser;
    private String email;
    private String adresse;
    private String tel;
    private String sexe;
    private String roles;
    private String photoUser;
   // private Session session;
    
    public static User Current_User;

    public User() {
    }

    public User(int idUser, String nomUser, String prenomUser, String login, String password, Date dateNaisUser, String email, String adresse, String tel, String sexe,String roles, String photoUser) {
        this.idUser = idUser;
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.login = login;
        this.password = password;
        this.dateNaisUser = dateNaisUser;
        this.email = email;
        this.adresse = adresse;
        this.tel = tel;
        this.sexe = sexe;
        this.roles = roles;
        this.photoUser = photoUser;
    }

    public User(String nomUser, String prenomUser, String login, String password, Date dateNaisUser, String email, String adresse, String tel, String sexe,String roles, String photoUser) {
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.login = login;
        this.password = password;
        this.dateNaisUser = dateNaisUser;
        this.email = email;
        this.adresse = adresse;
        this.tel = tel;
        this.sexe = sexe;
        this.roles = roles;
        this.photoUser = photoUser;
    }

    public User(String nomUser, String prenomUser, String login, String password, Date dateNaisUser, String email, String adresse, String tel, String sexe,String roles) {
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.login = login;
        this.password = password;
        this.dateNaisUser = dateNaisUser;
        this.email = email;
        this.adresse = adresse;
        this.tel = tel;
        this.sexe = sexe;
        this.roles = roles;
    }

    public User( String email , String password) {
        this.email = email;
        this.password = password;
     
    }

    public User(int id, String login, String password, String nom, String prenom, String email, String roles) {
         this.idUser = id;
        this.login = login;
        this.password = password;
        this.nomUser = nom;
        this.prenomUser = prenom;
        this.email = email;
        this.roles = roles;
    }

    public User(User user) {
        this.idUser = idUser;
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.login = login;
        this.password = password;
        this.dateNaisUser = dateNaisUser;
        this.email = email;
        this.adresse = adresse;
        this.tel = tel;
        this.sexe = sexe;
        this.roles = roles;
        this.photoUser = photoUser;
    }

    public User(String text, String text0, String text1, String text2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public double getIdUser() {
        return idUser;
    }

    public String getNomUser() {
        return nomUser;
    }

    public String getPrenomUser() {
        return prenomUser;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateNaisUser() {
        return dateNaisUser;
    }

    public String getEmail() {
        return email;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTel() {
        return tel;
    }

    public String getSexe() {
        return sexe;
    }

    public String getRoles() {
        return roles;
    }

    public String getPhotoUser() {
        return photoUser;
    }
    public static User getCurrent_User() {
        return Current_User;
    }

    public void setIdUser(double idUser) {
        this.idUser = idUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateNaisUser(Date dateNaisUser) {
        this.dateNaisUser = dateNaisUser;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setPhotoUser(String photoUser) {
        this.photoUser = photoUser;
    }
    public static void setCurrent_User(User Current_User) {
        User.Current_User = Current_User;
    }

/*************************************************************/
    @Override
    public String toString() {
        return "user{" + "idUser=" + idUser + ", nomUser=" + nomUser + ", prenomUser=" + prenomUser + ", login=" + login + ", password=" + password + ", dateNaisUser=" + dateNaisUser + ", email=" + email + ", adresse=" + adresse + ", tel=" + tel + ", sexe=" + sexe + ", roles=" + roles + ", photoUser=" + photoUser + '}';
    }
/************************************************************/ 
 
 
        
}