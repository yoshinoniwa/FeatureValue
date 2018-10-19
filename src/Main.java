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
import TimeSeriesClustering.DataCut;

public class Main {
	public static void main(String args[]) throws ParseException {
		try {
			// csvファイル(moviestreaming-0514)読み込み
			// File file = new File("./sencing/sencing01");
			File file = new File("./16-09-24.csv");
			BufferedReader br = new BufferedReader(new FileReader(file));
			ArrayList<String[]> filelist = new ArrayList<String[]>(); // csvファイルを格納するArrayListの準備
			// 時間 計算用
			ArrayList<Long> timeDiffList = new ArrayList<Long>();
			long time_dif = 0;
			double time_var_sub = 0;
			double time_var = 0;
			double time_dis = 0;
			long time_sum = 0;
			double time_ave = 0;
			int count = 0;

			// データ量計算用
			String dataChangeDblquo;
			String dataChange;
			long dataSum = 0;
			long dataLong = 0;
			boolean isDiff = true;
			ArrayList<Long> dataSumList = new ArrayList<Long>();
			double data_var_sub = 0;
			double data_var = 0;
			long bps = 0;
			long kbps = 0;

			double communication_avetime;
			long communication_distance;
			// グラフ用
			ArrayList<Long> data_list_graph = new ArrayList<Long>();
			// ArrayList<Long> kbps_list = new ArrayList<Long>();
			int time_smb = 0;

			// スライドカットしたデータ
			long[][] slide_data;
			long[] slide_data_time_ave;
			long[] slide_data_time_var;
			long[] slide_data_data_ave;
			double[] slide_data_data_var;
			// ArrayList<Long> slide_data_list = new ArrayList<Long>();

			// ArrayList:filelistにCSVファイルを格納
			while (br.ready()) {
				String line = br.readLine();
				filelist.add(line.split(",")); // ,で区切って読み込み
			}
			// 2次元配列の準備
			// fileString[][1] 時間, fileString[][3] 通信先, fileString[][5] データ量
			// ダブルコーテーションが入っている
			String[][] fileString = new String[filelist.size()][]; // csvファイル読み込み用配列
			String[][] fileDbleQuo = new String[filelist.size()][]; // csvファイル読み込み用配列
			String dbleQuo;
			for (int i = 0; i < filelist.size(); i++) {
				fileDbleQuo[i] = filelist.get(i);
				fileString[i] = filelist.get(i); // ArrayListから二次元配列
				for (int k = 0; k < 5; k++) {
					dbleQuo = fileDbleQuo[i][k];
					fileString[i][k] = dbleQuo.replace("\"", "");
				}
				// dataをlong型にかえるための下準備
				dataChangeDblquo = fileString[i][5];
				dataChange = dataChangeDblquo.replace("\"", "");

				// データ量の平均
				// ----------------------------------------TODO-------------------------------------------------------
				// Calculationクラスでやる
				if (i > 1) {
					dataLong = Long.parseLong(dataChange);// string型をlong型に変える
					// 通信していない秒数を算出
					time_dif = DataCalculation.difference(fileString[i][1], fileString[i - 1][1]);
					timeDiffList.add(time_dif);
					// System.out.println("差" + dif);
					// 1秒の差がない時はfalse 1秒の差がある時にtrue
					if (time_dif < 1) {
						isDiff = false;

					} else {
						count++;
						// System.out.println(i+" : "+fileString[i][1]);
						data_list_graph.add((long) 0);
						isDiff = true;
					}
					// 1秒の差がない時にデータ量を足す
					if (!isDiff) {
						dataSum += dataLong;

					} else if (isDiff) {
						if (dataSum != 0) {
							dataSumList.add(dataSum);
							// for(int j=0;j<dataSum;j++){
							// data_list_graph.add((long)0);
							// }
							// data_list_graph.add((long)0);
							// System.out.println("----------"+fileString[i][1]);
							// System.out.println(dataSum);
						} else {
							dataSumList.add(dataLong);
							data_list_graph.add(dataLong);
						}
						dataSum = 0;
					}
				} else {
				}
				// -----------------------------------------------------------------------------------------------------------
			}
			// DistinctionIpaddressにログを渡す
			// DistinctionIpaddress di = new DistinctionIpaddress();
			// di.setLog(fileString);
			// //他クラス呼び出し用
			// //Tracerouteの実行結果(通信先の距離)
			// TracerouteExec tracerouteexec = new TracerouteExec();
			// tracerouteexec.systemCall(fileString[3][3]);
			// //Pingの実行結果(通信時間)
			// PingExec pingexec = new PingExec();
			// pingexec.SystemCall(fileString[3][3]);
			// communication_avetime = pingexec.getAveTime();
			// //通信先の距離IPStaticのAPI使用
			// IpstackApi isa = new IpstackApi();
			// isa.getApi(fileString[3][3]);
			// communication_distance = (long)isa.distance();

			time_ave = DataCalculation.average(timeDiffList);
			bps = (long) DataCalculation.average(dataSumList);
			kbps = (bps / 100) * 8;
			for (int i = 0; i < count; i++) {
				time_var_sub += DataCalculation.variance(time_ave, timeDiffList.get(i));
				data_var_sub += DataCalculation.variance(kbps, dataSumList.get(i));
			}
			// for(int i=0;i<50;i++){
			// slide_data_data_var[i] = DataCalculation.variance()
			// }
			time_var = Math.sqrt(time_var_sub / count);
			time_dis = time_ave / count;
			data_var = Math.sqrt(data_var_sub / count);

			// int count_t=0;
			//
			// for(int i=0;i<timeDiffList.size();i++){
			// if(timeDiffList.get(i)!=0){
			// data_list_graph.add((long)0);
			// }else if(timeDiffList.get(i)==0){
			// data_list_graph.add((long)dataSumList.get(count_t));
			// count_t++;
			// }
			// }

			// 特徴量を１つのクラスにまとめる
			SetFeatureValue sfv = new SetFeatureValue();
			sfv.setTimeAve(time_ave);
			sfv.setTimVar(time_var);
			sfv.setTimeDis(time_dis);
			sfv.setKbps(kbps);
			// sfv.setCommuDitance(communication_distance);
			// sfv.setCommuAveTime(communication_avetime);
			for (int i = 0; i < dataSumList.size(); i++) {
				System.out.println(dataSumList.get(i));
			}
			DataCut dc = new DataCut();
			dc.setTrafficData(dataSumList);
			dc.setSlideData();
			slide_data = dc.getSlideData();
			dc.setTrrafficAverage();
			dc.setTrafficVariance();
			slide_data_data_ave = dc.getTrafficAverage();
			slide_data_data_var = dc.getTrafficVariance();

			// for(int i=0;i<timeDiffList.size();i++){
			// System.out.println(timeDiffList.get(i));
			// }
			// System.out.println("------------------------");
			// for(int i=0;i<data_list_graph.size();i++){
			// System.out.println(data_list_graph.get(i));
			// }
			// System.out.println("------------------------");
			// for(int i=0;i<data_list_graph.size();i++){
			// System.out.println(timeDiffList.get(i));
			// }
			// System.out.println("リストサイズ" + dataSumList.size());
			// System.out.println("カウント" + count);
			System.out.println("----------SCV形式----------");
			System.out.println(
					"\"time_ave(sec)\",\"time_var\",\"kbps\",\"communication_distance(m)\",\"communication_avetime(sec)\"");
			// System.out.println("\""+time_ave+"\",\""+time_var+"\",\""+kbps+"\",\""+
			// communication_distance+"\",\""+communication_avetime+"\"");
			// System.out.println("----------arff形式----------");
			// System.out.println("time_ave(sec),time_var,kbps,communication_distance(m),communication_avetime(sec)");
			// System.out.println(time_ave+","+time_var+","+kbps+","+
			// communication_distance+","+communication_avetime);
			System.out.println("データ量標準偏差" + data_var);
			// for(int i=0;i<slide_data_data_var.length;i++){
			// System.out.println("++++++" + slide_data_data_var[i]);
			// }
			br.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
