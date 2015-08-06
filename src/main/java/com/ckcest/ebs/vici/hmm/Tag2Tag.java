package com.ckcest.ebs.vici.hmm;

 
/**
 * @ClassName: Tag2Tag
 * @Description: 表示一个隐藏状态到另外一个隐藏状态
 * @author GongJun
 * @date 2015年8月5日 下午10:21:54
 * @version V1.0  
 */

public class Tag2Tag {
	private String preTag;
	private String bacTag;
	
	/**
	 * <p>Function: </p>
	 * <p>Description: </p>
	 * @param preTag
	 * @param bacTag
	 */
		
	public Tag2Tag(String preTag, String bacTag) {
		super();
		this.preTag = preTag;
		this.bacTag = bacTag;
	}

	/**
	 * @return preTag
	 */
	
	public String getPreTag() {
		return preTag;
	}

	/**
	 * @param preTag 要设置的 preTag
	 */
	
	public void setPreTag(String preTag) {
		this.preTag = preTag;
	}

	/**
	 * @return bacTag
	 */
	
	public String getBacTag() {
		return bacTag;
	}

	/**
	 * @param bacTag 要设置的 bacTag
	 */
	
	public void setBacTag(String bacTag) {
		this.bacTag = bacTag;
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
		result = prime * result + ((bacTag == null) ? 0 : bacTag.hashCode());
		result = prime * result + ((preTag == null) ? 0 : preTag.hashCode());
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
		Tag2Tag other = (Tag2Tag) obj;
		if (bacTag == null) {
			if (other.bacTag != null)
				return false;
		} else if (!bacTag.equals(other.bacTag))
			return false;
		if (preTag == null) {
			if (other.preTag != null)
				return false;
		} else if (!preTag.equals(other.preTag))
			return false;
		return true;
	}
	
	
}
