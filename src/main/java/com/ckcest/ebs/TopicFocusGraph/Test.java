package com.ckcest.ebs.TopicFocusGraph;

import java.io.IOException;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ckcest.ebs.vici.hmm.MyHmmModel;
import com.ckcest.ebs.vici.hmm.MyHmmModelSupport;
import com.ckcest.ebs.vici.nlp.FNLP;

 
/**
 * @ClassName: Test
 * @Description: 
 * @author GongJun
 * @date 2015年7月23日 下午11:00:36
 * @version V1.0  
 */

public class Test {
	private static Logger log = Logger.getLogger(Test.class);
	
	public static void main(String[] args) throws IOException{
		/*
		boolean flag = BaiduBaikeSearcher.isInBaiduBaike("月经稀发");
		HudongBaikeSearcher.isInHudongBaike("庞大苏本国际酒业有限公司");
		WikiBaikeSearcher.isInWiki("中国人民解放军驻澳门部队");
		*/
		/*
		String c = "1鋁及其合金的性";
		c = HanZiConversion.convert2SimplifiedChinese(c);
		log.info(BookSupport.isShuziLevel(c));
		log.info(BookSupport.removePrefix(c, 3));
		*/
		/*
		EngineeringBookCatalogSearcher.getCatalogListByBookNo("06315770");
		log.info(BookSupport.isZhangLevel("第十【表情】二章 张"));
		log.info(BookSupport.isJieLevel("第三节 计算机"));
		log.info(BookSupport.isShuziLevel("1鋁及其合金的性"));
		String catalog = "第三节计算机的特点";		
		
		String res = BookSupport.removePrefix(catalog, 3); 
		log.info("res " +res);
		*/
		/*
		Book book = new Book("06315755","t");
		List<String> catalogs = EngineeringBookCatalogSearcher.getCatalogListByBookNo(book.getBookNo());
		log.info("list size: " + catalogs.size());
		book.setSameHieCatalogs( BookSupport.initSameHieChapterMap(catalogs) );
		
		Map<Integer, List<String>> res = BookSupport.initSameHieChapterMap(catalogs);
		
		int count = 0;
		for(int key : res.keySet()){
			log.info("***********************");
			
			List<String> temp = res.get(key);
			count += temp.size();
			for(int i = 0; i < temp.size(); i ++){
				log.info(temp.get(i));
			}
		}
		
		log.info("map size: " + count);
		
		String space_str = "附录Ⅱ鉛錫合金的真空脫鉛";
		space_str = space_str.substring(space_str.lastIndexOf(" ") + 1);
		log.debug(space_str + "--->");
		*/
		
//		List<String> focusList = FocusExtractionSupport.getFocusList("政策、标准、大纲和步骤");
//		for(int i = 0; i < focusList.size(); i ++)
//			log.info(focusList.get(i));
		
		/*
		Random rand = new Random();
		for(int i = 0 ; i < 30; i ++){
			log.info(rand.nextInt(1000));
		}
		*/
		
		/*
		MyHmmModelSupport m = new MyHmmModelSupport("D:\\gongjun\\workspace4\\TopicFocusGraph\\data\\hmm\\tagDataForHMM\\");
		m.init();
		
		MyHmmModel myhmm = m.getMyHmm();
		
		log.info(myhmm.toString());
		*/
		
		String[][] arr = FNLP.getInstance().tag("中国人的特点");
		for(int i = 0; i  < arr.length; i ++){
			for(int j = 0; j < arr[i].length; j ++){
				System.out.print(arr[i][j] + " ");
				if(arr[i][j].equals("结构助词"))
					System.out.println("mmm");
						
			}
			System.out.println();
		}
	}
	
	
}
