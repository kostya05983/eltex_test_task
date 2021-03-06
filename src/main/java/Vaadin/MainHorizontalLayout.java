package Vaadin;

import Vaadin.Panels.ExchangeRatesPanel;
import Vaadin.Panels.VisitsPanel;
import Vaadin.Panels.WeatherPanel;
import com.vaadin.ui.HorizontalLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

class MainHorizontalLayout extends HorizontalLayout {

    /**
     * Конструктор создает все панели
     *
     * @param context - контекст
     */
    MainHorizontalLayout(VaadinUI context) {
        //конфигурируем weather Panel
        Logger logger = LogManager.getRootLogger();
        WeatherPanel weatherPanel = new WeatherPanel("Погода", context);
        weatherPanel.init();
        logger.debug(MarkerManager.getMarker("SERVER"), "WeatherPanel проинициализирована");
        addComponent(weatherPanel);

        //конфигурируем exchangeRatesPanel
        ExchangeRatesPanel exchangeRatesPanel = new ExchangeRatesPanel("Курсы валют", context);
        exchangeRatesPanel.init();
        logger.debug(MarkerManager.getMarker("SERVER"), "ExchangeRatesPanel проинициализирована");
        addComponent(exchangeRatesPanel);

        //конфигурируем visitsPanel
        VisitsPanel visitsPanel = new VisitsPanel("Количество посетителей", context);
        visitsPanel.init();
        logger.debug(MarkerManager.getMarker("SERVER"), "visits Panel проинициализирована");
        addComponent(visitsPanel);
    }

}
