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

public class FrameEjercicio3 extends JFrame implements ActionListener, ItemListener {

    /**
     * Identifica los diferentes valores que hay en un TextField y los guarda en una
     * colección
     * 
     * @param txfTextField TextField del que se recogerán los datos
     * 
     * @return Colección que los datos del TextField
     */
    public ArrayList<String> recogerValoresTXF(JTextField txfTextField) {
        ArrayList<String> valoresTXF = new ArrayList<>();

        for (int i = 0; i < txfTextField.getText().split(";").length; i++) {
            if (!txfTextField.getText().split(";")[i].trim().equals("")) {
                valoresTXF.add(txfTextField.getText().split(";")[i]);
            }
        }

        return valoresTXF;
    }

    /**
     * Traspasa el item seleccionado de un ComboBox a otro
     * 
     * @param cbTraspasador ComboBox que va a pasar su item al otro
     * @param cbTraspasado ComboBox que va a recibir el item seleccionado
     */
    public void traspasarItem(JComboBox cbTraspasador, JComboBox cbTraspasado){
        if(cbTraspasador.getSelectedItem() != null){ // Acciones a realizar si se ha seleccionado un item en el ComboBox
            cbTraspasado.addItem(cbTraspasador.getSelectedItem()); // Primero añado el item seleccionado al ComboBox
            cbTraspasador.removeItem(cbTraspasador.getSelectedItem()); // Después elimino el item seleccionado de su propio ComboBox
        }
        else{ // Si no se ha seleccionado un item del ComboBox, muestra un mensaje de error
            JOptionPane.showMessageDialog(null, "No hay ningún elemento seleccionado en el ComboBox traspasador!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    JComboBox cbA, cbB;
    JButton btnAnadir, btnQuitar, btnTraspasarA, btnTraspasarB;
    JTextField txf1, txf2;
    JLabel lblCantidadElementos, lblIndiceSeleccionado, lbl3;
    Timer temporizador;

    ArrayList<String> valoresTextField = new ArrayList<>(); // Creo una colección donde se guardarán los diferentes valores que contendrá el TextField indicado
    int segundos; // Variable para controlar el timer

    public FrameEjercicio3() {
        super("Ejercicio 3");
        setLayout(new FlowLayout());

        class MouseHandler extends MouseAdapter { // Creo el MouseHandler para gestionar los eventos de ratón

            @Override
            public void mouseEntered(MouseEvent e) { 
                if (e.getSource() == btnQuitar) { // Acciones a realizar cuando el cursor del ratón pasa por encima del botón de quitar
                    btnQuitar.setForeground(Color.RED);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(e.getSource() == btnQuitar){ // Acciones a realizar cuando el cursor del ratón sale de encima del botón de quitar
                    btnQuitar.setForeground(null);
                }
            }

        }

        MouseHandler mh = new MouseHandler();

        cbA = new JComboBox<String>();
        cbA.setToolTipText("ComboBox A");
        cbA.addItemListener(this);
        add(cbA);

        cbB = new JComboBox<String>();
        cbB.setToolTipText("ComboBox B"); // Empiezo con el Tooltip sólo en ComboBox B porque no tiene elementos añadidos
        cbB.addItemListener(this);
        add(cbB);

        btnAnadir = new JButton("Añadir al 1er ComboBox");
        btnAnadir.setSize(btnAnadir.getPreferredSize());
        btnAnadir.setToolTipText("Añade los elementos escritos en el primer TextField al ComboBox A");
        btnAnadir.addActionListener(this);
        add(btnAnadir);

        btnQuitar = new JButton("Quitar del 1er ComboBox");
        btnQuitar.setSize(btnQuitar.getPreferredSize());
        btnQuitar.setToolTipText("Quita el elemento seleccionado del ComboBox A");
        btnQuitar.addActionListener(this);
        btnQuitar.addMouseListener(mh);
        add(btnQuitar);

        btnTraspasarA = new JButton("Traspasar al 1er ComboBox");
        btnTraspasarA.setSize(btnTraspasarA.getPreferredSize());
        btnTraspasarA.setToolTipText("Traspasa el elemento seleccionado del ComboBox B al ComboBox A");
        btnTraspasarA.addActionListener(this);
        add(btnTraspasarA);

        btnTraspasarB = new JButton("Traspasar al 2o ComboBox");
        btnTraspasarB.setSize(btnTraspasarB.getPreferredSize());
        btnTraspasarB.setToolTipText("Traspasa el elemento seleccionado del ComboBox A al ComboBox B");
        btnTraspasarB.addActionListener(this);
        add(btnTraspasarB);

        txf1 = new JTextField(45);
        txf1.setToolTipText("Escribe los elementos que quieras entre ';' para añadirlos al ComboBox A");
        txf1.addActionListener(this);
        add(txf1);

        txf2 = new JTextField(45);
        txf2.setToolTipText("Sirve para eliminar los elementos del ComboBox A que empiecen por lo que escribas aquí");
        txf2.addActionListener(this);
        add(txf2);

        lblCantidadElementos = new JLabel("Nº de elementos en el 1er ComboBox : " + cbA.getItemCount());
        add(lblCantidadElementos);

        lblIndiceSeleccionado = new JLabel();
        add(lblIndiceSeleccionado);

        // Le doy un valor inicial a los minutos y los segundos, y empiezo el contador cuando se abra el formulario
        segundos = 0;
        temporizador = new Timer(1000, this);
        temporizador.start();

        addWindowListener(new WindowAdapter() { // Aquí programo la confirmación al salir de esta ventana usando el adaptador de WindowListener
            public void windowClosing(WindowEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Estás segur@?", "Cerrar?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (res == JOptionPane.OK_OPTION) {
                    e.getWindow().dispose();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnAnadir){ // Acciones a realizar cuando se pulsa el botón para añadir valores al 1er TextField
            if(recogerValoresTXF(txf1).size() == 0){
                JOptionPane.showMessageDialog(null, "No hay valores válidos en el TextField!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                valoresTextField = recogerValoresTXF(txf1);
                for (int i = 0; i < valoresTextField.size(); i++) {
                    cbA.addItem(valoresTextField.get(i));
                }
            }
        }
        else if(e.getSource() == btnQuitar){ // Acciones a realizar cuando se pulsa el botón de quitar elemento de cbA

            if(txf2.getText().equals("")){ // Acciones a realizar si no hay nada escrito en el segundo TextField
                if(cbA.getSelectedItem() != null){ // Si hay un elemento seleccionado, se elimina del ComboBox
                    cbA.removeItem(cbA.getSelectedItem());
                }
                else{ // Si no hay ningún elemento seleccionado, salta un mensaje de error
                    JOptionPane.showMessageDialog(null, "No has seleccionado ningún elemento en el ComboBox!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            else{ // Acciones a realizar si hay algo escrito en el segundo TextField
                int elementosDelCB = cbA.getItemCount();
                for (int i = 0; i < cbA.getItemCount(); i++) {
                    cbA.setSelectedIndex(i);
                    if(cbA.getSelectedItem().toString().startsWith(txf2.getText())){ // Si hay algún elemento del combobox que empiece por el texto dentro de txf2
                        cbA.removeItem(cbA.getSelectedItem()); // Se eliminará del combobox
                    }
                }

                if(elementosDelCB == cbA.getItemCount()){ // Si no se ha quitado ningún elemento lo muestro con un JOptionPane
                    JOptionPane.showMessageDialog(null, "No hay ningún elemento del ComboBox que empiece por " + txf2.getText(), "No se ha quitado nada", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        else if(e.getSource() == btnTraspasarA){ // Acciones a realizar si se pulsa el botón de trasapasar items del segundo a primer ComboBox
            traspasarItem(cbB, cbA); // Llamo a la función que hace que el item seleccionado de un ComboBox se traspase al otro
        }
        else if(e.getSource() == btnTraspasarB){
            traspasarItem(cbA, cbB); // Llamo a la función que hace que el item seleccionado de un ComboBox se traspase al otro
        }
        else if(e.getSource() == temporizador){
            segundos ++;

            if(segundos == 60){
                segundos = 0; // Reinicio el contador al llegar a 60 y hacer un reset
                cbA.removeAllItems();
                cbB.removeAllItems();
                txf1.setText("");
                txf2.setText("");
                lblCantidadElementos.setText("Nº de elementos en el 1er ComboBox : " + cbA.getItemCount()); // Actualizo la etiqueta que cuenta los elementos de cbA
                lblIndiceSeleccionado.setText(""); // Actualizo la etiqueta para que no indique nada, ya que los combobox no tendrán elementos
                cbB.setToolTipText("ComboBox B"); // Actualizo el ToolTip de cbB
            }
        }

        if(e.getSource() != temporizador){
            segundos = 0; // Reinicio el contador al realizar una acción que no sea el temporizador

            lblCantidadElementos.setText("Nº de elementos en el 1er ComboBox : " + cbA.getItemCount()); // Actualizo la etiqueta que cuenta los elementos de cbA una acción que no sea el temporizador
            lblIndiceSeleccionado.setText("Indice seleccionado : " + cbA.getSelectedIndex()); // Actualizo la etiqueta que indica el índice seleccionado de cbA una acción que no sea el temporizador
            cbB.setToolTipText("Indice seleccionado de ComboBox B: " + cbB.getSelectedIndex()); // Actualizo el ToolTip de cbB una acción que no sea el temporizador
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        segundos = 0; // Reinicio el contador al realizar una acción

        cbB.setToolTipText("Indice seleccionado de ComboBox B: " + cbB.getSelectedIndex()); // Actualizo el ToolTip de cbB

        if(e.getSource() == cbA){ // Si se hace cualquier acción en cbA, que se actualice la etiqueta que controla el índice seleccionado
            lblIndiceSeleccionado.setText("Indice seleccionado : " + cbA.getSelectedIndex()); // Actualizo la etiqueta que indica el índice seleccionado de cbA
        }
    }
}