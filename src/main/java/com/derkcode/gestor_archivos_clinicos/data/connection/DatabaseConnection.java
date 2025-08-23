/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.data.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    protected Connection connect;
    private Statement st;
    private PreparedStatement pstmt;

    String database = "jdbc:sqlite:database_files.db";

    private DatabaseConnection() {
        reestablishConnection();
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        if (connect == null || isConnectionClosed()) {
            reestablishConnection();
        }
        return connect;
    }

    private boolean isConnectionClosed() {
        try {
            return connect == null || connect.isClosed();
        } catch (SQLException e) {
            System.err.println("Error al verificar el estado de la conexión: " + e.getMessage());
            return true;
        }
    }

    private void reestablishConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection(database);
            if (connect != null) {
                st = connect.createStatement();
                // Habilitar claves foráneas
                try (Statement stmt = connect.createStatement()) {
                    stmt.execute("PRAGMA foreign_keys = ON;");
                }
                System.out.println("Conexión establecida o reestablecida con claves foráneas activadas.");
            }
        } catch (SQLException ex) {
            System.err.println("Error al establecer la conexión: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, "Error en la clase", ex);
        }
    }

    public Statement getStatement() {
        return st;
    }

    public void cerrarConexion() {
        try {
            if (pstmt != null) {
                pstmt.close();
                System.out.println("PreparedStatement cerrado");
            }
        } catch (SQLException ex) {
            System.err.println("Error al cerrar PreparedStatement: " + ex.getMessage());
        }
        try {
            if (st != null) {
                st.close();
                System.out.println("Statement cerrado");
            }
        } catch (SQLException ex) {
            System.err.println("Error al cerrar Statement: " + ex.getMessage());
        }
        try {
            if (connect != null && !connect.isClosed()) {
                connect.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException ex) {
            System.err.println("Error al cerrar la conexión: " + ex.getMessage());
        }
    }
}