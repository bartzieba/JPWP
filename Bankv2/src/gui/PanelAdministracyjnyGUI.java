package gui;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.UwierzytelnianieGUI.mainframe;

public class PanelAdministracyjnyGUI {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextField nrKonta_textField;
    private JButton szukajButton;
    private JTextField imie_textField;
    private JTextField pesel_textField;
    private JTextField ulica_textField;
    private JTextField kodPocztowy_textField;
    private JTextField email_textField;
    private JTextField nazwisko_textField;
    private JTextField miejscowosc_textField;
    private JTextField nrBudynku_textField;
    private JTextField narodowosc_textField;
    private JTextField telefon_textField;
    private JTextField online_textField;
    private JButton zatwierdźZmianyButton;
    private JButton wróćButton;

    public PanelAdministracyjnyGUI() {
        szukajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        zatwierdźZmianyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        wróćButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainframe.dispose();
                UwierzytelnianieGUI.showGUI();
            }
        });
    }

    public static void showGUI(){
        mainframe = new JFrame("Panel administracyjny");
        mainframe.setContentPane(new PanelAdministracyjnyGUI().panel1);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.pack();
        mainframe.setResizable(false);
        mainframe.setLocationRelativeTo(null);
        mainframe.setVisible(true);
    }
    public static void main(String[] args) {

    }
}
