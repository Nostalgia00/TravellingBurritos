public class Connection{
	private double travelTime; //time taken to travel this connection
	private int i;	//starting index
	private int j;	//ending index

	public Connection(double time, int n, int m){
		travelTime = time;
		i = n;
		j = m;
	}
	//getter functions
	public int getIndexI(){
		return i;
	}
	public int getIndexJ(){
		return j;
	}
	public double getTime(){
		return travelTime;
	}
}