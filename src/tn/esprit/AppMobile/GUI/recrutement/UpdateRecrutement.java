/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.recrutement;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.AppMobile.Entities.Recrutement;
import tn.esprit.AppMobile.Services.RecrutementService;
import tn.esprit.AppMobile.SideMenuBaseForm;

/**
 *
 * @author Mega-pc
 */
public class UpdateRecrutement extends SideMenuBaseForm{

    public UpdateRecrutement(Resources res ,Recrutement r,double id) {
         super(BoxLayout.y());
         
         setupSideMenu(res);
         
        setTitle("Ajouter un Recrutement");
        setLayout(BoxLayout.y());

        TextField tfprix = new TextField(""+r.getSalaire() , "Prix");
        TextField tfdesc = new TextField(""+r.getDescription(), "Description");
        TextField tfnom = new TextField(""+r.getNom(), "Nom");        
        
        
        tfprix.getAllStyles().setFgColor(0x000000);
        tfdesc.getAllStyles().setFgColor(0x000000);
        tfnom.getAllStyles().setFgColor(0x000000);

        Container emptySpace1 = new Container(new BorderLayout());

        emptySpace1.add(BorderLayout.CENTER, new Label("Nom"));
        Container emptySpace2 = new Container(new BorderLayout());
        emptySpace2.add(BorderLayout.CENTER, new Label("Salaire"));
        Container emptySpace3 = new Container(new BorderLayout());
        emptySpace3.add(BorderLayout.CENTER, new Label("Description"));
   
        Button btnValider = new Button("Ajouter Recrutement");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                 if (tfdesc.getText().equals("") ||tfnom.getText().equals("") || tfprix.getText().equals("")) {
                            Dialog.show("ERROR", "veiller remplir les champs nessesaire", new Command("OK"));
                            return;
                       }       
                 System.out.println(tfdesc.getText()+"-----  "+tfnom.getText()+" ----"+tfprix.getText());
                        double num = Double.parseDouble(tfprix.getText());     
                      Recrutement r = new Recrutement(id,tfnom.getText(), tfdesc.getText(), num);
                      
                         
                       RecrutementService.getInstance().updateRecrutement(r);                   
                        Dialog.show("Success", "votre  Recrutement a ete mise a jour avec Success", new Command("OK"));
                       new ListRecrutements(res).show();
            }
        });

        addAll(  emptySpace1,tfnom, emptySpace3,tfdesc, emptySpace2, tfprix, btnValider);
//        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new Home().showBack());

    }
    
    
    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
