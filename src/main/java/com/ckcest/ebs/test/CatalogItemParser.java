package com.ckcest.ebs.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class CatalogItemParser {
	private static final Logger logger = Logger.getLogger(CatalogItemParser.class);	
	
	/*
	public static void init(){
		NLPIRTool.init();
	}
	public static void exit(){
		NLPIRTool.exit();
	}
	*/
	
	public static String complex2simple(String title){		
		if(title==null||title.equals(""))
			return "";
		String result = Complex2SimpleTool.conver(title, 0);
		return result;
	}
	
	public static String trim(String title){
		/*
		String[] specialChar = { "　", "〖", "〗", "Ⅰ", "Ⅱ", "Ⅲ", "Ⅳ", "Ⅴ", "Ⅵ",
				"Ⅶ", "Ⅷ", "Ⅸ", "Ⅹ", "Ⅺ", "Ⅻ", "\\(", "\\)", "（", "）", "【", "】",
				"\\[", "\\]", "、", "］", "［", "“", "”", "§", "⊙", "「", "」", "◆",
				"\\.", "★", "▲", "．", "●  ", "●", "：", ":", "\\.", "■" };
				*/
		
		String[] specialChar = { "〖", "〗", "Ⅰ", "Ⅱ", "Ⅲ", "Ⅳ", "Ⅴ", "Ⅵ",
				"Ⅶ", "Ⅷ", "Ⅸ", "Ⅹ", "Ⅺ", "Ⅻ", "【", "】",
				"\\[", "\\]", "］", "［", "“", "”", "§", "⊙", "「", "」", "◆",
				 "★", "▲", "●  ", "●", "■" };
		
		//String[] splitChar = {"　"," ",".", "，","\\.","：", ":","\\(", "\\)", "（", "）"};
		
		
		String[] startChar = { "一", "二", "三", "四", "五", "六", "七", "八", "九",
				"十", "十一", "十二", "十三", "十四", "十五","十六" };
		String str = title;
		for (int i = 0; i < specialChar.length; i++) {
			str = str.replaceAll(specialChar[i], "");
		}/*
		for (int i = 0; i < startChar.length; i++) {
			if (str.startsWith(startChar[i]))
				str = str.replace(startChar[i], "");
		}*/
		 
		
		Pattern pattern = Pattern
				.compile("(?i)Chapter [1-9]|第.{1,3}[节|章|篇|部分|单元|章节|上篇|下篇|课]*");
		Matcher matcher = pattern.matcher(str);
		str = matcher.replaceAll("");
		//.{1,2}[　| |\\.|，|,|：|:]*
		logger.debug("first pattern: " + str);
		
		pattern = Pattern.compile("^(\\d+[\\.．\\-]){0,5}\\d*");
		matcher = pattern.matcher(str);
		str = matcher.replaceAll("");
		logger.debug("second pattern: " + str);
		
		pattern = Pattern.compile("^[\\(|（]*.{1,2}[\\)|）]*[　|\\s|\\.|．|，|,|：|:|、]+");
		matcher = pattern.matcher(str);
		str = matcher.replaceAll("");
		logger.debug("third pattern: " + str);
		
		pattern = Pattern.compile("^[\\(|（|\\[].{1,2}[\\)|）|\\]]");
		matcher = pattern.matcher(str);
		str = matcher.replaceAll("");		
		logger.debug("fourth pattern: " + str);
		
		pattern = Pattern.compile("^◎");
		matcher = pattern.matcher(str);
		str = matcher.replaceAll("");		
		logger.debug("fifth pattern: " + str);
		
		pattern = Pattern.compile("^[一二三四五六七八九十百千万]+[\\.|、]+");
		matcher = pattern.matcher(str);
		str = matcher.replaceAll("");		
		logger.debug("sixth pattern: " + str);
		
		pattern = Pattern.compile("^[\\(|（]*[一二三四五六七八九十百千万]+[\\)|）]*[　|\\s|\\.|．|，|,|：|:|、]+");
		matcher = pattern.matcher(str);
		str = matcher.replaceAll("");
		logger.debug("seven pattern: " + str);
		
		
		
		//“xxx 组成，xxx类型，xxx分类， xxx选介, 各种xxx,常用的xxx，常用xxx，xxx举例,xxx的类型，xxx种类，xxx的品种，xxx主要品种介绍，xxx简表”
		//pattern = Pattern.compile("[组成|类型|分类|选介|举例|的类型|种类|的品种|主要品种介绍|简表|品种]+$");
		//pattern = Pattern.compile("^[(各种)|常用的|常用]+");
		
		/*
		String[] p1 = {"各种","常用的","常用"};
		String[] p2 = {"的组成","的分类","之分类","之组成","之类型","之种类","组成","分类","选介","举例","的种类","的类型","类型","种类","的品种","主要品种介绍","简表","品种"};
		for(String p:p1){
			if(str.startsWith(p)){
				str = str.replace(p, "");
				break;
			}
		}
		logger.debug("seven pattern:" + str);
		for(String p:p2){
			if(str.endsWith(p)){
				str = str.replace(p, "");
				break;
			}
		}
		logger.debug("eight pattern:" + str);
		*/
		
		pattern = Pattern
				.compile("^第[0-9一二三四五六七八九十百千万]+[节|章|篇|部分|单元|章节|上篇|下篇|课]+");
		 matcher = pattern.matcher(str);
		str = matcher.replaceAll("");		
		logger.debug("nine pattern: " + str);
		
		return str.trim();
	}
	
	/*
	public static boolean isQuestion(String title){
		if(title.contains("如何") || title.contains("怎样") || title.contains("什么 ") || title.endsWith("?") || title.endsWith("？")||title.contains("哪些"))
			return true;
		return false;
	}
	
	private static ArrayList splitByBracket(String title){
		ArrayList terms = new ArrayList();		
		Pattern pattern = Pattern.compile("[\\(].+[\\)]");
		Matcher matcher = pattern.matcher(title);
		int start = -1;
		int end = 0;
		int index = 0;
		while (matcher.find()) {
			start = matcher.start();
			end = matcher.end();
			logger.debug("split find pattern: " + matcher.group() + " start: "
					+ start + " end: " + end);
			if (start != 0){
				terms.add(title.substring(index, start));
				index = end + 1;
			}
			terms.add(title.substring(start+1, end-1));
		}
		if(start == -1)
			terms.add(title);
		logger.debug("bracket splitter: " + terms.toString());
		return terms;
	}
	private static ArrayList splitByBlanks(String title){
		ArrayList terms = new ArrayList();		
		Pattern pattern = Pattern.compile("　|\\s|／");
		Matcher matcher = pattern.matcher(title);
		if(matcher.matches()){
			String[] ss = title.split("　|\\s|／");
			//Collections.addAll(terms, ss);
			for(String s:ss){
				s = s.trim();
				if(s.length()==0 || s.equals("")||s.equals("　"))
					continue;
				terms.add(s);
			}
		}else{
			terms.add(title);
		}
		logger.debug("blank splitter: " + terms.toString());
		return terms;
	}
	
	private static ArrayList splitByConjunction(String title){    //conj = "wn means 、, cc means conjunction words"
		ArrayList terms = new ArrayList();
		String str = NLPIRTool.parse(title);
		String[]ss = str.split(" ");
		ArrayList words = new ArrayList();
		ArrayList poss = new ArrayList();
		for(String s:ss){
			if(s.length()==0)
				continue;
			if(s.indexOf("/")==-1)
				continue;
			String word = s.substring(0,s.indexOf("/"));
			String pos = s.substring(s.indexOf("/")+1);
			words.add(word);
			poss.add(pos);
		}
		logger.debug("string = " + str + " words: " + words.toString() + " poss: " + poss.toString());
		String term = "";
		for(int i = 0;i<poss.size();i++){			
			String pos = (String)poss.get(i);
			String word = (String)words.get(i);
			if(pos.equals("wn") || pos.equals("cc") || pos.equals("wd") || (pos.equals("p")&&word.equals("与")) ){   //wn means 、, cc means conjunction words......wd=,  ..,p means prop, problem for:山地栽桑与溪滩栽, so we add pos=p and word = "与"
				terms.add(term);
				term = "";
			}else{
				term = term + words.get(i);
			}
		}
		terms.add(term);
		logger.debug("string = " + str + "terms size: " + terms.size() +  " terms: " + terms.toString());	
		return terms;
	}
	
	public static ArrayList split(String title){
		ArrayList terms = new ArrayList();		
		ArrayList<String> tmpterms = splitByBracket(title);		
		for(String s: tmpterms){
			ArrayList<String> blankTerms = splitByBlanks(s.trim());
			for(String s2:blankTerms){
				String tmp = s2.trim();
				if(tmp.length() ==0)
					continue;
				ArrayList list = splitByConjunction(s2.trim());
				terms.addAll(list);
			}
		}
		return terms;
	}
	
	//DE 指 "的"
	public static boolean existDE(String title){		
		String str = NLPIRTool.parse(title);
		String[]ss = str.split(" ");		
		ArrayList poss = new ArrayList();
		for(String s:ss){
			String word = s.substring(0,s.indexOf("/"));
			String pos = s.substring(s.indexOf("/")+1);
			if(pos.equals("ude1"))
				return true;
		}
		return false;				
	}
	
	public static double varOfTermLength(ArrayList terms){
		double var = 0;
		float mu = 0;
		if(terms.size()==1)
			return 0;
		for(int i=0;i<terms.size();i++){
			String s = (String)terms.get(i);
			int l = s.length();
			mu += l;
		}
		mu = mu/terms.size();
		for(int i=0;i<terms.size();i++){
			String s = (String)terms.get(i);
			int l = s.length();
			var = var + (l-mu)*(l-mu);
		}
		var = var/terms.size();
		var = Math.sqrt(var);
		return var;
	}
	
	/*
	public static String getEntities(String title){
		ArrayList  entities  = new ArrayList();
		String str = complex2simple(title);
		str = trim(str);
		
		String posline = NLPIRTool.parse(str);
		
		
		return entities;
	}*/
}
