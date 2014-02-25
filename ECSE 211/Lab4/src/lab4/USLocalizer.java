/* USLocalizer.java
 * 
 * Alex Reiff and Henry Wang
 * ECSE 211 - DPM - Group 46
 * February 11, 2014
 * 
 * Performs localization routine using the ultrasonic sensor
 * to determine the angle between the walls on either side of the robot,
 * allowing the robot to determine its heading.
 */
package lab4;

import lejos.nxt.LCD;
import lejos.nxt.UltrasonicSensor;

public class USLocalizer {
	public enum LocalizationType { FALLING_EDGE, RISING_EDGE };
	public static double ROTATION_SPEED = 30;

	private Odometer odo;
	private TwoWheeledRobot robot;
	private UltrasonicSensor us;
	private LocalizationType locType;
	
	public USLocalizer(Odometer odo, UltrasonicSensor us, LocalizationType locType) {
		this.odo = odo;
		this.robot = odo.getTwoWheeledRobot();
		this.us = us;
		this.locType = locType;
		// switch off the ultrasonic sensor
		us.off();
	}
	
	public void doLocalization() {
		double [] pos = new double [3];
		double angleA, angleB;
		double dTheta;
		
		if (locType == LocalizationType.FALLING_EDGE) {
			// rotate the robot until it sees no wall
			while (getFilteredData() < 50) {
				robot.setSpeeds(0, 50);	
			}
			// keep rotating until the robot sees a wall, then latch the angle
			while (getFilteredData() > 50) {
				robot.setSpeeds(0, 50);
			}
			angleA = robot.getHeading();
			
			// switch direction and wait until it sees no wall
			while (getFilteredData() < 50) {
				robot.setSpeeds(0, -50);
			}
			// keep rotating until the robot sees a wall, then latch the angle
			while (getFilteredData() > 50) {
				robot.setSpeeds(0, -50);
			}
			angleB = robot.getHeading();
			
			robot.setSpeeds(0, 0);
			// angleA is clockwise from angleB, so assume the average of the
			// angles to the right of angleB is 45 degrees past 'north'
			
			// update the odometer position
			dTheta = 50.0 - (angleA + angleB)/2.0;
			
			double currTheta = (dTheta + robot.getHeading()) % 360;
			odo.setPosition(new double [] {0.0, 0.0, currTheta}, new boolean [] {true, true, true});
		} else {
			/*
			 * The robot should turn until it sees the wall, then look for the
			 * "rising edges:" the points where it no longer sees the wall.
			 * This is very similar to the FALLING_EDGE routine, but the robot
			 * will face toward the wall for most of it.
			 */
			while (getFilteredData() > 50) {
				robot.setSpeeds(0, 50);
			}
			// keep rotating until the robot sees a wall, then latch the angle
			while (getFilteredData() < 50) {
				robot.setSpeeds(0, 50);
			}
			angleA = robot.getHeading();
			// switch direction and wait until it sees no wall
			while (getFilteredData() > 50) {
				robot.setSpeeds(0, -50);
			}
			// keep rotating until the robot sees a wall, then latch the angle
			while (getFilteredData() < 50) {
				robot.setSpeeds(0, -50);
			}
			angleB = robot.getHeading();
			robot.setSpeeds(0, 0);
			// angleA is counterclockwise from angleB, so assume the average of the
			// angles to the right of angleB is 225 degrees past 'north'
						
			dTheta = 230.0 - (angleA + angleB)/2.0;
			// update the odometer position
			double currTheta = (dTheta + robot.getHeading()) % 360;
			odo.setPosition(new double [] {0.0, 0.0, currTheta}, new boolean [] {true, true, true});
		}
	}
	
	private int getFilteredData() {
		int distance;
		
		// do a ping
		us.ping();
		
		// wait for the ping to complete
		try { Thread.sleep(50); } catch (InterruptedException e) {}
		
		// there will be a delay here
		distance = us.getDistance();
				
		return distance;
	}

}
