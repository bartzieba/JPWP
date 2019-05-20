package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class User {
    private String user_haslo;
    private String user_login ;
    private String user_stanKonta ;
    private String user_nrKonta ;
    private String user_nazwisko;
    private String user_imie ;
    private String user_pesel ;
    private String user_ulica ;
    private String user_nrBudynku ;
    private String user_mieszkania ;
    private String user_miejscowosc ;
    private String user_kodPocztowy ;
    private String user_email ;
    private String user_nrTel ;
    private String user_narodowosc;
    private String user_plec;

    public User() { //konstruktor//

    }
    public User(String user_haslo,String user_login,String user_stanKonta,String user_nrKonta,String user_nazwisko,String user_imie,String user_pesel,String user_ulica,String user_nrBudynku,String user_mieszkania,String user_miejscowosc,String user_kodPocztowy,String user_email,String user_nrTel,String user_narodowosc,String user_plec){
        this.user_haslo=user_haslo;
        this.user_login=user_login;
        this.user_stanKonta=user_stanKonta ;
        this.user_nrKonta=user_nrKonta ;
        this.user_nazwisko=user_nazwisko;
        this.user_imie=user_imie ;
        this.user_pesel=user_pesel ;
        this.user_ulica=user_ulica ;
        this.user_nrBudynku=user_nrBudynku ;
        this.user_mieszkania=user_mieszkania ;
        this.user_miejscowosc=user_miejscowosc ;
        this.user_kodPocztowy=user_kodPocztowy ;
        this.user_email=user_email ;
        this.user_nrTel=user_nrTel ;
        this.user_narodowosc=user_narodowosc;
        this.user_plec=user_plec;

    }

    public boolean wyszukajUzytkownika(String user_nrKonta)throws LoginException,SQLException,ClassNotFoundException {

        PreparedStatement ps;
        ResultSet rs;
        ps=MysqlConnection.Connect().prepareStatement("Select * from klienci where NrKonta=?");
        ps.setString(1,user_nrKonta);
        rs=ps.executeQuery();
        if(rs.next()){
            this.user_haslo=rs.getString(15);
            this.user_login=rs.getString(14);
            this.user_stanKonta=rs.getString(16);
            this.user_nrKonta=rs.getString(1);
            this.user_nazwisko=rs.getString(2);
            this.user_imie=rs.getString(3) ;
            this.user_pesel=rs.getString(4) ;
            this.user_ulica=rs.getString(5);
            this.user_nrBudynku=rs.getString(6) ;
            this.user_mieszkania=rs.getString(7) ;
            this.user_miejscowosc=rs.getString(8) ;
            this.user_kodPocztowy=rs.getString(9);
            this.user_email=rs.getString(10) ;
            this.user_nrTel=rs.getString(11);
            this.user_narodowosc=rs.getString(12);
            this.user_plec=rs.getString(13);
            return true;
        }else {
            throw new LoginException("Podany numer konta nie znajduje się w bazie danych");
        }
    }

    public boolean zmienDane(String user_nazwisko_new,String user_imie_new, String user_pesel_new, String user_ulica_new,
        String user_nrBudynku_new, String user_miejscowosc_new, String user_kodPocztowy_new, String user_email_new,
        String user_nrTel_new,String user_narodowosc_new,String user_nrKonta_new)throws SQLException,NewUserException,ClassNotFoundException{
        PreparedStatement ps;
        ps=MysqlConnection.Connect().prepareStatement("update klienci set Nazwisko=?, Imie=?, PESEL=?, Ulica=?, NrBudynku=?, Miejscowosc=?, KodPocztowy=?, Email=?, NrTel=?, Narodowosc=? where NrKonta=?");
        ps.setString(1,user_nazwisko_new);
        ps.setString(2,user_imie_new);
        ps.setString(3,user_pesel_new);
        ps.setString(4,user_ulica_new);
        ps.setString(5,user_nrBudynku_new);
        ps.setString(6,user_miejscowosc_new);
        ps.setString(7,user_kodPocztowy_new);
        ps.setString(8,user_email_new);
        ps.setString(9,user_nrTel_new);
        ps.setString(10,user_narodowosc_new);
        ps.setString(11,user_nrKonta_new);

        if(ps.executeUpdate()>0){
            ps.close();
            MysqlConnection.Connect().close();
            return true;
        }else {
            throw new NewUserException("Rejestracja nie powiodła się");
        }
    }




    public boolean login(String user_login,String user_haslo)throws LoginException,SQLException,ClassNotFoundException {

        PreparedStatement ps;
        ResultSet rs;
        ps=MysqlConnection.Connect().prepareStatement("Select * from klienci where Login=? and Haslo=?");
        ps.setString(1,user_login);
        ps.setString(2,user_haslo);
        rs=ps.executeQuery();
        if(rs.next()){
            this.user_haslo=rs.getString(15);
            this.user_login=rs.getString(14);
            this.user_stanKonta=rs.getString(16);
            this.user_nrKonta=rs.getString(1);
            this.user_nazwisko=rs.getString(2);
            this.user_imie=rs.getString(3) ;
            this.user_pesel=rs.getString(4) ;
            this.user_ulica=rs.getString(5);
            this.user_nrBudynku=rs.getString(6) ;
            this.user_mieszkania=rs.getString(7) ;
            this.user_miejscowosc=rs.getString(8) ;
            this.user_kodPocztowy=rs.getString(9);
            this.user_email=rs.getString(10) ;
            this.user_nrTel=rs.getString(11);
            this.user_narodowosc=rs.getString(12);
            this.user_plec=rs.getString(13);

            return true;
        }else {
        throw new LoginException("Błąd! Podaj poprawny login i hasło");
        }
    }
    public boolean newUser()throws SQLException,NewUserException,ClassNotFoundException{
        PreparedStatement ps;
        ps=MysqlConnection.Connect().prepareStatement("Insert  into klienci (NrKonta,Nazwisko,Imie,PESEL,Ulica,NrBudynku,NrMieszkania,Miejscowosc,KodPocztowy,Email,NrTel,Narodowosc,Plec,Login,Haslo,StanKonta)value (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1,user_nrKonta);
        ps.setString(2,user_nazwisko);
        ps.setString(3,user_imie);
        ps.setString(4,user_pesel);
        ps.setString(5,user_ulica);
        ps.setString(6,user_nrBudynku);
        ps.setString(7,user_mieszkania);
        ps.setString(8,user_miejscowosc);
        ps.setString(9,user_kodPocztowy);
        ps.setString(10,user_email);
        ps.setString(11,user_nrTel);
        ps.setString(12,user_narodowosc);
        ps.setString(13,user_plec);
        ps.setString(14,user_login);
        ps.setString(15,user_haslo);
        ps.setString(16,user_stanKonta);
        if(ps.executeUpdate()>0){
            ps.close();
            MysqlConnection.Connect().close();
            return true;
        }else {
            throw new NewUserException("Rejestracja nie powiodła się");
        }
    }
    public String getUser_haslo() {
        return user_haslo;
    }

    public String getUser_login() {
        return user_login;
    }

    public String getUser_stanKonta() {
        return user_stanKonta;
    }

    public String getUser_nrKonta() {
        return user_nrKonta;
    }

    public String getUser_nazwisko() {
        return user_nazwisko;
    }

    public String getUser_imie() {
        return user_imie;
    }

    public String getUser_pesel() {
        return user_pesel;
    }

    public String getUser_ulica() {
        return user_ulica;
    }

    public String getUser_nrBudynku() {
        return user_nrBudynku;
    }

    public String getUser_mieszkania() {
        return user_mieszkania;
    }

    public String getUser_miejscowosc() {
        return user_miejscowosc;
    }

    public String getUser_kodPocztowy() {
        return user_kodPocztowy;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_nrTel() {
        return user_nrTel;
    }

    public String getUser_narodowosc() {
        return user_narodowosc;
    }

    public String getUser_plec() {
        return user_plec;
    }

    public void setUser_stanKonta(String user_stanKonta) {
        this.user_stanKonta = user_stanKonta;
    }

}
