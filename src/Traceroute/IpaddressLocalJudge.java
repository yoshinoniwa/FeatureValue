package Traceroute;

import java.util.ArrayList;

public class IpaddressLocalJudge {
	//private IPアドレス

	ArrayList<String> private_ipaddress_list1 = new ArrayList<String>();  //10.0.0.0～10.255.255.255
	ArrayList<String> private_ipaddress_list2 = new ArrayList<String>();  //172.16.0.0～172.31.255.255
	ArrayList<String> private_ipaddress_list3 = new ArrayList<String>();  //192.168.0.0～192.168.255.255
	String ipaddress_src="";
	String ipaddress_dst="";
	boolean flag_ip=true;
	
	public void setPrivateIpaddressList1(){
		//private_ipaddress_1 値代入
		for(int i=0; i<256; i++){
			for(int j=0; j<256; j++){
				for(int k=0; k<256; k++){
					this.private_ipaddress_list1.add("10."+Integer.toString(i)+"."+ Integer.toString(j)+"."+ Integer.toString(k));
				}
			}
		}

	}
	public ArrayList<String> getPrivateIpaddressList1(){
//		System.out.println("aaaa " + private_ipaddress_list1.size());
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
	
	public void judeIpAddress(ArrayList<String> list1,ArrayList<String> list2,ArrayList<String> list3, ArrayList<String> ipaddress){
		for(int i=0; i<ipaddress.size(); i++){
			ipaddress_src = ipaddress.get(i);
//			System.out.println("IPアドレス:"+ipaddress.get(i)+",  リストサイズ:"+ipaddress.size());
			//10.0.0.0～10.255.255.255
			for(int j=0;j<list1.size();j++){
				String str1=list1.get(j);
				if(ipaddress_src.equals(str1)){
					ipaddress_dst = ipaddress.get(i+1);
					break;
				}
			}
			//172.16.0.0～172.31.255.255
			for(int k=0;k<list2.size();k++){
				String str2=list2.get(k);
				if(ipaddress_src.equals(str2)){
					ipaddress_dst = ipaddress.get(i+1);
					System.out.println("TRUE");
					break;
				}
			}
			//192.168.0.0～192.168.255.255
			for(int l=0; l<list3.size(); l++){
				String str3=list3.get(l);
				if(ipaddress_src.equals(str3)){
					ipaddress_dst = ipaddress.get(i+1);
					break;
				}
			}
		}
//		System.out.println("IP_DST : "+ ipaddress_dst);
//		return ipaddress_dst;
	}
	public String getGlobalIpaddress(){
		return ipaddress_dst;
	}
	
}
