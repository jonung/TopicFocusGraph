package com.ckcest.ebs.TopicFocusGraph;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;

 
/**
 * @ClassName: AppConfig
 * @Description: 
 * @author GongJun
 * @date Jul 21, 2015 9:06:42 PM
 * @version V1.0  
 */

public class AppConfig {

	private static Logger log = Logger.getLogger(AppConfig.class);
	
	private static Properties props = null;
		
	
	/**
	 * @Function: getBookMinningProperties
	 * @Description: 获取关于本程序的配置文件
	 * @param @return
	 * @param @throws IOException    
	 * @return Properties    
	 * @date Jun 10, 2015 8:01:47 PM
	 * @throws
	 */
		
	public static Properties getAppConfig(){
		
		if(props == null){
			try {
				props = PropertiesLoaderUtils.loadAllProperties("AppConfig.properties");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.info("Fail to load AppConfig.propeties");
			}

		}
		
		return props;
	}
	
}
