package Vaadin;


import Base.Connect;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VaadinUI extends UI{

    private final String DATE_CAPTION = "Информация по состоянию на ";
    private static Label dateLabel;
    private static Label ipLabel;

    @Override
    protected void init(VaadinRequest request) {
        refresh();
        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

        Label label = new Label("Тестовое сетевое приложение");
        verticalLayout.addComponent(label);

        verticalLayout.addComponent(new MainHorizontalLayout(this));

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        dateLabel = new Label();
        refreshDate();
        horizontalLayout.addComponent(dateLabel);

        ipLabel = new Label();
        refreshIp();
        horizontalLayout.addComponent(ipLabel);

        verticalLayout.addComponent(horizontalLayout);

        UI.getCurrent().setPollInterval( 100 );

    }

    public void refreshDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy:HH:mm");
        dateLabel.setValue( DATE_CAPTION + simpleDateFormat.format(date));
    }

    private void refreshIp() {
        WebBrowser webBrowser = Page.getCurrent().getWebBrowser();
        ipLabel.setValue(webBrowser.getAddress());
    }

    private void refresh() {
        Connect connect = new Connect();
        connect.writeOneVisit();
    }

}
