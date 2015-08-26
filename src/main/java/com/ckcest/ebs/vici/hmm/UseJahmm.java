package com.ckcest.ebs.vici.hmm;

import java.util.List;

import org.apache.log4j.Logger;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfIntegerFactory;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchLearner;

 
/**
 * @ClassName: UseJahmm
 * @Description: 
 * @author GongJun
 * @date 2015年8月6日 下午7:27:35
 * @version V1.0  
 */

public class UseJahmm {
	private static Logger log = Logger.getLogger(UseJahmm.class);
	//hmm学习时的迭代次数	
	private final static int ITERATION_NUM = 50;
	
	
	/**
	 * @Function: getHmm
	 * @Description: 根据我们求得的hmm参数，得到Jahmm中hmm模型
	 * @param @param myHmmModle
	 * @param @return    
	 * @return Hmm<ObservationInteger>    
	 * @date 2015年8月6日 下午7:32:20
	 * @throws
	 */
		
	public static Hmm<ObservationInteger> getInitHmm(MyHmmModel myHmmModle){
		log.info("use the params provided by myHmmMode to init the HMM model ! ");
		
		Hmm<ObservationInteger> hmm = new Hmm<ObservationInteger>(myHmmModle.getStateNum(),
				new OpdfIntegerFactory(myHmmModle.getObservationNum()));
		//Pi
		for(int i = 0; i < myHmmModle.getPi().length; i ++){
			hmm.setPi(i, myHmmModle.getPi()[i]);
		}
		//Aij
		for(int i = 0; i < myHmmModle.getStateNum(); i ++){
			for(int j = 0; j < myHmmModle.getStateNum(); j ++)
				hmm.setAij(i, j, myHmmModle.getA()[i][j]);
		}
		
		//Bij
		for(int i = 0; i < myHmmModle.getStateNum(); i ++){
			hmm.setOpdf(i, new OpdfInteger(myHmmModle.getB()[i]));
		}
		
		return hmm;
	}
	
	
	/**
	 * @Function: learnHmm
	 * @Description: 给定观察序列，提高hmm
	 * @param @param initHmm
	 * @param @param sequences
	 * @param @return    
	 * @return Hmm<ObservationInteger>    
	 * @date 2015年8月6日 下午7:51:39
	 * @throws
	 */
		
	public static  Hmm<ObservationInteger> learnHmm( Hmm<ObservationInteger> initHmm,
			List<List<ObservationInteger>> sequences){
		
		log.info("Try to learn a better HMM!");
		
		Hmm<ObservationInteger> hmm = initHmm;
		
		/* Baum-Welch learning */	
		BaumWelchLearner bwl = new BaumWelchLearner();
		
		// Incrementally improve the solution
		for (int i = 0; i < ITERATION_NUM; i++) {
			
			hmm = bwl.iterate(hmm, sequences);
		}
		
		
		return hmm;
	}
	
	
	
	/**
	 * @Function: predictStateSequence
	 * @Description: 给定hmm模型和观察序列，输出隐藏状态序列
	 * @param @param hmm
	 * @param @param observationSequence
	 * @param @return    
	 * @return int[]    
	 * @date 2015年8月6日 下午7:58:31
	 * @throws
	 */
		
	public static int[] predictStateSequence(Hmm<ObservationInteger> hmm, List<ObservationInteger> observationSequence){
		int[] stateSequence = hmm.mostLikelyStateSequence(observationSequence);
		
		return stateSequence;
	}
}
