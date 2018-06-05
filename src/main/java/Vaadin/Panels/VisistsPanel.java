package Vaadin.Panels;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

public class VisistsPanel extends Panel {

    private VerticalLayout verticalLayout;



    public VisistsPanel(String caption) {
        super(caption);
        verticalLayout = new VerticalLayout();
    }

    public void init() {
        Label label = new Label();
        verticalLayout.addComponent(label);
    }
}
