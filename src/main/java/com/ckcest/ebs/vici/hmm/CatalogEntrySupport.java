package com.ckcest.ebs.vici.hmm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;

import com.ckcest.ebs.vici.nlp.FNLP;

 
/**
 * @ClassName: CatalogEntrySupport
 * @Description: 
 * @author GongJun
 * @date 2015年8月6日 下午9:52:57
 * @version V1.0  
 */

public class CatalogEntrySupport {
	
	private static Logger log = Logger.getLogger(CatalogEntrySupport.class);
	
	/**
	 * @Function: getACatalogEntry
	 * @Description: 工厂方法，得到一个CatalogEntry实例
	 * @param @param catalog
	 * @param @return    
	 * @return CatalogEntry    
	 * @date 2015年8月6日 下午10:28:14
	 * @throws
	 */
		
	public static CatalogEntry getACatalogEntry(String catalog){
		CatalogEntry catalogEntry = new CatalogEntry(catalog);
		
		String[][] seg_arr = FNLP.getInstance().tag(catalogEntry.getCatalog());
		catalogEntry.setSegArr(seg_arr);
		
		if( seg_arr != null && seg_arr.length > 0 && seg_arr[0].length <= 10){
			
			List<ObservationInteger> observationSequenc = new ArrayList<ObservationInteger>();
			
			String startPos = "S";
			String endPos = "E";
			
			String prePos = "";
			String bacPos = "";
			
			bacPos = seg_arr[1][0];
			HmmObservation posPair = new PosPair(startPos, bacPos);
			
			if(HMMDictionary.getObservationDic().get(posPair) == null){
				catalogEntry.setValid(false);
			
			}
			else
				observationSequenc.add(new ObservationInteger(HMMDictionary.getObservationDic().get(posPair)));
			
			for(int m = 0; m < catalogEntry.getSegArr()[1].length - 1; m ++){
				prePos = seg_arr[1][m];
				bacPos = seg_arr[1][m+1];
				posPair = new PosPair(prePos, bacPos);
				
				log.debug(posPair.toString());
				if(HMMDictionary.getObservationDic().get(posPair) == null){
					catalogEntry.setValid(false);
					break;
				}
					
				
				
				observationSequenc.add(new ObservationInteger(HMMDictionary.getObservationDic().get(posPair)));
			}
			
			posPair = new PosPair(seg_arr[1][seg_arr[1].length - 1], endPos);
			if(HMMDictionary.getObservationDic().get(posPair) == null){
				catalogEntry.setValid(false);
				
			}
			else{
				observationSequenc.add(new ObservationInteger(HMMDictionary.getObservationDic().get(posPair)));
			
				catalogEntry.setObservationSequence(observationSequenc);
				catalogEntry.setValid(true);
			}
		}
		
		
			
		return catalogEntry;
	}
	
	
	
	/**
	 * @Function: parseStateSequence
	 * @Description: 根据状态序列还原到词序列
	 * @param @param catalogEntry
	 * @param @return    
	 * @return CatalogEntry    
	 * @date 2015年8月6日 下午10:27:30
	 * @throws
	 */
		
	public static CatalogEntry parseStateSequence(CatalogEntry catalogEntry){
		List<String> res = new ArrayList<String>();
		
		int[] stateSequence = catalogEntry.getStateSquence();
		for(int i = 0; i < stateSequence.length; i ++){
			String curStr = HMMDictionary.getNum2StateDic().get(stateSequence[i]);
			
			
			if(curStr == "["){
				StringBuilder sb = new StringBuilder();
				boolean flag = false;
				sb.append(catalogEntry.getSegArr()[0][i]);
				
				i = i + 1;
				while(i < stateSequence.length ){
					String next = HMMDictionary.getNum2StateDic().get(stateSequence[i]);
					if(next == "I"){
						sb.append(catalogEntry.getSegArr()[0][i]);
						i ++;
					}
					else if(next == "]"){
						//sb.append(catalogEntry.getSegArr()[0][i]);
						flag = true;
						//i ++;
						break;
					}
					else{
						break;
					}
				}
				
				if(flag)
					res.add(sb.toString());
				i --;
			}
		}
		catalogEntry.setRes(res);
		
		return catalogEntry;
	}
}
