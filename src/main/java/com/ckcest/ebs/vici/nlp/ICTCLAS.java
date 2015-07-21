package com.ckcest.ebs.vici.nlp;

import com.ckcest.ebs.TopicFocusGraph.AppConfig;

 
/**
 * @ClassName: ICTCLAS
 * @Description: 
 * @author GongJun
 * @date 2015-5-21 下午9:07:23
 * @version V1.0  
 */

public class ICTCLAS {
	
	public static boolean flag = false;
	
	
	/**
	 * @Function: init
	 * @Description: ictclas的初始化操作
	 * @param @return    
	 * @return boolean    
	 * @date Jun 10, 2015 9:09:15 PM
	 * @throws
	 */
		
	public static boolean init(){
		//String argu = "D:\\ICTCLAS2015";
		String argu = AppConfig.getAppConfig().getProperty("ictclas2015Path");
		// String system_charset = "GBK";//GBK----0
		//String system_charset = "UTF-8";
		int charset_type = 1;
		
		int init_flag = CLibrary.Instance.NLPIR_Init(argu, charset_type, "0");
		String nativeBytes = null;

		if (0 == init_flag) {
			nativeBytes = CLibrary.Instance.NLPIR_GetLastErrorMsg();
			System.err.println("初始化失败！fail reason is "+nativeBytes);
			return false;
		}
		
		flag = true;
		
		return true;
	}
	
	
	
	/**
	 * @Function: segStr
	 * @Description: 对输入的字符串分词，返回结果
	 * @param @param str
	 * @param @return    
	 * @return String    
	 * @date Jun 10, 2015 9:09:38 PM
	 * @throws
	 */
		
	public static String segStr(String str){
		if(!flag)
			init();
		
		String nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(str, 1);
		//System.out.println(nativeBytes);
		return nativeBytes;
	}
	
	
}
