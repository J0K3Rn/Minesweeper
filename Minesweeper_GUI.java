import java.awt.Graphics;
import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Minesweeper_GUI extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//USE JDIALOG FOR WIN / LOSE CONDITIONS
	int numOfBombs;
	int placeBombs;
	int tileCount = 0;
	int turn = 0;
	Cell[][] cells = new Cell[10][10];
	
	
	Cell last_clicked = null;
	MouseEvent last_clicked_event = null; 
	
	JFrame frame;
	Minesweeper_GUI() {
		this.frame = new JFrame();
		frame.add(this);
		
		//Set up window
		frame.setSize(600,600);
		frame.setTitle("Minesweeper");
		GridLayout playingField = new GridLayout(10,10);
		//end set up window
		
		//set up game
		numOfBombs = 10;
		placeBombs = numOfBombs;
		//System.out.println(placeBombs);
		
		this.setLayout(playingField);
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				//JButton button = new JButton("");
				//window.add(new JButton(""));
				Cell b = new Cell(this, i, j);
				cells[i][j] = b;
				//button.addActionListener(b);
				//b.addMouseListener(b);
				this.add(cells[i][j]);
			}
		}
		
		//Implement a feature that generates the bomb locations after the first click
		//cells[0][0].setBombCount(numOfBombs);//static variables
		//cells[0][0].setTileCount(100);
		
		while(placeBombs > 0) {//Places bombs
			//System.out.println("Infinite Loop");
			int x = (int) ((double)(Math.random() * 10));
			int y = (int) ((double)(Math.random() * 10));
			//System.out.println(x + " " + y);
			if(cells[x][y].getBomb() == false) {
				cells[x][y].setBomb(true);

				placeBombs -= 1;
			}
		}
	
		for(int i = 0; i < 10; i++) {//Calculates bombsNear
			
			for(int j = 0; j < 10; j++) {
				int count = 0;
				if (cells[i][j].getBomb()) {
					cells[i][j].setBombsNear(-1);
					continue;
				}
				
				if(i > 0 && (cells[i-1][j].getBomb()== true)) {//To the left
					count += 1;
				}
				if(i < 9 && (cells[i + 1][j].getBomb() == true)) {//To the right
					count += 1;
				}
				if(j > 0 && (cells[i][j - 1].getBomb()== true)) {//To the Top
					count += 1;
				}
				if(j < 9 && (cells[i][j + 1].getBomb() == true)) {//To the Bottom
					count += 1;
				}
				if(i > 0 && j > 0 && (cells[i-1][j-1].getBomb()== true)) {//To the Top left
					count += 1;
				}
				if(i < 9 && j > 0 && (cells[i + 1][j - 1].getBomb() == true)) {//To the Top right
					count += 1;
				}
				if(i > 0 && j < 9 && (cells[i-1][j + 1].getBomb()== true)) {//To the Bottom left
					count += 1;
				}
				if(i < 9 && j < 9 && (cells[i + 1][j + 1].getBomb() == true)) {//To the Bottom right
					count += 1;
				}
				
					
				cells[i][j].setBombsNear(count);
				cells[i][j].repaint();
				
			}
		}
		//cells[0][0].CopyArray(cells);//Transfer array over to cell
		//end set up game
		
		//Check for win conditions / Fix win condition
		//Do a recursive call to reveal adjacent blank squares
		//Upon losing expose all mines
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	void floodfill_tile(int x, int y) {
		if (0 <= x && x < cells.length && 0 <= y && y < cells[0].length && cells[x][y].getClicked() == false) {
			
			
			cells[x][y].setClicked(true);// = true;
			tileCount += 1;
			//System.out.println(cells[x][y].bombsNear);
			
			String text =" ";

			if (cells[x][y].getBombsNear() > 0) text = cells[x][y].getBombsNear() + "";

			
			cells[x][y].setText(text);
			
			if (cells[x][y].getBombsNear() > 0) return;
			
			floodfill_tile(x+1, y);
			floodfill_tile(x-1, y);
			floodfill_tile(x, y+1);
			floodfill_tile(x, y-1);
			
		}
	} 
	
	boolean gameover = false;
	@Override
    protected void paintComponent(Graphics g) {
		
		
		//FIX SEAL
		
		if (!gameover) {
			if(tileCount == 100) {
				gameover = true;
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame ,  "Congratulations! You won the game!" ,"Game Done", JOptionPane.INFORMATION_MESSAGE);
			}
		if (last_clicked != null) {
			this.last_clicked_event.getButton();
			
			if(last_clicked_event.getButton() == 3 && !last_clicked.getClicked()) {
				if(last_clicked.getText() != (last_clicked.getBombsNear() + "")) {
		    		if(last_clicked.getText() == "S") {
		    			last_clicked.setText("");
		    			tileCount -= 1;
		    		} else {
		    			last_clicked.setText("S");
		    			tileCount += 1;
		    		}
				}
			} 
			if(last_clicked_event.getButton() == 1 && !last_clicked.getClicked()) {
				if(last_clicked.getText() != "S") {
					last_clicked.setClicked(true);// = true;
					if (last_clicked.getBombsNear() >= 0) {
						if (last_clicked.getBombsNear() == 0) {
							last_clicked.setClicked(false);// = false;
							floodfill_tile(last_clicked.getROW(), last_clicked.getCOLUMN());
						} else {
							if(last_clicked.getText() != last_clicked.getBombsNear() + "") {
							last_clicked.setText("" + last_clicked.getBombsNear());
							tileCount += 1;
							} else {
								
							}
						}
		
				    }else if (last_clicked.getBomb()) {
				    	if(turn == 0) {//If clicked bomb on first turn
				    		int xPos =last_clicked.getROW();
				    		int yPos =last_clicked.getCOLUMN();
				    		cells[xPos][yPos].setBomb(false);
				    		placeBombs = 1;
				    		while(placeBombs > 0) {//Places bombs
				    			//System.out.println("Infinite Loop");
				    			int x = (int) ((double)(Math.random() * 10));
				    			int y = (int) ((double)(Math.random() * 10));
				    			//System.out.println(x + " " + y);
				    			if(cells[x][y].getBomb() == false && ((x != xPos)||(y != yPos))) {
				    				cells[x][y].setBomb(true);
				    				placeBombs -= 1;
				    				
				    			}
				    		}
				    		for(int i = 0; i < 10; i++) {//Calculates bombsNear
				    			
				    			for(int j = 0; j < 10; j++) {
				    				int count = 0;
				    				if (cells[i][j].getBomb()) {
				    					cells[i][j].setBombsNear(-1);
				    					continue;
				    				}
				    				
				    				if(i > 0 && (cells[i-1][j].getBomb()== true)) {//To the left
				    					count += 1;
				    				}
				    				if(i < 9 && (cells[i + 1][j].getBomb() == true)) {//To the right
				    					count += 1;
				    				}
				    				if(j > 0 && (cells[i][j - 1].getBomb()== true)) {//To the Top
				    					count += 1;
				    				}
				    				if(j < 9 && (cells[i][j + 1].getBomb() == true)) {//To the Bottom
				    					count += 1;
				    				}
				    				if(i > 0 && j > 0 && (cells[i-1][j-1].getBomb()== true)) {//To the Top left
				    					count += 1;
				    				}
				    				if(i < 9 && j > 0 && (cells[i + 1][j - 1].getBomb() == true)) {//To the Top right
				    					count += 1;
				    				}
				    				if(i > 0 && j < 9 && (cells[i-1][j + 1].getBomb()== true)) {//To the Bottom left
				    					count += 1;
				    				}
				    				if(i < 9 && j < 9 && (cells[i + 1][j + 1].getBomb() == true)) {//To the Bottom right
				    					count += 1;
				    				}
				    				
				    					
				    				cells[i][j].setBombsNear(count);
				    				
				    			}
				    		}
				    		last_clicked.setClicked(true);
				    		if(last_clicked.getText() != last_clicked.getBombsNear() + "") {
								last_clicked.setText("" + last_clicked.getBombsNear());
								tileCount += 1;
								} else {
									
								}
				    	} else {
						last_clicked.setText("X");
						for(int i = 0; i < cells.length; i++) {
							for(int j = 0; j < cells[0].length; j++) {
								if(cells[i][j].getText() == "S") {
									cells[i][j].setText("");
								}
								if(cells[i][j].getBomb()) {
									cells[i][j].setText("X");
								}
							}
						}
						gameover = true;
						JFrame frame = new JFrame();
						
						JOptionPane.showMessageDialog(frame ,  "BOOM! You lost!" ,"Game Done", JOptionPane.INFORMATION_MESSAGE);
						
				    }
				    }
			}
				turn += 1;
		}
			
		}
		
		last_clicked = null;
		last_clicked_event = null; 
		//super.paintComponent(g);
		repaint();
		}
	}

	
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e);
	}
}
