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
	private String catalog;
	private String[][] segArr;
	private PosPair[] posPairArr;
	
	private List<ObservationInteger> observationSequence;
	
	private int[] stateSquence;
	
	private List<String> res;


	
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
	
	
}
