package tn.esprit.AppMobile;

import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import tn.esprit.AppMobile.Entities.User;
import tn.esprit.AppMobile.Services.UserService;

/**
 * The Login form
 *
 * @author Shai Almog
 */
public class PasswordUpdate extends Form {

    public PasswordUpdate(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));

         Toolbar toolbar = new Toolbar();
        setToolbar(toolbar);
        toolbar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, x -> new ProfileForm(theme).show());
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Update Password, ", "WelcomeWhite")
        //new Label("Jennifer", "WelcomeBlue")
        );

        getTitleArea().setUIID("Container");

        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField confirm_password = new TextField("", "Confirm¨Password", 20, TextField.PASSWORD);
 
        password.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);

        Button Update = new Button("Update");
      //  Update.setUIID("LoginButton");
      getTitleArea().setUIID("Container");
        try {
            setBgImage(EncodedImage.create("/pass.jpg"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        Update.addActionListener(e -> {
            //  new LoginForm(theme).show();
            if (password.getText().length() == 0 || confirm_password.getText().length() == 0) {
                Dialog.show("Error", "all fields are required", "OK", null);
            } else {
                

                if(! confirm_password.getText().equals(password.getText()))
                {
                    Dialog.show("Error", "Passwords Doesnt match", "OK", null);
                }
                else
                {
                if (UserService.getInstance().modifierpassword(password.getText(),SessionManager.getIdUser())) {
                    Dialog.show("Success", "Password updated", "OK", null);
                    new ProfileForm(theme).show();
                    

                } else {
                    Dialog.show("Error", "Request Error", "OK", null);
                } 
                }
              

            }

        });

      

        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if (!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }

        Container by = BoxLayout.encloseY(
                welcome,
                spaceLabel,
              password,
              confirm_password,
                Update
        );
        add(BorderLayout.CENTER, by);

        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
