package helmet;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Player player = new Player(this);	// player instance
	
	
	private Clip backgroundMusic;						// audio background music		
	private Clip endMusic = null;						// audio game over
	private String user = " ";
	private boolean stop = false;
	
	//import background and doors images
	private Image openedDoor = new ImageIcon(this.getClass().getResource("Img/porta1.png")).getImage();
	private Image closedDoor = new ImageIcon(this.getClass().getResource("Img/porta2.png")).getImage();
	private Image reversedOpenDoor = new ImageIcon(this.getClass().getResource("Img/porta1r.png")).getImage();
	private Image background = new ImageIcon(this.getClass().getResource("Img/cloud.jpg")).getImage();
	
	private Image[] images;								// collect door images in an array
	private int door;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	private Timer doorTimer = new Timer(5000, this);			// timers
	public Timer screwdriverTimer = new Timer(5000, this);
	public Timer wrenchTimer = new Timer(5000, this);
	public Timer shieldTimer = new Timer(7000, this);
	
	public Hammer hammer = new Hammer (this, player);			// tools-threads
	public Screwdriver screwdriver = new Screwdriver (this, player);
	public Wrench wrench = new Wrench (this, player);
	public Shield shield = new Shield (this, player);
	public Heart heart = new Heart (this, player);
	
	public static int lives = 10;								// general game static variables
	public static int points = 0;
	public static int level = 1;
	
	public Game (){
		
		addKeyListener(new KeyListener() {						// keyboard listener 	
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {				// when the user stops pressing the key	
				player.keyReleased(e);							// keyboard listens	
			}

			@Override
			public void keyPressed(KeyEvent e) {				// when the user presses the key
				player.keyPressed(e);							// same
			}
		});
		setFocusable(true);
		
		// with the opened and closed door in an array, we just have to change the index to switch
		images = new Image[2];
		images[0] = reversedOpenDoor;
		images[1] = closedDoor;
        
        doorTimer.start();		// timer thread that starts to open and close the door
        
        try {					// open background music
        	AudioInputStream fons =AudioSystem.getAudioInputStream(Game.class.getResource("Audio/back.wav"));
        	this.backgroundMusic = AudioSystem.getClip();
			backgroundMusic.open(fons);
		} catch (LineUnavailableException | IOException e1) {e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {e1.printStackTrace();}
        
        // make background music volume lower, so we can hear the effects
        FloatControl volume = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
    	volume.setValue(-12);
    	backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
	    
  
	}
	
	 @Override
     public void actionPerformed(ActionEvent e) {
		 if(e.getSource()==doorTimer){				// every 5s the timer calls the method
			 if(door == 0){	
				 door = 1;							// change door each time
			 }else{
				 door = 0;
				 
			 }
		      repaint();
		  }else if(e.getSource()==screwdriverTimer){ 	// when the time is over
			 screwdriverTimer.stop(); 				 	// timer stops
			 Tool.kewChange=false;			 			// keys get back to their original state
		  }else if(e.getSource()==wrenchTimer){
				 wrenchTimer.stop(); 
				 Wrench.still=false;
		 }else if(e.getSource()==shieldTimer){
			 shieldTimer.stop(); 
			 Shield.immune=false;
	 }
		 
     }
	 
	public void paint(Graphics g) {
		super.paint(g);	
		Graphics2D g2d = (Graphics2D) g;	
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Image img = images[door];	// door variable changes door image in the repaint repaint
		
		g2d.drawImage(background, 0, 0, 550, 300, null);
		int fontSize = 20;
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));		// draw text in the frame
		g2d.drawString(String.valueOf("Lives:" + lives), 10, 20);
		g2d.drawString(String.valueOf("Points:" + points), 10, 50);
		g2d.drawString(String.valueOf("Level:" + level), 10, 80);
		g2d.drawString(String.valueOf("User: " +user), 420, 20);
		try {
			player.paint(g2d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		g2d.drawImage(openedDoor, 0, 120, 80, 120, null);
		g2d.drawImage(img, 450, 120, 80, 120, null);
		hammer.paint(g2d);
		screwdriver.paint(g2d);
		wrench.paint(g2d);
		shield.paint(g2d);
		heart.paint(g2d);
		
	}
	
	public void move(){
		player.move();
		
	}
	
	public Rectangle getBounds() {					// exit door bounds
		return new Rectangle(450, 120, 80, 120);
	}

	public static void main(String[] args) throws InterruptedException {
		Game game = new Game(); 				// game instance to start
		
		game.init(game);
	}
	
	private void init(Game game){				// main method, frame creation
		
		JFrame frame = new JFrame("Helmet");
		frame.add(game);
		frame.setSize(550, 300);	
		frame.setVisible(true);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		user = JOptionPane.showInputDialog(this, "Write user name: ", "User");
		
		hammer.start();				// initialize tools, each one is a thread
		screwdriver.start();
		wrench.start();
		shield.start();
		heart.start();
		
		while(!stop){
			game.move();
			game.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void gameOver() {		// actions performed when the game ends
		Tool.immune = true;
		stop=true;
		backgroundMusic.close();	// switch off background music
		this.play();				// effect "Game Oooover"
		JSON jsonSave = new JSON();						// JSON instance
		
		// new score is registered, then compared with the last one and returned
		Score registre = new Score(points, user);
		ArrayList<Score> points = jsonSave.compareScore(registre);

		// message 
		JOptionPane.showMessageDialog(this, "Game Over - Best scores\n 1. "
				+ points.get(0).getUser()+ " : " + points.get(0).getPunts() +"p\n 2. "
				+ points.get(1).getUser()+ " : " + points.get(1).getPunts() +"p\n 3. "
				+ points.get(2).getUser()+ " : " + points.get(2).getPunts()+"p");
		
		System.exit(ABORT);
	}
	
	public int getDoor(){
		return door;	
	}
	
	public void play() {	// method for the game over music
		 try
	        {
	            if (endMusic == null)
	            {
	                endMusic = AudioSystem.getClip();
	            }
	            else
	            {
	                endMusic.close();
	            }
	            AudioInputStream gameover =AudioSystem.getAudioInputStream(Game.class.getResource("Audio/gameover.wav"));
	            endMusic.open(gameover);
	 
	            endMusic.start();
	        }
	        catch (Exception e)
	        {
	            System.err.println(e.getMessage());
	        }
		
	}
	
	

}
