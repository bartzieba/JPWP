package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
    public static Connection Connect()throws SQLException,ClassNotFoundException{
        Connection connection=null;
        Class.forName("com.mysql.jdbc.Driver");
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root");
        return connection;
    }
}
