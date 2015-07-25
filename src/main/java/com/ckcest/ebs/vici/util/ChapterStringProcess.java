package com.ckcest.ebs.vici.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

 
/**
 * @ClassName: ChapterStringProcess
 * @Description: 
 * @author GongJun
 * @date Jun 10, 2015 4:27:50 PM
 * @version V1.0  
 */ 
	 
public class ChapterStringProcess {
	
	public static int FIRST_LEVEL = 1;
	public static int SECOND_LEVEL = 1;
	
	public Map<String,Integer> getChapterLevel(List<String> catalogs){
		Map<String,Integer> res = new HashMap<String,Integer>();
		for(int i = 0; i < catalogs.size(); i ++){
			
		}
		return res;
	}
	
	/**
	 * @Function: preProcessChapter
	 * @Description: 对每个章节做字符串处理，包括去前缀，繁体转简体
	 * @param @param chapter
	 * @param @return    
	 * @return String    
	 * @date 2015-1-18 下午8:09:18
	 * @throws
	 */
		
	public static String preProcessChapter(String chapter) {
		String res = trimPrefix(chapter);
		res = HanZiConversion.convert2SimplifiedChinese(res);
		return res;
	}
	 /**
	  * abandon the prefix of chapter,like "第一章"
	  * @param str_in
	  * @return
	  */
	public static String trimPrefix(String str_in)
		{
			String str=str_in;
			char[] str_array=str.toCharArray();
			int i=0;
			int len1=(str_array.length/3*2)>7?(str_array.length/3*2):7;
			int len=len1<str_array.length-2?len1:str_array.length-2;
			for(;i<=len;)
			{
				if(str_array[i]=='0'||str_array[i]=='1'||str_array[i]=='2'||str_array[i]=='3'||str_array[i]=='4'
					||str_array[i]=='5'||str_array[i]=='6'||str_array[i]=='7'||str_array[i]=='8'||str_array[i]=='9'
						||str_array[i]=='.'||str_array[i]=='$'||str_array[i]=='#'||str_array[i]=='^'||str_array[i]=='？'
							||str_array[i]=='?'||str_array[i]=='['||str_array[i]==']'||str_array[i]=='{'||str_array[i]=='}'
								||str_array[i]=='*'||str_array[i]=='&'||str_array[i]=='~'||str_array[i]=='+'||str_array[i]=='-'
									||str_array[i]==' '||str_array[i]=='Ⅰ'||str_array[i]=='Ⅱ'	||str_array[i]=='Ⅲ'	||str_array[i]=='、'	  
										||str_array[i]=='．'||str_array[i]=='（'||str_array[i]=='）'||str_array[i]=='('||str_array[i]==')')
											
				{
					i=i+1;
				}
				else if(str_array[i]=='一'||str_array[i]=='二'||str_array[i]=='三'||str_array[i]=='四'
						||str_array[i]=='五'||str_array[i]=='六'||str_array[i]=='七'||str_array[i]=='八'
							||str_array[i]=='九'||str_array[i]=='十')
				{
					if(str_array[i+1]!=' '&&str_array[i+1]!='、'&&str_array[i+1]!='章'&&str_array[i+1]!='节'&&str_array[i+1]!='篇'
						&&str_array[i+1]!='部'&&str_array[i+1]!='层'&&str_array[i+1]!='级'&&str_array[i+1]!='一'&&str_array[i+1]!='二'
							&&str_array[i+1]!='三'&&	str_array[i+1]!='四'&&str_array[i+1]!='五'&&str_array[i+1]!='六'&&str_array[i+1]!='七'
								&&str_array[i+1]!='八'&&str_array[i+1]!='九'&&str_array[i+1]!='十')
						break;
					else
						i=i+1;
				}
				else if(str_array[i]=='章'||str_array[i]=='节'||str_array[i]=='篇'||str_array[i]=='层'||str_array[i]=='级')
				{
					i=i+1;
					break;
				}
				else if(str_array[i]=='部')
				{
					if(str_array[i+1]=='分')
					{
						i=i+2;
					}
					else 
						i=i+1;
					break;
				}
				else if(str_array[i]=='第')
				{
					if(str_array[i+1]=='一'||str_array[i+1]=='二'||str_array[i+1]=='三'||str_array[i+1]=='四'
						||str_array[i+1]=='五'||str_array[i+1]=='六'||str_array[i+1]=='七'||str_array[i+1]=='八'
							||str_array[i+1]=='九'||str_array[i+1]=='十'||str_array[i+1]==' '||str_array[i+1]=='0'
								||str_array[i+1]=='1'||str_array[i+1]=='2'||str_array[i+1]=='3'||str_array[i+1]=='4'
									||str_array[i+1]=='5'||str_array[i+1]=='6'||str_array[i+1]=='7'||str_array[i+1]=='8'
										||str_array[i+1]=='9'||str_array[i+1]=='Ⅰ'||str_array[i+1]=='Ⅱ'||str_array[i+1]=='Ⅲ')
						i=i+1;
					else 
						break;
				}
				else
					break;
			}
			
			char[] str_array_new=new char[str_in.length()-i];
			for(int k=i;k<str_in.length();k++)
				str_array_new[k-i]=str_array[k];
			str=String.valueOf(str_array_new).trim();
			str=str.replaceAll("　", "");//12288，即中文全角空格
			return str;
		}
}
