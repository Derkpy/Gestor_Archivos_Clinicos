/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.data.source;
import com.derkcode.gestor_archivos_clinicos.data.DataDao;
import com.derkcode.gestor_archivos_clinicos.data.model.Paciente;
import com.derkcode.gestor_archivos_clinicos.data.connection.DatabaseConnection;
import com.derkcode.gestor_archivos_clinicos.data.model.Consulta_Model;
import com.derkcode.gestor_archivos_clinicos.data.model.Doctor_model;
import com.derkcode.gestor_archivos_clinicos.data.model.PacienteInsertado;
import com.derkcode.gestor_archivos_clinicos.ui.New_File;
import com.derkcode.gestor_archivos_clinicos.ui.Consulta;
import com.derkcode.gestor_archivos_clinicos.util.Session;
import com.derkcode.gestor_archivos_clinicos.ui.Visualizar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Derek
 */
public class DataSource implements DataDao {
    
    @Override
    public ArrayList<Paciente> consultarPacientes() {
        ArrayList<Paciente> lista = new ArrayList<>();
        Paciente paciente;

        String sql = "SELECT id_paciente, expediente, curp, nombre, localidad FROM pacientes ORDER BY expediente DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                paciente = new Paciente();
                paciente.setId_paciente(Integer.parseInt(rs.getString("id_paciente")));
                paciente.setExpediente(rs.getString("expediente"));
                paciente.setCurp(rs.getString("curp"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setLocalidad(rs.getString("localidad"));
                lista.add(paciente);
            }
            System.out.println("Consultados " + lista.size() + " pacientes.");
        } catch (SQLException ex) {
            System.err.println("Error al consultar los pacientes para la tabla: " + ex.getMessage());
        }
        return lista;
    }

    @Override
    public String consultarUEx() {
        
        String expediente = "";
        String sql = "SELECT expediente FROM pacientes ORDER BY id_paciente DESC LIMIT 1;";
        
        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                expediente = rs.getString("expediente");
                // Extraer el número del expediente después del guion o barra
                String[] partes = expediente.split("[-/]");
                if (partes.length > 1) {
                    expediente = partes[1].trim(); // Tomar la segunda parte después del guion o barra
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error al consultar el último expediente de la tabla pacientes");
            }
        return expediente;
    
    }

    @Override
    public PacienteInsertado insertarPacientes(New_File s) {
        String sql = "INSERT INTO pacientes(curp, expediente, fecha, nombre, fecha_nacimiento, sexo, " +
                     "telefono, familiar, domicilio, localidad, municipio, alergias) " +
                     "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Validar que los campos no estén vacíos
            if (s.curp().isEmpty() || s.expediente().isEmpty() || s.fecha().isEmpty() || 
                s.nombre().isEmpty() || s.fecha_nac().isEmpty() || s.sexo().isEmpty() || 
                s.telefono().isEmpty() || s.domicilio().isEmpty() || 
                s.localidad().isEmpty() || s.municipio().isEmpty() || s.alergias().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return null;
            }

            // Establecer parámetros
            pstmt.setString(1, s.curp());
            pstmt.setString(2, s.expediente());
            pstmt.setString(3, s.fecha());
            pstmt.setString(4, s.nombre());
            pstmt.setString(5, s.fecha_nac());
            pstmt.setString(6, s.sexo());
            pstmt.setString(7, s.telefono());
            pstmt.setString(8, s.familiar());
            pstmt.setString(9, s.domicilio());
            pstmt.setString(10, s.localidad());
            pstmt.setString(11, s.municipio());
            pstmt.setString(12, s.alergias());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La inserción del paciente falló, no se afectaron filas.");
            }

            // Obtener el ID generado y el expediente
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long idPaciente = generatedKeys.getLong(1); // ID generado
                    String expediente = s.expediente(); // Expediente proporcionado
                    return new PacienteInsertado(idPaciente, expediente);
                } else {
                    throw new SQLException("No se pudo obtener el ID del paciente insertado.");
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al ingresar un paciente: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error al agregar el paciente: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean expedienteExiste(String expediente) {
        try {
            // Eliminar los caracteres "-" y "/" del expediente
            expediente = expediente.replace("-", "").replace("/", "");

            PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM pacientes WHERE REPLACE(expediente, '-', '') = ? OR REPLACE(expediente, '/', '') = ?");
            pstmt.setString(1, expediente);
            pstmt.setString(2, expediente);

            ResultSet rs = pstmt.executeQuery();
        
            // Si el resultado de la consulta es mayor que 0, significa que el expediente ya existe
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
       
            System.out.println(e.getMessage());
    
        }
        return false;
    
    }

    @Override
    public void insertarConsulta(Long idPaciente, String enfermedad, String sintomas, String tratamiento, String sugerencias, String fecha) {
        String sql = "INSERT INTO consultas(id_paciente, enfermedad, sintomas, tratamiento, sugerencias, fecha) " +
                     "VALUES(?,?,?,?,?,?)";

        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            // Establecer parámetros
            pstmt.setLong(1, idPaciente);
            pstmt.setString(2, enfermedad);
            pstmt.setString(3, sintomas);
            pstmt.setString(4, tratamiento);
            pstmt.setString(5, sugerencias);
            pstmt.setString(6, fecha);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La inserción de la consulta falló, no se afectaron filas.");
            }
            JOptionPane.showMessageDialog(null, "Consulta guardada con éxito");
        } catch (SQLException ex) {
            System.err.println("Error al ingresar la consulta: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error al agregar la consulta: " + ex.getMessage());
        }
    }

    @Override
    public List<Paciente> buscarPacientes(String buscar) {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT id_paciente, expediente, curp, nombre, localidad FROM pacientes WHERE expediente LIKE ? OR curp LIKE ? OR nombre LIKE ?";

        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            String likePattern = "%" + buscar + "%";
            pstmt.setString(1, likePattern);
            pstmt.setString(2, likePattern);
            pstmt.setString(3, likePattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Paciente paciente = new Paciente();
                    paciente.setId_paciente(rs.getInt("id_paciente"));
                    paciente.setExpediente(rs.getString("expediente"));
                    paciente.setCurp(rs.getString("curp"));
                    paciente.setNombre(rs.getString("nombre"));
                    paciente.setLocalidad(rs.getString("localidad"));
                    lista.add(paciente);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar los pacientes para la tabla: " + ex.getMessage());
        }
        return lista;
    }

    @Override
    public Long eliminarRegistro_Paciente(String expediente) {
        String sql = "DELETE FROM pacientes WHERE expediente = ?";

        try (PreparedStatement pstmt = DatabaseConnection.getInstance()
                .getConnection().prepareStatement(sql)) {
            pstmt.setString(1, expediente);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "Registro eliminado de paciente");
                // Si necesitas devolver algo, podrías devolver null o el expediente como String convertido a Long si es numérico
                // Por ahora, devolvemos null ya que el retorno Long parece ser un error en el diseño original
                return null; // O ajusta según el uso real del retorno
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el paciente para eliminar");
                return null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el paciente: " + ex.getMessage());
            System.err.println("Error al eliminar un paciente: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<Paciente> updatePaciente(String update, Visualizar s) {
        String sql = "UPDATE pacientes set "
                
                + "expediente=?, "
                + "fecha=?, "
                + "nombre=?, "
                + "fecha_nacimiento=?, "
                + "sexo=?, domicilio=?, "
                + "localidad=?, municipio=?, "
                + "familiar=?, "
                + "curp=?, "
                + "telefono=?, "
                + "alergias=? "
                
                + "WHERE id_paciente = '" + update + "'";

        try {
            PreparedStatement pstmt = DatabaseConnection.getInstance().
                    getConnection().prepareStatement(sql);

            pstmt.setString(1, s.expediente());
            pstmt.setString(2, s.fecha());
            pstmt.setString(3, s.nombre());
            pstmt.setString(4, s.fecha_nac());
            pstmt.setString(5, s.sexo());
            pstmt.setString(6, s.domicilio());
            pstmt.setString(7, s.localidad());
            pstmt.setString(8, s.municipio());
            pstmt.setString(9, s.familiar());
            pstmt.setString(10, s.curp());
            pstmt.setString(11, s.telefono());
            pstmt.setString(12, s.alergias());
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Paciente actualizado con exito");
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("Error al actualizar un paciente");
        }
        return null;
    }

    @Override
    public ArrayList<Paciente> verPaciente(String expediente) {
        
        ArrayList<Paciente> listver = new ArrayList<>();

        String sql = "SELECT expediente, curp, nombre, fecha, fecha_nacimiento, sexo, telefono, familiar, domicilio, localidad, municipio, alergias " +
                     "FROM pacientes WHERE expediente = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, expediente);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Paciente p = new Paciente();
                    p.setExpediente(rs.getString("expediente"));
                    p.setCurp(rs.getString("curp"));
                    p.setNombre(rs.getString("nombre"));
                    p.setFecha(rs.getString("fecha"));
                    p.setFecha_nacimiento(rs.getString("fecha_nacimiento")); // Ajustado a setFechaNacimiento
                    p.setSexo(rs.getString("sexo"));
                    p.setTelefono(rs.getString("telefono"));
                    p.setFamiliar(rs.getString("familiar"));
                    p.setDomicilio(rs.getString("domicilio"));
                    p.setLocalidad(rs.getString("localidad"));
                    p.setMunicipio(rs.getString("municipio"));
                    p.setAlergias(rs.getString("alergias")); 
                    listver.add(p);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar la información del paciente: " + ex.getMessage());
        }
        return listver;
        }

    @Override
    public List<Consulta_Model> extraerConsultas(Long id) {
        List<Consulta_Model> list = new ArrayList<>();
        String sql = "SELECT * FROM consultas WHERE id_paciente = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id); // Usar setLong en lugar de setString para un campo INTEGER
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Consulta_Model c = new Consulta_Model();
                    c.setId_consulta(rs.getLong("id_consulta"));
                    c.setId_paciente(rs.getLong("id_paciente"));
                    c.setEnfermedad(rs.getString("enfermedad"));
                    c.setSintomas(rs.getString("sintomas"));
                    c.setTratamiento(rs.getString("tratamiento"));
                    c.setSugerencias(rs.getString("sugerencias"));
                    c.setFecha(rs.getString("fecha"));
                    list.add(c);
                }
                System.out.println("Consultas encontradas para id_paciente " + id + ": " + list.size());
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar: " + ex.getMessage());
        }
        return list;
    }

    @Override
    public List<Doctor_model> extraerInfoDoctor(String user, String password) {
        
        List<Doctor_model> list = new ArrayList<>() ;
        
        String sql = "SELECT name, specialty, phone_number, cedula, address FROM doctores WHERE user = '"+user+"' AND "+"password = '"+password +"';";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            try (ResultSet rs = pstmt.executeQuery()){
                while (rs.next()){
                    Doctor_model doc = new Doctor_model();
                    doc.setName(rs.getString("name"));
                    doc.setSpecialty(rs.getString("specialty"));
                    doc.setPhone_number(rs.getLong("phone_number"));
                    doc.setCedula(rs.getLong("cedula"));
                    doc.setAddress(rs.getString("address"));
                    list.add(doc);
                    
                }
            }
            
            for (Doctor_model dato : list){
                
                Session.starSession(user, dato.getName(), dato.getSpecialty(), dato.getPhone_number(), dato.getCedula(), dato.getAddress());
                
                
            }
            
        }catch (SQLException ex){
            System.err.println("Error al consultar: " + ex.getMessage());
        }
        
        return list;
    }
    
    
    
}
