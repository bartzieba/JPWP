import java.sql.*;




public class SQL {


    public static void main(String[] args) {

        String sql="";    //Zapytanie SQL

        try {
            Statement ps;
            ps = DBConnection.getConnection().createStatement(sql);
            ps.executeQuery();



        }
        catch (java.lang.NullPointerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
