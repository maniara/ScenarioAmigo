package PatternGenerator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.picking.PickedInfo;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class PatternTreeViewer {
	
	VisualizationViewer<PTNode, PTEdge> vv;

	//VisualizationServer.Paintable rings;

	String root;

	TreeLayout<PTNode, PTEdge> treeLayout;

	RadialTreeLayout<PTNode, PTEdge> radialLayout;
	
	private static class VertexPaintTransformer implements Transformer<PTNode,Paint> {

        private final PickedInfo<PTNode> pi;

        VertexPaintTransformer ( PickedState<PTNode> pickedState ) { 
            super();
            if (pickedState == null)
                throw new IllegalArgumentException("PickedInfo instance must be non-null");
            this.pi = pickedState;
        }

		@Override
		public Paint transform(PTNode node) {
			Color p = null;
			if(node.isLeaf)
				p = Color.YELLOW;
			else if(node.isWeight)
				p=Color.GRAY;
			else
				p = Color.WHITE;
			
			return p;
		}

        /*@Override
        public Paint PatternGenerator.PatternTreeViewer.VertexPaintTransformertransform(Integer i) {
            Color p = null;
            //Edit here to set the colours as reqired by your solution
            if ( i % 2 == 0)
                p = Color.GREEN;
            else
                p =  Color.RED;
            //Remove if a selected colour is not required
            if ( pi.isPicked(i)){
                p = Color.yellow;
            }
            return p;
        }*/
    }
		
	private class PTNode
	{
		String parentString;
		String verb;
		int seq;
		String nodeString;
		boolean isLeaf;
		boolean isWeight;
		PTNode(String parentString, String verb, int seq)
		{
			this.parentString = parentString;
			this.verb = verb;
			this.seq = seq;
			nodeString = verb + "-" + seq;
		}
		PTNode(String parentString, String nodeString)
		{
			this.parentString = parentString;
			this.nodeString = nodeString;
			this.isWeight = true;
			this.isLeaf = false;
		}
		public void setIsLeaf(boolean flag)
		{
			isLeaf = flag;
		}
		@Override
		public String toString()
		{
			return this.nodeString;
		}
		
		public String getAllVerbString()
		{
			return parentString + this.nodeString;
		}
		@Override
		public boolean equals(Object o)
		{
			PTNode n = (PTNode) o;
			if((n.parentString + n.toString()).equals(this.parentString+this.toString()))
				return true;
			else
				return false;
		}
		@Override
		public int hashCode()
		{
			return this.nodeString.hashCode();
		}
	}
	
	private class PTEdge
	{
		
		PTNode from;
		PTNode to;
		//Double weight;

		PTEdge(PTNode from, PTNode to)
		{

			this.from = from;
			this.to = to;
			//weight = e;
		}
		
		//PTEdge(double e)
		//{
			//weight = e;
		//}
		
		@Override
		public String toString()
		{
			return "";
		}
		@Override
		public boolean equals(Object o)
		{
			PTEdge n = (PTEdge) o;
			String aStr = this.from.getAllVerbString()+":"+this.to.toString();
			String bStr = n.from.getAllVerbString()+":"+n.to.toString();
			if(aStr.toString().equals(bStr))
				return true;
			else
				return false;
		}
		@Override
		public int hashCode()
		{
			return ( from.getAllVerbString() + this.from+":"+this.to).hashCode();
		}
	}
	
	Forest<PTNode, PTEdge> graph;
			
    private void createTree(PatternFragmentSet pSet) {
    	
    	ArrayList<PatternFragment> list = new ArrayList<PatternFragment>(pSet);
    	Collections.sort(list);
   		for(PatternFragment pf : list)
   		{
   			String parentString = "";
   			PTNode from = null;;
   			for(int i=0;i<pf.getVerbList().size();i++)
   			{
   				String v = pf.getVerbList().get(i);
   				
   				if(i==0)
   				{
   					PTNode n = new PTNode(parentString,v,i);
   					graph.addVertex(n);
   					from = n;
   				}
   					
   				//else
   				else
   				{
   					PTNode t = new PTNode(parentString,v, i);
   					PTEdge edge = new PTEdge(from,t);
   					
   					//if(v.equals("u:choose"))
   						//System.out.println(t.getAllVerbString());

					graph.addEdge(edge, from, t);
					from = t;
					
					if((i+1) == pf.getVerbList().size())
					{
						t.setIsLeaf(true);
						parentString = parentString + (v+"-"+i)+":";
						PTNode endNode = new PTNode(parentString,"EOP: "+pf.getAdjustedWeight());
						PTEdge endEdge = new PTEdge(t,endNode);
						graph.addEdge(endEdge,t,endNode);
					}
					else
						t.setIsLeaf(false);
   				}
   				parentString = parentString + (v+"-"+i)+":";
   			}
   		}
    	
    	/*graph.addVertex(new PTNode("V0"));
    	graph.addEdge(new PTEdge(0.0), new PTNode("V0"), new PTNode("V1"));
    	graph.addEdge(new PTEdge(0.0), new PTNode("V0"), new PTNode("V2"));
    	graph.addEdge(new PTEdge(0.0), new PTNode("V0"), new PTNode("V2"));*/
    }
	
	public void draw()
	{
		if(PatternGenerator.PatternSetInstance.PatternSet == null)
			return;
		
		PatternFragmentSet patternSet = PatternGenerator.PatternSetInstance.PatternSet;
		graph = new DelegateForest<PTNode, PTEdge>();

        createTree(patternSet);
        
        treeLayout = new TreeLayout<PTNode, PTEdge>(graph);
        radialLayout = new RadialTreeLayout<PTNode, PTEdge>(graph);
        vv =  new VisualizationViewer<PTNode, PTEdge>(treeLayout, new Dimension(2000,600));
        vv.setBackground(Color.white);
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setVertexFillPaintTransformer(new VertexPaintTransformer(vv.getPickedVertexState()));
        // add a listener for ToolTips
        vv.setVertexToolTipTransformer(new ToStringLabeller());
        vv.getRenderContext().setArrowFillPaintTransformer(new ConstantTransformer(Color.lightGray));
        //rings = new Rings();

		JFrame frame = new JFrame("Scenario Patterns");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
        
      
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		
		DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
		 gm.setMode(DefaultModalGraphMouse.Mode.PICKING);
		
		vv.addKeyListener(gm.getModeKeyListener());
		vv.setGraphMouse(gm); 
	}
	
    class Rings implements VisualizationServer.Paintable {
    	
    	Collection<Double> depths;
    	
    	public Rings() {
    		depths = getDepths();
    	}
    	
    	private Collection<Double> getDepths() {
    		Set<Double> depths = new HashSet<Double>();
    		Map<PTNode,PolarPoint> polarLocations = radialLayout.getPolarLocations();
    		for(PTNode v : graph.getVertices()) {
    			PolarPoint pp = polarLocations.get(v);
    			depths.add(pp.getRadius());
    		}
    		return depths;
    	}

		public void paint(Graphics g) {
			g.setColor(Color.lightGray);
		
			Graphics2D g2d = (Graphics2D)g;
			Point2D center = radialLayout.getCenter();

			Ellipse2D ellipse = new Ellipse2D.Double();
			for(double d : depths) {
				ellipse.setFrameFromDiagonal(center.getX()-d, center.getY()-d, 
						center.getX()+d, center.getY()+d);
				Shape shape = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).transform(ellipse);
				g2d.draw(shape);
			}
		}

		public boolean useTransform() {
			return true;
		}
    }

}


