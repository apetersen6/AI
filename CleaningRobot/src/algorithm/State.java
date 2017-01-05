package algorithm;

import java.util.LinkedList;

public class State {
	//only the goal can change, so only goal has a setter
	private int position;
	public enum Orientation{NORTH, WEST, SOUTH, EAST};
	private Orientation ori;
	public enum Action{SUCK, RIGHT, LEFT, MOVE, START};
	private Action action;
	State prev = null;
	protected LinkedList<State> next = new LinkedList<State>();
	private int goal;
	
	//the heuristic function, f = g (from start to node)+h(heuristic to a goal(a goal here is a dirt cell))
	private int f = 0;
	private int g = 0;//cost from start to the node the state is in
	private int h = 0;
	
	public State(int pos, Orientation o, Action act, int g) {
		this.position = pos;
		this.ori = o;
		this.action = act;
		this.goal = g;
	}
	public int getPosition() {
		return position;
	}

	public Orientation getOri() {
		return ori;
	}

	public Action getAction() {
		return action;
	}

	public int getGoal() {
		return goal;
	}
	public void setGoal(int goal) {
		this.goal = goal;
	}
	
	public String print(){
		return "STATE *** Pos: "+position+", Orientation: "+ori+", Action: "+action+", Goal: "+goal+"\n";
	}
	
	public State getPrev(){
		return prev;
	}
	public void setPrev(State s){
		prev = s;
	}
	
	public LinkedList<State> getNext(){
		return next;
	}
	public void setNext(State s){
		next.addLast(s);
	}
	//heuristics
	public void setG(int newG){
		g = newG;
	}
	public void setH(int newH){
		h = newH;
	}
	public int getG(){
		return g;
	}
	public int getH(){
		return h;
	}
	public int getF(){
		f = g+h;
		return f;
	}
	
	
	
}
