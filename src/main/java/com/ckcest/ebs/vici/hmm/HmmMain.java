package com.ckcest.ebs.vici.hmm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;

import com.ckcest.ebs.TopicFocusGraph.AppConfig;
import com.ckcest.ebs.vici.base.Book;
import com.ckcest.ebs.vici.base.BookSupport;
import com.ckcest.ebs.vici.lucene.EngineeringBookCatalogSearcher;
import com.ckcest.ebs.vici.nlp.FNLP;
import com.ckcest.ebs.vici.util.FileUtil;

 
/**
 * @ClassName: HmmMain
 * @Description: 
 * @author GongJun
 * @date 2015年8月6日 下午9:37:45
 * @version V1.0  
 */

public class HmmMain {
	public static Logger log = Logger.getLogger(HmmMain.class);
	public static String HMM_DATA_PATH = AppConfig.getAppConfig().getProperty("HMM_DATA_PATH");
	
	public static void main(String[] args) throws CorruptIndexException, IOException{
		MyHmmModelSupport m = new MyHmmModelSupport(HMM_DATA_PATH + "tagDataForHMM\\");
		m.init();
		
		MyHmmModel myhmm = m.getMyHmm();
		
		Hmm<ObservationInteger> hmm = UseJahmm.getInitHmm(myhmm);
		
		/*
		//随机生成观察序列，用于hmm的学习
		List<List<ObservationInteger>> sequences = generateLearnObservation();
		//learn a bettern hmm
		hmm = UseJahmm.learnHmm(hmm, sequences);
		*/
		
		StringBuilder observationValues = new StringBuilder();
		for(HmmObservation hmo: HMMDictionary.getObservationDic().keySet()){
			//System.out.println(hmo.toString());
			observationValues.append(hmo.toString() + "\n");
		}
		//观察值集合
		FileUtil.overWrite2File(observationValues.toString(), HMM_DATA_PATH + "model\\observationValues.txt");
		//hmm模型输出
		FileUtil.overWrite2File(hmm.toString(), HMM_DATA_PATH + "model\\hmm.txt");
		//对观察序列进行预测，得到状态序列，并解码
		predict(hmm);
		
	}
	
	
	/**
	 * @Function: predict
	 * @Description: 给定观察序列，预测其隐藏状态序列，并解码
	 * @param @param hmm
	 * @param @throws CorruptIndexException
	 * @param @throws IOException    
	 * @return void    
	 * @date 2015年8月7日 下午4:05:09
	 * @throws
	 */
		
	public static void predict(Hmm<ObservationInteger> hmm) throws CorruptIndexException, IOException{
		String filePath = AppConfig.getAppConfig().getProperty("Index_MetaTag");
    	IndexReader indexReader = IndexReader.open(FSDirectory.open(new File(filePath)));
    	//要处理的书本数目
    	int count = 10;
    	
    	List<String> tBook = new ArrayList<String>();
    	
    	for(int i = 0; i < indexReader.numDocs(); i ++){
    		Document doc = indexReader.document(i);
    		String clc = doc.get("CLC");
    		
    		if(clc != null && clc.startsWith("T") && count < 100){
    			String BookNo = doc.get("BookNo"); 
    			tBook.add(BookNo);
    		}
    		
    	}
    	//随机挑选书本
    	Random rand = new Random();
    	for(int i = 0; i < count; i ++){
    		int index = rand.nextInt(tBook.size());
    		String bookNo = tBook.get(index);
    		
    		List<String> catalogs = EngineeringBookCatalogSearcher.getCatalogListByBookNo(bookNo);
			Book book = new Book(bookNo,"T");
			book.setCatalogs(catalogs);
			book.setSameHieCatalogs(BookSupport.initSameHieChapterMap(catalogs));
			//
			//generateTagData(book.getSameHieCatalogs(),bookNo);
			
			Map<Integer,List<String>> catalogMap = book.getSameHieCatalogs();
			for(int m : catalogMap.keySet() ){
				List<String> temp = catalogMap.get(m);
				for(int x = 0; x < temp.size(); x ++){
					String catalog = temp.get(x);
					
					CatalogEntry catalogEntry = CatalogEntrySupport.getACatalogEntry(catalog);
					if(!catalogEntry.isValid())
						continue;
					int[] stateSquence = hmm.mostLikelyStateSequence(catalogEntry.getObservationSequence());
					
					catalogEntry.setStateSquence(stateSquence);
					
					CatalogEntrySupport.parseStateSequence(catalogEntry);
					
					//hmm结果输出到文本
					StringBuilder predictStr = new StringBuilder();
					predictStr.append("catalog :  " + catalogEntry.getCatalog() + "\n");
					predictStr.append("FNLP seg:  " + FNLP.getInstance().tag2String(catalogEntry.getCatalog()) + "\n");
					predictStr.append("HMM res :  ");
					for(int y = 0; y < catalogEntry.getRes().size(); y ++){
						predictStr.append(catalogEntry.getRes().get(y) + " ");
					}
					predictStr.append("\n******************************************");
					
					FileUtil.write2File(predictStr.toString(), HMM_DATA_PATH + "predict\\" + book.getBookNo() + ".txt");
				}
			}
    	}
    	
	}
	
	
	/**
	 * @Function: generateLearnObservation
	 * @Description: TODO
	 * @param @return    
	 * @return List<List<ObservationInteger>>    
	 * @date 2015年8月7日 下午4:09:58
	 * @throws
	 */
		
	public static List<List<ObservationInteger>> generateLearnObservation(){
		List<List<ObservationInteger>> sequences = new ArrayList<List<ObservationInteger>>();
		
		return sequences;
	}
}
