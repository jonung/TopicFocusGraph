package com.ckcest.ebs.vici.classification;

 
/**
 * @ClassName: EntryFeature
 * @Description: 一条数据包括的东西，包括分类号以及特征
 * @author GongJun
 * @date Aug 27, 2015 8:59:00 AM
 * @version V1.0  
 */

public class EntryFeature {
	//分类号
	private int label;
	//特征
	//特征一，短语在一条目录中所处的位置（需要进行2进制表示）
	private int pos;
	
	//前面是否有“的”
	private boolean isPreDe; 
	//后面是否有“的”
	private boolean isBacDe;
	
	//前面是否有并列连词
	private boolean isPreConj;
	
	//后面是否有并列连词
	private boolean isBacConj;
	
	//上下午是否有相同的词
	private boolean isContextEmerge;

	
	/**
	 * <p>Function: </p>
	 * <p>Description: </p>
	 */
		
	public EntryFeature() {
		super();
		
		isPreDe = false;
		isBacDe = false;
		isPreConj = false;
		isBacConj = false;
		isContextEmerge = false;
		
	}


	/**
	 * @return label
	 */
	
	public int getLabel() {
		return label;
	}


	/**
	 * @param label 要设置的 label
	 */
	
	public void setLabel(int label) {
		this.label = label;
	}


	/**
	 * @return pos
	 */
	
	public int getPos() {
		return pos;
	}


	/**
	 * @param pos 要设置的 pos
	 */
	
	public void setPos(int pos) {
		this.pos = pos;
	}


	/**
	 * @return isPreDe
	 */
	
	public boolean isPreDe() {
		return isPreDe;
	}


	/**
	 * @param isPreDe 要设置的 isPreDe
	 */
	
	public void setPreDe(boolean isPreDe) {
		this.isPreDe = isPreDe;
	}


	/**
	 * @return isBacDe
	 */
	
	public boolean isBacDe() {
		return isBacDe;
	}


	/**
	 * @param isBacDe 要设置的 isBacDe
	 */
	
	public void setBacDe(boolean isBacDe) {
		this.isBacDe = isBacDe;
	}


	/**
	 * @return isPreConj
	 */
	
	public boolean isPreConj() {
		return isPreConj;
	}


	/**
	 * @param isPreConj 要设置的 isPreConj
	 */
	
	public void setPreConj(boolean isPreConj) {
		this.isPreConj = isPreConj;
	}


	/**
	 * @return isBacConj
	 */
	
	public boolean isBacConj() {
		return isBacConj;
	}


	/**
	 * @param isBacConj 要设置的 isBacConj
	 */
	
	public void setBacConj(boolean isBacConj) {
		this.isBacConj = isBacConj;
	}


	/**
	 * @return isContextEmerge
	 */
	
	public boolean isContextEmerge() {
		return isContextEmerge;
	}


	/**
	 * @param isContextEmerge 要设置的 isContextEmerge
	 */
	
	public void setContextEmerge(boolean isContextEmerge) {
		this.isContextEmerge = isContextEmerge;
	}
	
	
}
