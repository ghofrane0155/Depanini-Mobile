/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.produit;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.AppMobile.Entities.Produit;
import tn.esprit.AppMobile.Services.ProduitService;
import tn.esprit.AppMobile.SideMenuBaseForm;

/**
 *
 * @author Msi
 */
public class ModifProduitForm extends SideMenuBaseForm {

    ProduitService ps = ProduitService.getInstance();
    Produit produit;

    public ModifProduitForm(Resources res, Produit p) {

        Container one = new Container();
// Set the form's title
        setTitle("Update du produit");

// Create the form components
        TextField nomField = new TextField("", p.getNomproduit());
        TextField categorieField = new TextField("", p.getCategorieproduit());
        TextField descriptionField = new TextField("", p.getDescription());
        TextField prixField = new TextField("", Integer.toString((int) p.getPrixproduit()));

// Disable editing of the fields
        nomField.setEditable(true);
        categorieField.setEditable(true);
        descriptionField.setEditable(true);
        prixField.setEditable(true);
        
        nomField.getAllStyles().setFgColor(0x000000);
        categorieField.getAllStyles().setFgColor(0x000000);

        descriptionField.getAllStyles().setFgColor(0x000000);

        prixField.getAllStyles().setFgColor(0x000000);

// Add the form components
        one.add(nomField);
        one.add(new Label("Catégorie:"));
        one.add(categorieField);
        one.add(new Label("Description:"));
        one.add(descriptionField);
        one.add(new Label("Prix:"));
        one.add(prixField);

        Button btn = new Button("Submit");
        btn.addActionListener((e) -> {

            double myDouble = Double.parseDouble(prixField.getText());
            Produit prod = new Produit(p.getIdproduit(), myDouble, nomField.getText(), categorieField.getText(), descriptionField.getText());

            ProduitService.getInstance().updateProduit(prod);

            new ListProduitForm(res).show();
        });

        add(one).add(btn);

    }

    @Override
    protected void showOtherForm(Resources res) {

    }

}
