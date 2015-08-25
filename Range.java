
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.*;
import java.util.ArrayList;


public class Range {
	private static int sizeStash=20;
	private volatile AtomicBoolean cartOnField;
	private volatile AtomicBoolean done;
	//ADD variable: ballsOnField collection;
	private volatile BlockingQueue<golfBall> ballsOnField = new ArrayBlockingQueue(sizeStash);

	//Add constructors
	public Range(AtomicBoolean cartFlag, AtomicBoolean doneF){
		done = doneF;
		cartOnField = cartFlag;
	}

	//ADD method: collectAllBallsFromField(golfBall [] ballsCollected)
	public synchronized void collectAllBallsFromField(ArrayList<golfBall> ballsCollected){
		while(ballsOnField.size()==0){
			if (done.get()){return;}
			try {
				wait();
			}
			catch (InterruptedException e) {}
		}
		
		cartOnField.set(true);

		for(golfBall elem : ballsOnField){
			ballsCollected.add(elem);
		}
		ballsOnField.clear();
		cartOnField.set(false);
		


	}

	//ADD method: hitBallOntoField(golfBall ball)
	public synchronized void hitBallOntoField(golfBall ball){
		ballsOnField.add(ball);
		if (ballsOnField.size()==sizeStash){
			notifyAll();
		}
	}
	
	public synchronized int getNumBalls(){
		return ballsOnField.size();
	}
}
