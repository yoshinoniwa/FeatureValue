package Ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PingExec {
	String pingResult;
	String test;
	String aveTiem_string;
	double aveTime;
	double minTime;
	double maxTime;
	double devTime;
	
	//Ping　実行
	public void SystemCall(String ipAddress) throws IOException{
		ipAddress = "184.51.198.59";//IPアドレス
		Runtime runtime = Runtime.getRuntime();
		String[] command = { "ping","-c","10", ipAddress }; //指定したipアドレスをtracerouteかける
		Process process = null;
		ArrayList pingList = new ArrayList();
		String[] splitPing = null;
		//traceroute実行
		try {
            process = runtime.exec(command); 
        } catch (IOException e) {
            e.printStackTrace();
        }
		//プロセス正常終了まで待機させる
		try {
            process.waitFor(); // プロセスの正常終了まで待機させる
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		java.io.InputStream is = process.getInputStream(); // プロセスの結果を変数に格納する
        BufferedReader br = new BufferedReader(new InputStreamReader(is)); // テキスト読み込みを行えるようにする
        
        while (true) {
            pingResult = br.readLine(); //tracerouteResultに実行結果を入れる  
            if (pingResult == null) {
            	getTime(splitPing[3]);
//            	show();
                break; // 全ての行を読み切ったら抜ける
            } else {
//                System.out.println("line : " + pingResult); // 実行結果を表示
                splitPing = splitLine(pingResult);
                pingList.add(splitPing.clone());//tracerouteの実行結果の1行を要素ごとに分割
            
            }
        }
	}
	
	//分割処理
	public static String[] splitLine(String line){
		String[] splitPing = line.split(" ");//空白で分割する
//		System.out.println(splitPing.length);	
		return splitPing;
		}
	//
	public void getTime(String time){
		String[] splitTime = time.split("/");
		minTime = Double.parseDouble(splitTime[0]);
		aveTime = Double.parseDouble(splitTime[1]);
		maxTime = Double.parseDouble(splitTime[2]);
		devTime = Double.parseDouble(splitTime[3]);
		
	}
	public void show(){
		System.out.println("最小/平均/最大/標準偏差　" + minTime+"/" +aveTime+"/" + maxTime+"/" + devTime);
	}
	
	public double getAveTime(){
		return aveTime;
	}
		
}

