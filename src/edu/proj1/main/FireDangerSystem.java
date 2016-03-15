package edu.proj1.main; 

import java.util.Scanner;

import edu.proj1.compute.Formula;

/********
 * Class for executing a run through of the fire danger system
 * ********/
public class FireDangerSystem {
	public boolean isSnow, isRain;
	public double grass=0, timber=0, bui=0, ffm=0, adfm=0, fli=0, df=0, dryWet = 0, buo = 0, precip = 0, wind=0;
	
	public FireDangerSystem() {}
	
	/*****
	 * Command line entry for the fire danger system. Can be executed via prompts or straight from command line<br>
	 * @param args :<br>
	 * args[0] = 1 or 0 for whether it's snowing<br>
	 * args[1] = 1 or 0 for whether it's raining<br>
	 * args[2] = dry-wet temp<br>
	 * args[3] = yesterday's buildup<br>
	 * args[4] = precipitation in inches<br>
	 * args[5] = wind speed in mph
	 * *****/
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
			
			input.close();
			
		}
		
		else{
				fds.isSnow = args[0].equals("1") ? true : false;
				fds.isRain = args[1].equals("1") ? true : false;
				fds.dryWet = Double.parseDouble(args[2]);
				fds.buo = Double.parseDouble(args[3]);
				fds.precip = Double.parseDouble(args[4]);
				fds.wind = Double.parseDouble(args[5]);
		}
		

		fds.fireDangerStart();
	}
	
	/*****
	 * Method that goes through a run of the fire danger system. Outputs are pushed to the console at termination
	 * *******/
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
