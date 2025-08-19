package main.java.com.alura.coinfluxcraft.service;

import main.java.com.alura.coinfluxcraft.client.ApiClient;
import main.java.com.alura.coinfluxcraft.config.ApiConfig;
import main.java.com.alura.coinfluxcraft.models.Currency;
import main.java.com.alura.coinfluxcraft.models.ExchangeRateResponse;
import main.java.com.alura.coinfluxcraft.models.SupportedCurrency;
import main.java.com.alura.coinfluxcraft.parser.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConversionService {

    public List<Currency> getFilteredCurrencies(String baseCurrency) throws IOException, InterruptedException {
        validateCurrency(baseCurrency);

        String url = ApiConfig.getLatestRatesUrl(baseCurrency);
        String jsonResponse = ApiClient.sendGetRequest(url);
        ExchangeRateResponse response = JsonParser.parseExchangeRateResponse(jsonResponse);

        if (!response.isSuccess()) {
            throw new RuntimeException("Error en la API: " + response.getErrorType());
        }
//        System.out.println(response.getConversionRates());
        return filterSupportedCurrencies(response.getConversionRates());
    }

    public List<Currency> filterSupportedCurrencies(Map<String, Double> allRates) {
        List<Currency> filteredCurrencies = new ArrayList<>();

        for (SupportedCurrency supportedCurrency : SupportedCurrency.values()) {
            String code = supportedCurrency.getCode();
            if (allRates.containsKey(code)) {
                Currency currency = new Currency(
                        code,
                        supportedCurrency.getName(),
                        allRates.get(code)
                );
                filteredCurrencies.add(currency);
            }
        }

        return filteredCurrencies;
    }

    private void validateCurrency(String currency) {
        if (!SupportedCurrency.isSupported(currency)) {
            throw new IllegalArgumentException("Moneda no soportada: " + currency +
                    ". Use: ARS, BOB, BRL, CLP, COP, USD");
        }
    }
}
