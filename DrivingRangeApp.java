import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrivingRangeApp {


	public static void main(String[] args) throws InterruptedException {
		AtomicBoolean done  =new AtomicBoolean(false);

		int noGolfers = Integer.parseInt(args[0]);
		int sizeStash= Integer.parseInt(args[1]);
		int sizeBucket= Integer.parseInt(args[2]);

		BallStash.setSizeStash(sizeStash);
		BallStash.setSizeBucket(sizeBucket);
		Golfer.setBallsPerBucket(sizeBucket);

		//initialize shared variables
		BallStash stash = new BallStash();
		AtomicBoolean cartFlag  = new AtomicBoolean(false);
		Range field = new Range(cartFlag);



		System.out.println("=======   River Club Driving Range Open  ========");
		System.out.println("======= Golfers:"+noGolfers+" balls: "+sizeStash+ " bucketSize:"+sizeBucket+"  ======");

		Golfer[] golfers = new Golfer[noGolfers];
		//create threads and set them running



		for (int i=0; i<noGolfers; i++){
			golfers[i] = new Golfer(stash, field, cartFlag, done);
			golfers[i].start();
		}
		Bollie bollie = new Bollie(stash, field, done);
		bollie.start();


		//for testing, just run for a bit
		Thread.sleep(30000);// this is an arbitrary value - you may want to make it random
		done.set(true);
		System.out.println("=======  River Club Driving Range Closing ========");


	}

}
