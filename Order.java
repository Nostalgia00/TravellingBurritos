import java.lang.Math.*;
public class Order{
	private String label; //stores the order number
	private int time;	//how long they have been waiting
	private double N;	//longitude
	private double W;	//latitude
	private boolean isDelivered;	//has it been delivered
	private int maxEdges;		//maximum number of connections
	private int numEdges;		//current number of connections
	
	public Order(){
		numEdges=0;
		maxEdges = (int)(Math.random()*75)+20; //let the number of allowed connections be between 20 and 95
		time = 0;
		N = 0;
		W=0;
	}
	public Order(String label, int t, double n, double w){
		numEdges=0;
		maxEdges=(int)(Math.random()*75)+20; //let the number of allowed connections be between 20 and 95
		//(int)(Math.random()*3)+2;
		time = t;
		N = n;
		W=w;
		isDelivered = false;
	}
	//getter functions
	public int getTime(){
		return time;
	}
	public double getN(){
		return N;
	}
	public double getW(){
		return W;
	}	
	public void completeOrder(){
		isDelivered = true;
	}
	public boolean checkOrder(){
		return isDelivered;
	}
	public boolean isMaxed(){
		return (numEdges>=maxEdges);
	}
	public int getMaxEdges(){
		return maxEdges;
	}
	public int getNumEdges(){
		return numEdges;
	}
	public int incNumEdges(){
		return numEdges++;
	}

}