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
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class closeListeners {

    private static List<JComponent> componentsWithListeners = new ArrayList<>();

    public static void registerComponent(JComponent component) {
        if (!componentsWithListeners.contains(component)) {
            componentsWithListeners.add(component);
        }
    }

    
    public static void clearAllListeners() {
        for (JComponent component : componentsWithListeners) {
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                for (ActionListener al : button.getActionListeners()) {
                    button.removeActionListener(al);
                }
            }
            //Aqui se puede extender para otros tipos de listeners
        }
        componentsWithListeners.clear(); 
    }

    public static void clearListeners(JComponent component) {
        if (component instanceof AbstractButton) {
            AbstractButton button = (AbstractButton) component;
            for (ActionListener al : button.getActionListeners()) {
                button.removeActionListener(al);
            }
        }
        componentsWithListeners.remove(component); // Opcional: quitar de la lista
    }
}