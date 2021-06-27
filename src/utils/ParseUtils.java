package utils;

import java.util.LinkedList;

public class ParseUtils
{
	public static String linkedStringsToString(LinkedList<String> linkedStrings, boolean mswindows)
	{
		String result = "";
		for (int i = 0; i < linkedStrings.size(); i++)
			result += linkedStrings.get(i) + (mswindows ? "\r\n" : "\n");
		return result;
	}
}