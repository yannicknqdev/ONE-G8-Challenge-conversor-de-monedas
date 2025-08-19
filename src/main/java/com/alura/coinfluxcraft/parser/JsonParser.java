package main.java.com.alura.coinfluxcraft.parser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import main.java.com.alura.coinfluxcraft.models.ConversionResult;
import main.java.com.alura.coinfluxcraft.models.ExchangeRateResponse;

public class JsonParser {
    private static final Gson gson = new Gson();

    public static ExchangeRateResponse parseExchangeRateResponse(String jsonResponse) {
        try {
            return gson.fromJson(jsonResponse, ExchangeRateResponse.class);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Error parsing JSON response: " + e.getMessage(), e);
        }
    }
    public static ConversionResult parseConversionResult(String jsonResponse) {
        try {
            return gson.fromJson(jsonResponse, ConversionResult.class);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Error parsing JSON response: " + e.getMessage(), e);
        }
    }

    public static String toJson(Object object) {
        try {
            return gson.toJson(object);
        } catch (Exception e) {
            throw new RuntimeException("Error converting object to JSON: " + e.getMessage(), e);
        }
    }

    public static boolean isValidJson(String jsonString) {
        try {
            gson.fromJson(jsonString, Object.class);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }
}
