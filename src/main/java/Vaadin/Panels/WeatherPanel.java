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
import org.apache.logging.log4j.MarkerManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class WeatherPanel extends Panel {
    private final String WEATHER = "weather";
    private final String CURRENT_WEATHER = "Температура текущая :   ";
    private final String TOMORROW_WEATHER = "Температура на завтра : ";
    private final Logger logger = LogManager.getRootLogger();

    private VaadinUI context;
    private VerticalLayout verticalLayout;
    private ComboBox<String> comboBox;


    /**
     *
     * @param caption - заголовок блока погоды
     * @param context - контекст
     */
    public WeatherPanel(String caption, VaadinUI context) {
        super(caption);
        logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : конструктор WeatherPanel с параметрами caption = "+caption+" VaadinUI = "+context);
        verticalLayout = new VerticalLayout();
        this.context = context;

    }

    public void init() {
        this.setPrimaryStyleName(WEATHER);
        initComboBox();
        logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
        }.getClass().getEnclosingMethod().getName() + " :  ComboBox проинициализирован");
        initWeather();
        logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Погода проинициализирована");
        setContent(verticalLayout);
    }

    /**
     * Метод инициализирующий comboBox
     */
    private void initComboBox() {
        List<String> cities = new ArrayList<>();
        cities.add("Новосибирск");
        cities.add("Москва");
        cities.add("Санкт-Петербург");
        cities.add("Красноярск");
        cities.add("Симферополь");

        //инициализируем ComboBox
        comboBox = new ComboBox<>();
        comboBox.setStyleName("v-filterselect-input");
        comboBox.setItems(cities);
        comboBox.setSelectedItem("Новосибирск");
        verticalLayout.addComponent(comboBox);
    }

    /**
     * Метод для конфигурирования панели
     */
    private void initWeather() {
        //получаем температуру
        HttpTemperature httpTemperature = new HttpTemperature(context);
        Temperature temperature = httpTemperature.getTemperature("Новосибирск");
        if(temperature == null){
            context.showNotification("Не удалось получить температуру,проверьте интернет соединение");
            logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
            }.getClass().getEnclosingMethod().getName() + " : не удалось получить температуру");
        }else {
            logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
            }.getClass().getEnclosingMethod().getName() + " : температура получена " + temperature);
        }

        //устанавливаем текущую погоду
        Label labelCurrent;
        Label labelTomorrow;
        if(temperature!=null) {
            labelCurrent = new Label(CURRENT_WEATHER + temperature.getCurrent());
            labelTomorrow = new Label(TOMORROW_WEATHER + temperature.getTomorrow());
            logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
            }.getClass().getEnclosingMethod().getName() + " : Лейблы проинициализированы с погодой "+temperature);
        }
        else {
            labelCurrent = new Label(CURRENT_WEATHER);
            labelTomorrow = new Label(TOMORROW_WEATHER);
            logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
            }.getClass().getEnclosingMethod().getName() + " : Лейблы проинициализированы без значения");
        }
        verticalLayout.addComponent(labelCurrent);
        verticalLayout.addComponent(labelTomorrow);

        //инициализация ресурсов для кнопок
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

        refresh.addClickListener((Button.ClickListener) event -> new Thread(() -> {
                    logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
                    }.getClass().getEnclosingMethod().getName() + " : Начинаю новый поток на обработчике кнопки погоды");
                    //заменяем кнопку на прогресс бар
                    ProgressBar progressBar = new ProgressBar();
                    progressBar.setValue(0.0f);
                    progressBar.setStyleName("progressBar-Weather");
                    progressBar.setIndeterminate(true);
                    progressBar.setSizeFull();
                    verticalLayout.removeComponent(refresh);
                    verticalLayout.addComponent(progressBar);

                    //получаем температуру для выбранного города
                    Temperature temperatureNew = null;
                    try {
                        if(comboBox.getSelectedItem().isPresent())
                        temperatureNew = httpTemperature.getTemperature(comboBox.getSelectedItem().get(), progressBar);
                        if (temperatureNew == null) {
                            labelCurrent.setValue(CURRENT_WEATHER);
                            labelTomorrow.setValue(TOMORROW_WEATHER);
                            logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
                            }.getClass().getEnclosingMethod().getName() + " :  не удалось получить температуру");
                            context.showNotification("Не удалось получить температуру,проверьте интернет соединение");
                        }else {
                            labelCurrent.setValue(CURRENT_WEATHER + temperatureNew.getCurrent());
                            labelTomorrow.setValue(TOMORROW_WEATHER + temperatureNew.getTomorrow());
                            logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
                            }.getClass().getEnclosingMethod().getName() + " : температура получена"+temperature);
                        }
                    } catch (NoSuchElementException ex) {
                        verticalLayout.removeComponent(progressBar);
                        verticalLayout.addComponent(refresh);
                        context.showNotification("Выберите город");
                        return;
                    }
                    progressBar.setValue(90.0f);

                    //обновление времени на странице
                    context.refreshDate();
                    logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
                    }.getClass().getEnclosingMethod().getName() + " : время обновлено");
                    progressBar.setValue(100.0f);

                    //возвращаем кнопку
                    verticalLayout.removeComponent(progressBar);
                    verticalLayout.addComponent(refresh);
                }).start()
        );
        verticalLayout.addComponent(refresh);
    }

}
