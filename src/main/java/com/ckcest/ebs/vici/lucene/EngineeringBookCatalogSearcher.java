package com.ckcest.ebs.vici.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.ckcest.ebs.TopicFocusGraph.AppConfig;
import com.ckcest.ebs.vici.util.ChapterStringProcess;
import com.ckcest.ebs.vici.util.HanZiConversion;

 
/**
 * @ClassName: EngineeringBookCatalogSearcher
 * @Description: 
 * @author GongJun
 * @date Jun 10, 2015 8:27:44 PM
 * @version V1.0  
 */

public class EngineeringBookCatalogSearcher {
	
	private static Logger log = Logger.getLogger(EngineeringBookCatalogSearcher.class);
	
	private static Version LUCENE_VERSION = Version.LUCENE_36;
	private static String filePath = AppConfig.getAppConfig().getProperty("Index_Engineering_CatalogLucene_Definition");
	private static IndexReader indexReader = null;
	private static IndexSearcher indexSearcher = null;
	
	static {
		try {
			indexReader = IndexReader.open(FSDirectory.open(new File(filePath)));
			indexSearcher = new IndexSearcher(indexReader);
			
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @Function: getCataloListByKeyword
	 * @Description: 给定关键字，查询满足条件的chapter list
	 * @param @param keyword
	 * @param @return
	 * @param @throws CorruptIndexException
	 * @param @throws IOException    
	 * @return List<String>    
	 * @date Jun 10, 2015 8:40:49 PM
	 * @throws
	 */
		
	public static List<String> getCataloListByKeyword(String keyword) throws CorruptIndexException, IOException {
		List<String> l = new ArrayList<String>();
		
		Term term = new Term("Chapter",keyword);
		Query query = new TermQuery(term);
		TopDocs topDocs = indexSearcher.search(query, 100000);
		ScoreDoc[] hits = topDocs.scoreDocs; 
		
		for(int i = 0; i < hits.length; i ++) {
			
			Document doc = indexSearcher.doc(hits[i].doc);
			String chapter = doc.get("Chapter");
			chapter = ChapterStringProcess.preProcessChapter(chapter); 
			
			l.add(chapter);
			
			
		}
		return l;
	}
	
	
	/**
	 * @Function: getCatalogListByBookNo
	 * @Description: TODO
	 * @param @param BookNo
	 * @param @return
	 * @param @throws IOException    
	 * @return List<String>    
	 * @date 2015年7月25日 下午11:43:47
	 * @throws
	 */
		
	public static List<String> getCatalogListByBookNo(String BookNo) throws IOException{
		List<String> res = new ArrayList<String>();
		
		Term term = new Term("BookNo",BookNo);
		Query query = new TermQuery(term);
		TopDocs topDocs = indexSearcher.search(query, 1000);
		ScoreDoc[] hits = topDocs.scoreDocs; 
		//System.out.println("hits: " + hits.length);
		for(int i = 0; i < hits.length; i ++) {
			Document doc = indexSearcher.doc(hits[i].doc);
			String chapter = doc.get("Chapter");
			
			log.debug(chapter);
			//chapter = ChapterStringProcess.preProcessChapter(chapter);
			//chapter = HanZiConversion.convert2SimplifiedChinese(chapter);
			res.add(chapter);
			
		}
		
		return res;
	}
	
	/**
	 * @Function: getCatalogs
	 * @Description: 得到一本书的所有目录，以及每条目录在整本书中所处的位置
	 * @param @param BookNo
	 * @param @return
	 * @param @throws CorruptIndexException
	 * @param @throws IOException    
	 * @return Map<String,Integer>    
	 * @date Jun 10, 2015 8:46:34 PM
	 * @throws
	 */
		
	public static Map<String,Integer> getCatalogs(String BookNo) throws CorruptIndexException, IOException {
		Map<String,Integer> hierachy = new HashMap<String,Integer>();
		int initialHierachy = 0;
		
		Term term = new Term("BookNo",BookNo);
		Query query = new TermQuery(term);
		TopDocs topDocs = indexSearcher.search(query, 1000);
		ScoreDoc[] hits = topDocs.scoreDocs; 
		//System.out.println("hits: " + hits.length);
		for(int i = 0; i < hits.length; i ++) {
			Document doc = indexSearcher.doc(hits[i].doc);
			String chapter = doc.get("Chapter");
			
			
			chapter = HanZiConversion.convert2SimplifiedChinese(chapter);
			//System.out.println(chapter);
			hierachy.put(chapter, initialHierachy);
			initialHierachy ++;
			
		}
		
		return hierachy;
	}
	
	
	/**
	 * @Function: getCatalogByMutiKeyword
	 * @Description: TODO
	 * @param @param keyword
	 * @param @throws ParseException
	 * @param @throws IOException    
	 * @return void    
	 * @date Jun 10, 2015 8:52:48 PM
	 * @throws
	 */
		
	public static void getCatalogByMutiKeyword(String[] keyword) throws ParseException, IOException {
			
			
	        //which field to search
	        String[] fields={"Chapter","Chapter"};
	        //Occur.MUST表示对应字段必须有查询值， Occur.MUST_NOT 表示对应字段必须没有查询值
	        Occur[] occ={Occur.MUST,Occur.MUST};
	         
	        Query query=MultiFieldQueryParser.parse(LUCENE_VERSION, keyword,fields,occ,new IKAnalyzer());
	        TopDocs topDocs = indexSearcher.search(query,1000);
	 		ScoreDoc[] hits = topDocs.scoreDocs; 
	 		
	 		//System.out.println("hits: " + hits.length);
	 		for(int i = 0; i < hits.length; i ++) {
	 			Document doc = indexSearcher.doc(hits[i].doc);
	 			String chapter = doc.get("Chapter");
	 			String bookNo = doc.get("BookNo");
	 			//System.out.println(bookNo);
	 			//System.out.println(chapter);
	 		}
		}
	}
