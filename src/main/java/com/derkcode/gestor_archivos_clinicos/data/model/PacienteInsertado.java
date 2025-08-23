/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.data.model;

//Autor Derek


public class PacienteInsertado {
    private Long idPaciente;
    private String expediente;

    public PacienteInsertado(Long idPaciente, String expediente) {
        this.idPaciente = idPaciente;
        this.expediente = expediente;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public String getExpediente() {
        return expediente;
    }
}
