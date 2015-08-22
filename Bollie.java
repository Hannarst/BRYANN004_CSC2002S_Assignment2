import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;


public class Bollie extends Thread{

	private volatile AtomicBoolean done;  // flag to indicate when threads should stop

	private volatile BallStash sharedStash; //link to shared stash
	private volatile Range sharedField; //link to shared field
	private Random waitTime;

	//link to shared field
	Bollie(BallStash stash,Range field,AtomicBoolean doneFlag) {
		sharedStash = stash; //shared
		sharedField = field; //shared
		waitTime = new Random();
		done = doneFlag;
	}

	public void run() {

		//while True
		ArrayList<golfBall> ballsCollected = new ArrayList();
		while (done.get()!=true) {
			try {
				sleep(waitTime.nextInt(1000));

				sleep(1000);
				sharedField.collectAllBallsFromField(ballsCollected);
				System.out.println("*********** Bollie collecting balls   ************");
				// collect balls, no golfers allowed to swing while this is happening
				System.out.println("*********** Bollie adding balls to stash ************");
				//sharedStash.addBallsToStash(ballsCollected,noCollected);
				sharedStash.addBallsToStash(ballsCollected);
				// while(sharedStash.getBallsInStash()<sharedStash.getSizeBucket()){
				// 	try {
				// 			wait();
				// 	}
				// 	catch (InterruptedException e) {}
				// }

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    		}
		}
}
