/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.AppMobile.Entities.Offre;
import tn.esprit.AppMobile.Utils.Statics;

/**
 *
 * @author leila
 */
public class OffreService {

    public ArrayList<Offre> offres;

    public static OffreService instance = null;
    public boolean resultOK;
    private ConnectionRequest cr;

    private OffreService() {
        cr = new ConnectionRequest();
    }

    public static OffreService getInstance() {
        if (instance == null) {
            instance = new OffreService();
        }
        return instance;
    }

    public boolean addOffre(Offre o) {

        String url = Statics.BASE_URL + "/add_offreApi?idUser="+o.getIdUser()+"&prix=" + o.getPrixOffre() + "&description=" + o.getDescriptionOffre() + "&localisation=" + o.getLocalisationOffre() + "&nom=" + o.getNomOffre() + "&type=" + o.getTypeOffre()+"&idCategorie="+o.getIdCategorie();

        cr.setUrl(url);
        cr.setPost(false);

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = cr.getResponseCode() == 200; //Code HTTP 200 OK
                cr.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cr);
        return resultOK;
    }

    
    public void updateOffre(double id,Offre o) {

       String url = Statics.BASE_URL + "/update_offreApi/"+id+ "?prix="+ o.getPrixOffre() + "&description=" + o.getDescriptionOffre()+ "&localisation="+o.getLocalisationOffre()+ "&nom="+o.getNomOffre();
       
       
       System.out.println(url);
        ConnectionRequest req = new ConnectionRequest();

        req.setUrl(url);
        req.setPost(false);

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    
    
    public ArrayList<Offre> parseOffre(String jsonText) {
        try {
            offres = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> StatutListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) StatutListJson.get("root");

            for (Map<String, Object> obj : list) {
                

                Offre coach = new Offre();

                coach.setIdOffre((double) obj.get("idOffre"));
                coach.setNomOffre(obj.get("nomOffre").toString());
                coach.setTypeOffre((String) obj.get("typeOffre"));
                coach.setDescriptionOffre((String) obj.get("descriptionOffre"));               
                coach.setLocalisationOffre((String) obj.get("localisationOffre"));                        
                coach.setPrixOffre((Double) obj.get("prixOffre"));
                

               
                offres.add(coach);
            }
        } catch (IOException ex) {
            System.out.println("Exception in parsing statuts ");
        }
        return offres;
    }

    public ArrayList<Offre> getAllOffres() {
        String url = Statics.BASE_URL + "/offre_get";
        cr.setUrl(url);
        System.out.println(url);
        cr.setPost(false);
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                offres = parseOffre(new String(cr.getResponseData()));
                System.out.println(offres);
                cr.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cr);
        return offres;
    }

   

    
    public boolean delete(int id ) {
        String url = Statics.BASE_URL +"/Deleteoffre_api/"+id;
        
        cr.setUrl(url);
        
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    cr.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(cr);
        return  resultOK;
    }

}