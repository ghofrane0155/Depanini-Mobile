/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.produit;

import com.barcodelib.barcode.Linear;
import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import java.util.Date;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import tn.esprit.AppMobile.Entities.Produit;
import tn.esprit.AppMobile.GUI.Home;
import tn.esprit.AppMobile.Services.ProduitService;
import tn.esprit.AppMobile.SideMenuBaseForm;

/**
 *
 * @author MSI
 */
public class AjoutProduitForm  extends SideMenuBaseForm {
    
    
    
     int iduser =2;
    int barcode =78657;
    String image = "path";
    
     
    Form current;
    public AjoutProduitForm( Resources res ) {
          
     
        
        
        super("Ajout Produit",BoxLayout.y());
        setupSideMenu(res);
        //*****************
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Produit");
        getContentPane().setScrollVisible(false);
        
           
                
        
        
        
        
        Tabs swipe = new Tabs();
        
        Label s1 = new Label();
        Label s2 = new Label();
        
        addTab(swipe,s1, res.getImage("gradient-overlay.png"),"","",res);
        
        //
        
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
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
            
        /******textfields******/
        TextField nomproduit = new TextField("", "entrer le nom du produit");
        nomproduit.setUIID("TextFieldBlack");
        addStringValue("Nomproduit",nomproduit);
        
        TextField categorie= new TextField("", "entrer la categorie du produit");
        categorie.setUIID("TextFieldBlack");
        addStringValue("Categorie",categorie);
        
        TextField description= new TextField("", "entrer sa description");
        description.setUIID("TextFieldBlack");
        addStringValue("Description",description);
        
        TextField prix= new TextField("", "tapez le prix ");
        prix.setUIID("TextFieldBlack");
        addStringValue("Prix",prix);
        
        TextField upload= new TextField("", "votre path");
        prix.setUIID("TextFieldBlack");
        addStringValue("Image",upload);

        
        String prixString = prix.getText();
        int prixInt = 100;
        
        
        /*****image***/
        Button btnimage = new Button ("Upload");
        Label imagelabel = new Label();
        addStringValue("", btnimage);
        btnimage.addActionListener((e)->  {
           String path= Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
           if(path!=null) {
               try {
        upload.setText(path);
        
                   Image img =  Image.createImage(path);
                   imagelabel.setIcon(img);
                     refreshTheme();  
               } catch (IOException ex) {
                 ex.printStackTrace();
               }
           }
         
        });
        
        
        
              

        
        Button btnAjouter = new Button ("Ajouter");
        addStringValue("", btnAjouter);
        
        //when u click on the button
        btnAjouter.addActionListener((e) -> {
            
            try {
                
                if (nomproduit.getText()=="" || description.getText()=="" || categorie.getText()=="" || prix.getText()=="" )
                
                { Dialog.show("Vérifier les donnés","","Annuler","OK");}
                
                else {
                    InfiniteProgress ip = new InfiniteProgress(); //loading
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    //String currentDate = formatter.format(new Date());
                    Date current = new Date();

                    Produit p = new Produit(iduser,prixInt,barcode,
                            String.valueOf(nomproduit.getText()),
                            String.valueOf(categorie.getText()),
                            String.valueOf(description.getText()),
                            String.valueOf(upload.getText())
                            ,current);
                    
                    
                           
                    
                    System.out.println("data produit =="+p);
                    
                    ProduitService.getInstance().ajoutProduit(p);
                    
                    
                            
                        String nom = nomproduit.getText();//data i want to stock in my  barcode
                        String codename ="Produit: "+nom;
                        
                        
                        Linear barcode = new Linear();
                        barcode.setType(Linear.CODE128B);
                         barcode.setData(codename); //data inside barcode
                         barcode.setI(11.0f);
                         
                        barcode.renderBarcode("../" + nom + ".png");

                    System.out.println("Code à barre créer sous le nom : " +nom+".png");
                    Dialog dlg = new Dialog("Produit Ajouté ✔ ");
                    dlg.setLayout(new BorderLayout());

                    SpanLabel bodyLabel = new SpanLabel("Code à barre creé", "DialogBody");
                    dlg.add(BorderLayout.CENTER, bodyLabel);

                    Button okButton = new Button("OK");
                    okButton.addActionListener((ok) -> dlg.dispose());
                    dlg.add(BorderLayout.SOUTH, okButton);

                    int h = Display.getInstance().getDisplayHeight();
                    dlg.setDisposeWhenPointerOutOfBounds(true);
                    dlg.show(h / 9 * 7, 0, 0, 0);
                  
                    iDialog.dispose(); //ends the loading
                    new ListProduitForm(res);

                }
               
                } catch(Exception ex) {
                    System.out.println(ex.getMessage());;
                }
        }
        
        );
        
    }

    private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0x773763));
    }
    
    private void addTab(Tabs swipe, Label spacer , Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        
        if(image.getHeight() < size) {
            image = image.scaledHeight(size);
        }
        
        
        
        if(image.getHeight() > Display.getInstance().getDisplayHeight() / 2 ) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overLay = new Label("","ImageOverlay");
        
        
        Container page1 = 
                LayeredLayout.encloseIn(
                imageScale,
                        overLay,
                        BorderLayout.south(
                        BoxLayout.encloseY(
                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                        )
                    )
                );
        
        swipe.addTab("",res.getImage("home.jpg"), page1);
        
        
        
        
    }
    
     private void updateArrowPosition(Button btn, Label l) {
        
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()  / 2  - l.getWidth() / 2 );
        l.getParent().repaint();
    }

     public void bindButtonSelection(Button btn , Label l ) {
        
        btn.addActionListener(e-> {
        if(btn.isSelected()) {
            updateArrowPosition(btn,l);
        }
    });
    }

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

      public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }
    
    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(39);
        separator.setShowEvenIfBlank(true);
        return separator;
    }
}