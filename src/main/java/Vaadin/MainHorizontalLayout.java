package Vaadin;

import Vaadin.Panels.ExchangeRatesPanel;
import Vaadin.Panels.VisistsPanel;
import Vaadin.Panels.WeatherPanel;
import com.vaadin.ui.HorizontalLayout;

public class MainHorizontalLayout extends HorizontalLayout {

    private WeatherPanel weatherPanel = new WeatherPanel("Погода");
    private ExchangeRatesPanel exchangeRatesPanel = new ExchangeRatesPanel("Курсы валют");
    private VisistsPanel visistsPanel = new VisistsPanel("Количество посетителей");

    public MainHorizontalLayout(){
        addComponent(weatherPanel);
        weatherPanel.init();

        addComponent(exchangeRatesPanel);
        exchangeRatesPanel.init();

        addComponent(visistsPanel);
        visistsPanel.init();
    }

}
