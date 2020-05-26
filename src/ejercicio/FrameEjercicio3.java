package ejercicio;

import java.util.Scanner;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.*;


public class FrameEjercicio3 extends JFrame implements ActionListener, ItemListener{
    JComboBox cbA, cbB;
    JButton btnAnadir, btnQuitar, btnTraspasarA, btnTraspasarB;
    JTextField txf1, txf2;
    JLabel lblCantidadElementos, lblIndiceSeleccionado, lbl3;

    public FrameEjercicio3(){
        super("Ejercicio 3");
        setLayout(new FlowLayout());

        cbA = new JComboBox<String>();
        cbA.addItemListener(this);
        add(cbA);

        cbB = new JComboBox<String>();
        cbB.addItemListener(this);
        add(cbB);

        btnAnadir = new JButton("Añadir");
        btnAnadir.setSize(btnAnadir.getPreferredSize());
        btnAnadir.addActionListener(this);
        add(btnAnadir);

        btnQuitar = new JButton("Quitar");
        btnQuitar.setSize(btnQuitar.getPreferredSize());
        btnQuitar.addActionListener(this);
        add(btnQuitar);

        btnTraspasarA = new JButton("Traspasar al primero");
        btnTraspasarA.setSize(btnTraspasarA.getPreferredSize());
        btnTraspasarA.addActionListener(this);
        add(btnTraspasarA);

        btnTraspasarB = new JButton("Traspasar al segundo");
        btnTraspasarB.setSize(btnTraspasarB.getPreferredSize());
        btnTraspasarB.addActionListener(this);
        add(btnTraspasarB);

        txf1 = new JTextField(45);
        add(txf1);

        txf2 = new JTextField(45);
        add(txf2);

        lblCantidadElementos = new JLabel();
        add(lblCantidadElementos);

        lblIndiceSeleccionado = new JLabel();
        add(lblIndiceSeleccionado);


        addWindowListener(new WindowAdapter() { // Aquí programo la confirmación al salir de esta ventana usando el adaptador de WindowListener
            public void windowClosing(WindowEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Estás segur@?", "Cerrar?",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (res == JOptionPane.OK_OPTION) {
                    e.getWindow().dispose();
                }
            }
        });
    }   
}