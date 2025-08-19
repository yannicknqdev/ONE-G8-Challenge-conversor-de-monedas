package main.java.com.alura.coinfluxcraft.models;

public enum SupportedCurrency {
    // Monedas originales latinoamericanas
    ARS("Peso argentino", "$", "America/Argentina/Buenos_Aires"),
    BOB("Boliviano boliviano", "Bs", "America/La_Paz"),
    BRL("Real brasileño", "R$", "America/Sao_Paulo"),
    CLP("Peso chileno", "$", "America/Santiago"),
    COP("Peso colombiano", "$", "America/Bogota"),

    // Monedas principales mundiales
    USD("Dólar estadounidense", "$", "America/New_York");

    private final String name;
    private final String symbol;
    private final String timezone;

    SupportedCurrency(String name, String symbol, String timezone) {
        this.name = name;
        this.symbol = symbol;
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCode() {
        return this.name();
    }

    public static boolean isSupported(String currencyCode) {
        try {
            valueOf(currencyCode.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static SupportedCurrency fromCode(String code) {
        return valueOf(code.toUpperCase());
    }

    public boolean isLatinAmerican() {
        return this == ARS || this == BOB || this == BRL || this == CLP ||
                this == COP;
    }

    public boolean isMajorCurrency() {
        return this == USD;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", name, getCode(), symbol);
    }
}
