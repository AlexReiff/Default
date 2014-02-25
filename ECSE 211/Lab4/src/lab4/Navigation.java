/*
 * File: Navigation.java
 * Written by: Sean Lawlor
 * Changed by: ALex Reiff and Henry Wang
 * ECSE 211 - Design Principles and Methods, Head TA
 * Last Update: Februrary 11, 2014
 * 
 * Movement control class (turnTo, travelTo, flt, localize)
 * Changed implementation of turnTo and travelTo methods to work
 * more natively with our code
 */
package lab4;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class Navigation {
	final static int FAST = 200, SLOW = 100, ACCELERATION = 4000;
	final static double DEG_ERR = 3.0, CM_ERR = 1.0;
	private Odometer odometer;
	private NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B;

	public Navigation(Odometer odo) {
		this.odometer = odo;

		//NXTRegulatedMotor[] motors = this.odometer.getMotors();
		//this.leftMotor = motors[0];
		//this.rightMotor = motors[1];

		// set acceleration
		this.leftMotor.setAcceleration(ACCELERATION);
		this.rightMotor.setAcceleration(ACCELERATION);
	}

	/*
	 * Functions to set the motor speeds jointly
	 */
	public void setSpeeds(float lSpd, float rSpd) {
		this.leftMotor.setSpeed(lSpd);
		this.rightMotor.setSpeed(rSpd);
		if (lSpd < 0)
			this.leftMotor.backward();
		else
			this.leftMotor.forward();
		if (rSpd < 0)
			this.rightMotor.backward();
		else
			this.rightMotor.forward();
	}

	public void setSpeeds(int lSpd, int rSpd) {
		this.leftMotor.setSpeed(lSpd);
		this.rightMotor.setSpeed(rSpd);
		if (lSpd < 0)
			this.leftMotor.backward();
		else
			this.leftMotor.forward();
		if (rSpd < 0)
			this.rightMotor.backward();
		else
			this.rightMotor.forward();
	}

	/*
	 * Float the two motors jointly
	 */
	public void setFloat() {
		this.leftMotor.stop();
		this.rightMotor.stop();
		this.leftMotor.flt(true);
		this.rightMotor.flt(true);
	}

	/*
	 * TravelTo function which takes as arguments the x and y position in cm Will travel to designated position, while
	 * constantly updating it's heading
	 */
	public void travelTo(double x, double y) {
		//computes the actual distance it has to turn
		double relativeX = x - odometer.getX(), relativeY = y - odometer.getY();
		double dist = Math.sqrt(Math.pow(relativeX, 2) + Math.pow(relativeY, 2));
		//and computes the angle it has turn beforehand
		double angle = Math.toDegrees(Math.atan(relativeX / relativeY));
		
		//turns to the angle required before heading off
		turnTo(angle, false);
		
		//turnTo() sets isNavigating to false when it returns
		//but the robot is still navigating
		
		//sets the motor speed to the faster one for travelling
		leftMotor.setSpeed(250);
		rightMotor.setSpeed(250);

		//rolls the correct distance forward.
		leftMotor.rotate(convertDistance(2.1, dist), true);
		rightMotor.rotate(convertDistance(2.1, dist), false);
	}

	/*
	 * TurnTo function which takes an angle and boolean as arguments The boolean controls whether or not to stop the
	 * motors when the turn is completed
	 */
	public void turnTo(double angle, boolean stop) {
		double relativeAngle = (angle - this.odometer.getAng()) % 360;
		if (relativeAngle < -180) {
			relativeAngle += 360;
		}
		else if (relativeAngle > 180) {
			relativeAngle -= 360;
		}
		
		//sets the motor speed to a slower speed for turning
		leftMotor.setSpeed(150);
		rightMotor.setSpeed(150);
		
		//rotates the motors at the same time to the correct angle
		leftMotor.rotate(convertAngle(2.1, 15, relativeAngle), true);
		rightMotor.rotate(-convertAngle(2.1, 15, relativeAngle), false);
		
	}

	//borrowed from Lab2's SquareDriver class
	//converts distances into numbers the motor understands
	private static int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}

	//borrowed from Lab2's SquareDrive class
	//converts angles into numbers the motor understands
	private static int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, Math.PI * width * angle / 360.0);
	}
	
	/*
	 * Go foward a set distance in cm
	 */
	public void goForward(double distance) {
		this.travelTo(Math.cos(Math.toRadians(this.odometer.getAng())) * distance, Math.cos(Math.toRadians(this.odometer.getAng())) * distance);

	}
}
