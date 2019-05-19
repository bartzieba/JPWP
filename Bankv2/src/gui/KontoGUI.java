package gui;

import model.*;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import java.io.File;
import java.io.FileOutputStream;
public class KontoGUI implements Obserwator {
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
    private JTextField textField3;
    private JButton wypłaćButton;
    private JButton wylogujButton;
    private JButton generujPDFButton;
    public  Transakcje transakcje;
    public String kwota_wiersz;
    public String tytul_wiersz;
    public String dane_nadawcy_wiersz;
    public String adres_nadawcy_wiersz;
    public String data_wiersz;
    public String nr_konta_nadawcy_wiersz;
    Zegar zegar = new Zegar();

    public KontoGUI(User l_user){
        this.l_user=l_user;
        refresh_data();
        zegar.subscribe(this);
        zegar.uruchom();


        wykonajPrzelewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Transakcje t=new Transakcje(l_user,textField6.getText(),textField4.getText(),textField5.getText(),textField7.getText(),"Przelew");
                l_user.setUser_stanKonta(t.przelew());
                refresh_data();
            }
        });
        wpłaćButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Transakcje t=new Transakcje(l_user," "," ",kwota.getText(),"Wpłata środków","Wpłata");
                try {

                    l_user.setUser_stanKonta(t.zmianaStanu(l_user.getUser_stanKonta(),l_user.getUser_login(),1));
                    refresh_data();
                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Błąd",JOptionPane.ERROR_MESSAGE);
                }catch (ClassNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        });
        wypłaćButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transakcje t=new Transakcje(l_user," "," ",kwota.getText(),"Wypłata środków","Wypłata");
                try {

                    l_user.setUser_stanKonta(t.zmianaStanu(l_user.getUser_stanKonta(),l_user.getUser_login(),-1));
                    refresh_data();
                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Błąd",JOptionPane.ERROR_MESSAGE);
                }catch (ClassNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        });

        tabbedPane1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    table();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }catch (ClassNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        });

        wylogujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.dispose();
                UwierzytelnianieGUI.showGUI();
            }
        });
        generujPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            PDF nowypdf=new PDF(kwota_wiersz,tytul_wiersz,dane_nadawcy_wiersz,adres_nadawcy_wiersz,data_wiersz,nr_konta_nadawcy_wiersz);
            nowypdf.stworzPDF();
            }

        });
        table11.addKeyListener(new KeyAdapter() {
        });
        table11.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int wiersz=table11.getSelectedRow();
                kwota_wiersz=table11.getValueAt(wiersz,1).toString();
                tytul_wiersz=table11.getValueAt(wiersz,2).toString();
                dane_nadawcy_wiersz=table11.getValueAt(wiersz,3).toString();
                adres_nadawcy_wiersz=table11.getValueAt(wiersz,4).toString();
                data_wiersz=table11.getValueAt(wiersz,5).toString();
                nr_konta_nadawcy_wiersz=table11.getValueAt(wiersz,6).toString();
            }
        });
    }

    public void refresh_data(){
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
        frame.setSize(700,300);
        frame.setVisible(true);
    }
    public  void table()throws SQLException,ClassNotFoundException{
        PreparedStatement pst=MysqlConnection.Connect().prepareStatement("select * from transakcje where NrKontaOdbiorcy=? or NrKontaNadawcy=?");
        pst.setString(1,l_user.getUser_nrKonta());
        pst.setString(2,l_user.getUser_nrKonta());

        ResultSet rs=pst.executeQuery();
        table11.setModel(DbUtils.resultSetToTableModel(rs));


    }
    public void inform(){
        textField3.setText(zegar.aktualnyCzas);
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
