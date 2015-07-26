package com.ckcest.ebs.vici.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

 
/**
 * @ClassName: TopicData
 * @Description: 
 * @author GongJun
 * @date Jul 21, 2015 8:24:33 PM
 * @version V1.0  
 */

public class TopicData {
	//topic集合
	public static Set<String> topicSet = new HashSet<String>();
	//topic分类信息
	public static Map<String,Set<String>> topic2Clc = new HashMap<String,Set<String>>();
	//topic统计次数
	public static Map<String,Integer> topic2Num = new HashMap<String,Integer>();
	//每个topic对应的focus
	public static Map<String,Set<String>> topic2Focus = new HashMap<String,Set<String>>();
}
