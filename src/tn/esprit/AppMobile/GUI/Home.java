/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI;

import tn.esprit.AppMobile.GUI.reclamation.ListReclamations;
import com.codename1.components.ImageViewer;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import tn.esprit.AppMobile.SideMenuBaseForm;
import tn.esprit.AppMobile.StatsForm;



/**
 *
 * @author Mega-pc
 */
public class Home extends SideMenuBaseForm{
private  EncodedImage logoImg;
    
    public Home(Resources res) {
        // First part
        setTitle("HOME");
        setScrollableY(true);
        setUIID("homePage");
        
        try {
            //image bg 
            setBgImage(EncodedImage.create("/home.jpg"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
                
        // side bar 
        getToolbar().addCommandToSideMenu("Acceuil", null, e -> {
            this.show();
        });
         getToolbar().addCommandToSideMenu("Reclamation", null, e -> {
            this.show();
        });
          getToolbar().addCommandToSideMenu("list Reclamation", null, e -> {
            new ListReclamations(res).show();
        });
          
    try {
        // add logo
        logoImg = EncodedImage.create("/logo.png");
    } catch (IOException ex) {
         System.out.println(ex.getMessage());
    }
    // image viewr 
    ImageViewer imglogo = new ImageViewer(logoImg);
        add(imglogo);
      
        
    
    }
 @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
    
}
