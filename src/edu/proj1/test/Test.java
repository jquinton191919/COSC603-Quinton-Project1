package edu.proj1.test;

import java.util.Random;

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
		Random rand = new Random();
		FireDangerSystem fds = new FireDangerSystem();
		fds.dryWet = 4.6; fds.buo = 0.01; fds.precip = 0.001; fds.wind=5;
		
		System.out.println("Test number " + ++testNo);
		fds.isSnow = true;
		fds.isRain = false;
		fds.fireDangerStart();
		
		System.out.println("Test number " + ++testNo);
		fds.isRain = true;
		fds.fireDangerStart();
		
		System.out.println("Test number " + ++testNo);
		fds.isSnow = false;
		fds.fireDangerStart();
		
		System.out.println("Test number " + ++testNo);
		fds.isRain = false;
		fds.fireDangerStart();
		
		System.out.println("Test number " + ++testNo);
		fds.isRain = true;
		fds.fireDangerStart();
		
		System.out.println("Test number " + ++testNo);
		fds.dryWet = 2;
		fds.fireDangerStart();
		
		System.out.println("Test number " + ++testNo);
		fds.dryWet = 500;
		fds.fireDangerStart();
		
		System.out.println("Test number " + ++testNo);
		fds.buo = rand.nextDouble() + rand.nextInt(30); 
		fds.precip = rand.nextDouble() / rand.nextInt(10); 
		fds.wind = rand.nextInt(50);
		fds.fireDangerStart();
		
		System.out.println("Test number:" + ++testNo);
		fds.buo = rand.nextDouble() + rand.nextInt(30); 
		fds.precip = rand.nextDouble() / rand.nextInt(10); 
		fds.wind = rand.nextInt(50);
		fds.fireDangerStart();
		
		System.out.println("Test number:" + ++testNo);
		fds.buo = rand.nextDouble() + rand.nextInt(30); 
		fds.precip = rand.nextDouble() / rand.nextInt(10); 
		fds.wind = rand.nextInt(50);
		fds.fireDangerStart();
		
	}
	
}
