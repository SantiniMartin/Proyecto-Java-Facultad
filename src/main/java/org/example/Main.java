package org.example;

import org.example.menus.MenuPrincipal;
import org.example.util.CrearTablas;

public class Main {
    public static void main(String[] args) {
        CrearTablas.crearTablasEnBD();
        MenuPrincipal.mostrarMenuPrincipal();
    }
}