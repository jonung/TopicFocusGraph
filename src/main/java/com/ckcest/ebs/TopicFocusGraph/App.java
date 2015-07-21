package com.ckcest.ebs.TopicFocusGraph;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import com.ckcest.ebs.vici.base.Book;
import com.ckcest.ebs.vici.extraction.TopicFocusExtraction;
import com.ckcest.ebs.vici.lucene.EngineeringBookCatalogSearcher;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws CorruptIndexException, IOException
    {
        System.out.println( "Hello World!" );
        tranverseBook();
    }
    
    public static void tranverseBook() throws CorruptIndexException, IOException{
    	String filePath = AppConfig.getAppConfig().getProperty("Index_MetaTag");
    	IndexReader indexReader = IndexReader.open(FSDirectory.open(new File(filePath)));
    	
    	for(int i = 0; i < indexReader.numDocs(); i ++){
    		Document doc = indexReader.document(i);
    		String clc = doc.get("CLC");
    		
    		if(clc != null && clc.startsWith("TP")){
    			String BookNo = doc.get("BookNo"); 
    			List<String> catalogs = EngineeringBookCatalogSearcher.getCatalogListByBookNO(BookNo);
    			Book book = new Book(BookNo,clc);
    			book.setCatalogs(catalogs);
    			
    			TopicFocusExtraction.extract(book);
    		}
    		
    	}
    }
}
