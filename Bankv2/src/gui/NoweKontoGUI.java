package gui;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import jdk.nashorn.internal.scripts.JO;
import model.NewUserException;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import java.util.Random;
import java.util.List;

public class NoweKontoGUI {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField6;
    private JTextField textField4;
    private JTextField textField3;
    private JTextField textField5;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    public JPanel panel1;
    private JButton Stworz;
    private JTextField textField10;
    private JComboBox JComboBox1;
    private JTextField textField11;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton stwórzButton;
    private JButton wróćButton;
    private JComboBox JcomboBox2;
    private ButtonGroup buttonGroup1;
    static JFrame frame;
    private KeyAdapter liczby=new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            super.keyTyped(e);
            char c=e.getKeyChar();
            if(!((c>='0')&&(c<='9'))){
                e.consume();
            }
        }
    };


    public NoweKontoGUI() {
        textField3.addKeyListener(liczby);
        textField10.addKeyListener(liczby);
        wróćButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                UwierzytelnianieGUI.showGUI();
            }
        });
        stwórzButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stworzKonto();
            }
        });
    textField10.addKeyListener(new KeyAdapter() { } );}
    public  static void showGUI(){
        frame = new JFrame("Nowe Konto");
        frame.setContentPane(new NoweKontoGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public void stworzKonto(){
        if(textField1.getText().isEmpty() ||textField2.getText().isEmpty() ||textField3.getText().isEmpty() ||textField4.getText().isEmpty() ||textField5.getText().isEmpty() ||textField6.getText().isEmpty() ||textField7.getText().isEmpty() ||textField8.getText().isEmpty() ||textField9.getText().isEmpty() ||textField10.getText().isEmpty() ||
                textField11.getText().isEmpty() || String.valueOf(passwordField1.getPassword()).isEmpty() || String.valueOf(passwordField1.getPassword()).isEmpty()||JComboBox1.getSelectedIndex()<1 ||JcomboBox2.getSelectedIndex()<1){
            JOptionPane.showMessageDialog(null,"Uzupełnij wszystkie pola!","Błąd",JOptionPane.ERROR_MESSAGE);
        }else{

            Random gen = new Random();

            String user_haslo=String.valueOf(passwordField1.getPassword());
            String user_login=textField11.getText() ;
            String user_stanKonta="0" ;
            String user_nrKonta="" ;
            for (int i=0; i<14; i++) {
                user_nrKonta=user_nrKonta+Integer.toString(gen.nextInt(10));
            }
            String user_nazwisko=textField2.getText();
            String user_imie=textField1.getText() ;
            String user_pesel=textField3.getText() ;
            String user_ulica=textField4.getText() ;
            String user_nrBudynku=textField5.getText() ;
            String user_mieszkania=textField6.getText() ;
            String user_miejscowosc=textField7.getText() ;
            String user_kodPocztowy=textField8.getText() ;
            String user_email=textField9.getText() ;
            String user_nrTel=textField10.getText() ;
            String user_narodowosc=JComboBox1.getSelectedItem().toString();
            String user_plec=JcomboBox2.getSelectedItem().toString();
        if (user_haslo.length()<6){
            JOptionPane.showMessageDialog(null,"Zbyt krótkie hasło.Minimum 6 znaków!","Błąd",JOptionPane.ERROR_MESSAGE);
        }else if (!user_haslo.equals(String.valueOf(passwordField2.getPassword()))){
            JOptionPane.showMessageDialog(null,"Hasła są rózne!","Błąd",JOptionPane.ERROR_MESSAGE);
        }else if (user_nrTel.length()!=9){
            JOptionPane.showMessageDialog(null,"Zbyt krótki numer telefonu","Błąd",JOptionPane.ERROR_MESSAGE);
        }else if (user_pesel.length()!=11){
            JOptionPane.showMessageDialog(null,"Niepoprawny PESEL","Błąd",JOptionPane.ERROR_MESSAGE);
        }else {
            try {
                User user=new User(user_haslo,user_login,user_stanKonta,user_nrKonta,user_nazwisko,user_imie,user_pesel,user_ulica,user_nrBudynku,user_mieszkania,user_miejscowosc,user_kodPocztowy,user_email,user_nrTel,user_narodowosc,user_plec);
                if (user.newUser()) {
                    JOptionPane.showMessageDialog(null,"Konto utworzone!Teraz możesz się zalogować.","Sukces",JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    UwierzytelnianieGUI.showGUI();

                }
            }catch (SQLException exc){
                String error=exc.getMessage();
                if(error.contains("Login")){
                    JOptionPane.showMessageDialog(null,"Podany login już istnieje!","Bład",JOptionPane.ERROR_MESSAGE);
                }else if (error.contains("Email")){
                    JOptionPane.showMessageDialog(null,"Podany email jest już skojarzony z istniejącym kontem!","Bład",JOptionPane.ERROR_MESSAGE);
                }
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }catch (NewUserException e){
                JOptionPane.showMessageDialog(null,e.getMessage(),"Błąd",JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        }

    }
    public static void main(String[] args) {

    }
}
