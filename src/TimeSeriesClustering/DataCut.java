package TimeSeriesClustering;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.crypto.Data;

public class DataCut {
	final static int SLIDE_WIDE = 30; // スライド幅(30秒)
	final static int WINDOW_WIDE = 120; // ウィンドウ幅(60秒)
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
			// System.out.println("++++++" + sdc.data_var());
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
		Calendar myCal = Calendar.getInstance();
		DateFormat myFormat = new SimpleDateFormat("yyyy_MM_dd_HHmm");
		String file_csv_name = "./weka_file/" + myFormat.format(myCal.getTime()) + ".csv";
		String file_arff_name = "./weka_file/" + myFormat.format(myCal.getTime()) + ".arff";
		File new_csv_file = new File(file_csv_name);
		File new_arff_file = new File(file_arff_name);
		// ファイル作成
		try {
			new_csv_file.createNewFile();
			new_arff_file.createNewFile();
			if (new_csv_file.createNewFile()) {
				System.out.println("ファイルの作成に成功しました");
			} else {
				System.out.println("ファイルの作成に失敗しました");
			}
			// CSVファイル作成
			if (checkBeforeWritefile(new_csv_file)) {
				PrintWriter pw = new PrintWriter(new_csv_file);
				pw.write("\"No.\",\"average\",\"variance\",\"class\"\n");
				for (int i = 0; i < traffic_data_ave_list.size(); i++) {
					pw.write(i + "," + String.valueOf(traffic_data_ave_list.get(i)) + ","
							+ String.valueOf(traffic_data_var_list.get(i)) + ",label\n");
				}
				pw.close();
			} else {
				System.out.println("ファイルに書き込めません");
			}
			

			// ARFFファイル作成
			if (new_arff_file.createNewFile()) {
				System.out.println("ファイルの作成に成功しました");
			} else {
				System.out.println("ファイルの作成に失敗しました");
			}
			if (checkBeforeWritefile(new_arff_file)) {
				PrintWriter pw = new PrintWriter(new_arff_file);
				pw.write("@relation traffic_pattern\n");
				pw.write("\n");
				pw.write("@attribute dataave real\n");
				pw.write("@attribute datavar real\n");
				pw.write("@attribute class {label}\n");
				pw.write("\n");
				pw.write("@data\n");
				for (int i = 0; i < traffic_data_ave_list.size(); i++) {
					pw.write(String.valueOf(traffic_data_ave_list.get(i)) + ","
							+ String.valueOf(traffic_data_var_list.get(i)) + ",label\n");
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
