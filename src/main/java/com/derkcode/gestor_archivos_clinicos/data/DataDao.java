/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.data;

import com.derkcode.gestor_archivos_clinicos.data.model.Consulta_Model;
import com.derkcode.gestor_archivos_clinicos.data.model.Paciente;
import com.derkcode.gestor_archivos_clinicos.data.model.Doctor_model;
import com.derkcode.gestor_archivos_clinicos.data.model.PacienteInsertado;
import com.derkcode.gestor_archivos_clinicos.ui.management.New_File;
import com.derkcode.gestor_archivos_clinicos.ui.management.Visualizar;
import com.derkcode.gestor_archivos_clinicos.ui.profile.Profile_Doctor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Derek
 */
public interface DataDao {
    
    public ArrayList<Paciente> consultarPacientes();
    
    //Funcion para extraer el id
    public String consultarUEx();
    
    //Ingresar nuevo expediente
    public PacienteInsertado insertarPacientes(New_File s);
    
    // Método para verificar si un expediente ya existe en la base de datos
    public boolean expedienteExiste(String expediente);
    
    public void insertarConsulta(Long idPaciente, String enfermedad, String sintomas, String tratamiento, String sugerencias, String fecha);

    //Buscar paciente
    public List<Paciente> buscarPacientes(String buscar);
    
    public Long eliminarRegistro_Paciente(String expediente);
    
    public ArrayList<Paciente> updatePaciente(String update, Visualizar s);
    
    public ArrayList<Paciente> verPaciente(String expediente);
    
    public List<Consulta_Model> extraerConsultas(Long id);
    
    public List<Doctor_model> extraerSesionDoctor(String user, String password);
    
    public boolean actualizarPerfil(Profile_Doctor s, long id_doctor);
    
    public Doctor_model actualizarContraseña(Profile_Doctor s);
    
}
