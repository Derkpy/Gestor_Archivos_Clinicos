package com.derkcode.gestor_archivos_clinicos.logic;

import com.derkcode.gestor_archivos_clinicos.data.source.DataSource;
import com.derkcode.gestor_archivos_clinicos.data.model.Paciente;
import com.derkcode.gestor_archivos_clinicos.ui.Buscar;
import com.derkcode.gestor_archivos_clinicos.ui.Consulta;
import com.derkcode.gestor_archivos_clinicos.ui.History_ui;
import com.derkcode.gestor_archivos_clinicos.ui.Menu;
import com.derkcode.gestor_archivos_clinicos.ui.Visualizar;
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
        // Evento del botón Buscar
        view.getBtnBuscar().addActionListener(e -> {
            buscador.clear();
            String buscar = view.getTxtBuscar().getText();
            buscador.add(buscar);
            mostrarBusqueda(buscar);
            view.getTxtBuscar().setText("");
        });

        // Evento del botón Ver (corregido con depuración)
        view.getBtnVer().addActionListener(e -> {
            List<Paciente> info = new ArrayList<Paciente>();
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

                Visualizar visualizar = new Visualizar(id, expediente, name);

                // Obtener y llenar los datos del paciente
                ArrayList<Paciente> datosPacientes = dataSource.verPaciente(expediente);
                if (!datosPacientes.isEmpty()) {
                    Paciente datosPaciente = datosPacientes.get(0); // Tomamos el primer paciente
                    visualizar.expediente.setText(datosPaciente.getExpediente());
                    visualizar.Fecha.setText(datosPaciente.getFecha());
                    visualizar.Nombre.setText(datosPaciente.getNombre());
                    visualizar.Nacimiento.setText(datosPaciente.getFecha_nacimiento()); // Ajustado a getFechaNacimiento
                    visualizar.Sexo.setSelectedItem(datosPaciente.getSexo());
                    visualizar.Direccion.setText(datosPaciente.getDomicilio());
                    visualizar.Localidad.setText(datosPaciente.getLocalidad());
                    visualizar.Municipio.setText(datosPaciente.getMunicipio());
                    visualizar.Nombre_madre.setText(datosPaciente.getFamiliar());
                    visualizar.Curp.setText(datosPaciente.getCurp());
                    visualizar.Telefono.setText(datosPaciente.getTelefono());
                    visualizar.getJtxtaAlergias().setText(datosPaciente.getAlergias());
                } else {
                    System.err.println("No se encontraron datos para el expediente: " + expediente);
                }
                
                visualizar.setVisible(true);
                view.dispose();
                
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un paciente para continuar");
            }
        });

        // Evento del botón Prestamo (nueva funcionalidad para abrir Consulta)
        view.getBtnConsulta().addActionListener(e -> {
            int selectedRow = view.getTablaPacientes().getSelectedRow();
            if (selectedRow > -1) {
                Paciente paciente = getPacienteSeleccionado(selectedRow);
                if (paciente != null) {
                    Consulta consultaView = new Consulta(paciente.getId_paciente(), paciente.getExpediente());
                    consultaView.setVisible(true);
                    new Consulta_Logic(consultaView); // Asumiendo que tienes una clase Consulta_Logic
                    view.setVisible(false);
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
                    new History_Logic(h, p.getId_paciente(), p.getExpediente(), p.getNombre()); // Inicializar la lógica con el id del paciente
                    h.setVisible(true);
                    view.dispose();
                }
            }else {
                JOptionPane.showMessageDialog(null, "Seleccione un paciente para continuar");
            }
        });

        // Evento del botón Regresar
        view.getRegresar().addActionListener(e -> {
            Menu menu = new Menu();
            menu.setVisible(true);
            view.dispose();
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
