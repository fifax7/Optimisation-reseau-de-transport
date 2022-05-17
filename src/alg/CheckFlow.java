package alg;

import model.Flow;
import model.Graph;

/**
 * Check if a flow is feasible on a transportation network.
 * @author DR
 *
 */
public class CheckFlow {
	
	/**
	 * Check if given flow is valid for graph g.
	 * @param flow flow
	 * @param g graph
	 * @param source source
	 * @param sink sink
	 * @return true if the flow is valid
	 */
	public static boolean isValid(Flow flow, Graph g, int source, int sink) {

		
		// Capacity constraints
		for (int u=0;u<g.getNumberOfArcs();u++) {
			if(g.capacity(u).length!=2) {
				for(int i=0;i<g.getNumberOfCapacity();i++){
				if (flow.getFlow(u)[i]<0) {
					System.out.println( "CheckFlow.isValid false : flow < 0 on arc "+u);
					return false;
				}
					else if (flow.getFlow(u)[i]>g.capacity(u)[i+1]) {
						System.out.println( "CheckFlow.isValid false : flow > capacity on arc "+u);
						return false;
					}
			}
			
			}
			else {
				if (flow.getFlowSum(u)<0) {
					System.out.println( "CheckFlow.isValid false : flow < 0 on arc "+u);
					return false;
				}
					else if (flow.getFlowSum(u)>g.capacity(u)[1]) {
						System.out.println( "CheckFlow.isValid false : flow > capacity on arc "+u);
						return false;
			}
		}
	}
		
		// Balance constraints
		for (int i=0;i<g.getNumberOfNodes();i++)
			if (i!=source && i!=sink) {
				int balance = 0;
				for (int u : g.outGoingArcs(i)) {
					balance+= flow.getFlowSum(u);
				}
				for (int u : g.inGoingArcs(i)) {
					balance-= flow.getFlowSum(u);		
				}
				if (balance!=0) {
					
					System.out.println( "CheckFlow.isValid false : balance not null on node "+i+" la balance "+ balance);
					return false;
				}
			}
		// Value of flow
		int[] value;
		value = new int[g.getNumberOfCapacity()];
		for(int j=0;j<value.length;j++)value[j]=0;
		
		for (int u : g.outGoingArcs(source)) {
			for(int j=0;j<g.getNumberOfCapacity();j++){
				value[j]+= flow.getFlow(u)[j];
			}
		}
			
		for(int j=0;j<g.getNumberOfCapacity();j++){
			if(value[j]!=flow.getValue()[j]) {
			System.out.println( "CheckFlow.isValid false : flow value is not valid");
			return false;
			}
		}
		//reset value of flow
		for(int j=0;j<value.length;j++)value[j]=0;

		for (int u : g.inGoingArcs(sink)) {
			for(int j=0;j<g.getNumberOfCapacity();j++){
				value[j]+= flow.getFlow(u)[j];
			}
		}
		for(int j=0;j<g.getNumberOfCapacity();j++){
			if(value[j]!=flow.getValue()[j]) {
			System.out.println( "CheckFlow.isValid false : flow value is not valid");
			return false;
		}
		}
		return true;
	}
}

