import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Calculation {
	//差を求める
	public static long difference(String timeTo,String timeFrom)throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //時間のデータフォーマット　ミリ秒
		Date timeToDate,timeFromDate; //時間をdate型に変更
		long timeToLong,timeFromLong; //long型に変更
		long diff=0; //差
	
		try{
			//Date型に時間を入れている
			timeToDate = sdf.parse(timeTo);
			timeFromDate = sdf.parse(timeFrom);
			//Date型からlong型へ変更
			timeToLong = timeToDate.getTime();
			timeFromLong = timeFromDate.getTime();
			//秒数の差
			diff = (timeToLong - timeFromLong)/1000;
//			System.out.println(timeToDate);
//			System.out.println(diff);
		}catch (ParseException e){
			System.out.println(e);
		}
		return diff;
	}
	//和を求める
	public static long addition(ArrayList<Long> list){
		long sum=0;
		for(int i=0;i<list.size();i++){
			sum += list.get(i);
		}
		return sum;
	}
	//平均を求める
	public static long average(ArrayList<Long> list){
		long ave=0;
		long sum = addition(list);
		ave = sum/list.size();
		return ave;
	}
	
	public static void result(){
		
	}
	
}
