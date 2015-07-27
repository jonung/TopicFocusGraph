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
	//public static Map<String,Set<String>> focus2Clc = new HashMap<String, Set<String>>();
	public static Map<String,Map<String,Integer>> focus2Clc = new HashMap<String, Map<String,Integer>>();
	//每个focus的出现次数
	public static Map<String,Integer> focus2Num = new HashMap<String,Integer>();
	//每个focus的层次信息(累加的层次信息，最终要除以focus次数来确定平均层次信息)
	public static Map<String,Double> focus2Hie = new HashMap<String,Double>();
	
}
