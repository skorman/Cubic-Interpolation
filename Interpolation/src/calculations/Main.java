package calculations;

import drawing.ConfigWaypoints;

public class Main {

	static double[] first = {0,10};
	static double[] second = {100,40};
	static double[] third = {200,89.84608769411487};
	static double[] fourth = {300,147.36214687961632};

	private static CubicInterpolation test = new CubicInterpolation(first, second, third, fourth);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(test.lagrangePolynomial(5) - test.lagrangePolynomial(4));
		ConfigWaypoints.draw();
		ConfigWaypoints.Interpolate();
	}

}
