/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.Events;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.list.MultiList;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import tn.esprit.AppMobile.Entities.Events;
import tn.esprit.AppMobile.GUI.Home;
import tn.esprit.AppMobile.MyApplication;
import tn.esprit.AppMobile.ProfileForm;
import tn.esprit.AppMobile.Services.EventsService;
import tn.esprit.AppMobile.SideMenuBaseForm;

/**
 * A form that displays a list of events and allows the user to participate in them.
 */
public class ListEventsUser extends SideMenuBaseForm {

    private EventsService es = EventsService.getInstance();
    private ArrayList<Map<String, Object>> eventsList = new ArrayList<>();

    public ListEventsUser(Resources res) {
          super(BoxLayout.y());
          setupSideMenu(res);
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("participate to the Events ");
        getContentPane().setScrollVisible(false);
        
        
        tb.addSearchCommand(e -> {});
        
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("event1 (1).jpg"), spacer1, "15 Likes  ", "85 Comments", "Create your own Event ( formation / social Event / ... )! ");
        addTab(swipe, res.getImage("event1 (2).jpg"), spacer2, "100 Likes  ", "66 Comments", "Depanini will help you organize your Event ! ");
                
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
                
        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        
        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("All", barGroup);
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("Featured", barGroup);
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("Popular", barGroup);
        popular.setUIID("SelectBar");
        RadioButton myFavorite = RadioButton.createToggle("My Favorites", barGroup);
        myFavorite.setUIID("SelectBar");
       
        Container cards = new Container(new BoxLayout(BoxLayout.Y_AXIS));

    for (Events e : es.fetchEvents()) {
        Container card = new Container(new BorderLayout());
        Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        EncodedImage image = EncodedImage.createFromImage(MyApplication.theme.getImage("logo.png"), false);
        ImageViewer imageViewer = new ImageViewer(image);
        imageViewer.setPreferredH(150);
        card.add(BorderLayout.WEST, imageViewer);

        Label nameLabel = new Label(e.getNomevent());
        nameLabel.getStyle().setFgColor(0x000000);
        nameLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        content.add(nameLabel);

        Label dateLabel = new Label(e.getDatedebutevent().toString());
        dateLabel.getStyle().setFgColor(0x666666);
        dateLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        content.add(dateLabel);

        Label orgLabel = new Label(e.getLieuevent());
        orgLabel.getStyle().setFgColor(0x666666);
        orgLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        content.add(orgLabel);

        Button participateButton = new Button("Participate");
        participateButton.getStyle().setBgColor(0xffffff); // Set button color to blue
        participateButton.getStyle().setFgColor(0x2196F3); // Set text color to white
        participateButton.getStyle().setAlignment(Component.CENTER); // Center the button text
        participateButton.addActionListener(evt -> new CreateTicket(res, e).show()); // Add action listener to the button
        content.add(participateButton);

        card.getStyle().setBorder(Border.createLineBorder(1, 0xffffff));
        card.getStyle().setBgColor(0xFFFFFF);

        card.add(BorderLayout.CENTER, content);

        cards.add(card);
    }

    this.add(cards);
    
    show();
}
    

    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        
        Container page1 = 
            LayeredLayout.encloseIn(
                image,
                overlay,
                BorderLayout.south(
                    BoxLayout.encloseY(
                            new SpanLabel(text, "LargeWhiteText"),
                            FlowLayout.encloseIn(likes, comments),
                            spacer
                        )
                )
            );

        swipe.addTab("", page1);
    }
    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }

   

