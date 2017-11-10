package com.HttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class baiduHeader {

	public static void main(String[] args) throws Exception{
		//设置代理
		HttpHost proxy = new HttpHost("127.0.0.1",8888,"Http");
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		
		String url = "https://www.baidu.com/s";
		//带参数的get请求
		List<NameValuePair> NameValuePairs = new ArrayList<NameValuePair>();
		NameValuePairs.add(new BasicNameValuePair("wd","kobe"));
		String uri = EntityUtils.toString(new UrlEncodedFormEntity(NameValuePairs),"UTF-8");
		
		
		CloseableHttpClient httpclient = SSL.createSSLClientDefault();
		HttpGet httpget = new HttpGet(url+"?"+uri);
		//启用代理
		httpget.setConfig(config);
		
		//处理请求头
		int i=0;
		HashMap<String,String> headers = new HashMap<String,String>();
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
		Header[] allheaders = new BasicHeader[headers.size()];
		for(String str:headers.keySet()){
			allheaders[i] = new BasicHeader(str, headers.get(str));
			i++;
		}
		httpget.setHeaders(allheaders);
		
		CloseableHttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		//获取响应码
		int code = response.getStatusLine().getStatusCode();
		System.out.println("状态码："+code);
		//接受响应头
		Header responseheadrs[] = response.getAllHeaders();
		int j = 0;
		while(j<responseheadrs.length){
			System.out.println(responseheadrs[j].getName()+": "+responseheadrs[j].getValue());
			j++;
		}
		
		//String responsecontent = EntityUtils.toString(entity);
		//System.out.println(responsecontent);
		httpclient.close();

	}

}
