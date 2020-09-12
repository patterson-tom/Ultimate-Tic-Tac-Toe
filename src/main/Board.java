package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class Board {
	
	private int[][] board = new int[9][9]; //stores value of each square 0 = blank, 1 = cross, 2 = nought.
	private int x;
	private int y;
	private boolean mainBoard;
	public static int playable = 10;
	
	public Board(int x, int y, boolean mainBoard){ //constructor sets location and type og board (sub/main)
		this.x = x;
		this.y = y;
		this.mainBoard = mainBoard;
	}
	public int getBoard(int x, int y) {
		return board[x][y];
	}
	
	public void setBoard(int x, int y, int value) {
		board[x][y] = value;
		
		if (mainBoard){
			checkWin();
		}
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		
		if (mainBoard){
			for (int i = 0; i < 3; i++){
				for (int j = 0; j < 3; j++){
					if (!(i*3 + j + 1 == playable || playable == 10) || !(board[i][j] == 0)){
						g.setColor(Color.gray);
						g.fillRect(x + (i*180),y+(j*180),180,180); //paints board gray when it is unplayable
					}
				}
			}
		}
		
		for(int i = 0; i < 3; i++){ //draws counters and shaded regions
			for (int j = 0; j < 3; j++){
				if (!mainBoard){
					if (board[i][j] == 1){
						Counter.drawX(x + (i*60) + 35, y + (j*60) + 32, 50, (Graphics2D) g); //draws an x counter
					}else if(board[i][j] == 2){
						Counter.drawO(x + (i*60) + 35, y + (j*60) + 32, 50, (Graphics2D) g); //draws an o counter
					}
				}else{ //for larger main board
					if (board[i][j] == 1){
						g.setColor(Color.red);
						g.fillRect(x + (i*180),y+(j*180),180,180); //paints board red when X has won it
					}else if(board[i][j] == 2){
						g.setColor(Color.green);
						g.fillRect(x + (i*180),y+(j*180),180,180); //paints board green when O has won it
					}
				}
			}
		}
		
		g.setColor(Color.black);
		if (!mainBoard){ //thinner borders for sub-boards
			for (int i = 0; i < 4; i++){
				g.fillRect(x+(i*60), y, 7, 180); //all vertical lines
			}
			for (int i = 0; i < 4; i++){
				g.fillRect(x, y+(i*60), 180, 7); //all horizontal lines
			}
			
		}else{ //for larger, main board
			for (int i = 0; i < 4; i++){
				g.fillRect(x+(i*180)-3, y, 13, 550); //550 down to fill space into bottom right corner of grid
			}
			for (int i = 0; i < 4; i++){
				g.fillRect(x, y+(i*180)-3, 540, 13);
			}
		}
		
		
	}
	
	public void checkClick(MouseEvent e){
		int ex = e.getX();
		int ey = e.getY();
		
		if (ex < x + 180 && ex > x && ey < y + 180 && ey > y && Game.board[9].getBoard(((x+180)/180)-1, ((y+180)/180)-1) == 0){ //dont bother checking if its not within the whole board
			for(int i = 0; i < 3; i++){ //loops through each square
				for (int j = 0; j < 3; j++){
					if (ex < x + (i*60)+60 && ex > x + (i*60) && ey < y + (j*60) + 60 && ey > y + (j*60)){ //if in square
						if (Game.turn == Player.X && board[i][j] == 0){ //assigns the correct counter, flips whose turn it is and returns
							board[i][j] = 1; 
							Game.turn = Player.O;
							checkWin();
							playable = Game.board[9].getBoard(i, j) == 0 ? i*3 + j + 1 : 10;
							return;
						}else if (Game.turn == Player.O && board[i][j] == 0){
							board[i][j] = 2;
							Game.turn = Player.X;
							checkWin();
							playable = Game.board[9].getBoard(i, j) == 0 ? i*3 + j + 1 : 10;
							return;
						}
					}
				}
			}
		}
	}
	
	private void checkWin(){
		
		for (int i = 0; i < 3; i++){
			if (board[0][i] == board[1][i] && board[0][i] == board[2][i]){
				if (board[0][i] == 1){
					win(Player.X);
				}else if (board[0][i] == 2){
					win(Player.O);
				}
			}
			
			if (board[i][0] == board[i][1] && board[i][1] == board[i][2]){
				if (board[i][0] == 1){
					win(Player.X);
				}else if (board[i][0] == 2){
					win(Player.O);
				}
			}
		}
		
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2]){
			if (board[0][0] == 1){
				win(Player.X);			
			}else if (board[0][0] == 2){
				win(Player.O);
			}
		}
		
		if (board[2][0] == board[1][1] && board[1][1] == board[0][2]){
			if (board[2][0] == 1){
				win(Player.X);
			}else if (board[2][0] == 2){
				win(Player.O);
			}
		}
		
		for(int i = 0; i < 3; i++){ //loops through each square, seeing if board is full
			for (int j = 0; j < 3; j++){
				if (board[i][j] == 0){
					return;
				}
			}
		}
		if (!mainBoard){
			Game.board[9].setBoard(((x+60)/60)-1, ((y+60)/60)-1, 3); //sets individual square to a draw (3)
		}else{
			Game.finished = true; //draw overall if no winner and full squares
		}
	} 
	
	
	
	private void win(Player winner){
		if (mainBoard){
			Game.winner = winner; //win
			Game.finished = true;
			return;
		}
		
		if (winner == Player.X){
			Game.board[9].setBoard(((x+180)/180)-1, ((y+180)/180)-1, 1);
		}else if (winner == Player.O){
			Game.board[9].setBoard(((x+180)/180)-1, ((y+180)/180)-1, 2);
		}
	}
}