package Vaadin.Panels;

import API.HttpTemperature;
import API.Temperature;
import com.vaadin.ui.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class WeatherPanel extends Panel {
    private final String WEATHER = "weather";
    private final String CURRENT_WEATHER="Температура текущая";
    private final String TOMORROW_WEATHER="Температура на завтра";

    private VerticalLayout verticalLayout;
    private ComboBox<String> comboBox;
    private int currentTempeture;
    private int tommorowTempeture;

    public WeatherPanel(){
        verticalLayout = new VerticalLayout();
    }

    public void init() {
        setPrimaryStyleName(WEATHER);
        setCaption("Погода");

        initComboBox();
        initWeather();

        setContent(verticalLayout);
    }

    private void initComboBox(){
        List<String> cities = new ArrayList<>();
        cities.add("Новосибирск");
        cities.add("Москва");
        cities.add("Санкт-Петербург");

        comboBox = new ComboBox<>();
        comboBox.setItems(cities);
        verticalLayout.addComponent(comboBox);
    }

    private void initWeather(){
        HttpTemperature httpTemperature = new HttpTemperature();
        Temperature temperature = httpTemperature.getTemperature("Новосибирск");

        Label labelCurrent = new Label( CURRENT_WEATHER+temperature.getCurrent());
        verticalLayout.addComponent(labelCurrent);

        Label labelTomorrow = new Label(TOMORROW_WEATHER+temperature.getTomorrow());
        verticalLayout.addComponent(labelTomorrow);

        Button button = new Button("Обновить");
        button.addClickListener((Button.ClickListener) event -> new Thread(() -> {
           Temperature temperature1 = httpTemperature.getTemperature(comboBox.getSelectedItem().get());
          labelCurrent.setValue(CURRENT_WEATHER+ temperature1.getCurrent());
          labelTomorrow.setValue(TOMORROW_WEATHER+ temperature1.getTomorrow());
        })
        );
        verticalLayout.addComponent(button);
    }

}
