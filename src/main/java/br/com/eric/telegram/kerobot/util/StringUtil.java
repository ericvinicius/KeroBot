package br.com.eric.telegram.kerobot.util;

public class StringUtil {

	public static Integer getIntergers(String txt) {
		return Integer.parseInt(txt.replaceAll("\\D+", ""));
	}

	public static String removeNumbers(String txt) {
		return txt.trim().replaceAll("\\d+", "");
	}

	public static String fixNumberWith3Digits(int number) {
		String numberAsString = String.valueOf(number);
		return "000".substring(numberAsString.length()) + numberAsString;
	}
	
	public static String fixNumberWith2Digits(long l) {
		String numberAsString = String.valueOf(l);
		return "00".substring(numberAsString.length()) + numberAsString;
	}

}
