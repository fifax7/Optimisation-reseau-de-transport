package alg;	
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import model.Flow;
import model.Graph;
import view.MaxFlowView;

public class AlgoFlow {


	/**
	 * Maximum flow solver.
	 * 
	 * @author DR
	 *
	 */

		// Ajouter les champs nécessaires à la recherche de chaîne améliorante
		private LinkedList<Integer> q;
		private Integer[] pred;

		/**
		 * Maximum flow view.
		 */
		private MaxFlowView view;

		/**
		 * Get maximum flow on g.
		 * 
		 * @param g underlying graph
		 * @return maximum flow on g
		 */
		public Flow getSolution(Graph g) {
			return getSolution(g, 0, g.getNumberOfNodes() - 1);
		}

		/**
		 * Get maximum flow on g between source and sink.
		 * 
		 * @param g underlying graph
		 * @param s source
		 * @param t sink
		 * @return maximum flow on g between source and sink
		 */
		public Flow getSolution(Graph g, int s, int t) {

			// Initialisation
			Flow x = new Flow(g.getNumberOfArcs(),g.getNumberOfCapacity());

			// Compléter par la création de q et de pred
			q = new LinkedList<Integer>();
			pred = new Integer[g.getNumberOfNodes()];

			int[] val;
			val = new int[g.getNumberOfCapacity()];
			for(int j=0;j<val.length;j++)val[j]=0;

			// on crée une vue graphique
			view = new MaxFlowView(g, 1500);

			// etape principale
			for (int l=0; l<g.getNumberOfCapacity();l++) {
			//int l= 0;
			while (augmentingPath(x, g, s, t,l)) {

				// calcul de la capacité de la chaîne améliorante
				int delta = deltaPath(x, g, s, t,l);

				// mise à jour du flot sur la chaîne améliorante
				updateFlow(delta, x, g, s, t,l);

				// mise à jour de la vue graphique
				view.newFlow(x);
					val[l] += delta;
			}
		}
			x.setValue(val);
			return x;
		}

		/**
		 * Recherche une chaine améliorante (renvoie faux s'il n'y en a plus).
		 * 
		 * @param x flot actuel
		 * @param g graphe
		 * @param s source
		 * @param t puit
		 * @param l product
		 * @return false si le flot est optimal
		 */
		private boolean augmentingPath(Flow x, Graph g, int s, int t, int l) {
				// Initialiser les cases de pred à null, vider q et y mettre s
				pred = new Integer[g.getNumberOfNodes()];
				q = new LinkedList<Integer>();
				q.clear();
				q.add(s);

				// Etape principale
				while (!q.isEmpty() && pred[t] == null) {
					int i = q.removeFirst();
					for (int u : g.outGoingArcs(i)) {
						if(g.capacity(u).length!=2) {
								if (x.getFlow(u)[l] < g.capacity(u)[l+1]) {
									int j = g.head(u);
									if (pred[j] == null) {
										pred[j] = u;
									q.add(j);}
								}
							}
						else {
							
							if(x.getFlowSum(u)< g.capacity(u)[1]) {
								if(g.getHubs().contains(i)){
									if(x.getArcsFlowSum(g,i)< g.capacity(u)[1]) {
										int j = g.head(u);
									if (pred[j] == null) {
										pred[j] = u;
									q.add(j);}
								}
								}
								else {
								int j = g.head(u);
								if (pred[j] == null) {
									pred[j] = u;
								q.add(j);}
							}
							}
						}
					}
						
					// Recherche d'arc à l'envers
					for (int u : g.inGoingArcs(i)) {
						if(g.capacity(u).length!=2) {
								if (x.getFlow(u)[l] > 0) {
									int j = g.tail(u);
									if (pred[j] == null) {
										pred[j] = -u;
									q.add(j);}
								}
							}	
						else {
							if (x.getFlowSum(u) > 0) {
								int j = g.tail(u);
								if (pred[j] == null) {
									pred[j] = -u;
								q.add(j);}
					}
				}
			}

		}
				//System.out.println("la chaine pred ");
				//g.listprint(pred);
				// Changer le return pour indiquer si une chaîne a été trouvée
			return pred[t] != null;	
	}
			
	

		/**
		 * Calcul de l'augmentation possible sur une chaine améliorante.
		 * 
		 * @param x flot actuel
		 * @param g graphe
		 * @param s source
		 * @param t puit
		 * @param l product
		 * @return la valeur d'augmentation maximale pour la chaine
		 * 
		 */
		private int deltaPath(Flow x, Graph g, int s, int t,int l) {
			int delta;
			delta= Integer.MAX_VALUE;
			int i = t;
			while (i != s) {
				int u = pred[i];
				if (u >= 0) {
					if(g.capacity(u).length!=2) {
							delta = Math.min(delta, g.capacity(u)[l+1] - x.getFlow(u)[l]);
							//System.out.println("le delta "+delta+"//"+x.getFlow(u)[l]+"//"+g.capacity(u)[l+1]);
						i = g.tail(u);
						if(delta<0)System.out.println("coucouc4 ");
					}
					else {
						
						if(g.getHubs().contains(i)){
							delta =Math.min( Math.min(delta, g.capacity(u)[1] - x.getFlowSum(u)),g.capacity(u)[1] -x.getArcsFlowSum(g, i));
							i= g.tail(u);
						}
						else {
							
							delta = Math.min(delta, g.capacity(u)[1] - x.getFlowSum(u));
						i = g.tail(u);
						}	
					}
				} else {
					
							delta =  Math.min(delta, x.getFlow(-u)[l]);			
					i = g.head(-u);
				}
			}
			return delta;
		}

		/**
		 * Mise à jour du flot de delta sur la chaine améliorante courante.
		 * 
		 * @param delta variation de flot.
		 * @param x     flot actuel
		 * @param g     graphe
		 * @param s     source
		 * @param t     puit
		 * @param l     product
		 */
	private void updateFlow(int delta, Flow x, Graph g, int s, int t, int l) {
			int i = t;
			int newset = 0;
			while (i != s) {
				int u = pred[i];
				if (u >= 0) {
					newset=x.getFlow(u)[l] + delta;
					x.setFlow(u, l,newset);
					i = g.tail(u);
				} else {
					newset=x.getFlow(-u)[l] - delta;
					x.setFlow(-u, l,newset);
					i = g.head(-u);
				}
			}
		}
	}