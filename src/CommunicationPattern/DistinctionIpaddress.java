package CommunicationPattern;

import java.util.ArrayList;

public class DistinctionIpaddress {
	//DistinctionIpaddress クラス ---------------
	//通信ログのDestination部分を持ってくる
	//IPアドレスごとにリストを作る
	//------------------------------------------
	//変数宣言
	ArrayList<String> ipAddress_list = new ArrayList<String>();
	//ログセット
	public void setLog(String log[][]){
		System.out.println("DistinctionIpaddress  "+log.length);
	}
}
