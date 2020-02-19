package main.db.sqlite.Person;

import java.sql.*;

import javax.swing.JOptionPane;

import main.enums.EnumPsicholigicalType;

public class Person {

	private final String CONST_DATABASE = "jdbc:sqlite:db\\stats";
	
	private void CreateException(Exception e) {
		JOptionPane.showMessageDialog(null, "Error: \n" + e.getClass().getName() + ": " + e.getMessage());
	}
	
	private Connection createConnection() {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(CONST_DATABASE);
			conn.setAutoCommit(false);
			
		} catch (Exception e) {
			CreateException(e);
			return null;
		}
		return conn;
	}
	
	public Person() {
		createConnection();
	}
	
	public int setPerson(String AName) {
		Connection conn = createConnection();
	    Statement stmt = null;
	    int result;
	    String sql;
	    try {
	    	stmt = conn.createStatement();
			sql = String.format(
	    		  "INSERT INTO t_person (pers_name) " +
	    				  "SELECT '%s';", AName); 
			stmt.executeUpdate(sql);

	    	sql = String.format("SELECT pers_id FROM t_person WHERE pers_name = '%s' ORDER BY pers_id DESC LIMIT 1;", AName); 
	    	ResultSet rs = stmt.executeQuery(sql);
	    	rs.next();
	    	result = rs.getInt("pers_id");
			rs.close();
			
			stmt.close();
			conn.commit();
			conn.close();
	    } catch ( Exception e ) {
	    	CreateException(e);
	    	return -1;
	    }
	    
	    return result;
	}
	
	public int setPersonPsichologicalType(int APersId, String APersType) {
		Connection conn = createConnection();
	    Statement stmt = null;
	    int result;
	    String sql;
	    try {
	    	stmt = conn.createStatement();
			sql = String.format(
	    		  "UPDATE t_person SET pers_psichological_type = '%s' "
	    		  + "WHERE pers_id = %d;", APersType, APersId); 
			stmt.executeUpdate(sql);

	    	sql = String.format("SELECT pers_id FROM t_person WHERE pers_id = %d;", APersId); 
	    	ResultSet rs = stmt.executeQuery(sql);
	    	rs.next();
	    	result = rs.getInt("pers_id");
			rs.close();
			
			stmt.close();
			conn.commit();
			conn.close();
	    } catch ( Exception e ) {
	    	CreateException(e);
	    	return -1;
	    }
	    
	    return result;
	}
	
	public int getLastPersonId() {
		Connection conn = createConnection();
		Statement stmt = null;
		int answer = -1;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT pers_id FROM t_person ORDER BY pers_id DESC LIMIT 1;"; 
			ResultSet rs = stmt.executeQuery(sql);
			while ( rs.next() ) {
				answer = rs.getInt("pers_id");
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch ( Exception e ) {
	    	CreateException(e);
	    	return -1;
	    }
		
		return answer;
	}
	
	public int getLastPersonId(String AUserName) {
		Connection conn = createConnection();
		Statement stmt = null;
		int answer = -1;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(String.format("SELECT pers_id FROM t_person "
					+ "WHERE pers_name = '%s' "
					+ "ORDER BY pers_id DESC "
					+ "LIMIT 1;", AUserName));
			while (rs.next()) {
				answer = rs.getInt("pers_id");
			}
			
			rs.close();
			stmt.close();
			conn.close();
		} catch ( Exception e ) {
	    	CreateException(e);
	    	return -1;
	    }
		
		return answer;
	}
}
