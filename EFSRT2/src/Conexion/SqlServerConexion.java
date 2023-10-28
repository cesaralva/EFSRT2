package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlServerConexion {

		   
	private static final String URL = "jdbc:sqlserver://localhost:1433;" +
		    "databaseName=BDProduccion;integratedSecurity=true;" +
		    "encrypt=false;trustServerCertificate=false;";

	

    private static final String USERNAME = "sa";  // 
    private static final String PASSWORD = "123"; // 

    public static Connection getConnection() throws SQLException {
        try {
        	
        	
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            Class.forName(driver);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se pudo cargar el controlador de SQL Server.");
            e.printStackTrace();
            throw new SQLException("Error: No se pudo cargar el controlador de SQL Server.");
        } catch (SQLException e) {
            System.err.println("Error de SQL al conectar a la base de datos.");
            e.printStackTrace();
            throw e;
        }
    }

}




