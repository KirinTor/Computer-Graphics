package lab1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
 
public class JavaApplication extends JComponent {
 
	
  Timer timer;
  Random rand = new Random(System.currentTimeMillis());
  private static final int FRAME_WIDTH=1366;
  private static final int FRAME_HEIGHT=768;
  private double maxR=350;
  private double minR=5;
  private double step = 1; //крок зміни радіуса
  private int size = 20;// початковий розмір елемента
  private int n = 20; //кількість елементів 
  private int count = 10;// кількість концентричних кіл
  float[][] nodesCoord = new float[n][2];// масив координат для кожного елемента орнаменту
  
    public static void main(String[] args)
    {
    JavaApplication app = new JavaApplication();
    MyFrame frame = app.new MyFrame();
    frame.add(new JavaApplication());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    }
 
    class MyFrame extends JFrame
    {
         public MyFrame()
         {
             setTitle("Lab 1");
             setSize(FRAME_WIDTH, FRAME_HEIGHT);
             Container pane = getContentPane();
             MyPanel panel = new MyPanel();
             panel.setSize(FRAME_WIDTH,FRAME_HEIGHT);
             pane.add(panel,BorderLayout.CENTER);
         }
     }
 
     class MyPanel extends JPanel implements ActionListener
     {
         public MyPanel()
                 {
                     JButton button1 = new JButton("Start");
                     JButton button2 = new JButton("Stop");
                     JButton button3 = new JButton("Exit");
                
 
                     button1.addActionListener(this);
                     button2.addActionListener(this);
                     button3.addActionListener(this);
                     
                     add(button1,BorderLayout.WEST);
                     add(button2,BorderLayout.WEST);
                     add(button3,BorderLayout.WEST);
 
                     radius = minR;
 
                  }
     
    @Override
    public void actionPerformed(ActionEvent e)
    {
    String action = e.getActionCommand();
    if(action.equals("Start"))
    {
        timer = new Timer();
        timer.schedule(new PaintTask(this),0,5);
    }else if(action.equals("Stop"))
    {
        timer.cancel();
    }else if(action.equals("Exit"))
    {
        System.exit(0);
    }
    }
    
    	
 
          
    @Override
    public void paintComponent (Graphics g)
    {
    	super.paintComponent(g);
   
        int width = FRAME_WIDTH;
        int height = FRAME_HEIGHT;
        
        int red = rand.nextInt(255);
		int green = rand.nextInt(255);
		int blue = rand.nextInt(255);
		Color newColor = new Color(red, green, blue);
		g.setColor(newColor);
        
        Graphics2D g2d = (Graphics2D) g;
      
        g2d.setStroke(new BasicStroke(2)); //розмір пера
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //згладжування
        double x=0.5*width;
        double y=0.5*height;
        
		double r = radius;	
		for (int j = 0; j < count; j++) { //цикл шукає координати для побудови фігури в орнаменті

			for (int i = 0; i < n; i++) {
				float angle = (float) (2 * i * Math.PI / n);
				nodesCoord[i][0] = (float) (x + r * Math.sin(angle));
				nodesCoord[i][1] = (float) (y + r * Math.cos(angle));
			}
			if (step>0) {
				for (int p = 0; p < n; p++) {	//цикл малює орнамент з елементів
					drawFig(g2d, (int) nodesCoord[p][0], (int) nodesCoord[p][1],size+4*j);
											}
			} else {
				for (int p = 0; p < n; p++) {	//цикл малює орнамент з елементів
					drawFig(g2d, (int) nodesCoord[p][0], (int) nodesCoord[p][1],size-4*j);
											}

					}

			if (step>0) {r*=1.2;} else {r*=-1.2;};

		}
   } 
    
    
   public void drawFig(Graphics2D g2d, double x, double y, double size) {
		double alpha = Math.toRadians(45); //кут повороту
		double factor = 1 / (Math.sin(alpha) + Math.cos(alpha)); //формула для коефіцієнту
		for (int i = 0; i < 2; i++) {
			g2d.drawRect((int) (x - size / 2), (int) (y - size / 2),(int)size, (int)size);
			g2d.rotate(alpha, x, y); // поворот квадрату
			size *= factor;// визначення сторони ромбу
		}
		g2d.rotate(-alpha * 2, x, y); //поворот назад для правильного розміщення елементу
	}
 
   
   
   
   
    public void updateRadius()
    {
    //зміна напрямку руху якщо радіус виходить за межі
    if(radius > maxR || radius < minR )
        step *= (-1);
	    radius += step;
    }
 
    private double radius;
    private Timer timer = new Timer();
 
    class PaintTask extends TimerTask
    {
    	private MyPanel mComponent;
 
    	public PaintTask(MyPanel component)
    		{
    			mComponent = component;
    		}
 
    	@Override
    	public void run()
    	{
    		mComponent.updateRadius();  //зміна радіуса
    		mComponent.repaint();		//перемальовка панелі
       	  //timer.schedule(new PaintTask(mComponent),1); //перевиклик самого себе //біситься!
    	}
    }
  }
}