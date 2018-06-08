import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//tracerouteの出力結果を解析していく
public class TracerouteInfo {
	public static String[] trancerouteofClassfication(String[] result, int num){
		String[] line = result;
//		for(int i=0;i<line.length;i++){
//        	System.out.println(line[i]);
//        }
		ArrayList<String> tracerouteList = new ArrayList<String>(Arrays.asList(line));
		String tracerouteNumber = line[0]; //経路数
		String tracerouteIpaddress = line[1]; //ipアドレス
		System.out.println("経路数 : "+ tracerouteNumber);
//		System.out.println("ipアドレス : "+ tracerouteIpaddress);
		
		tracerouteIpAddress(tracerouteIpaddress);
		return line;
	}
	public static String tracerouteIpAddress(String result){
		String tracerouteResult = result;
		String withoutOther = "\\((.+?)\\)";
		String ipAddresskakko;
		String ipAddress;
		
		Pattern p = Pattern.compile(withoutOther);
		Matcher m = p.matcher(tracerouteResult);
		List<String> list = new ArrayList<String>();
        while (m.find()) {
            list.add(m.group());
        }
        for (String str : list) {
//        	ipAddresskakko = str.replace("(", "");
//        	ipAddress = ipAddresskakko.replace(")", "");
            System.out.println("ipアドレス"+str);
        }
		return "aaa";
	}
}
