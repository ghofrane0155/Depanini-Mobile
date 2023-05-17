/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.Events;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.*;
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

import tn.esprit.AppMobile.Entities.Events;
import static tn.esprit.AppMobile.MyApplication.theme;
import tn.esprit.AppMobile.Services.EventsService;
import tn.esprit.AppMobile.SideMenuBaseForm;
/**
 *
 * @author noure
 */
public class CreateEvent extends SideMenuBaseForm {

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

    public CreateEvent(Resources res) {
//        
        super(BoxLayout.y());     
        setupSideMenu(res);
    
   
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
    content.setUIID("CreateEventForm");

    // Add components to form
    Container form = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    form.add(nomEventField = new TextField("", "EventName", 20, TextField.ANY));
       form.add( nombreLimiteEventField = new TextField("", "Limited Number", 20, TextField.ANY));
       form.add( prixEventField = new TextField("", "Event Price", 20, TextField.ANY));
        form.add(lieuEventField = new TextField("", "Place", 20, TextField.ANY));
        form.add(dateDebutEventPicker = new Picker());
        form.add(dateFinEventPicker = new Picker());
        form.add(organisateurEventField = new TextField("", "Organiser", 20, TextField.ANY));
        form.add(descriptionEventArea = new TextArea());
        
        form.add(uploadImageButton = new Button("Upload Image"));
    form.add(createButton = new Button("Create"));
    
    content.addComponent(BorderLayout.CENTER, form);
    

    // Set up listeners
    createButton.addActionListener(evt -> {
       try {
           createEvent();
       } catch (IOException ex) {
           System.out.println("tn.esprit.AppMobile.GUI.Events.CreateEvent.<init>()");
       }
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
    private void createEvent() throws IOException {
        // Retrieve field values and create event
       if(verif()){ 
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
        
        es.createEvent(Event);
        // TODO: Create event object and save to database

        // Display success message
        
       }
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
     
     private boolean verif() {
         if ( nomEventField.getText().isEmpty() || nombreLimiteEventField.getText().isEmpty() || lieuEventField.getText().isEmpty()
            || descriptionEventArea.getText().isEmpty() || organisateurEventField.getText().isEmpty()) {
                Dialog.show("Alert", "Please fill in all fields!", "OK", null);
                }
         
         int s=Integer.parseInt(nombreLimiteEventField.getText());
         if(s>100 && s<1){
             Dialog.show("Alert", "Please fill in the fields correctly !", "OK", null);
         }
         int s1=Integer.parseInt(prixEventField.getText());
         if(s1>100 && s1<1){
             Dialog.show("Alert", "Please fill in the fields correctly !", "OK", null);
         }
        
        
          return true;
         }

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
