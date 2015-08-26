package com.ckcest.ebs.vici.hmm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import be.ac.ulg.montefiore.run.jahmm.draw.GenericHmmDrawerDot;

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
		//路径
		String tag_data_path = HMM_DATA_PATH + "tagDataForHMM\\";
		String modelPath = HMM_DATA_PATH + "model\\hmm.txt";
		String model_aux_path = HMM_DATA_PATH + "model\\hmm_model_aux.txt";
		
		String dicDirectory = HMM_DATA_PATH + "dic\\";
		//训练模型
		//Hmm<ObservationInteger> hmm = trainHmmModel(tag_data_path,modelPath,model_aux_path);
		
		//字典映射表存入文件
		//HMMDictionary.save2File(dicDirectory);
		
		//画图数据
		//(new GenericHmmDrawerDot()).write(hmm, HMM_DATA_PATH + "learntHmm.dot");
		
		//导入模型
		Hmm<ObservationInteger> loadedHmm =loadModel(modelPath,model_aux_path); 
		//导入字典映射文件
		HMMDictionary.loadFromFile(dicDirectory);
		//HMMDictionary.save2File("1.txt");
		//预测
		predict(loadedHmm);
		/*
		double sum = 0.0;
		int count = 10;
		for(int i = 0; i < count; i ++){
			sum += predict(loadedHmm);
		}
		
		log.info("average rate is : " + sum / count);
		*/
	}
	
	
	
		
	
	/**
	 * @Function: loadModel
	 * @Description: 从本地文件得到hmm模型
	 * @param @param modelPath
	 * @param @param modelAuxPath
	 * @param @return
	 * @param @throws CorruptIndexException
	 * @param @throws IOException    
	 * @return Hmm<ObservationInteger>    
	 * @date 2015年8月13日 下午7:56:19
	 * @throws
	 */
		
	public static Hmm<ObservationInteger> loadModel(String modelPath, String modelAuxPath) throws CorruptIndexException, IOException{
		int nState = 0;
		int nObser = 0;
		
		double[] pi = null;
		double[][] A= null;
		double[][] B = null;
		
		File aux_file = new File(modelAuxPath);
		File model_file = new File(modelPath);
		BufferedReader br = new BufferedReader(new FileReader(aux_file));
		
		String line = br.readLine();
		nState = Integer.parseInt(line.split(":")[1]);
		line = br.readLine();
		nObser = Integer.parseInt(line.split(":")[1]);
		br.close();
		
		pi = new double[nState];
		A = new double[nState][nState];
		B = new double[nState][nObser];
		
		br = new BufferedReader(new FileReader(model_file));
		line = br.readLine();
		line = br.readLine();
		
		
		for(int s = 0; s < nState; s ++){
			line = br.readLine();
			line = br.readLine();
			
			String pi_s = line.split(":")[1].trim();
			pi[s] = Double.parseDouble(pi_s);
			
			line = br.readLine();
			String[] a_s = line.split(":")[1].trim().split(" ");
			for(int i = 0; i < a_s.length; i ++)
				A[s][i] = Double.parseDouble(a_s[i]);
			
			line = br.readLine();
			String[] b_s = line.split("---")[1].trim().split(" ");
			for(int i = 0; i < b_s.length; i ++){
				B[s][i] = Double.parseDouble(b_s[i]);
			}
			line = br.readLine();
		}
		
		br.close();
		
		MyHmmModel myhmm = new MyHmmModel(pi, A, B); 
		Hmm<ObservationInteger> hmm = UseJahmm.getInitHmm(myhmm);
		
		/*
		FileUtil.deleteFilesInDirectory(HMM_DATA_PATH + "predict"); 
		log.info("***************************predict***************************");
		//对观察序列进行预测，得到状态序列，并解码
		predict(hmm);
		*/
		return hmm;
	}
	
	
	
		
	
	/**
	 * @Function: trainHmmModel
	 * @Description: 根据标注数据训练得到模型，并将模型保存到本地
	 * @param @param tagDataPath
	 * @param @param modelPath
	 * @param @param mode_aux_path
	 * @param @return
	 * @param @throws CorruptIndexException
	 * @param @throws IOException    
	 * @return Hmm<ObservationInteger>    
	 * @date 2015年8月13日 下午8:00:14
	 * @throws
	 */
		
	public static Hmm<ObservationInteger> trainHmmModel(String tagDataPath, String modelPath, String mode_aux_path) throws CorruptIndexException, IOException{
		//清除旧文件
		
		FileUtil.deleteFilesInDirectory(HMM_DATA_PATH + "model"); 
		
		//
		MyHmmModelSupport m = new MyHmmModelSupport(tagDataPath);
		//MyHmmModelSupport m = new MyHmmModelSupport(HMM_DATA_PATH + "tagDataForHMM\\");
		m.init();
		
		MyHmmModel myhmm = m.getMyHmm();
		
		Hmm<ObservationInteger> hmm = UseJahmm.getInitHmm(myhmm);
		
		/*
		//随机生成观察序列，用于hmm的学习
		List<List<ObservationInteger>> sequences = generateLearnObservation();
		//learn a bettern hmm
		hmm = UseJahmm.learnHmm(hmm, sequences);
		*/
		
		log.info("******output model and observation values!*************");
		StringBuilder observationValues = new StringBuilder();
		for(HmmObservation hmo: HMMDictionary.getObservationDic().keySet()){
			//System.out.println(hmo.toString());
			observationValues.append(hmo.toString() + "\n");
		}
		//观察值集合
		FileUtil.overWrite2File(observationValues.toString(), HMM_DATA_PATH + "model\\observationValues.txt");
		//hmm模型输出
		FileUtil.overWrite2File(hmm.toString(), modelPath);
		//FileUtil.overWrite2File(hmm.toString(), HMM_DATA_PATH + "model\\hmm.txt");
		
		StringBuilder sb = new StringBuilder();
		sb.append("nState:" + myhmm.getStateNum() + "\n");
		sb.append("bObservation:" + myhmm.getObservationNum());
		//FileUtil.overWrite2File(sb.toString(), HMM_DATA_PATH + "model\\hmm_model_aux.txt");
		FileUtil.overWrite2File(sb.toString(),  mode_aux_path);
		
		return hmm;
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
		
	public static double predict(Hmm<ObservationInteger> hmm) throws CorruptIndexException, IOException{
		log.info("***************************predict***************************");
		FileUtil.deleteFilesInDirectory(HMM_DATA_PATH + "predict"); 
		
		String filePath = AppConfig.getAppConfig().getProperty("Index_MetaTag");
    	IndexReader indexReader = IndexReader.open(FSDirectory.open(new File(filePath)));
    	//要处理的书本数目
    	int count = 20;
    	
    	List<String> tBook = new ArrayList<String>();
    	
    	for(int i = 0; i < indexReader.numDocs(); i ++){
    		Document doc = indexReader.document(i);
    		String clc = doc.get("CLC");
    		
    		if(clc != null && clc.startsWith("T") && count < 100){
    			String BookNo = doc.get("BookNo"); 
    			tBook.add(BookNo);
    		}
    		
    	}
    	
    	double sumOver = 0;
    	 
    	double right_count = 0;
    	//随机挑选书本
    	Random rand = new Random();
    	
    	for(int i = 0; i < count; i ++){
    		int index = rand.nextInt(tBook.size());
    		String bookNo = tBook.get(index);
    		//String bookNo = "06349999";
    		List<String> catalogs = EngineeringBookCatalogSearcher.getCatalogListByBookNo(bookNo);
			Book book = new Book(bookNo,"T");
			book.setCatalogs(catalogs);
			book.setSameHieCatalogs(BookSupport.initSameHieChapterMap(catalogs));
			//
			//generateTagData(book.getSameHieCatalogs(),bookNo);
			log.info("***********predict book " + book.getBookNo() +  "************");
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
					predictStr.append("catalog :  |||" + catalogEntry.getCatalog() + "\n");
					predictStr.append("FNLP seg:  |||" + FNLP.getInstance().tag2String(catalogEntry.getCatalog()) + "\n");
					predictStr.append("HMM res :  |||");
					
					//System.out.println(catalogEntry.getRes().size());
					for(int y = 0; y < catalogEntry.getRes().size(); y ++){
						predictStr.append(catalogEntry.getRes().get(y) + " ");
						
					}
					
					predictStr.append("\n");
					predictStr.append("Label   :  |||");
					for(int y = 0; y < catalogEntry.getRes().size(); y ++){
						predictStr.append("[]  ");
						
					}
					
					predictStr.append("\n******************************************");
					
					sumOver ++;
					if(catalogEntry.getRes().size() > 0){
						right_count ++;
						
						FileUtil.write2File(predictStr.toString(), HMM_DATA_PATH + "predict\\" + book.getBookNo() + ".txt");
					}
					
					
				}
			}
    	}
    	System.out.println(sumOver + " " + right_count);
    	double rate = (double)right_count / (double)sumOver;
    	 
    	log.info("正确率为   " + rate);
    	return rate;
	}
	
	
	/**
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 * @Function: generateLearnObservation
	 * @Description: TODO
	 * @param @return    
	 * @return List<List<ObservationInteger>>    
	 * @date 2015年8月7日 下午4:09:58
	 * @throws
	 */
		
	public static List<List<ObservationInteger>> generateLearnObservation() throws CorruptIndexException, IOException{
		log.info("********************generating learn data*****************************");
		
		List<List<ObservationInteger>> sequences = new ArrayList<List<ObservationInteger>>();
		
		String filePath = AppConfig.getAppConfig().getProperty("Index_MetaTag");
    	IndexReader indexReader = IndexReader.open(FSDirectory.open(new File(filePath)));
    	//要处理的书本数目
    	int count = 10000;
    	
    	List<String> tBook = new ArrayList<String>();
    	
    	for(int i = 0; i < indexReader.numDocs(); i ++){
    		Document doc = indexReader.document(i);
    		String clc = doc.get("CLC");
    		
    		if(clc != null && clc.startsWith("T")){
    			String BookNo = doc.get("BookNo"); 
    			tBook.add(BookNo);
    		}
    		
    	}
    	
    	//System.out.println("tBook size: " + tBook.size());
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
					
					//Exception in thread "main" java.lang.IllegalArgumentException: Observation sequence too short
					if(catalogEntry.getObservationSequence().size() >= 2)
						sequences.add( catalogEntry.getObservationSequence() );
				}
			}
    	}
		
		
		return sequences;
	}
}
