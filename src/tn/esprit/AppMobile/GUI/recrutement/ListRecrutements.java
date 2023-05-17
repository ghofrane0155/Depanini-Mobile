/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.recrutement;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import tn.esprit.AppMobile.Entities.Recrutement;
import tn.esprit.AppMobile.GUI.Home;
import tn.esprit.AppMobile.GUI.reclamation.ListReclamations;
import tn.esprit.AppMobile.GUI.recrutement.RecrutementsDetails;
import tn.esprit.AppMobile.Services.RecrutementService;
import tn.esprit.AppMobile.SideMenuBaseForm;

/**
 *
 * @author yasmine w dedicaces lkhouna youyou
 */
public class ListRecrutements extends SideMenuBaseForm {
    // pour tester la covertion de json type en un objet java

    RecrutementService rs = RecrutementService.getInstance();
   

    public ListRecrutements(Resources res) {
//--------------------------side Bar------------------------------
        super(BoxLayout.y());
        setupSideMenu(res);
//**********************************************************
        // this.setLayout(BoxLayout.y());
        this.setTitle("List Recrutements Page");
        setScrollableY(true);
        // setUIID("recrutementsPage");
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
             new ListRecrutements(res).show();
        });

        // just pour tester
//        SpanLabel sl = new SpanLabel();
//        sl.setText(rs.fetchReclamations().toString());   
//        this.add(sl);
        // widgets
        Container cards = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        int i = 0;
        for (Recrutement r : rs.fetchRecrutements()) {
            i++;
            // create card
            Container card = new Container(new BorderLayout());
            // create card content
            Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Label titleLabel = new Label("Recrutement n° " + i, "MyCardHeaderTitle");
            titleLabel.getAllStyles().setMargin(RIGHT, 5);
            content.add(titleLabel);
            content.add(new Label(r.getNom()));
            content.add(new Label(r.getDate().toString()));
            content.add(new Label(r.getDescription()));

            // create image
            // create button with icon
            Button detailsButton = new Button();
            FontImage.setMaterialIcon(detailsButton, FontImage.MATERIAL_INFO);
            detailsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Recrutement rec = rs.getRecrutement(r.getReference());
                    new RecrutementsDetails(res, rec).show();
                }
            });

            
               Button deleteButton = new Button();

            deleteButton.getAllStyles().setFgColor(0xFF0000);
            deleteButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, deleteButton.getUnselectedStyle()));
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                  rs.deleteRecrutement(r.getReference());
                    Dialog.show("Success", "Your Reclamation has been deleted.", "OK", null);
                  new ListRecrutements(res).show();

                }
            });
            
               Button updateButton = new Button();

            updateButton.getAllStyles().setFgColor(0x00FF00);
            updateButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_UPDATE, updateButton.getUnselectedStyle()));
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                new UpdateRecrutement(res, r,r.getReference()).show();
                }
            });
            
            // add content and image containers to card container
            card.add(BorderLayout.CENTER, content);

            
            
            card.add(BorderLayout.EAST, detailsButton);
            //--delete-
            card.add(BorderLayout.WEST, deleteButton);
            //---update--
            card.add(BorderLayout.SOUTH, updateButton);
            
            // create a new container with a BoxLayout layout
            Container line = new Container(new BoxLayout(BoxLayout.X_AXIS));

// set the container's border to create a line effect
            line.getAllStyles().setBorder(Border.createLineBorder(1, 0x000000));

// set the container's preferred size to create a horizontal line
            line.setPreferredH(1);
            content.add(line);
            cards.add(card);
        }
        
         Button addbtn = new Button("add");
        addbtn.addActionListener(e -> {
            new GererRecrutement(res).show();
 });
        this.add(addbtn);

        this.add(cards);

    }

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
