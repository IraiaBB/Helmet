package helmet;

import java.awt.Graphics2D;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.ImageIcon;

public class Hammer extends Tool {
	
	public Hammer(Game game, Player player) {
		super(game, player);
		this.toolImg = new ImageIcon(this.getClass().getResource("Img/hammer.png")).getImage();
		
	}
	
	

	public void run(){
		running.set(true);
		while(running.get()){
			super.run();
			if(y>game.getHeight()){
				if(collision==true){
					collision=false;
				}
			}else{
				if(toolCollision() && immune==false){
					if(collision==false){
						this.play();	
						Game.lives=Game.lives-2;
						if(Game.lives<=0){
							game.gameOver();
						}
						player.setX(0);
						collision = true;
					}
				}
			}
		}
	}
	
	public void paint(Graphics2D g){
		
		g.drawImage(toolImg, x, y, WIDTH, HEIGHT, game);
	}



	@Override
	public void play() {
		 try
	        {
	            if (toolEffect == null)
	            {
	                toolEffect = AudioSystem.getClip();
	            }
	            else
	            {
	                toolEffect.close();
	            }
	            AudioInputStream hurt =AudioSystem.getAudioInputStream(Hammer.class.getResource("Audio/hurt.wav"));
	            toolEffect.open(hurt);
	 
	            toolEffect.start();
	        }
	        catch (Exception e)
	        {
	            System.err.println(e.getMessage());
	        }
		
	}
}
