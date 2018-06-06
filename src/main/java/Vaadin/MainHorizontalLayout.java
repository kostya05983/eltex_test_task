package Vaadin;

import Vaadin.Panels.ExchangeRatesPanel;
import Vaadin.Panels.VisistsPanel;
import Vaadin.Panels.WeatherPanel;
import com.vaadin.ui.HorizontalLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainHorizontalLayout extends HorizontalLayout {

    private final Logger logger = LogManager.getLogger(MainHorizontalLayout.class);
    private WeatherPanel weatherPanel;
    private ExchangeRatesPanel exchangeRatesPanel;
    private VisistsPanel visistsPanel = new VisistsPanel("Количество посетителей");

    public MainHorizontalLayout(VaadinUI context) {
        weatherPanel = new WeatherPanel("Погода", context);
        logger.debug(new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : create Weather Panel");
        addComponent(weatherPanel);
        weatherPanel.init();
        logger.debug(new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : init WeatherPanel");

        exchangeRatesPanel = new ExchangeRatesPanel("Курсы валют", context);
        logger.debug(new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : create exchangeRatesPanel");
        addComponent(exchangeRatesPanel);
        exchangeRatesPanel.init();
        logger.debug(new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : init ExchangeRatesPanel");

        addComponent(visistsPanel);
        visistsPanel.init();
        logger.debug(new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : init visits Panel");
    }

}
