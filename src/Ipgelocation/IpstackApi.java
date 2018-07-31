package Ipgelocation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;

public class IpstackApi {
	String accessKey = "be746d3505a55332ac424a03105ce3ce";
//	String ipAddress = "17.253.91.201";
	String requestUrl;
	String[] data;
	String country_name,city;
	static double latitude;
	static double longitude;
	static ArrayList<Double> latitude_list = new ArrayList<Double>();
	static ArrayList<Double> longitude_list = new ArrayList<Double>();
	//jsonData 配列
	//[3] continent_name
	//[4] contry_code
	//[5] contry_name
	//[10] latitude 緯度
	//[11] longitude　経度
	
	
	public void getApi(String ipAddress){
		requestUrl = "http://api.ipstack.com/"+ipAddress+"?access_key="+accessKey;
		data = splitJsonData(jsonGetRequest(requestUrl));
//		for(int i=0;i<data.length;i++){
//			System.out.println(data[i]);
//		}
		
		JsonData jd = new JsonData();
		jd.setLatitude(latitude);
		jd.setLongitude(longitude);
		latitudeList(jd.getLatitude());
		longitudeList(jd.getLongitude());
//		System.out.println("IPStack Ipaddress"+ ipAddress);
//		System.out.println("緯度" + jd.getLatitude() + ",経度"+ jd.getLongitude()+"    "+latitude_list.size());
		
//		System.out.println(jsonGetRequest(url));
	}
	private static ArrayList<Double> latitudeList(double lat){
		latitude_list.add(lat);
		return latitude_list;
	}
	private static ArrayList<Double> longitudeList(double log){
		longitude_list.add(log);
		return longitude_list;
	}
	
	public double distance(){
		DistanceofIpaddress doi = new DistanceofIpaddress();
		double distance = doi.distance(latitude_list.get(0), longitude_list.get(0), latitude_list.get(1), longitude_list.get(1));
		System.out.println("距離 : "+distance+"m");
		return distance;
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
	
	public static String[] splitJsonData(String line){
		String[] jsonData= line.split(",");
		//緯度
		String lat=jsonData[10].replace("\"","");
		lat = lat.replace(":", "");
		lat = lat.replace("latitude","");
		
		//経度
		String longi = jsonData[11].replace("\"","");
		longi = longi.replaceAll(":", "");
		longi = longi.replaceAll("longitude", "");

		latitude = Double.parseDouble(lat);
		longitude = Double.parseDouble(longi);
		return jsonData;
	}
	
	
}
