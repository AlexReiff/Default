package lab1;

import lejos.nxt.*;
import lejos.util.Delay;

public class PController implements UltrasonicController {

	private final int bandCenter, bandwith;
	private final int motorStraight = 300, FILTER_OUT = 20;
	private final NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.C;
	private int distance;
	private int currentLeftSpeed;
	private int filterControl;

	public PController(int bandCenter, int bandwith) {
		// Default Constructor
		this.bandCenter = bandCenter;
		this.bandwith = bandwith;
		leftMotor.setSpeed(motorStraight);
		rightMotor.setSpeed(motorStraight);
		leftMotor.forward();
		rightMotor.forward();
		currentLeftSpeed = 0;
		filterControl = 0;
	}

	@Override
	public void processUSData(int distance) {
		//because of the 45 degree angle, the distance read should be converted
		//to distance * cos(45) AKA distance * (.7072)
		// rudimentary filter
		if (distance == 255 && filterControl < FILTER_OUT) {
			// bad value, do not set the distance var, however do increment the
			// filter value
			filterControl++;
		} else if (distance == 255) {
			// true 255, therefore set distance to 255
			this.distance = (int) (distance * .7072);
		} else {
			// distance went below 255, therefore reset everything.
			filterControl = 0;
			this.distance = (int) (distance * .7072);
		}
		//have to increase the band center because of the distance changes
		int error = distance - (bandCenter * 2);
		int diff = getDiff(error);
		leftMotor.setSpeed(motorStraight - diff);
		rightMotor.setSpeed(motorStraight + diff);
	}

	private int getDiff(int error) {
		int diff = error * 10;
	//puts a cap on the motor rotation or else the motors are unhappy
		if (diff > 100) {
			diff = 100;
		}
		else if(diff < -150) {
			diff = -150;
		}
		
		return diff;
	}

	@Override
	public int readUSDistance() {
		return this.distance;
	}

}
