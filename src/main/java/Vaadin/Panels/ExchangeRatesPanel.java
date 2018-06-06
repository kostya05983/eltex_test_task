package Vaadin.Panels;

import API.HttpExchangeRates;
import API.Rates;
import Vaadin.VaadinUI;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ExchangeRatesPanel extends Panel {

    private final String EXCHANGE_RATES = "exchangeRates";
    private final String USD = "USD: ";
    private final String EUR = "EUR: ";
    private final Logger logger = LogManager.getLogger(ExchangeRatesPanel.class);

    private HttpExchangeRates httpExchangeRates = new HttpExchangeRates();

    private VaadinUI context;
    private VerticalLayout verticalLayout;
    private Label labelUSD;
    private Label labelEUR;

    public ExchangeRatesPanel(String caption, VaadinUI context){
        super(caption);
        logger.debug(new Object(){}.getClass().getEnclosingConstructor().getName()+" : Exchange Rates constructor");
        verticalLayout = new VerticalLayout();
        this.context=context;
    }

    public void init() {
        this.setPrimaryStyleName(EXCHANGE_RATES);
    initLabels();
    logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : labels was initialized");
    initButton();
    logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : buttons was initialized");
    setContent(verticalLayout);
    logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : verticalLayout was set as Content");

    }

    private void initLabels() {
        Rates rates = httpExchangeRates.getRates();
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : Rates was gotten");

        labelUSD = new Label(USD+rates.getUSD());
        labelUSD.setPrimaryStyleName(EXCHANGE_RATES+"-labels");
        verticalLayout.addComponent(labelUSD);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : labelUSD was initialized");

        labelEUR = new Label(EUR+rates.getEUR());
        labelEUR.setPrimaryStyleName(EXCHANGE_RATES+"-labels");
        verticalLayout.addComponent(labelEUR);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : labelEUR was initialized");
    }

    private void initButton() {
        Button refresh = new Button();
        refresh.setPrimaryStyleName(EXCHANGE_RATES+"-refresh");
        refresh.setIcon(new FileResource(new File("./src/main/resources/refreshNew.png")));
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : button was created");
        refresh.addClickListener((Button.ClickListener) event -> new Thread(()->{
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : thread handler started");

            ProgressBar progressBar = new ProgressBar(0.0f);
            progressBar.setPrimaryStyleName("progressBar");
            verticalLayout.removeComponent(refresh);
            verticalLayout.addComponent(progressBar);
            progressBar.setValue(10.0f);
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : add progressBar instead button");

            Rates rates = httpExchangeRates.getRates();
            if (rates == null) {
                logger.debug(new Object() {
                }.getClass().getEnclosingMethod().getName() + " :  rates was not gotten");
                verticalLayout.removeComponent(progressBar);
                verticalLayout.addComponent(refresh);
            }
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : get Rates");

            labelUSD.setValue(USD+rates.getUSD());
            labelEUR.setValue(EUR+rates.getEUR());
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : refresh labels");

            context.refreshDate();
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : refresh Date");
            progressBar.setValue(100.0f);

            verticalLayout.removeComponent(progressBar);
            verticalLayout.addComponent(refresh);
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : button was returned instead of progressBar");
        }).start());

        verticalLayout.addComponent(refresh);
    }
}
