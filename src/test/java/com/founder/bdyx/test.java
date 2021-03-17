package com.founder.bdyx;

import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSON;
import com.founder.bdyx.model.queryPatDayCharge;

/**
* @Description 功能描述
* @author yang.xuefeng
* @version 创建时间：2020年1月3日 上午10:01:17
*/
public class test {


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 创建动态客户端
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://www.webxml.com.cn/webservices/qqOnlineWebService.asmx?wsdl");
		Object[] objects = new Object[0];
		try {
			objects = client.invoke("qqCheckOnline", "249742073");
			System.out.println("返回数据:" + objects[0]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
        
		// 
        // weather.url=http://wthrcdn.etouch.cn/weather_mini?city=北京
		// http://wthrcdn.etouch.cn/weather_mini?citykey=
        
//		String weather ="http://wthrcdn.etouch.cn/weather_mini?city=北京";
//        System.out.println(getWeatherInfo(weather));
        
	}
	
	
	public static String getWeatherInfo(String url) {
		CloseableHttpClient client;
		client = HttpClients.createDefault();

		HttpGet get = new HttpGet(url);
		HttpResponse response;
		try {
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String str = convertStreamToString(instreams);
				get.abort();
				return str;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private static String convertStreamToString(InputStream is) {
		StringBuilder sb1 = new StringBuilder();
		byte[] bytes = new byte[4096];
		int size;

		try {
			while ((size = is.read(bytes)) > 0) {
				String str = new String(bytes, 0, size, "UTF-8");
				sb1.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb1.toString();
	}

}
