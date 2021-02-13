package helmet;

import java.awt.Graphics2D;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.ImageIcon;

public class Screwdriver extends Tool {
	
	public Screwdriver(Game game, Player player) {
		super(game, player);
		this.toolImg = new ImageIcon(this.getClass().getResource("Img/scdriver.png")).getImage();
	}
	
	public void run(){
		running.set(true);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
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
						Game.lives--;
						if(Game.lives<=0){
							game.gameOver();
						}
						game.screwdriverTimer.start();	//iniciem timer de 5 segons
						kewChange = true;	//invertim les direccions fins finalitzar el timer
						
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
	            AudioInputStream hurt =AudioSystem.getAudioInputStream(Screwdriver.class.getResource("Audio/hurt.wav"));
	            toolEffect.open(hurt);
	 
	            toolEffect.start();
	        }
	        catch (Exception e)
	        {
	            System.err.println(e.getMessage());
	        }
		
	}

}
