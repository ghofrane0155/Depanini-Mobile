package tn.esprit.AppMobile;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import tn.esprit.AppMobile.Entities.User;
import tn.esprit.AppMobile.Services.UserService;
import static tn.esprit.AppMobile.Services.UserService.resultOK;
import tn.esprit.AppMobile.Utils.Statics;

public class ProfileForm extends SideMenuBaseForm {

    private static String i;
    
    public ProfileForm(Resources theme) {
super(BoxLayout.y());
getTitleArea().setUIID("Container");
        try {
            setBgImage(EncodedImage.create("/pass.jpg"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = theme.getImage("youssef.jpg");
        Image mask = theme.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
        Container remainingTasks = BoxLayout.encloseY(
                        new Label("12", "CenterTitle"),
                        new Label("remaining tasks", "CenterSubTitle")
                );
        remainingTasks.setUIID("RemainingTasks");
        Container completedTasks = BoxLayout.encloseY(
                        new Label("32", "CenterTitle"),
                        new Label("completed tasks", "CenterSubTitle")
        );
        completedTasks.setUIID("CompletedTasks");

        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseY(
                                    new Label(SessionManager.getNomUser()+" "+SessionManager.getPrenomUser(), "Title"),
                                    new Label("UI/UX Designer", "SubTitle")
                                )
                            ).add(BorderLayout.WEST, profilePicLabel),
                        GridLayout.encloseIn(2, remainingTasks, completedTasks)
                );
        
/********************************************************/
        //Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setTitle("Mon Profile");
        getContentPane().setScrollVisible(true);   
        super.setupSideMenu(theme);
        
        Button modif =new Button("Modifier");
        modif.setIcon(FontImage.createMaterial(FontImage.MATERIAL_UPDATE, modif.getUnselectedStyle()));
        Button Delete =new Button("Delete");
        Delete.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, Delete.getUnselectedStyle()));
       
        add(LayeredLayout.encloseIn(
              //  sl,
                BorderLayout.south(
                    GridLayout.encloseIn(3,                           
                            FlowLayout.encloseCenter(
                                    )
                    )
                )
        ));
        
        String nu = SessionManager.getNomUser();
        String pu = SessionManager.getPrenomUser();
        System.out.println(nu+" "+pu);
        
        FontImage userIcon = FontImage.createMaterial(FontImage.MATERIAL_PERSON, UIManager.getInstance().getComponentStyle("TextFieldBlack"));
TextField username = new TextField(nu);
username.setUIID("TextFieldBlack");
Label userLabel = new Label(userIcon);
Container userContainer = new Container(new BorderLayout());
userContainer.add(BorderLayout.WEST, userLabel);
userContainer.add(BorderLayout.CENTER, username);
addStringValue("First name", userContainer);
        
FontImage userLastNameIcon = FontImage.createMaterial(FontImage.MATERIAL_PERSON, UIManager.getInstance().getComponentStyle("TextFieldBlack"));
TextField userlastname = new TextField(pu);
userlastname.setUIID("TextFieldBlack");
Label userLastNameLabel = new Label(userLastNameIcon);
Container userLastNameContainer = new Container(new BorderLayout());
userLastNameContainer.add(BorderLayout.WEST, userLastNameLabel);
userLastNameContainer.add(BorderLayout.CENTER, userlastname);
addStringValue("Last name", userLastNameContainer);
        
       FontImage loginIcon = FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, UIManager.getInstance().getComponentStyle("TextFieldBlack"));
TextField login = new TextField(SessionManager.getLogin());
login.setUIID("TextFieldBlack");
Label loginLabel = new Label(loginIcon);
Container loginContainer = new Container(new BorderLayout());
loginContainer.add(BorderLayout.WEST, loginLabel);
loginContainer.add(BorderLayout.CENTER, login);
addStringValue("Login", loginContainer);
        
FontImage telIcon = FontImage.createMaterial(FontImage.MATERIAL_PHONE, UIManager.getInstance().getComponentStyle("TextFieldBlack"));
TextField tel = new TextField(SessionManager.getTel());
tel.setUIID("TextFieldBlack");
Label telLabel = new Label(telIcon);
Container telContainer = new Container(new BorderLayout());
telContainer.add(BorderLayout.WEST, telLabel);
telContainer.add(BorderLayout.CENTER, tel);
addStringValue("Phone number", telContainer);
        
FontImage locationIcon = FontImage.createMaterial(FontImage.MATERIAL_LOCATION_ON, UIManager.getInstance().getComponentStyle("TextFieldBlack"));
        TextField adresse = new TextField(SessionManager.getAdresse());
        adresse.setUIID("TextFieldBlack");
        Label adresseLabel = new Label(locationIcon);
        Container adresseContainer = new Container(new BorderLayout());
        adresseContainer.add(BorderLayout.WEST, adresseLabel);
        adresseContainer.add(BorderLayout.CENTER, adresse);
        addStringValue("Location", adresseContainer);
        
        
//        FontImage passwordIcon = FontImage.createMaterial(FontImage.MATERIAL_LOCK, UIManager.getInstance().getComponentStyle("TextFieldBlack"));
//        TextField password = new TextField(SessionManager.getPassword(), "Password", 20, TextField.PASSWORD);
//        password.setUIID("TextFieldBlack");
//        Label passwordLabel = new Label(passwordIcon);
//        Container passwordContainer = new Container(new BorderLayout());
//        passwordContainer.add(BorderLayout.WEST, passwordLabel);
//        passwordContainer.add(BorderLayout.CENTER, password);
//        addStringValue("Password", passwordContainer);
        
        FontImage emailIcon = FontImage.createMaterial(FontImage.MATERIAL_EMAIL, UIManager.getInstance().getComponentStyle("TextFieldBlack"));
        TextField email = new TextField(SessionManager.getEmail(), "E-Mail", 20, TextField.EMAILADDR);
        email.setUIID("TextFieldBlack");
        Label emailLabel = new Label(emailIcon);
        Container emailContainer = new Container(new BorderLayout());
        emailContainer.add(BorderLayout.WEST, emailLabel);
        emailContainer.add(BorderLayout.CENTER, email);
        addStringValue("E-Mail", emailContainer);
        
        Delete.setUIID("Delete");
        modif.setUIID("Edit");
        addStringValue("", Delete);
        addStringValue("", modif);
        
        TextField path = new TextField("");
      
        Delete.addActionListener(e -> {
              if(UserService.getInstance().supprimer(SessionManager.getIdUser())){
               Dialog.show("Success", "User Deleted", "OK", null);
               new WalkthruForm(theme).show();
              } else {
                    Dialog.show("Error", "Request Error", "OK", null);
                }
        });
            
        
        modif.addActionListener((edit) -> {
            InfiniteProgress ip = new InfiniteProgress();
           final Dialog ipDlg = ip.showInfiniteBlocking();
           UserService us=new  UserService();
           us.editUser(username.getText(),userlastname.getText(),email.getText(),tel.getText(),adresse.getText(), SessionManager.getPassword(),login.getText());
           SessionManager.setNomUser(username.getText());
           SessionManager.setPrenomUser(userlastname.getText());
           SessionManager.setEmail(email.getText());
           
           //SessionManager.setPhotoUser(path.getText()+".jpg");
           SessionManager.setTel(tel.getText());
           SessionManager.setAdresse(adresse.getText());
           
          // SessionManager.setPassword(password.getText());
           SessionManager.setLogin(login.getText());
           
           Dialog.show("Succes","Modifications des coordonnees avec succes","OK",null);
           ipDlg.dispose();
           refreshTheme();
        });
    }
        
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
        public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
  
}
