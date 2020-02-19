package main;

import main.enums.EnumTestAnswerType;

public class Config {	
	public boolean configShowUsersStatistic;
	public EnumTestAnswerType configAnswerType;
	public int configRealistAnswerRange;
	
	public Config() {
		QuestionXMLReader qxmlConfig = new QuestionXMLReader();
		qxmlConfig.loadConfigSection();
		
		configShowUsersStatistic = qxmlConfig.getConfigShowUsersStatistic();
		configRealistAnswerRange = qxmlConfig.getConfigRealistAnswerRange();
		switch (qxmlConfig.getConfigTypeOfTest()) {
		case 0:
			configAnswerType = EnumTestAnswerType.AnswerStatistic;
			break;
		default:
			configAnswerType = EnumTestAnswerType.AnswerAverage;
			break;
		}
	}

}
