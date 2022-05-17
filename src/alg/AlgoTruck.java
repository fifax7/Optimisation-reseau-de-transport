package alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import model.*;


public class AlgoTruck {
	// map of truck on arc
	private int[][]road;
	// list order of truck's index
	private ArrayList<Integer>indextrucklist;
	// list order of truck's index
	private ArrayList<Integer>indextrucklist2;
	// capacity of truck
	private int[] trucklist;
	// number of truck
	private int[] numbertruck;
	// cost of truck
	private int[] costtruck;
	// total cost
	private int cost;
	// list of ratio
	private float[]ratio;
	// list of truck on road
	private int[]numberTruckRoad;

	public AlgoTruck(int u,Instance instance){
		cost=0;
		trucklist = instance.kappa;
		numbertruck= instance.eta;
		costtruck= instance.gamma;
		road= new int[u][instance.kappa.length];
		numberTruckRoad= new int[instance.kappa.length];
		for(int i=0;i<numberTruckRoad.length;i++)numberTruckRoad[i]=0;
		
		// create a list to order truck's capacity
		Integer[] trucklist1 = new Integer[instance.kappa.length];
		int i = 0;
		for (int value : instance.kappa)trucklist1[i++] = Integer.valueOf(value);
		Arrays.sort(trucklist1, Collections.reverseOrder());
		// give index of truck in order list
		indextrucklist= new ArrayList<Integer>();
		for(int k:trucklist1)for(int j=0;j<instance.kappa.length;j++)if(k==instance.kappa[j])indextrucklist.add(j);
		// create a list to oder truck with ratio of capacity and price
		ArrayList<Float> trucklist2 = new ArrayList<Float>();
		int b = 0;
		for(int a=0;a<instance.kappa.length;a++)trucklist2.add((float)0);
		for (int value : instance.kappa) {
			trucklist2.set(b, (float)Integer.valueOf(value)/costtruck[b]);
			b++;
		}
		Collections.sort(trucklist2);
		indextrucklist2= new ArrayList<Integer>();
		for(float k:trucklist2)for(int j=0;j<instance.kappa.length;j++)if(k==(float)instance.kappa[j]/costtruck[j])indextrucklist2.add(j);
		//create ratio of 2 trucks with ratio of capacity and price
		ratio= new float[instance.kappa.length];
		for(int j=0;j<indextrucklist.size()-1;j++) {
			float truck1=(float)costtruck[j]/trucklist[j];
			float truck2=(float)costtruck[indextrucklist.get(j+1)]/trucklist[indextrucklist.get(j+1)];
			ratio[j]=(float)truck1/truck2;
		}
		
	}
	
	public int getSolutionTruck(int u,Flow x) {
		cost =0;
		for(int j=0;j<u;j++) {
			int fret = x.getFlowSum(j);
			
			for(int l:indextrucklist) {
				//implement truck on arcs from the biggest to the smallest
				if(trucklist[l]<fret&& numbertruck[l]!=0){
						int truck=fret/trucklist[l];
						truck=Math.min(truck, numbertruck[l]);
						fret-= truck*trucklist[l];
						numbertruck[l]-= truck;
						road[j][l]+= truck;
						numberTruckRoad[l]+= truck;
						cost+=truck*costtruck[l];
					
				}
				//implement truck on arcs from the biggest to the smallest with rasio
				if(trucklist[l]*ratio[l]<fret&& numbertruck[l]!=0&&l!=indextrucklist.get(indextrucklist.size()-1)){
						int truck=fret/trucklist[l]+1;
						truck=Math.min(truck, numbertruck[l]);
						truck=Math.min(truck, numbertruck[l]);
						fret=Math.max(fret-truck*trucklist[l], 0);
						numbertruck[l]-= truck;
						road[j][l]+= truck;
						numberTruckRoad[l]+= truck;
						cost+=truck*costtruck[l];
				}
			}
			//implement truck for rest of fret from the smallest to the biggest
			if(fret>0) {
				for(int l=indextrucklist.size();l<0;l--) {
					if(numbertruck[indextrucklist.get(l)]!=0) {
						int truck= fret/trucklist[indextrucklist.get(l)]+1;
						truck=Math.min(truck, numbertruck[indextrucklist.get(l)]);
						fret=Math.max(fret-truck*trucklist[indextrucklist.get(l)], 0);
						numbertruck[indextrucklist.get(l)]-= truck;
						road[j][indextrucklist.get(l)]+= truck;
						numberTruckRoad[indextrucklist.get(l)]+= truck;
						cost+=truck*costtruck[indextrucklist.get(l)];
						
					}
				}
			}}
		return cost;
		}
			 
				
		public int getSolutionTruck2(int u,Flow x) {
			cost =0;
			for(int j=0;j<u;j++) {
				int fret = x.getFlowSum(j);
				//implement truck from best ratio to the worst ratio
				for(int l:indextrucklist2) {
					if(trucklist[l]<fret&& numbertruck[l]!=0){
							int truck=fret/trucklist[l];
							truck=Math.min(truck, numbertruck[l]);
							fret-= truck*trucklist[l];
							numbertruck[l]-= truck;
							road[j][l]+= truck;
							numberTruckRoad[l]+= truck;
							cost+=truck*costtruck[l];
						
					}
				}
				if(fret>0) {
					//implement truck on arcs from the biggest to the smallest with ratio
					for(int l=indextrucklist.size();l<0;l--) {
						if(numbertruck[indextrucklist.get(l)]!=0) {
							int truck= fret/trucklist[indextrucklist.get(l)]+1;
							truck=Math.min(truck, numbertruck[indextrucklist.get(l)]);
							fret=Math.max(fret-truck*trucklist[indextrucklist.get(l)], 0);
							numbertruck[indextrucklist.get(l)]-= truck;
							road[j][indextrucklist.get(l)]+= truck;
							numberTruckRoad[indextrucklist.get(l)]+= truck;
							cost+=truck*costtruck[indextrucklist.get(l)];
							
						}
					}
				}
				 
					
		}
			return cost;
	}



	public int getCost() {
		return cost;
		
	}


	public int[] getNumberTruckRoad() {
		return numberTruckRoad;
	}
	public int[] gettrucklist() {
		return trucklist;
	}
}
