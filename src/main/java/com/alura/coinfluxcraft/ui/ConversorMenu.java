package main.java.com.alura.coinfluxcraft.ui;

import main.java.com.alura.coinfluxcraft.models.ConversionResult;
import main.java.com.alura.coinfluxcraft.service.ConversionService;
import java.util.Scanner;

public class ConversorMenu {
    private final ConversionService conversionService;
    private final Scanner scanner;

    public ConversorMenu() {
        this.conversionService = new ConversionService();
        this.scanner = new Scanner(System.in);
    }

    public void ejecutarConversor() {
        boolean continuar = true;

        while (continuar) {
            exibirMenu();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    realizarConversion("USD", "ARS");
                    break;
                case 2:
                    realizarConversion("ARS", "USD");
                    break;
                case 3:
                    realizarConversion("USD", "BRL");
                    break;
                case 4:
                    realizarConversion("BRL", "USD");
                    break;
                case 5:
                    realizarConversion("USD", "COP");
                    break;
                case 6:
                    realizarConversion("COP", "USD");
                    break;
                case 7:
                    realizarConversion("USD", "CLP");
                    break;
                case 8:
                    realizarConversion("CLP", "USD");
                    break;
                case 9:
                    realizarConversion("USD", "BOB");
                    break;
                case 10:
                    realizarConversion("BOB", "USD");
                    break;
                case 11:
                    continuar = false;
                    mostrarDespedida();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor seleccione una opción del 1 al 11.");
                    break;
            }

            if (continuar) {
                esperarContinuar();
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n*************************************");
        System.out.println("Sea bienvenido/a al Conversor de Moneda =]");
        System.out.println("");
        System.out.println("1) Dólar =>> Peso argentino");
        System.out.println("2) Peso argentino =>> Dólar");
        System.out.println("3) Dólar =>> Real brasileño");
        System.out.println("4) Real brasileño =>> Dólar");
        System.out.println("5) Dólar =>> Peso colombiano");
        System.out.println("6) Peso colombiano =>> Dólar");
        System.out.println("7) Dólar =>> Peso chileno");
        System.out.println("8) Peso chileno =>> Dólar");
        System.out.println("9) Dólar =>> Boliviano boliviano");
        System.out.println("10) Boliviano boliviano =>> Dólar");
        System.out.println("11) Salir");
        System.out.println("Elija una opción válida:");
        System.out.println("*************************************");
        System.out.print(">> ");
    }

    private int leerOpcion() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void realizarConversion(String monedaOrigen, String monedaDestino) {
        int intentos = 0;
        final int maxIntentos = 3;

        while (intentos < maxIntentos) {
            try {
                System.out.printf("\nIngrese el valor que desea convertir de %s a %s:%n",
                        obtenerNombreMoneda(monedaOrigen), obtenerNombreMoneda(monedaDestino));
                System.out.print(">> ");

                double valor = leerValor();

                if (valor <= 0) {
                    intentos++;
                    if (intentos < maxIntentos) {
                        System.out.printf("Error: El valor debe ser positivo. Intentos restantes: %d%n",
                                maxIntentos - intentos);
                        continue;
                    } else {
                        System.out.println("Máximo número de intentos alcanzado. Regresando al menú principal.");
                        return;
                    }
                }

                if (valor > 1000000) {
                    System.out.println("Advertencia: Cantidad muy grande. ¿Está seguro? (s/n)");
                    System.out.print(">> ");
                    String confirmacion = scanner.nextLine().trim().toLowerCase();
                    if (!confirmacion.equals("s") && !confirmacion.equals("si")) {
                        System.out.println("Conversión cancelada.");
                        return;
                    }
                }

                System.out.println("\nProcesando conversión...");

                ConversionResult resultado = conversionService.performCurrencyConversion(
                        monedaOrigen, monedaDestino, valor);

                mostrarResultadoConversion(monedaOrigen, monedaDestino, valor, resultado);
                return; // Salir del bucle si la conversión fue exitosa

            } catch (NumberFormatException e) {
                intentos++;
                if (intentos < maxIntentos) {
                    System.out.printf("Error: Ingrese un número válido. Intentos restantes: %d%n",
                            maxIntentos - intentos);
                } else {
                    System.out.println("Máximo número de intentos alcanzado. Regresando al menú principal.");
                    return;
                }
            } catch (Exception e) {
                System.err.println("Error durante la conversión: " + e.getMessage());
                System.out.println("Por favor, intente nuevamente o verifique su conexión a internet.");
                return;
            }
        }
    }

    private double leerValor() {
        try {
            String input = scanner.nextLine().trim();
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void mostrarResultadoConversion(String monedaOrigen, String monedaDestino,
                                            double valor, ConversionResult resultado) {
        System.out.println("\n===== RESULTADO DE LA CONVERSIÓN =====");

        String simboloOrigen = obtenerSimboloMoneda(monedaOrigen);
        String simboloDestino = obtenerSimboloMoneda(monedaDestino);

        System.out.printf("El valor %.2f [%s] corresponde al valor final de =>>> %.2f [%s]%n",
                valor, monedaOrigen, resultado.getConversionResult(), monedaDestino);

        System.out.printf("%s %.2f %s = %s %.2f %s%n",
                simboloOrigen, valor, monedaOrigen,
                simboloDestino, resultado.getConversionResult(), monedaDestino);

        System.out.printf("Tasa de conversión utilizada: 1 %s = %.6f %s%n",
                monedaOrigen, resultado.getConversionRate(), monedaDestino);

        System.out.println("=======================================");
    }

    private void esperarContinuar() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    private void mostrarDespedida() {
        System.out.println("\n*************************************");
        System.out.println("¡Gracias por usar el Conversor de Monedas!");
        System.out.println("¡Que tengas un excelente día!");
        System.out.println("*************************************");
    }

    private String obtenerNombreMoneda(String codigo) {
        switch (codigo) {
            case "USD": return "Dólar estadounidense";
            case "ARS": return "Peso argentino";
            case "BRL": return "Real brasileño";
            case "COP": return "Peso colombiano";
            case "CLP": return "Peso chileno";
            case "BOB": return "Boliviano boliviano";
            default: return codigo;
        }
    }

    private String obtenerSimboloMoneda(String codigo) {
        switch (codigo) {
            case "USD": return "$";
            case "ARS": return "$";
            case "BRL": return "R$";
            case "COP": return "$";
            case "CLP": return "$";
            case "BOB": return "Bs";
            default: return codigo;
        }
    }

    public void cerrar() {
        scanner.close();
    }
}