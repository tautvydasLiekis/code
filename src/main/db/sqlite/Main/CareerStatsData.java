package main.db.sqlite.Main;

import javax.swing.JOptionPane;

import main.Config;
import main.FrameText;
import main.db.sqlite.Person.*;
import main.db.sqlite.PersonStat.PersonStat;
import main.enums.EnumPsicholigicalType;
import main.enums.EnumTestAnswerType;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;


public class CareerStatsData {
	
	private final String RES_YOU_ARE = "Jūs esate:";
	
	private int sessionId;
	private EnumTestAnswerType testType;
	
	private PersonStat personStat;
	
	public CareerStatsData() {
		Config cfg = new Config();
		personStat = new PersonStat();
		
		testType = cfg.configAnswerType;
	}
	
	public void setQuestionAnswer(int ATrueAnswer, int AAnswer, int AAnswerRange) throws SQLException {
		personStat.setPersonStat(sessionId, ATrueAnswer, AAnswer, AAnswerRange);
	}
	
	public void createSessionUser(String AUserName) {
		Person person = new Person();
		sessionId = person.setPerson(AUserName);
	}
	
	private String getCareerStatistic(EnumPsicholigicalType AType) {
		String answer;
		try {
			JSONObject jsonStats;
			if (this.testType == EnumTestAnswerType.AnswerStatistic)
				jsonStats = personStat.getPersonStatistic(-1);
			else
				jsonStats = personStat.getUsersAverageStatistic();
				
			if (jsonStats.has(AType.getValue())) {
				answer = jsonStats.getString(AType.getValue());
			} else
				answer = "0.00";
			
			return answer;
		}
		catch (JSONException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "JSON Error", JOptionPane.ERROR_MESSAGE);
			return "Error";
		}		
	}
	
	public String getPessimistPercenageStat() {
		return getCareerStatistic(EnumPsicholigicalType.Pessimist);
	}
	
	public String getRealistPercenageStat() {
		return getCareerStatistic(EnumPsicholigicalType.Realist);	
	}
	
	public String getOptimistPercenageStat() {
		return getCareerStatistic(EnumPsicholigicalType.Optimist);
	}

	public void getPersonPsychologicalPortrait() {
		String personStatistic = "<html>" + RES_YOU_ARE;
		try {
			JSONObject jsonObj = personStat.getPersonStatistic(sessionId);
			
			if (jsonObj.has(EnumPsicholigicalType.Optimist.getValue())) 
				personStatistic += String.format("<br />%s %s", EnumPsicholigicalType.Optimist.getConcateTranslation(2, "as"), 
						jsonObj.getString(EnumPsicholigicalType.Optimist.getValue())) + "%";
			
			if (jsonObj.has(EnumPsicholigicalType.Pessimist.getValue())) 
				personStatistic += String.format("<br />%s %s", EnumPsicholigicalType.Pessimist.getConcateTranslation(2, "as"), 
						jsonObj.getString(EnumPsicholigicalType.Pessimist.getValue())) + "%";
			
			if (jsonObj.has(EnumPsicholigicalType.Realist.getValue())) 
				personStatistic += String.format("<br />%s %s", EnumPsicholigicalType.Realist.getConcateTranslation(2, "as"), 
						jsonObj.getString(EnumPsicholigicalType.Realist.getValue())) + "%";
			
			personStatistic += "</html>";
			
			showAnswer(personStatistic, "");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void getPersonPsychologicalAverageStatus(int AGlobasAnswerRange) {
		String imagePath = "";
		String personStatistic = "<html>" + RES_YOU_ARE;
		try {
			String answer = personStat.getPersonStatisticByAverage(sessionId, AGlobasAnswerRange);
			
			Person person = new Person();
			person.setPersonPsichologicalType(sessionId, answer);
			
			if (answer.equals(EnumPsicholigicalType.Optimist.getValue())) {
				personStatistic += String.format("<br />%s", EnumPsicholigicalType.Optimist.getConcateTranslation(2, "as"));
				imagePath = String.format("img\\%s.png", EnumPsicholigicalType.Optimist.getValue());
			} else if (answer.equals(EnumPsicholigicalType.Pessimist.getValue())) { 
				personStatistic += String.format("<br />%s", EnumPsicholigicalType.Pessimist.getConcateTranslation(2, "as"));
				imagePath = String.format("img\\%s.png", EnumPsicholigicalType.Pessimist.getValue());
		    } else if (answer.equals(EnumPsicholigicalType.Realist.getValue())) {
				personStatistic += String.format("<br />%s", EnumPsicholigicalType.Realist.getConcateTranslation(2, "as"));
				imagePath = String.format("img\\%s.png", EnumPsicholigicalType.Realist.getValue());
		    }
			
			personStatistic += "</html>";
			
			showAnswer(personStatistic, imagePath);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	private void showAnswer(String AText, String AImagePath) throws Exception {
		FrameText frameAnswer = new FrameText(AText, AImagePath);
		frameAnswer.setVisible(true);
	}

}
