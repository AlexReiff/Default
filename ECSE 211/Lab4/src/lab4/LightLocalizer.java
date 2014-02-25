/* LightLocalizer.java
 * 
 * Alex Reiff and Henry Wang
 * ECSE 211 - DPM - Group 46
 * February 11, 2014
 * 
 * Performs localization routine using the color sensor to
 * detect grid lines and compute the robots position relative to them.
 */
package lab4;

import lejos.nxt.ColorSensor;
import lejos.nxt.Sound;
import lejos.nxt.LCD;

public class LightLocalizer {
	private Odometer odo;
	private TwoWheeledRobot robot;
	private ColorSensor ls;
	private Navigation nav;
	private double[] angles = new double[4];
	private final double radius = 11;
	
	public LightLocalizer(Odometer odo, ColorSensor ls, Navigation nav) {
		this.odo = odo;
		this.robot = odo.getTwoWheeledRobot();
		this.ls = ls;
		this.nav = nav;
		
		// turn on the light
		ls.setFloodlight(true);
		
	}
	
	public void doLocalization() {
		// drive to slightly off center from (0, 0)
		nav.travelTo(7, 7);
		// start rotating and register all 4 grid lines
		int lines = 0;
		robot.setSpeeds(0, -30);
		int reading;
		//stops rotating when all four gridlines have been found
		while (lines < 4) { 
			reading = ls.getNormalizedLightValue();
			//if the sensor reads a very dark value (AKA black)
			if (reading < 300) {
				//register the robot's current heading
				angles[lines] = odo.getAng();
				//beep for testing purposes
				Sound.beep();
				//ignores the sensor for half a second to avoid
				//registering the same line twice
				try {
	                Thread.sleep(500);
	            } catch (InterruptedException ie) {}
				lines++;
			}
		}
		ls.setFloodlight(false);
		robot.setSpeeds(0,0);
		// do trig to compute (0,0) and 0 degrees
		double yTheta = Math.abs((angles[0] - angles[2]) % 360);
		double xTheta = Math.abs((angles[1] - angles[3]) % 360);
		//Math.cos() is in radians, so make the conversion
		double yThetaRad = Math.toRadians(yTheta);
		double xThetaRad = Math.toRadians(xTheta);
		double x = -radius * Math.cos(yThetaRad/2);
		double y = -radius * Math.cos(xThetaRad/2);
		//take average to reduce error slightly
		double dTheta1 = (90 - (angles[2]) + (yTheta/2));
		double dTheta2 = (90 - (angles[0]) + (yTheta/2));
		double dTheta = (dTheta1 + dTheta2)/2;
		double currTheta = (dTheta2 + odo.getAng() + 180) % 360;

		//LCD.drawString(Double.toString(angles[0]), 0, 4);
		//LCD.drawString(Double.toString(angles[2]), 0, 6);
		//LCD.drawString(Double.toString(dTheta1), 0, 7);
		//LCD.drawString(Double.toString(dTheta2), 0, 5);
		
		// when done travel to (0,0) and turn to 0 degrees
		odo.setPosition(new double [] {x, y, currTheta}, new boolean [] {true, true, true});
		nav.travelTo(0,0);
		nav.turnTo(0, false);
	}
}
