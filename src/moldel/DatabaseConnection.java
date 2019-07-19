package moldel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection conn;
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        if(conn == null){
            Class.forName("org.apache.derby.jdbc.ClientDriver");   
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Database;user=username;password=password");
        }
        
        return conn;
    }
}
