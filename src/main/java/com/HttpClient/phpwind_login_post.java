package com.HttpClient;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.Utilities;
import javax.swing.text.html.parser.Entity;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class phpwind_login_post {
	private static HttpContext localContext = new BasicHttpContext();
	private static HttpClientContext Context = HttpClientContext.adapt(localContext);

	public static void main(String[] args) throws Exception{
		//设置代理
		HttpHost proxy = new HttpHost("127.0.0.1",8888,"Http");
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();		
		
		String url = "http://localhost/phpwind/";
		CloseableHttpResponse response = null;
		CloseableHttpClient httpclient = null;
		String responseContent =null;
		HttpEntity entity = null;
		String csrf_token = null;
		String _statu = null;
		
        httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        //执行代理
        httpGet.setConfig(config);
        
        response = httpclient.execute(httpGet,Context);
        entity = response.getEntity();
        responseContent = EntityUtils.toString(entity,"UTF-8");
       // System.out.println(responseContent);
        BufferedReader in = new BufferedReader(new StringReader(responseContent));
        Pattern pattern = Pattern.compile("name=\"csrf_token\" value=\"(.+?)\"/>");
        String s;
        while((s=in.readLine())!=null){
        	Matcher matcher = pattern.matcher(s);
        	if(matcher.find()){
        		csrf_token = matcher.group(1);
        	}
        }
        in.close();
        
        System.out.println("csrf_token值："+csrf_token);
        
        try{
        	httpclient = HttpClients.createDefault();
        	//url后面的参数的处理：/phpwind/index.php?m=u&c=login&a=dorun
        	List<NameValuePair> NamevaluePair_1 = new ArrayList<NameValuePair>();
        	NamevaluePair_1.add(new BasicNameValuePair("m","u"));
        	NamevaluePair_1.add(new BasicNameValuePair("c","login"));
        	NamevaluePair_1.add(new BasicNameValuePair("a","dorun"));
        	String str = EntityUtils.toString(new UrlEncodedFormEntity(NamevaluePair_1, Consts.UTF_8));
        	HttpPost httppost = new HttpPost(url+"?"+str);
        	httppost.setConfig(config);
        	
        	int i=0;
        	HashMap<String,String> headers = new HashMap<String,String>();
			headers.put("Accept","application/json, text/javascript, */*; q=0.01");
			headers.put("Accept-Encoding","gzip, deflate, br");
			headers.put("Accept-Language","zh-CN,zh;q=0.8");
			headers.put("User-Agent","User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");				
			headers.put("X-Requested-With","XMLHttpRequest");
			BasicHeader[] allheader = new BasicHeader[headers.size()];
			for(String str1:headers.keySet()){
				allheader[i] = new BasicHeader(str1,headers.get(str1));
				i++;
			}
			httppost.setHeaders(allheader);
			
        	List<NameValuePair> NamevaluePair_2 = new ArrayList<NameValuePair>();
        	NamevaluePair_2.add(new BasicNameValuePair("username","admin"));
        	NamevaluePair_2.add(new BasicNameValuePair("password","123456"));
        	NamevaluePair_2.add(new BasicNameValuePair("backurl","http://localhost/phpwind/"));
        	NamevaluePair_2.add(new BasicNameValuePair("csrf_token",csrf_token));
        	NamevaluePair_2.add(new BasicNameValuePair("csrf_token",csrf_token));
        	httppost.setEntity(new UrlEncodedFormEntity(NamevaluePair_2, Consts.UTF_8));   
        	response = httpclient.execute(httppost,Context);
        	entity = response.getEntity();
        	responseContent = EntityUtils.toString(entity,"UTF-8");
        	BufferedReader br = new BufferedReader(new StringReader(responseContent));
        	Pattern pattern2 = Pattern.compile("a=welcome&_statu=(.+?)\"");
        	String s1;
        	while((s1=br.readLine())!=null){
        		Matcher matcher2= pattern2.matcher(s1);
        		if(matcher2.find()){
        			_statu = matcher2.group(1);
        		}
        	}
        	br.close();
        	System.out.println("_statu值："+_statu);
        	httpclient.close();
        	
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        
        try{
        	httpclient = HttpClients.createDefault();
        	List<NameValuePair> NameValuePair_3 = new ArrayList<NameValuePair>();
        	NameValuePair_3.add(new BasicNameValuePair("m","u"));
        	NameValuePair_3.add(new BasicNameValuePair("c","login"));
        	NameValuePair_3.add(new BasicNameValuePair("a","welcome"));
        	NameValuePair_3.add(new BasicNameValuePair("_statu",_statu));
        	String str2 = EntityUtils.toString(new UrlEncodedFormEntity(NameValuePair_3));
        	HttpGet httpget = new HttpGet(url+"?"+str2);
        	httpget.setConfig(config);
        	
        	int i=0;
        	HashMap<String,String> headers = new HashMap<String,String>();
        	headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			headers.put("Accept-Encoding","gzip, deflate, br");
			headers.put("Accept-Language","zh-CN,zh;q=0.8");
			headers.put("User-Agent","User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        	BasicHeader[] allheaders = new BasicHeader[headers.size()];
        	for(String str3:headers.keySet()){
        		allheaders[i] = new BasicHeader(str3,headers.get(str3));
        		i++;
        	}
        httpget.setHeaders(allheaders);
        
        response = httpclient.execute(httpget,Context);
        entity = response.getEntity();
        responseContent = EntityUtils.toString(entity,Consts.UTF_8);
        
        boolean flag = responseContent.contains("U_NAME : 'admin'");
        if(flag){
        	System.out.println("登录成功！");
        }else{
        	System.out.println("登录失败！");
        }
        httpclient.close();
        
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        try{
        	httpclient = HttpClients.createDefault();
        	List<NameValuePair> NameValuePair_send = new ArrayList<NameValuePair>();
			NameValuePair_send.add(new BasicNameValuePair("c","post"));
			NameValuePair_send.add(new BasicNameValuePair("a","doadd"));
			NameValuePair_send.add(new BasicNameValuePair("_json","1"));
			NameValuePair_send.add(new BasicNameValuePair("fid","2"));
			String str4 = EntityUtils.toString(new UrlEncodedFormEntity(NameValuePair_send));
			HttpPost httppost_send = new HttpPost(url+"?"+str4);
			httppost_send.setConfig(config);
			
			int i=0;
			HashMap<String,String> headers_send = new HashMap<String,String>();
			headers_send.put("Accept","application/json, text/javascript, */*; q=0.01");
			headers_send.put("Accept-Encoding","gzip, deflate, br");
			headers_send.put("Accept-Language","zh-CN,zh;q=0.8");
			headers_send.put("User-Agent","User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");				
			headers_send.put("X-Requested-With","XMLHttpRequest");
        	BasicHeader[] allheaders = new BasicHeader[headers_send.size()];
        	for(String str5:headers_send.keySet()){
        		allheaders[i] = new BasicHeader(str4,headers_send.get(str5));
        		i++;
        	}
        	httppost_send.setHeaders(allheaders);
        	//标题，随机字符串 10个字符
			String  title =getRandomString(10);
			//内容   随机字符串 30个字符
			String  atc_content =getRandomString(30);
			
			//post里面参数的处理,发帖内容发处理
			List<NameValuePair> nameValuePairs_sendmsg = new ArrayList<NameValuePair>();
			nameValuePairs_sendmsg.add(new BasicNameValuePair("atc_title",title+"新梦想T71"));
			nameValuePairs_sendmsg.add(new BasicNameValuePair("atc_content",atc_content));
			nameValuePairs_sendmsg.add(new BasicNameValuePair("pid",""));
			nameValuePairs_sendmsg.add(new BasicNameValuePair("tid",""));
			nameValuePairs_sendmsg.add(new BasicNameValuePair("special","default"));
			nameValuePairs_sendmsg.add(new BasicNameValuePair("reply_notice","1"));			
			nameValuePairs_sendmsg.add(new BasicNameValuePair("csrf_token",csrf_token));			
			httppost_send.setEntity(new UrlEncodedFormEntity(nameValuePairs_sendmsg, "UTF-8"));	
			//发送发帖请求
			response = httpclient.execute(httppost_send,Context);
			
			entity = response.getEntity();
			
			responseContent = EntityUtils.toString(entity, "utf-8");

  		   System.out.println(responseContent);
  		   
			  //断言，判定登录是否成功          responseContent  中是否包含     "state":"success"
			  boolean flag=responseContent.contains("\"state\":\"success\"");
			  //待补充，用户名需要参数化
			  if(flag)
				   System.out.println("断言：发帖成功！");
			  else
				  System.out.println("断言：发帖失败！");
		   
	
			httpclient.close();
 		   
        }catch(Exception e){
        	e.printStackTrace();
        }
        
	}
	//获取指定位数的随机字符串(包含小写字母、大写字母、数字,0<length)
	public static String getRandomString(int length) {
	    //随机字符串的随机字符库
	    String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    StringBuffer sb = new StringBuffer();
	    int len = KeyString.length();
	    for (int i = 0; i < length; i++) {
	       sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
	    }
	    return sb.toString();
	}

}
