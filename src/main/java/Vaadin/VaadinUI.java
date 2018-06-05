package Vaadin;


import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

public class VaadinUI extends UI{

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

        Label label = new Label("Тестовое сетевое приложение");



        verticalLayout.addComponent(new MainHorizontalLayout());

    }
}
