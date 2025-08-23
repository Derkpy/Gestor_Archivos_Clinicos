/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.derkcode.gestor_archivos_clinicos.util;

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


public class PdfGenerator {

    public static void generateReceta(String expediente, String enfermedad, String sintomas, String tratamiento, String sugerencias) {
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
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                    // 📌 Encabezado de 4 líneas
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 14);
                    contentStream.newLineAtOffset(200, 700); // punto inicial

                    drawMultilineText(contentStream, "Nombre de Usuario", 20);
                    drawMultilineText(contentStream, "Categoría del usuario", 20);
                    drawMultilineText(contentStream, "Número 9392839", 20);
                    drawMultilineText(contentStream, "Número de contacto en caso de solicitar 898-283-9382", 20);

                    contentStream.endText();

                    // 📌 Inserción de imágenes (ajustadas de tamaño)
                    try {
                        PDImageXObject leftImage = PDImageXObject.createFromFile("left.png", document);
                        PDImageXObject rightImage = PDImageXObject.createFromFile("right.png", document);

                        // imagen izquierda
                        contentStream.drawImage(leftImage, 50, 680, 60, 60);
                        // imagen derecha
                        contentStream.drawImage(rightImage, page.getMediaBox().getWidth() - 110, 680, 60, 60);
                    } catch (IOException e) {
                        System.out.println("⚠ No se pudieron cargar las imágenes: " + e.getMessage());
                    }

                    // 📌 Párrafo (más abajo y no centrado)
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
                    contentStream.newLineAtOffset(70, 580);

                    drawMultilineText(contentStream,
                            "Este es un párrafo de ejemplo que aparece debajo del encabezado.\n"
                                    + "Aquí se puede colocar una descripción más larga o información\n"
                                    + "adicional que el documento necesite mostrar.",
                            15);

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

    // 🔑 Método auxiliar para soportar saltos de línea
    private static void drawMultilineText(PDPageContentStream contentStream, String text, float lineHeight) throws IOException {
        String[] lines = text.split("\n");
        for (String line : lines) {
            contentStream.showText(line);
            contentStream.newLineAtOffset(0, -lineHeight); // baja cada línea
        }
    }
}