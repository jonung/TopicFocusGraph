package com.ckcest.ebs.vici.hmm;

import java.util.HashMap;
import java.util.Map;

 
/**
 * @ClassName: HMMDictionary
 * @Description: 
 * @author GongJun
 * @date 2015年8月6日 下午8:22:12
 * @version V1.0  
 */

public class HMMDictionary {
	//数值化，编号
	private static Map<String,Integer> stateDic = new HashMap<String,Integer>();
	private static Map<HmmObservation,Integer> observationDic = new HashMap<HmmObservation,Integer>();
	
	private static Map<Integer,String> num2StateDic = new HashMap<Integer,String>();
	
	/**
	 * @return stateDic
	 */
	
	public static Map<String, Integer> getStateDic() {
		return stateDic;
	}
	/**
	 * @param stateDic 要设置的 stateDic
	 */
	
	public static void setStateDic(Map<String, Integer> stateDic) {
		HMMDictionary.stateDic = stateDic;
	}
	/**
	 * @return observationDic
	 */
	
	public static Map<HmmObservation, Integer> getObservationDic() {
		return observationDic;
	}
	/**
	 * @param observationDic 要设置的 observationDic
	 */
	
	public static void setObservationDic(Map<HmmObservation, Integer> observationDic) {
		HMMDictionary.observationDic = observationDic;
	}
	/**
	 * @return num2StaticDic
	 */
	
	public static Map<Integer, String> getNum2StateDic() {
		return num2StateDic;
	}
	/**
	 * @param num2StaticDic 要设置的 num2StaticDic
	 */
	
	public static void setNum2StateDic(Map<Integer, String> num2StaticDic) {
		HMMDictionary.num2StateDic = num2StaticDic;
	}
	
	
}
