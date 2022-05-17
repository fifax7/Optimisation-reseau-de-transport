package run;

import java.io.IOException;

import alg.AlgoFlow;
import alg.AlgoTruck;
import alg.CheckFlow;
import alg.CheckTruck;
import model.Flow;
import model.Graph;
import model.Instance;
import util.GraphFactory;
import util.InstanceFactory;
import view.MaxFlowView;

public class Main {

	
	public static void main(String[] args) throws IOException {
		
		// Construction d'une instance à partir d'un fichier au format txt
		Instance instance  = InstanceFactory.getFromFile("txt/Exemple.txt");
		
		// Affichage de l'instance au format dat
		System.out.println( instance.toDat() );
		
		//configuration de la vue graphique
		System.setProperty("org.graphstream.u1.renderer", "org.graphstream.u1.j2dviewer.320GraphRenderer");
		
		Graph g = GraphFactory.fromInstance(instance);
		MaxFlowView view = new MaxFlowView(g,15000);
		
		AlgoFlow solver = new AlgoFlow();
		AlgoTruck solver2 = new AlgoTruck(g.getNumberOfArcs(),instance);
		AlgoTruck solver3 = new AlgoTruck(g.getNumberOfArcs(),instance);
		// on calcule le flot maximum entre 0 et 11
		Flow x = solver.getSolution(g, 0, instance.n+instance.k+instance.m+1);
		
		// on vérifie que le flot est réalisable
		boolean valid = CheckFlow.isValid(x, g, 0, g.getNumberOfNodes()-1);
		System.out.println( "Le débit maximum du flot est : ") ;												
		g.listprint(x.getValue());
		
		//choix de la meilleur heuristique pour les camions
		boolean valid1= false;
		int sol= 0;
		int sol1=solver2.getSolutionTruck(g.getNumberOfArcs(), x);
		int sol2=80000;
		if(sol1<sol2) {
			sol=sol1;
			System.out.println( "solution 1 truck ") ;
			valid1= CheckTruck.isValid(x, g, solver2,1);
			System.out.println( "le nombre de camion ") ;
			g.listprint(solver2.getNumberTruckRoad());
			System.out.println("le cout total est : "+sol);
		}
		else {
			sol=sol2;
			System.out.println( "solution 2 truck ") ;
			valid1= CheckTruck.isValid(x, g, solver3,2);
			System.out.println( "le nombre de camion ") ;
			g.listprint(solver3.getNumberTruckRoad());
			System.out.println("le cout total est : "+sol);
		}
	
		
		
		//test de validité
		System.out.println( "Validité du flot : "+valid);
		System.out.println( "Validité du truck : "+valid1);
				
	}

}
