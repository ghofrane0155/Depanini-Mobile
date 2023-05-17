/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.Events;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import static com.codename1.io.Log.e;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
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
import com.codename1.ui.TextArea;
//import org.apache.commons.io.FilenameUtils;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import tn.esprit.AppMobile.Entities.Events;
import tn.esprit.AppMobile.GUI.Home;
import tn.esprit.AppMobile.GUI.reclamation.AddReclamation;
import tn.esprit.AppMobile.MyApplication;
import tn.esprit.AppMobile.Services.EventsService;
import tn.esprit.AppMobile.Services.EventsStat;
import tn.esprit.AppMobile.SideMenuBaseForm;
import tn.esprit.AppMobile.StatsForm;

/**
 *
 * @author noure
 */
public class ListEvents extends SideMenuBaseForm {
    
    EventsService es =EventsService.getInstance();
    EventsStat St =EventsStat.getInstance();
    public ListEvents (Resources res){
        
        super(BoxLayout.y());     
        setupSideMenu(res);
        
        
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        
        
        Image navimg = res.getImage("event1 (3).jpg");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
        int x = 0;
        try {
            x = St.totalEvents();
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }
        int y = 0;
        try {
            y = St.totalTickets();
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }
        
        Container remainingTasks = BoxLayout.encloseY(
                        new Label(""+x, "CenterTitle"),
                        new Label("Total Events ", "CenterSubTitle")
                );
        
        Container completedTasks = BoxLayout.encloseY(
                        new Label(""+y, "CenterTitle"),
                        new Label("completed tasks", "CenterSubTitle")
        );
        

        Style titleCmpStyle = new Style();
        titleCmpStyle.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
        titleCmpStyle.setBgImage(navimg);
        
        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseY(
                                    new Label("Client Name", "SubTitle"),
                                    new Label("Your Events", "Title")
                                )
                            ),
                        GridLayout.encloseIn(2, remainingTasks, completedTasks)
                );
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD_TASK);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.addActionListener(e -> {
            new CreateEvent(res).show();
        });
        
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        titleCmp.setUnselectedStyle(titleCmpStyle);
        titleCmp.setSelectedStyle(titleCmpStyle);
        
        
                        
       
        
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        
       
        
       
       
        Container cards = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        for (Events e : es.fetchEvents()) {
            
    Container card = new Container(new BorderLayout());
    Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));


    EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(100, 100, 0xffcccccc), false);
    URLImage urlImage = URLImage.createToStorage(placeholder, "temp.jpg", e.getImageevent());
    ImageViewer imageViewer = new ImageViewer(urlImage);
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

  // Create a container to hold the buttons
Container buttonsContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

// Add the details button
Button detailsButton = new Button();
FontImage.setMaterialIcon(detailsButton, FontImage.MATERIAL_INFO);
detailsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt) {
        Events event = es.getEvent(e.getIdevent());

        try {
            // Load the theme resources using createTheme() method
            Resources theme = Resources.openLayered("/theme");

            // Show the event details form
            new EventDetails( theme,e).show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
});

// Add the update button
Button updateButton = new Button();
FontImage.setMaterialIcon(updateButton, FontImage.MATERIAL_UPDATE);
updateButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt) {
        Events event = es.getEvent(e.getIdevent());
        
        try {
            // Load the theme resources using createTheme() method
            Resources theme = Resources.openLayered("/theme");

            // Show the event details form
            new UpdateEvent( theme,e).show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
});

// Add the delete button
Button deleteButton = new Button();
FontImage.setMaterialIcon(deleteButton, FontImage.MATERIAL_DELETE);
deleteButton.addPointerPressedListener(l -> {
            
            Dialog dig = new Dialog("Suppression");
            
            if(dig.show("Suppression","Vous voulez supprimer ce reclamation ?","Annuler","Oui")) {
                dig.dispose();
            }
            else {
                dig.dispose();
                 }
                //n3ayto l suuprimer men service Reclamation
                if(EventsService.getInstance().deleteEvent((int)(double)e.getIdevent())) {
                try {
                    Resources theme = Resources.openLayered("/theme");
                    new ListEvents(theme).show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                }
           
        });

// Add the buttons to the container
buttonsContainer.add(detailsButton);
buttonsContainer.add(updateButton);
buttonsContainer.add(deleteButton);

// Add the container to the card
    card.add(BorderLayout.EAST, buttonsContainer);


card.getStyle().setBorder(Border.createLineBorder(1, 0xCCCCCC));
card.getStyle().setBgColor(0xFFFFFF);

card.add(BorderLayout.CENTER, content);

cards.add(card);

}

        
        this.add(cards);
        
        
    
    }
    
    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
        
        
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
    
    private void addButton(Image img, String title, boolean liked, int likeCount, int commentCount) {
       int height = Display.getInstance().convertToPixels(11.5f);
       int width = Display.getInstance().convertToPixels(14f);
       Button image = new Button(img.fill(width, height));
       image.setUIID("Label");
       Container cnt = BorderLayout.west(image);
       cnt.setLeadComponent(image);
       TextArea ta = new TextArea(title);
       ta.setUIID("NewsTopLine");
       ta.setEditable(false);

       Label likes = new Label(likeCount + " Likes  ", "NewsBottomLine");
       likes.setTextPosition(RIGHT);
       if(!liked) {
           FontImage.setMaterialIcon(likes, FontImage.MATERIAL_FAVORITE);
       } else {
           Style s = new Style(likes.getUnselectedStyle());
           s.setFgColor(0xff2d55);
           FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, s);
           likes.setIcon(heartImage);
       }
       Label comments = new Label(commentCount + " Comments", "NewsBottomLine");
       FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);
       
       
       cnt.add(BorderLayout.CENTER, 
               BoxLayout.encloseY(
                       ta,
                       BoxLayout.encloseX(likes, comments)
               ));
       add(cnt);
       image.addActionListener(e -> ToastBar.showMessage(title, FontImage.MATERIAL_INFO));
    }
    
   
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    

     }
        
  
   private void addButtonBottom(Image arrowDown, String text, int color, boolean first) {
        
        MultiButton finishLandingPage = new MultiButton(text);
        finishLandingPage.setEmblem(arrowDown);
        finishLandingPage.setUIID("Container");
        finishLandingPage.setUIIDLine1("TodayEntry");
        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(),  first));
        finishLandingPage.setIconUIID("Container");
        add(FlowLayout.encloseIn(finishLandingPage));
    }
    
    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if(first) {
            y = height / 6 + 1;
        }
        g.drawLine(height / 2, y, height / 2, height);
        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }

    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    } 
    
    
 }
    

