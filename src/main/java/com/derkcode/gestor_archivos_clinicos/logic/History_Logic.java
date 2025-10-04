/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.logic;

import com.derkcode.gestor_archivos_clinicos.data.source.DataSource;
import com.derkcode.gestor_archivos_clinicos.ui.management.*;
import com.derkcode.gestor_archivos_clinicos.data.model.Consulta_Model;
import com.derkcode.gestor_archivos_clinicos.ui.menu.*;
import com.derkcode.gestor_archivos_clinicos.util.Managers.WindowManager;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;

public class History_Logic {
    
    private History_ui view;
    private DataSource query = new DataSource();
    private Long id;
    private String name;
    private String expediente;
    private List<Consulta_Model> consultas;

    public History_Logic(History_ui view, long id, String expediente, String name) 
    {
        this.view = view;
        this.id = id;
        this.name = name;
        this.expediente = expediente;
        initializeComponents();
        iniListeners();
    }

    private void initializeComponents() {
        
        iniTabla();
        view.getLbName().setText(name);
        view.getLbExpediente().setText(expediente);
    
    }   

    private void iniListeners() {
       
        
        
        view.getBtnBack().addActionListener(e -> {
           
            WindowManager.closeWindow(History_ui.class);
            
            WindowManager.showWindow(Buscar.class, (vista) -> {
                new Buscar_Logic(vista);
            });
            
        });
    }

    private void iniTabla() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        model.addColumn("Clave");
        model.addColumn("Fecha de consulta");
        model.addColumn("Acciones");

        consultas = query.extraerConsultas(id); // Guardar la lista
        System.out.println("Consultas recuperadas: " + consultas.size());
        for (Consulta_Model consulta : consultas) {
            System.out.println("Añadiendo consulta - ID: " + consulta.getId_consulta() + ", Fecha: " + consulta.getFecha());
            model.addRow(new Object[]{
                consulta.getId_consulta(),
                consulta.getFecha(),
                "Ver Consulta"
            });
        }

        view.getTable().setModel(model);
        customizeTable();
        view.getTable().repaint();
        view.getTable().revalidate();
    }

    private void customizeTable() {
        JTable table = view.getTable();
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(2).setCellRenderer(new ButtonRenderer());
        columnModel.getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private void openConsultaView(Long idConsulta, int row) {
        if (row >= 0 && row < consultas.size()) {
            Consulta_Model consulta = consultas.get(row);
            WindowManager.showWindow(Consulta.class, (vista) -> {
                vista.setIdPaciente(id);
                vista.setExpediente(expediente);
                vista.setModo("ver_consulta");
                new Consulta_Logic(vista, id, expediente, "ver_consulta", consulta);
            });
            //WindowManager.hideWindow(History_ui.class); // Oculta History_ui
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Ver Consulta" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private Long idConsulta;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped(); // Finalizar la edición antes de abrir la vista
                    openConsultaView(idConsulta, row);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            idConsulta = (Long) table.getValueAt(row, 0);
            button.setText((value == null) ? "Ver Consulta" : value.toString());
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Ver Consulta";
        }
    }
}