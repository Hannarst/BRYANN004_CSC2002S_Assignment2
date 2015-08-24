import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.*;
import java.util.ArrayList;


public class Range {
	private static int sizeStash=20;
	private volatile AtomicBoolean cartOnField;
	//ADD variable: ballsOnField collection;
	private ArrayList<golfBall> ballsOnField = new ArrayList();

	//Add constructors
	public Range(AtomicBoolean cartFlag){
		cartOnField = cartFlag;
	}

	//ADD method: collectAllBallsFromField(golfBall [] ballsCollected)
	public synchronized void collectAllBallsFromField(ArrayList<golfBall> ballsCollected){
		while(ballsOnField.size()<sizeStash){
			try {
				wait();
			}
			catch (InterruptedException e) {}
		}
		cartOnField.set(true);
		for(int i=0; i<ballsOnField.size(); i++){
			ballsCollected.add(ballsOnField.remove(0));
		}
		cartOnField.set(false);
		notifyAll();


	}

	//ADD method: hitBallOntoField(golfBall ball)
	public synchronized void hitBallOntoField(golfBall ball){
		ballsOnField.add(ball);
		if (ballsOnField.size()==sizeStash){
			notifyAll();
		}
	}
}
