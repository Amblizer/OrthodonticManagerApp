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
     * ��ָ��URL����GET����������
     * 
     * @param url
     *            ���������URL
     * @param params
     *            ����������������Ӧ����name1=value1&name2=value2����ʽ��
     * @return URL������Զ����Դ����Ӧ
     */
    public static String sendGet(String url, String params) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = url + "?" + params;
            URL realUrl = new URL(urlName);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
 
            // ����ʵ�ʵ�����
            conn.connect();
            // ��ȡ������Ӧͷ�ֶ�
 
            Map<String, List<String>> map = conn.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
 
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
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
     * ��ָ��URL����POST����������
     * 
     * @param url
     *            ���������URL
     * @param json
     *            ����������������Ӧ����name1=value1&name2=value2����ʽ��
     * @return URL������Զ����Դ����Ӧ
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
