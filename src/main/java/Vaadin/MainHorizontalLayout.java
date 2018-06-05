package Vaadin;

import Vaadin.Panels.WeatherPanel;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;

public class MainHorizontalLayout extends HorizontalLayout {
    private WeatherPanel weatherPanel = new WeatherPanel("Погода");



    public MainHorizontalLayout(){
        addComponent(weatherPanel);
        weatherPanel.init();
    }

}
