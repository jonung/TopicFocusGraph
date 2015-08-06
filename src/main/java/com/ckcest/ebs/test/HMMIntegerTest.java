package com.ckcest.ebs.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfIntegerFactory;
import be.ac.ulg.montefiore.run.jahmm.io.FileFormatException;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationIntegerReader;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationSequencesReader;

 
/**
 * @ClassName: HMMIntegerTest
 * @Description: 
 * @author GongJun
 * @date 2015年8月6日 下午6:52:45
 * @version V1.0  
 */

public class HMMIntegerTest {
	public static void main(String[] args){
		Hmm<ObservationInteger> hmm = buildInitHmm();
		System.out.println("Resulting HMM:\n" + hmm);
		
		Reader reader;
		try {
			reader = new FileReader("data\\hmm\\hmmData.txt");
			List<List<ObservationInteger>> data = ObservationSequencesReader.readSequences(new ObservationIntegerReader(), reader);
			
			int[] res = hmm.mostLikelyStateSequence(data.get(0));
			
			System.out.println("state sequentc: " );
			for(int i : res)
				System.out.println(res[i]);
			
			res = hmm.mostLikelyStateSequence(data.get(1));
			
			System.out.println("state sequentc: " );
			for(int i : res)
				System.out.println(res[i]);
			
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
	static Hmm<ObservationInteger> buildInitHmm()
	{	
		Hmm<ObservationInteger> hmm = 
			new Hmm<ObservationInteger>(2,
					new OpdfIntegerFactory(3));
		
		hmm.setPi(0, 0.50);
		hmm.setPi(1, 0.50);
		
		hmm.setOpdf(0, new OpdfInteger(new double[] { 0.8, 0.2 ,0.5}));
		hmm.setOpdf(1, new OpdfInteger(
				new double[] { 0.1, 0.9,0.5 }));
		//hmm.setOpdf(2, new OpdfInteger(new double[] { 0.8, 0.2 }));
		
		
		hmm.setAij(0, 1, 0.2);
		hmm.setAij(0, 0, 0.8);
		hmm.setAij(1, 0, 0.2);
		hmm.setAij(1, 1, 0.8);
		
		return hmm;
	}
}
