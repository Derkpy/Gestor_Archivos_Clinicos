package com.derkcode.gestor_archivos_clinicos.logic;

import com.derkcode.gestor_archivos_clinicos.data.source.DataSource;
import com.derkcode.gestor_archivos_clinicos.data.model.Paciente;
import com.derkcode.gestor_archivos_clinicos.ui.menu.Buscar;
import com.derkcode.gestor_archivos_clinicos.ui.management.History_ui;
import com.derkcode.gestor_archivos_clinicos.ui.management.Consulta;
import com.derkcode.gestor_archivos_clinicos.ui.menu.Menu;
import com.derkcode.gestor_archivos_clinicos.ui.management.Visualizar;
import com.derkcode.gestor_archivos_clinicos.util.Managers.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class Buscar_Logic {
    private Buscar view;
    private DataSource dataSource;
    private List<String> buscador = new ArrayList<>();

    public Buscar_Logic(Buscar view) {
        this.view = view;
        this.dataSource = new DataSource();
        initializeComponents();
        iniListeners();
    }

    private void initializeComponents() {
        view.transparencia();
        mostrarIni();
    }

    private void iniListeners() {
        
        closeListeners.registerComponent(view.getBtnBuscar());
        closeListeners.registerComponent(view.getBtnConsulta());
        closeListeners.registerComponent(view.getBtnHistorial());
        closeListeners.registerComponent(view.getBtnVer());
        closeListeners.registerComponent(view.getDelete());
        closeListeners.registerComponent(view.getRegresar());
        
        closeListeners.clearAllListeners();
        
        view.getBtnBuscar().addActionListener(e -> {
            
            buscador.clear();
            String buscar = view.getTxtBuscar().getText().trim();
            buscador.add(buscar);
            System.out.println("Busqueda con: "+buscar);
            mostrarBusqueda(buscar);
            view.getTxtBuscar().setText("");
        });

        // Evento del botón Ver (corregido con depuración)
        view.getBtnVer().addActionListener(e -> {
            
            
            List<Paciente> info = new ArrayList<>();
            if (buscador.isEmpty()) {
                info = dataSource.consultarPacientes();
                System.out.println("OPCION 1");
            } else {
                info = dataSource.buscarPacientes(buscador.get(0));
                System.out.println("OPCION 2");
                System.out.println(buscador.get(0));
            }

            if (view.Tabla_Pacientes.getSelectedRow() > -1 && !info.isEmpty()) {
                Paciente p = info.get(view.Tabla_Pacientes.getSelectedRow());

                String expediente = p.getExpediente();
                long id = p.getId_paciente();
                String name = p.getNombre();

                // Cerramos si había una ventana Visualizar abierta
                WindowManager.closeWindow(Visualizar.class);

                try {
                    // Abrimos la nueva ventana usando el initializer
                    WindowManager.showWindow(Visualizar.class, v -> {
                        // Cargar y asignar datos antes de mostrar la ventana
                        new Visualizar_Logic(v, id, expediente, name); 
                        ArrayList<Paciente> datosPacientes = dataSource.verPaciente(expediente);
                        if (!datosPacientes.isEmpty()) {
                            Paciente datosPaciente = datosPacientes.get(0);
                            v.getExpediente().setText(datosPaciente.getExpediente());
                            v.getFecha().setText(datosPaciente.getFecha());
                            v.getNombre().setText(datosPaciente.getNombre());
                            v.getNacimiento().setText(datosPaciente.getFecha_nacimiento());
                            v.getSexo().setSelectedItem(datosPaciente.getSexo());
                            v.getDireccion().setText(datosPaciente.getDomicilio());
                            v.getLocalidad().setText(datosPaciente.getLocalidad());
                            v.getMunicipio().setText(datosPaciente.getMunicipio());
                            v.getNombreMadre().setText(datosPaciente.getFamiliar());
                            v.getCurp().setText(datosPaciente.getCurp());
                            v.getTelefono().setText(datosPaciente.getTelefono());
                            v.getJtxtaAlergias().setText(datosPaciente.getAlergias());
                        } else {
                            System.err.println("No se encontraron datos para el expediente: " + expediente);
                        }
                    });

                    WindowManager.hideWindow(Buscar.class);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Error al abrir Visualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un paciente para continuar");
            }
        });
        
        view.getBtnHistorial().addActionListener(e->{
            int selecedRow = view.getTablaPacientes().getSelectedRow();
            if (selecedRow > -1) {
                Paciente p = getPacienteSeleccionado(selecedRow);
                if(p != null){
                    History_ui h = new History_ui();
                    h.getLbExpediente().setText(p.getExpediente());
                    h.getLbName().setText(p.getNombre());
                    
                    WindowManager.showWindow(History_ui.class, (vista) -> {
                    new History_Logic(vista, p.getId_paciente(), p.getExpediente(), p.getNombre());
                    });
                }
            }else {
                JOptionPane.showMessageDialog(null, "Seleccione un paciente para continuar");
            }
        });

        // Evento del botón Regresar
        view.getRegresar().addActionListener(e -> {
            
            WindowManager.hideWindow(Buscar.class);
            
            WindowManager.showWindow(Menu.class);
            
        });
        
        view.getBtnConsulta().addActionListener(e -> {
            List<Paciente> info = new ArrayList<>();
            if (buscador.isEmpty()) {
                info = dataSource.consultarPacientes();
                System.out.println("OPCION 1");
            } else {
                info = dataSource.buscarPacientes(buscador.get(0));
                System.out.println("OPCION 2");
                System.out.println(buscador.get(0));
            }

            if (view.Tabla_Pacientes.getSelectedRow() > -1 && !info.isEmpty()) {
                Paciente p = info.get(view.Tabla_Pacientes.getSelectedRow());
                Long idPaciente = p.getId_paciente();
                String expediente = p.getExpediente();

                WindowManager.showWindow(Consulta.class, (vista) -> {
                    vista.setIdPaciente(idPaciente);
                    vista.setExpediente(expediente);
                    vista.setModo("nueva_consulta");
                    new Consulta_Logic(vista, idPaciente, expediente, "nueva_consulta", null);
                });
                //WindowManager.hideWindow(Buscar.class); // Oculta Buscar
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un paciente para continuar");
            }
        });
            

        // Evento del botón Delete
        view.getDelete().addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este expediente?", "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                int selectedRow = view.getTablaPacientes().getSelectedRow();
                System.out.println("DIJISTE QUE SI");
                if (selectedRow > -1) {
                    Paciente paciente = getPacienteSeleccionado(selectedRow);
                    System.out.println("SI ESTAS SELECCIONANDO UN PACIENTE");
                    System.out.println(paciente);
                    if (paciente != null) {
                        String id = String.valueOf(paciente.getExpediente());
                        System.out.println(id);
                        dataSource.eliminarRegistro_Paciente(id);
                        mostrarIni();
                    } else {
                        System.out.println("NO SIRVE LA FUNCION");
                    }
                }
                else {
                    System.out.println("NO SIRVE");
                }
            }else{
                System.out.println("DIJISTE QUE NO");
            } 
        });
    }
    
    private Paciente getPacienteSeleccionado(int selectedRow) {
        List<Paciente> pacientes = buscador.isEmpty() ? dataSource.consultarPacientes() : dataSource.buscarPacientes(buscador.get(0));
        if (!pacientes.isEmpty() && selectedRow < pacientes.size()) {
            return pacientes.get(selectedRow);
        }
        return null;
    }

    private void mostrarIni() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Expediente");
        model.addColumn("CURP");
        model.addColumn("Nombre");
        model.addColumn("Localidad");

        List<Paciente> pacientes = dataSource.consultarPacientes();
        for (Paciente paciente : pacientes) {
            model.addRow(new Object[]{
                paciente.getExpediente(),
                paciente.getCurp(),
                paciente.getNombre(),
                paciente.getLocalidad(),
           });
        }
        view.getTablaPacientes().setModel(model);
    }

    private void mostrarBusqueda(String buscar) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Expediente");
        model.addColumn("CURP");
        model.addColumn("Nombre");
        model.addColumn("Localidad");

        List<Paciente> pacientes = dataSource.buscarPacientes(buscar);
        for (Paciente paciente : pacientes) {
            model.addRow(new Object[]{
                paciente.getExpediente(),
                paciente.getCurp(),
                paciente.getNombre(),
                paciente.getLocalidad(),
            });
        }
        view.getTablaPacientes().setModel(model);
    }
}
