package com.ckcest.ebs.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.io.FileFormatException;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationIntegerReader;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationSequencesReader;

 
/**
 * @ClassName: JaHMMReadFileTest
 * @Description: 
 * @author GongJun
 * @date 2015年8月6日 下午6:36:27
 * @version V1.0  
 */

public class JaHMMReadFileTest {
	public static void main(String[] args){
		try {
			Reader reader = new FileReader("data\\hmm\\hmmData.txt");
			List<List<ObservationInteger>> data = ObservationSequencesReader.readSequences(new ObservationIntegerReader(), reader);
			
			for(int i = 0; i < data.size(); i ++){
				for(int j = 0; j < data.get(i).size(); j ++)
					System.out.print(data.get(i).get(j) + " ");
				
				System.out.println();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
