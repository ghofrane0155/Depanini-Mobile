/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.Feedback;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.layouts.TextModeLayout;
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
public class UpdateFeedback extends SideMenuBaseForm {

    private final Container container;
    private final Form form;
    private int selectedIndex = 0;

    public UpdateFeedback(Resources res, Feedback f) {
        super(BoxLayout.y());

        FeedbackService fs = FeedbackService.getInstance();

        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);

        Image navimg = res.getImage("fdlast.png");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        // remainingTasks.setUIID("RemainingTasks");
        // Load the star image from the theme
        Container completedTasks = null;

        completedTasks = BoxLayout.encloseY(
                new Label(" ", "CenterTitle"),
                new Label("", "CenterSubTitle")
        );
        //  completedTasks.setUIID("CompletedTasks");

        Style titleCmpStyle = new Style();
        titleCmpStyle.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
        titleCmpStyle.setBgImage(navimg);

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("Update Feedback", "Title")
                        )
                ),
                GridLayout.encloseIn(1, completedTasks)
        );

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD_TASK);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.addActionListener(e -> {
            // new Addfeedback(res).show();
        });
// fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        titleCmp.setUnselectedStyle(titleCmpStyle);
        titleCmp.setSelectedStyle(titleCmpStyle);

        setupSideMenu(res);

        //--------------------------------------------------------------------
        Image unselectedStarIcon = res.getImage("unselected.png");
        Image selectedStarIcon = res.getImage("slected.png");

        container = new Container(new FlowLayout(CENTER, CENTER));
        

        //taswiret_el 3abed eli 3amlou comment
        
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

        Label userLabel = new Label("", profilePic);
        Label nomprenom = new Label(f.getNomUser() + " " + f.getPrenomUser());
        Container user = new Container(new FlowLayout(CENTER, CENTER));

        container.setLayout(new BoxLayout(BoxLayout.X_AXIS));
    
            createStarButton(container, unselectedStarIcon, selectedStarIcon,f.getStars());
        
        int s = getSelectedIndex();
        
        user.add(userLabel).add(nomprenom);

        form = new Form("");
        form.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        form.add(user);
        // Add a label
        form.addComponent(new Label("Please rate our service:"));

        // Add the star rating container
        form.addComponent(container);

        // Add a text field for comments
        final SpanLabel comments = new SpanLabel("Your Comment");
        comments.setTextBlockAlign(Component.CENTER);
        form.addComponent(comments);
        TextField descField = new TextField(f.getCommentaire(), "", 20, TextField.ANY);
        descField.setUIID("TextFieldBlack");

        form.addComponent(descField);

        // Add a submit button
        Button submit = new Button("Submit");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.out.println(descField.getText());
                if (descField.getText().equals("")){
                    Dialog.show("Error", "Please enter a comment", "OK", null);
                   return;
                }
                int selectedStarIndex = getSelectedIndex();
                System.out.println("ahawa"+descField.getText());
                System.out.println(f.getIdFeedback()+"Selected star index: " + selectedStarIndex);
                fs.updateFeedback(f.getIdFeedback(), selectedStarIndex, descField.getText());
                 Dialog.show("Thank You!", "Your feedback has been submitted.", "OK", null);
                 new ListFeedback(res).show();
            }
        });
        form.addComponent(submit);

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, form);
        this.setBackCommand(new Command("Back") {
            public void actionPerformed(ActionEvent ev) {
                new ListFeedback(res).show();
            }
        });
    }

   private void createStarButton(final Container parent, final Image unselectedStarIcon, final Image selectedStarIcon,double f) {
    for (int i = 0; i < 5; i++) {
        final CheckBox cb = new CheckBox();
        cb.setToggle(true);
        cb.setIcon(unselectedStarIcon);
        cb.setPressedIcon(selectedStarIcon);

        if (i < f) {
            cb.setSelected(true);
        }

        cb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (cb.isSelected()) {
                    selectedIndex = parent.getComponentIndex(cb)+1;
                    boolean selected = true;
                    for (int iter = 0; iter < parent.getComponentCount(); iter++) {
                        Component current = parent.getComponentAt(iter);
                        if (current == cb) {
                            selected = false;
                            continue;
                        }
                        ((CheckBox) current).setSelected(selected);
                    }
                } else {
                    selectedIndex = 0;
                }
            }
        });

        parent.addComponent(cb);
    }
}


    // Method to retrieve the selected index
    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}
