import java.lang.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.io.IOException;
public class burrito{
	public static double radiusOfEarth = 6371;
	
	public static void main(String[] args){		
		int s=0;
		char[] animationChars = new char[]{'|', '/', '-', '\\'};
		double bestTime= Double.MAX_VALUE;
		double N=100000000d;
		//run the program many times to find the best solution
		for(int z=0;z<N;z++){
			//initialise variables
			int numL =101;
			Order[] order = new Order[numL]; //stored the array of order objects
	        double n=0;
	        double w=0;
	        int time=0;
	        boolean[][] adjMat = new boolean[numL][numL]; //the adjacency array

	        //read data from csv file
			String csvFile = "orderList.txt.csv";
	        String line = "";
	        String cvsSplitBy = ",";
	        
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] anOrder = line.split(cvsSplitBy);
	                time=60-Integer.parseInt(anOrder[1].substring(2,4));//will store the time the order has been waiting for initialy
	                n=Double.parseDouble(anOrder[2]); //longitude
	                w=Double.parseDouble(anOrder[3]);//latitude
	                order[Integer.parseInt(anOrder[0])]= new Order(anOrder[0], time, n, w);
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			//enable certain connections
			for(int i=0; i<numL; i++){
				priorityQueue conQ = new priorityQueue(numL); //priority queue will sort based on timetaken
				if(!order[i].isMaxed()){ //if order i is not maxed on edges
					for(int j=0; j<numL; j++){
						if(i!=j){
							double timeTaken = timeTaken(order[i].getN(), order[j].getN(), order[i].getW(), order[j].getW());
							conQ.insert(new Connection(timeTaken, i, j), order);
						}
					}
					// //checks for existing connections and dumps that number
					 int num = order[i].getNumEdges();
					// for(int l =0; l<num;l++){//remove existing
						// Connection dump = conQ.remove();
					// }
					
					for(int k=0; k<(order[i].getMaxEdges()-num);k++){
						Connection edge = conQ.remove();
						adjMat[edge.getIndexI()][edge.getIndexJ()]=true;
						adjMat[edge.getIndexJ()][edge.getIndexI()]=true;
						order[edge.getIndexI()].incNumEdges();
						order[edge.getIndexJ()].incNumEdges();
					}
				}
			}
			//printMat(adjMat);

			int current=0;
			int visits=0;
			String visitOrder = "";
			double totalTime = 0;
			double overTime =0;
			Connection selected;
			try{
				do {
					visits++;
					visitOrder+=current+",";
					double howLong = order[current].getTime()+totalTime;
					if(howLong>30)
						overTime+=howLong-30;
					order[current].completeOrder();
					priorityQueue edgeQ = new priorityQueue(101);
					//System.out.println(current+", "+visits);
					if(visits>=101){
						if(overTime<bestTime){
							bestTime=overTime;
							//System.out.println(overTime);
							//System.out.println(visitOrder);
							try{
						    // Create file 
						    FileWriter fstream = new FileWriter("data/"+System.currentTimeMillis() + "_out.txt");
						        BufferedWriter out = new BufferedWriter(fstream);
						    out.write("\n"+overTime+"\n"+visitOrder);
						    //Close the output stream
						    out.close();
						    }catch (Exception e){//Catch exception if any
						      System.err.println("Error: " + e.getMessage());
						    }
						}
					}
					for(int i=0; i<101;i++){
						if(adjMat[current][i]){
							double timeTaken = timeTaken(order[current].getN(), order[i].getN(), order[current].getW(), order[i].getW());
							edgeQ.insert(new Connection(timeTaken, current, i), order);
						}
					}
					selected = edgeQ.remove();
					double rando=Math.random();
					if(!edgeQ.isEmpty()&&rando<0.2)
						selected = edgeQ.remove();
					if(!edgeQ.isEmpty()&&rando<0.1)
						selected = edgeQ.remove();
					
					totalTime+=selected.getTime();
					int next = selected.getIndexJ();
					for(int i=0;i<101;i++){
						adjMat[current][i]=false;
						adjMat[i][current]=false;
					}	
					current = next;	
						
				}while(visits<101);
			}
			catch(Exception e)
			{}
			if(z%(N/100000d)==0){
				System.out.print("Processing: " + (100*z)/N +"% " + animationChars[s % 4] + "\r");
				s++;
			}	
		}
	}
	
	//converts degree to radians
	public static double degToRad(double angle){
		return (angle*Math.PI)/180;
	}
	//calculates the haversine of an angle in radians
	public static double havesine(double theta){
		return (1-Math.cos(theta))/2;		
	}
	//calculates the time taken in minutes by the drone to travel between two points on the Earth given Latitude/Longitude.
	public static double timeTaken(double phi1, double phi2, double lambda1, double lambda2){
		double p1=degToRad(phi1);
		double p2=degToRad(phi2);
		double l1=degToRad(lambda1);
		double l2=degToRad(lambda2);
		return (2*radiusOfEarth*Math.asin(Math.sqrt(havesine(p2-p1)+(Math.cos(p1)*Math.cos(p2)*havesine(l2-l1))))/80)*60;
	}
	//fills an array of doubles with maxDouble
	public static void setToMax(double[] array){
		for(int i=0; i<array.length; i++){
			array[i]=Double.MAX_VALUE;
		}
	}
	//prints adjacency matrix
	public static void printMat(boolean[][] matrix){
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				System.out.print(matrix[i][j]? 1 : 0);
			}
			System.out.println();
		}

	}
}

