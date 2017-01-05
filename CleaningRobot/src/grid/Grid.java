package grid;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.JFrame;

import algorithm.State.Orientation;


public class Grid {

	private int gridDimension;
	private int cols;
	private int rows;
	private int totalCells;
	private Cell [] arrayCells;
	JFrame frame = new JFrame("Room layout");
	GridLayout grid;
	LinkedList<Integer> heuristicList = new LinkedList<Integer>();//the list of the dirt first and the adjacent cells next
	LinkedList<Integer> closedList = new LinkedList<Integer>();//the list of cells with unassigned heuristics
	
	public Grid(int columns, int rows){
		this.gridDimension = columns;
		this.cols = columns;
		this.rows = rows;
		this.totalCells = this.cols * this.rows;
		this.arrayCells = new Cell[this.totalCells];
		grid = new GridLayout(gridDimension, gridDimension);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLayout(grid);
		for(int i = 0; i<totalCells; i++){
			this.arrayCells[i] = new Cell(i);
			closedList.addLast(i);//add all the cells to the unassigned heuristics list
			frame.add(this.arrayCells[i].getBtn());
		}
	}
	
	public Cell getCell(int i){
		return arrayCells[i];
	}
	
	public Cell [] getArrayCells(){
		return arrayCells;
	}
	
	public void createObstacles(int[] positions){
		for(int i = 0; i<positions.length; i++){
			arrayCells[positions[i]].setType(3);
			//remove all the obstacles from the unassigned heuristics list. 
			//the obstacles heuristics need to stay initial: -2
			closedList.remove(closedList.indexOf(positions[i]));
		}	
	}
	
	public void createDirt(int[] positions){
		for(int i = 0; i<positions.length; i++){
			arrayCells[positions[i]].setType(2);
			//set the dirts heuristics to 0
			arrayCells[positions[i]].setHeuristic(0);
			//add all the dirt with heuristics 0 to the heuristics lists
			heuristicList.addLast(positions[i]);
			//remove the dirts from the unassigned heuristics list
			closedList.remove(closedList.indexOf(positions[i]));
		}
		
	}
	
	
	//to do: cannot place robot on obstacles
	/****\\
	 * 
	 * 
	 * 
	 * 
	 * to do: cannot place robot on obstacles
	 * 
	 * 
	 * ***/
	public void placeRobot(int position){
		arrayCells[position].setIsRobotOn(true, Orientation.EAST);//to do: let the user decide
	}
	

	public void create(){
		//while the unassigned heuristics list is not empty
		while(!closedList.isEmpty()){
			int adjacentPosition = move(heuristicList.getFirst(), Orientation.EAST);
			if(adjacentPosition != -1){//is the adjacentPosition valid?
				if(arrayCells[adjacentPosition].getType() != 3){//is it an obstacle?
					if(arrayCells[adjacentPosition].getHeuristic() == -2){//is it not visited?
						
						//set the heuristics to the smallest adjacent one + 1
						arrayCells[adjacentPosition].setHeuristic(arrayCells[heuristicList.getFirst()].getHeuristic() + 1);
						//add the cell to the heuristics list since it just got assigned a heuristic
						heuristicList.addLast(adjacentPosition);
						//trying to remove the just assigned cell position from the closedList
						int ind = closedList.indexOf(adjacentPosition);//get his index
						if(ind != -1){//is it a valid index?
							//if so, then remove as planned
							closedList.remove(ind);
						}
					}
				}
			}
			adjacentPosition = move(heuristicList.getFirst(), Orientation.NORTH);
			if(adjacentPosition != -1){
				if(arrayCells[adjacentPosition].getType() != 3){
					if(arrayCells[adjacentPosition].getHeuristic() == -2){
						arrayCells[adjacentPosition].setHeuristic(arrayCells[heuristicList.getFirst()].getHeuristic() + 1);
						heuristicList.addLast(adjacentPosition);
						int ind = closedList.indexOf(adjacentPosition);
						if(ind != -1){
							closedList.remove(ind);
						}
					}
				}
			}
			adjacentPosition = move(heuristicList.getFirst(), Orientation.SOUTH);
			if(adjacentPosition != -1){
				if(arrayCells[adjacentPosition].getType() != 3){
					if(arrayCells[adjacentPosition].getHeuristic() == -2){
						arrayCells[adjacentPosition].setHeuristic(arrayCells[heuristicList.getFirst()].getHeuristic() + 1);
						heuristicList.addLast(adjacentPosition);
						int ind = closedList.indexOf(adjacentPosition);
						if(ind != -1){
							closedList.remove(ind);
						}
					}
				}
			}
			adjacentPosition = move(heuristicList.getFirst(), Orientation.WEST);
			if(adjacentPosition != -1){
				if(arrayCells[adjacentPosition].getType() != 3){
					if(arrayCells[adjacentPosition].getHeuristic() == -2){
						arrayCells[adjacentPosition].setHeuristic(arrayCells[heuristicList.getFirst()].getHeuristic() + 1);
						heuristicList.addLast(adjacentPosition);
						int ind = closedList.indexOf(adjacentPosition);
						if(ind != -1){
							closedList.remove(ind);
						}
					}
				}
			}
			
		//remove the position of the cell we just used to assign his adjacent brothers, from the heuristics list
		//his brothers who got added to the heuristics list will be use later on to assign their UNASSIGNED brothers
		heuristicList.removeFirst();
		System.out.println("=====FINISH");
		}
	    Color bg = new Color(59, 89, 182);
	    frame.getContentPane().setBackground(bg);
		frame.setVisible(true);
	}
	
	/**
	 * Find the position where the move is taking the robot if do-able
	 * */
	public int move(int position, Orientation o){

		//determine next position first
		int nextPosition = -1;
		switch(o){
		case NORTH:
			nextPosition = position - rows;
			break;
		case WEST:
			nextPosition = position - 1;
			break;
		case SOUTH:
			nextPosition = position + rows;
			break;
		case EAST:
			nextPosition = position + 1;
			break;
		}
		
		//if next position does not exist
		if(nextPosition<0 || nextPosition>=arrayCells.length){
			System.out.println("--NO7");
			return -1;
		}
		//next position is neither top nor bottom nor left nor right
		if(nextPosition!=position+1 && nextPosition!=position-1 && 
				nextPosition!=position-rows && nextPosition!=position+rows){
			System.out.println("--NO1");
			return -1;
		}
		//next position is on right of positions 0, 4, 8 and 12
		if((position%gridDimension == 0) && (nextPosition == position - 1 )){
			System.out.println("--NO4");
			return -1;
		}
		//next position is on left of positions 3, 7, 11, 15
		if((position%gridDimension == (gridDimension-1)) && (nextPosition == position + 1 )){
			System.out.println("--NO5");
			return -1;
		}
		//next position is an obstacle
		if(arrayCells[nextPosition].getType() == 3){
			System.out.println("--NO6");
			return -1;
		}
		
		return nextPosition;
	}
	
	
	
	/**
	 * chars allowed: S: suck, R: right, L: left, M: move
	 * to do: add cost!!!!!!!
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */
	public void robotAction(char a, int position){
		if(a!='S' && a!='R' && a!='L' && a!='M'){
			return;
		}
		//if is not a robot don't do anything
		if(!arrayCells[position].getIsRobotOn()){
			return;
		}
		switch(a){
		case 'S':
			Orientation ori0 = arrayCells[position].getOritentation();
			if(arrayCells[position].getType() == 2){
				arrayCells[position].setType(1);
				System.out.println("pos: "+position+ ", "+ori0+", "+ "suck");
			}
			break;
			
		case 'R':
			Orientation ori1 = arrayCells[position].getOritentation();
			int orientationIndex1 = ori1.ordinal();
			arrayCells[position].setOrientation(Orientation.values()[(orientationIndex1+3)%4]);
			System.out.println("pos: "+position+ ", "+ori1+", "+ "right");
			break;
			
		case 'L':
			Orientation ori2 = arrayCells[position].getOritentation();
			int orientationIndex2 = ori2.ordinal();
			arrayCells[position].setOrientation(Orientation.values()[(orientationIndex2+1)%4]);
			System.out.println("pos: "+position+ ", "+ori2+", "+ "left");
			break;
			
		case 'M':
			Orientation ori3 = arrayCells[position].getOritentation();
			move(position, ori3);
			System.out.println("pos: "+position+ ", "+ori3+", "+ "move");
			break;
		default:
			System.out.println("just starting");
		}
	}
	
	public void toPrint(){
		for(int i = 0; i<arrayCells.length; i++){
			System.out.println(arrayCells[i].print());
		}
	}
}
