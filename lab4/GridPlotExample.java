package lab4;

import javax.swing.*;
import org.math.plot.*;
 
import static java.lang.Math.*;

 
public class GridPlotExample {
	
	public final static double minX = -10.0;
	public final static double maxX = 10.0;
	public final static double minY = -10.0;
	public final static double maxY = 10.0;
	public final static double step = 1.0;
	
	 public GridPlotExample(){
        		GridChart(); 
                //new MatrixChart();
        }
 
        
 
        // function definition: z=cos(PI*x)*sin(PI*y)
        public static double f(double x, double y) {
                double z = sin(sqrt(pow(x,2)+pow(y,2)));
                return z;
        }
        
        public static double[] FonX(double[] x) {
        	double[] z = new double[x.length];
            for (int i = 0; i < x.length; i++){
            	z[i] = sin(sqrt(pow(x[i],2)));
            	System.out.println(z[i]);
            }               
            return z;
        }
        
        public static double[] FonY(double[] y) {
        	double[] z = new double[y.length];
            for (int i = 0; i < y.length; i++){
            	z[i] = sin(sqrt(pow(y[i],2)));
            	System.out.println(z[i]);
            }               
            return z;
        }
 
        // grid version of the function
        public static double[][] f(double[] x, double[] y) {
                double[][] z = new double[y.length][x.length];
                for (int i = 0; i < x.length; i++)
                        for (int j = 0; j < y.length; j++)
                                z[j][i] = f(x[i], y[j]);
                return z;
        }

   public static double[] increment(double start, double step, double end) {
      double range = end - start;
      int steps = (int)(range / step);
      double[] rv = new double[steps];
      for (int i = 0; i<steps; i++) {
         rv[i] = start + ((step / range) * i);
      }
      return rv;
   }
   
   public static void GridChart(){
	   // define your data
       double[] x = increment(minX, step, maxX);
       double[] y = increment(minY, step, maxY);
       double[][] z1 = f(x,y);
       // create your PlotPanel (you can use it as a JPanel) with a legend at SOUTH
       Plot3DPanel plot = new Plot3DPanel("SOUTH");
       // add grid plot to the PlotPanel
       plot.addGridPlot("z=sin(sqrt(x^2+y^2))", x, y, z1);
       // put the PlotPanel in a JFrame like a JPanel
       JFrame frame = new JFrame("Lab4 - Table and plot");
       frame.setSize(600, 600);
       frame.setContentPane(plot);
       frame.setVisible(true);
   }
	
}