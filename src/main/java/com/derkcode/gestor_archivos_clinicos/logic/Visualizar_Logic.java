/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.logic;

import com.derkcode.gestor_archivos_clinicos.data.source.DataSource;
import com.derkcode.gestor_archivos_clinicos.ui.menu.Buscar;
import com.derkcode.gestor_archivos_clinicos.ui.Management.Visualizar;
import com.derkcode.gestor_archivos_clinicos.ui.Management.History_ui;
import javax.swing.JOptionPane;

public class Visualizar_Logic {
    private Visualizar view;
    private DataSource dataSource;
    private long idSelec;
    private String expediente;
    private String name;
    
    public Visualizar_Logic(Visualizar view, long idPaciente, String expediente, String name) {
        this.view = view;
        this.dataSource = new DataSource();
        this.idSelec = idPaciente;
        this.expediente = expediente;
        this.name = name;
        view.getExpediente().setText(expediente); // Asignar el expediente inicial
        initializeComponents();
        iniListeners();
    }

    private void initializeComponents() {
        deshabilitarComponentes(
            view.getExpediente(), view.getFecha(), view.getNombre(),
            view.getNacimiento(), view.getNombreMadre(), view.getDireccion(),
            view.getLocalidad(), view.getMunicipio(), view.getCurp(), view.getTelefono()
        );
        deshabilitarComponentes(view.getSexo());
        deshabilitarTextAreas(view.getJtxtaAlergias());
        deshabilitarComponentes(view.getBtnGuardar(), view.getBtnCancelar());
    }

    private void iniListeners() {
        // Evento del botón Cancelar
        view.getBtnCancelar().addActionListener(e -> {
            deshabilitarComponentes(
                view.getExpediente(), view.getFecha(), view.getNombre(),
                view.getNacimiento(), view.getNombreMadre(), view.getDireccion(),
                view.getLocalidad(), view.getMunicipio(), view.getCurp(), view.getTelefono()
            );
            deshabilitarComponentes(view.getSexo());
            deshabilitarTextAreas(view.getJtxtaAlergias());
            deshabilitarComponentes(view.getBtnGuardar(), view.getBtnCancelar());
        });

        // Evento del botón Guardar (solo guarda y verifica duplicación)
        view.getBtnGuardar().addActionListener(e -> {
            String update = String.valueOf(idSelec);
            String rep = view.getExpediente().getText();

            if (!rep.equals(view.getExpedientes().get(0)) && dataSource.expedienteExiste(rep)) {
                JOptionPane.showMessageDialog(null, "EXPEDIENTE DUPLICADO, INGRESE UNO DIFERENTE");
            } else {
                dataSource.updatePaciente(update, view);
                deshabilitarComponentes(
                    view.getExpediente(), view.getFecha(), view.getNombre(),
                    view.getNacimiento(), view.getNombreMadre(), view.getDireccion(),
                    view.getLocalidad(), view.getMunicipio(), view.getCurp(), view.getTelefono()
                );
                deshabilitarComponentes(view.getSexo());
                deshabilitarTextAreas(view.getJtxtaAlergias());
                deshabilitarComponentes(view.getBtnGuardar(), view.getBtnCancelar());
            }
        });

        // Evento del botón Editar
        view.getBtnEditar().addActionListener(e -> {
            habilitarComponentes(
                view.getExpediente(), view.getFecha(), view.getNombre(),
                view.getNacimiento(), view.getNombreMadre(), view.getDireccion(),
                view.getLocalidad(), view.getMunicipio(), view.getCurp(), view.getTelefono()
            );
            habilitarComponentes(view.getSexo());
            habilitarTextAreas(view.getJtxtaAlergias());
            habilitarComponentes(view.getBtnGuardar(), view.getBtnCancelar());
        });

        // Evento del botón Regresar
        view.getBtnRegresar().addActionListener(e -> {
           
            Buscar b = new Buscar();
            b.setVisible(true);
            view.dispose();
        
        });

        /// Evento del botón Historial
        view.getJBtnHistorial().addActionListener(e -> {
            History_ui h = new History_ui();
            h.getLbExpediente().setText(expediente);
            h.getLbName().setText(name);
            new History_Logic(h, idSelec, expediente, name); // Inicializar la lógica con el id del paciente
            h.setVisible(true);
            view.dispose();
        });
    }

    private void deshabilitarComponentes(javax.swing.JComponent... componentes) {
        for (javax.swing.JComponent componente : componentes) {
            componente.setEnabled(false);
        }
    }

    private void habilitarComponentes(javax.swing.JComponent... componentes) {
        for (javax.swing.JComponent componente : componentes) {
            componente.setEnabled(true);
        }
    }

    private void deshabilitarTextAreas(javax.swing.JTextArea... textAreas) {
        for (javax.swing.JTextArea textArea : textAreas) {
            textArea.setEnabled(false);
        }
    }

    private void habilitarTextAreas(javax.swing.JTextArea... textAreas) {
        for (javax.swing.JTextArea textArea : textAreas) {
            textArea.setEnabled(true);
        }
    }
}
