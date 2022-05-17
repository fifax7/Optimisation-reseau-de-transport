package view;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;


/**
 * 
 * Vue pour le solveur de flow max.
 * Basé sur la bibliothèque http://graphstream-project.org
 *
 */
public class GraphView {

	// underlying graph view
	private final Graph graphView;


	// style
	protected String styleSheet = "node { text-alignment: center; size: 20px, 20px;   fill-color: white; stroke-mode: plain;    }"
			+ "edge.green {" + "	fill-color: green; size: 2px;" + "}" + "edge.red {" + "fill-color: red; size: 2px;" + "}"
			+ "edge.black {" + "fill-color: black; size: 2px;" + "}"
			+ "edge { text-background-mode: plain; } sprite {fill-mode: none; }"
			+ "node.white { fill-color: white; text-color: black; }"
			+ "node.red { fill-color: red; text-color: white; }";

	// sprite manager for styling graph view
	private SpriteManager sman;

	/**
	 * Creates a graph view from graph model g.
	 * @param g graph model
	 */
	public GraphView(model.Graph g) {
		graphView = new SingleGraph("");	
		sman = new SpriteManager(graphView);
		for (int i = 0; i < g.getNumberOfNodes(); i++) {
			Node n = graphView.addNode(i + "");
			n.addAttribute("ui.label", n.getId());
			Sprite s = sman.addSprite(i + "");
			s.attachToNode(n.getId());
			s.setPosition(Units.PX, 20, 0, 270);
		}
		for (int u = 0; u < g.getNumberOfArcs(); u++) {
		  	Edge e = graphView.addEdge(u + "", g.tail(u), g.head(u), true);
			e.addAttribute("ui.label", "u = "+u+", capacity=" + g.capacity(u)[1]);
		}

		graphView.addAttribute("ui.stylesheet", styleSheet);
		graphView.display();
		
	}


}
