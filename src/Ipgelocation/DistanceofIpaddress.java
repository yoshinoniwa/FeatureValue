package Ipgelocation;

import java.util.ArrayList;

public class DistanceofIpaddress {

	 // 球面三角法により、大円距離(メートル)を求める
	  public static double distance(double base_latitude, double base_longitude, double post_latitude, double post_longitude) {

	    // 緯度経度をラジアンに変換
	    double rad_base_lat = Math.toRadians(base_latitude);
	    double rad_base_lng = Math.toRadians(base_longitude);
	    double rad_post_lat = Math.toRadians(post_latitude);
	    double rad_post_lng = Math.toRadians(post_longitude);

	    // 2点の中心角(ラジアン)を求める
	    double a =
	      Math.sin(rad_base_lat) * Math.sin(rad_post_lat) +
	      Math.cos(rad_base_lat) * Math.cos(rad_post_lat) *
	      Math.cos(rad_base_lng - rad_post_lng);
	    double rr = Math.acos(a);

	    // 地球赤道半径(メートル)
	    double earth_radius = 6378140;

	    // 2点間の距離(メートル)
	    double distance = earth_radius * rr;

	    return distance;
	  }
	
}
