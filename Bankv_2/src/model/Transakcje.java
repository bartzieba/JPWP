package model;

import com.mysql.jdbc.authentication.MysqlClearPasswordPlugin;
import gui.KontoGUI;
import gui.UwierzytelnianieGUI;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Transakcje extends User {
    String imieNazwiskoNadawcy;
    String adresNadawcy;
    String nrKontaNadawcy;
    String stanKontaNadawcy;
    String nrKontaOdbiorcy;
    String adresOdbiorcy;
    String kwota;
    String typ;
    String tytul;
    ResultSet rs;
    PreparedStatement ps;
    public Transakcje(User client, String ao, String nk, String kw, String tyt,String typTrans){
        imieNazwiskoNadawcy = client.getUser_imie()+" "+client.getUser_nazwisko();
        adresNadawcy = client.getUser_miejscowosc()+" "+client.getUser_ulica()+" "+client.getUser_nrBudynku()+" ";
        nrKontaNadawcy =client.getUser_nrKonta();
        stanKontaNadawcy = client.getUser_stanKonta();
        typ=typTrans;
        adresOdbiorcy = ao;
        nrKontaOdbiorcy = nk;
        kwota = kw;
        tytul = tyt;
    }

     public  String zmianaStanu (String stanKonta, String login, int typZmiany)throws SQLException,ClassNotFoundException, java.lang.NumberFormatException{
        try {

            int stan1 = Integer.parseInt(stanKonta);
            int zmiana = Integer.parseInt(kwota);
            if(zmiana<=0) throw new java.lang.NumberFormatException();
            else if (typZmiany > 0) {
                stan1 = stan1 + zmiana;
                ps = MysqlConnection.Connect().prepareStatement("update klienci set StanKonta='" + stan1 + "' where Login='" + login + "'");
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Dokonano wpłaty", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                historia();
                return Integer.toString(stan1);
            } else {
                if (stan1 >= zmiana) {
                    stan1 = stan1 - zmiana;
                    ps = MysqlConnection.Connect().prepareStatement("update klienci set StanKonta='" + stan1 + "' where Login='" + login + "'");
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Środki wypłacone", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    historia();
                    return Integer.toString(stan1);
                } else {
                    JOptionPane.showMessageDialog(null, "Brak środków na koncie!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return Integer.toString(stan1);
                }
            }
        }catch(java.lang.NumberFormatException e){
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Zły format. Wprowadź dane ponownie", "ERROR", JOptionPane.ERROR_MESSAGE);
            return stanKonta;
         }

        }
    public String przelew() {
        if (nrKontaNadawcy.equals(nrKontaOdbiorcy)){
            JOptionPane.showMessageDialog(null,"Nie możesz wysłać przelewu do samego siebie!","Błąd",JOptionPane.ERROR_MESSAGE);
        }else{
        try {

            int kwota_int = Integer.parseInt(kwota);
            int stan_int = Integer.parseInt(stanKontaNadawcy);
            try {
                ps = MysqlConnection.Connect().prepareStatement("Select * from klienci where NrKonta=?");
                ps.setString(1, this.nrKontaOdbiorcy);
                rs = ps.executeQuery();
                if (rs.next()) {
                    if (stan_int >= kwota_int) {
                        String kod="";
                        Random gen = new Random();

                        for (int i=0; i<6; i++) {
                            kod=kod+Integer.toString(gen.nextInt(10));
                        }
                        Sms sms=new Sms(kod);
                        sms.Sms_wyslij();
                        String input=JOptionPane.showInputDialog("Podaj kod sms:");
                        if (input.equals(kod)){
                        String stanKontaOdbiorcy = rs.getString("StanKonta");
                        int stan_int2 = Integer.parseInt(stanKontaOdbiorcy);
                        stan_int2 = stan_int2 + kwota_int;
                        stan_int = stan_int - kwota_int;
                        stanKontaNadawcy = Integer.toString(stan_int);
                        stanKontaOdbiorcy = Integer.toString(stan_int2);
                        ps = MysqlConnection.Connect().prepareStatement("update klienci set StanKonta='" + stanKontaNadawcy + "' where NrKonta='" + this.nrKontaNadawcy + "'");
                        ps.execute();
                        ps = MysqlConnection.Connect().prepareStatement("update klienci set StanKonta='" + stanKontaOdbiorcy + "' where NrKonta='" + this.nrKontaOdbiorcy + "'");
                        ps.execute();
                        JOptionPane.showMessageDialog(null,"Przelew wykonano prawidłowo","Sukces",JOptionPane.INFORMATION_MESSAGE);} else {
                            JOptionPane.showMessageDialog(null,"zly kod","Error",JOptionPane.ERROR_MESSAGE);
                        }
                        try {
                            historia();
                        }catch (SQLException ex){
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"Błąd",JOptionPane.ERROR_MESSAGE);
                        }catch (ClassNotFoundException ex){
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Brak środków na koncie", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Numer konta nie istnieje", "ERROR", JOptionPane.ERROR_MESSAGE);

                }
            } catch (Exception ex) {
                Logger.getLogger(UwierzytelnianieGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (java.lang.NumberFormatException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Zły format. Wprowadź dane ponownie", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }


    return stanKontaNadawcy;

    }

    public void historia()throws SQLException,ClassNotFoundException{
        ps=MysqlConnection.Connect().prepareStatement("Insert into transakcje (Typ,Kwota,Tytul,DaneNadawcy,AdresNadawcy,Data,NrKontaOdbiorcy,NrKontaNadawcy) value (?,?,?,?,?,?,?,?)");
        ps.setString(1,typ);
        ps.setString(2,kwota);
        ps.setString(3,tytul);
        ps.setString(4,imieNazwiskoNadawcy);
        ps.setString(5,adresNadawcy);
        GregorianCalendar dzis=new GregorianCalendar();
        String dzien=String.valueOf(dzis.get(Calendar.DAY_OF_MONTH));
        String miesiac=String.valueOf(dzis.get(Calendar.MONTH)+1);
        String rok=String.valueOf(dzis.get(Calendar.YEAR));
        ps.setString(6,dzien+"-"+miesiac+"-"+rok);
        ps.setString(7,nrKontaOdbiorcy);
        ps.setString(8,nrKontaNadawcy);
        ps.executeUpdate();

    }
}
