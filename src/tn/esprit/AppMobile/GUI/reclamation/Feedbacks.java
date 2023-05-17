package tn.esprit.AppMobile.GUI.reclamation;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * A class for updating feedback using star rating inputs.
 *
 */
public class Feedbacks extends Form {

    private final Container container;
    private final Form form;

    /**
     * Creates a new instance of UpdateFeedback.
     *
     * @param res The application's resources
     */
    public Feedbacks(Resources res) {
        Image unselectedStarIcon = res.getImage("unselectedStar.png");
        Image selectedStarIcon = res.getImage("selectedStar.png");

        container = new Container();
        container.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        for (int iter = 0; iter < 5; iter++) {
            createStarButton(container, unselectedStarIcon, selectedStarIcon);
        }

        form = new Form("Update Feedback");
        form.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        // Add a label
        form.addComponent(new Label("Please rate our service:"));

        // Add the star rating container
        form.addComponent(container);

        // Add a text field for comments
        final SpanLabel comments = new SpanLabel("");
        comments.setTextBlockAlign(Component.LEFT);
        form.addComponent(comments);
        
        
        
        form.addComponent(new TextFieldWithLabel("Enter your comments here...", "Comments", new TextModeLayout(20, 200)));

        // Add a submit button
        Button submit = new Button("Submit");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                Dialog.show("Thank You!", "Your feedback has been submitted.", "OK", null);
            }
        });
        form.addComponent(submit);

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, form);
        this.setBackCommand(new Command("Back") {
            public void actionPerformed(ActionEvent ev) {
                new Feedbacks(res).show();
            }
        });
    }

    private void createStarButton(final Container parent, final Image unselectedStarIcon, final Image selectedStarIcon) {
        final CheckBox cb = new CheckBox();
        cb.setToggle(true);
        cb.setIcon(unselectedStarIcon);
        cb.setPressedIcon(selectedStarIcon);
        cb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (cb.isSelected()) {
                    boolean selected = true;
                    for (int iter = 0; iter < parent.getComponentCount(); iter++) {
                        Component current = parent.getComponentAt(iter);
                        if (current == cb) {
                            selected = false;
                            continue;
                        }
                        ((CheckBox) current).setSelected(selected);
                    }
                }
            }
        });
        parent.addComponent(cb);
    }

    private class TextFieldWithLabel extends Container {

        public TextFieldWithLabel(String text, String label, Layout layout) {
            setLayout(layout);
            add(new Label(label));
            add(new com.codename1.ui.TextField(text));
        }

    }

}
