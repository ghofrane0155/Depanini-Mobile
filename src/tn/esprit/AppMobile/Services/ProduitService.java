/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.Services;

import tn.esprit.AppMobile.Entities.Produit;
import static tn.esprit.AppMobile.Services.ProduitService.instance;

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
import tn.esprit.AppMobile.Utils.Statics;

/**
 *
 * @author MSI
 */
public class ProduitService {

    ConnectionRequest req;
    static ProduitService instance = null;
    //util
    boolean resultOK = false;
    // list pour afficher GUI
    List<Produit> produits;
    // detail reclamation
    Produit p;

    //Constructor 
    public ProduitService() {
        req = new ConnectionRequest();
    }

    public static ProduitService getInstance() {
        if (instance == null) {
            instance = new ProduitService();
        }
        return instance;
    }

    /**
     *
     */
    public void ajoutProduit(Produit produit) {

        String url = Statics.BASE_URL + "/addproduitAPI?"
                + "nomproduit=" + produit.getNomproduit()
                + "&categorieproduit=" + produit.getCategorieproduit()
                + "&description=" + produit.getDescription()
                + "&prixproduit=" + produit.getPrixproduit()
                + "&image=" + produit.getImageproduit()
                + "&iduser=" + produit.getIduser();

        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

    }

    public void updateProduit(Produit p) {

        String url = Statics.BASE_URL + "/produit/update/" + p.getIdproduit() + "?"
                + "nomProduit=" + p.getNomproduit()
                + "&categorieProduit=" + p.getCategorieproduit()
                + "&description=" + p.getDescription()
                + "&prixProduit=" + p.getPrixproduit();

        System.out.println(url);
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(url);
        req.setPost(false);

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public ArrayList<Produit> updateProduits() {
        ArrayList<Produit> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/produit_list";
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapProduits = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapProduits.get("root");

                    for (Map<String, Object> obj : listOfMaps) {
                        Produit pro = new Produit();

                        float idproduit = Float.parseFloat(obj.get("idproduit").toString());
                        String nom = obj.get("nomproduit").toString();
                        float prix = Float.parseFloat(obj.get("prix").toString());

                        pro.setIdproduit((double) idproduit);
                        pro.setNomproduit(nom);
                        pro.setPrixproduit((double) prix);

                        result.add(pro);
                    }

                    System.out.println("Number of products in the list: " + result.size());

                    // test the result list
                    for (Produit p : result) {
                        System.out.println(p.getNomproduit());
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        return result;
    }

    public Produit DetailProduit(int id, Produit produit) {

        String url = Statics.BASE_URL + "/detailproduit?" + id;
        req.setUrl(url);

        String str = new String(req.getResponseData());
        req.addResponseListener(((evt) -> {

            JSONParser jsonp = new JSONParser();
            try {

                Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));

                produit.setNomproduit(obj.get("nomproduit").toString());
                produit.setCategorieproduit(obj.get("categorie").toString());
                produit.setDescription(obj.get("description").toString());
                produit.setPrixproduit(Integer.parseInt(obj.get("prix").toString()));

            } catch (IOException ex) {
                System.out.println("error related to sql 🙁 " + ex.getMessage());
            }

            System.out.println("data === " + str);

        }));

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return produit;

    }

    public boolean deleteProduit(int idproduit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Produit> fetchProduits() {

        req = new ConnectionRequest();

        //1
        String fetchURL = Statics.BASE_URL + "/produit_list";

        //2
        req.setUrl(fetchURL);

        //3 [Get]
        req.setPost(true);

        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    produits = parseProduits(new String(req.getResponseData()));
                } catch (ParseException ex) {
                    System.out.println(".actionPerformed()");
                    System.out.println("Number of products in the list: " + produits.size());
                }
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return produits;
    }

    /**
     *
     * @param jsonText
     * @return
     */
    public List<Produit> parseProduits(String jsonText) throws ParseException {
        //var
        produits = new ArrayList<>();

        //DO
        //1
        JSONParser jp = new JSONParser();

        try {
            //2

            Map<String, Object> ProduitListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) ProduitListJSON.get("root");

            //4
            for (Map<String, Object> item : list) {
                System.out.println(item.toString());

                Produit pro = new Produit();
                pro.setIdproduit((double) item.get("idProduit"));

                pro.setIduser((double) item.get("idUser"));

                pro.setNomproduit((String) item.get("nomProduit"));

                pro.setPrixproduit((double) item.get("prixProduit"));

                pro.setCategorieproduit((String) item.get("categorieProduit"));

                pro.setDescription((String) item.get("description"));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                Date datecreation = null;
                try {

                    datecreation = (Date) dateFormat.parse((String) item.get("dateCreation"));
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
                pro.setDatecreation(datecreation);

//                 pro.setBarecode((double) item.get("barcode"));
//                System.out.println(pro.toString());
                pro.setImageproduit((String) item.get("imageProduit"));

                produits.add(pro);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("produits:" + produits.size());

        return produits;

    }

}
