/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.logic;

import com.derkcode.gestor_archivos_clinicos.data.source.DataSource;
import com.derkcode.gestor_archivos_clinicos.ui.profile.Profile_Doctor;
import com.derkcode.gestor_archivos_clinicos.util.Session;
import com.derkcode.gestor_archivos_clinicos.util.Managers.WindowManager;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Profile_Doctor_Logic {

    private Profile_Doctor view;
    private DataSource query = new DataSource();
    private static final String IMAGE_PATH = "src/main/resources/images/logo_doctor.png";

    public Profile_Doctor_Logic(Profile_Doctor view) {
        this.view = view;
        initializeComponents();
        iniListeners();
    }

    private void initializeComponents() {
        // Cargar la imagen existente si existe
        try {
        File imageFile = new File(IMAGE_PATH);
            if (imageFile.exists()) {
                displayImage(imageFile);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "No se pudo cargar logo_doctor.png: " + ex.getMessage());
        }
    }

    private void iniListeners() {
        
        view.getBtnBack().addActionListener(e -> {
            
            WindowManager.closeWindow(Profile_Doctor.class);
            
            
        });

        view.getBtnChargeImage().addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));

            int result = fileChooser.showOpenDialog(view);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    // Convertir y guardar la imagen como PNG
                    BufferedImage originalImage = ImageIO.read(selectedFile);
                    BufferedImage pngImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    pngImage.getGraphics().drawImage(originalImage, 0, 0, null);
                    File outputFile = new File(IMAGE_PATH);
                    ImageIO.write(pngImage, "png", outputFile);

                    // Mostrar la imagen en el JLabel con tamaño 45x45
                    displayImage(outputFile);
                    JOptionPane.showMessageDialog(view, "Imagen guardada como logo_doctor.png");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(view, "Error al procesar la imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        view.getBtnSave().addActionListener(e -> {
            
            if (query.actualizarPerfil(view, Session.getInstance().getIdDoctor()) == true) {
                JOptionPane.showMessageDialog(view, "Informacion actualizada correctamente");
            } else {
                JOptionPane.showMessageDialog(view,"Fallo la actualizacion de información");
            }
            
            /*
            Doctor_model doctor = new Doctor_model();

            // Recolectar datos de los TextField
            doctor.setName(view.getTextFName().getText());
            doctor.setUser(view.getTextCed().getText()); // Usamos cedula como user temporalmente
            doctor.setSpecialty(view.getTextSpeciality().getText());
            try {
                doctor.setPhone_number(Long.parseLong(view.getTextPhone().getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "El número de teléfono debe ser un valor numérico", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            doctor.setCedula(view.getTextCed().getText());
            doctor.setAddress(view.getTextAddress().getText());*/
            
            //JOptionPane.showMessageDialog(view, "Datos recolectados: \n" + doctor.toString(), "Información", JOptionPane.INFORMATION_MESSAGE);
            
        });
    }

    private void displayImage(File imageFile) {
        try {
            BufferedImage img = ImageIO.read(imageFile);
            Image scaledImage = img.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            view.getLabelViewLogo().setIcon(icon);
            view.getLabelViewLogo().setText(""); // Eliminar texto si lo hubiera
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view, "Error al cargar la imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
