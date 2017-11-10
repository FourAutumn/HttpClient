package com.HttpClient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class testGetDemo1 {

	public static void main(String[] args) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String url = "http://www.qq.com";
		String responseContent = null;
		try {
		//1.做一个httpclient请求对象
		httpClient = HttpClients.createDefault();
		//2.get请求对象
		HttpGet httpGet = new HttpGet(url);
		//3.execute执行请求   4.response 返回对象
		response = httpClient.execute(httpGet);
		//5.提取对象返回的实体
		entity = response.getEntity();
		//把body转成字符串
		responseContent = EntityUtils.toString(entity, "utf-8");
		System.out.println(httpGet.getURI());
		System.out.println(responseContent);
		} catch (Exception e) {
		   System.out.println("系统错误！");
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				System.out.println("系统错误：关闭请求！");
			}
		}

	}

}
