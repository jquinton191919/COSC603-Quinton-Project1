package edu.proj1.main;

import java.util.Scanner;

import edu.proj1.compute.Formula;

public class FireDangerSystem {
	public boolean isSnow, isRain;
	public double grass=0, timber=0, bui=0, ffm=0, adfm=0, fli=0, df=0, dryWet = 0, buo = 0, precip = 0, wind=0;
	
	public FireDangerSystem() {}
	
	public static void main(String [] args) {
		FireDangerSystem fds = new FireDangerSystem();
		if(args == null || args.length < 6) {
			Scanner input = new Scanner(System.in);
			System.out.println("Is it snowing? 1-Yes, 0-No: ");
			fds.isSnow = input.nextLine().equals("1") ? true : false;
			
			System.out.println("Is it raining? 1-Yes, 0-No: ");
			fds.isRain = input.nextLine().equals("1") ? true : false;
			
			System.out.println("What is the dry-wet temp? ");
			fds.dryWet = Double.parseDouble(input.nextLine());
			
			System.out.println("What is yesterday's buildup? ");
			fds.buo = Double.parseDouble(input.nextLine());
			
			System.out.println("What is the precipitation in inches? ");
			fds.precip = Double.parseDouble(input.nextLine());
			
			System.out.println("What is the wind speed? ");
			fds.wind = Double.parseDouble(input.nextLine());
			
		}
		
		else{
				fds.isSnow = args[0].equals("1") ? true : false;
				fds.isRain = args[1].equals("1") ? true : false;
				fds.dryWet = Double.parseDouble(args[2]);
				fds.buo = Double.parseDouble(args[2]);
				fds.precip = Double.parseDouble(args[2]);
				fds.wind = Double.parseDouble(args[2]);
		}
		

		fds.fireDangerStart();
	}
	
	public void fireDangerStart() {
		if(isSnow) {
			grass = 0;
			timber = 0;
			if(isRain) {
				bui = Formula.getBuildupIndex(buo, precip);
			}
		}
		
		else {
			ffm = Formula.getFineFuelMoisture(dryWet);
			df = Formula.getDryingFactor(ffm); 
			
			//adjust fine fule for herb stage
			int [] hStage = {5, 10};
			ffm += hStage[ 0 ];
			
			if(isRain) {
				bui = Formula.getBuildupIndex(buo, precip);
			}
			
			bui += df;
			adfm = Formula.getAdjustedFuelMoisture(ffm, bui);
			if(ffm > 33) {
				grass = 1;
				timber = 1;
			}
			
			else{
				grass = Formula.getFineFuelSpread(wind, ffm);
				timber = Formula.getTimberSpreadIndex(wind, adfm);
				
				if(grass == 0 && timber == 0) {
					//do nothing
				}
				else{
					fli = Formula.getFireLoadIndex(timber, bui);
				}
				
			}
		}
		
		System.out.println("Buildup Index: " + bui + "\n" +
							"Fine Fuel Spread: " + grass + "\n" +
							"Fine Fuel Moisture: " + ffm + "\n" +
							"Adjusted Fuel Moisture: " + adfm + "\n" +
							"Timber Spread Index: " + timber + "\n" +
							"Fire Load Index: " + fli + "\n");
	}

}
