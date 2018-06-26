package Traceroute;

//セット作る
class ComponentofTraceroute {
	double hop_num;
	String ip;
	String domain;
	double responsetime1,responsetime2,responsetime3;
	
	//ホップの取得
	//ホップのセット
	public double getHopnum(){
		return hop_num;
	}
	public void setHopnum(double hop_num){
		this.hop_num = hop_num;
	}
	
	//IPアドレスの取得
	//IPアドレスのセット
	public String getIp(){
		return ip;
	}
	public void setIp(String ip){
		this.ip = ip;
	}
	
	//ドメインの取得
	//ドメインのセット
	public String getDomain(){
		return domain;
	}
	public void setDomain(String domain){
		this.domain = domain;
	}
	
	//レスポンス値(1回目)の取得
	//レスポンス値(1回目)のセット
	public double getResponseTime1(){
		return responsetime1;
	}
	public void setResponseTime1(double responsetime1){
		this.responsetime1 = responsetime1;
	}
	//レスポンス値(2回目)の取得
	//レスポンス値(2回目)のセット
	public double getResponseTime2(){
		return responsetime3;
	}
	public void setResponseTime2(double responsetime2){
		this.responsetime2 = responsetime2;
	}
	//レスポンス値(3回目)の取得
	//レスポンス値(3回目)のセット
	public double getResponseTime3(){
		return responsetime3;
	}
	public void setResponseTime3(double responsetime3){
		this.responsetime3 = responsetime3;
	}
}
