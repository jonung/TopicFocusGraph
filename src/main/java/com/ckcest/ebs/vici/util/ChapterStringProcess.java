package com.ckcest.ebs.vici.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;



 
/**
 * @ClassName: ChapterStringProcess
 * @Description: 
 * @author GongJun
 * @date Jun 10, 2015 4:27:50 PM
 * @version V1.0  
 */ 
	 
public class ChapterStringProcess {
	private static Logger log = Logger.getLogger(ChapterStringProcess.class);
	
	public static int FIRST_LEVEL = 1;
	public static int SECOND_LEVEL = 1;
	
	public Map<String,Integer> getChapterLevel(List<String> catalogs){
		Map<String,Integer> res = new HashMap<String,Integer>();
		for(int i = 0; i < catalogs.size(); i ++){
			
		}
		return res;
	}
	
	/**
	 * @Function: preProcessChapter
	 * @Description: 对每个章节做字符串处理，包括去前缀，繁体转简体
	 * @param @param chapter
	 * @param @return    
	 * @return String    
	 * @date 2015-1-18 下午8:09:18
	 * @throws
	 */
		
	public static String preProcessChapter(String chapter) {
		String res = trimPrefix(chapter);
		res = HanZiConversion.convert2SimplifiedChinese(res);
		return res;
	}
	 /**
	  * abandon the prefix of chapter,like "第一章"
	  * @param str_in
	  * @return
	  */
	public static String trimPrefix(String str_in){
		
		String str=str_in;
		char[] str_array=str.toCharArray();
		int i=0;
		int len1=(str_array.length/3*2)>7?(str_array.length/3*2):7;
		int len=len1<str_array.length-2?len1:str_array.length-2;
		for(;i<=len;)
		{
			if(str_array[i]=='0'||str_array[i]=='1'||str_array[i]=='2'||str_array[i]=='3'||str_array[i]=='4'
				||str_array[i]=='5'||str_array[i]=='6'||str_array[i]=='7'||str_array[i]=='8'||str_array[i]=='9'
					||str_array[i]=='.'||str_array[i]=='$'||str_array[i]=='#'||str_array[i]=='^'||str_array[i]=='？'
						||str_array[i]=='?'||str_array[i]=='['||str_array[i]==']'||str_array[i]=='{'||str_array[i]=='}'
							||str_array[i]=='*'||str_array[i]=='&'||str_array[i]=='~'||str_array[i]=='+'||str_array[i]=='-'
								||str_array[i]==' '||str_array[i]=='Ⅰ'||str_array[i]=='Ⅱ'	||str_array[i]=='Ⅲ'	||str_array[i]=='、'	  
									||str_array[i]=='．'||str_array[i]=='（'||str_array[i]=='）'||str_array[i]=='('||str_array[i]==')')
										
			{
				i=i+1;
			}
			else if(str_array[i]=='一'||str_array[i]=='二'||str_array[i]=='三'||str_array[i]=='四'
					||str_array[i]=='五'||str_array[i]=='六'||str_array[i]=='七'||str_array[i]=='八'
						||str_array[i]=='九'||str_array[i]=='十')
			{
				if(str_array[i+1]!=' '&&str_array[i+1]!='、'&&str_array[i+1]!='章'&&str_array[i+1]!='节'&&str_array[i+1]!='篇'
					&&str_array[i+1]!='部'&&str_array[i+1]!='层'&&str_array[i+1]!='级'&&str_array[i+1]!='一'&&str_array[i+1]!='二'
						&&str_array[i+1]!='三'&&	str_array[i+1]!='四'&&str_array[i+1]!='五'&&str_array[i+1]!='六'&&str_array[i+1]!='七'
							&&str_array[i+1]!='八'&&str_array[i+1]!='九'&&str_array[i+1]!='十')
					break;
				else
					i=i+1;
			}
			else if(str_array[i]=='章'||str_array[i]=='节'||str_array[i]=='篇'||str_array[i]=='层'||str_array[i]=='级')
			{
				i=i+1;
				break;
			}
			else if(str_array[i]=='部')
			{
				if(str_array[i+1]=='分')
				{
					i=i+2;
				}
				else 
					i=i+1;
				break;
			}
			else if(str_array[i]=='第')
			{
				if(str_array[i+1]=='一'||str_array[i+1]=='二'||str_array[i+1]=='三'||str_array[i+1]=='四'
					||str_array[i+1]=='五'||str_array[i+1]=='六'||str_array[i+1]=='七'||str_array[i+1]=='八'
						||str_array[i+1]=='九'||str_array[i+1]=='十'||str_array[i+1]==' '||str_array[i+1]=='0'
							||str_array[i+1]=='1'||str_array[i+1]=='2'||str_array[i+1]=='3'||str_array[i+1]=='4'
								||str_array[i+1]=='5'||str_array[i+1]=='6'||str_array[i+1]=='7'||str_array[i+1]=='8'
									||str_array[i+1]=='9'||str_array[i+1]=='Ⅰ'||str_array[i+1]=='Ⅱ'||str_array[i+1]=='Ⅲ')
					i=i+1;
				else 
					break;
			}
			else
				break;
		}
		
		char[] str_array_new=new char[str_in.length()-i];
		for(int k=i;k<str_in.length();k++)
			str_array_new[k-i]=str_array[k];
		str=String.valueOf(str_array_new).trim();
		str=str.replaceAll("　", "");//12288，即中文全角空格
		
		if(str.equals("习题") || str.equals("练习题") || str.equals("小结") || str.contains("思考题"))
			str = "";
		
		return str;
	}
	
	
	
	/**
	 * @Function: removePrefixByLuSir
	 * @Description: 鲁老师写的去目录前缀
	 * @param @param title
	 * @param @return    
	 * @return String    
	 * @date 2015年8月13日 下午3:47:42
	 * @throws
	 */
		
	public static String removePrefixByLuSir(String title){
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
		log.debug("first pattern: " + str);
		
		pattern = Pattern.compile("^(\\d+[\\.．\\-]){0,5}\\d*");
		matcher = pattern.matcher(str);
		str = matcher.replaceAll("");
		log.debug("second pattern: " + str);
		
		pattern = Pattern.compile("^[\\(|（]*.{1,2}[\\)|）]*[　|\\s|\\.|．|，|,|：|:|、]+");
		matcher = pattern.matcher(str);
		str = matcher.replaceAll("");
		log.debug("third pattern: " + str);
		
		pattern = Pattern.compile("^[\\(|（|\\[].{1,2}[\\)|）|\\]]");
		matcher = pattern.matcher(str);
		str = matcher.replaceAll("");		
		log.debug("fourth pattern: " + str);
		
		pattern = Pattern.compile("^◎");
		matcher = pattern.matcher(str);
		str = matcher.replaceAll("");		
		log.debug("fifth pattern: " + str);
		
		pattern = Pattern.compile("^[一二三四五六七八九十百千万]+[\\.|、]+");
		matcher = pattern.matcher(str);
		str = matcher.replaceAll("");		
		log.debug("sixth pattern: " + str);
		
		pattern = Pattern.compile("^[\\(|（]*[一二三四五六七八九十百千万]+[\\)|）]*[　|\\s|\\.|．|，|,|：|:|、]+");
		matcher = pattern.matcher(str);
		str = matcher.replaceAll("");
		log.debug("seven pattern: " + str);
		
		
		
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
		log.debug("seven pattern:" + str);
		for(String p:p2){
			if(str.endsWith(p)){
				str = str.replace(p, "");
				break;
			}
		}
		log.debug("eight pattern:" + str);
		*/
		
		pattern = Pattern
				.compile("^第[0-9一二三四五六七八九十百千万]+[节|章|篇|部分|单元|章节|上篇|下篇|课]+");
		 matcher = pattern.matcher(str);
		str = matcher.replaceAll("");		
		log.debug("nine pattern: " + str);
		
		return str.trim();
	}
}
