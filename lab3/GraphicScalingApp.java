package lab3;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import lab4.GridPlotExample;
import lab4.MatrixChart;

public class GraphicScalingApp extends Application {
  public static final int FRAME_WIDTH=1366;
  public static final int FRAME_HEIGHT=768;
  public double maxX=100;
  public double maxY=100;
  public double density=1;
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(final Stage stage) {
	double z=0;
	
	AnchorPane pane = new AnchorPane();
	pane.setPrefHeight(500);
	pane.setPrefWidth(500);
	pane.setMaxHeight(500);
	pane.setMaxWidth(500);
	pane.setMinHeight(500);
	pane.setMinWidth(500);
	////////////////////////////
	int coord = 150;
	Point3D o = new Point3D(coord,coord,coord);
	Point3D X = new Point3D(80,coord,coord);
	Point3D Y = new Point3D(coord,80,coord);
	Point3D Z = new Point3D(coord,coord,80);
	Cylinder axisX = createConnection(o, X);
	Cylinder axisY = createConnection(o, Y);
	Cylinder axisZ = createConnection(o, Z);
	
	final PhongMaterial redMaterial = new PhongMaterial();
    redMaterial.setDiffuseColor(Color.DARKRED);
    redMaterial.setSpecularColor(Color.RED);

    final PhongMaterial greenMaterial = new PhongMaterial();
    greenMaterial.setDiffuseColor(Color.GREEN);
    greenMaterial.setSpecularColor(Color.LIGHTGREEN);
     
    final PhongMaterial blueMaterial = new PhongMaterial();
    blueMaterial.setDiffuseColor(Color.DARKBLUE);
    blueMaterial.setSpecularColor(Color.BLUE);
	axisX.setMaterial(redMaterial);
	axisY.setMaterial(blueMaterial);
	axisZ.setMaterial(greenMaterial);
	
	pane.getChildren().setAll(axisX, axisY, axisZ);
	
	VBox controllers = new VBox(5);
    
    HBox XControl = new HBox(5);
    VBox maxX = new VBox(5);
    TextField maxXText = new TextField("20");
    Label maxXLabel = new Label("Max X");
    maxX.getChildren().addAll(maxXLabel, maxXText);
    VBox minX = new VBox(5);
    TextField minXText = new TextField("-20");
    Label minXLabel = new Label("Min X");
    minX.getChildren().addAll(minXLabel, minXText);
    XControl.getChildren().addAll(minX, maxX);
    
    HBox YControl = new HBox(5);
    VBox maxY = new VBox(5);
    TextField maxYText = new TextField("20");
    Label maxYLabel = new Label("Max Y");
    maxY.getChildren().addAll(maxYLabel, maxYText);
    VBox minY = new VBox(5);
    TextField minYText = new TextField("-20");
    Label minYLabel = new Label("Min Y");
    minY.getChildren().addAll(minYLabel, minYText);
    YControl.getChildren().addAll(minY, maxY);
    
    HBox ZControl = new HBox(5);
    VBox maxZ = new VBox(5);
    TextField maxZText = new TextField("1");
    Label maxZLabel = new Label("Max Z");
    maxZ.getChildren().addAll(maxZLabel, maxZText);
    VBox minZ = new VBox(5);
    TextField minZText = new TextField("-1");
    Label minZLabel = new Label("Min Z");
    minZ.getChildren().addAll(minZLabel, minZText);
    ZControl.getChildren().addAll(minZ, maxZ);    
    
    VBox OpacityControl = new VBox(5);
    Slider OpacitySlider = new Slider(0, 1, 0.75);
    Label OpacityLabel = new Label("Opacity: "+Double.toString(OpacitySlider.getValue()));
    OpacityControl.getChildren().addAll(OpacityLabel, OpacitySlider);
    
    VBox AccuracyControl = new VBox(5);
    Slider AccuracySlider = new Slider(0, 1, 0.5);
    Label AccuracyLabel = new Label("Accuracy: "+Double.toString(AccuracySlider.getValue()));
    AccuracyControl.getChildren().addAll(AccuracyLabel, AccuracySlider);
    
    VBox DensityControl = new VBox(5);
    Slider DensitySlider = new Slider(0, 1, 0.5);
    Label DensityLabel = new Label("Density: "+Double.toString(AccuracySlider.getValue()));
    DensityControl.getChildren().addAll(DensityLabel, DensitySlider);
    
    HBox Buttons = new HBox(165);
    Button VisibleAxises = new Button("Hide axises");
    VisibleAxises.setOnAction(event -> {
    	if (axisX.isVisible()){axisX.setVisible(false);} else {axisX.setVisible(true);} 
    	if (axisY.isVisible()){axisY.setVisible(false);} else {axisY.setVisible(true);} 
    	
    	if (axisZ.isVisible()){
    		axisZ.setVisible(false);
    		VisibleAxises.setText("Show axises");
    		} else {
    		axisZ.setVisible(true);
    		VisibleAxises.setText("Hide axises");
    		}
    });
    
    Button Repaint = new Button("Repaint");
    double maxXV =Double.parseDouble(maxXText.getText());
    double minXV =Double.parseDouble(minXText.getText());
    double maxYV =Double.parseDouble(maxYText.getText());
    double minYV =Double.parseDouble(minYText.getText());
    Repaint.setOnAction(event -> {
    	repaint(pane, minXV, maxXV, minYV, maxYV, DensitySlider.getValue(),AccuracySlider.getValue(),OpacitySlider.getValue());
    });
    Buttons.getChildren().setAll(VisibleAxises,Repaint);
    
    VBox XRotateControl = new VBox(5);
    Slider XRotateSlider = new Slider(-180, 180, 30);
    Label XRotateLabel = new Label("Rotate  on X-axis "+Double.toString(XRotateSlider.getValue()));
    XRotateControl.getChildren().addAll(XRotateLabel, XRotateSlider);  
    
    VBox YRotateControl = new VBox(5);
    Slider YRotateSlider = new Slider(-180, 180, 50);
    Label YRotateLabel = new Label("Rotate  on Y-axis "+Double.toString(YRotateSlider.getValue()));
    YRotateControl.getChildren().addAll(YRotateLabel, YRotateSlider);    
    
    VBox ZRotateControl = new VBox(5);
    Slider ZRotateSlider = new Slider(-180, 180, 30);
    Label ZRotateLabel = new Label("Rotate  on Z-axis "+Double.toString(ZRotateSlider.getValue()));
    ZRotateControl.getChildren().addAll(ZRotateLabel, ZRotateSlider);    
    
    Separator s0 = new Separator();
    Separator s1 = new Separator();
    Separator s2 = new Separator();
    Separator s3 = new Separator();
    
    
    DensitySlider.valueProperty().addListener((ov,old_val,new_val)->{
    	DensityLabel.setText(String.format("Density: "+"%.2f",new_val));
    });
    AccuracySlider.valueProperty().addListener((ov,old_val,new_val)->{
    	AccuracyLabel.setText(String.format("Accuracy: "+"%.2f",new_val));
    });
	OpacitySlider.valueProperty().addListener((ov,old_val,new_val)->{
	   	pane.setOpacity(new_val.doubleValue());
	   	OpacityLabel.setText(String.format("Opacity: "+"%.2f", new_val));
	}); 

    
    
    
    controllers.getChildren().addAll(
    		s0,XControl,YControl,ZControl,
    		s1,OpacityControl,AccuracyControl,DensityControl,
    		s2,Buttons,
    		s3,XRotateControl,YRotateControl,ZRotateControl);
    controllers.setMargin(s0, new Insets(40,0,10,0));
    controllers.setMargin(s1, new Insets(10,0,10,0));
    controllers.setMargin(s2, new Insets(10,0,10,0));
    controllers.setMargin(s3, new Insets(10,0,10,0));
    controllers.setPrefWidth(300);
    controllers.setMinWidth(300);
	
	for (double x=-20; x<20; x+=DensitySlider.getValue()){
		  for (double y=-20; y<20; y+=DensitySlider.getValue()){
			  z=Math.sin(Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)));
			  Sphere sphere = new Sphere(AccuracySlider.getValue());
			  sphere.setTranslateX(coord+x);
			  sphere.setTranslateY(coord+y);
			  sphere.setTranslateZ(coord+z);
			  pane.getChildren().add(sphere);
		  }
	}
		 
	Rotate rxView = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
	Rotate ryView = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
	Rotate rzView = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
	
	rxView.setAngle(30);
	ryView.setAngle(50);
	rzView.setAngle(30);
	XRotateSlider.valueProperty().addListener((ov,old_val,new_val)->{
		rxView.setAngle(new_val.doubleValue());
		XRotateLabel.setText("Rotate  on X-axis "+String.format("%.2f",new_val));
    });
	YRotateSlider.valueProperty().addListener((ov,old_val,new_val)->{
		ryView.setAngle(new_val.doubleValue());
		YRotateLabel.setText(String.format("Rotate  on Y-axis "+"%.2f",new_val));
    });
	ZRotateSlider.valueProperty().addListener((ov,old_val,new_val)->{
		rzView.setAngle(new_val.doubleValue());
		ZRotateLabel.setText(String.format("Rotate  on Z-axis "+"%.2f",new_val));
    });	
	pane.getTransforms().addAll(rxView, ryView, rzView);
 	axisX.toFront();
	axisY.toFront();
	axisZ.toFront();
    final Group group = new Group(pane);
    Parent zoomPane = createZoomPane(group);
    
    VBox layout = new VBox();
    
    Label Task = new Label("Z=sin(sqrt(x^2+y^2))");
    Task.setFont(Font.font(java.awt.Font.SERIF, 25));
    layout.getChildren().setAll(createMenuBar(stage, group), zoomPane, Task);
    layout.setAlignment(Pos.CENTER);
    layout.setPrefWidth(FRAME_WIDTH-controllers.getWidth());
    layout.setPrefHeight(FRAME_HEIGHT-Task.getHeight()-78);
    HBox hbox = new HBox();
    hbox.getChildren().setAll(layout, controllers);
    hbox.setMargin(controllers, new Insets(0,25,0,25));
    
    VBox.setVgrow(zoomPane, Priority.ALWAYS);

    Scene scene = new Scene(hbox, FRAME_WIDTH, FRAME_HEIGHT-78);

    stage.setTitle("Computer graphics lab 3");
    stage.setResizable(false);
    stage.getIcons().setAll(new Image(APP_ICON));
    stage.setScene(scene);
    stage.show();
  }
  
  private void repaint(AnchorPane pane, double minXValue, double maxXValue, double minYValue, double maxYValue, double DensityValue, double AccuracyValue, double OpacityValue){
	  	double z=0;
	    
		////////////////////////////
		int coord = 150;
		Point3D o = new Point3D(coord,coord,coord);
		Point3D X = new Point3D(300,coord,coord);
		Point3D Y = new Point3D(coord,300,coord);
		Point3D Z = new Point3D(coord,coord,300);
		Cylinder axisX = createConnection(o, X);
		Cylinder axisY = createConnection(o, Y);
		Cylinder axisZ = createConnection(o, Z);
		
		final PhongMaterial redMaterial = new PhongMaterial();
	    redMaterial.setDiffuseColor(Color.DARKRED);
	    redMaterial.setSpecularColor(Color.RED);

	    final PhongMaterial greenMaterial = new PhongMaterial();
	    greenMaterial.setDiffuseColor(Color.GREEN);
	    greenMaterial.setSpecularColor(Color.LIGHTGREEN);
	     
	    final PhongMaterial blueMaterial = new PhongMaterial();
	    blueMaterial.setDiffuseColor(Color.DARKBLUE);
	    blueMaterial.setSpecularColor(Color.BLUE);
		axisX.setMaterial(redMaterial);
		axisY.setMaterial(blueMaterial);
		axisZ.setMaterial(greenMaterial);
		
		pane.getChildren().setAll(axisX, axisY, axisZ);
	
		for (double x=minXValue; x<maxXValue; x+=DensityValue){
			  for (double y=minYValue; y<maxYValue; y+=DensityValue){
				  z=Math.sin(Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)));
				  Sphere sphere = new Sphere(AccuracyValue);
				  sphere.setTranslateX(coord+x);
				  sphere.setTranslateY(coord+y);
				  sphere.setTranslateZ(coord+z);
				  pane.getChildren().addAll(sphere);
			  }
		}
		
		pane.setOpacity(OpacityValue);				
  }

  
  
  private Parent createZoomPane(final Group group) {
    final double SCALE_DELTA = 1.1;
    final StackPane zoomPane = new StackPane();

    zoomPane.getChildren().add(group);

    final ScrollPane scroller = new ScrollPane();
    final Group scrollContent = new Group(zoomPane);
    scroller.setContent(scrollContent);

    scroller.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
      @Override
      public void changed(ObservableValue<? extends Bounds> observable,
          Bounds oldValue, Bounds newValue) {
        zoomPane.setMinSize(newValue.getWidth(), newValue.getHeight());
      }
    });

    scroller.setPrefViewportWidth(256);
    scroller.setPrefViewportHeight(256);

    zoomPane.setOnScroll(new EventHandler<ScrollEvent>() {
      @Override
      public void handle(ScrollEvent event) {
        event.consume();

        if (event.getDeltaY() == 0) {
          return;
        }

        double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA
            : 1 / SCALE_DELTA;

        // amount of scrolling in each direction in scrollContent coordinate
        // units
        Point2D scrollOffset = figureScrollOffset(scrollContent, scroller);

        group.setScaleX(group.getScaleX() * scaleFactor);
        group.setScaleY(group.getScaleY() * scaleFactor);

        // move viewport so that old center remains in the center after the
        // scaling
        repositionScroller(scrollContent, scroller, scaleFactor, scrollOffset);

      }
    });

    // Panning via drag....
    final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<Point2D>();
    scrollContent.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
      }
    });

    scrollContent.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        double deltaX = event.getX() - lastMouseCoordinates.get().getX();
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        double deltaH = deltaX * (scroller.getHmax() - scroller.getHmin()) / extraWidth;
        double desiredH = scroller.getHvalue() - deltaH;
        scroller.setHvalue(Math.max(0, Math.min(scroller.getHmax(), desiredH)));

        double deltaY = event.getY() - lastMouseCoordinates.get().getY();
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        double deltaV = deltaY * (scroller.getHmax() - scroller.getHmin()) / extraHeight;
        double desiredV = scroller.getVvalue() - deltaV;
        scroller.setVvalue(Math.max(0, Math.min(scroller.getVmax(), desiredV)));
      }
    });

    return scroller;
  }

  private Point2D figureScrollOffset(Node scrollContent, ScrollPane scroller) {
    double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
    double hScrollProportion = (scroller.getHvalue() - scroller.getHmin()) / (scroller.getHmax() - scroller.getHmin());
    double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
    double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
    double vScrollProportion = (scroller.getVvalue() - scroller.getVmin()) / (scroller.getVmax() - scroller.getVmin());
    double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
    return new Point2D(scrollXOffset, scrollYOffset);
  }

  private void repositionScroller(Node scrollContent, ScrollPane scroller, double scaleFactor, Point2D scrollOffset) {
    double scrollXOffset = scrollOffset.getX();
    double scrollYOffset = scrollOffset.getY();
    double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
    if (extraWidth > 0) {
      double halfWidth = scroller.getViewportBounds().getWidth() / 2 ;
      double newScrollXOffset = (scaleFactor - 1) *  halfWidth + scaleFactor * scrollXOffset;
      scroller.setHvalue(scroller.getHmin() + newScrollXOffset * (scroller.getHmax() - scroller.getHmin()) / extraWidth);
    } else {
      scroller.setHvalue(scroller.getHmin());
    }
    double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
    if (extraHeight > 0) {
      double halfHeight = scroller.getViewportBounds().getHeight() / 2 ;
      double newScrollYOffset = (scaleFactor - 1) * halfHeight + scaleFactor * scrollYOffset;
      scroller.setVvalue(scroller.getVmin() + newScrollYOffset * (scroller.getVmax() - scroller.getVmin()) / extraHeight);
    } else {
      scroller.setHvalue(scroller.getHmin());
    }
  }

  private MenuBar createMenuBar(final Stage stage, final Group group) {
    Menu fileMenu = new Menu("_File");
    MenuItem chartMenuItem = new MenuItem("Show charts");
    chartMenuItem.setGraphic(new ImageView(new Image(CHART_ICON)));
    chartMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.C));
    chartMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        new MatrixChart();
      }
    });
    MenuItem tableMenuItem = new MenuItem("Show table");
    tableMenuItem.setGraphic(new ImageView(new Image(TABLE_ICON)));
    tableMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.T));
    tableMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
    	  new GridPlotExample(); 
      }
    });
    MenuItem wolframMenuItem = new MenuItem("Wolfram Alpha");
    wolframMenuItem.setGraphic(new ImageView(new Image(WOLFRAM_ICON)));
    wolframMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.W));
    wolframMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
    	  try {
			Main.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
      }
    });
    MenuItem exitMenuItem = new MenuItem("E_xit");
    exitMenuItem.setGraphic(new ImageView(new Image(CLOSE_ICON)));
    exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.BACK_SPACE));
    exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        stage.close();
      }
    });
    fileMenu.getItems().setAll(tableMenuItem, chartMenuItem, wolframMenuItem, exitMenuItem);
    Menu zoomMenu = new Menu("_Zoom");
    MenuItem zoomResetMenuItem = new MenuItem("Zoom _Reset");
    zoomResetMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));
    zoomResetMenuItem.setGraphic(new ImageView(new Image(ZOOM_RESET_ICON)));
    zoomResetMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        group.setScaleX(1);
        group.setScaleY(1);
      }
    });
    MenuItem zoomInMenuItem = new MenuItem("Zoom _In");
    zoomInMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.I));
    zoomInMenuItem.setGraphic(new ImageView(new Image(ZOOM_IN_ICON)));
    zoomInMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        group.setScaleX(group.getScaleX() * 1.5);
        group.setScaleY(group.getScaleY() * 1.5);
      }
    });
    MenuItem zoomOutMenuItem = new MenuItem("Zoom _Out");
    zoomOutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O));
    zoomOutMenuItem.setGraphic(new ImageView(new Image(ZOOM_OUT_ICON)));
    zoomOutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        group.setScaleX(group.getScaleX() * 1 / 1.5);
        group.setScaleY(group.getScaleY() * 1 / 1.5);
      }
    });
    zoomMenu.getItems().setAll(zoomResetMenuItem, zoomInMenuItem,
        zoomOutMenuItem);
    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().setAll(fileMenu, zoomMenu);
    return menuBar;
  }
  
  private Cylinder createConnection(Point3D origin, Point3D target) {
	    Point3D yAxis = new Point3D(0, 1, 0);
	    Point3D diff = target.subtract(origin);
	    double height = diff.magnitude();

	    Point3D mid = target.midpoint(origin);
	    Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

	    Point3D axisOfRotation = diff.crossProduct(yAxis);
	    double angle = Math.acos(diff.normalize().dotProduct(yAxis));
	    Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

	    Cylinder line = new Cylinder(1, height);

	    line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

	    return line;
	}

  // icons source from:
  // http://www.iconarchive.com/show/soft-scraps-icons-by-deleket.html
  // icon license: CC Attribution-Noncommercial-No Derivate 3.0 =?
  // http://creativecommons.org/licenses/by-nc-nd/3.0/
  // icon Commercial usage: Allowed (Author Approval required -> Visit artist
  // website for details).

  public static final String APP_ICON = "/icons/1460507373_ic_3d_rotation_48px.png";
  public static final String ZOOM_RESET_ICON = "/icons/1460507434_update.png";
  public static final String ZOOM_OUT_ICON = "/icons/1460507417_69.png";
  public static final String ZOOM_IN_ICON = "/icons/1460507421_70.png";
  public static final String CLOSE_ICON = "/icons/1460507464_cross-24.png";
  public static final String TABLE_ICON = "/icons/1460507842_098_Spreadsheet.png";
  public static final String CHART_ICON = "/icons/1460507873_line-chart.png";
  public static final String WOLFRAM_ICON = "/icons/1460508799_wolfram_alpha.png";
}