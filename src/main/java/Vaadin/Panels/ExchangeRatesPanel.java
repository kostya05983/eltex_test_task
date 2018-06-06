package Vaadin.Panels;

import API.HttpExchangeRates;
import API.Rates;
import Vaadin.VaadinUI;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ExchangeRatesPanel extends Panel {

    private final String EXCHANGE_RATES = "exchangeRates";
    private final String USD = "USD: ";
    private final String EUR = "EUR: ";
    private final Logger logger = LogManager.getLogger(ExchangeRatesPanel.class);

    private HttpExchangeRates httpExchangeRates = new HttpExchangeRates();//class for getting Rates

    private VaadinUI context;
    private VerticalLayout verticalLayout;
    private Label labelUSD;
    private Label labelEUR;

    public ExchangeRatesPanel(String caption, VaadinUI context) {
        super(caption);
        logger.debug(new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : Exchange Rates constructor");
        verticalLayout = new VerticalLayout();
        this.context = context;
    }

    public void init() {
        this.setPrimaryStyleName(EXCHANGE_RATES);
        initLabels();
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : labels was initialized");
        initButton();
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : buttons was initialized");
        setContent(verticalLayout);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : verticalLayout was set as Content");

    }

    //method for initLabels
    private void initLabels() {
        //gets Rates
        Rates rates = httpExchangeRates.getRates();
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Rates was gotten");

        //set USD Rate
        labelUSD = new Label(USD + rates.getUSD());
        labelUSD.setPrimaryStyleName(EXCHANGE_RATES + "-labels");
        verticalLayout.addComponent(labelUSD);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : labelUSD was initialized");

        //set EUR Rate
        labelEUR = new Label(EUR + rates.getEUR());
        labelEUR.setPrimaryStyleName(EXCHANGE_RATES + "-labels");
        verticalLayout.addComponent(labelEUR);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : labelEUR was initialized");
    }

    //Method for Custom Button
    private void initButton() {
        Button refresh = new Button();
        refresh.setPrimaryStyleName(EXCHANGE_RATES + "-refresh");
        refresh.setIcon(new FileResource(new File("./src/main/resources/button_refresh.png")));
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : button was created");
        refresh.addClickListener((Button.ClickListener) event -> new Thread(() -> {
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : thread handler started");

            //set progressBar instead of button
            ProgressBar progressBar = new ProgressBar(0.0f);
            progressBar.setStyleName("progressBar-Rates");
            progressBar.setIndeterminate(true);
            verticalLayout.removeComponent(refresh);
            verticalLayout.addComponent(progressBar);
            progressBar.setValue(10.0f);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : add progressBar instead button");

            //getting Rates
            Rates rates = httpExchangeRates.getRates(progressBar);
            if (rates == null) {
                logger.debug(new Object() {
                }.getClass().getEnclosingMethod().getName() + " :  rates was not gotten");
                verticalLayout.removeComponent(progressBar);
                verticalLayout.addComponent(refresh);
                Notification notification = new Notification("Проверьте интернет соединение");
                notification.show(Page.getCurrent());
            }
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : get Rates");

            //set Rates
            assert rates != null;
            labelUSD.setValue(USD + rates.getUSD());
            labelEUR.setValue(EUR + rates.getEUR());
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : refresh labels");

            //refresh date on page
            context.refreshDate();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : refresh Date");
            progressBar.setValue(100.0f);

            //returned button
            verticalLayout.removeComponent(progressBar);
            verticalLayout.addComponent(refresh);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : button was returned instead of progressBar");
        }).start());

        verticalLayout.addComponent(refresh);
    }
}
