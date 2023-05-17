/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.offre;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
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
import tn.esprit.AppMobile.Entities.Offre;
import tn.esprit.AppMobile.Services.OffreService;
import tn.esprit.AppMobile.SideMenuBaseForm;

/**
 *
 * @author Mega-pc
 */
public class UpdateOffre extends SideMenuBaseForm{

    public UpdateOffre(Resources res,Offre o) {
         super(BoxLayout.y());
         
         setupSideMenu(res);
         
            setTitle("Ajouter votre offre");
        

        TextField tfprix = new TextField(""+o.getPrixOffre(), "Prix");
        TextField tfdesc = new TextField(""+o.getDescriptionOffre(), "Description");
        TextField tfloc = new TextField(""+o.getLocalisationOffre(), "Localisation");
        TextField tfnom = new TextField(""+o.getNomOffre(), "Nom");        
      

        
        tfprix.getAllStyles().setFgColor(0x000000);
        tfdesc.getAllStyles().setFgColor(0x000000);
        tfloc.getAllStyles().setFgColor(0x000000);
        tfnom.getAllStyles().setFgColor(0x000000);

        Container emptySpace1 = new Container(new BorderLayout());

        emptySpace1.add(BorderLayout.CENTER, new Label(" "));
        Container emptySpace2 = new Container(new BorderLayout());
        emptySpace2.add(BorderLayout.CENTER, new Label(" "));
        Container emptySpace3 = new Container(new BorderLayout());
        emptySpace3.add(BorderLayout.CENTER, new Label(" "));
        Container emptySpace4 = new Container(new BorderLayout());
        emptySpace4.add(BorderLayout.CENTER, new Label(" "));
        Container emptySpace5 = new Container(new BorderLayout());
        emptySpace5.add(BorderLayout.CENTER, new Label(" "));
        Container emptySpace6 = new Container(new BorderLayout());
        emptySpace6.add(BorderLayout.CENTER, new Label(" "));

        ComboBox<String> cbOffres = new ComboBox<>("Présentiel", "En ligne");
        TextField tftype = new TextField("", "type");
        cbOffres.addActionListener(evt -> {
            String selectedType = cbOffres.getSelectedItem();
            tftype.setText(selectedType);
        });
        Button btnValider = new Button("Update Offre");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfnom.getText().length() == 0)) {
                    Dialog.show("Alert", "Veuillez replir tous les champs !", new Command("OK"));
                } else {                                       
                        double num = Double.parseDouble(tfprix.getText());                        
                          Offre t = new Offre(num, tfdesc.getText(), tfloc.getText(), tfnom.getText(), tftype.getText(),1,3);
                          System.out.println(t.toString());
                        OffreService.getInstance().updateOffre(o.getIdOffre(),t); 
                        Dialog.show("Success", "votre Offre a ete ajouter avec Success", new Command("OK"));
                 new AfficherOffre(res).show();

                }

            }
        });

        addAll(emptySpace5, tfnom, emptySpace1, tfdesc, emptySpace3, cbOffres, emptySpace4, tfloc, emptySpace2, tfprix, emptySpace6, btnValider);
//        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new Home().showBack());

         
    }
    
    

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
