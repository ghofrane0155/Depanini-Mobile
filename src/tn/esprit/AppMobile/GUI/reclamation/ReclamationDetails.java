/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.reclamation;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.AppMobile.Entities.Reclamation;

/**
 *
 * @author Mega-pc
 */
public class ReclamationDetails extends Form {

    public ReclamationDetails(Reclamation r,Resources res) {

        setTitle("Reclamation Details");
        setLayout(new BorderLayout());
        setScrollableY(true);

        // Create card container
        Container card = new Container(new BorderLayout());
        card.setUIID("MyCard");

        // create header container with icon and title
        Container header = new Container(new BorderLayout());
        header.setUIID("MyCardHeader");

        // create label for title
        Label titleLabel = new Label("Reclamation Details", "MyCardHeaderTitle");
        titleLabel.getAllStyles().setMargin(RIGHT, 5);
        header.add(BorderLayout.CENTER, titleLabel);

        // add header to card container
        card.add(BorderLayout.NORTH, header);

        // create content container with reclamation details
        Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        content.setUIID("MyCardContent");
        content.getStyle().setAlignment(Component.CENTER);

        // add type label to content container
        Label typeLabel = new Label("Type de la reclamation:");
        typeLabel.setUIID("MyLabel");
        content.add(typeLabel);
        content.add(new Label(r.getType()));

        // add description label to content container
        Label descriptionLabel = new Label("Description:");
        descriptionLabel.setUIID("MyLabel");
        content.add(descriptionLabel);
        content.add(new Label(r.getDescription()));

        // add date reclamation label to content container
        Label dateRecLabel = new Label("Date reclamation:");
        dateRecLabel.setUIID("MyLabel");
        content.add(dateRecLabel);
        content.add(new Label(r.getDateReclamation().toString()));

        // add statut label to content container
        Label statutLabel = new Label("Statut de la reclamation:");
        statutLabel.setUIID("MyLabel");
        content.add(statutLabel);
        content.add(new Label(r.getStatut()));

        // add date resolution label to content container
        Label dateResLabel = new Label("Date resolution de la reclamation:");
        dateResLabel.setUIID("MyLabel");
        content.add(dateResLabel);
        if (r.getDateResolution()== null){
            content.add(new Label("no date"));
        }else{
            content.add(new Label(r.getDateResolution().toString()));
        }
        
        // add content to card container
        card.add(BorderLayout.CENTER, content);

        // add card container to form
        addComponent(BorderLayout.CENTER, card);

        // add back button to form
        Button backBtn = new Button("Retour");
        backBtn.setUIID("MyButton");
        backBtn.addActionListener(e -> {
             new ListReclamations(res).showBack();
        });
        addComponent(BorderLayout.SOUTH, backBtn);
    }
}
