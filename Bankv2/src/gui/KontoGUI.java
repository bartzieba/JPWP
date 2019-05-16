package gui;

import model.MysqlConnection;
import model.Transakcje;
import model.User;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class KontoGUI {
    static JFrame frame;
    private User l_user;
    public JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField kwota;
    private JButton wpłaćButton;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton wykonajPrzelewButton;
    private JTextField txImie;
    private JTextField txNazwisko;
    private JTextField txPesel;
    private JTextField txMiejsco;
    private JTextField txUlica;
    private JTextField txNrBud;
    private JTextField txkodpocz;
    private JTextField txNarodowosc;
    private JTextField txEmail;
    private JTextField txTelefon;
    public JTable table11;
    public  Transakcje transakcje;

    public KontoGUI(User l_user){
        this.l_user=l_user;
        refresh();
        try {
            table();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        wykonajPrzelewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transakcje t=new Transakcje(l_user,textField6.getText(),textField4.getText(),textField5.getText(),textField7.getText(),"Przelew");
                l_user.setUser_stanKonta(t.przelew());
                refresh();
            }
        });
        wpłaćButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Transakcje t=new Transakcje(l_user," "," ",kwota.getText(),"Wpłata środków","Wpłata");
                try {

                    l_user.setUser_stanKonta(t.wplata(l_user.getUser_stanKonta(),l_user.getUser_login()));
                    refresh();
                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Błąd",JOptionPane.ERROR_MESSAGE);
                }catch (ClassNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public void refresh(){
        textField1.setText(l_user.getUser_nrKonta());
        textField2.setText(l_user.getUser_stanKonta());
        txImie.setText(l_user.getUser_imie());
        txNazwisko.setText(l_user.getUser_nazwisko());
        txPesel.setText(l_user.getUser_pesel());
        txUlica.setText(l_user.getUser_ulica());
        txkodpocz.setText(l_user.getUser_kodPocztowy());
        txNrBud.setText(l_user.getUser_nrBudynku());
        txEmail.setText(l_user.getUser_email());
        txMiejsco.setText(l_user.getUser_miejscowosc());
        txTelefon.setText(l_user.getUser_nrTel());
        txNarodowosc.setText(l_user.getUser_narodowosc());
    }

    public static void showGUI(User l_user){
        frame = new JFrame("Konto");
        frame.setContentPane(new KontoGUI(l_user).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(600,300);
        frame.setVisible(true);
    }
    public  void table()throws SQLException,ClassNotFoundException{
        PreparedStatement pst=MysqlConnection.Connect().prepareStatement("select * from transakcje where NrKontaOdbiorcy=? or NrKontaNadawcy=?");
        pst.setString(1,l_user.getUser_nrKonta());
        pst.setString(2,l_user.getUser_nrKonta());
        ResultSet rs=pst.executeQuery();
        table11.setModel(DbUtils.resultSetToTableModel(rs));

    }

    public static void main(String[] args) {

       /* JFrame frame = new JFrame("Konto");
        frame.setContentPane(new KontoGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(300,300);
        frame.setVisible(true);
*/

    }
}
