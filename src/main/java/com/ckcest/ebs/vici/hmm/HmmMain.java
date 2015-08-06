package com.ckcest.ebs.vici.hmm;

import com.ckcest.ebs.vici.nlp.FNLP;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;

 
/**
 * @ClassName: HmmMain
 * @Description: 
 * @author GongJun
 * @date 2015年8月6日 下午9:37:45
 * @version V1.0  
 */

public class HmmMain {
	public static void main(String[] args){
		MyHmmModelSupport m = new MyHmmModelSupport("D:\\gongjun\\workspace4\\TopicFocusGraph\\data\\hmm\\tagDataForHMM\\");
		m.init();
		
		MyHmmModel myhmm = m.getMyHmm();
		
		Hmm<ObservationInteger> hmm = UseJahmm.getInitHmm(myhmm);
		
		for(HmmObservation hmo: HMMDictionary.getObservationDic().keySet()){
			System.out.println(hmo.toString());
		}
		
		String testStr = "设计程序和设计原则";
		
		CatalogEntry catalogEntry = CatalogEntrySupport.getACatalogEntry(testStr);
		int[] stateSquence = hmm.mostLikelyStateSequence(catalogEntry.getObservationSequence());
		
		catalogEntry.setStateSquence(stateSquence);
		
		CatalogEntrySupport.parseStateSequence(catalogEntry);
		
		for(int i = 0; i < catalogEntry.getRes().size(); i ++)
			System.out.println(catalogEntry.getRes().get(i));
		
		System.out.println(FNLP.getInstance().tag2String(testStr));
	}
}
