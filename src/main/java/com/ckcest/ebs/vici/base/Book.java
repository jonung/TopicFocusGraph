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
	//章节已经经过去前缀，繁体转简体
	private Map<Integer,List<String>> sameHieCatalogs; 
	
	public Book(String bookNo , String clc){
		this.bookNo = bookNo;
		this.clc = clc;
		
		catalogs = new ArrayList<String>();
		sameHieCatalogs = new HashMap<Integer,List<String>>();
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
	 * @return sameHieCatalogs
	 */
	
	public Map<Integer, List<String>> getSameHieCatalogs() {
		return sameHieCatalogs;
	}



	/**
	 * @param sameHieCatalogs 要设置的 sameHieCatalogs
	 */
	
	public void setSameHieCatalogs(Map<Integer, List<String>> sameHieCatalogs) {
		this.sameHieCatalogs = sameHieCatalogs;
	}

	
	
}
