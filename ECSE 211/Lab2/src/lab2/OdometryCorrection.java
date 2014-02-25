/* 
 * OdometryCorrection.java
 * Alex Reiff and Henry Wang
 * ECSE 211 - DPM - Group 46
 * January 27, 2014
 * 
 * Code for correcting distance traveled by the robot
 * when it crossed a specific black line
 */
package lab2;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.SensorConstants;
import lejos.nxt.SensorPort;

public class OdometryCorrection extends Thread {
	private static final long CORRECTION_PERIOD = 10;
	private Odometer odometer;
	
	private static final SensorPort colorPort = SensorPort.S1;
	private ColorSensor cs;
	private int lineNumber = 1;
	
	// constructor
	public OdometryCorrection(Odometer odometer) {
		this.odometer = odometer;
        cs = new ColorSensor(colorPort);
	}

	// run method (required for Thread)
	public void run() {
		long correctionStart, correctionEnd;

		while (true) {
			correctionStart = System.currentTimeMillis();
			
			Color reading = cs.getColor();
			if (reading.equals(ColorSensor.BLACK)) {
				boolean[] update = {false, false, false};
				double[] position = {0.0, 0.0, 0.0};
				//since there are a finite order of lines
				//I hardcoded what each line represents in space
				switch(lineNumber) {
				case 1: update[1] = true;
						position[1] = 15.0;
					break;
				case 2: update[1] = true;
						position[1] = 45.0;
					break;
				case 3: update[0] = true;
						position[0] = 15.0;
					break;
				case 4: update[0] = true;
						position[0] = 45.0;
					break;
				case 5: update[1] = true;
						position[1] = 45.0;
					break;
				case 6: update[1] = true;
						position[1] = 15.0;
					break;
				case 7: update[0] = true;
						position[0] = 45.0;
					break;
				case 8: update[0] = true;
						position[0] = 15.0;
					break;
				}
				odometer.setPosition(position, update);
				lineNumber++;
				if (lineNumber > 8){
					lineNumber %= 8;
				}
			}
			
			// this ensure the odometry correction occurs only once every period
			correctionEnd = System.currentTimeMillis();
			if (correctionEnd - correctionStart < CORRECTION_PERIOD) {
				try {
					Thread.sleep(CORRECTION_PERIOD
							- (correctionEnd - correctionStart));
				} catch (InterruptedException e) {
					// there is nothing to be done here because it is not
					// expected that the odometry correction will be
					// interrupted by another thread
				}
			}
		}
	}
}