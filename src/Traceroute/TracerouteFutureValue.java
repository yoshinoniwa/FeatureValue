package Traceroute;

import java.util.ArrayList;

public class TracerouteFutureValue {
	 double average_traceroute;
	 ArrayList<Double> list_responsetime = new ArrayList<Double>();	
	
	public void setFutureValue(double ave){
		average_traceroute = ave;
		System.out.println("ave "+ list_responsetime.size());
	}
	public void setList(){
		list_responsetime.add(average_traceroute);
	}
	
	public void show(){
		System.out.println("リストサイズ"+list_responsetime.size());
	}
	
}
