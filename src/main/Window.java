package main;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Window extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8255319694373975038L;

	public Window(int width, int height, Game game, JFrame frame){
		frame.setPreferredSize(new Dimension(width,height)); //only possible size is width,height
		frame.setMaximumSize(new Dimension(width,height));
		frame.setMinimumSize(new Dimension(width,height)); 
		frame.setResizable(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //makes the X button work
		frame.setLocationRelativeTo(null); //centres on screen
		frame.add(game); //adds the game to the window, so we can draw on it
		frame.setVisible(true);
		frame.requestFocus(); 
		
		frame.setCursor(frame.getToolkit().createCustomCursor( //hides mouse pointer
	            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
	            "null"));
		game.start(); //intialises thread and thus starts game loop
		
	}
}
