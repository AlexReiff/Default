/* Lab4.java
 * 
 * Alex Reiff and Henry Wang
 * ECSE 211 - DPM - Group 46
 * February 11, 2014
 * 
 * Initializes all necessary objects and then performs ultrasonic localization
 * followed by light sensor localization
 */
package lab4;

import lejos.nxt.*;

public class Lab4 {

	public static void main(String[] args) {
		// setup the odometer, display, navigation, and ultrasonic and light sensors
		TwoWheeledRobot patBot = new TwoWheeledRobot(Motor.A, Motor.B);
		Odometer odo = new Odometer(patBot, true);
		Navigation nav = new Navigation(odo);		
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		ColorSensor ls = new ColorSensor(SensorPort.S1);
		int buttonChoice;
		do {
			//clear the display
			LCD.clear();

			//ask the user which routine should be run
			LCD.drawString("< Left | Right >", 0, 0);
			LCD.drawString("FALLING| RISING ", 0, 1);
			LCD.drawString("  EDGE | EDGE   ", 0, 2);
			LCD.drawString("       |        ", 0, 3);
			LCD.drawString("       |        ", 0, 4);

			buttonChoice = Button.waitForAnyPress();
		} while (buttonChoice != Button.ID_LEFT &&
				 buttonChoice != Button.ID_RIGHT);


		LCDInfo lcd = new LCDInfo(odo);
		// perform the ultrasonic localization
		USLocalizer usl;
		if(buttonChoice == Button.ID_LEFT) {
			usl = new USLocalizer(odo, us, USLocalizer.LocalizationType.FALLING_EDGE);
		}
		else {
			usl = new USLocalizer(odo, us, USLocalizer.LocalizationType.RISING_EDGE);
		}
		usl.doLocalization();

		// drive to slightly off center from (0, 0)
		//nav.travelTo(7, 7);
		
		// perform the light sensor localization
//		LightLocalizer lsl = new LightLocalizer(odo, ls, nav);
//		lsl.doLocalization();			
		
		Button.waitForAnyPress();
	}

}
