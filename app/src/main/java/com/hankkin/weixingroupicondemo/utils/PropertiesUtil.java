package com.hankkin.weixingroupicondemo.utils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import android.content.Context;

public class PropertiesUtil {

	/**
	 * 指定property文件 我使用request.getRealPath("/")获取的绝对路径,这里就直接写出
	 */
	private static final String PROPERTY_FILE = "c://nine_rect.properties";

	/**
	 * 根据Key 读取Value
	 * @param key
	 * @return
	 */
	public static String readData(Context mContext, String key, int resId) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(mContext.getResources().openRawResource(resId));
			props.load(in);
			in.close();
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 修改或添加键值对 如果key存在，修改 反之，添加
	 * @param key
	 * @param value
	 */
	public static void writeData(String key, String value) {
		Properties prop = new Properties();
		try {
			File file = new File(PROPERTY_FILE);
			if (!file.exists())
				file.createNewFile();
			InputStream fis = new FileInputStream(file);
			prop.load(fis);
			fis.close();// 一定要在修改值之前关闭fis
			OutputStream fos = new FileOutputStream(PROPERTY_FILE);
			prop.setProperty(key, value);
			prop.store(fos, "Update '" + key + "' value");
			fos.close();
		} catch (IOException e) {
			System.err.println("Visit " + PROPERTY_FILE + " for updating "
					+ value + " value error");
		}
	}
}
// 注意：

// 1可以读写property文件，而且可修改已经存在的键值对。

// 2应该特别注意IOl流关闭的时间和顺序，否则读写不成功，尤其在改写数据的时候。
