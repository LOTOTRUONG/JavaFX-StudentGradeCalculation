package loto.vn.sgcapplication.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SGCConnection {
    private static Connection connection;
    private SGCConnection(){}

    public static Connection getConnection() {
        try {
            String dbURL = "jdbc:sqlserver://localhost:1434; database = StudentGradeCalculator";
            String user = "sa";
            String password = "loan@123456";
            connection = DriverManager.getConnection(dbURL, user, password);
            return connection;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
