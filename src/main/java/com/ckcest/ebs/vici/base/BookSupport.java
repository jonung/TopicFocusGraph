package com.ckcest.ebs.vici.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ckcest.ebs.test.CatalogItemParser;
import com.ckcest.ebs.vici.util.ChapterStringProcess;
import com.ckcest.ebs.vici.util.HanZiConversion;

 
/**
 * @ClassName: BookSupport
 * @Description: 
 * @author GongJun
 * @date 2015年7月25日 下午8:15:07
 * @version V1.0  
 */

public class BookSupport {
	private static Logger log = Logger.getLogger(BookSupport.class);
	
	public static int LING_LEVEL = 0;
	public static int ZHANG_LEVEL = 1;
	public static int JIE_LEVEL = 2;
	public static int SHUZI_LEVEL = 3;
	
	
	/**
	 * @Function: initSameHieChapterMap
	 * @Description: 将目录章节按照相同级别组织起来
	 * @param @param catalogs    
	 * @return void    
	 * @date 2015年7月25日 下午9:04:34
	 * @throws
	 */
		
	public static  Map<Integer,List<String>> initSameHieChapterMap(List<String> catalogs){
		Map<Integer,List<String>> sameHieCatalogMap = new HashMap<Integer,List<String>>();
		if(catalogs.isEmpty())	return sameHieCatalogMap;
		int keyCount = 0;
		List<String> zhang_entry = new ArrayList<String>();
		for(int i = 0; i < catalogs.size(); i ++){
			while(i < catalogs.size() && isZhangLevel(catalogs.get(i)) == ZHANG_LEVEL){
				String ch = removePrefix(catalogs.get(i), ZHANG_LEVEL);
				if(ch != null && ch != " " && ch.length() > 0 )
					zhang_entry.add(ch);
				i ++;
			}
			
			List<String> jie_shuzi_entry = new ArrayList<String>();
			for(; i < catalogs.size(); i ++){
				int flag = isZhangLevel(catalogs.get(i));
				if(flag == ZHANG_LEVEL){
					i --;
					break;
				}
				else{
					flag = isJieLevel(catalogs.get(i)) == JIE_LEVEL ? JIE_LEVEL : SHUZI_LEVEL;
					String ch = removePrefix(catalogs.get(i), flag);
					
					if(ch != null && ch != " " && ch.length() > 0 )
						jie_shuzi_entry.add(ch);
					
				}
			}
			
			if(jie_shuzi_entry.size() > 0)
				sameHieCatalogMap.put(keyCount ++ , jie_shuzi_entry);
		}
		
		if(zhang_entry.size() > 0)
			sameHieCatalogMap.put(keyCount ++ , zhang_entry);
		
		return sameHieCatalogMap;
	}
	
	
	/**
	 * @Function: isZhangLevel
	 * @Description: 目录是否是以“第X章” 开头的 : 第十七章 錫生产中的安全技术及劳动保护
	 * @param @param catalog
	 * @param @return    
	 * @return boolean    
	 * @date 2015年7月25日 下午9:08:20
	 * @throws
	 */
		
	public static int isZhangLevel(String catalog){
		String regex = "^\u7b2c\u5341?[\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d]\u7ae0.*";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(catalog);
		
		return match.matches() ? ZHANG_LEVEL : LING_LEVEL;
	}
	
	
	/**
	 * @Function: isJieLevel
	 * @Description: 判断目录是否以“第x节”开始: 第一节锌的历史及我国炼锌事业发展概况
	 * @param @param catalog
	 * @param @return    
	 * @return boolean    
	 * @date 2015年7月25日 下午9:58:43
	 * @throws
	 */
		
	public static int isJieLevel(String catalog){
		
		String regex = "^\u7b2c\u5341?[\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d]\u8282.*";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(catalog);
		
		return match.matches() ? JIE_LEVEL : LING_LEVEL;
	}
	
	
	/**
	 * @Function: isShuziLevel
	 * @Description: 判断目录是否是这样的：1.焙烧的目的与要求
	 * @param @param catalog
	 * @param @return    
	 * @return boolean    
	 * @date 2015年7月25日 下午11:00:07
	 * @throws
	 */
		
	public static int isShuziLevel(String catalog){
		String regex = "^[1-9]?[0-9]\u002e?.*";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(catalog);
		
		return match.matches() ? SHUZI_LEVEL: LING_LEVEL;
	}
	
	
	/**
	 * @Function: removePrefix
	 * @Description: 去掉“第三章”、“第三节”、”1.“这样的前缀
	 * @param @param catalog
	 * @param @param flag
	 * @param @return    
	 * @return String    
	 * @date 2015年7月25日 下午10:58:55
	 * @throws
	 */
		
	public static String removePrefix(String catalog, int flag){
		
		/*
		String zhang_regex = "^\u7b2c\u5341?[\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d]\u7ae0";
		String jie_regex = "^\u7b2c\u5341?[\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d]\u8282";
		String shuzi_regex = "^[1-9]?[0-9]";
		
		String regex = "";
		if(flag == ZHANG_LEVEL){
			regex = zhang_regex;
		}
		else if(flag == JIE_LEVEL){
			regex = jie_regex;
		}
		else{
			regex = shuzi_regex;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(catalog);
		
		String res = match.replaceFirst("").trim();
		//去掉这样的前缀：。
		//			§.
		//			附录 得到
		res = res.substring(res.lastIndexOf(" ") + 1);
		res = res.substring(res.indexOf(".") + 1);
		
		
		//繁体转简体
		res = HanZiConversion.convert2SimplifiedChinese(res);
		log.debug(catalog + "--->" + res);
		*/
		
		String res = ChapterStringProcess.preProcessChapter(catalog);
		
		
		return res;
	}
	
	/*
	 * 构造一棵目录树
	 */
	
	public static void constructCatalogTree(List<String> catalogs){
		
	}
	
}
