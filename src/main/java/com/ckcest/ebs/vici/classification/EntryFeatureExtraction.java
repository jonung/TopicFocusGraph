package com.ckcest.ebs.vici.classification;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

 
/**
 * @ClassName: EntryFeatureExtraction
 * @Description: 
 * @author GongJun
 * @date Aug 27, 2015 9:07:52 AM
 * @version V1.0  
 */

public class EntryFeatureExtraction {
	private static Logger log = Logger.getLogger(EntryFeatureExtraction.class);
	
	private final static int WINDOW = 3;
	
	
	/**
	 * @Function: extractionSampleEntry
	 * @Description: 对每一个SampleEntry进行特征提取，每一个SampleEntry有多少个短语，就能得到几条数据
	 * @param @param samEntryList
	 * @param @param level
	 * @param @return    
	 * @return List<EntryFeature>    
	 * @date 2015年8月27日 上午11:10:13
	 * @throws
	 */
		
	public static List<EntryFeature> extractionSampleEntry(List<SampleEntry> samEntryList, int level){
		SampleEntry sampleEntry = samEntryList.get(level);
		
		List<EntryFeature> entryFeatureList = new ArrayList<EntryFeature>();
		
		String[] phrase = sampleEntry.getPhrase();
		
		int deIndex = indexOfDe(sampleEntry.getSegArr());
		int conjIndex = indexOfConj(sampleEntry.getSegArr());
		
		log.debug("phrase size: " + phrase.length);
		for(int i = 0; i < phrase.length; i ++){
			EntryFeature entryFeature = new EntryFeature();
			entryFeature.setLabel(sampleEntry.getLabel()[i]);
			entryFeature.setPos(i);
			
			int phraseIndex = sampleEntry.getCatalog().indexOf(phrase[i]);
			
			if(deIndex != -1){	
				
				if(phraseIndex > deIndex){
					log.debug("preDe");
					entryFeature.setPreDe(true);
				}
				else{
					log.debug("bacDe");
					entryFeature.setBacDe(true);
				}
			}
			
			if(conjIndex != -1){
				if(phraseIndex > conjIndex){
					log.debug("preConj");
					entryFeature.setPreConj(true);
				}
				else{
					log.debug("bacConj");
					entryFeature.setBacConj(true);
				}
			}
			
			boolean contextFeature = isContextEmerge(samEntryList, WINDOW, level, phrase[i]);
			entryFeature.setContextEmerge(contextFeature);
			log.debug("context feature is " + contextFeature);
			
			
			entryFeatureList.add(entryFeature);
		}
		
		return entryFeatureList;
	}
	
	
	/**
	 * @Function: indexOfDe
	 * @Description: 结构助词位置
	 * @param @param segArr
	 * @param @return    
	 * @return int    
	 * @date 2015年8月27日 上午10:51:13
	 * @throws
	 */
		
	public static int indexOfDe(String[][] segArr){
		for(int i = 0; i < segArr[1].length; i ++){
			String cur = segArr[1][i];
			//log.debug("cur: " + cur);
			if(cur.equals("结构助词")){
				return i;
			}
		}
		return -1;
	}
	//并列连词位置
	
	/**
	 * @Function: indexOfConj
	 * @Description: 并列连词位置
	 * @param @param segArr
	 * @param @return    
	 * @return int    
	 * @date 2015年8月27日 上午10:51:06
	 * @throws
	 */
		
	public static int indexOfConj(String[][] segArr){ for(int i = 0; i < segArr[1].length; i ++){
			String cur = segArr[1][i];
			if(cur.equals("并列连词"))
				return i;
		}
		
		return -1;
	}
	
	
	/**
	 * @Function: isContextEmerge
	 * @Description: 固定窗口上下文是否出现相同的词
	 * @param @param sampleEntryList
	 * @param @param window
	 * @param @param cur
	 * @param @param word
	 * @param @return    
	 * @return boolean    
	 * @date 2015年8月27日 上午11:01:39
	 * @throws
	 */
		
	public static boolean isContextEmerge(List<SampleEntry> sampleEntryList, int window, int cur, String word){
		int left = cur - window,right = cur + window;
		if(left < 0)	left = 0;
		if(right >= sampleEntryList.size())	right = sampleEntryList.size() - 1;
		for(int i = left; i <= right; i ++){
			if(i != cur){
				String[] phrase = sampleEntryList.get(i).getPhrase();
				for(int j = 0; j < phrase.length; j ++){
					if(phrase.equals(word))
						return true;
				}
			}
		}
		return false;
	}
	
		
}
