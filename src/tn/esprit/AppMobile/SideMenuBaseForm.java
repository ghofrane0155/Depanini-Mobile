/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package tn.esprit.AppMobile;

import com.codename1.components.ToastBar;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import tn.esprit.AppMobile.GUI.Events.EventsPieChart;
import tn.esprit.AppMobile.GUI.Events.ListEvents;
import tn.esprit.AppMobile.GUI.Events.ListEventsUser;
import tn.esprit.AppMobile.GUI.Events.TicketsOfThisUser;
import tn.esprit.AppMobile.GUI.Feedback.ListFeedback;
import tn.esprit.AppMobile.GUI.offre.AfficherOffre;
import tn.esprit.AppMobile.GUI.produit.ListProduitForm;
import tn.esprit.AppMobile.GUI.reclamation.ListReclamations;
import tn.esprit.AppMobile.GUI.recrutement.ListRecrutements;

/**
 * Common code that can setup the side menu
 *
 * @author Shai Almog
 */
public abstract class SideMenuBaseForm extends Form {
    
   
    
    
    public SideMenuBaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseForm(String title) {
        super(title);
    }

    public SideMenuBaseForm() {
    }

    public SideMenuBaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public void setupSideMenu(Resources res) {
        Image profilePic = res.getImage("youssef.jpg");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        
        
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
       Label profilePicLabel = new Label(SessionManager.getNomUser()+" "+SessionManager.getPrenomUser(), profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");

        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("  Home ", FontImage.MATERIAL_HOME,e -> {
            new DashIndex(res).show();
        });   
        
       
       
        
        
        
        //--------------------------------------Reclamation,Feedback-----------------------------------------
        getToolbar().addMaterialCommandToSideMenu("  Stat", FontImage.MATERIAL_TRENDING_UP, e -> new DashIndex(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Liste Reclamation", FontImage.MATERIAL_LIST , e -> { new ListReclamations(res).show();});
        getToolbar().addMaterialCommandToSideMenu("  Liste Feedbacks", FontImage.MATERIAL_LIST , e -> { new ListFeedback(res).show();});
        
        //--------------------------------------Events,Ticket-----------------------------------------
         getToolbar().addMaterialCommandToSideMenu("Events Stat ", FontImage.MATERIAL_TRENDING_UP, e -> new EventsPieChart(res).show());       
         getToolbar().addMaterialCommandToSideMenu("  Events for Freelancers ",FontImage.MATERIAL_TRENDING_UP,  e -> new ListEventsUser(res));        
         getToolbar().addMaterialCommandToSideMenu(" Client Events List", FontImage.MATERIAL_DASHBOARD,   e ->  { new ListEvents(res).show();});
        getToolbar().addMaterialCommandToSideMenu("  Card ", FontImage.MATERIAL_ACCESS_TIME,  e -> new TicketsOfThisUser(res));
        //--------------------------------------Recrutements-----------------------------------------
        getToolbar().addMaterialCommandToSideMenu("  Liste Recrutements", FontImage.MATERIAL_ACCESS_TIME , e -> { new ListRecrutements(res).show();});
        
        //--------------------------------------Produit-----------------------------------------
        getToolbar().addMaterialCommandToSideMenu("  Liste Produits", FontImage.MATERIAL_PRODUCTION_QUANTITY_LIMITS , e -> { new ListProduitForm(res).show();});
        
        //--------------------------------------Offre-----------------------------------------
        getToolbar().addMaterialCommandToSideMenu("  Liste Offres", FontImage.MATERIAL_PRODUCTION_QUANTITY_LIMITS , e -> { new AfficherOffre(res).show();});
        


        
        
        
        getToolbar().addMaterialCommandToSideMenu("  Change Password", FontImage.MATERIAL_VPN_KEY, e -> new PasswordUpdate(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Account Settings", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> new LoginForm(res).show());
        
    }

    protected abstract void showOtherForm(Resources res);
}
