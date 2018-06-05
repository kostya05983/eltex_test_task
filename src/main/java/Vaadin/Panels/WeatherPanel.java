package Vaadin.Panels;

import API.HttpTemperature;
import API.Temperature;
import Vaadin.VaadinUI;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class WeatherPanel extends Panel {
    private final String WEATHER = "weather";
    private final String CURRENT_WEATHER = "Температура текущая: ";
    private final String TOMORROW_WEATHER = "Температура на завтра: ";

    private VaadinUI context;
    private VerticalLayout verticalLayout;
    private ComboBox<String> comboBox;

    public WeatherPanel(String caption, VaadinUI context) {
        super(caption);
        verticalLayout = new VerticalLayout();
        this.context = context;
    }

    public void init() {

        initComboBox();
        initWeather();

        setContent(verticalLayout);
    }

    private void initComboBox() {
        List<String> cities = new ArrayList<>();
        cities.add("Новосибирск");
        cities.add("Москва");
        cities.add("Санкт-Петербург");

        comboBox = new ComboBox<>();
        comboBox.setItems(cities);
        verticalLayout.addComponent(comboBox);
    }

    private void initWeather() {
        HttpTemperature httpTemperature = new HttpTemperature();
        Temperature temperature = httpTemperature.getTemperature("Новосибирск");

        Label labelCurrent = new Label(CURRENT_WEATHER + temperature.getCurrent());
        verticalLayout.addComponent(labelCurrent);

        Label labelTomorrow = new Label(TOMORROW_WEATHER + temperature.getTomorrow());
        verticalLayout.addComponent(labelTomorrow);

        Button button = new Button("Обновить");
        button.addClickListener((Button.ClickListener) event -> new Thread(() -> {
                    ProgressBar progressBar = new ProgressBar();
                    progressBar.setValue(0.0f);
                    verticalLayout.removeComponent(button);
                    verticalLayout.addComponent(progressBar);

                    Temperature temperature1 = httpTemperature.getTemperature(comboBox.getSelectedItem().get(), progressBar);
                    progressBar.setValue(90.0f);

                    labelCurrent.setValue(CURRENT_WEATHER + temperature1.getCurrent());
                    labelTomorrow.setValue(TOMORROW_WEATHER + temperature1.getTomorrow());

                    context.refreshDate();
                    progressBar.setValue(100.0f);

                    verticalLayout.removeComponent(progressBar);
                    verticalLayout.addComponent(button);
                }).start()
        );
        verticalLayout.addComponent(button);
    }

}
