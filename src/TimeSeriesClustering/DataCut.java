package TimeSeriesClustering;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DataCut {
	final static int SLIDE_WIDE = 30; // スライド幅(30秒)
	final static int WINDOW_WIDE = 60; // ウィンドウ幅(1分)
	// 通信量(1秒)のデータを入れるリスト
	static ArrayList<Long> traffic_data = new ArrayList<Long>();
	// カットしたデータを格納
	static long[][] traffic_cut_data;
	SlideDataCalclator sdc = new SlideDataCalclator();

	long data_ave;
	double data_var;

	// csvデータを持ってくる
	public void setTrafficData(ArrayList<Long> trafficdata) {
		this.traffic_data = trafficdata;
		// setCutData();
	}

	// 通信量の特徴量
	ArrayList<Long> traffic_data_ave_list = new ArrayList<Long>();
	ArrayList<Double> traffic_data_var_list = new ArrayList<Double>();
	long[] traffic_data_ave = new long[traffic_data.size()];
	double[] traffic_data_var = new double[traffic_data.size()];

	// データをカット
	public void setSlideData() {
		int count = 0;
		traffic_cut_data = new long[traffic_data.size()][WINDOW_WIDE];
		// データ全体を回す
		System.out.println("-----------TEST----------");
		for (int i = 0; i < traffic_data.size(); i++) {
			// SLIDE_WIDE*count がtraffic_dataのサイズより小さい場合のみ
			// ウィンドウ幅分回す
			if ((i + SLIDE_WIDE * count) < traffic_data.size()) {
				for (int j = 0; j < WINDOW_WIDE; j++) {
					// j+SLIDE_WIDE*countでスライドさせる
					traffic_cut_data[i][j] = traffic_data.get(j + SLIDE_WIDE * count);
				}
			} else {
				break;
			}
			// 平均を求める
			sdc.average(traffic_cut_data[i]);
			// 標準偏差を求める
			sdc.variance(traffic_cut_data[i]);
			System.out.println("++++++" + sdc.data_var());
			// リストに入れる
			traffic_data_ave_list.add(sdc.data_ave());
			traffic_data_var_list.add(sdc.data_var());
			count++;
		}
	}

	public long[][] getSlideData() {
		return traffic_cut_data;
	}

	public ArrayList<Long> getTrafficAverage() {
		return traffic_data_ave_list;
	}

	public ArrayList<Double> getTrafficVariance() {
		return traffic_data_var_list;
	}

	public void inputFile() {
		String file_name = "data.csv";
		// ファイル呼び込み
		try {
			File file = new File(file_name);
			if (checkBeforeWritefile(file)) {
				PrintWriter pw = new PrintWriter(file);
				pw.write("\"No.\",\"average\",\"variance\"");
				for (int i = 0; i < traffic_data_ave_list.size(); i++) {
					pw.write( "\""+i+"\",\"" + String.valueOf(traffic_data_ave_list.get(i)) +"\",\"" +String.valueOf(traffic_data_var_list.get(i)) + "\"\n");
				}
				pw.close();
			} else {
				System.out.println("ファイルに書き込めません");
			}

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private static boolean checkBeforeWritefile(File file) {
		if (file.exists()) {
			if (file.isFile() && file.canWrite()) {
				return true;
			}
		}
		return false;
	}
}
