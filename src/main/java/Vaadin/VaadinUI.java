package Vaadin;

import API.CustomInputStreamInterface;
import Base.Connect;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WebBrowser;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class VaadinUI extends UI implements CustomInputStreamInterface {

    private static Label dateLabel;
    private static Label ipLabel;
    private final Logger logger = LogManager.getLogger(VaadinUI.class);
    private final String VAADINUI = "VAADINUI";//string for css
    private final String DATE_CAPTION = "Информация по состоянию на ";
    private final String IP_CAPTION = "Ваш IP : ";
    private final int POLL_INTERVAL = 100;//interval of Update page ms

    @Override
    protected void init(VaadinRequest request) {
        logger.info("new Client");
        //load styles
        try {
            int height = Page.getCurrent().getWebBrowser().getScreenHeight();

            if (height < 800) {
                byte[] file = readAllBytes(this.getClass().getResourceAsStream("/style_800.css"));
                Page.getCurrent().getStyles().add(new String(file));
                logger.debug(new Object() {
                }.getClass().getEnclosingMethod().getName() + ":styles was loaded");
            }

            if (height > 800) {
                byte[] file = readAllBytes(this.getClass().getResourceAsStream("/style.css"));
                Page.getCurrent().getStyles().add(new String(file));
                logger.debug(new Object() {
                }.getClass().getEnclosingMethod().getName() + ":styles was loaded");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : new user refresh database");
        refresh();

        //create Main vertical layout
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setPrimaryStyleName(VAADINUI);
        setContent(verticalLayout);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : create main verticalLayout");

        //create the head of site
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setPrimaryStyleName(VAADINUI + "-HeadHorizontalLayout");
        Label head = new Label("Тестовое сетевое приложение");
        head.setPrimaryStyleName(VAADINUI + "-head");
        horizontalLayout.addComponent(head);
        verticalLayout.addComponent(horizontalLayout);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : create head text");

        //create the middle layout
        MainHorizontalLayout mainHorizontalLayout = new MainHorizontalLayout(this);
        mainHorizontalLayout.setPrimaryStyleName(VAADINUI + "-mainHorizontalLayout");
        verticalLayout.addComponent(mainHorizontalLayout);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : created MainHorizontalLayout");

        HorizontalLayout horizontalLayoutDown = new HorizontalLayout();
        horizontalLayoutDown.setPrimaryStyleName(VAADINUI + "-down");

        //create date Label and init it
        dateLabel = new Label();
        refreshDate();
        horizontalLayoutDown.addComponent(dateLabel);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : init dateLabel");

        //create ip label and init it
        ipLabel = new Label();
        ipLabel.setSizeUndefined();
        ipLabel.setPrimaryStyleName(VAADINUI + "-ipLabel");
        refreshIp();
        horizontalLayoutDown.addComponent(ipLabel);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : init ipLabel");

        verticalLayout.addComponent(horizontalLayoutDown);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : add down horizontalLayout");

        //set refresh of Page
        UI.getCurrent().setPollInterval(POLL_INTERVAL);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : set PollInterval=" + POLL_INTERVAL);
    }

    public void refreshDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy:HH:mm");
        dateLabel.setValue(DATE_CAPTION + simpleDateFormat.format(date));
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : get date " + simpleDateFormat.format(date));
    }

    private void refreshIp() {
        WebBrowser webBrowser = Page.getCurrent().getWebBrowser();
        ipLabel.setValue(IP_CAPTION + webBrowser.getAddress());
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : get ipAdress " + webBrowser.getAddress());
    }

    //this method adds one user to base
    private void refresh() {
        Connect connect = new Connect();
        connect.writeOneVisit();
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : writeOneVisit successful");
    }

    //method for notification
    public void showNotification(String description) {
        UI.setCurrent(this);
        Notification notification = new Notification(
                "Ошибка:", description,
                Notification.Type.ERROR_MESSAGE);
        notification.setPosition(Position.MIDDLE_CENTER);
        notification.setDelayMsec(5000);
        notification.setStyleName("Notification");
        notification.show(UI.getCurrent().getPage());
    }

    public byte[] readAllBytes(InputStream in) throws IOException {
        byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
        int capacity = buf.length;
        int nread = 0;
        int n;
        for (;;) {
            // read to EOF which may read more or less than initial buffer size
            while ((n = in.read(buf, nread, capacity - nread)) > 0)
                nread += n;

            // if the last call to read returned -1, then we're done
            if (n < 0)
                break;

            // need to allocate a larger buffer
            if (capacity <= MAX_BUFFER_SIZE - capacity) {
                capacity = capacity << 1;
            } else {
                if (capacity == MAX_BUFFER_SIZE)
                    throw new OutOfMemoryError("Required array size too large");
                capacity = MAX_BUFFER_SIZE;
            }
            buf = Arrays.copyOf(buf, capacity);
        }
        return (capacity == nread) ? buf : Arrays.copyOf(buf, nread);
    }


}
