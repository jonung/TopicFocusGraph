package com.ckcest.ebs.vici.extraction;

import java.io.IOException;

import com.ckcest.ebs.vici.lucene.BaiduBaikeSearcher;
import com.ckcest.ebs.vici.lucene.HudongBaikeSearcher;
import com.ckcest.ebs.vici.lucene.WikiBaikeSearcher;

 
/**
 * @ClassName: TopicBaikeValidation
 * @Description: 
 * @author GongJun
 * @date 2015年7月23日 下午10:25:45
 * @version V1.0  
 */

public class TopicSupport {
	
	
	/**
	 * @Function: getTopic
	 * @Description: 抽取每条目录的topic（默认情况下，每一个目录只存在一个topic）
	 * @param @param initTopic
	 * @param @return
	 * @param @throws IOException    
	 * @return String    
	 * @date 2015年7月25日 下午7:21:45
	 * @throws
	 */
		
	public String getTopic(String initTopic) throws IOException{
		String topic = "";
		
		if(isInBaikeEntrey(initTopic)){
			//通过百科验证
			topic = initTopic;
		}
		else{
			//通过其他方法抽取
			
		}
		
		return topic;
	}
	
	/**
	 * @Function: isInBaikeEntrey
	 * @Description: 验证是否是百科词条
	 * @param @param topic
	 * @param @return
	 * @param @throws IOException    
	 * @return boolean    
	 * @date 2015年7月23日 下午11:25:08
	 * @throws
	 */
		
	public static boolean isInBaikeEntrey(String topic) throws IOException{
		return BaiduBaikeSearcher.isInBaiduBaike(topic) || HudongBaikeSearcher.isInHudongBaike(topic) || WikiBaikeSearcher.isInWiki(topic);
	}
}
