package tw.edu.ntu.csie.libsvm.service;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.ckcest.ebs.TopicFocusGraph.AppConfig;

 
/**
 * @ClassName: UseLibSVM
 * @Description: 
 * @author GongJun
 * @date 2015年8月12日 下午12:38:59
 * @version V1.0  
 */

public class UseLibSVM {
	private static Logger log = Logger.getLogger(UseLibSVM.class);
	private final static String SVM_BASE_PATH = AppConfig.getAppConfig().getProperty("SVM_DATA_PATH");
	
	public static void main(String[] args) throws IOException{
		
		/*
		String[] arg = { "trainfile\\train1.txt", // 存放SVM训练模型用的数据的路径
						 "trainfile\\model_r.txt" }; // 存放SVM通过训练数据训练出来的模型的路径

		String[] parg = { "trainfile\\train2.txt", // 这个是存放测试数据
				"trainfile\\model_r.txt", // 调用的是训练以后的模型
				"trainfile\\out_r.txt" }; // 生成的结果的文件的路径
		
		*/
		
		/*
		String[] arg = new String[2];
		arg[0] = SVM_BASE_PATH + "train.txt";
		arg[1] = SVM_BASE_PATH + "svm_model.txt";
		*/
		String[] parg = new String[3];
		parg[0] = SVM_BASE_PATH + "test.txt";
		parg[1] = SVM_BASE_PATH + "svm_model.txt";
		parg[2] = SVM_BASE_PATH + "out.txt";
		
		//System.out.println("........SVM运行开始..........");
		// 创建一个训练对象
		//svm_train t = new svm_train();
		// 创建一个预测或者分类的对象
		svm_predict p = new svm_predict();
		
		//t.main(arg); // 调用
		p.main(parg); // 调用
	
	}
	
	public static void predict(){
		String[] parg = new String[3];
		parg[0] = SVM_BASE_PATH + "test.txt";
		parg[1] = SVM_BASE_PATH + "svm_model.txt";
		parg[2] = SVM_BASE_PATH + "out.txt";
		svm_predict p = new svm_predict();

		try {
			p.main(parg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 调用

	}
}
