package com.ckcest.ebs.vici.classification;

import java.util.Arrays;

 
/**
 * @ClassName: SampleEntry
 * @Description: 
 * @author GongJun
 * @date 2015年8月26日 下午8:19:34
 * @version V1.0  
 */

public class SampleEntry {
	//目录内容
	private String catalog;
	//复旦nlp分词结果，第一行是word，第二行是对应的词性标注
	private String[][] segArr;
	//短语
	private String[] phrase;
	//短语的标签（topic标为1，focus标为2）
	private int[] label;
	
	

	
	/**
	 * <p>Function: </p>
	 * <p>Description: </p>
	 */
		
	public SampleEntry() {
		super();
	}

	/**
	 * @return catalog
	 */
	
	public String getCatalog() {
		return catalog;
	}

	/**
	 * @param catalog 要设置的 catalog
	 */
	
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	/**
	 * @return segArr
	 */
	
	public String[][] getSegArr() {
		return segArr;
	}

	/**
	 * @param segArr 要设置的 segArr
	 */
	
	public void setSegArr(String[][] segArr) {
		this.segArr = segArr;
	}

	/**
	 * @return phrase
	 */
	
	public String[] getPhrase() {
		return phrase;
	}

	/**
	 * @param phrase 要设置的 phrase
	 */
	
	public void setPhrase(String[] phrase) {
		this.phrase = phrase;
	}

	/**
	 * @return label
	 */
	
	public int[] getLabel() {
		return label;
	}

	/**
	 * @param label 要设置的 label
	 */
	
	public void setLabel(int[] label) {
		this.label = label;
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
		sb.append("catalog: "+ catalog + "\n");
		
		sb.append("phase: ");
		for(int i = 0; i < phrase.length; i ++)
			sb.append(phrase[i] + " ");
		sb.append("\n");
		
		sb.append("label: ");
		for(int i = 0; i< label.length; i ++){
			sb.append(String.valueOf(label[i]) + " ");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	
	
}
