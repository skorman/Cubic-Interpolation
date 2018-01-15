package drawing;

import java.util.Arrays;

import calculations.CubicInterpolation;
import calculations.PositionTracker;
import gpdraw.DrawingTool;
import gpdraw.SketchPad;

public class ConfigWaypoints {
	
	/*
	 * 80.0 , 5.120000000000003 , 6.400000000000003
160.0 , 20.479999999999983 , 25.59999999999997
240.0 , 46.079999999999906 , 57.59999999999987
320.0 , 81.079439624972 , 101.34929953121507
400.0 , 117.8897175036924 , 147.36214687961632
480.0 , 154.51999538241344 , 193.14999422801802
80.0 , 209.6785016101587 , 236.38236179576373
160.0 , 269.2344383682238 , 278.0715175264094

	 */
	
	/*
	 * 
30.280725444312438 , -191.8472210056482
8.000000000000002

0.3509068053905418 , -189.80902625706008
10.000000000000004

34.353568115457385 , -168.07452578525968
31.999999999999954

5.44196014432171 , -160.0931572315744
39.99999999999993

51.23799071213166 , -131.9977339391113
71.8768701552919

26.547488390164563 , -114.99716742388894
89.84608769411487

83.93079778496276 , -100.01941033956477
117.8897175036924

67.41349723120403 , -75.02426292445531
147.36214687961632

125.19884769454144 , -82.80191327863812
162.9225648520936

118.9985596181776 , -53.50239159829667
203.65320606511818

176.98983775719964 , -52.88067383864019
223.3674857996749

155.03458010915213 , -32.604627047342454
245.96465072842514

	 */
	
	public static double scale = 2;
	public static SketchPad paper = new SketchPad(1000, 1000);
	public static DrawingTool pen = new DrawingTool(paper);
	public static double[][] rightWaypoints = {
			{30.280725444312438 , -191.8472210056482},
			{34.353568115457385 , -168.07452578525968},
			{51.23799071213166 , -131.9977339391113},
			{83.93079778496276 , -100.01941033956477},
			{125.19884769454144 , -82.80191327863812},
			{176.98983775719964 , -52.88067383864019}
	};
	
	public static double[][] leftWaypoints = {
			{0.3509068053905418 , -189.80902625706008},
			{5.44196014432171 , -160.0931572315744},
			{26.547488390164563 , -114.99716742388894},
			{67.41349723120403 , -75.02426292445531},
			{118.9985596181776 , -53.50239159829667},
			{155.03458010915213 , -32.604627047342454}
	};
	
	public static double[][] distances = { //(right,left)
			{8.000000000000002, 10.000000000000004},
			{31.999999999999954, 39.99999999999993},
			{71.8768701552919, 89.84608769411487},
			{117.8897175036924, 147.36214687961632},
			{162.9225648520936, 203.65320606511818},
			{223.3674857996749, 245.96465072842514}
	};
	
	private static double width = 8;
	private static double sumAngle = 0;
	
	public static CubicInterpolation interpo = new CubicInterpolation();
	
	public static PositionTracker leftPosition = new PositionTracker();
	public static PositionTracker rightPosition = new PositionTracker();

	static double[] leftCurrentWaypoints = new double[4];
	static double[] rightCurrentWaypoints = new double[4];
	
	static double lastRightDistance = 0;
	static double lastLeftDistance = 0;

	public ConfigWaypoints() {
		
	}
	
	public static void draw(){
		pen.up();
		pen.move(0, -200);
		pen.down();
		pen.drawCircle(2);
		pen.up();
		pen.move(30, -200);
		pen.down();
		pen.drawCircle(2);
		for(int i = 0; i < rightWaypoints.length; i++){
			pen.up();
			pen.move(rightWaypoints[i][0], rightWaypoints[i][1]);
			pen.down();
			pen.drawCircle(2);
		}
		for(int i = 0; i < leftWaypoints.length; i++){
			pen.up();
			pen.move(leftWaypoints[i][0], leftWaypoints[i][1]);
			pen.down();
			pen.drawCircle(2);
			pen.up();
		}
	}
	
	public static void Interpolate(){
		rightPosition.setRightX(30);
		leftPosition.setRightY(-200);
		rightPosition.setRightY(-200);
		pen.move(0, -200);
		resetWaypoints(0);
		int count = 0;
		for(int i = 0; i < 600; i+=50){
			double leftDistance;
			double leftTotal;
			double rightDistance;
			double rightTotal;
			if(i == 100) {
				resetWaypoints(1);
				count+=100;
			}
			if(i == 200) {
				resetWaypoints(2);
				count+=100;
			}
			if(i == 300) {
				resetWaypoints(3);
				count+=100;
			}
			double point1[] = {count, leftCurrentWaypoints[0]};
			double point2[] = {count + 100, leftCurrentWaypoints[1]};
			double point3[] = {count + 200, leftCurrentWaypoints[2]};
			double point4[] = {count + 300, leftCurrentWaypoints[3]};
			interpo.configInterpolation(point1, point2, point3, point4);
			leftTotal = interpo.lagrangePolynomialQuadratic(i);
			leftDistance = leftTotal - lastLeftDistance;
			//System.out.println(Arrays.toString(point3));
			
			double point1r[] = {count, rightCurrentWaypoints[0]};
			double point2r[] = {count + 100, rightCurrentWaypoints[1]};
			double point3r[] = {count + 200, rightCurrentWaypoints[2]};
			double point4r[] = {count + 300, rightCurrentWaypoints[3]};
			interpo.configInterpolation(point1r, point2r, point3r, point4r);
			rightTotal = interpo.lagrangePolynomialQuadratic(i);
			rightDistance = rightTotal - lastRightDistance;
			//System.out.println(rightDistance + " " + i);
			//if (i == 489) System.out.println(Arrays.toString(point1));
			double radius;
			double angle;
			if(leftTotal > rightTotal){
				radius = leftDistance * width / (leftDistance - rightDistance);
				angle = leftDistance / (2 * Math.PI * radius);
			}
			else {
				radius = rightTotal * width / (rightTotal - leftTotal);
				angle = rightTotal / (2 * Math.PI * radius);
			}
			double angleDegrees = Math.toDegrees(angle);
			sumAngle += angleDegrees;
			System.out.println(sumAngle + " " + i);
			
			leftPosition.setPosistions(angleDegrees, leftTotal);
			rightPosition.setPosistions(angleDegrees, rightTotal);
			
			pen.up();
			//pen.move(rightPosition.getRightXPos(), rightPosition.getRightYPos());
			pen.setDirection(-(sumAngle + 270));
			//pen.turn(-(angleDegrees + 270));
			pen.down();
			pen.forward(rightDistance);
			/*
			pen.up();
			//pen.move(leftPosition.getRightXPos(), leftPosition.getRightYPos());
			//pen.setDirection(-(angleDegrees + 270));
			pen.turn(75);
			pen.down();
			pen.forward(leftDistance);
			*/
			lastRightDistance = rightTotal;
			lastLeftDistance = leftTotal;
			
		}
	}
	
	private static void resetWaypoints(int x){
		for(int i = 0; i < 3; i++){
			leftCurrentWaypoints[i] = distances[i + x][1]; 
		}
		for(int i = 0; i < 3; i++){
			rightCurrentWaypoints[i] = distances[i + x][0]; 
		}
	}

}
