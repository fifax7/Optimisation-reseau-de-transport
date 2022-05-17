package view;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import model.Flow;

/**
 * 
 * Vue pour le solveur de flow max.
 * Basé sur la bibliothèque http://graphstream-project.org
 *
 */
public class MaxFlowView {

	// underlying graph view
	private final Graph graphView;

	// underlying graph model
	private model.Graph graphModel;

	// given flow
	private Flow flow;

	// delay between update
	private int delay;

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
	 * @param delay delay between updates
	 */
	public MaxFlowView(model.Graph g, int delay) {
		graphView = new SingleGraph("");
		this.delay = delay;
		flow = new Flow(g.getNumberOfArcs(),g.getNumberOfCapacity());
		this.graphModel = g;
		
		
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
			e.addAttribute("ui.label", "0|" + g.capacity(u)[1]);
		}

		graphView.addAttribute("ui.stylesheet", styleSheet);
		graphView.display();
		
	}

	/**
	 * Changes flow with new value. 
	 * @param flow new flow
	 */
	private void setFlow(Flow flow) {
		for (int u = 0; u < graphModel.getNumberOfArcs(); u++) {
			for(int l=0;l<graphModel.getNumberOfCapacity();l++) {
			Edge e = graphView.getEdge(u);
			if (flow.getFlowSum(u) == this.flow.getFlowSum(u))
				e.setAttribute("ui.class", "black");
			else if (flow.getFlowSum(u) > this.flow.getFlowSum(u))
				e.setAttribute("ui.class", "green");
			else
				e.setAttribute("ui.class", "red");
			this.flow.setFlow(u, l,flow.getFlow(u)[l]);
			e.addAttribute("ui.label", flow.getFlow(u)[0] + "|" + graphModel.capacity(u)[1]);
		}}
	}

	/**
	 * Update current flow with new flow.
	 * @param flow new flow
	 */
	public void newFlow(Flow flow) {
		setFlow(flow);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setFlow(flow);
	}

	/**
	 * Changes node color.
	 * @param i node id
	 * @param color new color
	 */
	public void setNodeColor(int i, String color) {
		Node n = graphView.getNode(i);
		n.setAttribute("ui.class", color);
	}

}
