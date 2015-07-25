package com.ckcest.ebs.vici.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 
/**
 * @ClassName: Book
 * @Description: 
 * @author GongJun
 * @date Jul 21, 2015 8:08:54 PM
 * @version V1.0  
 */

public class Book {
	//书号
	private String bookNo;
	//目录
	private List<String> catalogs;
	//书的分类
	private String clc;
	
	private List<String> firstLevelCatalogs;
	
	private Map<Integer,List<String>> secondLevelCatalogs;
	
	public Book(String bookNo , String clc){
		this.bookNo = bookNo;
		this.clc = clc;
		
		catalogs = new ArrayList<String>();
		firstLevelCatalogs = new ArrayList<String>();
		secondLevelCatalogs = new HashMap<Integer,List<String>>();
	}
	
	
	/**
	 * @Function: splitCatalogs
	 * @Description: 将目录分成两个层次
	 * @param     
	 * @return void    
	 * @date 2015年7月25日 下午7:49:11
	 * @throws
	 */
		
	public void splitCatalogs(){
		for(int i = 0; i < catalogs.size(); i ++){
			String catalog = catalogs.get(i);
			if(catalog.startsWith("第") && catalog.contains("章")){
				firstLevelCatalogs.add(catalog);
				
			}
		}
	}
	
	/**
	 * @return bookNo
	 */
	
	public String getBookNo() {
		return bookNo;
	}

	/**
	 * @param bookNo 要设置的 bookNo
	 */
	
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

	/**
	 * @return catalogs
	 */
	
	public List<String> getCatalogs() {
		return catalogs;
	}

	/**
	 * @param catalogs 要设置的 catalogs
	 */
	
	public void setCatalogs(List<String> catalogs) {
		this.catalogs = catalogs;
	}

	/**
	 * @return clc
	 */
	
	public String getClc() {
		return clc;
	}

	/**
	 * @param clc 要设置的 clc
	 */
	
	public void setClc(String clc) {
		this.clc = clc;
	}

	/**
	 * @return firstLevelCatalogs
	 */
	
	public List<String> getFirstLevelCatalogs() {
		return firstLevelCatalogs;
	}

	/**
	 * @param firstLevelCatalogs 要设置的 firstLevelCatalogs
	 */
	
	public void setFirstLevelCatalogs(List<String> firstLevelCatalogs) {
		this.firstLevelCatalogs = firstLevelCatalogs;
	}


	/**
	 * @param secondLevelCatalogs 要设置的 secondLevelCatalogs
	 */
	
	public void setSecondLevelCatalogs(
			Map<Integer, List<String>> secondLevelCatalogs) {
		this.secondLevelCatalogs = secondLevelCatalogs;
	}

	
	
}
