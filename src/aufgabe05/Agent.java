package aufgabe05;

public class Agent {
	private final static double LEARNING_RATE = 0.5;
	private final static double DISCOUNT_FAKTOR = 0.9; // dafür sorgt, dass weit in der Zukunft liegende mögliche Rewards geringer gewichtet werden.
	private final static double EPSILON = 0.1;

	private final static int NUM_OF_ACTIONS = 3;
	public final static int STAY = 0;
	public final static int LEFT = 1;
	public final static int RIGHT = 2;

	public final static int xBallMax = 10;
	public final static int yBallMax = xBallMax;
	public final static int xSchlaegerMax = yBallMax;
	public final static int xSpeedMax = 2;
	public final static int ySpeedMax = 2;


	private double[][] Q;

	private int currentindex = 0;
	private int currentAction = 0;

	private int lastindex = 0;
	private int lastAction = 0;

	public Agent() {
		Q = new double[xBallMax * yBallMax * xSchlaegerMax * xSpeedMax * ySpeedMax][NUM_OF_ACTIONS];
		//System.out.print(xMax * yMax * xVMax * yVMax * schlaegerMax);
		
		initActionArray();
	}

	private void initActionArray() {
		for (int i = 0; i < Q.length; i++) {
			Q[i][STAY] = Math.random();
			Q[i][LEFT] = Math.random();
			Q[i][RIGHT] = Math.random();
		}
	}

	public int calcNextAction(int xBall, int yBall, int xSchlaeger, int xVel, int yVel, int lastReward) {
		xVel += 1; // no negative values
		yVel += 1; // no negative values

		currentindex = (xBall + yBall * yBallMax + xSchlaeger * (yBallMax * xSchlaegerMax)
				+ xVel * (yBallMax * xSchlaegerMax * xSpeedMax) + yVel * (yBallMax * xSchlaegerMax * xSpeedMax * ySpeedMax));
		
		//currentindex = ((((yBall * xMax + xBall) * schlaegerMax + xSchlaeger) * xVMax + xVel) * yVMax + yVel);

		// make it explore other possibilities
		if (Math.random() < EPSILON) {
            currentAction = STAY;
        } else {
            currentAction = getMaxAction(currentindex);
        }

		updateQ_table(lastReward);
		lastindex = currentindex;
		lastAction = currentAction; // a sort of back propagation, because i update the previous action

		return currentAction;
	}

	private int getMaxAction(int index) {
		double max = Math.max(Math.max(Q[index][STAY], Q[index][LEFT]), Q[index][RIGHT]);

		if (max == Q[index][STAY]) {
			return STAY;
		} else if (max == Q[index][LEFT]) {
			return LEFT;
		} else if (max == Q[index][RIGHT]) {
			return RIGHT;
		} else {
			System.err.println("Wrong MAX = " + max);
			return -1;
		}
	}

	public void updateQ_table(int rewardFromLastResult) {
		Q[lastindex][lastAction] += LEARNING_RATE * (rewardFromLastResult + DISCOUNT_FAKTOR * (Q[currentindex][currentAction]) - Q[lastindex][lastAction]);
	}
}
