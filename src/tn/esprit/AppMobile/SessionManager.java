/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile;

import com.codename1.io.Preferences;
import java.util.Date;

/**
 *
 * @author Lenovo
 */
public class SessionManager {
    
    public static Preferences pref ; // 3ibara memoire sghira nsajlo fiha data 
    
    // hethom données ta3 user lyt7b tsajlhom fi session  ba3d login 
    private static int idUser ; 
    private static String nomUser ; 
    private static String prenomUser; 
    private static String login ;
    private static String password ;
    private static Date dateNaisUser;
    private static String email ;
    private static String adresse ;
    private static String tel ;
    private static String sexe ;
    private static String roles ;
    private static String photoUser;

    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionManager.pref = pref;
    }

    public static int getIdUser() {
        return pref.get("idUser",idUser);// kif nheb njib id user connecté apres njibha men pref 
    }
    public static String getNomUser() {
        return pref.get("nomUser",nomUser);
    }
    public static String getPrenomUser() {
        return pref.get("prenomUser",prenomUser);
    }
    public static String getLogin() {
        return pref.get("login",login);
    }
    public static String getPassword() {
        return pref.get("password",password);
    }
    public static String getDateNaisUser() {
        return pref.get("dateNaisUser",dateNaisUser.toString());
    }
    public static String getEmail() {
        return pref.get("email",email);
    }
    public static String getAdresse() {
        return pref.get("adresse",adresse);
    }
    public static String getTel() {
        return pref.get("tel",tel);
    }
    public static String getSexe() {
        return pref.get("sexe",sexe);
    }
    public static String getRoles() {
        return pref.get("roles",roles);
    }
    public static String getPhotoUser() {
        return pref.get("photoUser",photoUser);
    }
///////////////////////////////////////////////////
    public static void setIdUser(int idUser) {
        pref.set("idUser",idUser);
    }
    public static void setNomUser(String nomUser) {
        pref.set("nomUser",nomUser);
    }
    public static void setPrenomUser(String prenomUser) {
        pref.set("prenomUser",prenomUser);
    }
    public static void setLogin(String login) {
        pref.set("login",login);
    }
    public static void setPassword(String password) {
        pref.set("password",password);
    }
    public static void setDateNaisUser(Date dateNaisUser) {
        pref.set("dateNaisUser",dateNaisUser.toString());
    }
    public static void setEmail(String email) {
        pref.set("email",email);
    }
    public static void setAdresse(String adresse) {
        pref.set("adresse",adresse);
    }public static void setTel(String tel) {
        pref.set("tel",tel);
    }
    public static void setSexe(String sexe) {
        pref.set("sexe",sexe);
    }  
    public static void setRoles(String roles) {
        pref.set("roles",roles);
    }
    public static void setPhotoUser(String photoUser) {
        pref.set("photoUser",photoUser);
    }
 
}
