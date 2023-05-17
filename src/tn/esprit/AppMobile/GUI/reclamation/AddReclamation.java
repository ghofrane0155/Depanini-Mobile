/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.reclamation;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import tn.esprit.AppMobile.Entities.Reclamation;
import tn.esprit.AppMobile.Services.ReclamationService;
import tn.esprit.AppMobile.SideMenuBaseForm;
import tn.esprit.AppMobile.StatsForm;

/**
 *
 * @author Mega-pc
 */
public class AddReclamation extends SideMenuBaseForm {

    ReclamationService rs = ReclamationService.getInstance();

      private static final String ACCOUNT_SID = "AC1269849337e5bd69c2cec023fdfde7e8";
    private static final String AUTH_TOKEN = "57b78d6d3d113c2e4b49804baa5ac182";
// The Twilio phone number you want to use to send SMS messages
    private static final String TWILIO_NUMBER = "+15074187445";

    // The recipient phone number you want to send an SMS message to
    private static final String RECIPIENT_NUMBER = "+21650607702";
    
    public AddReclamation(Resources res) {
        super(BoxLayout.y());

        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);

        Image navimg = res.getImage("nv.jpg");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container remainingTasks = BoxLayout.encloseY(
                new Label(" ", "CenterTitle"),
                new Label(" ", "CenterSubTitle")
        );
        // remainingTasks.setUIID("RemainingTasks");
        Container completedTasks = BoxLayout.encloseY(
                new Label(" ", "CenterTitle"),
                new Label(" ", "CenterSubTitle")
        );
        //  completedTasks.setUIID("CompletedTasks");

        Style titleCmpStyle = new Style();
        titleCmpStyle.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
        titleCmpStyle.setBgImage(navimg);

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("Ajouter Reclamation", "Title")
                        )
                ),
                GridLayout.encloseIn(2, remainingTasks, completedTasks)
        );

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD_TASK);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
       
        // fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        titleCmp.setUnselectedStyle(titleCmpStyle);
        titleCmp.setSelectedStyle(titleCmpStyle);

        setupSideMenu(res);
        // formulaire code 
        // Create the form
        Form form = new Form();

        // Define the type options
        String[] typeOptions = {"Information sur votre compte", "Information sur vos commandes", "Suggestions et remarques sur le site", "Signaler un dysfonctionnement", "Autre"};

// Create the ComboBox with the type options
        ComboBox<String> typeField = new ComboBox<>(typeOptions);

// Set the ComboBox to have the first option selected by default
        typeField.setSelectedIndex(0);

// Add the ComboBox to the form
        form.add(typeField);

        // Add the description field
        TextField descField = new TextField("", "Description", 20, TextField.ANY);
        descField.setUIID("TextFieldBlack");
        form.add(descField);

        // Add the submit button
        Button submitButton = new Button("Submit");
        submitButton.addActionListener(e -> {
            Reclamation r1;
            // Get the values of the fields and save the reclamation
            String type = typeField.getSelectedItem();
            String description = descField.getText();
            if (description.isEmpty()) {
                // If the description field is empty, show a dialog alert
                Dialog.show("Error", "Please enter a description", "OK", null);
            } else {
                
                    r1 = new Reclamation(type, description);
                    rs.ajoutReclamation(type, description);
                    String Curent_user_email="yousseffarhat818@gmail.com";  
                         Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

                // Send an SMS message using the Twilio API
                Message message = Message.creator(
                        new PhoneNumber(RECIPIENT_NUMBER),
                        new PhoneNumber(TWILIO_NUMBER),
                        " Une Reclamation a ete envoyer ! "
                ).create();

                // Print the message SID to the console
                System.out.println("SMS message sent with SID: " + message.getSid());
              
                    Dialog.show("Done", "Votre Reclamation a ete cree avec sucees", "OK", null);
                    new ListReclamations(res).show();
           }
            
        });
        form.add(submitButton);

        add(form);
   
    }

    
  
     
    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

}
