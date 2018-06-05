package Vaadin.Panels;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class WeatherPanel extends Panel {
    private final String WEATHER = "weather";
    private VerticalLayout verticalLayout;
    private int currentTempeture;
    private int tommorowTempeture;

    public WeatherPanel(){
        verticalLayout = new VerticalLayout();
    }

    public void init() {
        setPrimaryStyleName(WEATHER);
        setCaption("Погода");

        initComboBox();
    }

    private void initComboBox(){
        List<String> cities = new ArrayList<>();
        cities.add("Новосибирск");
        cities.add("Москва");
        cities.add("Санкт-Петербург");

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(cities);
        verticalLayout.addComponent(comboBox);
    }

}
