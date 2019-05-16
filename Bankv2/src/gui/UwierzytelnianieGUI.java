package gui;

import com.sun.javaws.util.JfxHelper;
import jdk.nashorn.internal.scripts.JO;
import model.LoginException;
import model.User;
import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UwierzytelnianieGUI {
    private JTextField login;
    private JButton utwórzKontoButton;
    private JButton zalogujButton;
    public JPanel panel1;
    private JPasswordField haslo;
    static JFrame mainframe;

    public UwierzytelnianieGUI() {
        utwórzKontoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainframe.dispose();
                NoweKontoGUI.showGUI();
            }
        });

        zalogujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logIn();
            }
        });
    }

    public static void showGUI() {
        mainframe = new JFrame("Logowanie");
        mainframe.setContentPane(new UwierzytelnianieGUI().panel1);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.pack();
        mainframe.setResizable(false);
        mainframe.setLocationRelativeTo(null);
        mainframe.setVisible(true);
    }

    public void logIn() {
        if (login.getText().isEmpty() || String.valueOf(haslo.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Uzupełnij login i hasło", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else {
            String user_login = login.getText();
            String user_haslo = String.valueOf(haslo.getPassword());
            try {
                User user = new User();
                if (user.login(user_login, user_haslo)) {
                    mainframe.dispose();
                    KontoGUI.showGUI(user);

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


    public static void main(String[] args) {
        mainframe = new JFrame("Logowanie");

        mainframe.setContentPane(new UwierzytelnianieGUI().panel1);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.pack();
        mainframe.setResizable(false);
        mainframe.setLocationRelativeTo(null);
        mainframe.setSize(250, 250);
        mainframe.setVisible(true);

    }


}
