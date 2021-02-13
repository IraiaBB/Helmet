package helmet;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.Clip;


abstract public class Tool extends Thread{	// super classe of all the tools falling
											// protected variables, so child classes can use them
	protected static final int WIDTH = 40;	// size parameters are constants, as are the same for all tools	
	protected static final int HEIGHT = 40;
	
	protected int x;						// x is fixed, as the movement is vertical
	protected int y=-50;					// their starting point is the top of the frame	
	public static int ya = 1;				// constant speed: 1pxl/repaint
	
	protected Game game;
	protected Clip toolEffect = null;
	
	public static boolean kewChange=false;	// variable to activate the crash result depending the tool
	public static boolean still=false;
	public static boolean immune=false;
	
	protected Player player;
	protected Image toolImg;
	protected boolean collision=false;		// controls that the collision it can haven just once per thread


	
	public Tool(Game game, Player player){
		this.game = game;
		this.player = player;
		// x is a random number that gives the horizontal and static position along the frame
		x =(int) Math.floor(Math.random()*300+70);
		
	
	} 
	
	//variable to stop the threads
	public AtomicBoolean running = new AtomicBoolean(false);
	
	public void interrupt() {
        running.set(false);
        Tool.interrupted();
    }
	
	public void run(){
		
			if(y>game.getHeight()){		// when the y path is higher than the height frame
			
				y=0;					// the tool falls from the sky again
				x =(int) Math.floor(Math.random()*300+70);	// in a different and random x
				
			}else{
				y= y+ya;				//continues falling
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		
	} 
	
	
	public Rectangle getBounds() {					// tool's bounds
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	protected boolean toolCollision() {				// detects collision with the tool
		return game.player.getBounds().intersects(getBounds());
	}

	
	abstract protected void paint(Graphics2D g);	// abstract methods
	abstract protected void play();
	
}
