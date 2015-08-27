
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Random;


public class DrivingRangeApp {


	public static void main(String[] args) throws InterruptedException {

		Random random = new Random();

		int noGolfers = Integer.parseInt(args[0]);
		int sizeStash= Integer.parseInt(args[1]);
		int sizeBucket= Integer.parseInt(args[2]);

		BallStash.setSizeStash(sizeStash);
		BallStash.setSizeBucket(sizeBucket);
		Golfer.setBallsPerBucket(sizeBucket);

		//initialize shared variables
		AtomicBoolean doneFlag  =new AtomicBoolean(false);
		AtomicBoolean cartFlag  = new AtomicBoolean(false);

		BallStash stash = new BallStash(doneFlag);
		Range field = new Range(cartFlag, doneFlag);

		System.out.println("=======   River Club Driving Range Open  ========");
		System.out.println("======= Golfers:"+noGolfers+" balls: "+sizeStash+ " bucketSize:"+sizeBucket+"  ======");

		//create threads and set them running
		Golfer[] golfers = new Golfer[noGolfers];
		for (int i=0; i<noGolfers; i++){
			Thread.sleep(random.nextInt(4000));
			System.out.println(">>> New Golfer! <<<");
			golfers[i] = new Golfer(stash, field, cartFlag, doneFlag);
			golfers[i].start();
		}
		Bollie bollie = new Bollie(stash, field, doneFlag);
		bollie.start();

		//for testing, just run for a bit
		Thread.sleep(30000);// this is an arbitrary value - you may want to make it random
		System.out.println("=======  River Club Driving Range Closing ========");
		doneFlag.set(true);
		stash.wake();
		field.wake();
		System.out.println("=======  River Club Driving Closed ========");


	}

}
