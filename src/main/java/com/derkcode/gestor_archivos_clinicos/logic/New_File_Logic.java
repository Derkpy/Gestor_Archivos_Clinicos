/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.logic;

import com.derkcode.gestor_archivos_clinicos.data.source.DataSource;
import com.derkcode.gestor_archivos_clinicos.ui.Management.New_File;
import java.util.ArrayList;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import com.derkcode.gestor_archivos_clinicos.data.model.PacienteInsertado;
import com.derkcode.gestor_archivos_clinicos.ui.menu.Menu;
import java.awt.Window;
import com.derkcode.gestor_archivos_clinicos.ui.Management.Consulta;

/**
 *
 * @author Derek
 */
public class New_File_Logic {
    private New_File nf_view;
    public java.util.List<String> info = new ArrayList<>();
    public ArrayList<String> insert_data = new ArrayList<>();
    private DataSource query = new DataSource();

    public New_File_Logic(New_File nf_view) {
        this.nf_view = nf_view;
        initializeComponents();
        iniListeners();
    }

    private void initializeComponents() {
        automatic();
        habilitarSegunCheckBox(nf_view.getNombre_madre(), nf_view.getjCheckBox1());
    }

    private void iniListeners() {
        nf_view.getjCheckBox1().addActionListener(e -> {
            habilitarSegunCheckBox(nf_view.getNombre_madre(), nf_view.getjCheckBox1());
        });

        nf_view.getCancelar().addActionListener(e -> {
            Window[] windows = Window.getWindows();
            for (Window w : windows) {
                if (w instanceof Menu && !w.isVisible()) {
                    w.setVisible(true);
                    break;
                }
            }
            nf_view.dispose();
        });

        nf_view.getGuardar().addActionListener(e -> {
            String rep = nf_view.getExpediente().getText();
            if (query.expedienteExiste(rep)) {
                JOptionPane.showMessageDialog(null, "EXPEDIENTE DUPLICADO, INGRESE UNO DIFERENTE");
            } else {
                PacienteInsertado paciente = query.insertarPacientes(nf_view);
                if (paciente != null) {
                    limpiar();

                    UIManager.put("OptionPane.yesButtonText", "Sí");
                    UIManager.put("OptionPane.noButtonText", "No");
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea generar una consulta al paciente?", 
                            "Confirmación", JOptionPane.YES_NO_OPTION);

                    if (respuesta == JOptionPane.YES_OPTION) {
                        Long idPaciente = paciente.getIdPaciente();
                        String expediente = paciente.getExpediente();
                        Consulta consultaView = new Consulta(idPaciente, expediente, "nueva_consulta");
                        consultaView.setVisible(true);
                        new Consulta_Logic(consultaView);
                        nf_view.setVisible(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar el paciente");
                }
            }
        });
    }

    private void habilitarSegunCheckBox(javax.swing.JTextField textField, javax.swing.JCheckBox checkBox) {
        textField.setEnabled(checkBox.isSelected());
    }

    private void automatic() {
        String id = extraer_Id();
        LocalDate fechaActual = LocalDate.now();
        String fecha = fechaActual.toString();
        int year = fechaActual.getYear();
        int y = year % 100;
        String year_exp = String.valueOf(y);
        nf_view.getExpediente().setText(year_exp + "-" + id);
        nf_view.getFecha().setText(fecha);
    }

    private String extraer_Id() {
        int convert = 0;
        String extraido1 = query.consultarUEx();
        if (extraido1.isEmpty()) {
            convert = 0;
            System.out.println("ESTA VACIO");
        } else {
            convert = Integer.valueOf(extraido1) + 1;
        }
        System.out.println(convert);
        String ex = String.valueOf(convert);
        System.out.println("EL CONVERTIDO CORRECTO ES: " + ex);
        int x = ex.length();
        String digits = String.valueOf(x);
        System.out.println(x);
        String id = "";
        switch (digits) {
            case "1":
                id = "0000" + ex;
                break;
            case "2":
                id = "000" + ex;
                break;
            case "3":
                id = "00" + ex;
                break;
            case "4":
                id = "0" + ex;
                break;
            case "5":
                id = "" + ex;
                break;
            default:
                throw new AssertionError();
        }
        System.out.println(id);
        return id;
    }

    private void extraer() {
        insert_data.clear();
        String expedienteTexto = nf_view.getExpediente().getText();
        String[] partes = expedienteTexto.split("[-/]");
        String numeroParte = partes[1].trim();
        int numero = Integer.parseInt(numeroParte) + 1;
        String nuevoNumero = String.format("%05d", numero);
        String nuevoExpediente = partes[0] + "-" + nuevoNumero;
        insert_data.add(nuevoExpediente);
        insert_data.add(nf_view.getFecha().getText());
    }

    private void extraer1() {
        String a = nf_view.getExpediente().getText();
        int e = Integer.parseInt(a) + 1;
        String b = String.valueOf(e);
        System.out.println(b);
        insert_data.add(b);
        insert_data.add(nf_view.getFecha().getText());
    }

    private void insertar() {
        nf_view.getExpediente().setText(insert_data.get(0));
        System.out.println(insert_data.get(0));
        nf_view.getFecha().setText(insert_data.get(1));
    }

    private static boolean validar(String valor1, String valor2) {
        try {
            Integer.parseInt(valor2, 11);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean verificar() {
        añadir_lista();
        for (int i = 0; i < info.size(); i++) {
            String valor = info.get(i);
            if (valor.equals("x")) {
                break;
            } else {
                return true;
            }
        }
        return false;
    }

    private void limpiar() {
        nf_view.getCurp().setText("");
        nf_view.getExpediente().setText("");
        nf_view.getFecha().setText("");
        nf_view.getNombre().setText("");
        nf_view.getNacimiento().setText("");
        nf_view.getTelefono().setText("");
        nf_view.getNombre_madre().setText("");
        nf_view.getDireccion().setText("");
        nf_view.getLocalidad().setText("");
        nf_view.getMunicipio().setText("");
        nf_view.getAlergias().setText("");
    }

    private void añadir_lista() {
        info.clear();
        info.add(nf_view.getCurp().getText());
        info.add(nf_view.getExpediente().getText());
        info.add(nf_view.getFecha().getText());
        info.add(nf_view.getNombre().getText());
        info.add(nf_view.getNacimiento().getText());
        info.add(nf_view.getSexo().getSelectedItem().toString());
        info.add(nf_view.getTelefono().getText());
        info.add(nf_view.getNombre_madre().getText());
        info.add(nf_view.getDireccion().getText());
        info.add(nf_view.getLocalidad().getText());
    }
}