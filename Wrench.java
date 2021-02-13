package helmet;

import java.awt.Graphics2D;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.ImageIcon;

public class Wrench extends Tool{
	
	public Wrench(Game game, Player player) {
		super(game, player);
		this.toolImg = new ImageIcon(this.getClass().getResource("Img/wrench.png")).getImage();
	}
	
	public void run(){
		running.set(true);
		try {
			Thread.sleep(3000);
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
					still = true;
					Game.lives--;
					if(Game.lives<=0){
						game.gameOver();
					}
					game.wrenchTimer.start();
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
	            AudioInputStream hurt =AudioSystem.getAudioInputStream(Wrench.class.getResource("Audio/hurt.wav"));
	            toolEffect.open(hurt);
	 
	            toolEffect.start();
	        }
	        catch (Exception e)
	        {
	            System.err.println(e.getMessage());
	        }
		
	}

}
