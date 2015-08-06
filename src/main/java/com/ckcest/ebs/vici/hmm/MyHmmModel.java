package com.ckcest.ebs.vici.hmm;

import java.util.Arrays;

 
/**
 * @ClassName: MyHmmModel
 * @Description: 
 * @author GongJun
 * @date 2015年8月6日 下午7:17:17
 * @version V1.0  
 */

public class MyHmmModel {
	//private int stateNum;
	//private int observationNum;
	
	private double[] pi;
	private double[][] a;
	private double[][] b;
	
	
	
	/**
	 * <p>Function: </p>
	 * <p>Description: </p>
	 * @param pi
	 * @param a
	 * @param b
	 */
		
	public MyHmmModel(double[] pi, double[][] a, double[][] b) {
		super();
		this.pi = pi;
		this.a = a;
		this.b = b;
	}

	/**
	 * @return stateNum
	 */
	
	public int getStateNum() {
		return pi.length;
	}

	

	/**
	 * @return observationNum
	 */
	
	public int getObservationNum() {
		return b[0].length;
	}


	/**
	 * @return pi
	 */
	
	public double[] getPi() {
		return pi;
	}

	/**
	 * @param pi 要设置的 pi
	 */
	
	public void setPi(double[] pi) {
		this.pi = pi;
	}

	/**
	 * @return a
	 */
	
	public double[][] getA() {
		return a;
	}

	/**
	 * @param a 要设置的 a
	 */
	
	public void setA(double[][] a) {
		this.a = a;
	}

	/**
	 * @return b
	 */
	
	public double[][] getB() {
		return b;
	}

	/**
	 * @param b 要设置的 b
	 */
	
	public void setB(double[][] b) {
		this.b = b;
	}

	
	/* (非 Javadoc)
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */
		
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("nState: " + pi.length + "\n");
		sb.append("nObservation: " + b[0].length);
		
		/*
		for(double d : pi)
			System.out.print(d + " ");
		System.out.println();
		System.out.println();
		for(int i = 0; i < a.length; i ++){
			for(int j = 0; j < a[0].length; j ++)
				System.out.printf("%5f  ",a[i][j]);
			System.out.println();
		}
		
		System.out.println();
		for(int i = 0; i < b.length; i ++){
			for(int j = 0; j < b[0].length; j ++)
				System.out.printf("%5f  ",b[i][j]);
			System.out.println();
		}
		System.out.println();
		*/
		return sb.toString();
	}
	
	
}
