package Vaadin;


import Base.Connect;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;


public class VaadinUI extends UI{

    private final Logger logger = LogManager.getLogger(VaadinUI.class);
    private final String VAADINUI = "vaadinUI";
    private final String DATE_CAPTION = "Информация по состоянию на ";
    private final int POLL_INTERVAL = 100;
    private static Label dateLabel;
    private static Label ipLabel;



    @Override
    protected void init(VaadinRequest request) {
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".vaadinUI-head { margin-left: 500px; }");

        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : new user refresh database");
        refresh();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setPrimaryStyleName(VAADINUI);
        setContent(verticalLayout);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : create main verticalLayout");

        Label head = new Label("Тестовое сетевое приложение");
        head.setPrimaryStyleName(VAADINUI+"-head");
        verticalLayout.addComponent(head);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : create head text");

        verticalLayout.addComponent(new MainHorizontalLayout(this));
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : created MainHorizontalLayout");

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        dateLabel = new Label();
        refreshDate();
        horizontalLayout.addComponent(dateLabel);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : init dateLabel");

        ipLabel = new Label();
        refreshIp();
        horizontalLayout.addComponent(ipLabel);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : init ipLabel");

        verticalLayout.addComponent(horizontalLayout);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : add down horizontalLayout");

        UI.getCurrent().setPollInterval( POLL_INTERVAL );
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : set PollInterval="+POLL_INTERVAL);
    }

    public void refreshDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy:HH:mm");
        dateLabel.setValue( DATE_CAPTION + simpleDateFormat.format(date));
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : get date " +simpleDateFormat.format(date));
    }

    private void refreshIp() {
        WebBrowser webBrowser = Page.getCurrent().getWebBrowser();
        ipLabel.setValue(webBrowser.getAddress());
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : get ipAdress " + webBrowser.getAddress());
    }

    private void refresh() {
        Connect connect = new Connect();
        connect.writeOneVisit();
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : writeOneVisit successful");
    }

}
