package helmet;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class Player {
	private static final int Y = 170;		// vertical axis stays static
	private static final int WIDTH = 78;	// player's size
	private static final int HEIGHT = 100;	
	
	private int x=0;						// position
	private int xa = 0; 					// horizontal speed
	
	private Game game;
	private Clip winEffect = null;			// audio effect when the player crosses exit door
	
	// import image
	private Image playerImg = new ImageIcon(this.getClass().getResource("Img/player.png")).getImage();
	
	public Player(Game game){				// take the game instance
		this.game = game;
		
		
	}
	
	public void move(){
		if(game.getDoor()==1 && doorCollision()){	// if crashes with the closed door
			x= 450-WIDTH;							// returns to the door's margin
			xa = 0;
		}else if(x + xa > game.getWidth()){			// but, if exits through the opened door
			
			this.play();							// win audio effect
			Game.points = Game.points + 5;			// add 5 points to user
			x=0;									// player returns to the starting point (the left frame margin)
			xa=0;	
			
			if(Game.points%20==0){					// every 20 pts., level up
				Game.level++;	
				Tool.ya++;							// and speed up
			}
		
		}else if(x + xa < 0){						// if tries to go through left margin's frame (the starting point)
		
			x=0;									// don't let player go further, keep him on left margin
			xa=0;
			
		}else { 									// inside the frame, normal movement
			x = x + xa;	
			
		}
	}
	
	public void paint(Graphics2D g) throws IOException {
		g.drawImage(playerImg, x, Y, WIDTH, HEIGHT, game);   // draw character
	}
	
	public void keyReleased(KeyEvent e) {					// when the key is released
		xa = 0;												// player stops
	}

	public void keyPressed(KeyEvent e) {					// when the key is pressed
		if (e.getKeyCode() == KeyEvent.VK_LEFT){			// left arrow key
			if(Tool.kewChange){								// crash with screw driver, changes direction to right
				xa = 1;
			}else if(Tool.still){							// crash with wrench, stays still
				xa = 0;
			}else{											// normal movement
				xa = -1;
			}
		}
			
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)	
			if(Tool.kewChange){
				xa = -1;
			}else if(Tool.still){
				xa = 0;
			}else{
				xa = 1;
			}
	}
	
	public Rectangle getBounds() {					// player's bounds framed in a rectangle
		return new Rectangle(x, Y, WIDTH, HEIGHT);
	}
	
	private boolean doorCollision() {				// player-door collision
		return game.getBounds().intersects(getBounds());
	}
	
	public void setX(int x){
		this.x=x;
	}
	public void setXa(int xa){
		this.xa=xa;
	}
	private void play() {							// victory sound when crossing the door
		 try
	        {
	            if (winEffect == null)
	            {
	                winEffect = AudioSystem.getClip();
	            }
	            else
	            {
	                winEffect.close();
	            }
	            AudioInputStream hurt =AudioSystem.getAudioInputStream(Wrench.class.getResource("Audio/victory.wav"));
	            winEffect.open(hurt);
	 
	            winEffect.start();
	        }
	        catch (Exception e)
	        {
	            System.err.println(e.getMessage());
	        }
		
	}
}
