package com.ckcest.ebs.vici.extraction;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ckcest.ebs.vici.base.Book;
import com.ckcest.ebs.vici.base.FocusData;
import com.ckcest.ebs.vici.base.TopicData;
import com.ckcest.ebs.vici.base.TopicFocusPairData;

 
/**
 * @ClassName: TopicFocusExtraction
 * @Description: 
 * @author GongJun
 * @date Jul 21, 2015 8:50:00 PM
 * @version V1.0  
 */

public class TopicFocusExtraction {
	
	private static Logger log = Logger.getLogger(TopicFocusExtraction.class);
	
	private static double TOTAL_LEVEL_FOR_NORMALIZATION = 30.0;
	
	/**
	 * @throws IOException 
	 * @Function: extract
	 * @Description: 按每一本书进行处理
	 * @param @param book    
	 * @return void    
	 * @date Jul 21, 2015 9:29:50 PM
	 * @throws
	 */
		
	public static void extract(Book book) throws IOException{
		String bookNo = book.getBookNo();
		String clc = book.getClc();
		
		Map<Integer,List<String>> sameHieCatalogs = book.getSameHieCatalogs();
		for(int i : sameHieCatalogs.keySet()){
			List<String> catalogs = sameHieCatalogs.get(i);
			
			for(int j = 0; j <catalogs.size(); j ++){
				String catalog = catalogs.get(j);
				double hie = hieNormalization(j + 1, catalogs.size());
				if(catalog.endsWith("？"))//问号结尾大多比较长
					continue;
				extractCatalog(catalog, hie, clc);
			}
		}
		
		/*
		List<String> catalogs = book.getCatalogs();
		
		for(int i = 0; i < catalogs.size(); i ++){
			String catalog = catalogs.get(i);
			extractCatalog(catalog);
		}
		*/
	}
	
	
	/**
	 * @throws IOException 
	 * @Function: extractCatalog
	 * @Description: 对每一个目录进行抽取，抽取的关键
	 * @param @param catalog    
	 * @return void    
	 * @date Jul 21, 2015 9:31:15 PM
	 * @throws
	 */
		
	public static void extractCatalog(String catalog, double hie, String clc) throws IOException{
		//处理“的”的情况
		if(catalog.contains("的")){
			String[] arr = catalog.split("的");
			if(arr.length == 2){
				String initTopic = arr[0];
				String initFocus = arr[1];
				
				String topic = TopicExtractionSupport.getTopic(initTopic);
				List<String> focusList = FocusExtractionSupport.getFocusList(initFocus);
				
				for(int j = 0; j < focusList.size(); j ++ ){
					String focus = focusList.get(j);
					addFocusData(focus, clc, hie);
				
					
					if(topic.length() > 0){
						//topic->focus pair数据
						String pair = topic + "---->" + focus;
						TopicFocusPairData.topicFocusPair.add(pair);
						Integer pairNum = TopicFocusPairData.pairNum.get(pair);
						TopicFocusPairData.pairNum.put(pair,pairNum == null ? 1 : pairNum + 1);
						
						Set<String> topic2Focus = TopicData.topic2Focus.get(topic);
						if(topic2Focus == null){
							topic2Focus = new HashSet<String>();
						}
						topic2Focus.add(focus);
						
						TopicData.topic2Focus.put(topic, topic2Focus);
					}
				}
				
				if(topic.length() > 0){
					//topic数据
					addTopicData(topic, clc);
					
				}
				
				
			}
			
		}
		else{
			if(TopicExtractionSupport.isInBaikeEntrey(catalog)){
				addTopicData(catalog, clc);
			}
			else{
				if(FocusData.focusSet.contains(catalog)){
					addFocusData(catalog, clc, hie);
				}
			}
			
		}
	}
	
	public static void addTopicData(String topic, String clc){
		TopicData.topicSet.add(topic);
		Integer topicNum = TopicData.topic2Num.get(topic);
		TopicData.topic2Num.put(topic, topicNum == null ? 1:topicNum + 1);
		
		Set<String> clcSet = TopicData.topic2Clc.get(topic);
		if(clcSet == null)
			clcSet = new HashSet<String>();
		clcSet.add(clc);
		TopicData.topic2Clc.put(topic, clcSet);

	}

	public static void addFocusData(String focus, String clc, double hie){
		FocusData.focusSet.add(focus);
		Integer focusNum = FocusData.focus2Num.get(focus);
		FocusData.focus2Num.put(focus, focusNum == null ? 1 : focusNum + 1);
		FocusData.focus2Hie.put(focus, focusNum == null ? hie : FocusData.focus2Hie.get(focus) + hie );
		
		Set<String> clcSet = FocusData.focus2Clc.get(focus);
		if(clcSet == null){
			clcSet = new HashSet<String>();
		}
		clcSet.add(clc);
		FocusData.focus2Clc.put(focus, clcSet );
	}
	
	/**
	 * @Function: hieNormalization
	 * @Description: 用于将hie标准化
	 * @param @param level
	 * @param @param total
	 * @param @return    
	 * @return double    
	 * @date 2015年7月26日 上午10:57:36
	 * @throws
	 */
		
	public static double hieNormalization(int level, int total){
		double res = 0.0;
		
		res = (double) level * TOTAL_LEVEL_FOR_NORMALIZATION / (double)total;
		
		return res;
	}
}
