
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
		ArrayList<GolfBall> ballsCollected = new ArrayList();
		while (done.get()!=true) {
			try {
				sleep(waitTime.nextInt(5000));

				System.out.println("*********** Bollie going out ************");

				sharedField.collectAllBallsFromField(ballsCollected);

				System.out.println("*********** Bollie collected "+ballsCollected.size() +" balls   ************");
				// collect balls, no golfers allowed to swing while this is happening
				sleep(1000);
				System.out.println("*********** Bollie adding "+ ballsCollected.size()+" balls to stash ************");
				//sharedStash.addBallsToStash(ballsCollected,noCollected);
				sharedStash.addBallsToStash(ballsCollected);
				ballsCollected.clear();


			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
