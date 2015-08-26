package com.ckcest.ebs.vici.hmm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

 
/**
 * @ClassName: MyHmmModelSupport
 * @Description: 
 * @author GongJun
 * @date 2015年8月6日 下午8:00:02
 * @version V1.0  
 */

public class MyHmmModelSupport {
	
	private static Logger log = Logger.getLogger(MyHmmModelSupport.class);
	
	//存储每一个隐藏状态单独出现的次数
	private Map<String,Integer> state2Num = new HashMap<String,Integer>();
	//存储hide state间共现的次数，一个key可以是：[ --> ]  
	private Map<Tag2Tag,Integer> statePair2Num = new HashMap<Tag2Tag,Integer>();
	//存储词性对和隐藏状态共现的次数
	//比如说一个key可以是: 名词-I-名词
	private Map<ObservationOccurWithState,Integer> observationWithState2Num = new HashMap<ObservationOccurWithState,Integer>();
	
	
	//隐藏状态集合
	private Map<String,Double> state2Pi = new HashMap<String,Double>();
	
	//观察状态取值集合
	private Set<HmmObservation> observationSet = new HashSet<HmmObservation>();
	
	private MyHmmModel myHmm;
	
	private String fileListPath;
	
	public void init(){
		state2Pi.put("[", 0.5);
		state2Pi.put("]", 0.0);
		state2Pi.put("][", 0.0);
		state2Pi.put("I", 0.0);
		state2Pi.put("O", 0.5);
		
		readTagData(fileListPath);
		calculateParam();
		
	}
	
	public MyHmmModelSupport(String fileListPath){
		this.fileListPath = fileListPath;
	}
	
	private void readTagData(String fileListPath){
		File[] fileList = new File(fileListPath).listFiles();
		log.debug("file size: " + fileListPath.length() );
		for(File file : fileList ){
			if(file.getName().endsWith("_1.txt"))
				readFile(file.getAbsolutePath());
		}
		
	}
	
	/**
	 * @Function: readFile
	 * @Description: 读一个标注好的文件，进行处理
	 * @param @param filePath    
	 * @return void    
	 * @date 2015年8月5日 下午10:58:18
	 * @throws
	 */
		
	private  void readFile(String filePath){
		
		log.info("Read file: " + filePath);
		File file = new File(filePath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String tagLine = "";
			String posLine = "";
			
			boolean flag = true;
			
			while((tagLine = br.readLine()) != null && (posLine = br.readLine()) != null){
				flag = true;
				
				String[] arr = tagLine.split(" ");
				String newPosLine = "S " + posLine + "E"; //句子前面添加S，句子后面添加E
				String[] posArr =  newPosLine.split(" ");
//				System.out.println(newPosLine);
				
				StringBuilder tag = new StringBuilder();
				StringBuilder word = new StringBuilder();
				
				for(int i = 0; i < arr.length; i ++){
					if(state2Pi.containsKey(arr[i]) ){//确保标注合法
						tag.append(arr[i] + " ");
						
						i = i + 1;
						if(i < arr.length)
							word.append(arr[i] + " ");
					}
					else{
						flag = false;
						break;
					}
				}
				
				String[] tagArr = tag.toString().split(" ");
				String[] wordArr = word.toString().split(" ");
				
				
				if(tagArr.length != wordArr.length + 1 && wordArr.length == posArr.length - 2)
					flag = false;
				
				if(flag){
					//标注合法，继续处理
					
					collectData(tagArr, posArr);
				}
				
				br.readLine();
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
	
	private void collectData(String[] tagArr, String[] posArr){
		//[   I   I   I   I   ][   I   ] 
		//S 名词   名词   名词   名词   名词   动词   名词   E
		
		for(int i = 0; i < tagArr.length; i ++){
			String curTag = tagArr[i];
			incrementValueInMap(state2Num, curTag);
			
			PosPair posPair = new PosPair(posArr[i],posArr[i+1]);
			//记录观察值
			//HMMModel.getObservationSet().add(posPair);
			observationSet.add(posPair);
			
			PosPairWithTag pos2tag = new PosPairWithTag(posPair, curTag);
			incrementValueInMap(observationWithState2Num, pos2tag);
			
			
			int j = i + 1;
			if(j < tagArr.length){
				Tag2Tag t2t = new Tag2Tag(curTag, tagArr[j]);
				incrementValueInMap(statePair2Num, t2t);
			}
			
			
		}
	}
	
	
	/**
	 * @Function: calculateParam
	 * @Description: TODO
	 * @param     
	 * @return void    
	 * @date 2015年8月13日 下午4:10:16
	 * @throws
	 */
		
	private void calculateParam(){
		int nState = state2Pi.size();
		int nObservation = observationSet.size();
		
		double[][] A = new double[nState][nState];
		double[][] B = new double[nState][nObservation];
		
		//编号
		int base = 0;
		for(String state : state2Pi.keySet() ){
			HMMDictionary.getStateDic().put(state, base);
			base ++;
		}
		
		for(String state: HMMDictionary.getStateDic().keySet() ){
			String value = state;
			Integer key = HMMDictionary.getStateDic().get(state);
			
			HMMDictionary.getNum2StateDic().put(key, value);
		}
		
		base = 0;
		
		for(HmmObservation observation : observationSet) {
			HMMDictionary.getObservationDic().put(observation, base);
			base ++;
		}
		
		//状态转移矩阵A
		
		for(Tag2Tag t2t : statePair2Num.keySet() ){
			String preTag = t2t.getPreTag();
			String bacTag = t2t.getBacTag();
			
			double value = (double) statePair2Num.get(t2t) / (double) state2Num.get(preTag);
			
			int i = HMMDictionary.getStateDic().get(preTag);
			int j = HMMDictionary.getStateDic().get(bacTag);
			
			A[i][j] = value;
		}
		
		
		//发射矩阵B
		
		for(ObservationOccurWithState observatino2tag : observationWithState2Num.keySet() ){
			PosPairWithTag pos2Tag = (PosPairWithTag) observatino2tag;
			
			String tag = pos2Tag.getTag();
			//引入拉普拉斯平滑
			double value = (double) ( observationWithState2Num.get(observatino2tag) + 1 ) / (double) ( state2Num.get(tag) + observationSet.size());
			
			int i = HMMDictionary.getStateDic().get(tag);
			int j = HMMDictionary.getObservationDic().get(pos2Tag.getPosPair());
			
			B[i][j] = value;
		}
		//平滑处理
		for(int i = 0; i < B.length; i ++){
			for(int j = 0; j < B[0].length; j ++){
				if(B[i][j] == 0.0){
					String tag = HMMDictionary.getNum2StateDic().get(i);
					B[i][j] = 1.0 / (double) (state2Num.get(tag) + observationSet.size());
				}
			}
		}
		
		double[] pi = new double[state2Pi.size()];
		for(String tag : state2Pi.keySet() ){
			int index = HMMDictionary.getStateDic().get(tag);
			pi[index] = state2Pi.get(tag);
		}
		
		myHmm = new MyHmmModel(pi, A, B);
	}
	
	
	/**
	 * @Function: loadHmmModel
	 * @Description: 从本地加载一个训练好的hmm模型
	 * @param @param modelPath
	 * @param @return    
	 * @return MyHmmModel    
	 * @date 2015年8月13日 下午6:53:20
	 * @throws
	 */
		
	public static MyHmmModel loadHmmModel(String modelPath){
		int nState = 0;
		int nObser = 0;
		
		double[] pi = null;
		double[][] A= null;
		double[][] B = null;
		
		File modleFile = new File(modelPath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(modleFile));
			String line = br.readLine();
			//read nState and nObser
			nState = Integer.parseInt(line.split(":")[1]);
			line = br.readLine();
			nObser = Integer.parseInt(line.split(":")[1]);
			
			pi = new double[nState];
			A = new double[nState][nState];
			B = new double[nState][nObser];
			//read pi
			line = br.readLine();
			String[] piStr = line.split(" ");
			for(int i = 0; i < piStr.length; i ++){
				double temp = Double.parseDouble(piStr[i]);
				pi[i] = temp;
			}
			//read A
			for(int m = 0; m < nState; m ++){
				line = br.readLine();
				String[] aa = line.split(" ");
				for(int i = 0; i < aa.length; i ++){
					double temp = Double.parseDouble(aa[i]);
					A[m][i] = temp;
				}
			}
			//read B
			for(int m = 0; m < nState; m ++){
				line = br.readLine();
				String[] bb = line.split(" ");
				for(int i = 0; i < bb.length; i ++){
					double temp = Double.parseDouble(bb[i]);
					B[m][i] = temp;
				}
			}
			
			br.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyHmmModel myHmm = new MyHmmModel(pi, A, B);
		
		return myHmm;
	}
	
	/**
	 * @Function: incrementValueInMap
	 * @Description: 统计次数，不存在初始化为1，存在加1
	 * @param @param map
	 * @param @param key    
	 * @return void    
	 * @date 2015年8月5日 下午10:14:46
	 * @throws
	 */
		
	public static void incrementValueInMap(Map<String,Integer> map, String key){
		Integer value = map.get(key);
		map.put(key, value == null ? 1 : value + 1);
	}
	
	public static void incrementValueInMap(Map<ObservationOccurWithState,Integer> map, PosPairWithTag key){
		Integer value = map.get(key);
		map.put(key, value == null ? 1 : value + 1);
	}
	
	public static void incrementValueInMap(Map<Tag2Tag,Integer> map, Tag2Tag key){
		Integer value = map.get(key);
		map.put(key, value == null ? 1 : value + 1);
	}


	/**
	 * @return myHmm
	 */
	
	public MyHmmModel getMyHmm() {
		return myHmm;
	}


	/**
	 * @param myHmm 要设置的 myHmm
	 */
	
	public void setMyHmm(MyHmmModel myHmm) {
		this.myHmm = myHmm;
	}
	
	
}
