/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.logic;

import com.derkcode.gestor_archivos_clinicos.data.model.Consulta_Model;
import com.derkcode.gestor_archivos_clinicos.data.source.DataSource;
import com.derkcode.gestor_archivos_clinicos.ui.management.*;
import com.derkcode.gestor_archivos_clinicos.ui.menu.Buscar;
import com.derkcode.gestor_archivos_clinicos.util.Managers.WindowManager;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.derkcode.gestor_archivos_clinicos.util.PDFsGenerator.PdfGeneratorReceta;
import java.awt.Window;

public class Consulta_Logic {
    private Consulta c_view;
    private DataSource query = new DataSource();
    private Long idPaciente;
    private String expediente;
    private String consultaType; // "nueva_consulta" o "ver_consulta"
    private Consulta_Model consultaData; // Para datos prellenados desde History

    public Consulta_Logic(Consulta c_view, Long idPaciente, String expediente, String consultaType, Consulta_Model consultaData) {
        this.c_view = c_view;
        this.idPaciente = idPaciente;
        this.expediente = expediente;
        this.consultaType = consultaType;
        this.consultaData = consultaData;
        initializeComponents();
        iniListeners();
    }

    private void initializeComponents() {
        c_view.getLabelPaciente().setText(expediente);
        if ("ver_consulta".equals(consultaType) && consultaData != null) {
            // Modo "ver": prellenar campos y deshabilitar edición
            c_view.getTxtFieldEnfermedad().setText(consultaData.getEnfermedad());
            c_view.getTxtAreaSintomas().setText(consultaData.getSintomas());
            c_view.getTxtAreaTratamiento().setText(consultaData.getTratamiento());
            c_view.getTxtAreaSugerencias().setText(consultaData.getSugerencias());
            c_view.getTxtFieldEnfermedad().setEditable(false);
            c_view.getTxtAreaSintomas().setEditable(false);
            c_view.getTxtAreaTratamiento().setEditable(false);
            c_view.getTxtAreaSugerencias().setEditable(false);
            c_view.getBtnGenerar().setVisible(false);
            c_view.getBtnImprimir().setVisible(false);
            c_view.getBtnDescargar().setVisible(true);
        } else {
            // Modo "nueva_consulta": habilitar edición y mostrar botones de generación
            c_view.getTxtFieldEnfermedad().setEditable(true);
            c_view.getTxtAreaSintomas().setEditable(true);
            c_view.getTxtAreaTratamiento().setEditable(true);
            c_view.getTxtAreaSugerencias().setEditable(true);
            c_view.getBtnGenerar().setVisible(true);
            c_view.getBtnImprimir().setVisible(true);
            c_view.getBtnDescargar().setVisible(false);
        }
    }

    private void iniListeners() {
        c_view.getBtnGenerar().addActionListener(e -> { 
            if ("nueva_consulta".equals(consultaType)) { // Solo en modo nueva
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
            }
        });

        c_view.getBtnImprimir().addActionListener(e -> {
            if ("nueva_consulta".equals(consultaType)) { // Solo en modo nueva
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
            }
        });

        c_view.getBtnDescargar().addActionListener(e -> {
            if ("ver_consulta".equals(consultaType)) { // Solo en modo ver
                String enfermedad = c_view.getTxtFieldEnfermedad().getText().trim();
                String sintomas = c_view.getTxtAreaSintomas().getText().trim();
                String tratamiento = c_view.getTxtAreaTratamiento().getText().trim();
                String sugerencias = c_view.getTxtAreaSugerencias().getText().trim();
                PdfGeneratorReceta.generateReceta(expediente, enfermedad, sintomas, tratamiento, sugerencias);
                JOptionPane.showMessageDialog(null, "PDF generado como receta_" + expediente + "_" + LocalDateTime.now().toString().replace(":", "-") + ".pdf");
            }
        });

        c_view.getBtnAtras().addActionListener(e -> {
            
            WindowManager.closeWindow(Consulta.class);
            
            
        });
    }

    private void limpiar() {
        c_view.getTxtFieldEnfermedad().setText("");
        c_view.getTxtAreaSintomas().setText("");
        c_view.getTxtAreaTratamiento().setText("");
        c_view.getTxtAreaSugerencias().setText("");
    }
}