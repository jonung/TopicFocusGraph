package com.ckcest.ebs.vici.neo4j;


import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.ckcest.ebs.TopicFocusGraph.AppConfig;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @ClassName: Neo4jServerAccess
 * @Description: 
 * @author GongJun
 * @date 2015年7月27日 下午2:26:06
 * @version V1.0  
 */

public class Neo4jServerAccess {
	private static Logger log = Logger.getLogger(Neo4jServerAccess.class);
	
	//图数据库uri地址
	public final static String SERVER_ROOT_URI = AppConfig.getAppConfig().getProperty("Neo4j_Server_Topic_Focus_Root_Rri"); 
	
	
	
	/**
	 * @Function: getFocusByTopicName
	 * @Description: 给定topic，查询其相关foucs，focus按其次数降序排列
	 * @param @param topic
	 * @param @return    
	 * @return String    
	 * @date 2015年7月27日 下午4:04:40
	 * @throws
	 */
		
	public static String getFocusByTopicName(String topic){
		String res = "";
		final String txUri = SERVER_ROOT_URI + "transaction/commit";
		//String query = "match(topic:Topic{name:{name}}) -[t2f:TOPIC_TO_FOCUS]-> (focus:Focus)	retrun topic,focus \",\"parameters\":{\"name\":\"计算机\"}"; 
		
		//String payload = "{\"statements\" : [ {\"statement\" : \"" +query + "}]}";
		
		String payload = "{\"statements\":[{\"statement\":\"match(topic:Topic{name:{name}}) -[t2f:TOPIC_TO_FOCUS]-> (focus:Focus) return topic,focus order by focus.num desc\",\"parameters\":{\"name\":\""+ topic + "\"}}]}";
		//StringBuilder sb = new StringBuilder();
		//sb.append("\"")
		log.info(payload);
		WebResource resource = Client.create().resource(txUri);
		ClientResponse response = resource
				.accept( MediaType.APPLICATION_JSON )
		        .type( MediaType.APPLICATION_JSON )
		        .entity( payload )
		        .post( ClientResponse.class );
		
		res = response.getEntity(String.class);
		log.info( String.format(
		        "POST [%s] to [%s]\n status code [%d], returned data: "
		                + System.getProperty( "line.separator" ) + "%s",
		        payload, txUri, response.getStatus(),res ) );

		response.close();
		return res;
	}
	
	
	/**
	 * @Function: checkDatabaseIsRunning
	 * @Description: TODO
	 * @param     
	 * @return void    
	 * @date 2015年7月27日 下午4:04:34
	 * @throws
	 */
		
	private static void checkDatabaseIsRunning(){
        // START SNIPPET: checkServer
		log.info(SERVER_ROOT_URI);
        WebResource resource = Client.create()
                .resource( SERVER_ROOT_URI );
        ClientResponse response = resource.get( ClientResponse.class );

        log.info( String.format( "GET on [%s], status code [%d]",
                SERVER_ROOT_URI, response.getStatus() ) );
        
        response.close();
        // END SNIPPET: checkServer
    }
	
	
	public static void main(String[] args){
		checkDatabaseIsRunning();
		getFocusByTopicName("计算机网络");
	}
}
