package com.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class LoginSend {
	//执行上下文
	private static HttpContext localContext = new BasicHttpContext();
    private static HttpClientContext context = HttpClientContext.adapt(localContext);

	public static void main(String[] args) throws IOException {	  
		CloseableHttpClient httpClient =null;
		CloseableHttpResponse response =null;
		HttpEntity entity=null;
		String responseContent = null;
	    String url = "http://localhost/phpwind/index.php";
	   
	    
		//代理设置
		HttpHost proxy = new HttpHost("127.0.0.1",8888, "http");
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		
		//第二个关联，拿到第三个请求的参数_statu
	    String loginid_statu = null;
	    //第三个关联，获取当前的发帖的id
	    String tid=null;
	    
		/*************************
		 * 第一个请求，打开登录页面
		 * **************************/	
	
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(config);
   
	    //1. 创建HttpClient对象
	    httpClient=HttpClients.createDefault();
	    //2.get请求，创建HttpGet对象
//	    HttpGet httpGet=new HttpGet(url);
	    
	    //3.第一次发送请求 调用HttpClient对象的execute()发送请求  获取context
	    response=httpClient.execute(httpGet,context);
	    
	    //4.获取entity
	    entity = response.getEntity();
        //5. 将entity转化为String
	    responseContent=EntityUtils.toString(entity,"utf-8");
	 
	    //使用正则表达式，拿到csrf_token
	    String csrf_token = null;
	   
	    BufferedReader in;	    
        Pattern pattern = Pattern.compile("name=\"csrf_token\" value=\"(.+?)\"/>");       
        
        in = new BufferedReader(new StringReader(responseContent));
        String s;
        while ((s = in.readLine()) != null){
         Matcher matcher = pattern.matcher(s);
	         if (matcher.find())
	         {
	           csrf_token=matcher.group(1);
	         }
          }
         in.close();   
	     
         System.out.println("csrf_token值："+csrf_token);
//	     System.out.println(httpGet.getURI());		
//        System.out.println(responseContent);
          httpClient.close();
        
        System.out.println("log：登录第一请求完成");
		/*************************
		 * 第二个请求：带用户名和密码
		 * **************************/
       try {
			httpClient = HttpClients.createDefault();
									
			//url后面的参数的处理：/phpwind/index.php?m=u&c=login&a=dologin
			List<NameValuePair> nameValuePairs_1 = new ArrayList<NameValuePair>();
			nameValuePairs_1.add(new BasicNameValuePair("m","u"));
			nameValuePairs_1.add(new BasicNameValuePair("c","login"));
			nameValuePairs_1.add(new BasicNameValuePair("a","dorun"));
			String str = EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs_1, Consts.UTF_8));
			
			HttpPost httpPost = new HttpPost(url+"?"+str);		
			
			//设置代理，fiddle抓包
			httpPost.setConfig(config);
			
			//设置请求头
			int i=0;
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Accept","application/json, text/javascript, */*; q=0.01");
			headers.put("Accept-Encoding","gzip, deflate, br");
			headers.put("Accept-Language","zh-CN,zh;q=0.8");
			headers.put("User-Agent","User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");				
			headers.put("X-Requested-With","XMLHttpRequest");	
			BasicHeader[] allHeader = new BasicHeader[headers.size()];
			for (String str1 : headers.keySet()) {
				allHeader[i] = new BasicHeader(str1, headers.get(str1));
				i++;
			}
			httpPost.setHeaders(allHeader);
			
			
			//post里面参数的处理
			List<NameValuePair> nameValuePairs_2 = new ArrayList<NameValuePair>();
			nameValuePairs_2.add(new BasicNameValuePair("username","admin"));
			nameValuePairs_2.add(new BasicNameValuePair("password","123456"));
			nameValuePairs_2.add(new BasicNameValuePair("backurl","http://localhost/phpwind/"));
			nameValuePairs_2.add(new BasicNameValuePair("csrf_token",csrf_token));
			nameValuePairs_2.add(new BasicNameValuePair("csrf_token",csrf_token));
			
			
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs_2, "UTF-8"));	
			//第二次发送请求，加context
			response = httpClient.execute(httpPost,context);
			
			entity = response.getEntity();
			
			responseContent = EntityUtils.toString(entity, "utf-8");
			//备注：返回的是json数据，可以加读取json数据的实验
			
//			//第二个关联，拿到第三个请求的参数_statu
//		    String loginid_statu = null;
		    
	        Pattern pattern2 = Pattern.compile("a=welcome&_statu=(.+?)\"");       
	        in = new BufferedReader(new StringReader(responseContent));
	        String s1;
	        while ((s1 = in.readLine()) != null){
	         Matcher matcher = pattern2.matcher(s1);
		         if (matcher.find())
		         {
		        	 loginid_statu=matcher.group(1);
		         }
	          }
	         in.close(); 
	         
	         
	        System.out.println("_statu值："+loginid_statu);
//			System.out.println(responseContent);
			
			httpClient.close();
		               } catch (IOException e) {
				e.printStackTrace();
			}   
       
       System.out.println("log：登录第 二个请求完成");
       /*************************
        * 登录的第三个请求
        * **************************/
       try {
			httpClient = HttpClients.createDefault();
									
			//url后面的参数的处理
			List<NameValuePair> nameValuePairs_3 = new ArrayList<NameValuePair>();
			nameValuePairs_3.add(new BasicNameValuePair("m","u"));
			nameValuePairs_3.add(new BasicNameValuePair("c","login"));
			nameValuePairs_3.add(new BasicNameValuePair("a","welcome"));
			nameValuePairs_3.add(new BasicNameValuePair("_statu",loginid_statu));
			String str3 = EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs_3, Consts.UTF_8));
			
			HttpGet httpGet3 = new HttpGet(url+"?"+str3);
			
			//设置代理，fiddle抓包
			httpGet3.setConfig(config);
			
			
			//设置请求头
			int i=0;
			HashMap<String, String> headers3 = new HashMap<String, String>();
			headers3.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			headers3.put("Accept-Encoding","gzip, deflate, br");
			headers3.put("Accept-Language","zh-CN,zh;q=0.8");
			headers3.put("User-Agent","User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");				
		
			BasicHeader[] allHeader3 = new BasicHeader[headers3.size()];
			for (String str1 : headers3.keySet()) {
				allHeader3[i] = new BasicHeader(str1, headers3.get(str1));
				i++;
			}
			httpGet3.setHeaders(allHeader3);
			
			//发送请求
			 response=httpClient.execute(httpGet3,context);
			//获取entity
			  entity = response.getEntity();
		    //将entity转化为String
			  responseContent=EntityUtils.toString(entity,"utf-8");
			 //打印输出的结果
//			  System.out.println(responseContent);
			
			  
			  //断言，判定登录是否成功          responseContent  中是否包含     U_NAME : 'admin',
			  boolean flag=responseContent.contains("U_NAME : 'admin'");
			  //待补充，用户名需要参数化
			  if(flag)
				   System.out.println("断言：登录成功！");
			  else
				  System.out.println("断言：登录失败！");
			  		  
			  httpClient.close();	
			  System.out.println("log：登录第 三个请求完成、登录完成！");
       }catch(IOException e) {
			e.printStackTrace();
       }
       
       
       /*************************
        * 发帖的请求
        * **************************/
       try {
			httpClient = HttpClients.createDefault();
									
			//url后面的参数的处理：/phpwind/index.php?c=post&a=doadd&_json=1&fid=13
			List<NameValuePair> nameValuePairs_send= new ArrayList<NameValuePair>();
			nameValuePairs_send.add(new BasicNameValuePair("c","post"));
			nameValuePairs_send.add(new BasicNameValuePair("a","doadd"));
			nameValuePairs_send.add(new BasicNameValuePair("_json","1"));
			nameValuePairs_send.add(new BasicNameValuePair("fid","2"));
			String str = EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs_send, Consts.UTF_8));
			
			HttpPost httpPost_send = new HttpPost(url+"?"+str);		
			
			//设置代理，fiddle抓包
			httpPost_send.setConfig(config);
			
			//设置请求头
			int i=0;
			HashMap<String, String> headers_send = new HashMap<String, String>();
			headers_send.put("Accept","application/json, text/javascript, */*; q=0.01");
			headers_send.put("Accept-Encoding","gzip, deflate, br");
			headers_send.put("Accept-Language","zh-CN,zh;q=0.8");
			headers_send.put("User-Agent","User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");				
			headers_send.put("X-Requested-With","XMLHttpRequest");
		
			BasicHeader[] allHeader = new BasicHeader[headers_send.size()];
			for (String str1 : headers_send.keySet()) {
				allHeader[i] = new BasicHeader(str1, headers_send.get(str1));
				i++;
			}
			httpPost_send.setHeaders(allHeader);
					
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
			httpPost_send.setEntity(new UrlEncodedFormEntity(nameValuePairs_sendmsg, "UTF-8"));	
			//发送发帖请求
			response = httpClient.execute(httpPost_send,context);
			
			entity = response.getEntity();
			
			responseContent = EntityUtils.toString(entity, "utf-8");

  		   System.out.println(responseContent);
  		   
  		   //拿到发帖的tid,然后给回帖用
  		   
  	        Pattern pattern_tid = Pattern.compile("tid=(.*?)&fid");       
	        in = new BufferedReader(new StringReader(responseContent));
	        String s3;
	        while ((s3 = in.readLine()) != null){
	         Matcher matcher = pattern_tid.matcher(s3);
		         if (matcher.find())
		         {
		        	 tid=matcher.group(1);
		         }
	          }
	         in.close(); 
	         
  		   System.out.println("新贴ID:"+tid);
		   
			  //断言，判定登录是否成功          responseContent  中是否包含     "state":"success"
			  boolean flag=responseContent.contains("\"state\":\"success\"");
			  //待补充，用户名需要参数化
			  if(flag)
				   System.out.println("断言：发帖成功！");
			  else
				  System.out.println("断言：发帖失败！");
		   
	
			httpClient.close();
		               } catch (IOException e) {
				e.printStackTrace();
			}   
       /*************************
        * 回帖的请求
        * **************************/
       try {
			httpClient = HttpClients.createDefault();
									
			//url后面的参数的处理：http://localhost/phpwind/index.php?c=post&a=doreply&_json=1&fid=2
			List<NameValuePair> nameValuePairs_Replies= new ArrayList<NameValuePair>();
			nameValuePairs_Replies.add(new BasicNameValuePair("c","post"));
			nameValuePairs_Replies.add(new BasicNameValuePair("a","doreply"));
			nameValuePairs_Replies.add(new BasicNameValuePair("_json","1"));
			nameValuePairs_Replies.add(new BasicNameValuePair("fid","2"));
			String str = EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs_Replies, Consts.UTF_8));
			
			HttpPost httpPost_Replies = new HttpPost(url+"?"+str);		
			
			//设置代理，fiddle抓包
			httpPost_Replies.setConfig(config);
			
			//设置请求头
			int i=0;
			HashMap<String, String> headers_Replies = new HashMap<String, String>();
			headers_Replies.put("Accept","application/json, text/javascript, */*; q=0.01");
			headers_Replies.put("Accept-Encoding","gzip, deflate, br");
			headers_Replies.put("Accept-Language","zh-CN,zh;q=0.8");
			headers_Replies.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");				
			headers_Replies.put("X-Requested-With","XMLHttpRequest");
		
			BasicHeader[] allHeader = new BasicHeader[headers_Replies.size()];
			for (String str1 : headers_Replies.keySet()) {
				allHeader[i] = new BasicHeader(str1, headers_Replies.get(str1));
				i++;
			}
			httpPost_Replies.setHeaders(allHeader);
					
			//回复的标题，随机字符串 10个字符
			String  titleReplies =getRandomString(10);
			//回复的内容   随机字符串 30个字符
			String  atc_contentReplies =getRandomString(30);
			
			//post里面参数的处理,发帖内容发处理
			List<NameValuePair> nameValuePairs_Replies2 = new ArrayList<NameValuePair>();
			nameValuePairs_Replies2.add(new BasicNameValuePair("atc_content","回贴："+atc_contentReplies));			
			nameValuePairs_Replies2.add(new BasicNameValuePair("csrf_token",csrf_token));
			nameValuePairs_Replies2.add(new BasicNameValuePair("tid",tid));	
			httpPost_Replies.setEntity(new UrlEncodedFormEntity(nameValuePairs_Replies2, "UTF-8"));	
			//发送发帖请求
			response = httpClient.execute(httpPost_Replies,context);
			
			entity = response.getEntity();
			
			responseContent = EntityUtils.toString(entity, "utf-8");

//		   System.out.println(responseContent);
		   
			  //断言，判定登录是否成功          responseContent  中是否包含   参数化的回帖的内容，
			  boolean flag=responseContent.contains(atc_contentReplies);
			  //待补充，用户名需要参数化
			  if(flag)
				   System.out.println("断言：回帖成功！");
			  else
				  System.out.println("断言：回帖失败！");
		   
	
			httpClient.close();
		               } catch (IOException e) {
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
