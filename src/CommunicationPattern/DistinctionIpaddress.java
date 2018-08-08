package CommunicationPattern;

import java.util.ArrayList;

public class DistinctionIpaddress {
	//DistinctionIpaddress クラス ---------------
	//通信ログのDestination部分を持ってくる
	//IPアドレスごとにリストを作る
	//------------------------------------------
	//変数宣言
	ArrayList<String> ipAddress_list = new ArrayList<String>(); //Ipアドレスを格納するリスト
	//ログセット
	public void setLog(String log[][]){
		System.out.println("DistinctionIpaddress  "+log.length);
		separateIpaddress(log);
	}
	
	public void separateIpaddress(String log[][]){
		for(int i=0;i<log.length;i++){
			if(i>1){//最初の一行無視
				//ログのIPアドレスとリストに入っているIPアドレスが違えばIPアドレスをリストに入れる
				for(int j=0;j<ipAddress_list.size();j++){
					if(!log[i][3].equals(ipAddress_list.get(j))){
						ipAddress_list.add(log[i][3]);
						}else{}
					}
				
			}
			
		}
		
	}
}
