package com.HttpClient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class sendMessages {

	public static void main(String[] args) {
		String responsecontent = null;
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
				+"&appid=wx3b943850bc942084&secret=ac034520319c8270ea22ede720e788c4";
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response =null;
		HttpEntity entity =null;
		String access_token = null;
		int expires_in =0;
		try {
			//1.创建一个请求对象
			httpClient = HttpClients.createDefault();
			//2.GET请求
			HttpGet httpGet = new HttpGet(url);
			//3.执行请求并返回响应
			response = httpClient.execute(httpGet);
			//4.提取实体
			entity = response.getEntity();
			//5.把实体转换为字符串
			responsecontent = EntityUtils.toString(entity, "utf-8");
			//6.JSON解析返回的字符串
			JSONObject jo = new JSONObject(responsecontent);
			//7.获取JSON中的access_token和expires_in
			access_token = jo.getString("access_token");
			expires_in = jo.getInt("expires_in");
			//8.输出
			System.out.println("access_token:"+access_token);
			System.out.println("expires_in:"+expires_in);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				//9.关闭请求
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
			url="https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="+access_token;
		try{
			//处理请求的json数据
	        String jsonRequest ="{\"filter\":{\"is_to_all\":true,\"tag_id\":2},\"text\":{\"content\":\"牛皮\"},\"msgtype\":\"text\"}";
			//1.创建请求对象
	        httpClient = HttpClients.createDefault();
	        //2.Post请求
			HttpPost httpPost = new HttpPost(url);
			//3.设置参数
			StringEntity sentity = new StringEntity(jsonRequest,"UTF-8");
			sentity.setContentEncoding("UTF-8");
			sentity.setContentType("application/json");
			httpPost.setEntity(sentity);
			//4.执行请求并返回响应 5.获取实体
			response = httpClient.execute(httpPost);			
			entity = response.getEntity();
			//6.把实体转换为字符串
			responsecontent = EntityUtils.toString(entity, "utf-8");
			//7.输出
			System.out.println(responsecontent);
		}catch(Exception e){
			try {
				//8.关闭请求
				httpClient.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}

}
