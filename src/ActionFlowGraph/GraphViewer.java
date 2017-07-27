package ActionFlowGraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.samples.SimpleGraphDraw;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class GraphViewer {
	
	public static void draw(ActionFlowGraph AFGraph)
	{
		/*DirectedSparseMultigraph<String, Double> graph = new DirectedSparseMultigraph<String, Double>();
		for(AFGNode node : AFGraph.getNodeList()){
			System.out.println(node.toString());
			graph.addVertex(node.toString());}
		for(AFGEdge edge : AFGraph.getEdgeList()){
			System.out.println(edge.toString());
			graph.addEdge(edge.getWeight(),edge.getFromNode().toString(),edge.getToNode().toString());
		}*/
		
		DirectedSparseMultigraph<AFGNode, AFGEdge> graph = new DirectedSparseMultigraph<AFGNode, AFGEdge>();
		
		/*for(AFGNode node : AFGraph.getNodeList()){
			//System.out.println(node.toString());
			graph.addVertex(node);}*/
		for(AFGEdge edge : AFGraph.getEdgeList()){
			//System.out.println(edge.getIdString());
			graph.addVertex(edge.getFromNode());
			graph.addVertex(edge.getToNode());
			graph.addEdge(edge,edge.getFromNode(),edge.getToNode());
		}
		
		SimpleGraphDraw sgv = new SimpleGraphDraw();
		Layout<AFGNode, AFGEdge> layout= new CircleLayout(graph);
		layout.setSize(new Dimension(1000,800));
		
		final VisualizationViewer<AFGNode,AFGEdge> vv =  new VisualizationViewer<AFGNode,AFGEdge>(layout);
		vv.setPreferredSize(new Dimension(1050,850)); //Sets the viewing area size
		
		Transformer<AFGNode,Paint> vertexPaint = new Transformer<AFGNode,Paint>() {
			 public Paint transform(AFGNode i) {
				 i.toString();
				 return Color.GREEN;
			 }
		};
		
		float dash[] = {10.0f};
		final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		Transformer<AFGEdge, Stroke> edgeStrokeTransformer = new Transformer<AFGEdge, Stroke>() {
			public Stroke transform(AFGEdge s) {
				//String W = s.getOccurrencesByMax()+","+s.getRelatedInterconectivity();
				//s.getWeight();
				//s.getOccurCount();
				return edgeStroke;
			}
		};
		
		Transformer<AFGEdge, Paint> edgePaint = new Transformer<AFGEdge, Paint>() {
			@Override
			public Paint transform(AFGEdge arg0) {
				return Color.GRAY;
			}
		};

		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
		vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		
		DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
		 gm.setMode(DefaultModalGraphMouse.Mode.PICKING);
		
		vv.addKeyListener(gm.getModeKeyListener());
		vv.setGraphMouse(gm); 

		
		/*PluggableGraphMouse gm = new PluggableGraphMouse();
		gm.add(new TranslatingGraphMousePlugin(MouseEvent.BUTTON1_MASK));*/
		

		JFrame frame = new JFrame("Scenario Flow Graph");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	private static class EdgeFactory implements Factory<Number> {
		int i = 0;

		public Number create() {
			return i++;
		}
	}

	private static class VertexFactory implements Factory<Number> {
		int i = 0;

		public Number create() {
			return i++;
		}

	}
}


