package com.ckcest.ebs.vici.base;

import java.util.ArrayList;
import java.util.List;

 
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
	
	public Book(String bookNo , String clc){
		this.bookNo = bookNo;
		this.clc = clc;
		catalogs = new ArrayList<String>();
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
	
	
}
