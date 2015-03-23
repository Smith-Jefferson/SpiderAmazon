package com.test.spider.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtil {
	private static PrintWriter printOut;

	public static void toTxt(String str){
		File file =new File("/Users/CYF/desktop","test.html"); //输出文件邻路径
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 try {
			printOut = new PrintWriter(file);
			printOut.println(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
