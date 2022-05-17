package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Directed graph with arc capacities.
 * @author DR
 * 
 */
public class Graph {

	/**
	 * Number of node.
	 */
	private int n;
	
	/**
	 * Number of arcs.
	 */
	private int m;
	/**
	 * Number of product
	 */
	private int type;
	private List<Integer> tail;
	private List<Integer> head;
	private ArrayList<List<Integer>> outGoingArcs;
	private ArrayList<List<Integer>> inGoingArcs;
	private ArrayList<int[]> capacity;
	private ArrayList<Integer> listHubs;
	/**
	 * Builds an empty graph.
	 */
	public Graph() {
		n = m = 0;
		tail = new ArrayList<Integer>();
		head= new ArrayList<Integer>();
		outGoingArcs= new ArrayList<List<Integer>>();
		inGoingArcs=  new ArrayList<List<Integer>>();
		capacity = new ArrayList<int[]>();
	}
	
	/**
	 * Builds a graph with n nodes.
	 * @param n number of nodes
	 */
	public Graph(int n, int numberType) {
		this.n = n;
		m = 0;
		type= numberType;
		tail = new ArrayList<Integer>();
		head= new ArrayList<Integer>();
		outGoingArcs= new ArrayList<List<Integer>>(n);
		inGoingArcs= new ArrayList<List<Integer>>(n);
		capacity = new ArrayList<int[]>();
		listHubs = new ArrayList<Integer>();
		for(int i =0;i<n; i++) {
			outGoingArcs.add(new ArrayList<Integer>());
			inGoingArcs.add(new ArrayList<Integer>());
		}
		
	}
	
	/**
	 * Adds a node.
	 */
	public void addNode() {
		outGoingArcs.add(new ArrayList<Integer>());
		inGoingArcs.add(new ArrayList<Integer>());
		n++;
	}
	
	/**
	 * Adds an arc (i, j) with capacity capacity.
	 * @param i tail of arc
	 * @param j head of arc
	 * @param capacity capacity of arc
	 * @param hubs true if arcs on hubs
	 */
	public void addArc( int i, int j, int[] capacity, boolean hubs ) {
		tail.add(i);
		head.add(j);
		this.capacity.add(capacity);
		outGoingArcs.get(i).add(m);
		inGoingArcs.get(j).add(m);
		if(hubs)listHubs.add(i);
		m++;
	}
	
	/**
	 * Return out going arcs from node i.
	 * @param i node
	 * @return out going arcs from node i
	 */
	public List<Integer> outGoingArcs(int i) {
		return outGoingArcs.get(i);
	}
	
	/**
	 * Return in going arcs to node i.
	 * @param i node
	 * @return in going arcs from to i
	 */
	public List<Integer> inGoingArcs(int i) {
		return inGoingArcs.get(i);
	}
	
	/**
	 * Return head of arc u.
	 * @param u an arc
	 * @return head of arc u
	 */
	public int head(int u) {
		return head.get(u);
	}
	
	/**
	 * Return tail of arc u.
	 * @param u an arc
	 * @return tail of arc u
	 */
	public int tail(int u) {
		return tail.get(u);
	}
	
	/**
	 * Return capacity of arc u.
	 * @param u an arc 
	 * @return capacity of arc u
	 */
	public int[] capacity(int u) {
		return capacity.get(u);
	}
	
	/**
	 * Return number of nodes.
	 * @return number of nodes
	 */
	public int getNumberOfNodes() {
		return n;
	}
	
	/**
	 * Return number of arcs.
	 * @return number of arcs
	 */
	public int getNumberOfArcs() {
		return m;
	}
	/**
	 * Return number of capacity on arcs.
	 * @return number of capacity on arcs
	 */
	public int getNumberOfCapacity() {
		return type;
	}
	/**
	 * Return list of arcs of hubs
	 * @return list of arcs of hubs
	 */
	public ArrayList<Integer> getHubs() {
		return listHubs;
	}
	/**
	 * print list
	 */
	public void listprint(int[] list) {
		System.out.print("[ ");
		for(int i=0; i<list.length;i++)System.out.print(list[i]+" ,");
		System.out.println(" ]");
	}
	public void listprint(Integer[] list) {
		System.out.print("[ ");
		for(int i=0; i<list.length;i++)System.out.print(list[i]+" ,");
		System.out.println(" ]");
	}
	
}