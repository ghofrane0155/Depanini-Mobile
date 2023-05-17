/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.Events;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.Date;
import tn.esprit.AppMobile.Entities.Events;
import static tn.esprit.AppMobile.MyApplication.theme;
import tn.esprit.AppMobile.Services.EventsService;
import tn.esprit.AppMobile.SideMenuBaseForm;

/**
 *
 * @author noure
 */
public class UpdateEvent extends SideMenuBaseForm {
    
    private String imagePath;

    private TextField nomEventField;
    private TextField nombreLimiteEventField;
    private TextField lieuEventField;
    private Picker dateDebutEventPicker;
    private Picker dateFinEventPicker;
    private TextField organisateurEventField;
    private TextArea descriptionEventArea;
    private TextField prixEventField;
    private Button createButton;
    private Button uploadImageButton;
    private Image imageEvent;

    UpdateEvent(Resources theme, Events event) {
//        
     super(BoxLayout.y());     
        setupSideMenu(theme);
   
   setTitle(event.getNomevent());
    setScrollableY(true);
    
        setLayout(new BorderLayout());
        try {
            setBgImage(EncodedImage.create("/lgbg1.jpg"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        // Initialize fields
        

        // Set up layout
    setLayout(new BorderLayout());
    Container content = getContentPane();
    content.setUIID("UpdateEventForm");

    // Add components to form
    Container form = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    form.add(nomEventField = new TextField(event.getNomevent(), "EventName", 20, TextField.ANY));
       form.add( nombreLimiteEventField = new TextField(Double.toString(event.getNombrelimevent()), "Limited Number", 20, TextField.ANY));
       form.add( prixEventField = new TextField(Double.toString(event.getPrixevent()), "Event Price", 20, TextField.ANY));
        form.add(lieuEventField = new TextField(event.getLieuevent(), "Place", 20, TextField.ANY));
        form.add(dateDebutEventPicker = new Picker());
        form.add(dateFinEventPicker = new Picker());
        form.add(organisateurEventField = new TextField(event.getOrganisateurevent(), "Organiser", 20, TextField.ANY));
        form.add(descriptionEventArea = new TextArea(event.getDescriptionevent()));
        
        form.add(uploadImageButton = new Button("Upload Image"));
        form.add(createButton = new Button("Update"));
        
    
    content.addComponent(BorderLayout.CENTER, form);
    
    double id =event.getIdevent();
    // Set up listeners
    createButton.addActionListener(evt -> {
      
           String nomEvent = nomEventField.getText();
        Double nombreLimiteEvent = Double.parseDouble(nombreLimiteEventField.getText());
        Double prixEvent = Double.parseDouble(prixEventField.getText());
        String lieuEvent = lieuEventField.getText();
        Date dateDebutEvent = dateDebutEventPicker.getDate();
        Date dateFinEvent = dateFinEventPicker.getDate();
        String organisateurEvent = organisateurEventField.getText();
        String descriptionEvent = descriptionEventArea.getText();
        EventsService es = new EventsService();
        Events Event=new Events(id,nomEvent, nombreLimiteEvent, lieuEvent, dateDebutEvent, dateFinEvent, organisateurEvent, descriptionEvent, imagePath, prixEvent);
        
        es.UpdateEvent(Event , id);
        // Display success message
        Dialog.show("Success", "Event created successfully", "OK", null);
        new ListEvents(theme).show();
       
   });
    uploadImageButton.addActionListener(evt -> uploadImage());
   
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
    private void UpdateEvent() throws IOException {
        // Retrieve field values and create event
        String nomEvent = nomEventField.getText();
        Double nombreLimiteEvent = Double.parseDouble(nombreLimiteEventField.getText());
        Double prixEvent = Double.parseDouble(prixEventField.getText());
        String lieuEvent = lieuEventField.getText();
        Date dateDebutEvent = dateDebutEventPicker.getDate();
        Date dateFinEvent = dateFinEventPicker.getDate();
        String organisateurEvent = organisateurEventField.getText();
        String descriptionEvent = descriptionEventArea.getText();
        EventsService es = new EventsService();
        Events Event=new Events(nomEvent, nombreLimiteEvent, lieuEvent, dateDebutEvent, dateFinEvent, organisateurEvent, descriptionEvent, imagePath, prixEvent);
        
       
        // Display success message
        Dialog.show("Success", "Event created successfully", "OK", null);
        new ListEvents(theme).show();
    }

     private void uploadImage() {
        Display.getInstance().openGallery(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt != null && evt.getSource() != null) {
                    String filePath = (String) evt.getSource();
                    try {
                        // Load the selected image into an Image object
                        imageEvent = Image.createImage(filePath);

                        // Set the file path of the selected image
                        imagePath = filePath;

                        // Display the selected image in the UI
                        Container imageContainer = new Container(new BorderLayout());
                        imageContainer.add(BorderLayout.CENTER, new ScaleImageLabel(imageEvent));
                        getContentPane().replace(uploadImageButton, imageContainer, CommonTransitions.createFade(500));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, Display.GALLERY_IMAGE);
    }

    
        
        
    

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
