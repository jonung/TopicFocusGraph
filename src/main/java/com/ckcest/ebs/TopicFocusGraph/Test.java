package com.ckcest.ebs.TopicFocusGraph;

import java.io.IOException;

import com.ckcest.ebs.vici.lucene.BaiduBaikeSearcher;
import com.ckcest.ebs.vici.lucene.EngineeringBookCatalogSearcher;
import com.ckcest.ebs.vici.lucene.HudongBaikeSearcher;
import com.ckcest.ebs.vici.lucene.WikiBaikeSearcher;

 
/**
 * @ClassName: Test
 * @Description: 
 * @author GongJun
 * @date 2015年7月23日 下午11:00:36
 * @version V1.0  
 */

public class Test {
	public static void main(String[] args) throws IOException{
		/*
		boolean flag = BaiduBaikeSearcher.isInBaiduBaike("月经稀发");
		HudongBaikeSearcher.isInHudongBaike("庞大苏本国际酒业有限公司");
		WikiBaikeSearcher.isInWiki("中国人民解放军驻澳门部队");
		*/
		EngineeringBookCatalogSearcher.getCatalogListByBookNo("06315758");
	}
	
}
