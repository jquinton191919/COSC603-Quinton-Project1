package edu.proj1.test;

import edu.proj1.main.FireDangerSystem;

public class Test {
	/***
	 * Static integer to keep track of the test number
	 * **/
	static int testNo = 0;
	 
	/*******
	 * Main method to test all paths in flow control
	 * ********/
	public static void main(String[] args) {
		FireDangerSystem fds = new FireDangerSystem();
		fds.dryWet = 4.6; fds.buo = 0.01; fds.precip = 0.001; fds.wind=5;
		
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
		
		fds.dryWet = 5;
		fds.fireDangerStart();
		
	}
	
}
