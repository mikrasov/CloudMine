package com.cloudmine.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class PRequest {
    
	private final String USER_AGENT = "Mozilla/5.0";
    
	public static void main(String[] args) throws Exception {
        
		PRequest http = new PRequest();
        
		System.out.println("Send Http POST request");
		http.sendPost();
        
	}
    private void sendPost() throws Exception {
        
		String url = "https://richcoin.cs.ucsb.edu:9443/store/";
        
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
        
		// add header
		post.setHeader("User-Agent", USER_AGENT);
        
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("username", "modqhx"));
		urlParameters.add(new BasicNameValuePair("password", ""));
		
        
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
        
		HttpResponse response = client.execute(post);
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " +
                           response.getStatusLine().getStatusCode());
        
		BufferedReader rd = new BufferedReader(
                                               new InputStreamReader(response.getEntity().getContent()));
        
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
        
		System.out.println(result.toString());
        
	}
    
}