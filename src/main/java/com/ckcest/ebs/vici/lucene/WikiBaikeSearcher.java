package com.ckcest.ebs.vici.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.ckcest.ebs.TopicFocusGraph.AppConfig;

 
/**
 * @ClassName: WikiBaikeSearcher
 * @Description: 
 * @author GongJun
 * @date 2015年7月23日 下午10:27:07
 * @version V1.0  
 */

public class WikiBaikeSearcher {

	private static Logger log = Logger.getLogger(WikiBaikeSearcher.class);
	
	private static Version LUCENE_VERSION = Version.LUCENE_36;
	private static String filePath = AppConfig.getAppConfig().getProperty("Index_Lucene_Baike_ZhwikiPedia");
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
	 * @Function: isInWiki
	 * @Description: 验证是否是百科词条
	 * @param @param keyword
	 * @param @return
	 * @param @throws IOException    
	 * @return boolean    
	 * @date 2015年7月23日 下午11:22:09
	 * @throws
	 */
		
	public static boolean isInWiki(String keyword) throws IOException{
		Term term = new Term("Title",keyword);
		Query query = new TermQuery(term);
		TopDocs topDocs = indexSearcher.search(query, 1000);
		ScoreDoc[] hits = topDocs.scoreDocs; 
		log.debug("Hits nums: " + hits.length);
		return hits.length > 0 ? true : false;
	}
}
