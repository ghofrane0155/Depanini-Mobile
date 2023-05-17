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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tn.esprit.AppMobile.Entities.Reclamation;
import tn.esprit.AppMobile.Utils.Statics;

/**
 *
 * @author Mega-pc
 */
public class ReclamationService {

    ConnectionRequest req;
    static ReclamationService instance = null;

    //util
    public static boolean resultOk = true;
    // list pour afficher GUI
    List<Reclamation> reclamations;
    // detail reclamation
    Reclamation r;

    //Constructor 
    private ReclamationService() {
        req = new ConnectionRequest();
    }

    //Singleton
    public static ReclamationService getInstance() {
        if (instance == null) {
            instance = new ReclamationService();
        }
        return instance;
    }

    public void ajoutReclamation(String type, String description) {

        String url = Statics.BASE_URL + "/addReclamation_api?type=" + type + "&description=" + description;

        req.setUrl(url);
        System.out.println(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

    }

    public int getcountrec() throws UnsupportedEncodingException {
        req = new ConnectionRequest();
        String fetchURL = Statics.BASE_URL + "/nbrre_api";
        req.setUrl(fetchURL);
        req.setPost(false);
        NetworkManager.getInstance().addToQueueAndWait(req);
        String response = new String(req.getResponseData(), "UTF-8");
        return Integer.parseInt(response);
    }

     public int getcountrecresolu() throws UnsupportedEncodingException {
        req = new ConnectionRequest();
        String fetchURL = Statics.BASE_URL + "/nbrre_resolu_api";
        req.setUrl(fetchURL);
        req.setPost(false);
        NetworkManager.getInstance().addToQueueAndWait(req);
        String response = new String(req.getResponseData(), "UTF-8");
        return Integer.parseInt(response);
    }
    
 public int getcountrecouvert() throws UnsupportedEncodingException {
        req = new ConnectionRequest();
        String fetchURL = Statics.BASE_URL + "/nbrre_ouvert_api";
        req.setUrl(fetchURL);
        req.setPost(false);
        NetworkManager.getInstance().addToQueueAndWait(req);
        String response = new String(req.getResponseData(), "UTF-8");
        return Integer.parseInt(response);
    }

       //Delete 
    public boolean deleteReclamation(Double id ) {
        String url = Statics.BASE_URL +"/deleteReclamation_api/"+id;
        
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
    
    
    public List<Reclamation> fetchReclamations() {

        req = new ConnectionRequest();

        //1
        String fetchURL = Statics.BASE_URL + "/reclamation_get";

        //2
        req.setUrl(fetchURL);

        //3 [Get]
        req.setPost(false);

        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reclamations = parseReclamations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return reclamations;
    }

    // pour convertir du json en java pour faire la traduction +- 
    public List<Reclamation> parseReclamations(String jsonText) {
        //var
        reclamations = new ArrayList<>();

        //DO
        //1
        JSONParser jp = new JSONParser();

        try {
            //2

            Map<String, Object> ReclamationListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) ReclamationListJSON.get("root");

            //4
            for (Map<String, Object> item : list) {

                Reclamation r = new Reclamation();
                r.setIdReclamation((Double) item.get("idReclamation"));
                r.setType((String) item.get("type"));
                r.setDescription((String) item.get("description"));

                r.setPieceJointe((String) item.get("pieceJointe"));
                r.setStatut((String) item.get("statut"));

                r.setIdAdmin((Double) item.get("idAdmin"));
                r.setIdUser((Double) item.get("idUser"));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateReclamation = null;
                try {
                    dateReclamation = (Date) dateFormat.parse((String) item.get("dateReclamation"));
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
                r.setDateReclamation(dateReclamation);

                String dateStr = (String) item.get("dateResolution");
                Date dateResolution = null;
                if (dateStr != null) {
                    try {
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM-dd-yyyy");
                        dateResolution = dateFormat2.parse(dateStr);
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                }
                r.setDateResolution(dateResolution);
                
                reclamations.add(r);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
System.out.println(reclamations.toString());
        return reclamations;

    }

    public Reclamation getReclamation(Double id) {

        req = new ConnectionRequest();

        //1
        String fetchURL = Statics.BASE_URL + "/detailsReclamation_api/" + id;

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

    public Reclamation fromJson(String jsonText) {
        JSONParser jp = new JSONParser();
        r = new Reclamation();

        try {

            //2
            Map<String, Object> CompetitionsListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) CompetitionsListJSON.get("root");
            for (Map<String, Object> item : list) {

                r.setIdReclamation((Double) item.get("idReclamation"));
                r.setType((String) item.get("type"));
                r.setDescription((String) item.get("description"));

                r.setPieceJointe((String) item.get("pieceJointe"));
                r.setStatut((String) item.get("statut"));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                r.setIdAdmin((Double) item.get("idAdmin"));
                r.setIdUser((Double) item.get("idUser"));

                Date dateReclamation = null;
                try {
                    dateReclamation = (Date) dateFormat.parse((String) item.get("dateReclamation"));
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
                r.setDateReclamation(dateReclamation);
                String dateStr = (String) item.get("dateResolution");
                Date dateResolution = null;               
                try {
                    if (dateStr != null){
                    dateResolution = (Date) dateFormat.parse((String) item.get("dateResolution"));
                    }
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
                r.setDateResolution(dateResolution);
            }
           
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }

    
    
     
     
}
