/* Driver.java
 * 
 * Alex Reiff and Henry Wang
 * ECSE 211 - DPM - Group 46
 * February 4, 2014
 * 
 * Runs the two different navigation routines as stipulated
 * in the lab 3 assignment.
 */
package lab3;

import lejos.nxt.*;

public class Driver extends Thread {
	private static final double LEFT_RADIUS = 2.1, RIGHT_RADIUS = 2.1, WHEEL_BASE = 15;
	private static final int FORWARD_SPEED = 250, ROTATE_SPEED = 150;
	private static final long DRIVER_PERIOD = 250;
	private final NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B;
	
	private Odometer odometer;
	private Object lock;
	
	private boolean isNavigating = false;
	private boolean isAvoiding = false;
	private int sideNum = 0;
	private int version;
	private double obstacleDistance;
	private double myX, myY, myTheta;
	
	//makes sure that it is reading the same odometer as everything else
	public Driver(Odometer odometer) {
		this.odometer = odometer;
		lock = new Object();	
	}
	
	//tells the robot if it is running part 1 or part 2
	public void setVersion(int i) {
		version = i;
	}
	
	public void run() {
		//initialize motors before starting to drive
		for (NXTRegulatedMotor motor : new NXTRegulatedMotor[] { leftMotor, rightMotor }) {
			motor.stop();
			motor.setAcceleration(3000);
		}
		//sleep for 2 seconds to make sure everything has loaded up
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {}
		
		long driverStart, driverEnd;
		//main thread loop
		while (true) {
			driverStart = System.currentTimeMillis();
			
			// driving routine for part 1
			if(version == 1) {
				switch(sideNum) {
				case 0: travelTo(60.0, 30.0);
					break;
				case 1: travelTo(30.0, 30.0);
					break;
				case 2: travelTo(30.0, 60.0);
					break;
				case 3: travelTo(60.0, 00.0); 
					break;
				case 4: for (NXTRegulatedMotor motor : new NXTRegulatedMotor[] { Motor.A, Motor.B, Motor.C }) {
					motor.forward();
					motor.flt();
				}
					break;
				}
			}
		
			// driving routine for part 2
			else if (version == 2) {
				switch(sideNum) {
				case 0: travelTo(00.0, 60.0);
					break;
				case 1: travelTo(60.0, 00.0);
					break;
				case 2: System.exit(0);
					break;
				}
			}
			driverEnd = System.currentTimeMillis();
			//regulates the thread rate of driver
			if (driverEnd - driverStart < DRIVER_PERIOD) {
				try {
					Thread.sleep(DRIVER_PERIOD - (driverEnd - driverStart));
				} catch (InterruptedException e) {}
			}
		}
	}
	
	//drives the robot to the given absolute coordinates
	public void travelTo(double x, double y) {
		//the robot is currently navigating
		synchronized (lock) {
			isNavigating = true;
		}
		//computes the actual distance it has to turn
		double relativeX = x - myX, relativeY = y - myY;
		double dist = Math.sqrt(Math.pow(relativeX, 2) + Math.pow(relativeY, 2));
		//and computes the angle it has turn beforehand
		double angle = Math.toDegrees(Math.atan(relativeX / relativeY));
		
		//arctan prefers to give the positive value on this turn, so have to correct that
		if (sideNum == 3) {
			angle += 180;
		}
		
		//non-reproduceable bug on this turn made me want to hard code this turn
		if(sideNum == 1) {
			turnTo(-90);
		}
		//turns to the angle required before heading off
		else {
			turnTo(angle);
		}
		//turnTo() sets isNavigating to false when it returns
		//but the robot is still navigating
		synchronized (lock) {
			isNavigating = true;
		}
		//sets the motor speed to the faster one for travelling
		leftMotor.setSpeed(FORWARD_SPEED);
		rightMotor.setSpeed(FORWARD_SPEED);

		//rolls the correct distance forward.
		leftMotor.rotate(convertDistance(LEFT_RADIUS, dist), true);
		rightMotor.rotate(convertDistance(RIGHT_RADIUS, dist), false);

		//the robot has completed this part of its routine
		sideNum++;
		//the robot has stopped navigating
		synchronized (lock) {
			isNavigating = false;
		}
	}
	
	//turns the robot to the absolute theta given
	public void turnTo(double theta) {
		//the robot is currently navigating
		synchronized (lock) {
			isNavigating = true;
		}
		//computes the actual angle it has to turn
		double relativeAngle = (theta - myTheta) % 360; 
		
		//sets the motor speed to a slower speed for turning
		leftMotor.setSpeed(ROTATE_SPEED);
		rightMotor.setSpeed(ROTATE_SPEED);
		
		//rotates the motors at the same time to the correct angle
		leftMotor.rotate(convertAngle(LEFT_RADIUS, WHEEL_BASE, relativeAngle), true);
		rightMotor.rotate(-convertAngle(RIGHT_RADIUS, WHEEL_BASE, relativeAngle), false);
		
		//the robot has stopped navigating
		synchronized (lock) {
			isNavigating = false;
		}
	}
	
	//returns if the robot is currently driving
	public boolean isNavigating() {
			return isNavigating;
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
	
	//gets the current x heading from odometer
	public void setX(double x) {
		synchronized (lock) {
		myX = x;
		}
	}
	
	//gets the current y heading from odometer
	public void setY(double y) {
		synchronized (lock) {
			myY = y;
		}
	}

	//gets the current theta from the odometer
	public void setTheta(double t) {
		synchronized (lock) {
			myTheta = Math.toDegrees(t);
		}
	}
	
	//gets the ultrasonic distance from the sensor
	public void setObs(double dist) {
		synchronized (lock) {
			obstacleDistance = dist;
		//begin avoidance if too close to obstacle
			if (obstacleDistance < 30) {
				isAvoiding = true;
			}
			else {
				isAvoiding = false;
			}
		}
	}
	
	public void avoidObstacle() {
		leftMotor.setSpeed(FORWARD_SPEED);
		rightMotor.setSpeed(FORWARD_SPEED);
		leftMotor.forward();
		rightMotor.forward();
		while (obstacleDistance < 30) {
			leftMotor.setSpeed(FORWARD_SPEED + 150);
			rightMotor.setSpeed(FORWARD_SPEED - 150);
		}
		isAvoiding = false;
		LCD.drawString("      ", 0 , 4);
	}
}
