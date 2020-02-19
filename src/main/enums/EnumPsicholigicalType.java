package main.enums;

public enum EnumPsicholigicalType {	
	Pessimist("pessimist"), 
	Realist("realist"), 
	Optimist("optimist");
	
	private final String CONST_PESIMIST = "Pesimistų";
	private final String CONST_REALIST = "Realistų";
	private final String CONST_OPTIMIST = "Optimistų";
	
	private String value;
	private String translate;
	
	private EnumPsicholigicalType(String AValue) {
		this.value = AValue;
		
		if (AValue.equals("pessimist"))
			this.translate = CONST_PESIMIST;
		else if (AValue.equals("realist"))
			this.translate = CONST_REALIST;
		else
			this.translate = CONST_OPTIMIST;
	}
	
	public String getValue() {
	    return this.value;
	}
	
	public String getTranslation() {
		return this.translate;
	}
	
	public String getConcateTranslation(int ADelCount, String AText) {
		String tmp = String.copyValueOf(this.translate.toCharArray(), 0, (this.translate.length() + 1) - ADelCount);
		tmp += AText;
		return tmp;
	}
}
