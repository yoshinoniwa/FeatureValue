package TimeSeriesClustering;

import java.util.ArrayList;

public class DataCut {
	final static int SLIDE_WIDE = 30; // スライド幅(30秒)
	final static int WINDOW_WIDE = 60; // ウィンドウ幅(1分)
	// 通信量(1秒)のデータを入れるリスト
	static ArrayList<Long> traffic_data = new ArrayList<Long>();
	// カットしたデータを格納
	static long[][] traffic_cut_data;
	static long[] traffic_pass = new long[WINDOW_WIDE];
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
					traffic_pass[j] = traffic_cut_data[i][j];
				}
			} else {
				break;
			}
			sdc.average(traffic_cut_data[i]);
			sdc.variance(traffic_pass);
			System.out.println("++++++" + sdc.data_ave());
			traffic_data_ave_list.add(sdc.data_ave());
			traffic_data_var_list.add(sdc.data_var());
			traffic_pass = new long[WINDOW_WIDE];
			count++;
		}
	}

	public long[][] getSlideData() {
		return traffic_cut_data;
	}

	public void setTrrafficAverage() {
//		// traffic_data_ave = new long[traffic_data.size()];
//		for (int i = 0; i < traffic_data.size(); i++) {
//			traffic_data_ave[i] = sdc.data_ave();
////			System.out.println("++++++" + sdc.data_ave());
//		}
		
	}

	public long[] getTrafficAverage() {
		return traffic_data_ave;
	}

	public void setTrafficVariance() {
		traffic_data_var = new double[traffic_data.size()];
		for (int i = 0; i < traffic_data.size(); i++) {
			traffic_data_var[i] = sdc.data_var();
		}
	}

	public double[] getTrafficVariance() {
		return traffic_data_var;
	}

	public void show() {
		for (int i = 0; i < traffic_data.size(); i++) {
			System.out.println("++" + traffic_data_ave[i] + "," + traffic_data_var[i]);
		}
	}
}
