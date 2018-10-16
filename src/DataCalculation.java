import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataCalculation {
	//差を求める
	public static long difference(String timeTo,String timeFrom)throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //時間のデータフォーマット　ミリ秒
		Date timeToDate,timeFromDate; //時間をdate型に変更
		long timeToLong,timeFromLong; //long型に変更
		long time_diff=0; //差
	
		try{
			//Date型に時間を入れている
			timeToDate = sdf.parse(timeTo);
			timeFromDate = sdf.parse(timeFrom);
			//Date型からlong型へ変更
			timeToLong = timeToDate.getTime();
			timeFromLong = timeFromDate.getTime();
			//秒数の差
			time_diff = (timeToLong - timeFromLong)/1000;
//			System.out.println(timeToDate);
//			System.out.println(diff);
		}catch (ParseException e){
			System.out.println(e);
		}
		return time_diff;
	}
	//和
	public static long addition(ArrayList<Long> list){
		long sum=0;
		for(int i=0;i<list.size();i++){
			sum += list.get(i);
		}
		return sum;
	}
	//平均
	public static double average(ArrayList<Long> list){
		double ave=0.0;
		double sum = (double)addition(list);
		ave = sum/list.size();
		return ave;
	}
	//標準偏差
	public static double variance(double time_ave,long diff){
		double sum =0;
//		double var=0;
		sum = (((double)diff-time_ave)*((double)diff-time_ave));

		return sum;
		}
	
	
	public static void result(){
		
	}
	
}
