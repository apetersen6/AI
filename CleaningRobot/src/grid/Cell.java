package grid;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import algorithm.State.Orientation;

//A cell is a JButton with infos attached
public class Cell {
	private JButton btn = new JButton();
	private int type;
	//type 1 is clean
	//type 2 is dirt
	//type 3 is obstacle
	private boolean isRobotOn = false;
	private Orientation ori;
	private int position;
	private int heuristic = -2;
	
	public Cell(int p){
		this.type = 1;
		this.position = p;
	}
	
	public void setType(int n){
		if(n !=1 && n!=2 && n!=3){
			return;
		}
		type = n;
		switch(n){
		case 1:
			btn.setText(null);
			btn.setForeground(null);
			break;
		case 2:
			btn.setText("X");
			btn.setForeground(Color.BLACK);
			break;
		case 3:
			btn.setText("O");
			btn.setOpaque(true);
			btn.setForeground(null);
			btn.setBackground(Color.BLACK);
			btn.setBorderPainted(false);
			break;
		}
	}
	
	public int getType(){
		return type;
	}
	public JButton getBtn(){
		return btn;
	}
	
	public boolean getIsRobotOn(){
		return isRobotOn;
	}
	public void setIsRobotOn(boolean bool, Orientation o){
		this.ori = o;
		isRobotOn = bool;
		if(isRobotOn){
			btn.setOpaque(true);
			btn.setBackground(Color.PINK);
			setOrientation(o);
		} else{
			btn.setOpaque(false);
			btn.setBackground(null);
			btn.setBorder(null);
		}
	}
	
	public void setOrientation(Orientation o){
		switch(o){
		case EAST:
			ori = o;
			btn.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 6, Color.CYAN));
			break;
		case WEST:
			ori = o;
			btn.setBorder(BorderFactory.createMatteBorder(0, 6, 0, 0, Color.CYAN));
			break;
		case NORTH:
			ori = o;
			btn.setBorder(BorderFactory.createMatteBorder(6, 0, 0, 0, Color.CYAN));
			break;
		case SOUTH:
			ori = o;
			btn.setBorder(BorderFactory.createMatteBorder(0, 0, 6, 0, Color.CYAN));
			break;	
		}
	}
	
	public Orientation getOritentation(){
		return ori;
	}
	
	public int getPosition(){
		return position;
	}
	public void setHeuristic(int i){
		heuristic = i;
	}
	public int getHeuristic(){
		return heuristic;
	}
	
	public String print(){
		return "position: "+position+", type: "+type+", heuristic: "+heuristic;
	}
}
