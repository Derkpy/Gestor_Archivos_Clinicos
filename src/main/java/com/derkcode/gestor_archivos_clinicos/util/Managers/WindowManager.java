/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.util.Managers;

/**
 *
 * @author Derek
 */
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WindowManager {

    // Guarda las ventanas creadas
    private static final Map<Class<? extends JFrame>, JFrame> windows = new HashMap<>();

    // Abre o muestra una ventana (con inicializador opcional para lógica)
    public static <T extends JFrame> void showWindow(Class<T> windowClass, Consumer<T> initializer) {
        try {
            T window = (T) windows.get(windowClass);

            if (window == null) { 
                // Si no existe, la creamos
                window = windowClass.getDeclaredConstructor().newInstance();
                windows.put(windowClass, window);

                // Aquí se inicializa la lógica si se pasa
                if (initializer != null) {
                    initializer.accept(window);
                }
            }

            window.setLocationRelativeTo(null); // centrar
            window.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sobrecarga para abrir ventana sin lógica extra
    public static <T extends JFrame> void showWindow(Class<T> windowClass) {
        showWindow(windowClass, null);
    }

    // Ocultar la ventana
    public static <T extends JFrame> void hideWindow(Class<T> windowClass) {
        JFrame window = windows.get(windowClass);
        if (window != null) {
            window.setVisible(false);
        }
    }

    // Cerrar y eliminar la ventana
    public static <T extends JFrame> void closeWindow(Class<T> windowClass) {
        JFrame window = windows.remove(windowClass);
        if (window != null) {
            window.dispose();
        }
    }

    // Cerrar todas las ventanas
    public static void closeAll() {
        for (JFrame w : windows.values()) {
            w.dispose();
        }
        windows.clear();
    }
}