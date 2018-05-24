import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


public class Main {
	public static void main(String args[]) throws ParseException {
		try {
			//csvファイル(moviestreaming-0514)読み込み
			File file = new File("musicstreaming-0518");
		    BufferedReader br = new BufferedReader(new FileReader(file));
		    ArrayList<String[]> filelist = new ArrayList<String[]>(); //csvファイルを格納するArrayListの準備
		      
		    //Time 計算用
		    ArrayList<Long> timeDiffList = new ArrayList<Long>();
//		    long timeDiff[] = new long[filelist.size()];
		    long dif=0;
		    
		    //Data計算用
		    String dataChangeDblquo;
		    String dataChange;
		    long dataSum =0;
		    long dataLong=0;
		    boolean isDiff = true;
		    ArrayList<Long> dataSumList = new ArrayList<Long>();
		    long bps=0;
		    long kbps =0;
		    
		    //秒数確認
		    int s_count=0;
		    //ArrayList:filelistにCSVファイルを格納
		    while (br.ready()){
		    	String line = br.readLine();
		    	filelist.add(line.split(",")); //,で区切って読み込み
		    }
		    //2次元配列の準備
		    //fileString[][1] 時間, fileString[][3] 通信先, fileString[][5] データ量
		    String[][] fileString = new String[filelist.size()][]; //csvファイル読み込み用配列
		    for(int i=0;i<filelist.size();i++){
		    	fileString[i]=filelist.get(i); //ArrayListから二次元配列
		    	//dataをlong型にかえるための下準備
		    	dataChangeDblquo=fileString[i][5];
		    	dataChange = dataChangeDblquo.replace("\"","");
		    	
		    	//--------------------TODO-------------------------------------
		    	//Calculationクラスでやる
		    	if(i>1){
		    		dataLong = Long.parseLong(dataChange);//data型をlong型に変える
		    		dif = Calculation.difference(fileString[i][1], fileString[i-1][1]);//difにCalculationのdifferenceを入れる
		    		timeDiffList.add(dif);
		    		
		    		//1秒の差がない時はfalse 1秒の差がある時にtrue
		    		if(dif<1){
		    			isDiff=false;
		    		}else{
		    			s_count += 1;
		    			System.out.println(fileString[i][1]);
		    			isDiff=true;
		    		}
		    		//1秒の差がない時にデータ量を足す
		    		if(!isDiff){
			    		dataSum += dataLong;
			    	}else if(isDiff){
			    		if(dataSum !=0){
//			    			System.out.println(dataSum);
			    			dataSumList.add(dataSum);
			    		}
			    		dataSum =0;
			    	}
		    	}else{}
		    	//---------------------------------------------------------
		    }
		     bps = Calculation.average(dataSumList);
		     System.out.println("データ量 (B/s)"+bps);
		     kbps = (bps/100)*8;
		     System.out.println("Kbps " + kbps);
		     System.out.println("リストサイズ" + dataSumList.size());
		     System.out.println("秒数" + s_count);
		     System.out.println("ファイルサイズ" + filelist.size());
		    br.close();
		    } catch (IOException e) {
		    	System.out.println(e);
		    }
	}
}
