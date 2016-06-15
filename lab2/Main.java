package lab2;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.internal.chartpart.Chart;

public class Main {
	final static double R=10;
	final static double m=2;
	final static double N=2;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args){
		List<Double> xlist = new ArrayList<Double>();
		List<Double> ylist = new ArrayList<Double>();
		for(double t = 0; t <= N*Math.PI; t+=0.0001){
			xlist.add(R*(Math.cos(t)-Math.pow(Math.cos(t), 3)/m));
			ylist.add(R*(Math.pow(Math.sin(t), 3)/m));
		}
		double[] x = new double[xlist.size()];
		double[] y = new double[ylist.size()];
		
		for(int i = 0; i < xlist.size(); i++){
			x[i] = xlist.get(i);
			y[i] = ylist.get(i);
		}
		
	    Chart chart = QuickChart.getChart(
	    		"x=R*(cos(t)-((cos(t))^3)/m) \n y=R*(((sin(t))^3)/m)",
	    		"X",
	    		"Y",
	    		"y(t), x(t)",
	    		x,
	    		y);
	 
	    // Show it
	    new SwingWrapper(chart).displayChart();
	}
}