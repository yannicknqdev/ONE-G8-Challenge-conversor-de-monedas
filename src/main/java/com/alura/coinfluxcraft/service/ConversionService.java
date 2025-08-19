package main.java.com.alura.coinfluxcraft.service;

import main.java.com.alura.coinfluxcraft.client.ApiClient;
import main.java.com.alura.coinfluxcraft.config.ApiConfig;
import main.java.com.alura.coinfluxcraft.models.Currency;
import main.java.com.alura.coinfluxcraft.models.ExchangeRateResponse;
import main.java.com.alura.coinfluxcraft.models.ConversionResult;
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

    public ConversionResult performCurrencyConversion(String fromCurrency, String toCurrency, double amount)
            throws IOException, InterruptedException {

        validateCurrencyConversion(fromCurrency, toCurrency, amount);

        String url = ApiConfig.getPairConversionWithAmountUrl(fromCurrency, toCurrency, amount);
        String jsonResponse = ApiClient.sendGetRequest(url);
        ConversionResult result = JsonParser.parseConversionResult(jsonResponse);

        if (!result.isSuccess()) {
            throw new RuntimeException("Error en la conversión: " + result.getErrorType());
        }

        validateConversionResult(result);
        return result;
    }

    public double calculateDirectConversion(double amount, double exchangeRate) {
        return CurrencyCalculator.calculateConversion(amount, exchangeRate);
    }

    public double calculateInverseConversion(double amount, double exchangeRate) {
        return CurrencyCalculator.calculateInverseConversion(amount, exchangeRate);
    }

    public ConversionResult convertWithCustomCalculation(String fromCurrency, String toCurrency, double amount)
            throws IOException, InterruptedException {

        validateCurrencyConversion(fromCurrency, toCurrency, amount);

        if (fromCurrency.equals(toCurrency)) {
            return createSameCurrencyResult(fromCurrency, amount);
        }

        double exchangeRate = getExchangeRate(fromCurrency, toCurrency);
        double convertedAmount = calculateDirectConversion(amount, exchangeRate);

        return createCustomConversionResult(fromCurrency, toCurrency, amount, convertedAmount, exchangeRate);
    }

    public double getExchangeRate(String fromCurrency, String toCurrency)
            throws IOException, InterruptedException {

        validateCurrencies(fromCurrency, toCurrency);

        if (fromCurrency.equals(toCurrency)) {
            return 1.0;
        }

        String url = ApiConfig.getPairConversionUrl(fromCurrency, toCurrency);
        String jsonResponse = ApiClient.sendGetRequest(url);
        ConversionResult result = JsonParser.parseConversionResult(jsonResponse);

        if (!result.isSuccess()) {
            throw new RuntimeException("Error obteniendo tasa: " + result.getErrorType());
        }

        return result.getConversionRate();
    }

    public List<String> getAllConversionPairs() {
        List<String> pairs = new ArrayList<>();
        SupportedCurrency[] currencies = SupportedCurrency.values();

        for (SupportedCurrency from : currencies) {
            for (SupportedCurrency to : currencies) {
                if (!from.equals(to)) {
                    pairs.add(from.getCode() + " → " + to.getCode());
                }
            }
        }

        return pairs;
    }

    public List<SupportedCurrency> getSupportedCurrencies() {
        return List.of(SupportedCurrency.values());
    }

    private void validateCurrency(String currency) {
        if (!SupportedCurrency.isSupported(currency)) {
            throw new IllegalArgumentException("Moneda no soportada: " + currency +
                    ". Use: ARS, BOB, BRL, CLP, COP, USD");
        }
    }

    private void validateCurrencies(String fromCurrency, String toCurrency) {
        validateCurrency(fromCurrency);
        validateCurrency(toCurrency);
    }

    private void validateCurrencyConversion(String fromCurrency, String toCurrency, double amount) {
        validateCurrencies(fromCurrency, toCurrency);

        if (!CurrencyCalculator.isValidAmount(amount)) {
            throw new IllegalArgumentException("Cantidad inválida: " + amount);
        }
    }

    private void validateConversionResult(ConversionResult result) {
        if (result.getConversionResult() == null) {
            throw new RuntimeException("La respuesta de la API no contiene el resultado de conversión");
        }

        if (!CurrencyCalculator.isValidRate(result.getConversionRate())) {
            throw new RuntimeException("Tasa de cambio inválida recibida de la API");
        }
    }

    private ConversionResult createSameCurrencyResult(String currency, double amount) {
        ConversionResult result = new ConversionResult();
        result.setResult("success");
        result.setBaseCode(currency);
        result.setTargetCode(currency);
        result.setConversionRate(1.0);
        result.setConversionResult(amount);
        return result;
    }

    private ConversionResult createCustomConversionResult(String fromCurrency, String toCurrency,
                                                          double amount, double convertedAmount, double rate) {
        ConversionResult result = new ConversionResult();
        result.setResult("success");
        result.setBaseCode(fromCurrency);
        result.setTargetCode(toCurrency);
        result.setConversionRate(rate);
        result.setConversionResult(convertedAmount);
        return result;
    }
}
