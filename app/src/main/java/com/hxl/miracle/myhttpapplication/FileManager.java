package com.hxl.miracle.myhttpapplication;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManager {
	public static final String path = Environment.getExternalStorageDirectory().getPath() + "/okhttp/stat/";



	public static void writeFile(String fileContent) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date firstDate = new Date(System.currentTimeMillis());
		String fileName = formatter.format(firstDate);
		writeFile(fileContent,fileName);
	}
	
	/**
	 * 向文件添加内容
	 * @param fileContent 文件内容
	 * @param fileName    文件名称
	 */
	public static void writeFile(String fileContent,String fileName) {
		writeFile(fileContent,path,fileName);
	}
	
	/**
	 * 向文件添加内容
	 * @param fileContent 文件内容
	 * @param filePath    文件路径
	 * @param fileName    文件名称
	 */
	public static void writeFile(String fileContent,String filePath,String fileName) {
		//文件名称或路径为空时不写入
		if(TextUtils.isEmpty(filePath) || TextUtils.isEmpty(fileName)){
			return;
		}
		//内容为空时写入空串
		if(TextUtils.isEmpty(fileContent)){
			fileContent = "";
		}
		
		try {
			File fileDir = new File(filePath);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			File file = new File(fileDir, fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file,true);
			if(file.length() > 0){
				fileOutputStream.write("\r\n".getBytes());
			}
			fileOutputStream.write(fileContent.getBytes());
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
