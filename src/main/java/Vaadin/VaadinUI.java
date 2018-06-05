package Vaadin;


import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VaadinUI extends UI{



    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

        Label label = new Label("Тестовое сетевое приложение");
        verticalLayout.addComponent(label);

        verticalLayout.addComponent(new MainHorizontalLayout());
        UI.getCurrent().setPollInterval( 100 );

    }
}
