package com.ckcest.ebs.vici.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

 
/**
 * @ClassName: SortUtil
 * @Description: 
 * @author GongJun
 * @date 2014-11-17 上午10:58:53
 * @version V1.0  
 */

public class SortUtil {
	
	
	/**
	 * @Function: sorIntegertMap
	 * @Description: sort map whose value is a integer by descending order
	 * @param @param m    
	 * @return ArrayList<Entry<String, Integer>>
	 * @date 2014-11-18 上午10:14:50
	 * @throws
	 */
		
	public static ArrayList<Entry<String, Integer>> sortIntegerMap(Map<String,Integer> m){
		
		//change
		ArrayList<Entry<String, Integer>> arrayList = new ArrayList<Entry<String, Integer>>(m.entrySet());
		//sort
		Collections.sort(arrayList, new Comparator<Map.Entry<String, Integer>>() {
		    public int compare(Map.Entry<String, Integer> map1, Map.Entry<String, Integer> map2) {
		        return (map2.getValue() - map1.getValue());
		    }
		});
		
		/*for (Entry<String, Integer> entry : arrayList) {
		    System.out.println(entry.getKey() + "\t" + entry.getValue());
		}*/
		return arrayList;
	}
	
	
	/**
	 * @Function: sortFloatMap
	 * @Description: sort map whose value is a float by descending order
	 * @param @param m    
	 * @return ArrayList<Entry<String, Float>>    
	 * @date 2014-11-18 上午10:17:44
	 * @throws
	 */
		
	public static ArrayList<Entry<String, Float>> sortFloatMap(Map<String,Float> m){
			
		//change
		ArrayList<Entry<String, Float>> arrayList = new ArrayList<Entry<String, Float>>(m.entrySet());
		//sort
		Collections.sort(arrayList, new Comparator<Map.Entry<String, Float>>(){
		    public int compare(Map.Entry<String, Float> map1, Map.Entry<String,Float> map2) {
			    return ((map2.getValue() - map1.getValue() == 0) ? 0: (map2.getValue() - map1.getValue() > 0) ? 1: -1);
		    }
		});
		/*//输出
		for (Entry<String, Float> entry : arrayList) {
		    System.out.println(entry.getKey() + "\t" + entry.getValue());
		}*/
		return arrayList;	
	}
	
	
	/**
	 * @Function: sortDoubleMap
	 * @Description: sort map whose value is a double by descending order
	 * @param @param m    
	 * @return ArrayList<Entry<String, Double>>    
	 * @date 2014-11-18 上午10:17:56
	 * @throws
	 */
		
	public static ArrayList<Entry<String, Double>> sortDoubleMap(Map<String,Double> m){
		
		//change
		ArrayList<Entry<String, Double>> arrayList = new ArrayList<Entry<String, Double>>(m.entrySet());
		//sort
		Collections.sort(arrayList, new Comparator<Map.Entry<String, Double>>(){
		    public int compare(Map.Entry<String, Double> map1, Map.Entry<String,Double> map2) {
		        return ((map2.getValue() - map1.getValue() == 0) ? 0 : (map2.getValue() - map1.getValue() > 0) ? 1 : -1);
		    }
		});
		/*//输出
		for (Entry<String, Double> entry : arrayList) {
		    System.out.println(entry.getKey() + "\t" + entry.getValue());
		}*/
		return arrayList;
	}	
}
