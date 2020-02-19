package main.db.sqlite.PersonStat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import main.enums.EnumPsicholigicalType;

import org.json.JSONObject;

public class PersonStat {

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
	
	public PersonStat() {
		createConnection();
	}

	public boolean setPersonStat(int ASessionId, int ATrueAnswer, int AAnswer, int AAnswerRange) {
		Connection conn = createConnection();
	    Statement stmt = null;
	    String sql;
	    try {
	    	stmt = conn.createStatement();
			sql = String.format(
	    		  "INSERT INTO t_person_stat (ps_person_id, ps_true_answer, ps_person_answer, ps_answer_range) " +
	    				  "SELECT %d, %d, %d, %d;", ASessionId, ATrueAnswer, AAnswer, AAnswerRange); 
			stmt.executeUpdate(sql);

			stmt.close();
			conn.commit();
			conn.close();
	    } catch ( Exception e ) {
	    	CreateException(e);
	    	return false;
	    }

		return true;
	}
	
	public String getPersonStatisticByAverage(int ASessionId, int ARealistAnswerRange) {
		Connection conn = createConnection();
		Statement stmt = null;
		String sql;
		ResultSet rs;
		double answersAvg = 0.00;
		double userAvg = 0.00;
		int smallAnswer = 0;
		int resultByAvg = 0;
		String result = null;
		double percentageAvg = 0.00;
		
		try {
			stmt = conn.createStatement();
	    	// first get all users answered questions average except session user answers
	    	sql = String.format("SELECT AVG(ps_person_answer) AS answers_avg FROM t_person_stat "
	    			+ "WHERE (ps_person_id != %d);", ASessionId); 
			rs = stmt.executeQuery(sql);
			while ( rs.next() ) {
				answersAvg = rs.getFloat("answers_avg");
			}
			rs.close();
	
			// second get session user answers average
	    	sql = String.format("SELECT AVG(ps_person_answer) AS answers_avg FROM t_person_stat "
	    			+ "WHERE (ps_person_id = %d);", ASessionId); 
			rs = stmt.executeQuery(sql);
			while ( rs.next() ) {
				userAvg = rs.getFloat("answers_avg");
			}
			rs.close();
			
			// and for last one, get small person_aswer value
			sql = "SELECT MIN(ps_person_answer) AS answers_min FROM t_person_stat";
			rs = stmt.executeQuery(sql);
			while ( rs.next() ) {
				smallAnswer = rs.getInt("answers_min");
			}
			rs.close();
			
			percentageAvg = 100.00 - Math.abs(((userAvg - smallAnswer) * 100)/(answersAvg - smallAnswer));
			
			// realist logic calculation by range
			if ((percentageAvg <= ARealistAnswerRange) && (percentageAvg >= (ARealistAnswerRange * (-1))))
				resultByAvg = 0;
		    // optimist logic calculation by range
			else if (percentageAvg > ARealistAnswerRange)
				resultByAvg = 1;		    		  
		    // pessimist logic calculation by range
			else if (percentageAvg < (ARealistAnswerRange * (-1)))
				resultByAvg = -1;
			
		    switch (resultByAvg) {
				case -1: 
					result = EnumPsicholigicalType.Pessimist.getValue();
					break;
				case 0:  
					result = EnumPsicholigicalType.Realist.getValue();
					break;
				case 1:
					result = EnumPsicholigicalType.Optimist.getValue();
					break;
			}
			rs.close();
			conn.commit();
			conn.close();
			
			return result;
		} catch ( Exception e ) {
	    	CreateException(e);
	    	return null;
	    }
	}
	
	public JSONObject getUsersAverageStatistic() {
		Connection conn = createConnection();
	    Statement stmt = null;
	    String sql;
	    ResultSet rs;
	    int userCount = 0;
	    try {
	    	stmt = conn.createStatement();
	    	// first get by session user answered questions count
	    	sql = "SELECT COUNT(*) as count FROM t_person;"; 
			rs = stmt.executeQuery(sql);
			while ( rs.next() ) {
				userCount = rs.getInt("count");
			}
			rs.close();
	    	
	    	sql = "SELECT pers_psichological_type AS answer, COUNT(*) AS count FROM t_person "
	    			+ "GROUP BY pers_psichological_type"; 
	    	
	    	rs = stmt.executeQuery(sql);	
			JSONObject obj = new JSONObject();
			while ( rs.next() ) {
				obj.put(rs.getString("answer"),  String.format("%.2f", (rs.getDouble("count")*100.00) / userCount));
			}

			rs.close();
			conn.commit();
			conn.close();
			
			return obj;
	    } catch ( Exception e ) {
	    	CreateException(e);
	    	return null;
	    }
	}
	
	public JSONObject getPersonStatistic(int ASessionId) {
		Connection conn = createConnection();
	    Statement stmt = null;
	    String sql;
	    ResultSet rs;
	    int questionCount = 0;
	    try {
	    	stmt = conn.createStatement();
	    	// first get by session user answered questions count
	    	sql = String.format("SELECT COUNT(*) as count FROM t_person_stat WHERE (ps_person_id = %1$d OR (%1$d = -1));", ASessionId); 
			rs = stmt.executeQuery(sql);
			while ( rs.next() ) {
				questionCount = rs.getInt("count");
			}
			rs.close();
			
			// second get the question statistic in percentage using proportion
			sql = String.format(
	    		  "SELECT COUNT(a.ps_person_id) AS count, "
					      // realist logic calculation by range
	    		  + "     (CASE WHEN (((a.true_answer - a.answer_range) <= a.pers_answer) "
	    		  + "             AND ((a.true_answer + a.answer_range) >= a.pers_answer)) THEN 0 "
	    		          // optimist logic calculation by range
	    		  + "           WHEN ((a.true_answer - a.answer_range) > a.pers_answer) THEN 1 "
	    		          // pessimist logic calculation by range
	    		  + "           WHEN ((a.true_answer + a.answer_range) < a.pers_answer) THEN -1 "              
	    		  + "      END) AS answer "
	    		  + "FROM ( "
	    		  + "  SELECT ps_answer_range AS answer_range, "
	    		  + "         ps_person_answer AS pers_answer, "
	    		  + "         ps_true_answer AS true_answer, "
	    		  + "         ps_person_id "
	    		  + "    FROM t_person_stat "
	    		  + "    WHERE (ps_person_id = %1$d OR (%1$d = -1)) "
	    		  + ") AS a "
	    		  + "GROUP BY answer;", ASessionId); 
			rs = stmt.executeQuery(sql);
			JSONObject obj = new JSONObject();
			while ( rs.next() ) {
				switch (rs.getInt("answer")) {
				case -1: 
					obj.put(EnumPsicholigicalType.Pessimist.getValue(), String.format("%.2f", 
							(rs.getDouble("count")*100.00) / questionCount));
					break;
				case 0:  
					obj.put(EnumPsicholigicalType.Realist.getValue(), String.format("%.2f",
							(rs.getDouble("count")*100.00) / questionCount));
					break;
				case 1:
					obj.put(EnumPsicholigicalType.Optimist.getValue(), String.format("%.2f",
							(rs.getDouble("count")*100.00) / questionCount));
					break;
				}
			}

			rs.close();
			conn.commit();
			conn.close();
			
			return obj;
	    } catch ( Exception e ) {
	    	CreateException(e);
	    	return null;
	    }
	}
}