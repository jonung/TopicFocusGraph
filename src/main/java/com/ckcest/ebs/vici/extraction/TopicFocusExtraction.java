package com.ckcest.ebs.vici.extraction;

import java.util.List;

import org.apache.log4j.Logger;

import com.ckcest.ebs.vici.base.Book;

 
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
	 * @Function: extract
	 * @Description: 按每一本书进行处理
	 * @param @param book    
	 * @return void    
	 * @date Jul 21, 2015 9:29:50 PM
	 * @throws
	 */
		
	public static void extract(Book book){
		String bookNo = book.getBookNo();
		String clc = book.getClc();
		
		List<String> catalogs = book.getCatalogs();
		
		for(int i = 0; i < catalogs.size(); i ++){
			String catalog = catalogs.get(i);
			extractCatalog(catalog);
		}
	}
	
	
	/**
	 * @Function: extractCatalog
	 * @Description: 对每一个目录进行抽取，抽取的关键
	 * @param @param catalog    
	 * @return void    
	 * @date Jul 21, 2015 9:31:15 PM
	 * @throws
	 */
		
	public static void extractCatalog(String catalog){
		//处理“的”的情况
		if(catalog.contains("的")){
			String[] arr = catalog.split("的");
			if(arr.length == 2){
				String initTopic = arr[0];
				String initFocus = arr[1];
				
				log.info("Topic: " + initTopic);
				log.info("focus: " + initFocus);
			}
			
		}
	}
}
