package s06;

import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class DiscrSimul {
	//============================================================
	static class SimulEvent implements Comparable<SimulEvent> {
		int      who_;       // client identification number
		int      time_;      // when the event will occur
		boolean  isArrival_; // "arrival" or "departure" event
		// --------------------
		SimulEvent(int who, int time, boolean isArrival) {
			who_       = who;
			time_      = time;
			isArrival_ = isArrival;
		}
		@Override
		public int compareTo(SimulEvent o) {
			if (time_ < o.time_) return -1;
			if (time_ > o.time_) return +1;
			return 0;
		}
	}
	//============================================================
	//-------------------------------------- Simulation basics
	private Random   rnd_     = new Random();
	private PriorityQueue<SimulEvent> events_  = new PriorityQueue<>();
	private int      crtTime_ = 0;
	//-------------------------------------- The "world" at 1 instant
	private Queue<Integer> clientQueue_ = new LinkedList<Integer>();
	private int lastClient_  = 0; 
	private int freeTellers_;
	//-------------------------------------- The world parameters
	private int avgTransactionDuration_;
	private int avgArrivalInterval_;
	//-------------------------------------- Statistics stuff
	private int peakTime_           = 0;
	private int peakTimerStart_ = 0;
	private int freeTellersTime_    = 0;
	private int nbOfClientsServed_  = 0;
	//------------------------------------------------------------
	public DiscrSimul(int nbOfTellers, int avgTrans, int avgArrivalInterval) {
		this.freeTellers_ = nbOfTellers;
		this.avgTransactionDuration_ = avgTrans;
		this.avgArrivalInterval_ = avgArrivalInterval;

		this.events_.add(new SimulEvent(lastClient_, 0, true));

	}
	//------------------------------------------------------------
	public void runSimulation(int stoppingTime) {
		while(crtTime_ < stoppingTime){
			handleEvent(this.events_.poll());
		}
	}
	//------------------------------------------------------------
	public void printStatistics() {
		System.out.println("Total duration of free tellers = " + freeTellersTime_);
		System.out.println("               Total peak time = " + peakTime_);
		System.out.println("          Nb of served clients = " + nbOfClientsServed_); 
		int benefit=(nbOfClientsServed_-freeTellersTime_/avgTransactionDuration_);
		System.out.println("    Benefit (silly) estimation = "+ benefit);
	}
	//------------------------------------------------------------
	// Private methods
	//------------------------------------------------------------
	private void handleEvent (SimulEvent e) {
		peakTime(this.crtTime_,e.time_);
		freeTellersTime(this.crtTime_, e.time_);
		this.crtTime_ = e.time_;
		if(e.isArrival_){
			handleArrival(e);
		}else{
			handleDeparture(e);
		}	
	}
	//------------------------------------------------------------
	private void handleArrival(SimulEvent e) {
		if(this.freeTellers_ == 0)
			this.clientQueue_.add(e.who_);
		else
			handleStartTransaction(e.who_);
		int d = rndArrivalInterval();
		this.lastClient_++;
		this.events_.add(new SimulEvent(this.lastClient_, this.crtTime_+d, true));
	}

	//------------------------------------------------------------
	private void handleDeparture(SimulEvent e) {
		this.freeTellers_++;
		this.nbOfClientsServed_++;
		if(this.clientQueue_.isEmpty())
			return;
		handleStartTransaction(clientQueue_.poll());
	}

	//------------------------------------------------------------
	private void handleStartTransaction(int clientId) {
		this.freeTellers_--;
		int d = rndTransactionDuration();
		this.events_.add(new SimulEvent(clientId, this.crtTime_+d, false));
	}
	private void peakTime(int currentTime, int newTime){
		if(this.freeTellers_ == 0 && this.peakTimerStart_ == 0){
			this.peakTimerStart_ = this.crtTime_;
		}else if(this.freeTellers_ > 0 && this.peakTimerStart_ > 0){
			this.peakTime_ += (this.crtTime_-this.peakTimerStart_);
			this.peakTimerStart_ = 0;
		}
	}
	private void freeTellersTime(int crtTime, int time){
		int temp = 0;
		for(int i = 0; i < freeTellers_; i++){
			temp += (time - crtTime);
		}
		this.freeTellersTime_ += temp;
	}
	//------------------------------------------------------------
	private int rndTransactionDuration() {
		return (int)(Math.round(nextNegExp(rnd_, avgTransactionDuration_)));
	}
	//------------------------------------------------------------
	private int rndArrivalInterval() {
		return (int)(Math.round(nextNegExp(rnd_, avgArrivalInterval_)));
	}
	//------------------------------------------------------------
	// exponential law
	private static double nextNegExp(Random r, double expectedValue) {
		return expectedValue * (-Math.log(1.0 - r.nextDouble()));  
	}
	//------------------------------------------------------------
	//------------------------------------------------------------
	//------------------------------------------------------------
	public static void main(String [] args) {
		int a=5, b=70, c=20, d=100000; // tellers,transTime,arrivalDelay,stopTime
		if (args.length == 4) {
			a = Integer.parseInt(args[0]);
			b = Integer.parseInt(args[1]);
			c = Integer.parseInt(args[2]);
			d = Integer.parseInt(args[3]);
		}
		System.out.println("===== Simulations parameters: "
				+"tellers("+a+") transTime("+b+") arrivalDelay("+c+") stopTime("+d+")");
		DiscrSimul o = new DiscrSimul(a, b, c);
		o.runSimulation(d);
		o.printStatistics();

		// Varying the number of tellers...
		for(a=1; a<10; a++) {
			System.out.println("===== Simulations parameters: "
					+"tellers("+a+") transTime("+b+") arrivalDelay("+c+") stopTime("+d+")");
			o = new DiscrSimul(a, b, c);
			o.runSimulation(d);
			o.printStatistics();
		}
	}
}
