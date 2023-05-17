/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.offre;


import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;

import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;

import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import tn.esprit.AppMobile.Entities.Offre;
import tn.esprit.AppMobile.Services.OffreService;
import tn.esprit.AppMobile.SideMenuBaseForm;

/**
 *
 * @author ryhab
 */
public class AfficherOffre extends SideMenuBaseForm{
    int i = 0;
    
     public AfficherOffre(Resources res) {
         super(BoxLayout.y());
         
         setupSideMenu(res);
         
        setTitle("Offre");
        setLayout(BoxLayout.y());
   
       
       ComboBox<String> typeComboBox = new ComboBox<>("Présentiel", "En ligne");
          
      int i = 0;
        ArrayList<Offre> offres = OffreService.getInstance().getAllOffres();
        for (Offre t : offres) {
            i++;
            addElement(res,i,t);
        }
         Button deleteButton = new Button("add");
        deleteButton.addActionListener(e -> {
            new GererOffre(res).show();
 });
        
        add(deleteButton);
       // getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
      
    
    }

  

   



  
   public void addElement(Resources res ,int i ,Offre stat) {

    Container container = new Container(new BorderLayout());
    container.setUIID("ListRenderer");             
            Label titleLabel = new Label("Offre n° " + i, "MyCardHeaderTitle");
            titleLabel.getAllStyles().setMargin(RIGHT, 5);
           
    Label nomLabel = new Label("Nom: " + stat.getNomOffre());    
    Label typelabel = new Label("Type: " + stat.getTypeOffre());
    Label descLabel = new Label("Description: " + stat.getDescriptionOffre());
    Label locLabel = new Label("Localisation: " + stat.getLocalisationOffre());
    Label prixLabel = new Label("Prix: " + stat.getPrixOffre());
    
    
    

    
    /*Button editButton = new Button("Modifier");
    editButton.addActionListener(e -> {
    ModifForum updateForm = new ModifForum(this, stat);
    updateForm.show();
});*/
    
    
   
    

    Button deleteButton = new Button("Supprimer");
    deleteButton.addActionListener(e -> {
  
    
            
            OffreService.getInstance().delete((int) stat.getIdOffre());
                       
            removeComponent(container);
            Dialog.show("Succès", "La suppression a été effectuée", "OK", null);
            new AfficherOffre(res).show();
    
    
    });
       Button updateButton = new Button("Update");
    updateButton.addActionListener(e -> {
        System.out.println("allooooo zqsdqs");          
           new UpdateOffre(res,stat).show();
 
    });


    container.add(BorderLayout.WEST, BoxLayout.encloseY(
            titleLabel,nomLabel,typelabel,descLabel,locLabel,prixLabel
    ));
    container.add(BorderLayout.EAST, BoxLayout.encloseY(
             deleteButton ,updateButton
    ));

    // Ajouter un séparateur rouge entre chaque élément
    Container separator = new Container();
    separator.setUIID("Separator");
    add(separator);

    add(container);
}

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}