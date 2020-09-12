package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameOver {

	public void render(Graphics g){
		g.setFont(new Font("Arial", 0, 50));
		
		if (Game.winner == null){
			g.setColor(Color.white);
		}else if (Game.winner == Player.X){
			g.setColor(Color.red);
		}else{
			g.setColor(Color.green);
		}
		
		g.fillRect(150, 210, 240, 120);
		
		g.setColor(Color.black);
		g.drawRect(150, 210, 240, 120);
		
		g.drawString("WINS", 240, 285);
		
		if (Game.winner == null){
			Graphics2D g2d = (Graphics2D) g;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			Counter.drawX(190, 270, 70, g2d);
			Counter.drawO(193, 270, 70, g2d);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}else if (Game.winner == Player.X){
			Counter.drawX(190, 270, 70, (Graphics2D) g);
		}else{
			Counter.drawO(190, 270, 70, (Graphics2D) g);
		}
	}
}
