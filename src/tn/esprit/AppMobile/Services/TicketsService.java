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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.AppMobile.Entities.Events;
import tn.esprit.AppMobile.Entities.Tickets;
import static tn.esprit.AppMobile.Services.EventsService.resultOk;
import tn.esprit.AppMobile.Utils.Statics;

/**
 *
 * @author noure
 */
public class TicketsService {
    ConnectionRequest req;
    static TicketsService instance = null;
    public static boolean resultOk = true;
    //util
    boolean resultOK = false;
    // list pour afficher GUI
    List<Tickets> tickets;
    // detail reclamation
    Tickets t;

    //Constructor 
    public TicketsService() {
        req = new ConnectionRequest();
    }
      public static TicketsService getInstance() {
        if (instance == null) {
            instance = new TicketsService();
        }
        return instance;
    }
      
     public List<Tickets> fetchTickets() {
    req = new ConnectionRequest();

    // 1. Update the fetchURL variable
    String fetchURL = Statics.BASE_URL + "/ticketOfThisUserapi";

    // 2. Set the URL for the request
    req.setUrl(fetchURL);

    // 3. Set request method to GET
    req.setPost(false);

    // 4. Add a response listener to parse the JSON response
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            tickets = parseTickets(new String(req.getResponseData()));
            req.removeResponseListener(this);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);

    return tickets;
}
     
      public List<Tickets> parseTickets(String jsonText) {
    List<Tickets> tickets = new ArrayList<>();

    try {
        JSONParser jp = new JSONParser();
        Map<String, Object> data = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("root");

        for (Map<String,Object> item : list) {
           
            Double idticket = (Double) item.get("idticket");
            
            Map<String, Object> ideventObj = (Map<String, Object>) item.get("idevent");
            Double idevent = ((Double) ideventObj.get("idevent"));
            int idUser = ((Double) ((Map<String, Object>) item.get("idUser")).get("idUser")).intValue();
            double prixtotale = (Double) item.get("prixtotale");
            int quantite = ((Double) item.get("quantite")).intValue();
            String login = (String) item.get("login");

            Tickets ticket = new Tickets(idticket,idevent, idUser, prixtotale, quantite, login);
            tickets.add(ticket);
        }
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }

    return tickets;
}




      
      public void createTicket(Tickets ticket,int id) {
        
        String url =Statics.BASE_URL+"/addTicketapi?idevent="+id+"&idUser="+ticket.getIdUser()+"&login="+ticket.getLogin()+"&quantite="+ticket.getQuantite()+"&prixtotale="+ticket.getPrixtotale(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
      
      public boolean deleteTicket (int id ) {
        String url = Statics.BASE_URL +"/DeleteTicketapi/"+id;
        
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
      
      
}