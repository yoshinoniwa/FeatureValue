import java.io.BufferedReader;
//import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
//import org.omg.CORBA.portable.InputStream;

public class SystemCall {
	 public static String communicationDestination(String ipAdrress) throws IOException {
	        Runtime runtime = Runtime.getRuntime();
//	        String ipAddress = "google.co.jp"; // mecabで形態素解析したいtxtファイルを指定

	        String[] Command = { "traceroute" , ipAdrress }; // cmd.exeでmecab.exeを起動し指定したファイル(filePath)を形態素解析する
	        String[] tracerouteLine; 
	        Process process = null;
	        int count = 0; 
	        
	        try {
	            process = runtime.exec(Command); // 実行ディレクトリ(dir)でCommand(mecab.exe)を実行する
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
	        String tracerouteResult;
	        while (true) {
	            tracerouteResult = br.readLine();
	            if (tracerouteResult == null) {
	                break; // 全ての行を読み切ったら抜ける
	            } else {
	                System.out.println("line : " + tracerouteResult); // 実行結果を表示
	                tracerouteLine = Traceroute.tracerouteresult(tracerouteResult);
//	                System.out.println("配列");
//	                for(int i=0;i<tracerouteLine.length;i++){
//	                	System.out.println(tracerouteLine[i]);
//	                }
	                TracerouteInfo.trancerouteofClassfication(tracerouteLine,count);
	                count++;
	            }
	        }
//	        Traceroute.tracerouteresult(line);
	        return tracerouteResult;
	    }
}
