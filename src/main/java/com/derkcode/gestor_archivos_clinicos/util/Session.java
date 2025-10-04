/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.util;

import com.derkcode.gestor_archivos_clinicos.data.model.Doctor_model;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Derek
 */
public class Session {
    
    
    
    private static Session instance;
    
    private long id_doctor;
    private String user;
    private String username;
    private String specialty;
    private long phone_number;
    private String cedula;
    private String address;

    public Session(long id_doctor, String user, String username, String specialty, long phone_number, String cedula, String address) {
        this.id_doctor = id_doctor;
        this.user = user;
        this.username = username;
        this.specialty = specialty;
        this.phone_number = phone_number;
        this.cedula = cedula;
        this.address = address;
    }
    
    public static void starSession(long id_doctor, String user, String username,String specialty, long phone_number, String cedula, String address){
        if (instance == null){
            instance = new Session(id_doctor, user, username,specialty, phone_number, cedula, address);
        }
    }
    
    public static Session getInstance() {
        return instance;
    }
    
    public static void closeSession() {
        instance = null;
    }

    public List<Doctor_model> getAll(){
        
        List<Doctor_model> All = new ArrayList<>();
        
        Doctor_model doc = new Doctor_model();
        
        doc.setId_doctor(id_doctor);
        doc.setName(username);
        doc.setSpecialty(specialty);
        doc.setPhone_number(phone_number);
        doc.setCedula(cedula);
        doc.setAddress(address);
        All.add(doc);
        
        return All;
    }
    
    public long getIdDoctor(){return id_doctor; }
    public String getUser() { return user; }
    public String getUsername() {return username; }
    public String getSpeciality() { return specialty; }
    public long getPhoneNumber() { return phone_number; }
    public String getCedula() { return cedula; }
    public String getAddress() { return address; }
    
}
