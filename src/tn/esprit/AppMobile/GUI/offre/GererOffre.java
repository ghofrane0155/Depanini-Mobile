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
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.AppMobile.Entities.Offre;
import tn.esprit.AppMobile.GUI.Home;
import tn.esprit.AppMobile.Services.OffreService;
import tn.esprit.AppMobile.SideMenuBaseForm;

/**
 *
 * @author ryhab
 */
public class GererOffre extends SideMenuBaseForm {

    public GererOffre(Resources res) {
         super(BoxLayout.y());
         
         setupSideMenu(res);
         
        setTitle("Ajouter votre offre");
        setLayout(BoxLayout.y());

        TextField tfprix = new TextField("", "Prix");
        TextField tfdesc = new TextField("", "Description");
        TextField tfloc = new TextField("", "Localisation");
        TextField tfnom = new TextField("", "Nom");        
        

        
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
        Button btnValider = new Button("Ajouter Offre");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfnom.getText().length() == 0)) {
                    Dialog.show("Alert", "Veuillez replir tous les champs !", new Command("OK"));
                } else {
                    try {
                        
                        double num = Double.parseDouble(tfprix.getText());
                        
                          Offre t = new Offre(num, tfdesc.getText(), tfloc.getText(), tfnom.getText(), tftype.getText(),1,3);
                          System.out.println(t.toString());
                        OffreService.getInstance().addOffre(t); 
                        Dialog.show("Success", "votre Offre a ete ajouter avec Success", new Command("OK"));
                        new AfficherOffre(res).show();
                        if (tfdesc.getText().equals("") || tfloc.getText().equals("") ||tfnom.getText().equals("") || tftype.getText().equals("")) {
                            Dialog.show("ERROR", "veiller remplir les champs nessesaire", new Command("OK"));
                       } 
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

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