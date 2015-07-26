package com.ckcest.ebs.vici.base;

import java.util.ArrayList;
import java.util.List;

 
/**
 * @ClassName: CatalogNode
 * @Description: 
 * @author GongJun
 * @date 2015年7月25日 下午8:48:04
 * @version V1.0  
 */

public class CatalogNode {

	public String value;
	public String level;
	public List<CatalogNode> next;
	
	public CatalogNode(String value, String level){
		this.value = value;
		this.level = level;
		next = new ArrayList<CatalogNode>();
	}
}
