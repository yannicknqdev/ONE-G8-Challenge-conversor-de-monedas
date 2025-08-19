package main.java.com.alura.coinfluxcraft.service;

import main.java.com.alura.coinfluxcraft.models.SupportedCurrency;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyCalculator {
    private static final int DECIMAL_PRECISION = 6;

    public static double calculateConversion(double amount, double exchangeRate) {
        if (amount < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        if (exchangeRate <= 0) {
            throw new IllegalArgumentException("La tasa de cambio debe ser positiva");
        }

        BigDecimal amountDecimal = BigDecimal.valueOf(amount);
        BigDecimal rateDecimal = BigDecimal.valueOf(exchangeRate);
        BigDecimal result = amountDecimal.multiply(rateDecimal);

        return result.setScale(DECIMAL_PRECISION, RoundingMode.HALF_UP).doubleValue();
    }

    public static String formatAmount(double amount) {
        return String.format("%.2f", amount);
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0 && !Double.isInfinite(amount) && !Double.isNaN(amount);
    }

    public static boolean isValidRate(double rate) {
        return rate > 0 && !Double.isInfinite(rate) && !Double.isNaN(rate);
    }

    public static String getCurrencySymbol(String currencyCode) {
        try {
            SupportedCurrency currency = SupportedCurrency.fromCode(currencyCode);
            switch (currency) {
                case USD: return "$";
                case BRL: return "R$";
                case ARS: return "$";
                case CLP: return "$";
                case COP: return "$";
                case BOB: return "Bs";
                default: return currencyCode;
            }
        } catch (Exception e) {
            return currencyCode;
        }
    }

}
