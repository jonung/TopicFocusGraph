package com.ckcest.ebs.vici.extraction;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ckcest.ebs.vici.nlp.ICTCLAS;

 
/**
 * @ClassName: FocusExtractionSupport
 * @Description: 
 * @author GongJun
 * @date 2015年7月26日 上午10:41:08
 * @version V1.0  
 */

public class FocusExtractionSupport {
	private static Logger log = Logger.getLogger(FocusExtractionSupport.class);
	
	public static List<String> getFocusList(String initFocus){
		List<String> focusList = new ArrayList<String>();
		//分词
		String seg = ICTCLAS.segStr(initFocus);
		
		if(seg.contains("/cc")){
			String[] cc_arr = seg.split(" ");
			int cc_pos = -1;
			for(int i = 0; i < cc_arr.length; i ++){
				if(cc_arr[i].contains("/cc")){
					cc_pos = i;
					break;
				}
			}
			
			String cc_pre = "";
			String cc_bac = "";
			
			String focus = "";
			for(int j = 0; j < cc_pos; j ++){
				if(cc_arr[j].contains("/w")){
					if(focus.length() > 0 )
						focusList.add(focus);
					focus = "";
				}
				else{
					//log.info(cc_arr[j]);
					focus += removeSegTag(cc_arr[j]);
					
				}
			}
			
			if(focus.length() > 0){
				focusList.add(focus);
			}
			
			
			for(int j = cc_pos + 1; j < cc_arr.length; j ++){
				cc_bac += removeSegTag(cc_arr[j]);
			}
			
			focusList.add(cc_bac);
			//对前半部分进行标点检查
			/*
			if(cc_pre.contains("、")){
				String[] douhao = cc_pre.split("、");
				for(int m = 0; m <douhao.length; m ++)
					focusList.add(douhao[m]);
			}
			else{
				if(cc_pre.contains("，"))
			}
			*/
		}
		else{
			focusList.add(initFocus);
			
		}
		
		return focusList;
	}
	
	public static List<String> punctuationCheck(String str){
		List<String> res = new ArrayList<String>();
		
		return res;
	}
	
	/**
	 * @Function: removeSegTag
	 * @Description: 去掉词性tag
	 * @param @param str
	 * @param @return    
	 * @return String    
	 * @date 2015年7月26日 下午12:06:27
	 * @throws
	 */
		
	public static String removeSegTag(String str){
		return str.substring(0, str.indexOf("/"));
	}
}
