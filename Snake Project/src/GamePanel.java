import java.awt.*;	
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	static final int SCREEN_WIDTH = 600; //game width
	static final int SCREEN_HEIGHT = 600; //game height
	static final int UNIT_SIZE = 25; //each item has 25 pixels
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	//coordinates of snake 
	final int x[] = new int[GAME_UNITS]; //x cordinates of snake
	final int y[] = new int[GAME_UNITS]; //y coordinates of snake
	//snake is never gonna be bigger than the game dimensions itself
	int bodyParts = 6; //initially how many body parts the snake begins with
	int applesEaten; 
	int appleX; //x coordinate in which the apple is located
	int appleY; //y coordinate
	char direction = 'R'; //initially goes in the right direction
	boolean running = false;
	Timer timer;
	Random random;
	
	
	GamePanel(){ //Constructor
		random = new Random(); //creating an instance of the random class
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); //size of the window
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame() {
		newApple(); //This is to create a new apple on the screen
		running = true; //Was false at first
		timer = new Timer(DELAY,this);
		timer.start(); //using the start function to start the game
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(running) {	
			//we are gonna make the game window into a grid/matrix so that objects can be seen easily as commpared to the window size
			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) { //drawing lines on the game window to become a grid
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); //X AXIS(VERITCAL LINES)
			g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH, i*UNIT_SIZE);    //Y AXIS(HORIZONTAL LINES)
			//g.drawline(x1,y1,x2,y2);
			}
			//starting to draw the apple
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			//g.fillOval(x coordinate of apple, y coordiante of apple, size of apple, size of apple)
			 
			//drawing the snake
			for(int i=0;i<bodyParts;i++) {
				if(i==0) { //head of snake
					g.setColor(Color.gray); //color of head of snake
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE); //shape of snake
				}
				else {
					g.setColor(Color.green); //color of body of snake
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);				
				}
			}
			//Score on top of screen
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
			//The line of code above displays score on top of screen
		}
		else {
			gameOver(g); //calling the gameOver method
			//g is the graphics
		}
	}
	public void newApple() {//this method is to generate the coordinates of a new apple everytime an apple is eaten
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE; //x coordinate(THE BRACKETS HAVE THE RANGE) 
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE; //y coordinate(THE BRACKETS HAVE THE RANGE) 
		
	}
	public void move() { //movement of snake
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1]; //shifting the x coordinate of the bodypart
			y[i] = y[i-1]; //shifting the y coordinates of the bodypart
		}
		
		//changing direction of snake
		switch(direction) {
		case 'U': //up direction
			//y[index 0/head of snake] = next position in that direction/        
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D': //down direction
			//y[index 0/head of snake] = next position in that direction/        
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L': //left direction
			//y[index 0/head of snake] = next position in that direction/        
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R': //up direction
			//y[index 0/head of snake] = next position in that direction/        
			x[0] = x[0] + UNIT_SIZE;
			break;
			
		}
	}
	public void checkApple() {
		if((x[0] == appleX) && y[0] == appleY) { //if coordinates of head of snake == coordinate of apple
			bodyParts++; //snake becomes bigger
			applesEaten++; //score increases
			newApple(); //new apple function is called to spawn new apple
		}
	}
	public void checkCollisions() {
		//checking if the head of the snake collides with the body of the snake
		for(int i = bodyParts;i>0;i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) { //x[0] and y[0] is the head of the snake
				running = false; //game stops running
			}
		}
		//checks if head touches left border
		if(x[0] < 0) {
			running = false;
		}
		//checks if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		//checks if head touches top border
		if(y[0] < 0) {
			running = false;
		}
		//checks if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		if(!running) { //if the running is false
			timer.stop(); //timer stops
		}
	}
	public void gameOver(Graphics g) {
		//Score when game is over
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		
		//Game Over text
		g.setColor(Color.red);
		g.setFont(new Font("Arial",Font.BOLD,75));
		//g.setFont(new Font("Font type", Font editing, Font size);
		
		//To bring the text to the centre of the screen
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		//("Text to be written", Width of the screen - the size of text(space taken up by text in the scrren0/2(centre of scrren), sntre of scrren vertically 
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) { //if the game is running 
			move(); //call the move function
			checkApple(); //checking if the snake ran into the apple
			checkCollisions(); //checking if collisions occur
		}
		repaint(); //if the game isn't running   
	}
	
	//Creating an inner class
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) { //method to control snake with arrow keys
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
				//these lines of code are to avoid 180 degree turns
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}

}
