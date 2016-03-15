package edu.proj1.compute; 

/****
 * Static class for the various computations related to the Fire Danger Legacy System
 * ***/
public class Formula {
	/******** Computation Coefficient for timber and grass spread calculations *******/
	private static double coEfficientA, coEfficientB;
	public Formula() {}
	
	/****
	 * Calculates the Buildup Index
	 * @param buo - Yesterday's buildup
	 * @param precip - the past 24 hours of precipitation in inches and hundredths
	 * *******/
	public static double getBuildupIndex(double buo, double precip) {
		return -50 * (Math.log( 1 - (-1 * Math.E * buo / 50) )) * Math.pow(Math.E, 1.175) * (precip - .1);
	}
	

	/***
	 * Calculates the Fine Fuel Moisture
	 * A and B are the piecewise regression coefficients used to determine FFM. 
	 * The depression of the wet bulb is used to decide which set of A and B will be used.
	 * Herb stage is used to adjust the calculated fine fuel moisture by adding 5 percent for transition stage or
	 * 10-percent for green fuels.
	 * @param dryWet - dry-wet in degrees farenheit
	 * **/
	public static double getFineFuelMoisture(double dryWet) {
		double a, b;
		if(dryWet < 4.5) {
			a = 30.0;
			b = -.1859;
		}
		else if(dryWet < 12.5 && dryWet >= 4.5) {
			a = 19.2;
			b = -.0859;
		}
		else if(dryWet < 27.5 && dryWet >= 12.5) {
			a = 13.8;
			b = -.0579;
		}
		else{
			a = 22.5;
			b = -.0774;
		}
		return Math.pow( (a * Math.E), b);
	}
	
	/******
	 * Calculates the Adjusted Fuel Moisture
	 * @param ffm - the current Fine Fuel Moisture
	 * @param bui - today's Buildup Index Recovery
	 * ********/
	public static double getAdjustedFuelMoisture(double ffm, double bui) {
		return .9 * ffm + 9.5 * Math.E * ( (-1 * bui) / 50);
	}
	
	
	/*******
	 * Calculates the Fine Fuel Spread for grass
	 * @param wind - Windspeed in m.p.h., 20 feet above open level ground
	 * @param ffm - the current Fine Fuel Moisture
	 * **************/
	public static double getFineFuelSpread(double wind, double ffm) {
		double first, second, mid, ans;
		setCoefficients(wind);
		first = coEfficientA * (wind + coEfficientB);
		mid = 33 - ffm;
		if(ffm > 33) mid *= -1;
		second = Math.pow( (mid), 1.65);
		ans = first * second - 3;
		return ans;
	}
	
	/*******
	 * Calculates the Timber Spread Index
	 * @param wind - Windspeed in m.p.h., 20 feet above open level ground
	 * @param adfm - Fuel moisture adjusted for 50-day timelag
	 * **************/
	public static double getTimberSpreadIndex(double wind, double adfm) {
		double first, second, mid, ans;
		setCoefficients(wind);
		first = coEfficientA * (wind + coEfficientB);
		mid = 33 - adfm;
		if(adfm > 33) mid *= -1;
		second = Math.pow( (mid), 1.65);
		ans = first * second - 3;
		return ans;
	}
	
	/*****
	 * Calculates the Fire Load Index
	 * @param tsi - Timber Spread Index
	 * @param bui - Buildup Index
	 * *********/
	public static double getFireLoadIndex(double tsi, double bui) {
		double exponent;
		tsi = (tsi < 0) ? 0 : tsi;
		bui = (bui < 0) ? 0 : bui;
		exponent = 1.75 * ( log(tsi, 10) + .32 * (log(bui, 10) - 1.64) );
		return Math.pow(10,exponent);
	}
	
	/******
	 * Returns the drying factor based on the Fine Fuel Moisture
	 * @param ffm - Fine Fuel Moisture value
	 * *******/
	public static int getDryingFactor(double ffm) {
		if(ffm > 16.0){
			return 1;
		}
		else if(ffm <= 16.0 && ffm > 10.0) {
			return 2;
		}
		else if(ffm <= 10.0 && ffm > 7.0) {
			return 3;
		}
		else if(ffm <= 7.0 && ffm > 5.0) {
			return 4;
		}
		else if(ffm <= 5.0 && ffm > 4.0) {
			return 5;
		}
		else if(ffm <= 4.0 && ffm > 3.0) {
			return 6;
		}
		else {
			return 7;
		}
		
		
	}
	
	/******
	 * Private method to set the coefficients according to wind speed
	 * @param wind - Windspeed in m.p.h., 20 feet above open level ground 
	 * *******/
	private static void setCoefficients(double wind){
		if(wind < 14) {
			coEfficientA = 0.01312;
			coEfficientB = 6.0;
		}
		else{
			coEfficientA = 0.009184;
			coEfficientB = 14.4;
		}
	}
	
	/*****
	 * Returns logs for other than natural log
	 * @param input - input number
	 * @param base - desired base
	 * *******/
	private static double log(double input, int base){
		return Math.log(input) / Math.log(base);
	}
}
