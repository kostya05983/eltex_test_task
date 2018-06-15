package Vaadin;

import API.CustomInputStream;
import Base.Connect;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WebBrowser;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VaadinUI extends UI {

    private static Label dateLabel;
    private static Label ipLabel;
    private final Logger logger = LogManager.getRootLogger();
    private final String VAADINUI = "VAADINUI";//строка для имени класса css
    private final String DATE_CAPTION = "Информация по состоянию на ";
    private final String IP_CAPTION = "Ваш IP : ";
    private final int POLL_INTERVAL = 100;//интервал обновления
    private static long visits;

    @Override
    protected void init(VaadinRequest request) {
        logger.info("new Client");
        //загружаем стили
        loadStyles();

        //обновляем бд
        new Thread(this::refresh).start();
        logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Новый пользователь");

        //создаем главную платформу
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setPrimaryStyleName(VAADINUI);
        setContent(verticalLayout);

        //загружаем шапку сайта
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setPrimaryStyleName(VAADINUI + "-HeadHorizontalLayout");
        Label head = new Label("Тестовое сетевое приложение");
        head.setPrimaryStyleName(VAADINUI + "-head");
        horizontalLayout.addComponent(head);
        verticalLayout.addComponent(horizontalLayout);

        //загружаем середину сайта
        MainHorizontalLayout mainHorizontalLayout = new MainHorizontalLayout(this);
        mainHorizontalLayout.setPrimaryStyleName(VAADINUI + "-mainHorizontalLayout");
        verticalLayout.addComponent(mainHorizontalLayout);

        //Создаем подвал
        HorizontalLayout horizontalLayoutDown = new HorizontalLayout();
        horizontalLayoutDown.setPrimaryStyleName(VAADINUI + "-down");

        //конфигурируем лейбл времени
        dateLabel = new Label();
        refreshDate();
        horizontalLayoutDown.addComponent(dateLabel);

        //конфигурируем лейбл с ip
        ipLabel = new Label();
        ipLabel.setSizeUndefined();
        ipLabel.setPrimaryStyleName(VAADINUI + "-ipLabel");
        refreshIp();
        horizontalLayoutDown.addComponent(ipLabel);

        verticalLayout.addComponent(horizontalLayoutDown);

        //устанавливаем интервал обновления страницы
        UI.getCurrent().setPollInterval(POLL_INTERVAL);
        logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingMethod().getName() + " : интервал обновления равен = " + POLL_INTERVAL);
    }

    /**
     * Метод для загрузки стилей в зависимости от разрешения
     */
    private void loadStyles() {
        try {
            int height = Page.getCurrent().getWebBrowser().getScreenHeight();

            if (height < 800) {
                byte[] file = new CustomInputStream(this.getClass().getResourceAsStream("/style_800.css")).readAllBytes();
                Page.getCurrent().getStyles().add(new String(file));
                logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
                }.getClass().getEnclosingMethod().getName() + ": стили с разрешением по высоте больше 800 загружены");
            }

            if (height > 800) {
                byte[] file = new CustomInputStream(this.getClass().getResourceAsStream("/style.css")).readAllBytes();
                Page.getCurrent().getStyles().add(new String(file));
                logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
                }.getClass().getEnclosingMethod().getName() + ": стили с разрешением по высоте меньше 800 загружены");
            }

        } catch (IOException e) {
            logger.error(MarkerManager.getMarker("SERVER"),e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Метод для обновления даты и времени
     */
    public void refreshDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy:HH:mm");
        dateLabel.setValue(DATE_CAPTION + simpleDateFormat.format(date));
        logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingMethod().getName() + " : получена дата = " + simpleDateFormat.format(date));
    }

    /**
     * Метод для установки ip - адресса
     */
    private void refreshIp() {
        WebBrowser webBrowser = Page.getCurrent().getWebBrowser();
        ipLabel.setValue(IP_CAPTION + webBrowser.getAddress());
        logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingMethod().getName() + " : получен ipAdress = " + webBrowser.getAddress());
    }

    /**
     * Метод который записывает нового пользователя
     */
    private void refresh() {
        try {
            Connect connect = new Connect();
            connect.writeOneVisit();
            visits = connect.getVisits();
        } catch (Exception e) {
            showNotification("Не удалось подключиться к базе данных, обратитесь к администратору");
            logger.error(MarkerManager.getMarker("SERVER"),e.getMessage());
            e.printStackTrace();
            return;
        }
        logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingMethod().getName() + " : writeOneVisit successful");
    }

    /**
     * Метод для оповещений
     *
     * @param description - сообщение
     */
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

    public long getVisits() {
        return visits;
    }


}
