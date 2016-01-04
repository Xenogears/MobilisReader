package com.mobilis.reader.tools;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils
{
	public static ArrayList<String> matchRegex(String In, String Regex)
	{
		Pattern Patt = Pattern.compile(Regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher Match = Patt.matcher(In);
		
		ArrayList<String> Result = new ArrayList<String>();
		while(Match.find())
		{
			Result.add(Match.group());
		}
		
		return Result;
	}
	
	public static String[] splitRegex(String In, String Regex)
	{
		return In.split(Regex);		
	}
	
	public static String replaceRegex(String In, String Regex, String Value)
	{
		return In.replaceAll(Regex, Value);
	}
	
	public static String removeHTML(String In)
	{
		return Utils.replaceRegex(In, "<[^>]*>|\r|\n", "");
	}
	
	public static String fullTrim(String In)
	{
		return Utils.replaceRegex(In, "^\\s*|\\s*$", "");
	}
	
	public static String padLeft(String In, int Len, String Char)
	{
		for(int i = In.length(); i < Len; i++)
			In = String.format("%s%s", Char, In);
	
		return In;
	}
}
