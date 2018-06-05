import API.HttpExchangeRates;

public class TestForRates {

    public static void main(String[] args) {
        HttpExchangeRates httpExchangeRates = new HttpExchangeRates();
        httpExchangeRates.getRates(null);
    }
}
