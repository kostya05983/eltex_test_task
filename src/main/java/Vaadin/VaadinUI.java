package Vaadin;


import Base.Connect;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VaadinUI extends UI{

    private final String VAADINUI = "vaadinUI";
    private final String DATE_CAPTION = "Информация по состоянию на ";
    private static Label dateLabel;
    private static Label ipLabel;

    @Override
    protected void init(VaadinRequest request) {
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".vaadinUI-head { margin-left: 500px; }");

        refresh();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setPrimaryStyleName(VAADINUI);
        setContent(verticalLayout);

        Label head = new Label("Тестовое сетевое приложение");
        head.setPrimaryStyleName(VAADINUI+"-head");
        verticalLayout.addComponent(head);

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
