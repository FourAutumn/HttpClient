package com.HttpClient;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class testGetDemo2 {

	public static void main(String[] args) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response  = null;
		HttpEntity entity = null;
		String url = "http://www.huawei.com";
		String responsecontent = null;
		Header[] headers = null;
			try {
				//1.创建一个httpclient请求对象
				httpclient = HttpClients.createDefault();
				//2.get请求对象
				HttpGet httpget = new HttpGet(url);
				//3.execute 请求对象  4.response 返回对象
			    response =  httpclient.execute(httpget);
			    //5.提取对象返回的实体和响应头
			    entity = response.getEntity();
			    headers = response.getAllHeaders();
			    //把body转换成字符串
			    responsecontent =  EntityUtils.toString(entity,"utf-8");
			    //输出
			    System.out.println(httpget.getURI());
			    System.out.println(responsecontent);
			    System.out.println(headers);
				
			} catch (Exception e) {
				System.out.println("系统错误！");
			}finally{
				try {
					httpclient.close();
				} catch (IOException e) {
					System.out.println("系统错误：关闭请求！");
				}	
			}

	}

}
