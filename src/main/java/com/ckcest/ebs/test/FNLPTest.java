package com.ckcest.ebs.test;

import java.util.HashMap;

import org.fnlp.nlp.cn.CNFactory;

 
/**
 * @ClassName: FNLPTest
 * @Description: 
 * @author GongJun
 * @date 2015年8月3日 下午4:23:15
 * @version V1.0  
 */

public class FNLPTest {
	public static void main(String[] args) throws Exception {
		String s = " ";
		System.out.println(s.length());
	    // 创建中文处理工厂对象，并使用“models”目录下的模型文件初始化
	    CNFactory factory = CNFactory.getInstance("FNLP_models");

	    // 使用分词器对中文句子进行分词，得到分词结果
	    String[] words = factory.seg("关注自然语言处理、语音识别、深度学习等方向的前沿技术和业界动态。");
	    
	    String[][] arr = factory.tag(" 1");
	    System.out.println(arr.length + " " + arr[0].length);
	    for(int i = 0; i < arr.length; i ++){
	    	for(int j = 0; j < arr[0].length; j ++)
	    		System.out.print(arr[i][j] + " ");
	    	System.out.println();
	    }
	    // 打印分词结果
	    for(String word : words) {
	        System.out.print(word + " ");
	    }
	     System.out.println();
	     
	     String result = factory.tag2String("铅精矿的烧结焙烧");
	     System.out.println(result);
	     
	     HashMap<String, String> entity = factory.ner("铅精矿的烧结焙烧。");

	     // 显示标注结果
	     System.out.println(entity);
	     
	}
}
