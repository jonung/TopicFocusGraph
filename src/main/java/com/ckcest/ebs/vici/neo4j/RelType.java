package com.ckcest.ebs.vici.neo4j;


import org.neo4j.graphdb.RelationshipType;

 
/**
 * @ClassName: RelType
 * @Description: 
 * @author GongJun
 * @date 2015年7月26日 下午3:37:52
 * @version V1.0  
 */

public enum RelType implements RelationshipType{ 
	TOPIC_TO_FOCUS,
	TOPIC_TO_CLC,
	FOCUS_TO_CLC,
	TOPIC_HYPERNYM,
	FOCUS_HYPERNYM
}
