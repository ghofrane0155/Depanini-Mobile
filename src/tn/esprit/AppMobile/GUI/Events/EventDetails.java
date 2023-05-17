 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.Events;

import com.codename1.components.ScaleImageLabel;
import static com.codename1.push.PushContent.setTitle;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import tn.esprit.AppMobile.Entities.Events;
import tn.esprit.AppMobile.SideMenuBaseForm;

/**
 *
 * @author noure
 */
public class EventDetails extends SideMenuBaseForm {
    
    private Form form;
    
    public EventDetails(Resources res, Events event) throws IOException {
        
         super(BoxLayout.y());     
        setupSideMenu(res);
        
        form = new Form("Event Details", new BorderLayout());
        setTitle(event.getNomevent()+" Details");
       

        // Create the header container with the event image
        Container mainContainer = new Container(new BorderLayout());
Container headerContainer = new Container(new BorderLayout());
headerContainer.setUIID("ContainerWithPadding");
EncodedImage placeholder = EncodedImage.createFromImage(res.getImage("logo.png"), true);
ScaleImageLabel image = new ScaleImageLabel(placeholder);

        
        

       // Create the details container with padding and border
Container details = new Container(new BoxLayout(BoxLayout.Y_AXIS));
details.getStyle().setBorder(Border.createLineBorder(3, 0x007fff));
details.getStyle().setPadding(10, 10, 10, 10);

// Add the event name label in bold and underlined
Label eventNameLabel = new Label("Event Title: " + event.getNomevent());
Font eventNameFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD | Font.STYLE_UNDERLINED, Font.SIZE_LARGE);
Style eventNameStyle = eventNameLabel.getAllStyles();
eventNameStyle.setFont(eventNameFont);
eventNameStyle.setPadding(10, 0, 0, 0); // set top padding
eventNameStyle.setFgColor(0x007fff); // set text color
eventNameStyle.setUnderline(true); // set underline
 // set bold
eventNameStyle.setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE));
details.addComponent(eventNameLabel);

// Add the number of participants label
Label participantsLabel = new Label(event.getNombrelimevent() + " participants maximum");
details.addComponent(participantsLabel);

// Add the location container with the location image and label
Container locationContainer = new Container(new FlowLayout(Component.LEFT, Component.CENTER));
Button locationButton = new Button("", res.getImage("location-pin.png").scaledHeight(50));
locationButton.setUIID("Container");
locationButton.setPressedIcon(res.getImage("location-pin.png").scaledHeight(40));
locationContainer.addComponent(locationButton);
Label locationLabel = new Label(event.getLieuevent());
locationContainer.addComponent(locationLabel);
details.addComponent(locationContainer);

// Add the start date container with the calendar image and label
Container startDateContainer = new Container(new FlowLayout(Component.LEFT, Component.CENTER));
Button startDateButton = new Button("", res.getImage("schedule.png").scaledHeight(50));
startDateButton.setUIID("Container");
startDateButton.setPressedIcon(res.getImage("schedule.png").scaledHeight(40));
startDateContainer.addComponent(startDateButton);
Label startDateLabel = new Label("Date début: ");
startDateContainer.addComponent(startDateLabel);
Label startDateValueLabel = new Label(event.getDatedebutevent().toString());
startDateContainer.addComponent(startDateValueLabel);
details.addComponent(startDateContainer);

// Add the end date container with the calendar image and label
Container endDateContainer = new Container(new FlowLayout(Component.LEFT, Component.CENTER));
Button endDateButton = new Button("", res.getImage("schedule.png").scaledHeight(50));
endDateButton.setUIID("Container");
endDateButton.setPressedIcon(res.getImage("schedule.png").scaledHeight(40));
endDateContainer.addComponent(endDateButton);
Label endDateLabel = new Label("Date fin: ");
endDateContainer.addComponent(endDateLabel);
Label endDateValueLabel = new Label(event.getDatefinevent().toString());
endDateContainer.addComponent(endDateValueLabel);
details.addComponent(endDateContainer);

// Add the organizer container with the user image and label
Container organizerContainer = new Container(new FlowLayout(Component.LEFT, Component.CENTER));
Button organizerButton = new Button("", res.getImage("user.png").scaledHeight(50));
organizerButton.setUIID("Container");
organizerButton.setPressedIcon(res.getImage("user.png").scaledHeight(40));
organizerContainer.addComponent(organizerButton);
Label organizerLabel = new Label("Organisateur: ");
organizerContainer.addComponent(organizerLabel);
Label organizerValueLabel = new Label(event.getOrganisateurevent());
organizerContainer.addComponent(organizerValueLabel);
details.addComponent(organizerContainer);

// Add the event description label
Label descriptionLabel = new Label(event.getDescriptionevent());
details.addComponent(descriptionLabel);

// Add the price label
Label priceLabel = new Label("Prix: " + event.getPrixevent() + "DT");
details.addComponent(priceLabel);

// Add back arrow button
Button backButton = new Button("");
FontImage.setMaterialIcon(backButton, FontImage.MATERIAL_ARROW_BACK);


backButton.setUIID("Title");
backButton.addActionListener(e -> {
    // Handle back button action here
  
                new ListEvents(res).show();
           
});

details.addComponent(backButton);

        // Add the header and details containers to the form
       // Add the header and details containers to the form
    headerContainer.addComponent(BorderLayout.CENTER, image);
mainContainer.addComponent(BorderLayout.NORTH, headerContainer);
mainContainer.addComponent(BorderLayout.CENTER, details);
form.add(BorderLayout.CENTER, mainContainer);
        // Set the title and back command
        setTitle("Event Details");
        
    }
    public void show() {

    form.show();
}

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


