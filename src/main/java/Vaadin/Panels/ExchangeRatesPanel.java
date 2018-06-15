package Vaadin.Panels;

import API.HttpExchangeRates;
import API.Rates;
import Vaadin.VaadinUI;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

import java.io.File;

public class ExchangeRatesPanel extends Panel {

    private final String EXCHANGE_RATES = "exchangeRates";
    private final String USD = "USD: ";
    private final String EUR = "EUR: ";
    private final Logger logger = LogManager.getRootLogger();

    private HttpExchangeRates httpExchangeRates;//класс для получения курсов валют

    private VaadinUI context;
    private VerticalLayout verticalLayout;
    private Label labelUSD;
    private Label labelEUR;

    /**
     * @param caption - заголовок блока курсы валют
     * @param context - контекст
     */
    public ExchangeRatesPanel(String caption, VaadinUI context) {
        super(caption);
        logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : Exchange Rates конструктор с параметрами caption = " + caption + " context = " + context);
        verticalLayout = new VerticalLayout();
        this.context = context;
    }

    public void init() {
        this.setPrimaryStyleName(EXCHANGE_RATES);
        httpExchangeRates = new HttpExchangeRates(context);
        initLabels();
        logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Лейблы успешно проинициализированны");
        initButton();
        logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Кнопки успешно проинициализированны");
        setContent(verticalLayout);
    }

    /**
     * метод инициализирующий лейблы
     */
    private void initLabels() {
        //получаем курсы валют
        Rates rates = httpExchangeRates.getRates();
        if (rates == null) {
            labelUSD = new Label(USD);
            labelEUR = new Label(EUR);
            context.showNotification("Не удалось получить курсы валют,проверьте интернет соединение");
            logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
            }.getClass().getEnclosingMethod().getName() + " : Не удалось получить курсы валют, лейблы проинициализированы без значения");
        } else {
            labelUSD = new Label(USD + rates.getUSD());
            labelEUR = new Label(EUR + rates.getEUR());
            logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
            }.getClass().getEnclosingMethod().getName() + " : Курсы валют получены, лейблы проинициализированны со значениями " + rates);
        }

        labelUSD.setPrimaryStyleName(EXCHANGE_RATES + "-labels");
        labelEUR.setPrimaryStyleName(EXCHANGE_RATES + "-labels");

        verticalLayout.addComponent(labelUSD);
        verticalLayout.addComponent(labelEUR);
    }


    /**
     * метод для конфигурирования кнопки
     */
    private void initButton() {
        Button refresh = new Button();
        refresh.setPrimaryStyleName(EXCHANGE_RATES + "-refresh");

        if (Page.getCurrent().getWebBrowser().getScreenHeight() < 800) {
            FileResource fileResource = new FileResource(new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                    + "/src/main/resources/images/refresh_32.png"));
            refresh.setIcon(fileResource);
        }

        if (Page.getCurrent().getWebBrowser().getScreenHeight() > 800) {
            FileResource fileResource = new FileResource(new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                    + "/src/main/resources/images/refresh_64.png"));
            refresh.setIcon(fileResource);
        }

        //Создание нового потока для обработчика
        refresh.addClickListener((Button.ClickListener) event -> new Thread(() -> {
            logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
            }.getClass().getEnclosingMethod().getName() + " : Начинаю новый поток на обработчике кнопки курса валют");

            //заменяем кнопку на прогресс бар
            ProgressBar progressBar = new ProgressBar(0.0f);
            progressBar.setStyleName("progressBar-Rates");
            progressBar.setIndeterminate(true);
            verticalLayout.removeComponent(refresh);
            verticalLayout.addComponent(progressBar);
            progressBar.setValue(10.0f);
            logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
            }.getClass().getEnclosingMethod().getName() + " : кнопка заменена на прогресс бар");

            //получение курса валют
            Rates rates = httpExchangeRates.getRates(progressBar);
            if (rates == null) {
                logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
                }.getClass().getEnclosingMethod().getName() + " : курсы валют не получены");
                labelUSD.setValue(USD);
                labelEUR.setValue(EUR);
                Notification notification = new Notification("Проверьте интернет соединение");
                notification.show(Page.getCurrent());
            } else {
                logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
                }.getClass().getEnclosingMethod().getName() + " : курсы валют успешно получены rates=" + rates);
                labelUSD.setValue(USD + rates.getUSD());
                labelEUR.setValue(EUR + rates.getEUR());
            }

            //обновление времени на странице
            context.refreshDate();
            logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
            }.getClass().getEnclosingMethod().getName() + " : время обновлено");
            progressBar.setValue(100.0f);

            //возвращаем кнопку
            verticalLayout.removeComponent(progressBar);
            verticalLayout.addComponent(refresh);
            logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
            }.getClass().getEnclosingMethod().getName() + " : кнопка возвращена");
        }).start());

        verticalLayout.addComponent(refresh);
    }
}
