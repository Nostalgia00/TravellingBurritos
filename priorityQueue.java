public class priorityQueue{
	private int maxSize;
	private int nItems;
	private Connection[] queArray;

	public priorityQueue(int s){
		maxSize=s;
		queArray=new Connection[maxSize];
		nItems=0;
	}
	//will order based on time to travel the connection
	public void insert(Connection item, Order[] order) {    	// insert item
		if(nItems==0){                         		// if no items,
			queArray[0] = item;         			// insert at 0
		}
		else{                        				// if some items,
			int j = nItems;							// start at end
			while(j > 0 && (queArray[j-1].getTime()) < item.getTime()){   // while new item larger
			//while(j > 0 && (queArray[j-1].getTime()-(order[queArray[j-1].getIndexJ()].getTime())) < item.getTime()-((order[item.getIndexJ()].getTime()))){   // while new item larger
				queArray[j] = queArray[j-1];    	// shift upward
				j--;								// decrement j
			}
			queArray[j] = item;                		// insert it
		}
		nItems++;  								// increase items
	}
	public Connection remove(){
		Connection temp = queArray[nItems-1];
		nItems--;
		return temp;
	}
	public boolean isEmpty(){
		return nItems<=0;
	}
}