package com.ckcest.ebs.vici.classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import tw.edu.ntu.csie.libsvm.service.UseLibSVM;

import com.ckcest.ebs.TopicFocusGraph.AppConfig;
import com.ckcest.ebs.vici.util.FileUtil;

 
/**
 * @ClassName: ClassificationMain
 * @Description: 
 * @author GongJun
 * @date 2015年8月27日 上午11:16:45
 * @version V1.0  
 */

public class ClassificationMain {
	private static Logger log = Logger.getLogger(ClassificationMain.class);
	
	private static String CLASSIFICATION_CORPUS_PATH = AppConfig.getAppConfig().getProperty("CLASSIFICATION_CORPUS_PATH");
	
	private static List<BookDocument> corpus = new ArrayList<BookDocument>();
	private static List<EntryFeature> featureList = new ArrayList<EntryFeature>();
	
	public static void main(String[] args) throws IOException{
		loadCorpusFile(CLASSIFICATION_CORPUS_PATH + "corpus");
		//extractFeatures();
		//save2File();
		
		extractAndPredict();
	}
	
	public static void save2File(){
		FileUtil.deleteExistFile(CLASSIFICATION_CORPUS_PATH + "libsvm\\train.txt");
		
		for(int i = 0; i < featureList.size(); i ++){
			StringBuilder sb = new StringBuilder();
			EntryFeature entryFeature = featureList.get(i);
			
			System.out.println("label: " + entryFeature.getLabel());
			if(entryFeature.getLabel() == 1){
				sb.append("+1");
			}
			else{
				sb.append("-1");
			}
			
			int no = 1;
			sb.append(" " + no + ":" + (entryFeature.isPreDe() == true ? 1 : 0));
			no ++;
			sb.append(" " + no + ":" + (entryFeature.isBacDe() == true ? 1 : 0));
			
			no ++;
			sb.append(" " + no + ":" + (entryFeature.isPreConj() == true ? 1 : 0));
			
			no ++;
			sb.append(" " + no + ":" + (entryFeature.isBacConj() == true ? 1 : 0));
			
			no ++;
			sb.append(" " + no + ":" + (entryFeature.isContextEmerge() == true ? 1 : 0));
			no ++;
			
			int pos = entryFeature.getPos();
			String posStr = int2bin(pos);
			for(int x = 0; x < posStr.length(); x ++){
				sb.append(" " + no + ":" + posStr.charAt(x));
				no ++;
			}
			
			FileUtil.write2File(sb.toString(), CLASSIFICATION_CORPUS_PATH + "libsvm\\train.txt");
		}
	}
	
	public static String int2bin(int pos){
		String ret = "";
		while(pos != 0){
			int mod = pos % 2;
			pos = pos / 2;
			
			ret = String.valueOf(mod) + ret;
		}
		//System.out.println(ret + " " + ret.length());
		int sum = 3 - ret.length();
		for(int i = 0; i < sum; i ++)
			ret = "0"+ret;
		return ret;
	}
	public static void extractFeatures(){
		for(int i = 0; i < corpus.size(); i ++){
			BookDocument bookDocument = corpus.get(i);
			
			List<SampleEntry> sampleEntryList = bookDocument.getEntryList();
			
			for(int j = 0 ;j < sampleEntryList.size(); j ++){
				
				List<EntryFeature> entryFeatureList = EntryFeatureExtraction.extractionSampleEntry(sampleEntryList, j);
				
				featureList.addAll(entryFeatureList);
			}
		}
	}
	
	public static void loadCorpusFile(String fileListPath){
		File[] fileList = new File(fileListPath).listFiles();
		log.debug("file size: " + fileListPath.length() );
		for(File file : fileList ){
			//if(file.getName().endsWith("_1.txt")){
				BookDocument bookDocument = BookDocumentSupport.createBookDocument(file.getAbsolutePath());
				
				corpus.add(bookDocument);
			//}
				
		}
	}
	
	public static void extractAndPredict() throws IOException{
		FileUtil.deleteExistFile(CLASSIFICATION_CORPUS_PATH + "res.txt");
		for(int i = 0; i < corpus.size(); i ++){
			BookDocument bookDocument = corpus.get(i);
			
			List<SampleEntry> sampleEntryList = bookDocument.getEntryList();
			
			for(int j = 0 ;j < sampleEntryList.size(); j ++){
				
				List<EntryFeature> entryFeatureList = EntryFeatureExtraction.extractionSampleEntry(sampleEntryList, j);
				SampleEntry sampleEntry = sampleEntryList.get(j);
				StringBuilder sssb = new StringBuilder();
				sssb.append("catalog: " + sampleEntry.getCatalog() + "\n");
				for(int x = 0; x < entryFeatureList.size(); x ++){
					EntryFeature entryFeature = entryFeatureList.get(x);
					
					StringBuilder sb = new StringBuilder();
					
					//System.out.println("label: " + entryFeature.getLabel());
					if(entryFeature.getLabel() == 1){
						sb.append("+1");
					}
					else{
						sb.append("-1");
					}
					
					int no = 1;
					sb.append(" " + no + ":" + (entryFeature.isPreDe() == true ? 1 : 0));
					no ++;
					sb.append(" " + no + ":" + (entryFeature.isBacDe() == true ? 1 : 0));
					
					no ++;
					sb.append(" " + no + ":" + (entryFeature.isPreConj() == true ? 1 : 0));
					
					no ++;
					sb.append(" " + no + ":" + (entryFeature.isBacConj() == true ? 1 : 0));
					
					no ++;
					sb.append(" " + no + ":" + (entryFeature.isContextEmerge() == true ? 1 : 0));
					no ++;
					
					int pos = entryFeature.getPos();
					String posStr = int2bin(pos);
					for(int y = 0; y < posStr.length(); y ++){
						sb.append(" " + no + ":" + posStr.charAt(y));
						no ++;
					}
					
					FileUtil.overWrite2File(sb.toString(), CLASSIFICATION_CORPUS_PATH + "libsvm\\test.txt");
					
					UseLibSVM.predict();
					
					BufferedReader br = new BufferedReader(new FileReader(new File(CLASSIFICATION_CORPUS_PATH + "libsvm\\out.txt")));
					
					String line = br.readLine();
					br.close();
					
					
					String curWord= sampleEntry.getPhrase()[x];
					
					System.out.println("predict label: " + line);
					if(line.equals("1.0")){
						//System.out.println(curWord + " is a topic.");
						sssb.append(curWord + "  :  topic.\n");
					}
					else{
						//System.out.println(curWord + " is a focus.");
						sssb.append(curWord + "  :  focus.\n");
					}
					
				}
				
				FileUtil.write2File(sssb.toString(),CLASSIFICATION_CORPUS_PATH + "res.txt");
				featureList.addAll(entryFeatureList);
			}
		}
	}
}
