package main.java.com.alura.coinfluxcraft;

import main.java.com.alura.coinfluxcraft.ui.ConversorMenu;
import main.java.com.alura.coinfluxcraft.ui.AdvancedConversorMenu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("🌟 BIENVENIDO AL CONVERSOR DE MONEDAS 🌟");
            System.out.println("\nSeleccione el modo de uso:");
            System.out.println("1) 📱 Modo Básico (menú original)");
            System.out.println("2) 🚀 Modo Avanzado (con historial y estadísticas)");
            System.out.print("\nIngrese su opción (1 o 2): ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    System.out.println("\n🔄 Iniciando modo básico...");
                    ConversorMenu conversorBasico = new ConversorMenu();
                    conversorBasico.ejecutarConversor();
                    conversorBasico.cerrar();
                    break;

                case "2":
                    System.out.println("\n🚀 Iniciando modo avanzado...");
                    AdvancedConversorMenu conversorAvanzado = new AdvancedConversorMenu();
                    conversorAvanzado.ejecutarConversorAvanzado();
                    conversorAvanzado.cerrar();
                    break;

                default:
                    System.out.println("Opción no válida. Iniciando modo básico por defecto...");
                    ConversorMenu conversorDefault = new ConversorMenu();
                    conversorDefault.ejecutarConversor();
                    conversorDefault.cerrar();
                    break;
            }

        } catch (Exception e) {
            System.err.println("Error inesperado en el programa: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}