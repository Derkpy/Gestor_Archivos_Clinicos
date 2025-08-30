/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.logic;

import com.derkcode.gestor_archivos_clinicos.data.source.DataSource;
import com.derkcode.gestor_archivos_clinicos.ui.Management.Consulta;
import com.derkcode.gestor_archivos_clinicos.ui.Management.History_ui;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.derkcode.gestor_archivos_clinicos.util.PDFsGenerator.PdfGeneratorReceta;
import java.awt.Window;

public class Consulta_Logic {
    
    private Consulta c_view;
    private DataSource query = new DataSource();
    
    public Consulta_Logic(Consulta c_view) {
        this.c_view = c_view;
        initializeComponents();
        iniListeners();
    }
    
    private void initializeComponents() {
        String expediente = c_view.getExpediente();
        c_view.getLabelPaciente().setText(expediente);
    }

    private void iniListeners() {
        c_view.getBtnGenerar().addActionListener(e -> { 
            Long idPaciente = c_view.getIdPaciente();
            String expediente = c_view.getExpediente();
            String enfermedad = c_view.getTxtFieldEnfermedad().getText().trim();
            String sintomas = c_view.getTxtAreaSintomas().getText().trim();
            String tratamiento = c_view.getTxtAreaTratamiento().getText().trim();
            String sugerencias = c_view.getTxtAreaSugerencias().getText().trim();
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            if (enfermedad.isEmpty() || sintomas.isEmpty() || tratamiento.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Síntomas y tratamiento son obligatorios");
            } else {
                query.insertarConsulta(idPaciente, enfermedad, sintomas, tratamiento, sugerencias, fecha);
                PdfGeneratorReceta.generateReceta(expediente, enfermedad, sintomas, tratamiento, sugerencias);
                JOptionPane.showMessageDialog(null, "Receta generada como receta_" + expediente + "_" + LocalDateTime.now().toString().replace(":", "-") + ".pdf");
                limpiar();
            }
        });
        
        c_view.getBtnImprimir().addActionListener(e -> {
            try {
                String lastFile = "receta_" + c_view.getIdPaciente() + "_" + LocalDateTime.now().toString().replace(":", "-") + ".pdf";
                if (new java.io.File(lastFile).exists()) {
                    java.awt.Desktop.getDesktop().print(new java.io.File(lastFile));
                    JOptionPane.showMessageDialog(null, "Imprimiendo receta...");
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el archivo de receta para imprimir.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al imprimir: " + ex.getMessage());
            }
        });
        
        c_view.getBtnDescargar().addActionListener(e -> {
            String expediente = c_view.getExpediente();
            String enfermedad = c_view.getTxtFieldEnfermedad().getText().trim();
            String sintomas = c_view.getTxtAreaSintomas().getText().trim();
            String tratamiento = c_view.getTxtAreaTratamiento().getText().trim();
            String sugerencias = c_view.getTxtAreaSugerencias().getText().trim();
            PdfGeneratorReceta.generateReceta(expediente, enfermedad, sintomas, tratamiento, sugerencias);
            JOptionPane.showMessageDialog(null, "PDF generado como receta_" + expediente + "_" + LocalDateTime.now().toString().replace(":", "-") + ".pdf");
        });
        
        c_view.getBtnAtras().addActionListener(e -> {
            Window[] windows = Window.getWindows();
            for (Window w : windows) {
                if (w instanceof History_ui && !w.isVisible()) {
                    w.setVisible(true);
                    break;
                }
            }
            c_view.dispose();
        });
    }
    
    private void limpiar() {
        c_view.getTxtFieldEnfermedad().setText("");
        c_view.getTxtAreaSintomas().setText("");
        c_view.getTxtAreaTratamiento().setText("");
        c_view.getTxtAreaSugerencias().setText("");
    }
}