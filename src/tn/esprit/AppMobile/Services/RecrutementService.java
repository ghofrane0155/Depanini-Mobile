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
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import tn.esprit.AppMobile.Entities.Recrutement;
import tn.esprit.AppMobile.Utils.Statics;

/**
 *
 * @author yasmine
 */
public class RecrutementService {

    ConnectionRequest req;
    static RecrutementService instance = null;

   
      //util
    public static boolean resultOk = true;
    // list pour afficher GUI
    List<Recrutement> recrutements;
    // detail recrutements
    Recrutement r;

    //Constructor 
    private RecrutementService() {
        req = new ConnectionRequest();
    }

    //Singleton
    public static RecrutementService getInstance() {
        if (instance == null) {
            instance = new RecrutementService();
        }
        return instance;
    }

    public List<Recrutement> fetchRecrutements() {

        req = new ConnectionRequest();

        //1
        String fetchURL = Statics.BASE_URL + "/recrutement_get";

        //2
        req.setUrl(fetchURL);

        //3 [Get]
        req.setPost(false);
      
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                recrutements = parseRecrutements(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return recrutements;
    }

    // pour convertir du json en java pour faire la traduction +- 
    public List<Recrutement> parseRecrutements(String jsonText) {
        //var
        recrutements = new ArrayList<>();

        //DO
        //1
        JSONParser jp = new JSONParser();

        try {
            //2

            Map<String, Object> RecrutementListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
           
            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) RecrutementListJSON.get("root");
           
            //4
            for (Map<String, Object> item : list) {
                
                Recrutement r = new Recrutement();
                r.setReference((Double) item.get("reference"));
                r.setSalaire((Double) item.get("salaire"));
                r.setDescription((String) item.get("description"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                r.setNom((String) item.get("nom"));
                r.setIdclient((Double) item.get("idclient"));

                Date date = null;
                try {

                    date = (Date) dateFormat.parse((String) item.get("date"));
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
                r.setDate(date);
                Date dateResolution = null;
                recrutements.add(r);
             }
             }catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return recrutements;

    }

    public Recrutement getRecrutement(Double id) {

        req = new ConnectionRequest();

        //1
        String fetchURL = Statics.BASE_URL + "/detailsRecrutements_api/" + id;

        //2
        req.setUrl(fetchURL);

        //3 [Get]
        req.setPost(false);

        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                r = fromJson(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return r;
    }

    public Recrutement fromJson(String jsonText) {
        JSONParser jp = new JSONParser();
        r = new Recrutement();

        try {

            //2
            Map<String, Object> CompetitionsListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) CompetitionsListJSON.get("root");
            for (Map<String, Object> item : list) {

                r.setReference((Double) item.get("reference"));
                r.setSalaire((Double) item.get("salaire"));
                r.setDescription((String) item.get("description"));

                r.setNom((String) item.get("nom"));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                r.setIdclient((Double) item.get("idclient"));

                Date date = null;
               
                try {
                    date = (Date) dateFormat.parse((String) item.get("date"));
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
                r.setDate(date);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }

    
     public boolean deleteRecrutement(Double id ) {
        String url = Statics.BASE_URL +"/deleteRecrutement_api/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {               
                    req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
    
     
     public void addRecrutement(Recrutement r ) {
        String url = Statics.BASE_URL +"/ajout_recrutement_api?salaire="+r.getSalaire()+"&description="+r.getDescription()+"&nom="+r.getNom();
        req.setUrl(url);
        System.out.println(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    }

    public void updateRecrutement(Recrutement r) {
            String url = Statics.BASE_URL +"/update_recrutement_api/"+r.getReference()+"?salaire="+r.getSalaire()+"&description="+r.getDescription()+"&nom="+r.getNom();
      
        req.setUrl(url);
        System.out.println(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }

    
}
