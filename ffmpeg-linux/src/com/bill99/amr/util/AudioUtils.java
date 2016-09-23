package com.bill99.amr.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AudioUtils {
	/**
	 * ffmpeg.exe文件所在的路径
	 */
	private final static String FFMPEG_PATH;
	static {
		FFMPEG_PATH = AudioUtils.class.getResource("ffmpeg").getFile();
	}
	/**
	 * 将一个amr文件转换成mp3文件
	 * @param amrFile 
	 * @param mp3File 
	 * @throws IOException 
	 */
	public static void amr2mp3(String amrFileName, String mp3FileName) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(FFMPEG_PATH + " -i "+amrFileName+" -ar 8000 -ac 1 -y -ab 12.4k " + mp3FileName);
		InputStream in = process.getErrorStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while((line = br.readLine())!=null) {
			System.out.println(line);
		}
		if(process.exitValue() != 0 ) {
			throw new RuntimeException("转换失败！");
		}
	}
}