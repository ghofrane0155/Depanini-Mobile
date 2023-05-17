package tn.esprit.AppMobile.GUI.produit;

import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import tn.esprit.AppMobile.Entities.Produit;
import tn.esprit.AppMobile.Services.ProduitService;

import java.util.ArrayList;
import tn.esprit.AppMobile.SideMenuBaseForm;

public class ListProduitForm extends SideMenuBaseForm {

    ProduitService ps = ProduitService.getInstance();

    public ListProduitForm(Resources res) {
        //---------------------
          super(BoxLayout.y());     
        setupSideMenu(res);
        //*****************
        
        setTitle("Produits");

        // Set the form's layout
        setLayout(new BorderLayout());

        // Create a container for the products
        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setScrollableY(true);
//          this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
//            new Home().showBack();
//        });

        // Fetch and order the products by name
        ArrayList<Produit> produits = (ArrayList<Produit>) ps.fetchProduits();
//    Collections.sort(produits, (p1, p2) -> p1.getNomproduit().compareTo(p2.getNomproduit())); //classing

        // Add each product to the container
        for (Produit p : produits) {
            // Create a MultiButton for the product
            MultiButton mb = new MultiButton();
            mb.setTextLine1(p.getNomproduit());
            mb.setTextLine1(p.getDescription());

            mb.setTextLine1(p.getCategorieproduit());
          

            mb.setTextLine2(p.getPrixproduit() + " DT");

            // Add a button to view the product details
            Button detailsButton = new Button("Details");
            detailsButton.addActionListener(evt -> {
                // Handle button click
            });
            
            // Add a button to view the product details
            Button updateButton = new Button("Update");
            updateButton.addActionListener(evt -> {
                new ModifProduitForm(res, p).show();
            });

            // Add the MultiButton and button to a container with a border
            Container mbContainer = BorderLayout.center(mb);
            mbContainer.getAllStyles().setBorder(Border.createLineBorder(1, 0x999999));
            mbContainer.setLeadComponent(detailsButton);
            mbContainer.add(BorderLayout.EAST, detailsButton);
            mbContainer.setLeadComponent(updateButton);
            mbContainer.add(BorderLayout.EAST, updateButton);
            container.add(mbContainer);
        }

          // Add a button to view the product details
            Button detailsButton = new Button("add");
            detailsButton.addActionListener(evt -> {
                new AjoutProduitForm(res).show();
            });
            container.add(detailsButton);
        // Add the container to the form
        add(BorderLayout.CENTER, container);
        
    }

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
