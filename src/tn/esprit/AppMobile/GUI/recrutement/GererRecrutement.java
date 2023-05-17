/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.recrutement;

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
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import tn.esprit.AppMobile.Entities.Offre;
import tn.esprit.AppMobile.Entities.Recrutement;
import tn.esprit.AppMobile.GUI.offre.AfficherOffre;
import tn.esprit.AppMobile.Services.OffreService;
import tn.esprit.AppMobile.Services.RecrutementService;
import tn.esprit.AppMobile.SideMenuBaseForm;

/**
 *
 * @author Mega-pc
 */
public class GererRecrutement extends SideMenuBaseForm {

    private static final String ACCOUNT_SID = "AC1269849337e5bd69c2cec023fdfde7e8";
    private static final String AUTH_TOKEN = "57b78d6d3d113c2e4b49804baa5ac182";
// The Twilio phone number you want to use to send SMS messages
    private static final String TWILIO_NUMBER = "+15074187445";

    // The recipient phone number you want to send an SMS message to
    private static final String RECIPIENT_NUMBER = "+21650607702";

    public GererRecrutement(Resources res) {
        super(BoxLayout.y());

        setupSideMenu(res);

        setTitle("Ajouter un Recrutement");
        setLayout(BoxLayout.y());

        TextField tfprix = new TextField("", "Salaire");
        TextField tfdesc = new TextField("", "Description");
        TextField tfnom = new TextField("", "Nom");

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
                if (tfdesc.getText().equals("") || tfnom.getText().equals("") || tfprix.getText().equals("")) {
                    Dialog.show("ERROR", "veiller remplir les champs nessesaire", new Command("OK"));
                    return;
                }

                double num = Double.parseDouble(tfprix.getText());
                Recrutement r = new Recrutement(tfnom.getText(), tfdesc.getText(), num);

                RecrutementService.getInstance().addRecrutement(r);

                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

                // Send an SMS message using the Twilio API
                Message message = Message.creator(
                        new PhoneNumber(RECIPIENT_NUMBER),
                        new PhoneNumber(TWILIO_NUMBER),
                        " Votre Recrutement ajouté avec succes! "
                ).create();

                // Print the message SID to the console
                System.out.println("SMS message sent with SID: " + message.getSid());
                Dialog.show("Succés", "Recrutement ajouté avec succes", new Command("Ok"));
                new ListRecrutements(res).show();

            }
        });

        addAll(emptySpace1, tfnom, emptySpace3, tfdesc, emptySpace2, tfprix, btnValider);
//        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new Home().showBack());

    }

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
