package Vaadin;


import Base.Connect;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VaadinUI extends UI{

    private final Logger logger = LogManager.getLogger(VaadinUI.class);
    private final String VAADINUI = "VAADINUI";
    private final String DATE_CAPTION = "Информация по состоянию на ";
    private final int POLL_INTERVAL = 100;
    private static Label dateLabel;
    private static Label ipLabel;



    @Override
    protected void init(VaadinRequest request) {
        System.out.println(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath());
        try {
            FileInputStream fileInputStream = new FileInputStream("./src/main/resources/style.css");
            byte[] file = fileInputStream.readAllBytes();
            Page.getCurrent().getStyles().add(new String(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : new user refresh database");
        refresh();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setPrimaryStyleName(VAADINUI);

        setContent(verticalLayout);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : create main verticalLayout");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setPrimaryStyleName(VAADINUI+"-HeadHorizontalLayout");
        Label head = new Label("Тестовое сетевое приложение");
        head.setPrimaryStyleName(VAADINUI+"-head");
        horizontalLayout.addComponent(head);
        verticalLayout.addComponent(horizontalLayout);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : create head text");

        MainHorizontalLayout mainHorizontalLayout = new MainHorizontalLayout(this);
        mainHorizontalLayout.setPrimaryStyleName(VAADINUI+"-mainHorizontalLayout");
        verticalLayout.addComponent(mainHorizontalLayout);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : created MainHorizontalLayout");

        HorizontalLayout horizontalLayoutDown = new HorizontalLayout();
        horizontalLayoutDown.setPrimaryStyleName(VAADINUI+"-down");

        dateLabel = new Label();
        refreshDate();
        horizontalLayoutDown.addComponent(dateLabel);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : init dateLabel");

        ipLabel = new Label();
        ipLabel.setPrimaryStyleName(VAADINUI+"-ipLabel");
        refreshIp();
        horizontalLayoutDown.addComponent(ipLabel);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : init ipLabel");

        verticalLayout.addComponent(horizontalLayoutDown);
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
