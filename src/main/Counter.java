package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Counter {
	
	public static void drawX(float x, float y, float width, Graphics2D g2d){ //draws an X counter
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.red); //sets the colour to red
		AffineTransform old = g2d.getTransform(); //gets the current transformation for restoration later
		
		AffineTransform transform = new AffineTransform(); //new transform object
		
		transform.rotate(Math.toRadians(45), x, y); //rotate by 45 degrees
		g2d.transform(transform); 
		g2d.fillRect((int)(x-width/2), (int)(y-width/10), (int)width, (int)width/5); //draw first arm of x
		g2d.setColor(Color.black);
		g2d.drawRect((int)(x-width/2), (int)(y-width/10), (int)width, (int)width/5);
		transform.rotate(Math.toRadians(-45), x , y); //rotate by 135 deg //reset transform object
		g2d.setTransform(old); //restores old transformation
		
		transform.rotate(Math.toRadians(135), x, y); //rotate by 135 deg
		g2d.transform(transform);
		g2d.setColor(Color.red);
		g2d.fillRect((int)(x-width/2), (int)(y-width/10), (int)width, (int)width/5); //draw second arm of x
		g2d.setColor(Color.black);
		g2d.drawRect((int)(x-width/2), (int)(y-width/10), (int)width, (int)width/5);
		g2d.setTransform(old); //restores old transformation
		
	}
	
	public static void drawO(float x, float y, float width, Graphics2D g2d){ //draws an O counter
		g2d.setColor(Color.green);
		g2d.setStroke(new BasicStroke(10));
		g2d.drawOval((int)(x - width/2 + 6),(int)(y - width/2 + 8),(int)( width * 0.7), (int)(width * 0.7));
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.black);
		g2d.drawOval((int)(x - width/2 + 6)-4,(int)(y - width/2 + 8)-4,(int)( width * 0.7)+8, (int)(width * 0.7)+8);
		g2d.drawOval((int)(x - width/2 + 6)+4,(int)(y - width/2 + 8)+4,(int)( width * 0.7)-8, (int)(width * 0.7)-8);
	}
}
