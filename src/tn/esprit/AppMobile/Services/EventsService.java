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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import tn.esprit.AppMobile.Entities.Events;
import tn.esprit.AppMobile.Entities.Tickets;
import tn.esprit.AppMobile.Utils.Statics;



/**
 *
 * @author noure
 */
public class EventsService {
    ConnectionRequest req;
    static EventsService instance = null;
    public static boolean resultOk = true;
    //util
    boolean resultOK = false;
    // list pour afficher GUI
    List<Events> events;
    // detail reclamation
    Events ev;

    //Constructor 
    public EventsService() {
        req = new ConnectionRequest();
    }
    
    //Singleton
    public static EventsService getInstance() {
        if (instance == null) {
            instance = new EventsService();
        }
        return instance;
    }
    
   public List<Events> fetchEvents(){
        req = new ConnectionRequest();

        //1
        String fetchURL = Statics.BASE_URL + "/FetchEventsapi";

        //2
        req.setUrl(fetchURL);

        //3 [Get]
        req.setPost(false);

        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                events = parseEvents(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return events;
   }
    public List<Events> parseEvents(String jsonText){
       
            events= new ArrayList<>();
            
            JSONParser jp = new JSONParser();
         try {    
            Map<String, Object> EventsListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String, Object>> list = (List<Map<String, Object>>) EventsListJSON.get("root");
            
            for (Map<String,Object> item : list) {
                
                Events e= new Events();
                
                e.setIdevent((Double)item.get("idevent"));
                e.setNombrelimevent((Double)item.get("nombrelimevent"));
                e.setPrixevent((Double)item.get("prixevent"));
                e.setNomevent((String) item.get("nomevent"));
                e.setLieuevent((String) item.get("lieuevent"));
                e.setOrganisateurevent((String) item.get("organisateurevent"));
                e.setDescriptionevent((String) item.get("descriptionevent"));
                e.setImageevent((String) item.get("imageevent"));
                
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                
                Date dbEvent = null;
                try {

                    dbEvent = (Date) dateFormat.parse((String) item.get("datedebutevent"));
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
                e.setDatedebutevent(dbEvent);
                
                Date dfEvent = null;
                try {

                    dfEvent = (Date) dateFormat.parse((String) item.get("datefinevent"));
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
                e.setDatefinevent(dfEvent);
                
                events.add(e);

            } 
            
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
         return events;
    }
    
    
    public Events getEvent(Double id) {

        req = new ConnectionRequest();

        //1
        String fetchURL = Statics.BASE_URL + "/detailsEventapi/" + id;

        //2
        req.setUrl(fetchURL);

        //3 [Get]
        req.setPost(false);

        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    ev = fromJson(new String(req.getResponseData()));
                } catch (IOException ex) {
                     System.out.println(ex.getMessage());
                }
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return ev;
    }

    public Events fromJson(String jsonText) throws IOException {
        JSONParser jp = new JSONParser();
        ev = new Events();

        try {

            //2
            Map<String, Object> CompetitionsListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) CompetitionsListJSON.get("root");
            for (Map<String, Object> item : list) {

               

                ev.setIdevent((Double)item.get("idevent"));
                ev.setNombrelimevent((Double)item.get("nombrelimevent"));
                ev.setPrixevent((Double)item.get("prixevent"));
                ev.setNomevent((String) item.get("nomevent"));
                ev.setLieuevent((String) item.get("lieuevent"));
                ev.setOrganisateurevent((String) item.get("organisateurevent"));
                ev.setDescriptionevent((String) item.get("descriptionevent"));
                ev.setImageevent((String) item.get("imageevent"));
               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                
                Date dbEvent = null;
                try {

                    dbEvent = (Date) dateFormat.parse((String) item.get("datedebutevent"));
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
                ev.setDatedebutevent(dbEvent);
                
                Date dfEvent = null;
                try {

                    dfEvent = (Date) dateFormat.parse((String) item.get("datefinevent"));
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
                ev.setDatefinevent(dfEvent);
                
        
        }}
         catch (IOException ex) {
            System.out.println(ex.getMessage());
        
    }
return ev;
    }
    
    public boolean deleteEvent(int id ) {
        String url = Statics.BASE_URL +"/DeleteEventapi/"+id;
        
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
    
    public void createEvent(Events event) {
        
        String url =Statics.BASE_URL+"/events/createapi?nomevent="+event.getNomevent()+"&nombrelimevent="+event.getNombrelimevent()+"&lieuevent="+event.getLieuevent()+"&datedebutevent="+event.getDatedebutevent()+"&datefinevent="+event.getDatefinevent()+"&organisateurevent="+event.getOrganisateurevent()+"&descriptionevent="+event.getDescriptionevent()+"&imageevent="+event.getImageevent()+"&prixevent="+event.getPrixevent(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    public void UpdateEvent(Events event ,Double id) {
        
    int idint = (int) Math.round(id);


        String url =Statics.BASE_URL+"/updateEventapi/"+id+"?nomevent="+event.getNomevent()+"&nombrelimevent="+event.getNombrelimevent()+"&lieuevent="+event.getLieuevent()+"&datedebutevent="+event.getDatedebutevent()+"&datefinevent="+event.getDatefinevent()+"&organisateurevent="+event.getOrganisateurevent()+"&descriptionevent="+event.getDescriptionevent()+"&imageevent="+event.getImageevent()+"&prixevent="+event.getPrixevent(); 
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    
    
    public static double calculateEventIncome(Double eventId, List<Tickets> tickets) {
    double totalIncome = 0.0;
    for (Tickets ticket : tickets) {
      if (ticket.getIdevent()== eventId) {
        totalIncome += ticket.getPrixtotale();
      }
    }
    return totalIncome;
  }
    
   public double[] StatEvent( ) {
       double[] valeur = new double[12];

        String url = Statics.BASE_URL + "/eventsapi";
        req.setUrl(url);
        req.setPost(false);
        JSONParser json = new JSONParser();
        //String data = new String(req.getResponseData());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String responceData = new String(req.getResponseData());
                String[] ch = split(responceData.substring(1, responceData.length() - 1));
                for (int i = 0; i < 12; i++) {
                    System.out.println(ch[i]);
                    valeur[i] = Double.parseDouble(ch[i]);
                }
                req.removeResponseListener(this);
            }
        });

        try {
            NetworkManager.getInstance().addToQueueAndWait(req);
        } catch (Exception ex) {
            System.out.println("this is stat reclamation | " + ex.getMessage());
        }

        return valeur;
   } 
   public String[] split(String str) {
        ArrayList<String> splitArray = new ArrayList<>();
        StringTokenizer arr = new StringTokenizer(str, ",");//split by commas
        while (arr.hasMoreTokens()) {
            splitArray.add(arr.nextToken());
        }
        return splitArray.toArray(new String[splitArray.size()]);
    }
    
}