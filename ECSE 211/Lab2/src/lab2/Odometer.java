/*
 * Odometer.java
 * Alex Reiff and Henry Wang
 * ECSE 211 - DPM - Group 46
 * January 27, 2014
 * 
 * Code for determining distance traveled by the robot
 */
package lab2;

import lejos.nxt.Motor;

public class Odometer extends Thread {
	// robot position
	private double x, y, theta;

	public static int lastTachoL, lastTachoR, nowTachoL, nowTachoR;
	public final double WHEEL_RADIUS = 2.1; // in CM
	public final double WHEEL_BASE = 15;// in CM
	public static final double PI = 3.14159;
	// odometer update period, in ms
	private static final long ODOMETER_PERIOD = 25;

	// lock object for mutual exclusion
	private Object lock;

	// default constructor
	public Odometer() {
		x = 0.0;
		y = 0.0;
		theta = 0.0;
		Motor.A.resetTachoCount();
		Motor.B.resetTachoCount();
		Motor.A.flt();
		Motor.B.flt();
		lastTachoL = Motor.A.getTachoCount();
		lastTachoR = Motor.B.getTachoCount();
		lock = new Object();
	}

	// run method (required for Thread)
	public void run() {
		long updateStart, updateEnd;

		while (true) {
			updateStart = System.currentTimeMillis();
			// put (some of) CODE HERE

			synchronized (lock) {
				// don't use the variables x, y, or theta anywhere but here!
				double distL, distR, deltaD, deltaT, dX, dY;
				nowTachoL = Motor.A.getTachoCount();
				nowTachoR = Motor.B.getTachoCount();
				distL = PI * WHEEL_RADIUS * (nowTachoL - lastTachoL) / 180;
				distR = PI * WHEEL_RADIUS * (nowTachoR - lastTachoR) / 180;
				lastTachoL = nowTachoL;
				lastTachoR = nowTachoR;
				deltaD = 0.5 * (distL + distR);
				deltaT = (distL - distR) / WHEEL_BASE;
				dX = deltaD * Math.sin(theta);
				dY = deltaD * Math.cos(theta);

				theta += deltaT;
				if (theta > 2 * PI) {
					theta %= 2 * PI;
				}
				x += dX;
				y += dY;
			}

			// this ensures that the odometer only runs once every period
			updateEnd = System.currentTimeMillis();
			if (updateEnd - updateStart < ODOMETER_PERIOD) {
				try {
					Thread.sleep(ODOMETER_PERIOD - (updateEnd - updateStart));
				} catch (InterruptedException e) {
					// there is nothing to be done here because it is not
					// expected that the odometer will be interrupted by
					// another thread
				}
			}
		}
	}

	// accessors
	public void getPosition(double[] position, boolean[] update) {
		// ensure that the values don't change while the odometer is running
		synchronized (lock) {
			if (update[0])
				position[0] = x;
			if (update[1])
				position[1] = y;
			if (update[2])
				position[2] = theta;
		}
	}

	public double getX() {
		double result;

		synchronized (lock) {
			result = x;
		}

		return result;
	}

	public double getY() {
		double result;

		synchronized (lock) {
			result = y;
		}

		return result;
	}

	public double getTheta() {
		double result;

		synchronized (lock) {
			result = theta;
		}

		return result;
	}

	// mutators
	public void setPosition(double[] position, boolean[] update) {
		// ensure that the values don't change while the odometer is running
		synchronized (lock) {
			if (update[0])
				x = position[0];
			if (update[1])
				y = position[1];
			if (update[2])
				theta = position[2];
		}
	}

	public void setX(double x) {
		synchronized (lock) {
			this.x = x;
		}
	}

	public void setY(double y) {
		synchronized (lock) {
			this.y = y;
		}
	}

	public void setTheta(double theta) {
		synchronized (lock) {
			this.theta = theta;
		}
	}
}