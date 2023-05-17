/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.reclamation;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import tn.esprit.AppMobile.Entities.Reclamation;
import tn.esprit.AppMobile.GUI.Home;
import tn.esprit.AppMobile.Services.ReclamationService;
import tn.esprit.AppMobile.SideMenuBaseForm;
import tn.esprit.AppMobile.StatsForm;

/**
 *
 * @author Mega-pc
 */
public class ListReclamations extends SideMenuBaseForm {
    // pour tester la covertion de json type en un objet java

    ReclamationService rs = ReclamationService.getInstance();

    public ListReclamations(Resources res) {
        super(BoxLayout.y());

        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);

        Image navimg = res.getImage("nv.jpg");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        int x = 0;
        try {
            x = rs.getcountrec();
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }
        int y = 0;
        try {
            y = rs.getcountrecresolu();
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }
        Container remainingTasks = BoxLayout.encloseY(
                new Label(" " + x, "CenterTitle"),
                new Label("Nbr Reclamation", "CenterSubTitle")
        );
        // remainingTasks.setUIID("RemainingTasks");
        Container completedTasks = BoxLayout.encloseY(
                new Label(" " + y, "CenterTitle"),
                new Label("Nbr Reclamation Resolu", "CenterSubTitle")
        );
        //  completedTasks.setUIID("CompletedTasks");

        Style titleCmpStyle = new Style();
        titleCmpStyle.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
        titleCmpStyle.setBgImage(navimg);

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("Liste Reclamation", "Title")
                        )
                ),
                GridLayout.encloseIn(2, remainingTasks, completedTasks)
        );

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD_TASK);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.addActionListener(e -> {
            new AddReclamation(res).show();
        });
// fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        titleCmp.setUnselectedStyle(titleCmpStyle);
        titleCmp.setSelectedStyle(titleCmpStyle);

        // Get the current date
        Date currentDate = new Date();

// Format the date as a string using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        String formattedDate = dateFormat.format(currentDate);

// Create a label with the formatted date string
        Label todayLabel = new Label("Today " + formattedDate, "TodayTitle");

        add(todayLabel);

        setupSideMenu(res);

        // this.setLayout(BoxLayout.y());
        //  this.setTitle("List Reclamtions Page");
//        setScrollableY(true);
//        // setUIID("reclamationsPage");
//        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
//            new Home(res).showBack();
//        });
        // just pour tester
//        SpanLabel sl = new SpanLabel();
//        sl.setText(rs.fetchReclamations().toString());   
//       this.add(sl);
        //  widgets
        Container cards = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        int i = 0;
        for (Reclamation r : rs.fetchReclamations()) {
            i++;
            // create card container
            Container card = new Container(new BorderLayout());
            card.setUIID("MyCard");

            // create header container with icon and title
            Container header = new Container(new BorderLayout());
            header.setUIID("MyCardHeader");

            // create label for icon
            // create label for title
            Label titleLabel = new Label("Reclamation n° " + i, "MyCardHeaderTitle");
            titleLabel.getAllStyles().setMargin(RIGHT, 5);
            header.add(BorderLayout.CENTER, titleLabel);

            // add header to card container
            card.add(BorderLayout.NORTH, header);

            // create content container with reclamation details
            Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            content.setUIID("MyCardContent");

            content.add(new Label("type:" + r.getType(), "MyCardContentLabel"));
            content.add(new Label("Description: " + r.getDescription(), "MyCardContentLabel"));
            content.add(new Label("Date: " + r.getDateReclamation().toString(), "MyCardContentLabel"));
            
                
                
            // create label for status with image
            Image statusImage = null;
            if (r.getStatut().equals("Ouvert")) {
                statusImage = FontImage.createMaterial(FontImage.MATERIAL_SEND, "MyCardContentLabelImage", 5);
            } else if (r.getStatut().equals("Resolu")) {
                statusImage = FontImage.createMaterial(FontImage.MATERIAL_CHECK, "MyCardContentLabelImage", 5);
            } else if (r.getStatut().equals("En attente")) {
                statusImage = FontImage.createMaterial(FontImage.MATERIAL_HOURGLASS_EMPTY, "MyCardContentLabelImage", 5);
            }
            if (statusImage != null) {
                Label statusLabel = new Label("", statusImage, "MyCardContentLabel");
                content.add(statusLabel);
            } else {
                content.add(new Label("Statut: " + r.getStatut(), "MyCardContentLabel"));
            }
            content.add(new Label("Statut: " + r.getStatut(), "MyCardContentLabel"));

            // add content to card container
            card.add(BorderLayout.CENTER, content);

            // create button with icon
            Button detailsButton = new Button();
            FontImage.setMaterialIcon(detailsButton, FontImage.MATERIAL_INFO);
            detailsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Reclamation reclamation = rs.getReclamation(r.getIdReclamation());
                    new ReclamationDetails(reclamation, res).show();
                }
            });

            Button deleteButton = new Button();

            deleteButton.getAllStyles().setFgColor(0xFF0000);
            deleteButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, deleteButton.getUnselectedStyle()));
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    rs.deleteReclamation(r.getIdReclamation());
                    Dialog.show("Success", "Your Reclamation has been deleted.", "OK", null);
                    new ListReclamations(res).show();

                }
            });

            // add button to card container
            card.add(BorderLayout.EAST, detailsButton);
            card.add(BorderLayout.WEST, deleteButton);
            // add card container to cards container
            cards.add(card);

        }
        this.add(cards);

    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}
