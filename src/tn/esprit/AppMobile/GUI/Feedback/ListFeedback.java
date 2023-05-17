/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.Feedback;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import tn.esprit.AppMobile.Entities.Feedback;
import tn.esprit.AppMobile.Services.FeedbackService;
import tn.esprit.AppMobile.SideMenuBaseForm;
import tn.esprit.AppMobile.StatsForm;

/**
 *
 * @author Mega-pc
 */
public class ListFeedback extends SideMenuBaseForm {

    public ListFeedback(Resources res) {

        super(BoxLayout.y());

        FeedbackService fs = FeedbackService.getInstance();

        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);

        Image navimg = res.getImage("fdlast.png");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        int x = 0;
        int y = 0;

        String currentRank = "";
        String rankImage = null;
        String rankBadge = null;

        try {
            int rankStars = fs.feedback_total_stars_api();
            if (rankStars >= 0 && rankStars <= 20) {
                rankImage = "iron.png";
                rankBadge = "Iron";
            } else if (rankStars > 10 && rankStars <= 40) {
                rankImage = "bronze.png";
                rankBadge = "Bronze";
            } else if (rankStars > 20 && rankStars <= 60) {
                rankImage = "silver.png";
                rankBadge = "Silver";
            } else if (rankStars > 50 && rankStars <= 80) {
                rankImage = "gold.png";
                rankBadge = "Gold";
            } else {
                rankImage = "platinum.png";
                rankBadge = "Platinum";
            }

        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }

        Image navImg = res.getImage(rankImage);
        int size = Display.getInstance().convertToPixels(10);
        Image scaledImage = navImg.scaled(size, size);
        EncodedImage encodedImage = EncodedImage.createFromImage(scaledImage, false);
        Label rankimg = new Label("", encodedImage, "CenterTitle");

        //Systeme de rank
        Container remainingTasks = BoxLayout.encloseY(
                new Label("My Rank", "CenterSubTitle"),
                rankimg,
                new Label(rankBadge, "CenterSubTitle")
        );
        // remainingTasks.setUIID("RemainingTasks");

        // Load the star image from the theme
        Image starImage = res.getImage("stars.png");
        int size2 = Display.getInstance().convertToPixels(5);

        Image scaledImage2 = starImage.scaled(size2, size2);

        EncodedImage encodedImage2 = EncodedImage.createFromImage(scaledImage2, false);

        Label etoile = new Label("", encodedImage2, "CenterTitle");
        Container completedTasks = null;
        try {
            completedTasks = BoxLayout.encloseY(
                    etoile,
                    new Label(" " + fs.feedback_total_stars_api(), "CenterTitle"),
                    new Label("Nbr Of stars", "CenterSubTitle")
            );
            //  completedTasks.setUIID("CompletedTasks");
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }

        Style titleCmpStyle = new Style();
        titleCmpStyle.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
        titleCmpStyle.setBgImage(navimg);

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("My Feedbacks", "Title")
                        )
                ),
                GridLayout.encloseIn(2, remainingTasks, completedTasks)
        );

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD_TASK);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.addActionListener(e -> {
            new AddFeedback(res).show();
        });
// fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        titleCmp.setUnselectedStyle(titleCmpStyle);
        titleCmp.setSelectedStyle(titleCmpStyle);

        setupSideMenu(res);

        // ----------------------------------------------
        // Load the user comment image from the theme
        int j = 0;
        for (Feedback f : fs.fetchFeedback()) {
            j++;
            Container header = new Container(new BorderLayout());
            header.setUIID("MyCardHeader");
            Label titleLabel = new Label("Feedback n° " + j, "MyCardHeaderTitle");
            titleLabel.getAllStyles().setMargin(RIGHT, 5);
            header.add(BorderLayout.CENTER, titleLabel);
            
            //-------------------
            
            // create image
            Image profilePic = null;
            // EncodedImage enc = 
            try {
                profilePic = URLImage.createToStorage(
                        EncodedImage.createFromImage(Image.createImage("/load.png"), false),
                        f.getPhotoUser(),
                        f.getPhotoUser(),
                        URLImage.RESIZE_SCALE_TO_FILL
                );
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            //------------------
               
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());

       
            
// esmo el rajel eli 3mattlou comment 
// Load the star image from the theme
            Image starImageicon = res.getImage("icons8-star.png");

// Create a container to hold the user image, star rating, and comment text
            Container feedbackContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            feedbackContainer.add(header);
            Container ratingContainer = new Container(new FlowLayout(CENTER, CENTER));
            Container user = new Container(new FlowLayout(CENTER, CENTER));
            Container starContainer = new Container(new FlowLayout(CENTER, CENTER));
            Container number = new Container(new FlowLayout(CENTER, CENTER));

               Label userLabel = new Label("", profilePic);
            Label nomprenom = new Label(f.getNomUser() + " " + f.getPrenomUser());

// Add the appropriate number of stars to the star container
            double rating = f.getStars(); // Replace this with the actual rating
            for (int i = 0; i < rating; i++) {
                starContainer.add(new Label("", starImageicon));
            }

// Add the user image and star container to the rating container
            ratingContainer.add(starContainer);
               
            user.add(userLabel).add(nomprenom);
// Add the rating container to the feedback container
            int nst = (int) f.getStars();
            Label str = new Label("" + nst);
            number.add(str);
            feedbackContainer.add(user);
            feedbackContainer.add(ratingContainer);
            feedbackContainer.add(number);
// Add your comment text to the feedback container
            Label commentLabel = new Label("My comment: " + f.getCommentaire());
            commentLabel.setPreferredW(5);
            feedbackContainer.add(commentLabel);

            Container lineContainer = new Container(new BorderLayout());

// Set the background color of the container to black
            lineContainer.getAllStyles().setBgColor(0x000000);

// Set the preferred height of the line container to the thickness you want for the line
            lineContainer.setPreferredH(3);

// Add the line container to your form or container where you want the line to appear
            feedbackContainer.add(lineContainer);
            Button deleteButton = new Button();
            deleteButton.getAllStyles().setFgColor(0xFF0000);
            deleteButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, deleteButton.getUnselectedStyle()));
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    fs.deleteFeedback(f.getIdFeedback());
                    Dialog.show("Success", "Your feedback has been deleted.", "OK", null);
                    new ListFeedback(res).show();
                }
            });

            Button updateButton = new Button();
            updateButton.getAllStyles().setFgColor(0x0000FF);
            updateButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_UPDATE, updateButton.getUnselectedStyle()));
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    // fs.deleteFeedback(f.getIdFeedback());
                    new UpdateFeedback(res, f).show();
                }
            });

            Container buttonContainer = new Container(new FlowLayout(CENTER, CENTER));

            buttonContainer.add(deleteButton);
            buttonContainer.add(updateButton);

            feedbackContainer.add(buttonContainer);

            // create a new container with a BoxLayout layout
            Container line = new Container(new BoxLayout(BoxLayout.X_AXIS));

// set the container's border to create a line effect
            line.getAllStyles().setBorder(Border.createLineBorder(1, 0x000000));

// set the container's preferred size to create a horizontal line
            line.setPreferredH(1);
            feedbackContainer.add(line);
            this.add(feedbackContainer);
// ----------------------------------------------
        }
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

}
