package Vaadin;

import Vaadin.Panels.ExchangeRatesPanel;
import Vaadin.Panels.VisistsPanel;
import Vaadin.Panels.WeatherPanel;
import com.vaadin.ui.HorizontalLayout;

public class MainHorizontalLayout extends HorizontalLayout {

    private WeatherPanel weatherPanel;
    private ExchangeRatesPanel exchangeRatesPanel ;
    private VisistsPanel visistsPanel = new VisistsPanel("Количество посетителей");

    public MainHorizontalLayout(VaadinUI context){
        weatherPanel = new WeatherPanel("Погода",context);
        addComponent(weatherPanel);
        weatherPanel.init();

        exchangeRatesPanel = new ExchangeRatesPanel("Курсы валют",context);
        addComponent(exchangeRatesPanel);
        exchangeRatesPanel.init();

        addComponent(visistsPanel);
        visistsPanel.init();
    }

}
