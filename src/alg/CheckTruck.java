package alg;

import model.Flow;
import model.Graph;

public class CheckTruck {
	public static boolean isValid(Flow flow, Graph g, AlgoTruck y,int i) {
 		int balance=0;
		for(int u=0;u<g.getNumberOfArcs();u++)balance+=flow.getFlowSum(u);
		for(int u=0;u<y.getNumberTruckRoad().length;u++)balance-= y.getNumberTruckRoad()[u]*y.gettrucklist()[u];
		if(balance>0)return false;
		return true;

}}