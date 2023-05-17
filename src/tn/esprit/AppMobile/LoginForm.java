package tn.esprit.AppMobile;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;

import tn.esprit.AppMobile.Services.UserService;


public class LoginForm extends Form {
    public LoginForm(Resources theme) {
        //ghofrane youssef
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Welcome, to ", "WelcomeWhite"),
                new Label("Depanini", "WelcomeBlue")
        );
        
        getTitleArea().setUIID("Container");
        try {
            setBgImage(EncodedImage.create("/lgbg1.jpg"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
//        Image profilePic = theme.getImage("youssef.jpg");
//        Image mask = theme.getImage("round-mask.png");
//        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
//        Label profilePicLabel = new Label(profilePic, "ProfilePic");
//        profilePicLabel.setMask(mask.createMask());
        
        TextField login = new TextField("", "Email", 20, TextField.EMAILADDR) ;
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD) ;
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        
        Button loginButton = new Button("LOGIN");
        loginButton.setUIID("LoginButton");
            
        Button createNewAccount = new Button("CREATE NEW ACCOUNT");
        createNewAccount.setUIID("CreateNewAccountButton");
        
        loginButton.addActionListener(e -> {
            UserService.getInstance().signin(login, password.getText(),theme);
            Toolbar.setGlobalToolbar(false);
            //new WalkthruForm(theme).show();
            Toolbar.setGlobalToolbar(true);
        });
        
        createNewAccount.addActionListener(e -> new Inscription(theme).show());
        
        Button  mp = new Button("oublier mot de passe?");
        //Mp oublie event      
        mp.addActionListener((e) -> {      
         //   new ActivateForm(theme).show();
               
        });
        
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        
        Container by = BoxLayout.encloseY(
                welcome,
               // profilePicLabel,
                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                loginButton,
                createNewAccount,mp
        );
        add(BorderLayout.CENTER, by);
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
