
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.*;
import java.util.ArrayList;


public class Range {
	private static int sizeStash=20;
	private volatile AtomicBoolean cartOnField;
	private volatile AtomicBoolean done;
	//ADD variable: ballsOnField collection;
	private ArrayList<GolfBall> ballsOnField = new ArrayList(sizeStash);

	//Add constructors
	public Range(AtomicBoolean cartFlag, AtomicBoolean doneF){
		done = doneF;
		cartOnField = cartFlag;
	}

	//ADD method: collectAllBallsFromField(GolfBall [] ballsCollected)
	public synchronized void collectAllBallsFromField(ArrayList<GolfBall> ballsCollected){
		while(ballsOnField.size()==0){
			if (done.get()){return;}
			try {
				wait();
			}
			catch (InterruptedException e) {}
		}

		cartOnField.set(true);

		for(GolfBall elem : ballsOnField){
			ballsCollected.add(elem);
		}
		ballsOnField.clear();
		cartOnField.set(false);
	}

	public synchronized void wake(){
		notify();
	}

	//ADD method: hitBallOntoField(GolfBall ball)
	public synchronized void hitBallOntoField(GolfBall ball){
		ballsOnField.add(ball);
	}

	public synchronized int getNumBalls(){
		return ballsOnField.size();
	}
}
