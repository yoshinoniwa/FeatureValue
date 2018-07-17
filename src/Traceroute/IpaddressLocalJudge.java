package Traceroute;

import java.util.ArrayList;

public class IpaddressLocalJudge {
	//private IPアドレス

	ArrayList<String> private_ipaddress_list1 = new ArrayList<String>();
	ArrayList<String> private_ipaddress_list2 = new ArrayList<String>();
	ArrayList<String> private_ipaddress_list3 = new ArrayList<String>();
	
	public void setPrivateIpaddressList1(){
		//private_ipaddress_1 値代入
		for(int i=0; i<256; i++){
			for(int j=0; j<256; j++){
				for(int k=0; k<256; k++){
					private_ipaddress_list1.add("10."+Integer.toString(i)+"."+ Integer.toString(j)+"."+ Integer.toString(k));
				}
			}
		}
	}
	public ArrayList<String> getPrivateIpaddressList1(){
		return private_ipaddress_list1;
	}
	
	public void setPrivateIpaddressList2(){
		//private_ipaddress_2 値代入
		for(int i=16; i<32; i++){
			for(int j=0; j<256; j++){
				for(int k=0; k<256; k++){
					private_ipaddress_list2.add("172."+String.valueOf(i)+"."+ String.valueOf(j)+"."+ String.valueOf(k));
				}
			}
		}
	}
	public ArrayList<String> getPrivateIpaddressList2(){
		return private_ipaddress_list2;
	}
	
	public void setPrivateIpaddressList3(){
		//private_ipaddress_3 値代入
		for(int i=0; i<256; i++){
			for(int j=0; j<256; j++){
				private_ipaddress_list3.add("192.168."+String.valueOf(i)+"."+ String.valueOf(j));
			}
		}
	}
	public ArrayList<String> getPrivateIpaddressList3(){
		return private_ipaddress_list3;
	}
	
}
