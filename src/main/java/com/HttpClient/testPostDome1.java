package com.HttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class testPostDome1 {

	public static void main(String[] args) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String url = "http://localhost/phpwind/index.php?m=u&c=login";
		String responsecontent = null;
		try{
			//1.创建一个httpclient请求对象
			httpclient = HttpClients.createDefault();
			//2.Post请求对象
			HttpPost httppost = new HttpPost(url);
			//3。设置参数
			List<NameValuePair> NameValuePairs = new ArrayList<NameValuePair>();
			NameValuePairs.add(new BasicNameValuePair("username","admin"));
			NameValuePairs.add(new BasicNameValuePair("password","123456"));
			httppost.setEntity(new UrlEncodedFormEntity(NameValuePairs,"utf-8"));
			//4.execute 执行请求     5.response 返回对象
			response = httpclient.execute(httppost);
			//6.提取实体
			entity = response.getEntity();
			responsecontent = EntityUtils.toString(entity,"utf-8");
			//7.输出
			System.out.println(httppost.getURI());
			System.out.println(responsecontent);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("系统错误！");
		}finally{
			try {
			//8.关闭对象请求
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("系统错误：请求关闭！");
			}
		}

	}

}
