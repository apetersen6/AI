package algorithm;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import algorithm.State.Action;
import algorithm.State.Orientation;
import grid.Cell;
import grid.Grid;

public class Algorithm {

	/*
	 * type 1 is DFS
	 * type 2 is BFS
	 * type 3 is A*
	 * */
	protected State initial = new State(0, Orientation.EAST, Action.START, 4);
	private LinkedList<State> frontier = new LinkedList<State>();//from initial state to goal???
	protected LinkedList<State> exploredSet = new LinkedList<State>();
	private int type;//is it DFS, BFS or A*?
        private static int cost=0;//total cost of the algorithm
	/*
	 * type 1 is DFS
	 * type 2 is BFS
	 * type 3 is A*
	 * */
	
	private static Grid grid;
	//all the cells in the grid
//	private static Cell [] arrayCells = grid.getArrayCells();

	
	public Algorithm(Grid g){
		frontier.addLast(initial);
		this.grid = g;
		
	}
	
	public boolean goalTest(State state){
		return (state.getGoal() == 0);
	}
	
	public State DFS(){
		State temp;
		while(!frontier.isEmpty()){
			
			temp = frontier.getFirst();
			System.out.print("STACK Positions: ");
			for(int i = 0; i<frontier.size(); i++){
				System.out.print(frontier.get(i).getPosition()+",");
			}
			System.out.println();

			frontier.removeFirst();
			
			if(goalTest(temp)){
				return temp;
			}
			exploredSet.addFirst(temp);
			//add VALID state, for example move to an obstacle is not valid
			
			//turn right state
			System.out.println("R");
			State rightState = new State(temp.getPosition(), Orientation.values()[(temp.getOri().ordinal()+3)%4], 
					Action.RIGHT, temp.getGoal());
			if(!frontier.contains(rightState) && !exploredSet.contains(rightState)){
				frontier.addFirst(rightState);
				temp.setNext(rightState);
				rightState.setPrev(temp);
                                cost+=20;
				System.out.println("right    "+rightState.getOri());
			}
			//turn left state
			System.out.println("L");
			State leftState = new State(temp.getPosition(), Orientation.values()[(temp.getOri().ordinal()+1)%4], 
					Action.LEFT, temp.getGoal());
			if(!frontier.contains(leftState) && !exploredSet.contains(leftState)){
				frontier.addFirst(leftState);
				temp.setNext(leftState);
				leftState.setPrev(temp);
                                cost+=20;
				System.out.println("left   "+leftState.getOri());
			}
			
			
			
			
			//Can move?
			//if so move state
			System.out.println("M");
			if(grid.move(temp.getPosition(), temp.getOri()) != 99){
				State moveState = new State(grid.move(temp.getPosition(), temp.getOri()), 
						temp.getOri(), Action.MOVE, temp.getGoal());
				if(!frontier.contains(moveState) && !exploredSet.contains(moveState)){
					frontier.addFirst(moveState);
					temp.setNext(moveState);
					moveState.setPrev(temp);
                                        cost+=50;
					System.out.println("move");
				}
			}
			
			
			
			
			//Can suck?
			//if so suck state
			System.out.println("S");
			if(grid.getCell(temp.getPosition()).getType() == 2)//if the cell where the temp state is, is dirty
			{
				State dirtState = new State(temp.getPosition(), temp.getOri(), Action.SUCK, temp.getGoal()-1);
				if(!frontier.contains(dirtState) && !exploredSet.contains(dirtState)) //is it not in the frontier or exploredSet
				{
					frontier.addFirst(dirtState);
					temp.setNext(dirtState);
					dirtState.setPrev(temp);
                                        cost+=10;
					System.out.println("suck");

				}
			}
		
			
			if(frontier.isEmpty()){
                                return null;
			}
		}
		print();
		
		return new State(1, Orientation.EAST, Action.START, 99);
		
	}
	
	
	public State BFS(){
		
		State temp;
		while(!frontier.isEmpty()){
			
			temp = frontier.getFirst();
			System.out.print("FIFO Positions: ");
			for(int i = 0; i<frontier.size(); i++){
				System.out.print(frontier.get(i).getPosition()+",");
			}
			System.out.println();

			frontier.removeFirst();
			
			if(goalTest(temp)){
				return temp;
			}
			exploredSet.addLast(temp);
			//add VALID state, for example move to an obstacle is not valid
			
			//Can suck?
			//if so suck state
			System.out.println("S");
			if(grid.getCell(temp.getPosition()).getType() == 2)//if the cell where the temp state is, is dirty
			{
				State dirtState = new State(temp.getPosition(), temp.getOri(), Action.SUCK, temp.getGoal()-1);
				if(!frontier.contains(dirtState) && !exploredSet.contains(dirtState)) //is it not in the frontier or exploredSet
				{
					frontier.addLast(dirtState);
					temp.setNext(dirtState);
					dirtState.setPrev(temp);
                                        cost+=10;
					System.out.println("suck");

				}
			}
			//Can move?
			//if so move state
			System.out.println("M");
			if(grid.move(temp.getPosition(), temp.getOri()) != 99){
				State moveState = new State(grid.move(temp.getPosition(), temp.getOri()), 
						temp.getOri(), Action.MOVE, temp.getGoal());
				if(!frontier.contains(moveState) && !exploredSet.contains(moveState)){
					frontier.addLast(moveState);
					temp.setNext(moveState);
					moveState.setPrev(temp);
                                        cost+=50;
					System.out.println("move");
				}
			}
			//turn right state
			System.out.println("R");
			State rightState = new State(temp.getPosition(), Orientation.values()[(temp.getOri().ordinal()+3)%4], 
					Action.RIGHT, temp.getGoal());
			if(!frontier.contains(rightState) && !exploredSet.contains(rightState)){
				frontier.addLast(rightState);
				temp.setNext(rightState);
				rightState.setPrev(temp);
                                cost+=20;
				System.out.println("right    "+rightState.getOri());
			}
			//turn left state
			System.out.println("L");
			State leftState = new State(temp.getPosition(), Orientation.values()[(temp.getOri().ordinal()+1)%4], 
					Action.LEFT, temp.getGoal());
			if(!frontier.contains(leftState) && !exploredSet.contains(leftState)){
				frontier.addLast(leftState);
				temp.setNext(leftState);
				leftState.setPrev(temp);
                                cost+=20;
				System.out.println("left   "+leftState.getOri());
			}
			if(frontier.isEmpty()){
				return null;
			}
		}
//		print();
		return new State(1, Orientation.EAST, Action.START, 99);	
		
	}


	//
	public static Comparator<State> heuristicComparator = new Comparator<State>(){
		public int compare(State s1, State s2){
			return (int) (s1.getF() - s2.getF());
		}
	};
	
	public State Astar(){
		State returnState = null;
		
		Queue <State> priorityFrontier = new PriorityQueue<>(9, heuristicComparator);
		initial.setH(grid.getCell(initial.getPosition()).getHeuristic());
		initial.setG(0);
		priorityFrontier.add(initial);
		
		State temp;
		while(!priorityFrontier.isEmpty()){
			temp = priorityFrontier.peek();
			priorityFrontier.remove();
			//is it goal?
			if(goalTest(temp)){
				return temp;
			}
			
			//add VALID state, for example move to an obstacle is not valid
			
			//Can suck?
			//if so suck state
			System.out.println("S");
			if(grid.getCell(temp.getPosition()).getType() == 2)//if the cell where the temp state is, is dirty
			{
				State dirtState = new State(temp.getPosition(), temp.getOri(), Action.SUCK, temp.getGoal()-1);
				dirtState.setG(temp.getG()+10);
				dirtState.setH(grid.getCell(temp.getPosition()).getHeuristic());
					priorityFrontier.add(dirtState);
					temp.setNext(dirtState);
					dirtState.setPrev(temp);
                                        cost+=10;
					System.out.println("suck");
			}
			
			//Can move?
			//if so move state
			System.out.println("M");
			if(grid.move(temp.getPosition(), temp.getOri()) != -1){
				State moveState = new State(grid.move(temp.getPosition(), temp.getOri()), 
						temp.getOri(), Action.MOVE, temp.getGoal());
				moveState.setG(temp.getG()+50);
				moveState.setH(grid.getCell(moveState.getPosition()).getHeuristic());
					priorityFrontier.add(moveState);
					temp.setNext(moveState);
					moveState.setPrev(temp);
                                        cost+=50;
					System.out.println("move");
			}
			
			//turn right state
			System.out.println("R");
			State rightState = new State(temp.getPosition(), Orientation.values()[(temp.getOri().ordinal()+3)%4], 
					Action.RIGHT, temp.getGoal());
			rightState.setG(temp.getG()+20);
			rightState.setH(grid.getCell(rightState.getPosition()).getHeuristic());
				priorityFrontier.add(rightState);
				temp.setNext(rightState);
				rightState.setPrev(temp);
                                cost+=20;
				System.out.println("right    "+rightState.getOri());			
			
			//turn left state
			System.out.println("L");
			State leftState = new State(temp.getPosition(), Orientation.values()[(temp.getOri().ordinal()+1)%4], 
					Action.LEFT, temp.getGoal());
			
			leftState.setG(temp.getG()+20);
			leftState.setH(grid.getCell(leftState.getPosition()).getHeuristic());
				priorityFrontier.add(leftState);
				temp.setNext(leftState);
				leftState.setPrev(temp);
                                cost+=20;
				System.out.println("left   "+leftState.getOri());
			
			
			if(frontier.isEmpty()){
                               return null;
			}
			
			
			
		}
		print();
		
		return returnState;
	}
	
	
	public static int getCost(){
            return cost;
        }
	
	
	public void print(){
		for(int i = 0; i<exploredSet.size(); i++){
			System.out.print(exploredSet.get(i).print());
		}
	}
}
