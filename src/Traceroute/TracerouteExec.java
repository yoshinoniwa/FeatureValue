package Traceroute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//tracerouteを
public class TracerouteExec {
	String tracerouteResult;
	public ArrayList<String> tracerouteList = new ArrayList<String>();
	public String hop_num_string;
	public double hop_num;
	public String ipaddress;
	public String domain=null;
	public double responsetime1,responsetime2,responsetime3;
//	private int count = 0;
	public boolean hop;
	
	//呼び出し処理
	public void systemCall() throws IOException{
		String ipAddress="17.253.91.201";  //IPアドレスの指定
		Runtime runtime = Runtime.getRuntime();
		String[] Command = { "traceroute", "-I", ipAddress }; //指定したipアドレスをtracerouteかける
        Process process = null;
        Traceroute tr = new Traceroute();
        tr.setTracerouteMap();
        
        try {
            process = runtime.exec(Command); //traceroute実行
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            process.waitFor(); // プロセスの正常終了まで待機させる
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        java.io.InputStream is = process.getInputStream(); // プロセスの結果を変数に格納する
        BufferedReader br = new BufferedReader(new InputStreamReader(is)); // テキスト読み込みを行えるようにする
        
        while (true) {
            tracerouteResult = br.readLine(); //tracerouteResultに実行結果を入れる  
//            count++;
            if (tracerouteResult == null) {
                break; // 全ての行を読み切ったら抜ける
            } else {
//                System.out.println("line : " + tracerouteResult); // 実行結果を表示
                splitLine(tracerouteResult);//tracerouteの実行結果の1行を要素ごとに分割
                setTracerouteValue();
            }
            
        	}
	}
	
	//分割処理
	//一行に出力されるtracerouteの結果をそれぞれ要素ごとに分割する
	public void splitLine(String line){
		String[] splitTraceroute =  line.split("  "); //空白で分割する
//		System.out.print("出力結果 yyyyy");
//		for(int i=0;i<splitTraceroute.length;i++){
//			System.out.print(" "+splitTraceroute[i]+",");
//		}
//		System.out.println("");
		
		separateDomainIp(splitTraceroute[1]); //ドメインとIPアドレスを分ける
		changeTracerouteType(splitTraceroute); //型変更変更
		System.out.println("");	
	}

	//分割したデータを型変換するメソッド
	//ホップ数, レスポンス値
	public void changeTracerouteType(String[] traceroute){
		//ホップ数
		hop_num_string = traceroute[0];
		//ホップ数が含まれているかどうか
		if(!hop_num_string.isEmpty()){
			//ホップ数があれば空白を決してダブル型に
			hop_num = Double.parseDouble(hop_num_string.trim());
			hop=true;
		}else{
			//ホップ数がなければ前のホップ数にプラス0.1
			hop_num += 0.1;
			hop=false;
		}
		System.out.println("ホップ数 : "+hop_num);
		
		//レスポンス値

		responsetime3 = getResponsetime(traceroute.length,4,traceroute);
		responsetime2 = getResponsetime(traceroute.length,3,traceroute);
		responsetime1 = getResponsetime(traceroute.length,2,traceroute);

		System.out.println("レスポンス値1:"+responsetime1+", レスポンス値2:" + responsetime2+", レスポンス値3:" + responsetime3);
	}
	
	//ドメインとIPアドレスを分ける
	public void separateDomainIp(String domainAndIp){
		//IPアドレス抜き出し
		//括弧内取得
		String kakko =  "\\((.+?)\\)";
		Pattern p_ip = Pattern.compile(kakko);
		Matcher m_ip = p_ip.matcher(domainAndIp);
		List<String> list = new ArrayList<String>();
		while (m_ip.find()) {
			list.add(m_ip.group(1));
		}
		for (String str : list) {
			ipaddress = str;
        }
		//ドメイン抜き出し
		//括弧部部分削除
		Pattern p_domain = Pattern.compile("\\([^\\(\\)]*?\\)");
		while (true) {
            domain = p_domain.matcher(domainAndIp).replaceAll("");
            if (domainAndIp.equals(domain)) {
                break;
            }
            domainAndIp = domain;
        }
		System.out.println("ドメイン:"+domain + ", Ipアドレス:"+ ipaddress);
	}
	
	//レスポンス値の設定
	private static double getResponsetime(int length, int num, String[] responsetime){
		String responsetime_string;
		double set_responsetime=0;
		//配列の数が指定のレスポンス値分あるか
		if(length >= (num+1)){
			responsetime_string = responsetime[num];
			responsetime_string = responsetime_string.replaceAll("ms", "");//指定の文字列からmsを除く
			if(responsetime_string.contains("*")){ //アスタリスクがある場合はアスタリスクを消す
				responsetime_string = responsetime_string.replaceAll("\\*", "");
			}else if(responsetime_string.contains("(")){//(が含まれている場合ドメイン当が含まれているから隣のgetする配列をずらす
				responsetime_string = responsetime[num+1];
				responsetime_string = responsetime_string.replace("ms", "");
//				separateDomainIp(responsetime[num]);
			}
			set_responsetime = Double.parseDouble(responsetime_string.trim());
		}else{//該当する配列がなければレスピンス値を0にする
			set_responsetime = 0;
		}
		return set_responsetime;
	}
	
	//分割したデータを格納するメソッド
	//ComponentofTraceroute を宣言　
	//宣言.setHopnum(配列)
	public void setTracerouteValue(){
		ComponentofTraceroute cot = new ComponentofTraceroute();
		cot.setHopnum(hop_num);//ホップ数のセット
		cot.setDomain(domain); //ドメインをセット
		cot.setIp(ipaddress); //IPアドレスのセット 
		cot.setResponseTime1(responsetime1); //レスポンス値1セット
		cot.setResponseTime2(responsetime2); //レスポンス値2セット
		cot.setResponseTime3(responsetime3); //レスポンス値3セット
		//Tracerouteクラスに値を渡す
		Traceroute tre = new Traceroute();
		tre.setTracerouteValue(cot.getHopnum(), cot.getDomain(), cot.getIp(), cot.getResponseTime1(), cot.getResponseTime2(), cot.getResponseTime3());
		//TracerouteCalculationクラスに値を渡す
		TracerouteCalculation tracal = new TracerouteCalculation();
		tracal.setValue(tre.setTracerouteMap(),cot.getHopnum());
	}
		
}
