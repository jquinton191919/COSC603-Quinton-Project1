package edu.proj1.test;

import edu.proj1.compute.Formula;
import edu.proj1.main.FireDangerSystem;

public class Test {
	/**
	 * static boolean isSnow, isRain;
	static double grass=0, timber=0, bui=0, ffm=0, adfm=0, fli=0, df=0, dryWet = 12.2, buo = 10, precip = 1, wind=20;
	*/
	static int testNo = 0;
	 
	/*******
	 * Main method to test all paths in flow control
	 * ********/
	public static void main(String[] args) {
		FireDangerSystem fds = new FireDangerSystem();
		fds.isSnow = true;
		fds.isRain = false;
		fds.fireDangerStart();
		
		fds.isRain = true;
		fds.fireDangerStart();
		
		fds.isSnow = false;
		fds.fireDangerStart();
		
		fds.isRain = false;
		fds.fireDangerStart();
		
		fds.isRain = true;
		fds.fireDangerStart();
		
		fds.dryWet = 30;
		fds.fireDangerStart();
	}
	
}
