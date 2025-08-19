package main.java.com.alura.coinfluxcraft.models;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class ConversionHistory {
    private static final DateTimeFormatter DETAILED_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String HISTORY_FILE = "conversion_history.txt";

    private List<ConversionRecord> history;
    private Map<String, Integer> currencyUsageCount;
    private Map<String, Double> totalAmountsByPair;

    public ConversionHistory() {
        this.history = new ArrayList<>();
        this.currencyUsageCount = new HashMap<>();
        this.totalAmountsByPair = new HashMap<>();
        loadHistoryFromFile();
    }

    public void addConversion(String fromCurrency, String toCurrency,
                              double amount, double result, double rate) {
        ConversionRecord record = new ConversionRecord(
                fromCurrency, toCurrency, amount, result, rate, ZonedDateTime.now()
        );

        history.add(record);
        updateStatistics(fromCurrency, toCurrency, amount);
        saveHistoryToFile();
    }

    private void updateStatistics(String fromCurrency, String toCurrency, double amount) {
        // Actualizar contadores de uso
        currencyUsageCount.merge(fromCurrency, 1, Integer::sum);
        currencyUsageCount.merge(toCurrency, 1, Integer::sum);

        // Actualizar totales por par
        String pair = fromCurrency + "->" + toCurrency;
        totalAmountsByPair.merge(pair, amount, Double::sum);
    }

    public List<ConversionRecord> getHistory() {
        return new ArrayList<>(history);
    }

    public List<ConversionRecord> getHistoryByDate(LocalDate date) {
        return history.stream()
                .filter(record -> record.getTimestamp().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<ConversionRecord> getHistoryByDateRange(LocalDate start, LocalDate end) {
        return history.stream()
                .filter(record -> {
                    LocalDate recordDate = record.getTimestamp().toLocalDate();
                    return !recordDate.isBefore(start) && !recordDate.isAfter(end);
                })
                .collect(Collectors.toList());
    }

    public List<ConversionRecord> getHistoryByCurrencyPair(String fromCurrency, String toCurrency) {
        return history.stream()
                .filter(record -> record.getFromCurrency().equals(fromCurrency) &&
                        record.getToCurrency().equals(toCurrency))
                .collect(Collectors.toList());
    }

    public Map<String, Long> getConversionsByHour() {
        return history.stream()
                .collect(Collectors.groupingBy(
                        record -> String.valueOf(record.getTimestamp().getHour()),
                        Collectors.counting()
                ));
    }

    public Map<String, Integer> getCurrencyUsageStatistics() {
        return new HashMap<>(currencyUsageCount);
    }

    public List<String> getMostUsedCurrencies(int limit) {
        return currencyUsageCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> getMostPopularPairs(int limit) {
        return totalAmountsByPair.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public void displayAdvancedHistory() {
        if (history.isEmpty()) {
            System.out.println("No hay conversiones en el historial.");
            return;
        }

        System.out.println("\n=============================================");
        System.out.println("       HISTORIAL AVANZADO DE CONVERSIONES");
        System.out.println("=============================================");

        System.out.printf("Total de conversiones: %d%n", history.size());
        System.out.printf("Primera conversión: %s%n",
                history.get(0).getTimestamp().format(DETAILED_FORMATTER));
        System.out.printf("Última conversión: %s%n",
                history.get(history.size()-1).getTimestamp().format(DETAILED_FORMATTER));

        System.out.println("\n--- ÚLTIMAS 10 CONVERSIONES ---");
        List<ConversionRecord> recent = getLastNConversions(10);
        for (int i = 0; i < recent.size(); i++) {
            ConversionRecord record = recent.get(i);
            System.out.printf("%d. %s%n", i + 1, record.toDetailedString());
        }

        displayStatistics();
    }

    public void displayStatistics() {
        System.out.println("\n--- ESTADÍSTICAS DE USO ---");

        System.out.println("Monedas más utilizadas:");
        List<String> topCurrencies = getMostUsedCurrencies(5);
        for (int i = 0; i < topCurrencies.size(); i++) {
            String currency = topCurrencies.get(i);
            System.out.printf("  %d. %s: %d usos%n",
                    i + 1, currency, currencyUsageCount.get(currency));
        }

        System.out.println("\nPares de conversión más populares:");
        List<String> topPairs = getMostPopularPairs(5);
        for (int i = 0; i < topPairs.size(); i++) {
            String pair = topPairs.get(i);
            System.out.printf("  %d. %s: %.2f total convertido%n",
                    i + 1, pair, totalAmountsByPair.get(pair));
        }

        Map<String, Long> hourlyStats = getConversionsByHour();
        if (!hourlyStats.isEmpty()) {
            System.out.println("\nActividad por hora del día:");
            hourlyStats.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(3)
                    .forEach(entry ->
                            System.out.printf("  %s:00 - %d conversiones%n",
                                    entry.getKey(), entry.getValue()));
        }

        System.out.println("=============================================\n");
    }

    public void displayTodaysActivity() {
        LocalDate today = LocalDate.now();
        List<ConversionRecord> todaysConversions = getHistoryByDate(today);

        System.out.println("\n=== ACTIVIDAD DE HOY ===");
        if (todaysConversions.isEmpty()) {
            System.out.println("No se han realizado conversiones hoy.");
        } else {
            System.out.printf("Conversiones realizadas hoy: %d%n", todaysConversions.size());
            for (ConversionRecord record : todaysConversions) {
                System.out.println("  " + record.toDetailedString());
            }
        }
        System.out.println("========================\n");
    }

    public List<ConversionRecord> getLastNConversions(int n) {
        if (history.isEmpty()) {
            return new ArrayList<>();
        }

        int start = Math.max(0, history.size() - n);
        return new ArrayList<>(history.subList(start, history.size()));
    }

    private void saveHistoryToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HISTORY_FILE))) {
            for (ConversionRecord record : history) {
                writer.println(record.toFileFormat());
            }
        } catch (IOException e) {
            System.err.println("Error guardando historial: " + e.getMessage());
        }
    }

    private void loadHistoryFromFile() {
        File file = new File(HISTORY_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ConversionRecord record = ConversionRecord.fromFileFormat(line);
                if (record != null) {
                    history.add(record);
                    updateStatistics(record.getFromCurrency(),
                            record.getToCurrency(), record.getAmount());
                }
            }
        } catch (IOException e) {
            System.err.println("Error cargando historial: " + e.getMessage());
        }
    }

    public void clearHistory() {
        history.clear();
        currencyUsageCount.clear();
        totalAmountsByPair.clear();

        try {
            new File(HISTORY_FILE).delete();
        } catch (Exception e) {
            System.err.println("Error eliminando archivo de historial: " + e.getMessage());
        }
    }

    public int getHistorySize() {
        return history.size();
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }

    public static class ConversionRecord {
        private final String fromCurrency;
        private final String toCurrency;
        private final double amount;
        private final double result;
        private final double rate;
        private final ZonedDateTime timestamp;

        public ConversionRecord(String fromCurrency, String toCurrency,
                                double amount, double result, double rate, ZonedDateTime timestamp) {
            this.fromCurrency = fromCurrency;
            this.toCurrency = toCurrency;
            this.amount = amount;
            this.result = result;
            this.rate = rate;
            this.timestamp = timestamp;
        }

        public String getFromCurrency() { return fromCurrency; }
        public String getToCurrency() { return toCurrency; }
        public double getAmount() { return amount; }
        public double getResult() { return result; }
        public double getRate() { return rate; }
        public ZonedDateTime getTimestamp() { return timestamp; }

        public String toDetailedString() {
            return String.format("%.2f %s → %.2f %s (Tasa: %.6f) - %s [%s]",
                    amount, fromCurrency, result, toCurrency, rate,
                    timestamp.format(DETAILED_FORMATTER),
                    timestamp.getZone().getId());
        }

        public String toFileFormat() {
            return String.format("%s|%s|%.6f|%.6f|%.6f|%s",
                    fromCurrency, toCurrency, amount, result, rate,
                    timestamp.toString());
        }

        public static ConversionRecord fromFileFormat(String line) {
            try {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    return new ConversionRecord(
                            parts[0], parts[1],
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3]),
                            Double.parseDouble(parts[4]),
                            ZonedDateTime.parse(parts[5])
                    );
                }
            } catch (Exception e) {
                System.err.println("Error parseando línea del historial: " + line);
            }
            return null;
        }

        @Override
        public String toString() {
            return String.format("%.2f %s → %.2f %s - %s",
                    amount, fromCurrency, result, toCurrency,
                    timestamp.format(DETAILED_FORMATTER));
        }
    }
}