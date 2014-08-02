/* Lab3.java
 * 
 * Alex Reiff and Henry Wang
 * ECSE 211 - DPM - Group 46
 * February 4, 2014
 * 
 * Initializes the different threads needed to run navigation
 */
package lab3;
import lejos.nxt.*;

public class Lab3 {
	public static void main(String[] args) {
		int buttonChoice;

		//the objects that need to be instantiated
		Odometer odometer = new Odometer();
		Driver driver = new Driver(odometer);
		OdometryDisplay odometryDisplay = new OdometryDisplay(driver, odometer);
		UltrasonicInterrupt usI = new UltrasonicInterrupt(driver);

		do {
			//clear the display
			LCD.clear();

			//ask the user which routine should be run
			LCD.drawString("< Left | Right >", 0, 0);
			LCD.drawString("Obsta- | No     ", 0, 1);
			LCD.drawString("cle    | Detec- ", 0, 2);
			LCD.drawString("Detec- | tion   ", 0, 3);
			LCD.drawString("tion   |        ", 0, 4);

			buttonChoice = Button.waitForAnyPress();
		} while (buttonChoice != Button.ID_LEFT &&
				 buttonChoice != Button.ID_RIGHT);

		if (buttonChoice == Button.ID_LEFT) {
			// start the robot on the obstacle correction routine
			odometer.start();
			odometryDisplay.start();
			usI.start();
			driver.setVersion(2);
			driver.start();
		} else {
			// start the robot on the non-obstalce correction routine
			odometer.start();
			odometryDisplay.start();
			driver.setVersion(1);
			driver.start();
		}
		
		//exit on button press
		while (Button.waitForAnyPress() != Button.ID_ESCAPE);
		System.exit(0);
	}
}