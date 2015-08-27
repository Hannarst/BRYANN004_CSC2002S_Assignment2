
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;


public class Golfer extends Thread {

	//remeber to ensure thread saftey

	private volatile AtomicBoolean doneFlag;
	private volatile AtomicBoolean cartFlag;

	private int myBuckets = 0;
	private static final int MAX_BUCKETS = 5;

	private static volatile AtomicInteger noGolfers = new AtomicInteger(1); //shared amoungst threads
	private static int ballsPerBucket=4; //shared amoungst threads

	private int myID;

	private GolfBall[] golferBucket;
	private BallStash sharedStash; //link to shared stash
	private Range sharedField; //link to shared field
	private Random swingTime;



	Golfer(BallStash stash,Range field, AtomicBoolean cartFlag, AtomicBoolean doneFlag) {
		sharedStash = stash; //shared
		sharedField = field; //shared
		this.cartFlag = cartFlag; //shared
		this.doneFlag = doneFlag;
		golferBucket = new GolfBall[ballsPerBucket];
		swingTime = new Random();
		myID=newGolfID();
	}

	public
	static int newGolfID() {
		return noGolfers.getAndIncrement();
	}

	public static void setBallsPerBucket (int noBalls) {
		ballsPerBucket=noBalls;
	}
	public static int getBallsPerBucket () {
		return ballsPerBucket;
	}
	public void run() {

		while (doneFlag.get()!=true) {



				System.out.println(">>> Golfer #"+ myID + " trying to fill bucket with "+getBallsPerBucket()+" balls.");

				if(myBuckets>=MAX_BUCKETS){
					System.out.println(">>> Golfer #"+ myID + " was too greedy and exceeded the maximum number of buckets allowed. Tut tut tut..");
					break;
				}
				int left = sharedStash.getBucketBalls(golferBucket);
				if(doneFlag.get()){
					System.out.println(">>> Golfer #"+ myID + " denied bucket! Bye though..");
					break;
				}
				myBuckets++;

				System.out.println("<<< Golfer #"+ myID + " filled bucket with "+getBallsPerBucket()+" balls (remaining stash=" + left+")");


				for (int b=0;b<ballsPerBucket;b++){ //for every ball in bucket

				    try {
							sleep(swingTime.nextInt(2000));
							sharedField.hitBallOntoField(golferBucket[b]);
							System.out.println("Golfer #"+ myID + " hit ball #"+golferBucket[b].getID()+" onto field");

							while(cartFlag.get()){
								sleep(3000);
							}

						}
						catch (InterruptedException e) {
							e.printStackTrace();
						} //      swing


				    //!!wair for cart if necessary if cart there

				}
				//bucket now considered empty


		}



	}
}
