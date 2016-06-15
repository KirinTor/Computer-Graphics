package lab4;

import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class MatrixChart{
 	   	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MatrixChart(){
		
    	List charts = new ArrayList();
		charts.add(getLineChart(1));
		charts.add(getLineChart(2));
		charts.add(getPieChart(1));
		charts.add(getBarChart(1));
		charts.add(getBarChart(2));
		charts.add(getPieChart(2));
		JFrame sw = new SwingWrapper(charts).displayChartMatrix();
		sw.setTitle("Lab4 - Charts");
	}

		public XYChart getLineChart(int i){
			
			List<Double> xlist = new ArrayList<Double>();
			List<Double> ylist = new ArrayList<Double>();
			List<Double> zlist = new ArrayList<Double>();

			for(double t = -10; t < 10; t+=0.0001){
	    		   xlist.add(t);
	    		   ylist.add(t);
	    		   zlist.add(sin(sqrt(pow(t,2))));
	    	}
			
			double[] x = new double[xlist.size()];
			double[] y = new double[ylist.size()];
			double[] z = new double[zlist.size()];
  	   
	    	for(int j = 0; j < xlist.size(); j++){
	    		   x[j] = xlist.get(j);
	    		   y[j] = ylist.get(j);
	    		   z[j] = zlist.get(j);
	    	}
	    	XYChart chart = new XYChartBuilder().width(500).height(300).build();
		    chart.getStyler().setYAxisMin(-1);
		    chart.getStyler().setYAxisMax(1);
		    chart.getStyler().setLegendPosition(LegendPosition.InsideSW);
			switch (i){
			case 1:
				/*==============XOZ=============*/
				chart.setTitle("LineChart XOZ");
			    chart.getStyler().setXAxisMin(-10);
			    chart.getStyler().setXAxisMax(10);
			    chart.setXAxisTitle("X");
			    chart.setYAxisTitle("Z");
			    
			    XYSeries seriesXOZ = chart.addSeries("z(x)", x, z);
			    seriesXOZ.setMarker(SeriesMarkers.NONE);
			    seriesXOZ.setLineColor(XChartSeriesColors.RED);
			    seriesXOZ.setLineStyle(SeriesLines.SOLID);
			    break;
			case 2:
				/*==============YOZ=============*/
				chart.setTitle("LineChart YOZ");
				chart.getStyler().setXAxisMin(-10);
			    chart.getStyler().setXAxisMax(10);
			    chart.setXAxisTitle("Y");
			    chart.setYAxisTitle("Z");
			    
			    XYSeries seriesYOZ = chart.addSeries("z(y)", x, z);
			    seriesYOZ.setMarker(SeriesMarkers.NONE);
			    seriesYOZ.setLineColor(XChartSeriesColors.GREEN);
			    seriesYOZ.setLineStyle(SeriesLines.SOLID);
			    break;
			}
			return chart;			    
		}
		
		public CategoryChart getBarChart(int i){
			
			List<Double> xlist = new ArrayList<Double>();
			List<Double> ylist = new ArrayList<Double>();
			List<Double> zlist = new ArrayList<Double>();

			for(double t = -10; t <= 10; t+=1){
	    		   xlist.add(t);
	    		   ylist.add(t);
	    		   zlist.add(sin(sqrt(pow(t,2))));
	    	}
			
			double[] x = new double[xlist.size()];
			double[] y = new double[ylist.size()];
			double[] z = new double[zlist.size()];
  	   
	    	for(int j = 0; j < xlist.size(); j++){
	    		   x[j] = xlist.get(j);
	    		   y[j] = ylist.get(j);
	    		   z[j] = zlist.get(j);
	    	}
	    	CategoryChart chart = new CategoryChartBuilder().width(500).height(300).build();
		    chart.getStyler().setYAxisMin(-1);
		    chart.getStyler().setYAxisMax(1);
		    chart.getStyler().setLegendPosition(LegendPosition.InsideSW);
		    chart.getStyler().setPlotContentSize(1.0);
			switch (i){
			case 1:
				/*==============XOZ=============*/
				chart.setTitle("BarChart XOZ");
			    chart.getStyler().setXAxisMin(-10);
			    chart.getStyler().setXAxisMax(10);
			    chart.setXAxisTitle("X");
			    chart.setYAxisTitle("Z");
			    chart.addSeries("XOZ", x, z);
			    break;
			case 2:
				/*==============YOZ=============*/
				chart.setTitle("BarChart YOZ");
			    chart.getStyler().setXAxisMin(-10);
			    chart.getStyler().setXAxisMax(10);
			    chart.setXAxisTitle("Y");
			    chart.setYAxisTitle("Z");
			    chart.addSeries("YOZ", y, z);
			    break;
			}
			return chart;	    
		}

		public PieChart getPieChart(int i){
						
	    	PieChart chart = new PieChartBuilder().width(500).height(300).build();
	    	
		    chart.getStyler().setLegendVisible(false);
		    //chart.setTitle("XOZ");
		    switch (i){
		    case 1: 
		    	chart.setTitle("PieChart XOZ");
		    	Color[] sliceColors1 = new Color[]{
		    			new Color(224, 68, 14),
		    			new Color(230, 105, 62),
		    			new Color(236, 143, 110),
		    			new Color(243, 180, 159),
		    			new Color(246, 199, 182)};
		    	chart.getStyler().setSeriesColors(sliceColors1);
		    	for(double t = -10; t <= 10; t++){
		    		   chart.addSeries(""+t, sin(sqrt(pow(t,2))));
		    	}
		    	break;
		    case 2:
		    	chart.setTitle("PieChart YOZ");
		    	Color[] sliceColors2 = new Color[]{
		    			new Color(224, 68, 114),
		    			new Color(230, 105, 162),
		    			new Color(236, 143, 210),
		    			new Color(243, 180, 249),
		    			new Color(246, 199, 142)};
		    	chart.getStyler().setSeriesColors(sliceColors2);
		    	for(double t = 10; t >= -10; t--){
		    		   chart.addSeries(""+t, sin(sqrt(pow(t,2))));
		    	}
		    	break;
		    }
			
			return chart;			    
		}
}
