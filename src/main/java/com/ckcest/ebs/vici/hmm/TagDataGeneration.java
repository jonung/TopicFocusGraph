package com.ckcest.ebs.vici.hmm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import com.ckcest.ebs.TopicFocusGraph.AppConfig;
import com.ckcest.ebs.vici.base.Book;
import com.ckcest.ebs.vici.base.BookSupport;
import com.ckcest.ebs.vici.lucene.EngineeringBookCatalogSearcher;
import com.ckcest.ebs.vici.nlp.FNLP;
import com.ckcest.ebs.vici.util.FileUtil;

 
/**
 * @ClassName: TagDataGeneration
 * @Description: 
 * @author GongJun
 * @date 2015年8月3日 下午4:40:06
 * @version V1.0  
 */

public class TagDataGeneration {
	private static Logger log = Logger.getLogger(TagDataGeneration.class);
	//存储词性及其编码
	private static Map<String,Integer> observationMap = new HashMap<String,Integer>();
	private static int start = 1;
	
	private static String outPath = "data//tagDataForHMM//";
	
	public static void main(String[] args) throws CorruptIndexException, IOException{
		String filePath = AppConfig.getAppConfig().getProperty("Index_MetaTag");
    	IndexReader indexReader = IndexReader.open(FSDirectory.open(new File(filePath)));
    	
    	int count = 50;
    	
    	List<String> tBook = new ArrayList<String>();
    	
    	for(int i = 0; i < indexReader.numDocs(); i ++){
    		Document doc = indexReader.document(i);
    		String clc = doc.get("CLC");
    		
    		if(clc != null && clc.startsWith("T") && count < 100){
    			String BookNo = doc.get("BookNo"); 
    			tBook.add(BookNo);
    			/*
    			List<String> catalogs = EngineeringBookCatalogSearcher.getCatalogListByBookNo(BookNo);
    			Book book = new Book(BookNo,clc);
    			book.setCatalogs(catalogs);
    			book.setSameHieCatalogs(BookSupport.initSameHieChapterMap(catalogs));
    			
    			generateTagData(book.getSameHieCatalogs());
    			
    			count ++;
    			*/
    		}
    		
    	}
    	
    	
    	Random rand = new Random();
    	for(int i = 0; i < count; i ++){
    		int index = rand.nextInt(tBook.size());
    		String bookNo = tBook.get(index);
    		
    		List<String> catalogs = EngineeringBookCatalogSearcher.getCatalogListByBookNo(bookNo);
			Book book = new Book(bookNo,"T");
			book.setCatalogs(catalogs);
			book.setSameHieCatalogs(BookSupport.initSameHieChapterMap(catalogs));
			
			generateTagData(book.getSameHieCatalogs(),bookNo);
    	}
    	
	}
	
	public static void generateTagData(Map<Integer,List<String>> chapterMap, String bookNo){
		for(int key : chapterMap.keySet()){
			List<String> chapterList = chapterMap.get(key);
			
			for(int i = 0; i < chapterList.size(); i ++){
				String chapter = chapterList.get(i);
				log.debug(chapter);				
				String[][] seg_arr = FNLP.getInstance().tag(chapter);
				
				if( seg_arr != null && seg_arr.length > 0 && seg_arr[0].length <= 10){
					StringBuilder tagStr = new StringBuilder();
					StringBuilder wordStr = new StringBuilder();
					
					for(int m = 0; m < seg_arr[1].length; m ++)
						tagStr.append(seg_arr[1][m] + " ");
					
					for(int m = 0; m < seg_arr[0].length; m ++)
						wordStr.append(seg_arr[0][m] + " ");
					
					String str_in = wordStr.append("\n").append(tagStr).append("\n").toString();
					try {
						FileUtil.write2File(str_in, outPath + bookNo + ".txt");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
				
				
			}
		}
	}
	
	
}
