package display;



import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class DisplayPane extends Pane{
	private static final int BWIDTH = 100;
	private ArrayList<Shelves> shelvesList = new ArrayList<>();
	private ArrayList<Robot> robotList = new ArrayList<>();
	private int []robotMap;
	private int [][] shelvesMap = {{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0}
	};
	
	private Robot robot1;
	private int pressRobot = 0;
	
	public DisplayPane(){
		onMouseClickedProperty().set(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				addShelves(e);
				clickRun(e);
			}
		});
		
		
		new FileReader();
		
		FileReader fileReader = new FileReader();
		robotMap = fileReader.getRmap();
//		Robot test = new Robot(1, 0 ,0, 4, true);

	//		test.setxPos(20);
	//		test.setyPos(y);
	//		test.setDirection(d);
			
		
		

	}
	
	private void clickRun(MouseEvent e){
		if (e.isPrimaryButtonDown() == false){
			DisplayPane.this.getChildren().removeAll(getChildren());
			double x = (double)(robotMap[pressRobot]);
			double y = (double)(robotMap[pressRobot+1]);
			int d = robotMap[pressRobot+3];
			pressRobot = pressRobot +4;
			Robot test = new Robot(0, x ,y, d, true);
		}
	}
	
	private void addShelves(MouseEvent e){
		if (e.isPrimaryButtonDown() == false){
			shelvesMap[(int) (e.getX()/BWIDTH)][(int) (e.getY()/BWIDTH)] = 1;
			shelvesList.add(new Shelves((int)(e.getX()/BWIDTH),(int)(e.getY()/BWIDTH)));
			System.out.print((int) (e.getX()/BWIDTH) + "  " + (int)(e.getY()/BWIDTH));
		}
		else if(e.isSecondaryButtonDown() == false){
			shelvesMap[(int) (e.getX()/BWIDTH)][(int) (e.getY()/BWIDTH)] = 0;
		}
	}
	
	class Shelves{
		private int id;
		private double xPos;
		private double yPos;
		private Stop[] stops = { new Stop(0, Color.WHITE), new Stop(1, Color.BLACK)};
		private LinearGradient Cl1 = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, stops);
		
		public Shelves(int xPos,int yPos){

			this.xPos = xPos;
			this.yPos = yPos;
			Rectangle a = new Rectangle(xPos*BWIDTH+BWIDTH/20, yPos*BWIDTH+BWIDTH/20, BWIDTH*9/10, BWIDTH*9/10);
			a.setFill(Color.ORANGE);

		}
		

		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public double getxPos() {
			return xPos;
		}

		public void setxPos(double xPos) {
			this.xPos = xPos;
		}

		public double getyPos() {
			return yPos;
		}

		public void setyPos(double yPos) {
			this.yPos = yPos;
		}
		
	}
	
	class Robot{
		private int id;
		private double xPos;
		private double yPos;
		private boolean crd;
		private int direction;
		public Robot(int id, double xPos, double yPos,int direction, boolean iscarried){
			this.id = id;
			this.xPos = xPos;
			this.yPos = yPos;
			this.direction = direction;
			crd = iscarried;
			
			Pane robot = drawNorth();

			switch (direction){
				case 0:	robot = drawNorth();break;
				case 1: robot = drawWest();break;
				case 2: robot = drawSouth();break;
				case 3: robot = drawEast();break;
			}	
			DisplayPane.this.getChildren().add(robot);
		
		
		}
		
		
		public void setDirection(int direction) {
			this.direction = direction;
		}


		public void setxPos(double xPos) {
			this.xPos = xPos;
		}


		public void setyPos(double yPos) {
			this.yPos = yPos;
		}


		public Pane drawNorth(){
			Stop[] stops = { new Stop(0, Color.RED ), new Stop(1, Color.web("#BABABA"))};
			LinearGradient Cl1 = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, stops);
			Pane robot = new Pane();
			Rectangle robotMid = new Rectangle(xPos*BWIDTH+BWIDTH*25/100, yPos*BWIDTH+BWIDTH*35/100, BWIDTH/2, BWIDTH/2);
			Rectangle robotLeft = new Rectangle(xPos*BWIDTH+BWIDTH*15/100, yPos*BWIDTH+BWIDTH*10/100, BWIDTH/5, BWIDTH*4/5);
			Rectangle robotRight = new Rectangle(xPos*BWIDTH+BWIDTH*65/100, yPos*BWIDTH+BWIDTH*10/100, BWIDTH/5, BWIDTH*4/5);
			robotMid.setFill(Cl1);robotLeft.setFill(Cl1);robotRight.setFill(Cl1);
			robot.getChildren().addAll(robotMid,robotLeft,robotRight);
			return robot;
			
		}
		public Pane drawWest(){
			Stop[] stops = { new Stop(0, Color.WHITE ), new Stop(1, Color.web("#00ff7f"))};
			LinearGradient Cl1 = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, stops);
			Pane robot = new Pane();
			Rectangle robotMid = new Rectangle(xPos*BWIDTH+BWIDTH*30/100, yPos*BWIDTH+BWIDTH*25/100, BWIDTH/2, BWIDTH/2);
			Rectangle robotLeft = new Rectangle(xPos*BWIDTH+BWIDTH*10/100, yPos*BWIDTH+BWIDTH*15/100, BWIDTH*4/5, BWIDTH/5);
			Rectangle robotRight = new Rectangle(xPos*BWIDTH+BWIDTH*10/100, yPos*BWIDTH+BWIDTH*65/100, BWIDTH*4/5, BWIDTH/5);
			robotMid.setFill(Cl1);robotLeft.setFill(Cl1);robotRight.setFill(Cl1);
			robot.getChildren().addAll(robotMid,robotLeft,robotRight);
			return robot;
			
		}
		public Pane drawSouth(){
			Stop[] stops = { new Stop(0, Color.WHITE ), new Stop(1, Color.web("#00ff7f"))};
			LinearGradient Cl1 = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, stops);
			Pane robot = new Pane();
			Rectangle robotMid = new Rectangle(xPos*BWIDTH+BWIDTH*25/100, yPos*BWIDTH+BWIDTH*15/100, BWIDTH/2, BWIDTH/2);
			Rectangle robotLeft = new Rectangle(xPos*BWIDTH+BWIDTH*15/100, yPos*BWIDTH+BWIDTH*10/100, BWIDTH/5, BWIDTH*4/5);
			Rectangle robotRight = new Rectangle(xPos*BWIDTH+BWIDTH*65/100, yPos*BWIDTH+BWIDTH*10/100, BWIDTH/5, BWIDTH*4/5);
			robotMid.setFill(Cl1);robotLeft.setFill(Cl1);robotRight.setFill(Cl1);
			robot.getChildren().addAll(robotMid,robotLeft,robotRight);
			return robot;

		}
		public Pane drawEast(){
			Stop[] stops = { new Stop(0, Color.WHITE ), new Stop(1, Color.web("#00ff7f"))};
			LinearGradient Cl1 = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, stops);
			Pane robot = new Pane();
			Rectangle robotMid = new Rectangle(xPos*BWIDTH+BWIDTH*20/100, yPos*BWIDTH+BWIDTH*25/100, BWIDTH/2, BWIDTH/2);
			Rectangle robotLeft = new Rectangle(xPos*BWIDTH+BWIDTH*10/100, yPos*BWIDTH+BWIDTH*15/100, BWIDTH*4/5, BWIDTH/5);
			Rectangle robotRight = new Rectangle(xPos*BWIDTH+BWIDTH*10/100, yPos*BWIDTH+BWIDTH*65/100, BWIDTH*4/5, BWIDTH/5);
			robotMid.setFill(Cl1);robotLeft.setFill(Cl1);robotRight.setFill(Cl1);
			robot.getChildren().addAll(robotMid,robotLeft,robotRight);
			return robot;
		}
		
		
	}
}
