package com.ckcest.ebs.vici.extraction;

import java.io.IOException;
import java.util.List;

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
		
		List<String> catalogs = book.getCatalogs();
		
		for(int i = 0; i < catalogs.size(); i ++){
			String catalog = catalogs.get(i);
			extractCatalog(catalog);
		}
		
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
		
	public static void extractCatalog(String catalog) throws IOException{
		//处理“的”的情况
		if(catalog.contains("的")){
			String[] arr = catalog.split("的");
			if(arr.length == 2){
				String initTopic = arr[0];
				String initFocus = arr[1];
				
				if(TopicSupport.isInBaikeEntrey(initTopic)){
					TopicData.topicSet.add(initTopic);
					Integer topicNum = TopicData.topic2Num.get(initTopic);
					TopicData.topic2Num.put(initTopic, topicNum == null ? 1:topicNum + 1);
					
					String pair = initTopic + "--->" + initFocus;
					TopicFocusPairData.topicFocusPair.add(pair);
					Integer pairNum = TopicFocusPairData.pairNum.get(pair);
					TopicFocusPairData.pairNum.put(pair, pairNum == null ? 1 : pairNum +1);
				}
				
				FocusData.focusSet.add(initFocus);
				Integer focusNum = FocusData.focus2Num.get(initFocus);
				FocusData.focus2Num.put(initFocus, focusNum == null ? 1 : focusNum + 1);
				
				//log.info("Topic: " + initTopic);
				//log.info("focus: " + initFocus);
			}
			
		}
	}
}
