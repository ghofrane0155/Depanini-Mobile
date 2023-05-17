/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile.GUI.Events;

import com.barcodelib.barcode.Linear;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;

import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.AppMobile.Entities.Events;
import tn.esprit.AppMobile.Entities.Tickets;
import tn.esprit.AppMobile.Services.TicketsService;
import tn.esprit.AppMobile.SessionManager;
import tn.esprit.AppMobile.SideMenuBaseForm;

/**
 *
 * @author noure
 */
public class CreateTicket extends SideMenuBaseForm {
    
    private Events event;
    
    public CreateTicket(Resources res, Events event) {
     /////////////////////////////////////////////////  
        super(BoxLayout.y());     
        setupSideMenu(res);
     ////////////////////////////////////////////////
        
    this.event = event;
setTitle("Participate in " + event.getNomevent());

TextField quantiteField = new TextField("", "Quantity");
quantiteField.getAllStyles().setAlignment(Component.CENTER);
quantiteField.getHintLabel().setUIID("HomeTextFieldHint");
quantiteField.getHintLabel().getAllStyles().setFgColor(0x666666);
quantiteField.getHintLabel().getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
quantiteField.getUnselectedStyle().setFgColor(0x000000);

TextField loginField = new TextField("", "Login");
loginField.getAllStyles().setAlignment(Component.CENTER);
loginField.getHintLabel().setUIID("HomeTextFieldHint");
loginField.getHintLabel().getAllStyles().setFgColor(0x666666);
loginField.getHintLabel().getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
loginField.getUnselectedStyle().setFgColor(0x000000);

Button submitButton = new Button("Submit");
submitButton.getAllStyles().setAlignment(Component.CENTER);
submitButton.getAllStyles().setFgColor(0x333333);
submitButton.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

    submitButton.addActionListener((ActionEvent e) -> {
        TicketsService ticketService = new TicketsService();
        try {
            int quantity = Integer.parseInt(quantiteField.getText());
            double totalPrice = quantity * event.getPrixevent();
            Tickets ticket = new Tickets();
            ticket.setIdUser(SessionManager.getIdUser());
            ticket.setLogin(SessionManager.getLogin());
            ticket.setQuantite(quantity);
            ticket.setPrixtotale(totalPrice);
            double idt=event.getIdevent();
            ticketService.createTicket(ticket,(int)idt);

                        String s =String.valueOf(ticket.getPrixtotale());
                        String nom = ticket.getLogin();//data i want to stock in my  barcode
                        String codename ="Login:"+nom+" price :"+s;
                        
                        
                        Linear barcode = new Linear();
                        barcode.setType(Linear.CODE128B);
                         barcode.setData(codename); //data inside barcode
                         barcode.setI(11.0f);
                         
                        barcode.renderBarcode("../" + nom +".png");

            
            Dialog.show("Success", "welcome "+ticket.getLogin()+" to "+event.getNomevent()+"!! \n your Ticket is added to the basket !", "OK", null);
          new TicketsOfThisUser(res).show();
        } catch (NumberFormatException ex) {
            Dialog.show("Error", "Quantity must be a number", "OK", null);
        } catch (Exception ex) {
            Dialog.show("Error", ex.getMessage(), "OK", null);
        }
    });
    
    Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
content.add(quantiteField);
content.add(loginField);
content.add(submitButton);

Container wrapper = new Container(new FlowLayout(Component.CENTER));
wrapper.add(content);

this.add(wrapper);
show();
}

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
