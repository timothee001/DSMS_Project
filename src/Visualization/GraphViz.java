package Visualization;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.picking.PickedInfo;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class GraphViz {

    public Graph<String, String> g;
    public GraphViz() throws IOException {     

         this.g = readNet("SmallTown.net");
    }

    private static class VertexPaintTransformer implements Transformer<String,Paint> {

        private final PickedInfo<String> pi;

        VertexPaintTransformer ( PickedInfo<String> pi ) { 
            super();
            if (pi == null)
                throw new IllegalArgumentException("PickedInfo instance must be non-null");
            this.pi = pi;
        }

        @Override
        public Paint transform(String i) {
            Color p = Color.RED;
            /*
            if ( i % 2 == 0)
                p = Color.GREEN;
            else
                p =  Color.RED;*/

            if ( pi.isPicked(i)){
                p = Color.yellow;
            }
            return p;
        }
    }

    private static class VertexLabelTransformer implements Transformer<String,String>{
        private final PickedInfo<String> pi;

        public VertexLabelTransformer( PickedState<String> pickedState ){
            this.pi = pickedState;
        }

        @Override
        public String transform(String t) {
            if (pi.isPicked(t))
                return t.toString();
            else
              return "";
        }
    }


    @SuppressWarnings("unchecked")
    public static <Paint> void main(String[] args) throws IOException {
        GraphViz sgv = new GraphViz(); 
        Layout<String, String> layout = new FRLayout(sgv.g);
        layout.setSize(new Dimension(2000,2000));  
        VisualizationViewer<String, String> vv = new VisualizationViewer<String, String>(layout);

        Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {

            @Override
            public Paint transform(String i) {
               /* if ( i % 2 == 0)
                    return (Paint) Color.GREEN;
                else
                    return  (Paint) Color.RED;
                    */
            	 return  (Paint) Color.RED;
            }
        };  

        vv.setPreferredSize(new Dimension(2000,2000));
        vv.getRenderContext().setVertexFontTransformer(new Transformer<String,Font>() {
            public Font transform(String node) {
                Font font = new Font("Calibri", Font.BOLD, 100);
                return font;
            }
        });
        vv.getRenderContext().setVertexLabelRenderer(new DefaultVertexLabelRenderer(Color.BLUE));
        vv.getRenderContext().setVertexLabelTransformer(new VertexLabelTransformer(vv.getPickedVertexState()));


        vv.getRenderContext().setEdgeDrawPaintTransformer(new ConstantTransformer(Color.white));
        vv.getRenderContext().setEdgeStrokeTransformer(new ConstantTransformer(new BasicStroke(2.5f)));

        vv.getRenderContext().setVertexFillPaintTransformer(new VertexPaintTransformer(vv.getPickedVertexState()));

        DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        graphMouse.setMode(edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(graphMouse);

        vv.setBackground(Color.gray);

        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);


        JFrame frame = new JFrame("Projet Algo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv); 
        frame.pack();
        frame.setVisible(true);       
    }

    public static Graph<String, String> readNet(String string) throws IOException {
    	
    	
    	TreeSet<Edge> allCouplesSort = new TreeSet<Edge>();		
		BufferedReader br = new BufferedReader(new FileReader("inputGraph.txt"));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();		  
		        if(line!=null){
		        	 
				        String [] tab = line.split(",");
				        Edge c= new Edge();
				        c.first=tab[0];
				        c.second = tab[1];
				        c.count = Integer.parseInt(tab[2]);
				       //System.out.println(c);
				        allCouplesSort.add(c);
		        }
		      
		        
		    }
		    String everything = sb.toString();
		   
		} finally {
		    br.close();
		}
		
        Graph<String, String> g2 = new SparseMultigraph<String, String>();


		for(int i =0;i<allCouplesSort.size();i++){
			
	    		Edge c=allCouplesSort.pollLast();
	    		g2.addEdge(c.first+"*"+c.second, c.first,c.second,EdgeType.UNDIRECTED);
	    	
		}
		
 
        return  g2;

    }
}