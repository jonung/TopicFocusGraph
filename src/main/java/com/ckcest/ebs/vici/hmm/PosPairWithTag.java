package com.ckcest.ebs.vici.hmm;

 
/**
 * @ClassName: PosPairWithTag
 * @Description: 词性对到隐藏状态
 * @author GongJun
 * @date 2015年8月5日 下午10:18:49
 * @version V1.0  
 */

public class PosPairWithTag implements ObservationOccurWithState{
	private PosPair posPair;
	private String tag;
	
	/**
	 * <p>Function: </p>
	 * <p>Description: </p>
	 * @param posPair
	 * @param tag
	 */
		
	public PosPairWithTag(PosPair posPair, String tag) {
		super();
		this.posPair = posPair;
		this.tag = tag;
	}

	/**
	 * @return posPair
	 */
	
	public PosPair getPosPair() {
		return posPair;
	}

	/**
	 * @param posPair 要设置的 posPair
	 */
	
	public void setPosPair(PosPair posPair) {
		this.posPair = posPair;
	}

	/**
	 * @return tag
	 */
	
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag 要设置的 tag
	 */
	
	public void setTag(String tag) {
		this.tag = tag;
	}

	
	/* (非 Javadoc)
	 * <p>Title: hashCode</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((posPair == null) ? 0 : posPair.hashCode());
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}

	
	/* (非 Javadoc)
	 * <p>Title: equals</p>
	 * <p>Description: </p>
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PosPairWithTag other = (PosPairWithTag) obj;
		if (posPair == null) {
			if (other.posPair != null)
				return false;
		} else if (!posPair.equals(other.posPair))
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}
	
	
}
