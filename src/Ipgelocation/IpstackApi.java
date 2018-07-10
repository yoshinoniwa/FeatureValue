package Ipgelocation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;

public class IpstackApi {
	String accessKey = "be746d3505a55332ac424a03105ce3ce";
	String ipAddress = "17.253.91.201";
	String url = "http://api.ipstack.com/"+ipAddress+"?access_key="+accessKey;
	
	public void getApi(){
		System.out.println(jsonGetRequest(url));
	}
	
	private static String streamToString(InputStream inputStream) {
	    String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
	    return text;
	  }

	public static String jsonGetRequest(String urlQueryString){
		String json = null;
		try {
		      URL url = new URL(urlQueryString);
		      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		      connection.setDoOutput(true);
		      connection.setInstanceFollowRedirects(false);
		      connection.setRequestMethod("GET");
		      connection.setRequestProperty("Content-Type", "application/json");
		      connection.setRequestProperty("charset", "utf-8");
		      connection.connect();
		      InputStream inStream = connection.getInputStream();
		      json = streamToString(inStream); // input stream to string
		    } catch (IOException ex) {
		      ex.printStackTrace();
		    }
		return json;
	}
	
	public void getData(){
		
	}
	
}
