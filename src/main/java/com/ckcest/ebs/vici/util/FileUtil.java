package com.ckcest.ebs.vici.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

 
/**
 * @ClassName: FileUtil
 * @Description: 
 * @author GongJun
 * @date Jun 10, 2015 3:38:07 PM
 * @version V1.0  
 */

public class FileUtil {
	
	/**
	 * @throws IOException 
	 * @Function: write2File
	 * @Description: 将字符串写入文件，模式为追加写入。
	 * @param @param str_in
	 * @param @param out_path    
	 * @return void    
	 * @date Jun 10, 2015 3:40:03 PM
	 * @throws
	 */
		
	public static void write2File(String str_in, String out_path) {
		File file =new File(out_path);
		try{
			
			if(file.exists()==false)
				file.createNewFile();
			FileWriter fw = new FileWriter(file,true);//true代表追加写入
	        // 将缓冲对文件的输出
	        BufferedWriter bw = new BufferedWriter(fw); 
	        // 定义一个String类型的变量,用来每次读取一行
	        // 写入文件
	        bw.write(str_in);      
	        bw.newLine();
	        // 刷新该流的缓冲
	        bw.flush(); 
	        bw.close();          
	        fw.close();   
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @throws IOException 
	 * @Function: overWrite2File
	 * @Description: 删除文件已经存在的内容，写入新内容
	 * @param @param str_in
	 * @param @param out_path    
	 * @return void    
	 * @date Jun 10, 2015 3:45:50 PM
	 * @throws
	 */
		
	public static void overWrite2File(String str_in, String out_path) {
		try{
			File file =new File(out_path);// 
			if(file.exists()==false)
				file.createNewFile();
			FileWriter fw = new FileWriter(file,false);//true代表追加写入
	        // 将缓冲对文件的输出
	        BufferedWriter bw = new BufferedWriter(fw); 
	        // 定义一个String类型的变量,用来每次读取一行
	        // 写入文件
	        bw.write(str_in);      
	        bw.newLine();
	        // 刷新该流的缓冲
	        bw.flush(); 
	        bw.close();          
	        fw.close(); 
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * @throws IOException 
	 * @Function: overWriteList2File
	 * @Description: 将一个list的内容写入文件，覆盖原来的文件
	 * @param @param src
	 * @param @param out_path    
	 * @return void    
	 * @date Jun 10, 2015 3:51:23 PM
	 * @throws
	 */
		
	public static void overWriteList2File(List<String> src_list, String out_path) throws IOException{
		StringBuilder sb = new StringBuilder();
		
		for(String each : src_list){
			sb.append(each + "\n");
			
		}
		
		overWrite2File(sb.toString(), out_path);
	}
	
	
	/**
	 * @Function: writeSet2File
	 * @Description: TODO
	 * @param @param set
	 * @param @param out_path
	 * @param @throws IOException    
	 * @return void    
	 * @date 2015年7月23日 下午10:10:21
	 * @throws
	 */
		
	public static void writeSet2File(Set<String> set, String out_path) throws IOException{
		StringBuilder sb = new StringBuilder();
		
		for(String s : set){
			sb.append(s + "\n");
		}
		
		overWrite2File(sb.toString(), out_path);
	}
	
	
	/**
	 * @Function: writeMap2File
	 * @Description: TODO
	 * @param @param m
	 * @param @param out_path
	 * @param @throws IOException    
	 * @return void    
	 * @date 2015年7月23日 下午10:10:18
	 * @throws
	 */
		
	public static void writeMap2File(Map<String,Integer> m, String out_path) throws IOException{
		StringBuilder sb = new StringBuilder();
		
		for(String s : m.keySet()){
			sb.append(s + "->" + String.valueOf(m.get(s)) + "\n");
		}
		
		overWrite2File(sb.toString(), out_path);
	}
	
	
	public static void writeArrayList2File(ArrayList<Entry<String,Integer>> array, String out_path) throws IOException{
		StringBuilder sb = new StringBuilder();
		for(Entry<String,Integer> entry : array){
			sb.append(entry.getKey() + "->" + entry.getValue() + "\n");
		}
		overWrite2File(sb.toString(), out_path);
	}
}
