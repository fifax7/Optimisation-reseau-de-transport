package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Flow for a given transportation network.
 * @author DR
 */
public class Flow {

	/**
	 * Flow on arcs.
	 */
	private ArrayList<int[]> flow;
	
	/**
	 * Value of flow.
	 */
	private int[] value;
	
	/**
	 * Get the flow value.
	 * @return the value 
	 */
	public int[] getValue() {
		return value;
	}

	/**
	 * Set the flow value.
	 * @param value the value to set
	 */
	public void setValue(int[] value) {
		this.value = value;
	}

	/**
	 * Builds a null flow for a graph with m arcs.
	 * @param m number of arcs
	 */
	public Flow(int m,int r) {
		value = new int[r];
		for(int i=0;i<r;i++)value[i]=0;
		flow = new ArrayList<int[]>(m);
		for(int i=0;i<m;i++) {
			flow.add(i, value);		
			}
		}
	
	/**
	 * Set a flow on u.
	 * @param u arc
	 * @param val flow value
	 */
	public void setFlow(int u, int l, int newset) {
		int[] vallist = new int[flow.get(u).length];
		for(int j=1;j<vallist.length;j++)vallist[j]=0;
		for(int i=0;i<flow.get(u).length;i++) {
			if(i==l)vallist[i]=newset;
			else vallist[i]= flow.get(u)[i];
		}
		flow.set(u,vallist);
	}
 
	/**
	 * Returns flow on u.
	 * @param u arc
	 * @return flow on u
	 */
	public int[] getFlow(int u) {
		return flow.get(u);
	}
	/**
	 * Returns sum of flow on u.
	 * @param u arc
	 * @return  sum of flow on u
	 */
	public int getFlowSum(int u) {
		
		int sum= 0;
		for (int e=0; e<this.getFlow(u).length;e++) {
			sum+=this.getFlow(u)[e];
		}
		return sum;	}
	
	/**
	 * Returns sum of  arcs flow on i.
	 * @param g graph
	 * @param i node
	 * @return  sum of  arcs flow on i
	 */
	public int getArcsFlowSum(Graph g,int i) {
		int flowArcsSum =0;
		for (int a : g.outGoingArcs(i)) {
			flowArcsSum += getFlowSum(a);
		}
		return flowArcsSum;
	}
	   
	
	
}
