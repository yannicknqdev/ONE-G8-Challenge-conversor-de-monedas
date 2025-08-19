package main.java.com.alura.coinfluxcraft.ui;

import main.java.com.alura.coinfluxcraft.models.ConversionResult;
import main.java.com.alura.coinfluxcraft.models.SupportedCurrency;
import main.java.com.alura.coinfluxcraft.models.ConversionHistory;
import main.java.com.alura.coinfluxcraft.service.ConversionService;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class AdvancedConversorMenu {
    private final ConversionService conversionService;
    private final Scanner scanner;
    private final ConversionHistory history;
    private List<String> favoriteConversions;

    public AdvancedConversorMenu() {
        this.conversionService = new ConversionService();
        this.scanner = new Scanner(System.in);
        this.history = new ConversionHistory();
        this.favoriteConversions = Arrays.asList(
                "USD->ARS", "USD->BRL", "EUR->USD", "USD->MXN", "GBP->USD"
        );
    }

    public void ejecutarConversorAvanzado() {
        mostrarBienvenidaAvanzada();

        boolean continuar = true;
        while (continuar) {
            int opcion = mostrarMenuPrincipal();

            switch (opcion) {
                case 1:
                    mostrarConversionesRapidas();
                    break;
                case 2:
                    realizarConversionPersonalizada();
                    break;
                case 3:
                    explorarTodasLasMonedas();
                    break;
                case 4:
                    mostrarHistorialAvanzado();
                    break;
                case 5:
                    mostrarEstadisticas();
                    break;
                case 6:
                    buscarEnHistorial();
                    break;
                case 7:
                    mostrarActividadHoy();
                    break;
                case 8:
                    gestionarFavoritos();
                    break;
                case 9:
                    continuar = false;
                    mostrarDespedidaAvanzada();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor seleccione 1-9.");
                    break;
            }

            if (continuar) {
                esperarContinuar();
            }
        }
    }

    private void mostrarBienvenidaAvanzada() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("    🌍 CONVERSOR DE MONEDAS MUNDIAL AVANZADO 🌍");
        System.out.println("=".repeat(60));
        System.out.printf("     Soporte para %d monedas de todo el mundo%n",
                SupportedCurrency.values().length);
        System.out.println("     Con historial, estadísticas y persistencia");

        if (!history.isEmpty()) {
            System.out.printf("     Historial actual: %d conversiones guardadas%n",
                    history.getHistorySize());
        }

        System.out.println("=".repeat(60));
    }

    private int mostrarMenuPrincipal() {
        System.out.println("\n🔹 MENÚ PRINCIPAL 🔹");
        System.out.println("1) 🚀 Conversiones rápidas (favoritas)");
        System.out.println("2) 🎯 Conversión personalizada");
        System.out.println("3) 🌐 Explorar todas las monedas");
        System.out.println("4) 📜 Historial avanzado de conversiones");
        System.out.println("5) 📊 Estadísticas y análisis");
        System.out.println("6) 🔍 Buscar en historial");
        System.out.println("7) 📅 Actividad de hoy");
        System.out.println("8) ⭐ Gestionar favoritos");
        System.out.println("9) 🚪 Salir");
        System.out.print("\nSeleccione una opción (1-9): ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void mostrarConversionesRapidas() {
        System.out.println("\n🚀 CONVERSIONES RÁPIDAS");
        System.out.println("Seleccione una conversión popular:");

        for (int i = 0; i < favoriteConversions.size(); i++) {
            String conversion = favoriteConversions.get(i);
            String[] parts = conversion.split("->");
            System.out.printf("%d) %s → %s%n", i + 1, parts[0], parts[1]);
        }

        System.out.print("\nSeleccione conversión (1-" + favoriteConversions.size() + "): ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());
            if (opcion >= 1 && opcion <= favoriteConversions.size()) {
                String conversion = favoriteConversions.get(opcion - 1);
                String[] parts = conversion.split("->");
                realizarConversion(parts[0], parts[1]);
            } else {
                System.out.println("Opción no válida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un número válido.");
        }
    }

    private void realizarConversionPersonalizada() {
        System.out.println("\n🎯 CONVERSIÓN PERSONALIZADA");

        String fromCurrency = seleccionarMoneda("Moneda de origen");
        if (fromCurrency == null) return;

        String toCurrency = seleccionarMoneda("Moneda de destino");
        if (toCurrency == null) return;

        realizarConversion(fromCurrency, toCurrency);
    }

    private String seleccionarMoneda(String prompt) {
        System.out.println("\n" + prompt + ":");
        System.out.println("Principales: USD, EUR, GBP, JPY, CAD, CHF, AUD");
        System.out.println("Latinoamericanas: ARS, BRL, COP, CLP, MXN, PEN, UYU, BOB");
        System.out.println("Asiáticas: CNY, KRW, INR");
        System.out.print("Ingrese código de moneda: ");

        String currency = scanner.nextLine().trim().toUpperCase();

        if (SupportedCurrency.isSupported(currency)) {
            return currency;
        } else {
            System.out.println("Moneda no soportada: " + currency);
            return null;
        }
    }

    private void explorarTodasLasMonedas() {
        System.out.println("\n🌐 TODAS LAS MONEDAS SOPORTADAS");

        SupportedCurrency[] currencies = SupportedCurrency.values();

        System.out.println("\n🌎 MONEDAS LATINOAMERICANAS:");
        Arrays.stream(currencies)
                .filter(SupportedCurrency::isLatinAmerican)
                .forEach(currency -> System.out.printf("  %s - %s%n",
                        currency.getCode(), currency.toString()));

        System.out.println("\n💰 MONEDAS PRINCIPALES:");
        Arrays.stream(currencies)
                .filter(SupportedCurrency::isMajorCurrency)
                .forEach(currency -> System.out.printf("  %s - %s%n",
                        currency.getCode(), currency.toString()));

        System.out.println("\n🌏 OTRAS MONEDAS:");
        Arrays.stream(currencies)
                .filter(currency -> !currency.isLatinAmerican() && !currency.isMajorCurrency())
                .forEach(currency -> System.out.printf("  %s - %s%n",
                        currency.getCode(), currency.toString()));

        System.out.printf("\nTotal: %d monedas soportadas%n", currencies.length);
    }

    private void realizarConversion(String monedaOrigen, String monedaDestino) {
        try {
            System.out.printf("\nConversión: %s → %s%n", monedaOrigen, monedaDestino);
            System.out.print("Ingrese la cantidad: ");

            double valor = Double.parseDouble(scanner.nextLine().trim());

            if (valor <= 0) {
                System.out.println("Error: El valor debe ser positivo.");
                return;
            }

            System.out.println("Procesando conversión...");

            ConversionResult resultado = conversionService.performCurrencyConversion(
                    monedaOrigen, monedaDestino, valor);

            mostrarResultadoAvanzado(monedaOrigen, monedaDestino, valor, resultado);

            // Guardar en historial avanzado
            history.addConversion(monedaOrigen, monedaDestino, valor,
                    resultado.getConversionResult(), resultado.getConversionRate());

        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un número válido.");
        } catch (Exception e) {
            System.err.println("Error durante la conversión: " + e.getMessage());
        }
    }

    private void mostrarResultadoAvanzado(String monedaOrigen, String monedaDestino,
                                          double valor, ConversionResult resultado) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           RESULTADO DE CONVERSIÓN");
        System.out.println("=".repeat(50));

        SupportedCurrency fromCurr = SupportedCurrency.fromCode(monedaOrigen);
        SupportedCurrency toCurr = SupportedCurrency.fromCode(monedaDestino);

        System.out.printf("💱 %s %.2f %s = %s %.2f %s%n",
                fromCurr.getSymbol(), valor, monedaOrigen,
                toCurr.getSymbol(), resultado.getConversionResult(), monedaDestino);

        System.out.printf("📈 Tasa: 1 %s = %.6f %s%n",
                monedaOrigen, resultado.getConversionRate(), monedaDestino);
        System.out.printf("📉 Inversa: 1 %s = %.6f %s%n",
                monedaDestino, 1.0 / resultado.getConversionRate(), monedaOrigen);

        System.out.printf("🌍 %s → %s%n", fromCurr.getName(), toCurr.getName());
        System.out.printf("⏰ Conversión guardada en historial%n");

        System.out.println("=".repeat(50));
    }

    private void mostrarHistorialAvanzado() {
        history.displayAdvancedHistory();
    }

    private void mostrarEstadisticas() {
        history.displayStatistics();
    }

    private void buscarEnHistorial() {
        System.out.println("\n🔍 BUSCAR EN HISTORIAL");
        System.out.println("1) Buscar por fecha");
        System.out.println("2) Buscar por par de monedas");
        System.out.println("3) Últimas N conversiones");
        System.out.print("Seleccione opción: ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());

            switch (opcion) {
                case 1:
                    buscarPorFecha();
                    break;
                case 2:
                    buscarPorParMonedas();
                    break;
                case 3:
                    buscarUltimas();
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un número válido.");
        }
    }

    private void buscarPorFecha() {
        System.out.print("Ingrese fecha (dd/mm/yyyy) o 'hoy': ");
        String input = scanner.nextLine().trim();

        LocalDate fecha;
        if ("hoy".equalsIgnoreCase(input)) {
            fecha = LocalDate.now();
        } else {
            try {
                String[] parts = input.split("/");
                fecha = LocalDate.of(
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[0])
                );
            } catch (Exception e) {
                System.out.println("Formato de fecha inválido.");
                return;
            }
        }

        List<ConversionHistory.ConversionRecord> records =
                history.getHistoryByDate(fecha);

        if (records.isEmpty()) {
            System.out.printf("No se encontraron conversiones para %s%n", fecha);
        } else {
            System.out.printf("\nConversiones del %s:%n", fecha);
            for (ConversionHistory.ConversionRecord record : records) {
                System.out.println("  " + record.toDetailedString());
            }
        }
    }

    private void buscarPorParMonedas() {
        System.out.print("Moneda origen: ");
        String from = scanner.nextLine().trim().toUpperCase();
        System.out.print("Moneda destino: ");
        String to = scanner.nextLine().trim().toUpperCase();

        List<ConversionHistory.ConversionRecord> records =
                history.getHistoryByCurrencyPair(from, to);

        if (records.isEmpty()) {
            System.out.printf("No se encontraron conversiones para %s→%s%n", from, to);
        } else {
            System.out.printf("\nConversiones %s→%s:%n", from, to);
            for (ConversionHistory.ConversionRecord record : records) {
                System.out.println("  " + record.toDetailedString());
            }
        }
    }

    private void buscarUltimas() {
        System.out.print("¿Cuántas conversiones mostrar? ");
        try {
            int n = Integer.parseInt(scanner.nextLine().trim());
            List<ConversionHistory.ConversionRecord> records =
                    history.getLastNConversions(n);

            System.out.printf("\nÚltimas %d conversiones:%n", records.size());
            for (ConversionHistory.ConversionRecord record : records) {
                System.out.println("  " + record.toDetailedString());
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un número válido.");
        }
    }

    private void mostrarActividadHoy() {
        history.displayTodaysActivity();
    }

    private void gestionarFavoritos() {
        System.out.println("\n⭐ GESTIÓN DE FAVORITOS");
        System.out.println("Conversiones favoritas actuales:");
        for (int i = 0; i < favoriteConversions.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, favoriteConversions.get(i));
        }

        System.out.println("\nBasado en tu historial, podrías agregar:");
        List<String> popular = history.getMostPopularPairs(3);
        for (String pair : popular) {
            if (!favoriteConversions.contains(pair)) {
                System.out.println("  📈 " + pair);
            }
        }
    }

    private void esperarContinuar() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    private void mostrarDespedidaAvanzada() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  🌟 ¡Gracias por usar el Conversor Avanzado! 🌟");

        if (!history.isEmpty()) {
            System.out.printf("     Sesión completada: %d conversiones realizadas%n",
                    history.getHistorySize());

            List<String> topCurrencies = history.getMostUsedCurrencies(3);
            if (!topCurrencies.isEmpty()) {
                System.out.print("     Monedas más utilizadas: ");
                System.out.println(String.join(", ", topCurrencies));
            }
        }

        System.out.println("     Tu historial ha sido guardado automáticamente");
        System.out.println("     ¡Hasta la próxima! 👋");
        System.out.println("=".repeat(60));
    }

    public void cerrar() {
        scanner.close();
    }
}