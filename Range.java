
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.*;
import java.util.ArrayList;


public class Range {
	private static int sizeStash=20;
	private volatile AtomicBoolean cartFlag;
	private volatile AtomicBoolean doneFlag;
	//ADD variable: ballsOnField collection;
	private ArrayList<GolfBall> ballsOnField = new ArrayList(sizeStash);

	//Add constructors
	public Range(AtomicBoolean cartFlag, AtomicBoolean doneFlag){
		this.doneFlag = doneFlag;
		this.cartFlag = cartFlag;
	}

	//ADD method: collectAllBallsFromField(GolfBall [] ballsCollected)
	public synchronized void collectAllBallsFromField(ArrayList<GolfBall> ballsCollected){
		while(ballsOnField.size()==0){
			if (doneFlag.get()){return;}
			try {
				wait();
			}
			catch (InterruptedException e) {}
		}

		cartFlag.set(true);

		for(GolfBall elem : ballsOnField){
			ballsCollected.add(elem);
		}
		ballsOnField.clear();
		cartFlag.set(false);
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
