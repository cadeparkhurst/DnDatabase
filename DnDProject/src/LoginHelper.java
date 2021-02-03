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

	public boolean connect(String user, String pass) {
		//TODO: Task 1
		String connectionString = SampleURL.replace("${dbServer}", this.serverName);
		connectionString = connectionString.replace("${dbName}", this.databaseName);
		connectionString = connectionString.replace("${user}", user);
		connectionString = connectionString.replace("${pass}", pass);
		System.out.println(connectionString);
		try{
			Connection connectiontemp = DriverManager.getConnection(connectionString);
			this.connection = connectiontemp;
			return true;
		}
		  // Code here.
		catch (SQLException e) {
			System.out.println(e.getErrorCode());
			e.printStackTrace();
			return false;
		}
        

	}
	
	public boolean register(String username, String password) {
		//TODO: Task 6
		CallableStatement cs = null;
		ResultSet rs = null;
		int error = 0;
		try {
			cs = this.connection.prepareCall("{? = call Register(?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			System.out.println("test1");
			cs.execute();
			System.out.println("test2");
			error = cs.getInt(1);
			if (error == 1 || error == 2 || error == 3 || error == 4) {
				JOptionPane.showMessageDialog(null, "Registration Failed"+error);
				return false;
			}
//			if (rs.getString(4) == null) {
//				return true;
//			}
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			if (error == 1) {
				JOptionPane.showMessageDialog(null, "Registration Failed"+e.getCause());
			}
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
	
