package Traceroute;

import java.util.ArrayList;
import java.util.TreeMap;

public class Traceroute{
	//値の設定
	private double hop_num;
	private String domain;
	private String ip;
	private double responsetime1,responsetime2,responsetime3;
	public TreeMap<Double, ArrayList> tracerouteMap = new TreeMap<Double, ArrayList>(); 
	
	//値のセット
	void setTracerouteValue(double num,String doo,String i,double res1,double res2, double res3){
		hop_num =num;
		domain = doo;
		ip = i;
		responsetime1 = res1;
		responsetime2 = res2;
		responsetime3 = res3;
		setTracerouteList();
		setTracerouteMap();
	}
	
	//リストの作成
	public ArrayList setTracerouteList(){
		ArrayList list = new ArrayList();
		list.add(domain);
		list.add(ip);
		list.add(responsetime1);
		list.add(responsetime2);
		list.add(responsetime3);

		return list;
	}
	
	//TreeMapに値の導入
	public TreeMap setTracerouteMap(){
		ArrayList tracerouteList = new ArrayList();
		tracerouteList = setTracerouteList();
		
		tracerouteMap.put(hop_num,tracerouteList);
//		System.out.println(hop_num + ", "+ domain + ", " + ip);
//		System.out.println("MAP : "  + tracerouteMap.get(hop_num));
		
		return tracerouteMap;
	}
	
}
