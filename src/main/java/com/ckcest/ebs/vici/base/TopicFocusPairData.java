package com.ckcest.ebs.vici.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

 
/**
 * @ClassName: TopicFocusData
 * @Description: 
 * @author GongJun
 * @date Jul 21, 2015 9:39:00 PM
 * @version V1.0  
 */

public class TopicFocusPairData {
	//存储 topic---->focus 对
	public static Set<String> topicFocusPair = new HashSet<String>();
	//存储pair的出现次数
	//topic + "---->" + focus
	public static Map<String,Integer> pairNum = new HashMap<String,Integer>();
	
}
