import API.HttpExchangeRates;
import API.Rates;
import CenterBank.Rate;

public class TestForRates {


    public static void main(String[] args) {
        HttpExchangeRates httpExchangeRates = new HttpExchangeRates();

        Rates rates = httpExchangeRates.getRates();

        //if(rates==null)

    }
}
