package com.ckcest.ebs.vici.hmm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

 
/**
 * @ClassName: HMMParamEstimation
 * @Description: 
 * @author GongJun
 * @date Aug 5, 2015 9:06:31 PM
 * @version V1.0  
 */

public class HMMParamEstimation {

	private static Logger log = Logger.getLogger(HMMParamEstimation.class);
	
	private static Set<String> hideState = new HashSet<String>();
	
	static{
		hideState.add("[");
		hideState.add("]");
		hideState.add("][");
		hideState.add("I");
		hideState.add("O");
	}
	
	public static void readFile(String filePath){
		File file = new File(filePath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String tagLine = "";
			String posLine = "";
			
			boolean flag = true;
			
			while((tagLine = br.readLine()) != null && (posLine = br.readLine()) != null){
				flag = true;
				
				String[] arr = tagLine.split(" ");
				String[] posArr = posLine.split(" ");
				
				StringBuilder tag = new StringBuilder();
				StringBuilder word = new StringBuilder();
				
				for(int i = 0; i < arr.length; i ++){
					if(hideState.contains(arr[i])){
						tag.append(arr[i] + " ");
						
						i = i + 1;
						word.append(arr[i]);
					}
					else{
						flag = false;
						break;
					}
				}
				
				String[] tagArr = tag.toString().split(" ");
				String[] wordArr = word.toString().split(" ");
				
				if(tagArr.length != wordArr.length + 1)
					flag = false;
				
				if(flag){
					//标注合法，继续处理
					for(String t : tagArr)
						System.out.print(t + "   ");
					System.out.println();
					for(String t : wordArr)
						System.out.print(t + "   ");
					
					System.out.println();
					for(String t : posArr)
						System.out.print(t + "   ");
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
	
	public static void main(String[] args){
		String filePath = "/Users/vici/Documents/workspace/TopicFocusGraph/data/06319232_1.txt";
		readFile(filePath);
	}
}
