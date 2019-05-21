import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.proteanit.sql.DbUtils;
import sun.dc.pr.PRError;

import javax.swing.*;

public class JTable {


    private javax.swing.JTable table1;
    private JButton załadujTabeleButton;
    private JPanel panel;

    public JTable() {
        załadujTabeleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    table();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }catch (ClassNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public void table()throws SQLException,ClassNotFoundException{
        String sql="select * FROM  pracownicy"; // Tutaj należy zmienić nazwę tabeli pracownicyN gdzie N liczba 1-16
        PreparedStatement ps=DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs=ps.executeQuery(sql);
        table1.setModel(DbUtils.resultSetToTableModel(rs));

    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("Tabela");
        frame.setContentPane(new JTable().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500,500);
        frame.setVisible(true);

    }
}
