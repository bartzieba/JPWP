package model;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Wiadomosc {
    String tresc;
    String data;
    ResultSet rs;
    PreparedStatement ps;

    public Wiadomosc(String wiadomosc){
        this.tresc = wiadomosc;
        this.data = zwrocDate();
    }

    public boolean wyslijWiadomosc(){
        try {
            ps = MysqlConnection.Connect().prepareStatement("Insert into wiadomosci (tresc,data) value(?,?)");
            ps.setString(1, tresc);
            ps.setString(2, data);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Wiadomość wysłana");


        }catch(java.sql.SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Błąd",JOptionPane.ERROR_MESSAGE);
        }catch(java.lang.ClassNotFoundException e){
            e.printStackTrace();
        }
        return true;
    }

    public void odbierzWiadomosci(){

    }

    public String zwrocDate(){
        GregorianCalendar dzis=new GregorianCalendar();
        String dzien=String.valueOf(dzis.get(Calendar.DAY_OF_MONTH));
        String miesiac=String.valueOf(dzis.get(Calendar.MONTH)+1);
        String rok=String.valueOf(dzis.get(Calendar.YEAR));

        return dzien+"-"+miesiac+"-"+rok;
    }
}
