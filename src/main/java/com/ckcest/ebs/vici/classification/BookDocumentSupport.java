package com.ckcest.ebs.vici.classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ckcest.ebs.vici.nlp.FNLP;

 
/**
 * @ClassName: BookDocumentSupport
 * @Description: 
 * @author GongJun
 * @date 2015年8月26日 下午8:36:49
 * @version V1.0  
 */

public class BookDocumentSupport {
	private static Logger log = Logger.getLogger(BookDocument.class);
	
	
	/**
	 * @Function: createBookDocument
	 * @Description: 读入一个文件，得到一个BookDocument对象
	 * @param @param filePath
	 * @param @return    
	 * @return BookDocument    
	 * @date 2015年8月26日 下午9:17:37
	 * @throws
	 */
		
	public static BookDocument createBookDocument(String filePath){
		log.debug("Read file: " + filePath);
		File file = new File(filePath);
		
		String bookNo = file.getName().substring(0, file.getName().indexOf('.'));
		log.info("load book : " + bookNo);
		BookDocument bookDocument = new BookDocument(bookNo);
		List<SampleEntry> entryList = new ArrayList<SampleEntry>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			List<String> fiveLine = new ArrayList<String>();
			
			String line = "";
			int lineCount = 5;
			while((line = br.readLine()) != null){
				fiveLine.add(line);
				for(int i = 0; i < lineCount - 1; i ++){
					line = br.readLine();
					fiveLine.add(line);
				}
				
				SampleEntry se = createSampleEntry(fiveLine);
				entryList.add(se);
				
				fiveLine.clear();
			}
			
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bookDocument.setEntryList(entryList);
		
		return bookDocument;
	}
	
	
	/**
	 * @Function: createSampleEntry
	 * @Description: 创建一个SampleEntry对象
	 * @param @param fiveLine
	 * @param @return    
	 * @return SampleEntry    
	 * @date 2015年8月26日 下午9:18:08
	 * @throws
	 */
		
	public static SampleEntry createSampleEntry(List<String> fiveLine){
		SampleEntry sampleEntry = new SampleEntry();
		String catalog = splitByLine(fiveLine.get(0));
		String[][] segArr = FNLP.getInstance().tag(catalog);
		
		String[] phrase = splitByLine(fiveLine.get(2)).split(" ");
		
		log.debug(splitByLine(fiveLine.get(3)));
		String[] tmp = splitByLine(fiveLine.get(3)).split("  ");
		
		int[] label = new int[tmp.length];
		for(int i = 0; i< tmp.length; i ++){
			//log.debug(tmp[i] + " ");
			try{
				label[i] = Integer.parseInt(tmp[i].charAt(1) + "");
			}catch( java.lang.NumberFormatException e){
				label[i] = 2;
			}
		}
		
		sampleEntry.setCatalog(catalog);
		sampleEntry.setSegArr(segArr);
		sampleEntry.setPhrase(phrase);
		sampleEntry.setLabel(label);
		return sampleEntry;
	}
	
	
	/**
	 * @Function: splitByLine
	 * @Description: 得到“FNLP seg:  |||河上/名词” 中 |||后面的部分
	 * @param @param line
	 * @param @return    
	 * @return String    
	 * @date 2015年8月26日 下午9:18:23
	 * @throws
	 */
		
	public static String splitByLine(String line){
		line = line.substring(line.indexOf("|||") + 3);
		return line;
	}
	
	public static void main(String[] args){
		BookDocument bookDocument = createBookDocument("D:\\gongjun\\workspace4\\TopicFocusGraph\\data\\classification\\corpus\\06320332_1.txt");
		
		System.out.println(bookDocument.getEntryList().size());
		for(int i = 0; i < bookDocument.getEntryList().size(); i ++)
			EntryFeatureExtraction.extractionSampleEntry(bookDocument.getEntryList(),i);
	}
}
