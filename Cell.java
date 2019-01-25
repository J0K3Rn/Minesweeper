import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
//import javax.swing.JDialog;
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class Cell extends JButton implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*private static final int MARKED_NONE=0, MARKED_MINE=1;//, MARKED_POTENTIAL=2;
	private static final int max_marks = 2;
	private static int tileCount = 100;
	private static int bombCount = 0;
	private static int bombSealedCount = 0;
	private static int tileOpenedCount = 0;
	private static boolean finished = false;*/
	
	
	private boolean isBomb;
	private boolean clicked = false;
	
	
	//private int marked = Cell.MARKED_NONE;
	private Minesweeper_GUI parent;
	
	private int ROW,COLUMN;
	//private int n_neighboring_bombs;
	
	

	//private static Cell[][] Board;
	
	private int bombsNear;
	public int getBombsNear() {
		return bombsNear;
	}
	public int getROW() {
		return ROW;
	}
	public int getCOLUMN() {
		return COLUMN;
	}
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
	//assign each square with a box class
	public boolean getClicked() {
		return clicked;
	}
	
	
	Cell(Minesweeper_GUI parent, int ROW, int COLUMN) { 
		//isBomb = true;
		bombsNear = 0;
		isBomb = false;
		this.parent = parent;
		
		this.addMouseListener(this);
		this.ROW = ROW;
		this.COLUMN = COLUMN;
	}
	
	/*public void CopyArray(Cell[][] Array) {
		Board = Array;
	}*/
	/*public int getTurn() {
		return tileOpenedCount;
	}*/
	public boolean getBomb() {
		//return isBomb;
		if(isBomb == true) {
			return true;
		} else {
			return false;
		}
	}
	public void setBomb(boolean status) {
		isBomb = status;
		/*if(isBomb == true) {
			bombCount += 1;
		}
		else {
			bombCount -= 1;
		}*/
	}
	/*public void setBombCount(int bombs) {
		bombCount = bombs;
	}
	public void setTileCount(int num) {
		tileCount = num;
	}*/
	public void setBombsNear(int num) {
		bombsNear = num;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
		/*
		if ((marked % Cell.max_marks == 0) && e.getButton() == 1) { 
			clicked = true;
			if(bombsNear == 0) {
				
			}
			if(isBomb && (marked % Cell.max_marks == 0)) {//LOSE CONDITION
				JFrame frame = new JFrame();
				finished = true;
				JOptionPane.showMessageDialog(frame ,  "BOOM! You lost!" ,"Game Done", JOptionPane.INFORMATION_MESSAGE);
				//Expose all mines & make buttons unusable
			} 
		} else if (!clicked && e.getButton() == 3) {//Mouse click 3
			marked = (marked + 1) % Cell.max_marks;
			if(this.isBomb && this.getText() == "") {
				bombSealedCount += 1;
			} else if(this.isBomb && this.getText() == "S") {
				bombSealedCount -= 1;
			}
		}
		
		if (clicked) {
			if (this.isBomb) {
				this.setText("X");
			} else {
				if(bombsNear == 0) {
					this.setText("");
				} else {
					this.setText(String.valueOf(bombsNear));
				}
			}
			tileOpenedCount += 1;
		} else {
			if (marked == Cell.MARKED_NONE) {
				this.setText("");
			} else if (marked == Cell.MARKED_MINE) {
				this.setText("S");
			}
		}
		
		if(bombSealedCount == bombCount && tileOpenedCount == (tileCount - bombCount)) {//Check Win Condition
			JFrame frame = new JFrame();
			finished = true;
			JOptionPane.showMessageDialog(frame ,  "Congratulations! You won the game!" ,"Game Done", JOptionPane.INFORMATION_MESSAGE);
			//Make board unusable
		}
		*/
		this.parent.last_clicked = this;
		this.parent.last_clicked_event = e;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Paint paint;
        if (this.clicked) {
    	    paint = Color.WHITE.brighter();
        } else {
    	    paint = new GradientPaint(new Point(0, 0), Color.WHITE, new Point(0,getHeight()), Color.GRAY.brighter());
        }
        g2.setPaint(paint);
        g2.fillRect(0, 0, getWidth(), getHeight());
      
        g2.setPaint(Color.BLACK);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		this.setVerticalTextPosition(SwingConstants.CENTER);
		Font f = new Font(g2.getFont().getFontName(), Font.BOLD, Math.min(getWidth(),getHeight()));
		g2.setFont(f);
        g2.drawString(getText(), getWidth()*1/5, getHeight()*9/10);
        g2.dispose();

      // super.paintComponent(g);
    }
	

}
