package com.huasheng.sitelog;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * 配置类
 * @author Administrator
 *
 */
public class Config {
	
	static{	
		
		try {
			Properties p=new Properties();
			InputStream input=Config.class.getResourceAsStream("/sitelog.propertis");
			p.load(input);
			host=p.getProperty("host");
			port=Integer.parseInt( p.getProperty("port"));
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//加载		
	}
	
	private static String host;//主机地址
	private static int port;//端口
	
	public static String getHost() {
		return host;
	}
	public static int getPort() {
		return port;
	}
	
}
