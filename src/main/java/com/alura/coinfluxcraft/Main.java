package main.java.com.alura.coinfluxcraft;

import main.java.com.alura.coinfluxcraft.ui.ConversorMenu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            ConversorMenu conversorBasico = new ConversorMenu();
            conversorBasico.ejecutarConversor();
            conversorBasico.cerrar();
        } catch (Exception e) {
            System.err.println("Error inesperado en el programa: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}