/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.Services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.codename1.util.StringUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import tn.esprit.AppMobile.Entities.Events;

import tn.esprit.AppMobile.Utils.Statics;

/**
 *
 * @author noure
 */
public class EventsStat {
    ConnectionRequest req;
    static EventsStat instance = null;
    public static boolean resultOk = true;
    //util
    boolean resultOK = false;
    // list pour afficher GUI
    List<Events> events;
    // detail reclamation
    Events ev;
    
    public EventsStat() {
        req = new ConnectionRequest();
    }
    
    //Singleton
    public static EventsStat getInstance() {
        if (instance == null) {
            instance = new EventsStat();
        }
        return instance;
    }
    
    
    public int totalTickets( ) throws UnsupportedEncodingException {
        
       
        req = new ConnectionRequest();
            String fetchURL = Statics.BASE_URL +"/totalTickets";
            req.setUrl(fetchURL);
            req.setPost(false);
            NetworkManager.getInstance().addToQueueAndWait(req);
            String response = new String(req.getResponseData(), "UTF-8");
            return Integer.parseInt(response);
        

        
    }
    
    public int totalRevenue( )  throws UnsupportedEncodingException {
        
        req = new ConnectionRequest();
            String fetchURL = Statics.BASE_URL +"/totalRevenue";
            req.setUrl(fetchURL);
            req.setPost(false);
            NetworkManager.getInstance().addToQueueAndWait(req);
            String response = new String(req.getResponseData(), "UTF-8");
            return Integer.parseInt(response);
        
    }
    
    public int totalEvents( ) throws UnsupportedEncodingException {
        
            req = new ConnectionRequest();
            String fetchURL = Statics.BASE_URL +"/totalEvents";
            req.setUrl(fetchURL);
            req.setPost(false);
            NetworkManager.getInstance().addToQueueAndWait(req);
            String response = new String(req.getResponseData(), "UTF-8");
            return Integer.parseInt(response);
        
        }
        
     public int Chart( ) throws UnsupportedEncodingException {
        
            req = new ConnectionRequest();
            String fetchURL = Statics.BASE_URL +"/Chartapi";
            req.setUrl(fetchURL);
            req.setPost(false);
            NetworkManager.getInstance().addToQueueAndWait(req);
            String response = new String(req.getResponseData(), "UTF-8");
            return Integer.parseInt(response);
        
        } 
     
     
   

public List<Integer> getPieChartData() throws UnsupportedEncodingException {
    List<Integer> chartData = new ArrayList<>();

    ConnectionRequest req = new ConnectionRequest();
    String fetchURL = Statics.BASE_URL + "/Chartapi2";
    req.setUrl(fetchURL);
    req.setPost(false);
    NetworkManager.getInstance().addToQueueAndWait(req);
    byte[] responseData = req.getResponseData();
    String response = new String(responseData, "UTF-8");

    // Remove square brackets from the response
    response = response.replace('[', ' ').replace(']', ' ');

    // Split the response into individual values
        List<String> values = StringUtil.tokenize(response, ',');

    // Parse the values and add them to the list
    for (String value : values) {
        System.out.println(value.trim());
        chartData.add(Integer.parseInt(value.trim()));
        
    }

    return chartData;
}






     
     public List<Map<String, String>> getChartData() throws UnsupportedEncodingException, IOException {
        List<Map<String, String>> chartData = new ArrayList<>();

        ConnectionRequest req = new ConnectionRequest();
        String fetchURL = Statics.BASE_URL + "/Chartapi";
        req.setUrl(fetchURL);
        req.setPost(false);
        NetworkManager.getInstance().addToQueueAndWait(req);
        byte[] responseData = req.getResponseData();

        if (responseData != null) {
            String response = new String(responseData, "UTF-8");
            try {
                JSONParser parser = new JSONParser();
                Map<String, Object> json = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(response.getBytes())));

                if (json.containsKey("data")) {
                    List<Object> dataList = (List<Object>) json.get("data");
                    for (Object data : dataList) {
                        if (data instanceof Map) {
                            Map<String, String> chartEntry = new LinkedHashMap<>();
                            Map<String, Object> dataMap = (Map<String, Object>) data;
                            if (dataMap.containsKey("nomevent")) {
                                chartEntry.put("nomevent", (String) dataMap.get("nomevent"));
                            }
                            if (dataMap.containsKey("totalPrix")) {
                                chartEntry.put("totalPrix", (String) dataMap.get("totalPrix"));
                            }
                            chartData.add(chartEntry);
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return chartData;
    }
     

        
    }