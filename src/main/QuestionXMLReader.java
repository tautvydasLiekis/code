package main;
import java.io.File;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QuestionXMLReader {

	NodeList nodeList;
	
	private final String CONST_FILE_ADDR = "questions\\MainFile.xml";
	
	public QuestionXMLReader() {

	}
	
	public void loadQuestionSection() {
		try {
			File file = new File(CONST_FILE_ADDR);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			nodeList = doc.getElementsByTagName("qestion");			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void loadConfigSection() {
		try {
			File file = new File(CONST_FILE_ADDR);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			nodeList = doc.getElementsByTagName("config");			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public int getQuestionsCount() {
		return nodeList.getLength();
	}
	
	private String getQuestionValueByTag(int AIteration, String ATagName) {	
		Node fstNode = nodeList.item(AIteration);
		// kaip suprasti kad reikai naudoti Element objekta?????
		Element fstElmnt = (Element) fstNode;
		NodeList fstNmElmntLst = fstElmnt.getElementsByTagName(ATagName);
		Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
		NodeList fstNm = fstNmElmnt.getChildNodes();
		return ((Node) fstNm.item(0)).getNodeValue();
	}
	
	private int getConfigValueByTag(String ATagName) {
		return Integer.parseInt(getQuestionValueByTag(0, ATagName));
	}
	
	public String getQuestion(int AIteration) {
		return getQuestionValueByTag(AIteration, "text");
	}
	
	public int getQuestionAnswer(int AIteration) {
		return Integer.parseInt(getQuestionValueByTag(AIteration, "answer"));
	}
	
	public int getQuestionAnswerRange(int AIteration) {
		return Integer.parseInt(getQuestionValueByTag(AIteration, "answer_range"));
	}
	
	public int getConfigTypeOfTest() {
		return getConfigValueByTag("type_of_test");
	}
	
	public int getConfigRealistAnswerRange() {
		return getConfigValueByTag("realist_answer_range");
	}

	public boolean getConfigShowUsersStatistic() {
		boolean answer = false;
		switch (getConfigValueByTag("show_users_statistic")) {
		case 0:
			answer = false;
			break;
		case 1:
			answer = true;
			break;
		}
		return answer;
	}

}
