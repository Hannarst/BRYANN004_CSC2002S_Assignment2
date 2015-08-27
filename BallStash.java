
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;

public class BallStash {
	//static variables
	private static int sizeStash=20;
	private static int sizeBucket=4;

	private volatile AtomicBoolean doneFlag;
	private ArrayList<GolfBall> ballsInStash = new ArrayList(sizeStash);


	public BallStash(AtomicBoolean doneFlag){
		this.doneFlag = doneFlag;
		for (int i=0; i<sizeStash; i++){
			ballsInStash.add(new GolfBall());
		}
	}

	public synchronized int getBucketBalls(GolfBall[] bucket){
		while(getBallsInStash()<sizeBucket){
			if (doneFlag.get()){
				return -1;
			}
			try {
	 			wait();
			}
			catch (InterruptedException e) {}
		}

		for(int i=0; i<sizeBucket; i++){
			bucket[i] = ballsInStash.remove(0);
		}

		if(getBallsInStash()<sizeBucket){
			notifyAll();
		}

		return getBallsInStash();
	}

	public synchronized void addBallsToStash(ArrayList<GolfBall> balls){
		for(GolfBall ball: balls){
			ballsInStash.add(ball);
		}
		notifyAll();
	}

	public synchronized void wake(){
		notifyAll();
	}

	public int getBallsInStash(){
		return ballsInStash.size();
	}

	//getters and setters for static variables - you need to edit these
	public static void setSizeBucket (int noBalls) {
		sizeBucket=noBalls;
	}
	public static int getSizeBucket () {
		return sizeBucket;
	}
	public static void setSizeStash (int noBalls) {
		sizeStash=noBalls;
	}
	public static int getSizeStash () {
		return sizeStash;
	}


}
