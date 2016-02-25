package edu.proj1.test;

import edu.proj1.compute.Formula;

public class Test {
	static boolean isSnow, isRain;
	static double grass=0, timber=0, bui=0, ffm=0, adfm=0, fli=0, df=0, dryWet = 12.2, buo = 10, precip = 1, wind=20;
	static int testNo = 0;

	public static void main(String[] args) {
		isSnow = true;
		isRain = false;
		test();
		
		isRain = true;
		test();
		
		isSnow = false;
		test();
		
		isRain = false;
		test();
		
		isRain = true;
		test();
		
		dryWet = 30;
		test();
	}
	
	public static void test() {
		System.out.println("Test number " + ++testNo);
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
