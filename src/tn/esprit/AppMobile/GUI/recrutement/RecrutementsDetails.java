/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.recrutement;

import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.AppMobile.Entities.Recrutement;
import tn.esprit.AppMobile.GUI.recrutement.ListRecrutements;

/**
 *
 * @author dell
 */
public class RecrutementsDetails extends Form {
    


    public RecrutementsDetails(Resources res,Recrutement r) {

        setTitle("Recrutement Details");
        setScrollableX(true);
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
        new ListRecrutements(res).showBack();
        });
        Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
          content.add(new Label("Nom de l'entreprise :"+r.getNom()));
            content.add(new Label("Description:"+r.getDescription()));
            content.add(new Label("Salaire :"+r.getSalaire()));
            content.add(new Label("Date :"+r.getDate().toString()));
        
        
        this.add(content);

    }

}
