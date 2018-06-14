package Vaadin.Panels;

import API.HttpTemperature;
import API.Temperature;
import Vaadin.VaadinUI;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class WeatherPanel extends Panel {
    private final String WEATHER = "weather";
    private final String CURRENT_WEATHER = "Температура текущая :   ";
    private final String TOMORROW_WEATHER = "Температура на завтра : ";
    private final Logger logger = LogManager.getLogger(WeatherPanel.class);

    private VaadinUI context;
    private VerticalLayout verticalLayout;
    private ComboBox<String> comboBox;

    public WeatherPanel(String caption, VaadinUI context) {
        super(caption);
        logger.debug(new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : WeatherPanel Constructor");
        verticalLayout = new VerticalLayout();
        this.context = context;

    }

    public void init() {
        this.setPrimaryStyleName(WEATHER);

        initComboBox();
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : initialized ComboBox end");
        initWeather();
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : initialized Weather");
        setContent(verticalLayout);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : set Content of WeatherPanel");
    }

    //method forcustom ComboBox
    private void initComboBox() {
        List<String> cities = new ArrayList<>();
        cities.add("Новосибирск");
        cities.add("Москва");
        cities.add("Санкт-Петербург");
        cities.add("Красноярск");
        cities.add("Симферополь");
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : initialized list of cities");

        ///init ComboBox
        comboBox = new ComboBox<>();
        //comboBox.setPrimaryStyleName(WEATHER+"-ComboBox");
        comboBox.setStyleName("v-filterselect-input");
        comboBox.setItems(cities);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : set items in combobox");
        verticalLayout.addComponent(comboBox);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : add comboBox");
    }

    //method for Customizing Panel
    private void initWeather() {
        //getting Temperature
        HttpTemperature httpTemperature = new HttpTemperature();
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : httpTemperature created");
        Temperature temperature = httpTemperature.getTemperature("Новосибирск");
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : temperature was gotten");

        //set currentWeather
        Label labelCurrent = new Label(CURRENT_WEATHER + temperature.getCurrent());
        verticalLayout.addComponent(labelCurrent);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : initialized label with CurrentWeather");

        //set TomorrowWeather
        Label labelTomorrow = new Label(TOMORROW_WEATHER + temperature.getTomorrow());
        verticalLayout.addComponent(labelTomorrow);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : initialized label with tomorrow weather");

        Button refresh = new Button();
        refresh.setPrimaryStyleName(WEATHER + "-refresh");
        if(Page.getCurrent().getWebBrowser().getScreenHeight()<800){
            FileResource fileResource = new FileResource(new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                    +"/src/main/resources/images/refresh_32.png"));
            refresh.setIcon(fileResource);
        }


        if(Page.getCurrent().getWebBrowser().getScreenHeight()>800) {
            FileResource fileResource = new FileResource(new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                    +"/src/main/resources/images/refresh_64.png"));
            refresh.setIcon(fileResource);
        }

        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : button was created");
        refresh.addClickListener((Button.ClickListener) event -> new Thread(() -> {
                    logger.debug(new Object() {
                    }.getClass().getEnclosingMethod().getName() + " : new thread handler was started button=Обновить in WeatherPanel");
                    //create ProgressBar and set instead of button
                    ProgressBar progressBar = new ProgressBar();
                    progressBar.setValue(0.0f);
                    progressBar.setStyleName("progressBar-Weather");
                    progressBar.setIndeterminate(true);
                    progressBar.setSizeFull();
                    verticalLayout.removeComponent(refresh);
                    verticalLayout.addComponent(progressBar);
                    logger.debug(new Object() {
                    }.getClass().getEnclosingMethod().getName() + " :  progressBar  was initialized and button was removed ");

                    //getting temperature for selected city
                    Temperature temperatureNew;
                    try {
                        temperatureNew = httpTemperature.getTemperature(comboBox.getSelectedItem().get(), progressBar);
                        if (temperatureNew == null) {
                            logger.debug(new Object() {
                            }.getClass().getEnclosingMethod().getName() + " :  tempereture was not gotten");
                            verticalLayout.removeComponent(progressBar);
                            verticalLayout.addComponent(refresh);
                            context.showNotification("Проверьте интернет соединение");
                        }
                    } catch (NoSuchElementException ex) {
                        logger.debug(new Object() {
                        }.getClass().getEnclosingMethod().getName() + " :  error city was not chosen");
                        verticalLayout.removeComponent(progressBar);
                        verticalLayout.addComponent(refresh);
                        context.showNotification("Выберите город");
                        return;
                    }
                    logger.debug(new Object() {
                    }.getClass().getEnclosingMethod().getName() + " : temperature was gotten");
                    progressBar.setValue(90.0f);

                    //set values
                    assert temperatureNew != null;
                    labelCurrent.setValue(CURRENT_WEATHER + temperatureNew.getCurrent());
                    labelTomorrow.setValue(TOMORROW_WEATHER + temperatureNew.getTomorrow());
                    logger.debug(new Object() {
                    }.getClass().getEnclosingMethod().getName() + " : temperature was set current=" + temperatureNew.getCurrent() + "tomorrow=" + temperatureNew.getTomorrow());

                    //refresh date on Page
                    context.refreshDate();
                    logger.debug(new Object() {
                    }.getClass().getEnclosingMethod().getName() + " : date was refreshed");
                    progressBar.setValue(100.0f);

                    //returned button
                    verticalLayout.removeComponent(progressBar);
                    verticalLayout.addComponent(refresh);
                    logger.debug(new Object() {
                    }.getClass().getEnclosingMethod().getName() + " : button was returned");
                }).start()
        );
        verticalLayout.addComponent(refresh);
    }

}
