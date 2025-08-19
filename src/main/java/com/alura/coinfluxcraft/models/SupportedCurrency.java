package main.java.com.alura.coinfluxcraft.model;

public enum SupportedCurrency {
    // Monedas originales latinoamericanas
    ARS("Peso argentino", "$", "America/Argentina/Buenos_Aires"),
    BOB("Boliviano boliviano", "Bs", "America/La_Paz"),
    BRL("Real brasileño", "R$", "America/Sao_Paulo"),
    CLP("Peso chileno", "$", "America/Santiago"),
    COP("Peso colombiano", "$", "America/Bogota"),

    // Monedas principales mundiales
    USD("Dólar estadounidense", "$", "America/New_York"),
    EUR("Euro", "€", "Europe/Paris"),
    GBP("Libra esterlina", "£", "Europe/London"),
    JPY("Yen japonés", "¥", "Asia/Tokyo"),
    CAD("Dólar canadiense", "C$", "America/Toronto"),
    CHF("Franco suizo", "CHF", "Europe/Zurich"),
    AUD("Dólar australiano", "A$", "Australia/Sydney"),

    // Monedas asiáticas populares
    CNY("Yuan chino", "¥", "Asia/Shanghai"),
    KRW("Won surcoreano", "₩", "Asia/Seoul"),
    INR("Rupia india", "₹", "Asia/Kolkata"),

    // Monedas adicionales latinoamericanas
    MXN("Peso mexicano", "$", "America/Mexico_City"),
    PEN("Sol peruano", "S/", "America/Lima"),
    UYU("Peso uruguayo", "$U", "America/Montevideo");

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

    public String getCode() {
        return this.name();
    }

    public String getSymbol() {
        return symbol;
    }

    public String getTimezone() {
        return timezone;
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
                this == COP || this == MXN || this == PEN || this == UYU;
    }

    public boolean isMajorCurrency() {
        return this == USD || this == EUR || this == GBP || this == JPY ||
                this == CAD || this == CHF || this == AUD;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", name, getCode(), symbol);
    }
}