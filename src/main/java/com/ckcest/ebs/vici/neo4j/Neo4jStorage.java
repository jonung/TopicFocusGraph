package com.ckcest.ebs.vici.neo4j;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.Schema;
import org.neo4j.io.fs.FileUtils;

import com.ckcest.ebs.TopicFocusGraph.AppConfig;
import com.ckcest.ebs.vici.base.CLCData;
import com.ckcest.ebs.vici.base.FocusData;
import com.ckcest.ebs.vici.base.TopicData;
import com.ckcest.ebs.vici.base.TopicFocusPairData;

 
/**
 * @ClassName: Neo4jStorage
 * @Description: 
 * @author GongJun
 * @date 2015年7月26日 下午10:09:36
 * @version V1.0  
 */

public class Neo4jStorage {
	private static Logger log = Logger.getLogger(Neo4jStorage.class);
	
	public final static String DB_PATH = AppConfig.getAppConfig().getProperty("Neo4j_Storage_Locatioin");
	private static GraphDatabaseService GRAPH_DB = null;
	
	private static String TOPIC_LABEL = "Topic";
	private static String FOCUS_LABEL = "Focus";
	private static String CLC_LABEL = "Clc";
	
	
	/**
	 * @Function: getGraphDB
	 * @Description: TODO
	 * @param @return    
	 * @return GraphDatabaseService    
	 * @date 2015年7月27日 下午4:02:15
	 * @throws
	 */
		
	public static GraphDatabaseService getGraphDB(){
		if(GRAPH_DB == null){
			GRAPH_DB = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		}
		
		return GRAPH_DB;
	}
	
	
	/**
	 * @Function: setup
	 * @Description: 启动neo4j图数据库
	 * @param @throws IOException    
	 * @return void    
	 * @date 2015年7月27日 下午4:02:20
	 * @throws
	 */
		
	public static void setup() throws IOException{
        FileUtils.deleteRecursively( new File( DB_PATH ) );
        
		GRAPH_DB = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		registerShutdownHook();
		
		createIndex();
		insertNode();
		insertRelationShip();
	}
	
	
	
	/**
	 * @Function: insertNode
	 * @Description: 增加Node
	 * @param     
	 * @return void    
	 * @date 2015年7月27日 下午4:03:02
	 * @throws
	 */
		
	public static void insertNode(){
		insertTopicNode();
		insertFocusNode();
		insertCLCNode();
		
	}
	
	
	/**
	 * @Function: insertRelationShip
	 * @Description: TODO
	 * @param     
	 * @return void    
	 * @date 2015年7月27日 下午4:03:25
	 * @throws
	 */
		
	public static void insertRelationShip(){
		
		insertTopic2FocusRelationShip();
		insertTopic2ClcRelationShip();
		insertFocus2ClcRelatioinShip();
	}
	
	
	/**
	 * @Function: insertTopicNode
	 * @Description: TODO
	 * @param     
	 * @return void    
	 * @date 2015年7月27日 下午4:03:29
	 * @throws
	 */
		
	public static void insertTopicNode(){
		Label topicLabel = DynamicLabel.label(TOPIC_LABEL);
		
		try( Transaction tx = GRAPH_DB.beginTx() ){
			
			for(String topic : TopicData.topicSet ){
				Node topicNode = GRAPH_DB.createNode(topicLabel);
				topicNode.setProperty("name", topic);
				topicNode.setProperty("num", TopicData.topic2Num.get(topic));
			}
			
			tx.success();
		}
	}
	
	
	/**
	 * @Function: insertFocusNode
	 * @Description: TODO
	 * @param     
	 * @return void    
	 * @date 2015年7月27日 下午4:03:34
	 * @throws
	 */
		
	public static void insertFocusNode(){
		Label focusLabel = DynamicLabel.label(FOCUS_LABEL);
		
		try( Transaction tx = GRAPH_DB.beginTx() ){
			
			for(String focus : FocusData.focusSet){
				Node focusNode = GRAPH_DB.createNode(focusLabel);
				focusNode.setProperty("name", focus);
				focusNode.setProperty("num", FocusData.focus2Num.get(focus));
				
				double hie = FocusData.focus2Hie.get(focus) / FocusData.focus2Num.get(focus);
				focusNode.setProperty("hie", hie);
				
			}
			
			tx.success();
		}
		
	}
	
	
	/**
	 * @Function: insertCLCNode
	 * @Description: TODO
	 * @param     
	 * @return void    
	 * @date 2015年7月27日 下午4:03:39
	 * @throws
	 */
		
	public static void insertCLCNode(){
		Label clcLabel = DynamicLabel.label(CLC_LABEL);
		try( Transaction tx = GRAPH_DB.beginTx() ){
			for(String clc : CLCData.clcSet){
				Node clcNode = GRAPH_DB.createNode(clcLabel);
				clcNode.setProperty("name", clc);
				
			}
			
			tx.success();
		}
	}
	
	
	/**
	 * @Function: createIndex
	 * @Description: 创建索引
	 * @param     
	 * @return void    
	 * @date 2015年7月27日 下午4:03:42
	 * @throws
	 */
		
	public static void createIndex(){
        try ( Transaction tx = GRAPH_DB.beginTx() ){	
        
            Schema schema = GRAPH_DB.schema();
            schema.indexFor( DynamicLabel.label( TOPIC_LABEL ) )
            .on( "name" )
            .create();
            
            tx.success();
        }
        
        try ( Transaction tx = GRAPH_DB.beginTx() ){	
        
            Schema schema = GRAPH_DB.schema();
            schema.indexFor( DynamicLabel.label( FOCUS_LABEL ) )
            .on( "name" )
            .create();
            
            tx.success();
        }
		
        try ( Transaction tx = GRAPH_DB.beginTx() ){	
        
            Schema schema = GRAPH_DB.schema();
           schema.indexFor( DynamicLabel.label( CLC_LABEL ) ) 
            .on( "name" )
            .create();
            
            tx.success();
        }
        
	}
	
	public static void insertTopic2FocusRelationShip(){
		Label topicLabel = DynamicLabel.label(TOPIC_LABEL);
		Label focusLabel = DynamicLabel.label(FOCUS_LABEL);
		
		try( Transaction tx = GRAPH_DB.beginTx() ){
			
			for(String topic : TopicData.topic2Focus.keySet() ){
				Node topicNode = GRAPH_DB.findNode(topicLabel, "name", topic);
				
				Set<String> focusSet = TopicData.topic2Focus.get(topic);
				for(String focus : focusSet ){
					Node focusNode = GRAPH_DB.findNode(focusLabel, "name", focus);
					String topic2focus_pair = topic + "---->" + focus;
					int pair_num = TopicFocusPairData.pairNum.get(topic2focus_pair);
					
					Relationship topic2focusRelatioinShip = topicNode.createRelationshipTo(focusNode, RelType.TOPIC_TO_FOCUS);
					topic2focusRelatioinShip.setProperty("num", pair_num);
					
				}
				
			}
			
			tx.success();
		}
	}
	
	public static void insertTopic2ClcRelationShip(){
		
		Label topicLabel = DynamicLabel.label(TOPIC_LABEL);
		Label clcLabel = DynamicLabel.label(CLC_LABEL);
		
		try( Transaction tx = GRAPH_DB.beginTx() ){
			
			for(String topic : TopicData.topic2Clc.keySet()) {
				
				Node topicNode = GRAPH_DB.findNode(topicLabel, "name", topic);
				
				Map<String,Integer> topic2clcMap = TopicData.topic2Clc.get(topic);
				for(String clc : topic2clcMap.keySet() ){
					Node clcNode = GRAPH_DB.findNode(clcLabel, "name", clc);
					int num = topic2clcMap.get(clc);
					
					Relationship topc2clcRelationShip = topicNode.createRelationshipTo(clcNode, RelType.TOPIC_TO_CLC);
					topc2clcRelationShip.setProperty("num", num);
				}
				
			}
			
			tx.success();
		}
	}
	
	private static void insertFocus2ClcRelatioinShip(){
		Label focusLabel = DynamicLabel.label(FOCUS_LABEL);
		Label clcLabel = DynamicLabel.label(CLC_LABEL);
		
		try( Transaction tx = GRAPH_DB.beginTx() ){
			
			for(String focus : FocusData.focus2Clc.keySet() ){
				Node focusNode = GRAPH_DB.findNode(focusLabel, "name", focus);
				
				Map<String,Integer> focus2clcMap = FocusData.focus2Clc.get(focus);
				for(String clc : focus2clcMap.keySet() ){
					Node clcNode = GRAPH_DB.findNode(clcLabel, "name", clc);
					int clcNum = focus2clcMap.get(clc);
					
					Relationship focus2clcRelationShip = focusNode.createRelationshipTo(clcNode, RelType.FOCUS_TO_CLC);
					focus2clcRelationShip.setProperty("num", clcNum);
					
				}
			}
			
			tx.success();
		}
	}
	
	private static void registerShutdownHook(){
        // Registers a shutdown hook for the Neo4j and index service instances
        // so that it shuts down nicely when the VM exits (even if you
        // "Ctrl-C" the running example before it's completed)
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                shutdown();
            }
        } );
    }
	
	private static void shutdown(){
		GRAPH_DB.shutdown();
    }
}
