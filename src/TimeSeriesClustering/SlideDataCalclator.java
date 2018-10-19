package TimeSeriesClustering;

public class SlideDataCalclator {
	long data_ave;
	double data_var;
	//平均
	public void average(long[] data){
		long sum=0;
		long ave=0;
		for(int i=0;i<data.length;i++){
			sum += data[i];
		}
		ave = sum/data.length;
		data_ave = ave;
	}
	public long data_ave(){
		return data_ave;
	}
	//分散
	public void variance(long data[]){
		long sum = 0;
		double var=0;
		for(int i=0;i<data.length;i++){
			sum=(data[i]-data_ave)*(data[i]-data_ave);
		}
		var = Math.sqrt((double)sum/data.length);
		data_var = var;
	}
	public double data_var(){
		return data_var;
	}
}
