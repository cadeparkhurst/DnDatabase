import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Random;
import java.util.Base64;
import java.security.SecureRandom;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class LoginHelper {
	
	public LoginHelper() {
		
	}
	
	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public LoginHelper(String serverName, String databaseName) {
		//DO NOT CHANGE THIS METHOD
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect() {
		String connectionString = SampleURL.replace("${dbServer}", this.serverName)
										   .replace("${dbName}", this.databaseName)
										   //.replace("${user}", "dndatabasefrontend")
										   .replace("${user}", "parkhuca")
										   .replace("${pass}", "Thec@tisyellow");
										   //.replace("${pass}", "ivor1234");// REPLACE WITH USER ACCOUNT
		try{
			Connection connectiontemp = DriverManager.getConnection(connectionString);
			this.connection = connectiontemp;
			return true;
		}
		catch (SQLException e) {
			System.out.println(e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	

	
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
		this.connection = null;
	}

}
	
