package calculations;

public class CubicInterpolation {
	
	double x_1, x_2, x_3, x_4, y_1, y_2, y_3, y_4;

	public CubicInterpolation(double[] point1, double[] point2, double[]point3, double[] point4) {
		x_1 = point1[0];
		y_1 = point1[1];
		x_2 = point2[0];
		y_2 = point2[1];
		x_3 = point3[0];
		y_3 = point3[1];
		x_4 = point4[0];
		y_4 = point4[1];
	}
	
	public CubicInterpolation(){
		
	}
	
	public void configInterpolation(double[] point1, double[] point2, double[]point3, double[] point4) {
		x_1 = point1[0];
		y_1 = point1[1];
		x_2 = point2[0];
		y_2 = point2[1];
		x_3 = point3[0];
		y_3 = point3[1];
		x_4 = point4[0];
		y_4 = point4[1];
	}

	public double lagrangePolynomial(double x) {
		double firstCoefficient = (x - x_2)*(x - x_3)*(x - x_4) / ((x_1 - x_2)*(x_1 - x_3)*(x_1 - x_4));
		double secondCoefficient = (x - x_1)*(x - x_3)*(x - x_4) / ((x_2 - x_1)*(x_2 - x_3)*(x_2 - x_4));
		double thirdCoefficient = (x - x_1)*(x - x_2)*(x - x_4) / ((x_3 - x_1)*(x_3 - x_2)*(x_3 - x_4));
		double fourthCoefficient = (x - x_1)*(x - x_2)*(x - x_3) / ((x_4 - x_1)*(x_4 - x_2)*(x_4 - x_3));
		double y = firstCoefficient * y_1 + secondCoefficient * y_2 + thirdCoefficient * y_3 + fourthCoefficient * y_4;
		return y;
	}
	
	public double lagrangePolynomialQuadratic(double x) {
		double firstCoefficient = (x - x_2)*(x - x_3) / ((x_1 - x_2)*(x_1 - x_3));
		double secondCoefficient = (x - x_1)*(x - x_3) / ((x_2 - x_1)*(x_2 - x_3));
		double thirdCoefficient = (x - x_1)*(x - x_2) / ((x_3 - x_1)*(x_3 - x_2));
		double y = firstCoefficient * y_1 + secondCoefficient * y_2 + thirdCoefficient * y_3;
		return y;
	}
	
}
