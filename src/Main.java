import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

//import Traceroute.Traceroute;
import Traceroute.TracerouteExec;
//import Traceroute.TracerouteCalculation;
//import Traceroute.TracerouteFutureValue;
import Ping.PingExec;
import Ipgelocation.IpstackApi;
import CommunicationPattern.DistinctionIpaddress;

public class Main {
	public static void main(String args[]) throws ParseException {
		try {
			//csvファイル(moviestreaming-0514)読み込み
			File file = new File("./streaming/music14");
		    BufferedReader br = new BufferedReader(new FileReader(file));
		    ArrayList<String[]> filelist = new ArrayList<String[]>(); //csvファイルを格納するArrayListの準備
		      
		    //時間 計算用
		    ArrayList<Long> timeDiffList = new ArrayList<Long>();
		    long time_dif=0;
		    double time_var_sub=0;
		    double time_var=0;
		    double time_dis=0;
		    long time_sum=0;
		    double time_ave=0;
		    int count=0;
		    
		    //データ量計算用
		    String dataChangeDblquo;
		    String dataChange;
		    long dataSum =0;
		    long dataLong=0;
		    boolean isDiff = true;
		    ArrayList<Long> dataSumList = new ArrayList<Long>();
		    long bps=0;
		    long kbps =0;
		   
		    double communication_avetime;
		    double communication_distance;
		    
		    //ArrayList:filelistにCSVファイルを格納
		    while (br.ready()){
		    	String line = br.readLine();
		    	filelist.add(line.split(",")); //,で区切って読み込み
		    }
		    //2次元配列の準備
		    //fileString[][1] 時間, fileString[][3] 通信先, fileString[][5] データ量
		    //ダブルコーテーションが入っている
		    String[][] fileString = new String[filelist.size()][]; //csvファイル読み込み用配列
		    String[][] fileDbleQuo = new String[filelist.size()][]; //csvファイル読み込み用配列
		    String dbleQuo;
		    for(int i=0;i<filelist.size();i++){
		    	fileDbleQuo[i] = filelist.get(i);
		    	fileString[i]=filelist.get(i); //ArrayListから二次元配列
		    	for(int k=0;k<5;k++){
		    		dbleQuo = fileDbleQuo[i][k];
		    		fileString[i][k]=dbleQuo.replace("\"","");
		    	}
		    	//dataをlong型にかえるための下準備
		    	dataChangeDblquo=fileString[i][5];
		    	dataChange = dataChangeDblquo.replace("\"","");
		    	
		    	//データ量の平均
		    	//----------------------------------------TODO-------------------------------------------------------
		    	//Calculationクラスでやる
		    	if(i>1){
		    		dataLong = Long.parseLong(dataChange);//string型をlong型に変える
		    		time_dif = DataCalculation.difference(fileString[i][1], fileString[i-1][1]);//difにCalculationのdifferenceを入れる
		    		timeDiffList.add(time_dif);
//		    		System.out.println("差" + dif);
		    		//1秒の差がない時はfalse 1秒の差がある時にtrue
		    		if(time_dif<1){
		    			isDiff=false;
		    		}else{
		    			count++;
//		    			System.out.println(i+" : "+fileString[i][1]);
		    			isDiff=true;
		    		}
		    		//1秒の差がない時にデータ量を足す
		    		if(!isDiff){
			    		dataSum += dataLong;
			    	}else if(isDiff){
			    		if(dataSum !=0){
			    			dataSumList.add(dataSum);
//			    			System.out.println("----------"+fileString[i][1]);
//			    			System.out.println(dataSum);
			    		}else{
			    			dataSumList.add(dataLong);
			    		}
			    		dataSum =0;
			    	}
		    	}else{}
		    	//-----------------------------------------------------------------------------------------------------------
		    }
		    //DistinctionIpaddressにログを渡す
	    	DistinctionIpaddress di = new DistinctionIpaddress();
	    	di.setLog(fileString);
		    //他クラス呼び出し用
			//Tracerouteの実行結果(通信先の距離)
			TracerouteExec tracerouteexec = new TracerouteExec();
			tracerouteexec.systemCall(fileString[3][3]);
			//Pingの実行結果(通信時間)
			PingExec pingexec = new PingExec();
			pingexec.SystemCall(fileString[3][3]);
			communication_avetime = pingexec.getAveTime();
			//通信先の距離IPStaticのAPI使用
			IpstackApi isa = new IpstackApi();
			isa.getApi(fileString[3][3]);
			communication_distance = isa.distance();
			   
		    time_ave = DataCalculation.average(timeDiffList);
		    for(int i=0;i<count;i++){
		    	time_var_sub = DataCalculation.variance(time_ave, timeDiffList.get(i), count);
			 }
		     time_var = Math.sqrt(time_ave/count);
		     time_dis = time_ave/count;
		     bps = (long)DataCalculation.average(dataSumList);
		     kbps = (bps/100)*8;
		     
		     //特徴量を１つのクラスにまとめる
		     SetFeatureValue sfv = new SetFeatureValue();
		     sfv.setTimeAve(time_ave);
		     sfv.setTimVar(time_var);
		     sfv.setTimeDis(time_dis);
		     sfv.setKbps(kbps);
		     sfv.setCommuDitance(communication_distance);
		     sfv.setCommuAveTime(communication_avetime);
		     for(int i=0;i<dataSumList.size();i++){
		    	 System.out.println(dataSumList.get(i));
		     }
//		     System.out.println("リストサイズ" + dataSumList.size());
//		     System.out.println("カウント" + count);
		     System.out.println("----------SCV形式----------");
		     System.out.println("\"time_ave(sec)\",\"time_var\",\"kbps\",\"communication_distance(m)\",\"communication_avetime(sec)\"");
		     System.out.println("\""+time_ave+"\",\""+time_var+"\",\""+kbps+"\",\""+ communication_distance+"\",\""+communication_avetime+"\"");
		     System.out.println("----------arff形式----------");
		     System.out.println("time_ave(sec),time_var,kbps,communication_distance(m),communication_avetime(sec)");
		     System.out.println(time_ave+","+time_var+","+kbps+","+ communication_distance+","+communication_avetime);
		    br.close();
		    } catch (IOException e) {
		    	System.out.println(e);
		    }
	}
}
