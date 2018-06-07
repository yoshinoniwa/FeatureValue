//tracerouteの出力結果を一行ずつ配列にまとめるクラス
public class Traceroute {
	public static String[] tracerouteresult(String line){
//		String aaa=line;
		String[] output = line.split("  ");
//		ArrayList<String[]> tracelist = new ArrayList<String[]>();
		System.out.print("出力結果");
		for(int i=0;i<output.length;i++){
			System.out.print(" "+output[i]+",");
		}
		System.out.println("");
		
		return output;
	}
}
