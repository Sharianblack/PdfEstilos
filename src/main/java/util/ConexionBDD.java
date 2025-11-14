package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDD {
    private static String url ="jdbc:mysql://locahost:3306/ventasinsanas?serverTimezone=UTC";
    //declarar variables estaticas
    private static String username="root";
    private static String password="";
    //hacemos un constructor estatico
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }
}
