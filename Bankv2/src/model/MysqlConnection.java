package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
    public static Connection Connect()throws SQLException,ClassNotFoundException{
        Connection connection=null;
        Class.forName("com.mysql.jdbc.Driver");
        connection= DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7292173","sql7292173","W5ebF1Za9w");






        return connection;
    }
}
