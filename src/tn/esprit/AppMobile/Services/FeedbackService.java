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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.AppMobile.Entities.Feedback;
import tn.esprit.AppMobile.Entities.User;
import tn.esprit.AppMobile.Utils.Statics;

/**
 *
 * @author Mega-pc
 */
public class FeedbackService {

    ConnectionRequest req;
    static FeedbackService instance = null;

    //util
    public static boolean resultOk = true;

    Feedback feedback;
    List<Feedback> feedbacks;
    
    User user;
    List<User> users ;
    //Constructor 

    private FeedbackService() {
        req = new ConnectionRequest();
    }

    //Singleton
    public static FeedbackService getInstance() {
        if (instance == null) {
            instance = new FeedbackService();
        }
        return instance;
    }

    public void updateFeedback(double feedbackId, double stars, String commentaire) {

        String url = Statics.BASE_URL + "/feedback/updatef_api/" + feedbackId + "?stars=" + stars + "&commentaire=" + commentaire;
        System.out.println(url);
        ConnectionRequest req = new ConnectionRequest();

        req.setUrl(url);
        req.setPost(false);

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

      public void ajoutFeedback(int Curent_user,int id_client,double stars, String commentaire) {

        String url = Statics.BASE_URL + "/feedback/add_api/"+Curent_user+"?idUser="+ id_client + "&stars=" + stars+ "&commentaire=" + commentaire;
        req.setUrl(url);
        System.out.println(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

    }
  
    
    
    public int feedback_total_stars_api() throws UnsupportedEncodingException {
        req = new ConnectionRequest();
        String fetchURL = Statics.BASE_URL + "/feedback/feedback_total_stars_api";
        req.setUrl(fetchURL);
        req.setPost(false);
        NetworkManager.getInstance().addToQueueAndWait(req);
        String response = new String(req.getResponseData(), "UTF-8");
        return Integer.parseInt(response);
    }

    public List<Feedback> fetchFeedback() {

        req = new ConnectionRequest();

        //1
        String fetchURL = Statics.BASE_URL + "/feedback/user_details_api";

        //2
        req.setUrl(fetchURL);

        //3 [Get]
        req.setPost(false);

        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                feedbacks = parseFeedback(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return feedbacks;
    }

    // pour convertir du json en java pour faire la traduction +- 
    public List<Feedback> parseFeedback(String jsonText) {
        //var
        feedbacks = new ArrayList<>();

        //DO
        //1
        JSONParser jp = new JSONParser();

        try {
            //2

            Map<String, Object> FeedbackListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) FeedbackListJSON.get("root");

            //4
            for (Map<String, Object> item : list) {

                Feedback f = new Feedback();

                f.setNomUser((String) item.get("nomUser"));
                f.setPrenomUser((String) item.get("prenomUser"));
                f.setPhotoUser((String) Statics.BASE_URL +"/"+ item.get("photoUser"));
               

                f.setIdFeedback((double) item.get("idFeedback"));
                f.setCommentaire((String) item.get("commentaire"));
                f.setStars((double) item.get("stars"));

                feedbacks.add(f);

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return feedbacks;

    }

    public boolean deleteFeedback(double idFeedback) {

        String url = Statics.BASE_URL + "/feedback/deletefeedback_api/" + idFeedback;

        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return resultOk;
    }


    
      public List<User> getlistUsers() {

        req = new ConnectionRequest();

        //1
        String fetchURL = Statics.BASE_URL + "/feedback/get_all_user_api";
         
        //2
        req.setUrl(fetchURL);

        //3 [Get]
        req.setPost(false);

        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUser(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return users;
    }
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!
//----------------------------------------------!!!!!!!!!!!!!!!!!

    // pour convertir du json en java pour faire la traduction +- 
    public List<User> parseUser(String jsonText) {
        //var
        users = new ArrayList<>();
       
        //DO
        //1
        JSONParser jp = new JSONParser();

        try {
            //2

            Map<String, Object> FeedbackListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) FeedbackListJSON.get("root");

            //4
            for (Map<String, Object> item : list) {
                
              
                
                User u = new User();               
                 u.setIdUser((double) item.get("idUser"));
                u.setNomUser((String) item.get("nomUser"));
                u.setPrenomUser((String) item.get("prenomUser"));
                u.setPhotoUser((String) item.get("photoUser"));

                users.add(u);

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return users;

    }

}
