package Ipgelocation;

public class JsonData {
	String country_name;
	String city;
	double latitude; //緯度
	double longitude; //経度
	
	//緯度
	public double getLatitude(){
		return latitude;
	}
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	//経度
	public double getLongitude(){
		return longitude;
	}
	public void setLongitude(double longitude){
		this.longitude = longitude;
	}
}
