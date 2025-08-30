/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.ui.login;

import com.derkcode.gestor_archivos_clinicos.data.model.Doctor_model;
import com.derkcode.gestor_archivos_clinicos.ui.menu.Menu;
import java.awt.Image;
import java.net.URL;
import com.derkcode.gestor_archivos_clinicos.data.source.DataSource;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author derk
 */

public class Login extends javax.swing.JFrame {
    private List<Doctor_model> doctor = new ArrayList<>();
    DataSource query = new DataSource();

    private boolean cambioContrasena = false; 

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        SetImageLabel(lblBiblio, getClass().getResource("/images/Biblioteca_logo.png"));
    }

    private void SetImageLabel(JLabel labelName, URL resource) {
        if (resource != null) {
            ImageIcon image = new ImageIcon(resource);
            Icon icon = new ImageIcon(image.getImage().getScaledInstance(labelName.getWidth(), labelName.getHeight(), Image.SCALE_DEFAULT));
            labelName.setIcon(icon);
            this.repaint();
        } else {
            System.err.println("Recurso no encontrado: /images/Biblioteca_logo.png");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabelUsuario = new javax.swing.JLabel();
        jLabelNueva = new javax.swing.JLabel();
        jLabelVieja = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtNuevaContrasena = new javax.swing.JPasswordField();
        txtViejaContrasena = new javax.swing.JPasswordField();
        btnIngresar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lblCambiarContrasena = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblBiblio = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 0));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelUsuario.setFont(new java.awt.Font("Arial", 1, 12)); 
        jLabelUsuario.setForeground(new java.awt.Color(0, 0, 0));
        jLabelUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelUsuario.setText("Usuario");

        jLabelNueva.setFont(new java.awt.Font("Arial", 1, 12)); 
        jLabelNueva.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNueva.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNueva.setText("Contraseña");

        jLabelVieja.setFont(new java.awt.Font("Arial", 1, 12)); 
        jLabelVieja.setForeground(new java.awt.Color(0, 0, 0));
        jLabelVieja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelVieja.setText("");

        txtUsuario.setBackground(new java.awt.Color(255, 255, 255));
        txtUsuario.setFont(new java.awt.Font("Arial", 1, 11)); 
        txtUsuario.setForeground(new java.awt.Color(0, 0, 0));
        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsuario.setPreferredSize(new java.awt.Dimension(150, 25));

        txtNuevaContrasena.setBackground(new java.awt.Color(255, 255, 255));
        txtNuevaContrasena.setFont(new java.awt.Font("Arial", 1, 11)); 
        txtNuevaContrasena.setForeground(new java.awt.Color(0, 0, 0));
        txtNuevaContrasena.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNuevaContrasena.setPreferredSize(new java.awt.Dimension(150, 25));

        txtViejaContrasena.setBackground(new java.awt.Color(255, 255, 255));
        txtViejaContrasena.setFont(new java.awt.Font("Arial", 1, 11)); 
        txtViejaContrasena.setForeground(new java.awt.Color(0, 0, 0));
        txtViejaContrasena.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtViejaContrasena.setPreferredSize(new java.awt.Dimension(150, 25));

        btnIngresar.setBackground(new java.awt.Color(153, 153, 153));
        btnIngresar.setFont(new java.awt.Font("Arial", 1, 11)); 
        btnIngresar.setForeground(new java.awt.Color(0, 0, 0));
        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(153, 153, 153));
        btnSalir.setFont(new java.awt.Font("Arial", 1, 11)); 
        btnSalir.setForeground(new java.awt.Color(0, 0, 0));
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        lblCambiarContrasena.setFont(new java.awt.Font("Arial", 1, 12)); 
        lblCambiarContrasena.setForeground(new java.awt.Color(0, 0, 0));
        lblCambiarContrasena.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCambiarContrasena.setText("Cambiar contraseña");
        lblCambiarContrasena.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCambiarContrasenaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelNueva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNuevaContrasena, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelVieja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtViejaContrasena, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCambiarContrasena, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelNueva)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNuevaContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelVieja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtViejaContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblCambiarContrasena)
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIngresar)
                    .addComponent(btnSalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblBiblio, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBiblio, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    } // </editor-fold>                        

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        if (cambioContrasena) {
            String usuario = txtUsuario.getText();
            String nuevaPass = new String(txtNuevaContrasena.getPassword());
            String viejaPass = new String(txtViejaContrasena.getPassword());

            if (usuario.isEmpty() || nuevaPass.isEmpty() || viejaPass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Rellene todos los campos");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de cambiar la contraseña?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Contraseña cambiada correctamente");
                cambioContrasena = false;
                actualizarInterfaz();
            }
        } else {
            String usuario = txtUsuario.getText();
            String pasw = new String(txtNuevaContrasena.getPassword());

            if (usuario.isEmpty() || pasw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Rellene todos los campos");
                txtUsuario.setText("");
                txtNuevaContrasena.setText("");
            } else { 
                //if (usuario.equals("app") && pasw.equals("2024")) {
                
                //doctor = ;
                
                
                //System.out.println(doctor.get(0).toString());
                // System.out.println(doctor.get(1).getUser());
                
                if (query.extraerInfoDoctor(usuario, pasw).isEmpty()) {
                    
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
                    txtUsuario.setText("");
                    txtNuevaContrasena.setText("");
                
                } else {
                    
                    Menu b = new Menu();
                    b.setVisible(true);
                    this.dispose();
                
                }
            }
        }
    }                                           

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if (cambioContrasena) {
            cambioContrasena = false;
            actualizarInterfaz();
        } else {
            this.dispose();
        }
    }                                        

    private void lblCambiarContrasenaMouseClicked(java.awt.event.MouseEvent evt) {                                                 
        cambioContrasena = true; 
        actualizarInterfaz();
    }                                               

    private void actualizarInterfaz() {
        if (cambioContrasena) {
            jLabelUsuario.setText("Usuario");
            jLabelNueva.setText("Contraseña nueva");
            jLabelVieja.setText("Contraseña vieja");

            btnIngresar.setText("Confirmar");
            btnSalir.setText("Cancelar");

            txtUsuario.setText("");
            txtNuevaContrasena.setText("");
            txtViejaContrasena.setText("");
        } else {
            jLabelUsuario.setText("Usuario");
            jLabelNueva.setText("Contraseña");
            jLabelVieja.setText("");

            btnIngresar.setText("Ingresar");
            btnSalir.setText("Salir");

            txtUsuario.setText("");
            txtNuevaContrasena.setText("");
            txtViejaContrasena.setText("");
        }
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JLabel jLabelNueva;
    private javax.swing.JLabel jLabelVieja;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblBiblio;
    private javax.swing.JLabel lblCambiarContrasena;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JPasswordField txtNuevaContrasena;
    private javax.swing.JPasswordField txtViejaContrasena;
    // End of variables declaration                   
}
