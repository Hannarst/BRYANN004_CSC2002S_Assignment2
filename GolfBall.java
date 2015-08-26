

public class GolfBall {

	//add mechanisms for thread safety

	private volatile static int noBalls;
	private int myID;

	GolfBall() {
		myID=noBalls;
		incID();
	}

	public int getID() {
		return myID;
	}

	private static void  incID() {
		noBalls++;
	}

}
