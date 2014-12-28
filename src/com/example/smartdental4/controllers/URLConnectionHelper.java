package com.example.smartdental4.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
 
public class URLConnectionHelper {
 
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param params
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendGet(String url, String params) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = url + "?" + params;
            URL realUrl = new URL(urlName);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
 
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
 
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
 
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
 
    /**
     * 向指定URL发送POST方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param json
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, String json) {
    	HttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost("http://59.66.137.62:8000/mergeJson");
        StringEntity input;
        String output;
        String responseStr = "";
		try {
//			input = new StringEntity(json);
			input = new StringEntity(json, "UTF-8");
			
			input.setContentType("application/json;charset=UTF-8");
			postRequest.setEntity(input);
	        input.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
	        postRequest.setHeader("Accept", "application/json");
	        postRequest.setEntity(input);
	        
	        HttpResponse response = httpClient.execute(postRequest);

            BufferedReader br = new BufferedReader(
                            new InputStreamReader((response.getEntity().getContent())));
            while ((output = br.readLine()) != null) {
            	responseStr += output;
            }

            httpClient.getConnectionManager().shutdown();
            
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return responseStr;
    }
}
