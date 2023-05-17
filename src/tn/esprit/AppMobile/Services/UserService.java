package tn.esprit.AppMobile.Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tn.esprit.AppMobile.Entities.User;

import tn.esprit.AppMobile.Inscription;
import tn.esprit.AppMobile.Template;
import tn.esprit.AppMobile.SessionManager;
import tn.esprit.AppMobile.Utils.Statics;


/**
 *
 * @author Ghof
 */
public class UserService {
    ConnectionRequest req;
    public static UserService instance=null;
    public static boolean resultOK;
    String json;

    public ArrayList<User> users;

    
    public UserService() {
         req = new ConnectionRequest();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }  
    
/**********************************/     
        public User getUser(String jsontext) throws IOException {
        if (jsontext.length() > 4) {

            JSONParser j = new JSONParser();
            Map<String, Object> obj = j.parseJSON(new CharArrayReader(jsontext.toCharArray()));

            float id = Float.parseFloat(obj.get("id").toString());
            String email = obj.get("email").toString();
            String login = obj.get("login").toString();
            String password = obj.get("password").toString();
            String nom = obj.get("nom").toString();
            String prenom = obj.get("prenom").toString();
            String rolesObj = obj.get("roles").toString();

            User b = new User((int) id, login, password, nom, prenom, email, rolesObj);

            return b;
        } else {
            User uc = new User();

            return uc;
        }

    }
/***************************************************************************/
    public ArrayList<User> parseUsers(String jsonText){
                try {
                    char firstChar = jsonText.charAt(0);
                    if(firstChar != '[')
                    {
                    jsonText="["+jsonText+"]";
                    }
                    System.out.println(jsonText);
            users=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                User a = new User();
                float id = Float.parseFloat(obj.get("idUser").toString());
                a.setIdUser((double)id);
                a.setNomUser(obj.get("nomUser").toString());
                a.setPrenomUser(obj.get("prenomUser").toString());
                a.setLogin(obj.get("login").toString());
                a.setPassword(obj.get("password").toString());
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dn = null;
                try {
                    dn = (Date) dateFormat.parse((String) obj.get("dateNaisUser"));
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
                a.setDateNaisUser(dn);
                         
                a.setEmail(obj.get("email").toString());
                a.setAdresse(obj.get("adresse").toString());
                a.setTel(obj.get("tel").toString());
                a.setSexe(obj.get("sexe").toString());
                a.setRoles(obj.get("roles").toString());
                a.setPhotoUser(obj.get("photoUser").toString());

                users.add(a);

            }
        } catch (IOException ex) {
            
        }
        return users;
    }
/*****************ALL USERS**********************************************************/
    public ArrayList<User> getAllUsers(){
        String url = Statics.BASE_URL+"/dashboardUserAPI";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }    
/******************SIGN UP****************************************************************/
    public boolean addUser(User u) {
        String url = Statics.BASE_URL + "/signupAPI?nomUser="+u.getNomUser()+"&prenomUser=" + u.getPrenomUser()+ "&login="+ u.getLogin()+ "&password="+ u.getPassword()+ "&dateNaisUser="+ u.getDateNaisUser()+ "&email="+ u.getEmail()+ "&adresse="+ u.getAdresse()+ "&tel="+ u.getTel()+ "&sexe="+ u.getSexe()+ "&role="+ u.getRoles()+ "&photoUser="+ u.getPhotoUser(); //création de l'URL
               req.setUrl(url);
               System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
/*************************************************************************************/    
 public void signin(TextField username, String password, Resources rs) {   
    if (username.getText().isEmpty() || password.isEmpty()) {
        Dialog.show("Erreur", "Veuillez remplir tous les champs", "OK", null);
        return;
    }
    if (username.getText().isEmpty()) {
        Dialog.show("Error", "Email field is empty", "OK", null);
        return;
    }
    if (password.isEmpty()) {
        Dialog.show("Error", "Password field is empty", "OK", null);
        return;
    }
    
    req = new ConnectionRequest();
    req.setPost(false);

    String url = Statics.BASE_URL + "/loginAPI?email=" + username.getText().toString() + "&password=" + password.toString();
    req = new ConnectionRequest(url, false);
    req.setUrl(url);

    req.addResponseListener((e) -> {
        JSONParser j = new JSONParser();
        String json = new String(req.getResponseData()) + "";
        try {
            if (json.equals("failed")) {
                Dialog.show("Echec d'authentification", "Email éronné", "OK", null);
            }else if(json.equals("password not found")){
                Dialog.show("Echec d'authentification", "Mot de passe éronné", "OK", null);
            }
            else {
                System.out.println("data ==" + json);
                Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                //Session
                float id = Float.parseFloat(user.get("idUser").toString());
                SessionManager.setIdUser((int) id);
                SessionManager.setNomUser(user.get("nomUser").toString());
                SessionManager.setPrenomUser(user.get("prenomUser").toString());
                SessionManager.setLogin(user.get("login").toString());
                SessionManager.setPassword(user.get("password").toString());
                SessionManager.setEmail(user.get("email").toString());
                SessionManager.setAdresse(user.get("adresse").toString());
                SessionManager.setTel(user.get("tel").toString());
                SessionManager.setSexe(user.get("sexe").toString());

                if (user.get("roles").toString().equals("[ROLE_CLIENT, ROLE_USER]"))
                    SessionManager.setRoles("CLIENT");
                else
                    SessionManager.setRoles("FREELANCER");
                System.out.println(" session mara okhra :" + SessionManager.getRoles());

                SessionManager.setPhotoUser(user.get("photoUser").toString());

                //photo
                if (user.get("photo") != null)
                    SessionManager.setPhotoUser(user.get("photo").toString());

                System.out.println("current user " + SessionManager.getIdUser() + " " + SessionManager.getNomUser() + " " + SessionManager.getPrenomUser());
                if (user.size() > 0) // l9a user
                    new Template(rs).show();
                else{
                    System.out.println("mafamechhhhhh!!");
                    new Inscription(rs).show();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    });
    NetworkManager.getInstance().addToQueueAndWait(req);
}
   
    
 
/*****************************************************************************************/
    public void editUser(String nom,String prenom,String email,String tel,String adresse,String password,String login) {
        String url = Statics.BASE_URL + "/profilAPI?id="+SessionManager.getIdUser()+"&nom="+nom+"&prenom=" +prenom+ "&login="+login+ "&password="+password+ "&email="+ email+ "&adresse="+ adresse+ "&tel="+ tel;  

        MultipartRequest req = new MultipartRequest();
            
            req.setUrl(url);
            req.setPost(true);
            req.addArgument("id", String.valueOf(SessionManager.getIdUser()));
            req.addArgument("nom", nom);
            req.addArgument("prenom", prenom);
            req.addArgument("login", login);
            req.addArgument("password", password);
            req.addArgument("email", email);
            req.addArgument("adresse", adresse);
            req.addArgument("tel", tel);
            System.out.println("houni!!!");
            //req.addArgument("photo", photo);
            
            req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }    
         
/******************DELETE*********************************************************************/
    public boolean deleteUser(int fi) {
        String url = Statics.BASE_URL + "/DeleteUserAPI/" + fi;
               req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public boolean supprimer(int id) {
        req = new ConnectionRequest();
req.setPost(false);
          String url = Statics.BASE_URL + "/DeleteUserAPI?id="+id;
          System.out.println("url delete :"+url);
          System.out.println("id chkoun "+id);
       
       req.setUrl(url);
      req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
    
        NetworkManager.getInstance().addToQueueAndWait(req);
      return resultOK;
    }
/************************************************************************/      
     public User getUserById() throws UnsupportedEncodingException, IOException {
        ConnectionRequest request = new ConnectionRequest();
        request.setUrl("http://localhost:8080/users/" + 13);
        request.setHttpMethod("GET");

        NetworkManager.getInstance().addToQueueAndWait(request);

        Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));

        User user = new User();
        user.setIdUser((int) response.get("id"));
        user.setNomUser((String) response.get("name"));
        user.setEmail((String) response.get("email"));

        return user;
    }
/***********************************************/
    public String getPasswordByEmail(String email, Resources rs ) {          
        String url = Statics.BASE_URL+"/getPasswordByEmail?email="+email;
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);    
        
        req.addResponseListener((e) ->{           
            JSONParser j = new JSONParser();     
            String json = new String(req.getResponseData()) + "";  
            try {
                System.out.println("data =="+json);
                if(json.equals("user not found")){
                    Dialog.show("Echec d'authentification","Emaileronné","OK",null);
                    return;}                
                
                Map<String,Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));
            }catch(Exception ex) {
                ex.printStackTrace();
            }  
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    return json;
    }

     public boolean modifierpassword(String pass,int id) {
        String url = Statics.BASE_URL + "/updatepassword?password=" + pass + "&id=" + id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;

    }
/**************************************************************************/
//      public void sendEmail(String email, String code,String login) throws IOException {
//        //  la session pour l'envoie d'un email
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("depanini2023@gmail.com", "gpjhpzyjvtqycvlv");
//            }
//        });
//        try {
//            // Création de l'objet Message
//            Message message = new MimeMessage(session);
//            // from 
//            message.setFrom(new InternetAddress("depanini2023@gmail.com"));
//            // Recipients
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
//            // Objet de l'email
//            message.setSubject("Réinitialisation du mot de passe de votre compte Depanini");
//        //////////////////////////////////////////////////////    
//            message.setSentDate(new java.sql.Date(System.currentTimeMillis()));
//            //String htmlBody = new String(Files.readAllBytes(Paths.get("src/mail.html"))).replace("123456",code);
//      //      String htmlBody2 = new String(htmlBody).replace("XXYY",login);
//          
//            message.setContent(htmlBody2, "text/html");
//                     
//            //  Envoyer le message
//            Transport.send(message);
//        } catch (MessagingException e) {
//            System.out.println(e.getMessage());
//        }
//
//    }
}