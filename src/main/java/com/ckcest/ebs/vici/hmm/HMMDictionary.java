package com.ckcest.ebs.vici.hmm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ckcest.ebs.vici.util.FileUtil;

 
/**
 * @ClassName: HMMDictionary
 * @Description: 
 * @author GongJun
 * @date 2015年8月6日 下午8:22:12
 * @version V1.0  
 */

public class HMMDictionary {
	private static Logger log = Logger.getLogger(HMMDictionary.class);
	
	//数值化，编号
	//状态对应的编号
	private static Map<String,Integer> stateDic = new HashMap<String,Integer>();
	//词性对对应的变换
	private static Map<HmmObservation,Integer> observationDic = new HashMap<HmmObservation,Integer>();
	//编号到状态
	private static Map<Integer,String> num2StateDic = new HashMap<Integer,String>();
	
	
	/**
	 * @Function: save2File
	 * @Description: 词典存入文件
	 * @param @param directory    
	 * @return void    
	 * @date 2015年8月13日 下午10:03:33
	 * @throws
	 */
		
	public static void save2File(String directory){
		FileUtil.deleteFilesInDirectory(directory);
		
		String state2no = directory + "state2no.txt";
		String observation2no = directory + "observation.txt";
		String no2state = directory + "no2state.txt";
		
		StringBuilder sb = new StringBuilder();
		for(String state: stateDic.keySet() ){
			String line = state + "--->" + stateDic.get(state);
			sb.append(line + "\n");
		}
		FileUtil.overWrite2File(sb.toString(), state2no);
		
		sb = new StringBuilder();
		for(HmmObservation obser: observationDic.keySet() ){
			PosPair pp = (PosPair) obser;
			String line = pp.getPrePos() + "," + pp.getBacPos() + "--->" + observationDic.get(obser);
			sb.append(line + "\n");
		}
		FileUtil.overWrite2File(sb.toString(), observation2no);
		
		sb = new StringBuilder();
		for(Integer no : num2StateDic.keySet() ){
			String line = no + "--->" + num2StateDic.get(no);
			sb.append(line + "\n");
		}
		FileUtil.overWrite2File(sb.toString(), no2state);
	}
	
	
	/**
	 * @Function: loadFromFile
	 * @Description: TODO
	 * @param @param directory    
	 * @return void    
	 * @date 2015年8月16日 下午7:32:23
	 * @throws
	 */
		
	public static void loadFromFile(String directory){
		log.info("**********************loading hmm dic ***********************");
		
		String state2no = directory + "state2no.txt";
		String observation2no = directory + "observation.txt";
		String no2state = directory + "no2state.txt";
		
		File file = null;
		BufferedReader br = null;
		String line = "";
		try {
			//
			file = new File(state2no);
			br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null){
				if(line.contains("--->")){
					String[] arr = line.split("--->");
					stateDic.put(arr[0], Integer.parseInt(arr[1]));
				}
			}
			br.close();
			//
			file = new File(observation2no);
			br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null){
				if(line.contains("--->")){
					String[] arr = line.split("--->");
					String[] pos_arr = arr[0].split(",");
					HmmObservation posPair = new PosPair(pos_arr[0], pos_arr[1]);
					observationDic.put(posPair, Integer.parseInt(arr[1]));
				}
			}
			br.close();
			//
			file = new File(no2state);
			br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null){
				if(line.contains("--->")){
					String[] arr = line.split("--->");
					num2StateDic.put(Integer.parseInt(arr[0]), arr[1]);
				}
			}
			br.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
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
