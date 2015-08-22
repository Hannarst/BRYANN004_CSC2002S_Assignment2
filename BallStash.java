import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.ArrayList;

public class BallStash {
	//static variables
	private static int sizeStash=20;
	private static int sizeBucket=4;
	//ADD variables: a collection of golf balls, called stash
	private volatile ArrayList<golfBall> ballsInStash = new ArrayList();


	public BallStash(){
		for (int i=0; i<sizeStash; i++){
			ballsInStash.add(new golfBall());
		}

	}

	//ADD methods:
	//getBucketBalls
	// addBallsToStash
	// getBallsInStash - return number of balls in the stash

	public synchronized golfBall[] getBucketBalls(){
		while(getBallsInStash()<sizeBucket){
			notifyAll();
			try {
	 			wait();
			}
			catch (InterruptedException e) {}
		}
		golfBall[] bucket = new golfBall[sizeBucket];
		for(int i=0; i<sizeBucket; i++){
			bucket[i] = ballsInStash.remove(0);
		}
		notifyAll();
		return bucket;
	}

	public synchronized void addBallsToStash(ArrayList<golfBall> balls){
		while(getBallsInStash()==sizeStash){
			try {
				wait();
			}
			catch (InterruptedException e) {}
		}
		for(golfBall ball: balls){
			ballsInStash.add(ball);
		}
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
