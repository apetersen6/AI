package robot;


import algorithm.Algorithm;
import algorithm.State;
import grid.Grid;

public class RobotApp {

	static Grid layout = new Grid(4, 4);

	private static Algorithm algo = new Algorithm(layout);
	private static State goalState;
	
	public static void search(int AlgoType, Grid grid){
		switch(AlgoType){
		case 1:
			goalState = algo.DFS();
			break;
		case 2:
			goalState = algo.BFS();
			break;
		case 3:
			goalState = algo.Astar();
			break;
		}
	}
	
	public static void main (String [] args){
                long startTime = System.nanoTime(); //start time of the algorithm
                int depth = 0; //depth to the goal state
                
		int [] obstacles = {5, 6, 9};

		layout.createObstacles(obstacles);
		int [] dirt = {1, 4, 10, 13};

		layout.createDirt(dirt);
		layout.create();
		layout.toPrint();
		
		search(3, layout);
		System.out.println(goalState);
		System.out.println("//////////////////"+"\n");
                
		State tr = goalState;
		while(tr!=null){
			System.out.println(tr.print());
                        depth++;
			tr = tr.getPrev();                      
		}
                System.out.println("Total cost: "+ Algorithm.getCost());
                System.out.println("Depth: "+depth);
                System.out.println("Time elapsed: "+ (System.nanoTime() - startTime)/1000000 + " ms");
	}
}
