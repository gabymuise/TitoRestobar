package Programa.View;

import javax.swing.*;
import java.awt.*;

public class VistaImagen extends javax.swing.JPanel {

    public VistaImagen() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        // Crear un JLabel para mostrar la imagen
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("/Resources/LogoTito.png"));
        imageLabel.setIcon(icon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Establecer el diseño
        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.CENTER);
    }
}// </editor-fold>//GEN-END:initComponents


