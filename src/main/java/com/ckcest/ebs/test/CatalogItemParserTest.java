/*
package com.ckcest.ebs.test;

import java.util.ArrayList;



public class CatalogItemParserTest {
	public static void main(String[] args){
		CatalogItemParser parser = new CatalogItemParser();
		System.out.println(parser.trim("第1章 抗感染类药物"));
		System.out.println(parser.trim("第一节 抗生素类药物"));
		System.out.println(parser.trim("一、青霉素类"));
		System.out.println(parser.trim("一次肺小叶"));
		System.out.println(parser.trim("十四烷基硫酸钠"));
		System.out.println(parser.trim("1.1 一次肺小叶"));
		System.out.println(parser.trim("1.1 一次肺小叶、麻黄素"));
		String[] testcase = {"3 Achondrogenesis(软骨成长不全)","（一）渗透压","第3节降低温度对组织的影响","1．1孕妇用药对胎儿的影响", "1．儿童生理特点与用药关系","五、内服煎膏剂(膏滋)","规范处方行为，力求临床用药有效、安全、经济、方便","延缓衰老及保健药物","阿莫西林／克拉维酸","◎枣树坐果率低的原因及解决办法","山地栽桑与溪滩栽桑","山地栽桑和溪滩栽桑","消毒液如何消毒蚕、蚕座？","消毒液消毒蚕、蚕座？","三十七、暗绿绣眼鸟","工业性外伤的种类","自动控制新技术的采用","辊锻的分类","第158節電磁式測量儀表"};
		for(String test:testcase){
			test = parser.complex2simple(test);
			String pStr = parser.trim(test);
			System.out.println("trim result: " + pStr);
			ArrayList splitResult = parser.split(pStr);
			System.out.println("split result: " + splitResult.toString() + " var of term length: " + parser.varOfTermLength(splitResult));
			System.out.println("is Question: " + parser.isQuestion(test));
		}		
	}
}
*/