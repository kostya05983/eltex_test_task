package Vaadin.Panels;

import API.HttpExchangeRates;
import API.Rates;
import com.vaadin.ui.*;

public class ExchangeRatesPanel extends Panel {

    private final String USD = "USD:";
    private final String EUR = "EUR:";

    private HttpExchangeRates httpExchangeRates = new HttpExchangeRates();

    private VerticalLayout verticalLayout;
    private Label labelUSD;
    private Label labelEUR;

    public ExchangeRatesPanel(String caption){
        super(caption);
        verticalLayout = new VerticalLayout();
    }

    public void init() {
    initLabels();
    initButton();
    setContent(verticalLayout);

    }

    private void initLabels() {
        Rates rates = httpExchangeRates.getRates();
        labelUSD = new Label(USD+rates.getUSD());
        verticalLayout.addComponent(labelUSD);

        labelEUR = new Label(EUR+rates.getEUR());
        verticalLayout.addComponent(labelEUR);
    }

    private void initButton() {
        Button refresh = new Button("Обновить");
        refresh.addClickListener((Button.ClickListener) event -> new Thread(()->{
            ProgressBar progressBar = new ProgressBar(0.0f);
            verticalLayout.removeComponent(refresh);
            verticalLayout.addComponent(progressBar);
            progressBar.setValue(10.0f);

            Rates rates = httpExchangeRates.getRates();
            labelUSD.setValue(USD+rates.getUSD());
            labelEUR.setValue(EUR+rates.getEUR());
            progressBar.setValue(100.0f);

            verticalLayout.removeComponent(progressBar);
            verticalLayout.addComponent(refresh);
        }).start());

        verticalLayout.addComponent(refresh);
    }
}
