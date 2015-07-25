package com.ckcest.ebs.TopicFocusGraph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import com.ckcest.ebs.vici.base.Book;
import com.ckcest.ebs.vici.base.FocusData;
import com.ckcest.ebs.vici.base.TopicData;
import com.ckcest.ebs.vici.base.TopicFocusPairData;
import com.ckcest.ebs.vici.extraction.TopicFocusExtraction;
import com.ckcest.ebs.vici.lucene.EngineeringBookCatalogSearcher;
import com.ckcest.ebs.vici.util.FileUtil;
import com.ckcest.ebs.vici.util.SortUtil;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger log = Logger.getLogger(App.class);
	
    public static void main( String[] args ) throws CorruptIndexException, IOException
    {
        System.out.println( "Hello World!" );
        tranverseBook();
        FileUtil.writeSet2File(TopicData.topicSet, "topicSet.txt");
        ArrayList<Entry<String, Integer>> topicArray = SortUtil.sortIntegerMap(TopicData.topic2Num);
        FileUtil.writeArrayList2File(topicArray, "topic2Num.txt");
        //FileUtil.writeMap2File(TopicData.topic2Num, "topic2Num.txt");
        log.info("Topic num : " + TopicData.topicSet.size());
        log.info("Focus NUm : " + FocusData.focusSet.size());
        FileUtil.writeSet2File(FocusData.focusSet, "focusSet.txt");
        //FileUtil.writeMap2File(FocusData.focus2Num, "focus2Num.txt");
        ArrayList<Entry<String,Integer>> focusArray = SortUtil.sortIntegerMap(FocusData.focus2Num);
        FileUtil.writeArrayList2File(focusArray , "focus2Num.txt");
        
        ArrayList<Entry<String,Integer>> pariArray = SortUtil.sortIntegerMap(TopicFocusPairData.pairNum);
        FileUtil.writeArrayList2File(pariArray, "pair.txt");
        
    }
    
    public static void tranverseBook() throws CorruptIndexException, IOException{
    	String filePath = AppConfig.getAppConfig().getProperty("Index_MetaTag");
    	IndexReader indexReader = IndexReader.open(FSDirectory.open(new File(filePath)));
    	
    	for(int i = 0; i < indexReader.numDocs(); i ++){
    		Document doc = indexReader.document(i);
    		String clc = doc.get("CLC");
    		
    		if(clc != null && clc.startsWith("TP")){
    			String BookNo = doc.get("BookNo"); 
    			List<String> catalogs = EngineeringBookCatalogSearcher.getCatalogListByBookNo(BookNo);
    			Book book = new Book(BookNo,clc);
    			book.setCatalogs(catalogs);
    			
    			TopicFocusExtraction.extract(book);
    		}
    		
    	}
    }
}
