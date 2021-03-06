package helmet;

import java.awt.Graphics2D;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.ImageIcon;

public class Shield extends Tool{
	

	public Shield(Game game, Player player) {
		super(game, player);
		this.toolImg = new ImageIcon(this.getClass().getResource("Img/shield.png")).getImage();
	}
	
	public void run(){
		running.set(true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		while(running.get()){
			super.run();
			if(y>game.getHeight()){
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(collision==true){
					collision=false;
				}
			}else{
				if(toolCollision() && immune==false){
					if(collision==false){
						this.play();
						game.shieldTimer.start();
						immune = true;
						collision = true;
					}
				}
			}
		}
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(toolImg, x, y, WIDTH, HEIGHT, game);
		
	}
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
	            AudioInputStream hurt =AudioSystem.getAudioInputStream(Shield.class.getResource("Audio/yes.wav"));
	            toolEffect.open(hurt);
	 
	            toolEffect.start();
	        }
	        catch (Exception e)
	        {
	            System.err.println(e.getMessage());
	        }
		
	}

}
