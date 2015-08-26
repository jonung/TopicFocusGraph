package com.ckcest.ebs.vici.classification;

import java.util.List;

 
/**
 * @ClassName: BookDocument
 * @Description: 
 * @author GongJun
 * @date 2015年8月26日 下午8:18:40
 * @version V1.0  
 */

public class BookDocument {
	private String bookNo;
	private List<SampleEntry> entryList;
	
	/**
	 * <p>Function: </p>
	 * <p>Description: </p>
	 * @param bookNo
	 */
		
	public BookDocument(String bookNo) {
		super();
		this.bookNo = bookNo;
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
	 * @return entryList
	 */
	
	public List<SampleEntry> getEntryList() {
		return entryList;
	}

	/**
	 * @param entryList 要设置的 entryList
	 */
	
	public void setEntryList(List<SampleEntry> entryList) {
		this.entryList = entryList;
	}

	
	/* (非 Javadoc)
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */
		
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BookNo: " + bookNo + "\n");
		for(int i = 0; i < entryList.size() && i < 5; i ++){
			sb.append(entryList.get(i).toString());
		}
		
		return sb.toString();
	}
	
	
}
