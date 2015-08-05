package com.ckcest.ebs.vici.nlp;

import org.apache.log4j.Logger;
import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.util.exception.LoadModelException;

 
/**
 * @ClassName: FNLP
 * @Description: 
 * @author GongJun
 * @date 2015年8月3日 下午4:28:54
 * @version V1.0  
 */

public class FNLP {
	private static Logger log = Logger.getLogger(FNLP.class);
	
	private static CNFactory fnlpFactory = null;
	
	public static CNFactory getInstance(){
		if(fnlpFactory == null){
			try {
				fnlpFactory = CNFactory.getInstance("FNLP_models");
			} catch (LoadModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.info("FNLP 初始化失败！");
			}
		}
		return fnlpFactory;
	}
			
	
}
