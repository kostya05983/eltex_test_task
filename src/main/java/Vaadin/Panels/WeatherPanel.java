package Vaadin.Panels;

import API.HttpTemperature;
import API.Temperature;
import Vaadin.VaadinUI;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class WeatherPanel extends Panel {
    private final String WEATHER = "weather";
    private final String CURRENT_WEATHER = "Температура текущая: ";
    private final String TOMORROW_WEATHER = "Температура на завтра: ";
    private final Logger logger = LogManager.getLogger(WeatherPanel.class);

    private VaadinUI context;
    private VerticalLayout verticalLayout;
    private ComboBox<String> comboBox;

    public WeatherPanel(String caption, VaadinUI context) {
        super(caption);
        logger.debug(new Object(){}.getClass().getEnclosingConstructor().getName()+" : WeatherPanel Constructor");
        verticalLayout = new VerticalLayout();
        this.context = context;

    }

    public void init() {
        initComboBox();
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : initialized ComboBox end");
        initWeather();
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : initialized Weather");
        setContent(verticalLayout);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : set Content of WeatherPanel");
    }

    private void initComboBox() {
        List<String> cities = new ArrayList<>();
        cities.add("Новосибирск");
        cities.add("Москва");
        cities.add("Санкт-Петербург");
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : initialized list of cities");

        comboBox = new ComboBox<>();
        comboBox.setItems(cities);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : set items in combobox");
        verticalLayout.addComponent(comboBox);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : add comboBox");
    }

    private void initWeather() {
        HttpTemperature httpTemperature = new HttpTemperature();
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : httpTemperature created");
        Temperature temperature = httpTemperature.getTemperature("Новосибирск");
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : temperature was gotten");

        Label labelCurrent = new Label(CURRENT_WEATHER + temperature.getCurrent());
        verticalLayout.addComponent(labelCurrent);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : initialized label with CurrentWeather");

        Label labelTomorrow = new Label(TOMORROW_WEATHER + temperature.getTomorrow());
        verticalLayout.addComponent(labelTomorrow);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : initialized label with tomorrow weather");

        Button button = new Button("Обновить");
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : button was created");
        button.addClickListener((Button.ClickListener) event -> new Thread(() -> {
                    logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : new thread handler was started button=Обновить in WeatherPanel");
                    ProgressBar progressBar = new ProgressBar();
                    progressBar.setValue(0.0f);
                    verticalLayout.removeComponent(button);
                    verticalLayout.addComponent(progressBar);
                    logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" :  progressBar  was initialized and button was removed ");

                    Temperature temperatureNew = httpTemperature.getTemperature(comboBox.getSelectedItem().get(), progressBar);
                    logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : temperature was gotten");
                    progressBar.setValue(90.0f);

                    labelCurrent.setValue(CURRENT_WEATHER + temperatureNew.getCurrent());
                    labelTomorrow.setValue(TOMORROW_WEATHER + temperatureNew.getTomorrow());
                    logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : temperature was set current="+temperatureNew.getCurrent()+"tomorrow="+temperatureNew.getTomorrow());

                    context.refreshDate();
                    logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : date was refreshed");
                    progressBar.setValue(100.0f);

                    verticalLayout.removeComponent(progressBar);
                    verticalLayout.addComponent(button);
                    logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : button was returned");
                }).start()
        );
        verticalLayout.addComponent(button);
    }

}
