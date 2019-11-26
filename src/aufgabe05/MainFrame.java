package aufgabe05;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import static aufgabe05.Agent.xBallMax;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	public static final int imageWidth = 360;
	public static final int imageHeight = 360;
	public InputOutput inputOutput = new InputOutput(this);
	
	ImagePanel canvas = new ImagePanel();
	ImageObserver imo = null;
	Image renderTarget = null;
	public int mousex, mousey, mousek;
	public int key;

	private int lastReward = 0;
	public boolean stop = false;
	public int won = 0;
	public int lost =  0;
	public int played = 0;

	public MainFrame(String[] args) {
		super("PingPong");

		getContentPane().setSize(imageWidth, imageHeight);
		setSize(imageWidth + 50, imageHeight + 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		canvas.img = createImage(imageWidth, imageHeight);

		add(canvas);

		run();
	}

	public void run() {

		int xBall = 5, yBall = 6, xSchlaeger = 5, xV = 1, yV = 1;
		Agent agent = new Agent();

		while (!stop) {
			System.out.println("played "+ played +" times"+ " won: "+ won + " lost: "+ lost);
			inputOutput.fillRect(0, 0, imageWidth, imageHeight, Color.black);
			inputOutput.fillRect(xBall * 30, yBall * 30, 30, 30, Color.green);
			inputOutput.fillRect(xSchlaeger * 30, 11 * 30 + 20, 90, 10, Color.orange);

			xSchlaeger = lerne(xBall, yBall, xSchlaeger, xV, yV, lastReward, agent);
															
			// move the ball
			xBall += xV;
			yBall += yV;
			if (xBall > 9 || xBall < 1) {
				xV = -xV;
			}
			if (yBall > 10 || yBall < 1) {
				yV = -yV;
			}

			if (yBall == 11) {
				if (xSchlaeger == xBall || xSchlaeger == xBall - 1 || xSchlaeger == xBall - 2) {
					lastReward = 1;
					xSchlaeger = lerne(xBall, yBall, xSchlaeger, xV, yV, lastReward, agent);
					System.out.println("getroffen");
					won++;

				} else {	
					lastReward = -1;
					xSchlaeger = lerne(xBall, yBall, xSchlaeger, xV, yV, lastReward, agent);
					System.out.println("verpasst");
					lost++;
				}

			} else {
				lastReward = 0;
				xSchlaeger = lerne(xBall, yBall, xSchlaeger, xV, yV, lastReward, agent);
			}

			// increase timing to display the ball slowly: slow the game
			if( played > 300000) { // display the game slowly when it's trained enough
				try {
					Thread.sleep(60);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
			
			repaint();
			validate();
			played++;
		}

		setVisible(false);
		dispose();
	}
	
	public int lerne(int xBall, int yBall, int xSchlaeger, int xV, int yV, int lastReward, Agent agent) {
		int action = agent.calcNextAction(xBall, yBall, xSchlaeger, xV, yV, lastReward);
		if (action == Agent.LEFT) {
			xSchlaeger--;
		} else if (action == Agent.RIGHT) {
			xSchlaeger++;
		} else if (action == Agent.STAY) {
			//System.err.println("stay man!!!!");
		}
		if (xSchlaeger < 0) {
			xSchlaeger = 0;
		}
		if (xSchlaeger > xBallMax) {
			xSchlaeger = xBallMax;
		}
		return xSchlaeger;
	}

	public void mouseReleased(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
		mousek = e.getButton();
	}

	public void mousePressed(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
		mousek = e.getButton();
	}

	public void mouseExited(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
		mousek = e.getButton();
	}

	public void mouseEntered(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
		mousek = e.getButton();
	}

	public void mouseClicked(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
		mousek = e.getButton();

		// fastMode = !fastMode;
	}

	public void mouseMoved(MouseEvent e) {
		// System.out.println(e.toString());
		mousex = e.getX();
		mousey = e.getY();
		mousek = e.getButton();
	}

	public void mouseDragged(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
		mousek = e.getButton();
	}

	public void keyTyped(KeyEvent e) {
		key = e.getKeyCode();
	}

	public void keyReleased(KeyEvent e) {
		key = e.getKeyCode();
	}

	public void keyPressed(KeyEvent e) {
		System.out.println(e.toString());
	}

	/**
	 * Construct main frame
	 *
	 * @param args
	 *            passed to MainFrame
	 */
	public static void main(String[] args) {
		new MainFrame(args);
	}
}
