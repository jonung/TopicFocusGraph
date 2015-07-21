package com.ckcest.ebs.vici.nlp;

import com.sun.jna.Library;
import com.sun.jna.Native;

 
/**
 * @ClassName: CLibrary
 * @Description: 
 * @author GongJun
 * @date 2015-5-21 下午9:08:38
 * @version V1.0  
 */

public interface CLibrary extends Library  {
	// 定义并初始化接口的静态变量
	public CLibrary Instance = (CLibrary) Native.loadLibrary(
			"D:\\ICTCLAS2015\\lib\\win64\\NLPIR", CLibrary.class);
	
	public int NLPIR_Init(String sDataPath, int encoding,
			String sLicenceCode);
			
	public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);

	public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit,
			boolean bWeightOut);
	public String NLPIR_GetFileKeyWords(String sLine, int nMaxKeyLimit,
			boolean bWeightOut);
	public int NLPIR_AddUserWord(String sWord);//add by qp 2008.11.10
	public int NLPIR_DelUsrWord(String sWord);//add by qp 2008.11.10
	public String NLPIR_GetLastErrorMsg();
	public void NLPIR_Exit();
}
