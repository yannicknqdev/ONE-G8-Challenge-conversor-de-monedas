package main.java.com.alura.coinfluxcraft.config;

public class ApiConfig {
    public static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private static String API_KEY;

    static {
        loadApiKey();
    }

    private static void loadApiKey() {
        API_KEY = System.getenv("EXCHANGE_RATE_API_KEY");

        if (API_KEY == null || API_KEY.trim().isEmpty()) {
            throw new RuntimeException(
                    "API key not found! Please set EXCHANGE_RATE_API_KEY environment variable.\n" +
                            "Run: export EXCHANGE_RATE_API_KEY=your_api_key_here"
            );
        }
         System.out.println("API key loaded successfully");
    }
}
