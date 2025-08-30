/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.util.PDFsGenerator;

import com.derkcode.gestor_archivos_clinicos.data.model.Doctor_model;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.File; 
import java.io.IOException;
import java.time.LocalDate;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import com.derkcode.gestor_archivos_clinicos.util.Session;
import com.derkcode.gestor_archivos_clinicos.util.Session;
import java.awt.Color;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.stream.ImageInputStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1CFont;


public class PdfGeneratorReceta {
    
    private static InputStream imageStream0;
    private static InputStream imageStream1;

    public static void generateReceta(
            String expediente, 
            String enfermedad, 
            String sintomas, 
            String tratamiento, 
            String sugerencias 
            ) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar receta como...");
        fileChooser.setSelectedFile(new File("receta_" + expediente + "_" + LocalDate.now().toString().replace(":", "-") + ".pdf"));

        
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String dest = fileToSave.getAbsolutePath();
            if (!dest.toLowerCase().endsWith(".pdf")) {
                dest += ".pdf";
            }

            try (PDDocument document = new PDDocument()) {
                PDRectangle halfLetter = new PDRectangle(612, 396);
                PDPage page = new PDPage(halfLetter);
                document.addPage(page);
                

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    
                    
                    List<String> texts = new ArrayList<>();
                    
                    //(x,y) donde x es de izquierda a derecha e y es de abajo hacia arriba
                    PDFont font = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
                    int fontSize = 13;
                    
                    float textWidth = font.getStringWidth("Dr. "+Session.getInstance().getUsername()) / 1000 * fontSize;
                    float textWidth1 = font.getStringWidth(Session.getInstance().getSpeciality()) / 1000 * fontSize;
                    float textWidth2 = font.getStringWidth("CED. PROF. "+Session.getInstance().getCedula()) / 1000 * fontSize;
                    float textWidth3 = font.getStringWidth("Numero de contacto: "+Session.getInstance().getPhoneNumber()) / 1000 * fontSize;
                    float textWidth4 = font.getStringWidth("HORARIO: ") / 1000 * fontSize;
                    float textWidth5 = font.getStringWidth("Paciente: " + Session.getInstance().getUsername()) / 1000 * fontSize;
                    float textWidth6 = font.getStringWidth("Fecha: " + LocalDate.now().toString()) / 1000 * fontSize;
                    float textWidth7 = font.getStringWidth("Dirección: " + Session.getInstance().getAddress())  / 1000 * fontSize;
                    
                    float pageWidth = page.getMediaBox().getWidth();
                    float startRec = (pageWidth - 420) / 2;
                    
                    float startX0 = (pageWidth - textWidth) / 2;
                    float startX1 = (pageWidth - textWidth1) / 2;
                    float startX2 = (pageWidth - textWidth2) / 2;
                    float startX3 = (pageWidth - textWidth3) / 2;
                    float startX4 = (pageWidth - textWidth4) / 2;
                    float startX5 = (pageWidth - textWidth5) / 2;
                    float startX6 = (pageWidth - textWidth6) / 2;
                    float startX7 = (pageWidth - textWidth7) / 2;
                    
                    //Nombre del doctor
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 13);
                    contentStream.newLineAtOffset(startX0, 370);
                    contentStream.showText("Dr. "+Session.getInstance().getUsername());
                    contentStream.endText(); 
                    
                    //Especialidad del doctor
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 13);
                    contentStream.newLineAtOffset(startX1, 355);
                    contentStream.showText(Session.getInstance().getSpeciality());
                    contentStream.endText(); 
                    
                    //Cedula profesional del doctor
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 13);
                    contentStream.newLineAtOffset(startX2, 340);
                    contentStream.showText("CED. PROF. "+Session.getInstance().getCedula());
                    contentStream.endText(); 
                    
                    //Numero de contacto
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 13);
                    contentStream.newLineAtOffset(startX3, 325);
                    contentStream.showText("Numero de contacto: "+Session.getInstance().getPhoneNumber());
                    contentStream.endText(); 
                    
                    //Horario de consultas
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 13);
                    contentStream.newLineAtOffset(startX4, 310);
                    contentStream.showText("HORARIO: ");
                    contentStream.endText(); 
                    
                    //Nombre del paciente
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 13);
                    contentStream.newLineAtOffset(100, 270);
                    contentStream.showText("Paciente: " + Session.getInstance().getUsername());
                    contentStream.endText(); 
                    
                    //Fecha de la receta
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 13);
                    contentStream.newLineAtOffset(440, 270);
                    contentStream.showText("Fecha: " + LocalDate.now().toString());
                    contentStream.endText(); 
                    
                    
                    
                    /*
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 13);
                    contentStream.newLineAtOffset(startX0, 370); // punto inicial

                    drawMultilineText(contentStream, "Dr. "+ Session.getInstance().getUsername(), 16);
                    drawMultilineText(contentStream, Session.getInstance().getSpeciality(), 16);
                    drawMultilineText(contentStream, "Cedula Profesional: " + Session.getInstance().getCedula(), 16);
                    drawMultilineText(contentStream, "Número de contacto: "+ Session.getInstance().getPhoneNumber(), 16);

                    contentStream.endText();
                    */
                    
                    try {
                        imageStream0 = PdfGeneratorReceta.class.getResourceAsStream("/images/GyE_Logo.png");
                        imageStream1 = PdfGeneratorReceta.class.getResourceAsStream("/images/logo_2.png");
                        System.out.println(imageStream0);
                        PDImageXObject leftImage = PDImageXObject.createFromByteArray(document, imageStream0.readAllBytes(), "Logo0");
                        PDImageXObject rightImage = PDImageXObject.createFromByteArray(document, imageStream1.readAllBytes(), "Logo1"); 

                        // imagen izquierda
                        contentStream.drawImage(leftImage, 25, 300, 75, 75);
                        // imagen derecha
                        contentStream.drawImage(rightImage, page.getMediaBox().getWidth() - 105, 300, 75, 75); 
                    } catch (IOException e) {
                        System.out.println("⚠ No se pudieron cargar las imágenes: " + e.getMessage());
                    }
                    
                    //DATOS DEL PACIENTE
                    contentStream.beginText();
                    contentStream.setFont(font, fontSize);
                    contentStream.newLineAtOffset(15, 240); 
                    drawMultilineText(contentStream, "EDAD: ", 16);
                    drawMultilineText(contentStream, "23 AÑOS", 30);
                    drawMultilineText(contentStream, "PESO:", 16);
                    drawMultilineText(contentStream, "46KG", 30);
                    drawMultilineText(contentStream, "SEXO:", 16);
                    drawMultilineText(contentStream, "MASCULINO", 30);
                    drawMultilineText(contentStream, "TEMP:", 16);
                    drawMultilineText(contentStream, "36°", 30);
                    contentStream.endText();
                    
                    
                    //Rectangulo para el contenido de la receta
                    contentStream.setStrokingColor(Color.BLACK);
                    contentStream.addRect(startRec, 40, 460, 220);
                    contentStream.stroke();
                    
                    //Linea de firma
                    contentStream.setStrokingColor(Color.BLACK);
                    contentStream.setLineWidth(2);
                    contentStream.moveTo(360, 80); // punto inicial
                    contentStream.lineTo(510, 80); // punto final
                    contentStream.stroke();
                    
                    //Firma
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 13);
                    contentStream.newLineAtOffset(415, 65);
                    contentStream.showText("FIRMA");
                    contentStream.endText(); 
                    
                    
                    //Contenido dentro del rectangulo
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
                    contentStream.newLineAtOffset(105, 245); 
                    drawMultilineText(contentStream,
                            tratamiento,
                            15);
                    contentStream.endText();
                    
                    //Dirección del consultorio
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 13);
                    contentStream.newLineAtOffset(startX7, 20);
                    contentStream.showText("Dirección: " + Session.getInstance().getAddress());
                    contentStream.endText(); 
                }

                document.save(dest);
                JOptionPane.showMessageDialog(null, "Receta guardada como: " + dest);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Guardado cancelado por el usuario.");
        }
    }

    private static void drawMultilineText(PDPageContentStream contentStream, String text, float lineHeight) throws IOException {
        String[] lines = text.split("\n");
        for (String line : lines) {
            contentStream.showText(line);
            contentStream.newLineAtOffset(0, -lineHeight); // baja cada línea
        }
    }
}