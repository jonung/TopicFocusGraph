package com.ckcest.ebs.vici.util;

import org.apache.log4j.Logger;

import com.hankcs.hanlp.HanLP;

 
/**
 * @ClassName: HanZiConversion
 * @Description: 用于汉字的中繁体转换
 * @author GongJun
 * @date Jun 10, 2015 4:07:52 PM
 * @version V1.0  
 */

public class HanZiConversion {
	
	private static Logger log = Logger.getLogger(HanZiConversion.class);
	
	/**
	 * @Function: convert2SimplifiedChinese
	 * @Description: 中文繁体->简体
	 * @param @param trad_str
	 * @param @return    
	 * @return String    
	 * @date Jun 10, 2015 4:12:10 PM
	 * @throws
	 */
		
	public static String convert2SimplifiedChinese(String trad_str){
		String res = HanLP.convertToSimplifiedChinese(trad_str);
		log.debug(trad_str + "--->" + res);
		return res;
	}
	
	
	/**
	 * @Function: convert2TraditionalChinese
	 * @Description: 中文简体->繁体
	 * @param @param simp_str
	 * @param @return    
	 * @return String    
	 * @date Jun 10, 2015 4:12:31 PM
	 * @throws
	 */
		
	public static String convert2TraditionalChinese(String simp_str){
		String resString = HanLP.convertToTraditionalChinese(simp_str);
		log.debug(simp_str + "--->" + resString);
		return resString;
	}
}
