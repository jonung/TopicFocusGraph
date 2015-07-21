package com.ckcest.ebs.vici.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

 
/**
 * @ClassName: FocusData
 * @Description: 
 * @author GongJun
 * @date Jul 21, 2015 8:17:15 PM
 * @version V1.0  
 */

public class FocusData {
	//focus的集合
	public static Set<String> focusSet = new HashSet<String>();
	//每个focus对应的分类信息
	public static Map<String,Set<String>> focus2Clc = new HashMap<String, Set<String>>();
	//每个focus的出现次数
	public static Map<String,Integer> focus2Num = new HashMap<String,Integer>();
	
	
	
}
