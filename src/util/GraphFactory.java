package util;
import model.Graph;
import model.Instance;

public class GraphFactory {
	
	public static Graph fromInstance(Instance instance) {
		
		Graph g = new Graph(instance.n+instance.k+instance.m+2, instance.r);
		
		// on crée les arcs de la source aux entreprots
		for (int e=1; e<= instance.n; e++) {
			g.addArc(0, e,instance.alpha[e],false);
		}

			
		//on crée les arcs qui sortent des entrepots
		
		for(int e=1; e<= instance.n; e++) {
			for(int j : instance.succE[e]) {
				int[] a = {0,Integer.MAX_VALUE};	
				g.addArc(e, j, a, false);
			}
		}
		
		// on cree les arcs qui sortent des hubs
		for (int e=1; e<= instance.k; e++) {
			for(int j : instance.succH[e]) {
				int[] a = {0,instance.mu[e]};
				g.addArc(e+instance.n, j, a,true);
			}
		}
		//on cree les arcs des clients vers le puit
		for (int e=1; e<= instance.m; e++) {
			g.addArc(e+instance.n+instance.k,instance.n+instance.k+instance.m+1, instance.beta[e], false);
		}
		return g;
		
		
	}

}
