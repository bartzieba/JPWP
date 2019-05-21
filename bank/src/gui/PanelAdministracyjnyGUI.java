package gui;

import model.LoginException;
import model.NewUserException;
import model.User;
import model.Wiadomosc;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
    private JTextField stanKonta_textField;
    private JTextArea wiadomosc_textArea;
    private JButton wyślijButton;

    public PanelAdministracyjnyGUI() {
        szukajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wyszukaj();
            }
        });
        zatwierdźZmianyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zatwierdzZmiay();
            }
        });
        wróćButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainframe.dispose();
                UwierzytelnianieGUI.showGUI();
            }
        });
        wyślijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Wiadomosc info = new Wiadomosc(wiadomosc_textArea.getText());
                if(info.wyslijWiadomosc()){
                    wiadomosc_textArea.setText("");
                }

            }
        });
    }

    public void zatwierdzZmiay() {
        String user_nrKonta = nrKonta_textField.getText();
        String user_imie =imie_textField.getText();
        String user_nazwisko=nazwisko_textField.getText();
        String user_ulica=ulica_textField.getText();
        String user_miejscowosc=miejscowosc_textField.getText();
        String user_nrBudynku=nrBudynku_textField.getText();
        String user_email=email_textField.getText();
        String user_narodowosc=narodowosc_textField.getText();
        String user_kodPocztowy=kodPocztowy_textField.getText();
        String user_pesel=pesel_textField.getText();
        String user_telefon=telefon_textField.getText();

        try {
            User user = new User();
            user.zmienDane(user_nazwisko,user_imie,user_pesel,user_ulica,user_nrBudynku,user_miejscowosc,user_kodPocztowy,user_email,
                    user_telefon,user_narodowosc,user_nrKonta);
            JOptionPane.showMessageDialog(null,"Operacja powiodła się");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd połączenia", "Błąd", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
         catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (NewUserException e) {
        e.printStackTrace();
    }
    }
    public void wyszukaj(){
        if (nrKonta_textField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Wpisz numer konta", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else {
            String user_nrKonta = nrKonta_textField.getText();
            try {
                User user = new User();
                if (user.wyszukajUzytkownika(user_nrKonta)) {
                    imie_textField.setText(user.getUser_imie());
                    nazwisko_textField.setText(user.getUser_nazwisko());
                    ulica_textField.setText(user.getUser_ulica());
                    miejscowosc_textField.setText(user.getUser_miejscowosc());
                    nrBudynku_textField.setText(user.getUser_nrBudynku());
                    email_textField.setText(user.getUser_email());
                    narodowosc_textField.setText(user.getUser_narodowosc());
                    kodPocztowy_textField.setText(user.getUser_kodPocztowy());
                    pesel_textField.setText(user.getUser_pesel());
                    telefon_textField.setText(user.getUser_nrTel());
                    stanKonta_textField.setText(user.getUser_stanKonta());

                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Błąd połączenia", "Błąd", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (LoginException e){
                JOptionPane.showMessageDialog(null,e.getMessage(),"Błąd",JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
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
