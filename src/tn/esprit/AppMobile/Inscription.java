/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.*;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.layouts.*;
import com.codename1.ui.util.Resources;
import com.codename1.ui.spinner.Picker;

import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.FontImage;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.plaf.UIManager;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import tn.esprit.AppMobile.Entities.User;
import tn.esprit.AppMobile.Services.UserService;
/**
 *
 * @author noure
 */
public class Inscription extends BaseForm {

    private TextField nomUserField;
    private TextField prenomUserField;
    private TextField loginField;
    private Picker dnPicker;
    private TextField passwordField;
    private TextField emailField;
    private TextField adresseField;
    private TextField telField;
    private TextField sexeField;
    private ComboBox<String> rolesComboBox;
    private TextField photoUserField;   
    
    private Button createButton;
    private Button signInButton;

    
    
    public Inscription(Resources res) {       
        super(new BorderLayout());      
        
        // Initialize fields
        nomUserField = new TextField();
        prenomUserField = new TextField();
        loginField = new TextField();
        dnPicker = new Picker();
        passwordField = new TextField();
        emailField = new TextField();
        adresseField = new TextField();
        telField = new TextField();
        sexeField = new TextField();
        
        Vector<String> roles = new Vector<>();
        roles.add("Client");
        roles.add("Freelancer");
        rolesComboBox = new ComboBox<>(roles);
        photoUserField = new TextField();      
        createButton = new Button("Inscription");
        signInButton = new Button("Sign In");
        
        
        // Set up layout
        setLayout(new BorderLayout());
        Container content = getContentPane();
        content.setUIID("Sign up");

        // Add components to form
        Container form = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        form.setScrollableY(true);
        form.setUIID("CreateEventFormFieldHolder");
        
        form.add(createTextHolder("Nom", nomUserField));
        form.add(createTextHolder("Prenom", prenomUserField));
        form.add(createTextHolder("Login", loginField));
        form.add(createTextHolder("Date de naissance", dnPicker));
        form.add(createTextHolder("Mot de passe", passwordField));
        form.add(createTextHolder("Email", emailField));
        form.add(createTextHolder("Adresse", adresseField));
        form.add(createTextHolder("Téléphone", telField));
        form.add(createTextHolder("Sexe", sexeField));
        form.add(createTextHolder("Rôle", rolesComboBox));
        form.add(createTextHolder("Photo", photoUserField));

        content.addComponent(BorderLayout.CENTER, form);
        content.addComponent(BorderLayout.SOUTH, BoxLayout.encloseY(createButton, signInButton));

        
        createButton.addActionListener(evt -> Inscription(res)); 
        signInButton.addActionListener(e -> new LoginForm(res).show());
    show();
}

    private Container createTextHolder(String labelText, Component field) {
        Container holder = new Container(new BorderLayout());
        Label label = new Label(labelText);
        label.setUIID("CreateEventFormLabel");
        holder.addComponent(BorderLayout.NORTH, label);
        holder.addComponent(BorderLayout.CENTER, field);
        holder.setUIID("CreateEventFormFieldHolder");
        return holder;
    }
    
    private void Inscription(Resources res) {
        String nomUser = nomUserField.getText();
        String prenomUser = prenomUserField.getText();
        Date dateNaisUser = dnPicker.getDate();
        String login = loginField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String adresse = adresseField.getText();
        String tel = telField.getText();
        String sexe = sexeField.getText();
        String roles = rolesComboBox.getSelectedItem();
        String photoUser = photoUserField.getText();
        
        UserService es = new UserService();
//          // Hash a password
//          String hashedPassword = PasswordStorage.hashPassword(password);
//       // System.out.println("pass "+hashedPassword);
       
        User user=new User(nomUser, prenomUser, login, password, dateNaisUser, email, adresse, tel, sexe,roles,photoUser);
        es.addUser(user);

        Dialog.show("Success", "Inscription faite avec succés", "OK", null);
        new LoginForm(res).show();
    }    
}
