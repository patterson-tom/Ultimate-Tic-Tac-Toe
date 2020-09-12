package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 8216362858768021820L;
	private long timer = System.currentTimeMillis(); //keeps track of uptimer for calculating fps
	private Thread thread;
	private boolean running = false;
	public static Board[] board = new Board[10]; // 10 boards, 9 sub and 1 main
	private Cursor cursor;
	public static Player turn;
	public static Player winner;
	public static boolean finished = false;
	private GameOver gameOver;
	
	public static void main(String[] args){
		new Game(); //creates new instance, basically just calls constructor
	}
	
	public Game(){
		JFrame frame = new JFrame("Noughts and Crosses"); // creates the JFrame with the name "Noughts and Crosses"
		
		int counter = 0; //keeps track of where up to in board array
		for (int i = 0; i <=360; i+=180){
			for(int j = 0; j <=360; j+=180){ //all sub-boards intitialised
				board[counter] = new Board(i,j,false);
				counter++; //moves onto next index in array
			}
		}
		board[9] = new Board(0,0,true); //main board
		
		cursor = new Cursor(board);
		
		turn = Player.O; // X goes first
		this.addMouseMotionListener(cursor);
		this.addMouseListener(cursor);
		
		gameOver = new GameOver();
		new Window(552,578, this, frame); //creates window
	}
	
	public void run() { //game loop
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		int frames = 0;
		int sleepTime;
		double delta; //delta makes everything move at constant rate no matter the fps
		
		while(running){ //loops round roughly 60 times a second if all is workking
			long now = System.nanoTime(); //updates time variables
			delta = (now-lastTime)/ns; 
			lastTime = now;
			
			tick(delta); // calls the tick and render methods for everything
			render();
			
			frames++; //increments the number of frames
			sleepTime = (int) (lastTime - System.nanoTime() + ns) /1000000; //sleeps for necessary amount of time to achieve 60 fps;
			
			try { //try catch to stop interrupted exception and incase sleeptime is < 0
				Thread.sleep(sleepTime);
			} catch (Exception e) {}
			
			if (System.currentTimeMillis() - timer > 1000){ //checks if second has passed, and outputs fps
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}
	
	public synchronized void start(){ //starts game loop
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public void stop(){
		running = false;
	}
	
	private void tick(double delta){ //ticks everyhting in game
		if (!finished){
			cursor.tick();
		}
	}
	
	private void render(){ //renders everything in game
		
		BufferStrategy bs = this.getBufferStrategy(); //gets graphics
		if (bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
	    g.setColor(Color.white); //draw backgorund
		g.fillRect(0,0,552,578);
		
		board[9].render(g); //renders main board first
		for (int i = 0; i < 9; i++){ //draws boards
			board[i].render(g);
		}
		
		if (!finished){
			cursor.render(g);
		}
		
		if (finished){
			gameOver.render(g);
		}
		
		g.dispose();
		bs.show();
	}

}
