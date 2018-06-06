package Vaadin;

import Vaadin.Panels.ExchangeRatesPanel;
import Vaadin.Panels.VisistsPanel;
import Vaadin.Panels.WeatherPanel;
import com.vaadin.ui.HorizontalLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class MainHorizontalLayout extends HorizontalLayout {

    private final Logger logger = LogManager.getLogger(MainHorizontalLayout.class);
    private WeatherPanel weatherPanel;
    private ExchangeRatesPanel exchangeRatesPanel;
    private VisistsPanel visistsPanel = new VisistsPanel("Количество посетителей");

    MainHorizontalLayout(VaadinUI context) {
        //create and init weather Panel
        weatherPanel = new WeatherPanel("Погода", context);
        logger.debug(new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : create Weather Panel");
        addComponent(weatherPanel);
        weatherPanel.init();
        logger.debug(new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : init WeatherPanel");

        //create and init exchangeRatesPanel
        exchangeRatesPanel = new ExchangeRatesPanel("Курсы валют", context);
        logger.debug(new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : create exchangeRatesPanel");
        addComponent(exchangeRatesPanel);
        exchangeRatesPanel.init();
        logger.debug(new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : init ExchangeRatesPanel");

        //create and init visitsPanel
        addComponent(visistsPanel);
        visistsPanel.init();
        logger.debug(new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : init visits Panel");
    }

}
