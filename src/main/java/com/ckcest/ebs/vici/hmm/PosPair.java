package com.ckcest.ebs.vici.hmm;

 
/**
 * @ClassName: PosPair
 * @Description: 
 * @author GongJun
 * @date 2015年8月5日 下午10:43:12
 * @version V1.0  
 */

public class PosPair implements HmmObservation{
	private String prePos;
	private String bacPos;
	
	/**
	 * <p>Function: </p>
	 * <p>Description: </p>
	 * @param prePos
	 * @param bacPos
	 */
		
	public PosPair(String prePos, String bacPos) {
		super();
		this.prePos = prePos;
		this.bacPos = bacPos;
	}

	/**
	 * @return prePos
	 */
	
	public String getPrePos() {
		return prePos;
	}

	/**
	 * @param prePos 要设置的 prePos
	 */
	
	public void setPrePos(String prePos) {
		this.prePos = prePos;
	}

	/**
	 * @return bacPos
	 */
	
	public String getBacPos() {
		return bacPos;
	}

	/**
	 * @param bacPos 要设置的 bacPos
	 */
	
	public void setBacPos(String bacPos) {
		this.bacPos = bacPos;
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
		result = prime * result + ((bacPos == null) ? 0 : bacPos.hashCode());
		result = prime * result + ((prePos == null) ? 0 : prePos.hashCode());
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
		PosPair other = (PosPair) obj;
		if (bacPos == null) {
			if (other.bacPos != null)
				return false;
		} else if (!bacPos.equals(other.bacPos))
			return false;
		if (prePos == null) {
			if (other.prePos != null)
				return false;
		} else if (!prePos.equals(other.prePos))
			return false;
		return true;
	}

	
	/* (非 Javadoc)
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */
		
	@Override
	public String toString() {
		return "(" + prePos + "->" + bacPos + ")"; 
	}
	
	
}
