package com.ckcest.ebs.vici.hmm;

import java.util.List;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;

 
/**
 * @ClassName: ChapterEntry
 * @Description: 
 * @author GongJun
 * @date 2015年8月6日 下午9:47:51
 * @version V1.0  
 */

public class CatalogEntry {
	//目录内容
	private String catalog;
	//复旦nlp分词结果，第一行是word，第二行是对应的词性标注
	private String[][] segArr;
	//词性对
	private PosPair[] posPairArr;
	//jahmm观察序列
	private List<ObservationInteger> observationSequence;
	//jahmm得到的隐藏状态序列
	private int[] stateSquence;
	//被[]包含的短语
	private List<String> res;
	//该目录是否有效
	private boolean valid;
	
	/**
	 * <p>Function: </p>
	 * <p>Description: </p>
	 * @param catalog
	 */
		
	public CatalogEntry(String catalog) {
		super();
		this.catalog = catalog;
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
	 * @return posPairArr
	 */
	
	public PosPair[] getPosPairArr() {
		return posPairArr;
	}

	/**
	 * @param posPairArr 要设置的 posPairArr
	 */
	
	public void setPosPairArr(PosPair[] posPairArr) {
		this.posPairArr = posPairArr;
	}

	/**
	 * @return observationSequence
	 */
	
	public List<ObservationInteger> getObservationSequence() {
		return observationSequence;
	}

	/**
	 * @param observationSequence 要设置的 observationSequence
	 */
	
	public void setObservationSequence(List<ObservationInteger> observationSequence) {
		this.observationSequence = observationSequence;
	}

	/**
	 * @return stateSquence
	 */
	
	public int[] getStateSquence() {
		return stateSquence;
	}

	/**
	 * @param stateSquence 要设置的 stateSquence
	 */
	
	public void setStateSquence(int[] stateSquence) {
		this.stateSquence = stateSquence;
	}

	/**
	 * @return res
	 */
	
	public List<String> getRes() {
		return res;
	}

	/**
	 * @param res 要设置的 res
	 */
	
	public void setRes(List<String> res) {
		this.res = res;
	}

	/**
	 * @return valid
	 */
	
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param valid 要设置的 valid
	 */
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	
	
	
}
